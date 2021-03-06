#include "pch.h"
#include <windows.h>
#include <iostream>
#include <strsafe.h>

constexpr auto MAX_BUF = 256;

using namespace std;

int getCount(int min, int max);

int main()
{
	TCHAR buf[MAX_BUF] = { 0 };
	HANDLE hMutexReader, hMutexWriter;	// mutex for r/w
	HANDLE hMsgEvent;	// msg event
	HANDLE hEndReaderEvent, hEndWriterEvent;	// end session events
	int msgCount, processCount;	// messages count, r/w count

	cout << "Enter number of Readers/Writers" << endl;
	processCount = getCount(1, 5);

	cout << "Enter number of messages: " << endl;
	msgCount = getCount(1, 5);

	hMutexReader = CreateMutex(NULL, FALSE, L"MutexReader");
	hMutexWriter = CreateMutex(NULL, FALSE, L"MutexWriter");

	if (hMutexReader == NULL || hMutexWriter == NULL)
	{
		cout << "Create mutex failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	hMsgEvent = CreateEvent(NULL, TRUE, FALSE, L"MessageEvent");
	hEndWriterEvent = CreateEvent(NULL, TRUE, FALSE, L"EndWriteEvent");
	hEndReaderEvent = CreateEvent(NULL, TRUE, FALSE, L"EndReaderEvent");

	if (hMsgEvent == NULL || hEndWriterEvent == NULL || hEndReaderEvent == NULL)
	{
		cout << "Create event failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	StringCbPrintf(buf, MAX_BUF, L"%d", msgCount);
	SetEnvironmentVariable(L"msgCount", buf);

	int i = 0;
	while (i < processCount)	// run r/w count
	{
		i++;
		STARTUPINFO si, si2;
		PROCESS_INFORMATION pi, pi2;
		ZeroMemory(&si, sizeof(STARTUPINFO));
		si.cb = sizeof(STARTUPINFO);
		ZeroMemory(&si2, sizeof(STARTUPINFO));
		si2.cb = sizeof(STARTUPINFO);

		// start writer and reader
		LPCWSTR lpszReaderName = L"Reader.exe";
		if (!CreateProcess(lpszReaderName, NULL, NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &si2, &pi2)) {
			cout << "The new Reader process is not created." << endl;
			cout << "Press any key to exit." << endl;
			cin.get();
			return GetLastError();
		}

		LPCWSTR lpszWritterName = L"Writer.exe";
		if (!CreateProcess(lpszWritterName, NULL, NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi))	{
			cout << "The new Writer process is not created." << endl;
			cout << "Press2 any key to exit." << endl;
			cin.get();
			return GetLastError();
		}
	}

	// open end session events
	int ends = 0;
	HANDLE hEndEvents[2];
	hEndEvents[0] = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"EndWriteEvent");
	hEndEvents[1] = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"EndReaderEvent");

	while (ends < processCount)	// wait all sessions end
	{
		WaitForMultipleObjects(2, hEndEvents, TRUE, INFINITE);
		ResetEvent(hEndEvents[0]);	// reset events for next session
		ResetEvent(hEndEvents[1]);
		ends++;
		cout << "Writer and Reader session closed" << endl;
	}


	// close all handles
	CloseHandle(hMutexReader);
	CloseHandle(hMutexWriter);

	CloseHandle(hMsgEvent);
	CloseHandle(hEndWriterEvent);
	CloseHandle(hEndReaderEvent);

	return 0;
}

int getCount(int min, int max)
{
	int result;

	bool isCorrect = false;

	do {
		cout << "Min - ";
		cout << min << endl;
		cout << "Max - ";
		cout << max << endl;

		cin >> result;

		if (result < min || result > max) {
			cout << "Incorrect value" << endl;
			cout << "Repeat pleas" << endl;
		} 
		else {
			isCorrect = true;
		}

	} while (!isCorrect);

	return result;
}