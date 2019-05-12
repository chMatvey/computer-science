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
	HANDLE hMutexR1, hMutexR2, hMutexW1, hMutexW2;	// mutex for maximum 2 current r/w
	HANDLE hMsgA, hMsgB;	// msg events
	HANDLE hEndW, hEndR;	// end session events
	int msgCount, processCount;	// messages count, r/w count

	cout << "Enter number of Readers/Writers" << endl;
	processCount = getCount(1, 5);

	cout << "Enter number of messages: " << endl;
	msgCount = getCount(1, 5);

	hMutexR1 = CreateMutex(NULL, FALSE, L"MutexR1");
	hMutexR2 = CreateMutex(NULL, FALSE, L"MutexR2");
	hMutexW1 = CreateMutex(NULL, FALSE, L"MutexW1");
	hMutexW2 = CreateMutex(NULL, FALSE, L"MutexW2");

	if (hMutexR1 == NULL || hMutexR2 == NULL || hMutexW1 == NULL || hMutexW2 == NULL)
	{
		cout << "Create mutex failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	hMsgA = CreateEvent(NULL, TRUE, FALSE, L"MessageA");
	hMsgB = CreateEvent(NULL, TRUE, FALSE, L"MessageB");

	hEndW = CreateEvent(NULL, TRUE, FALSE, L"EndW");
	hEndR = CreateEvent(NULL, TRUE, FALSE, L"EndR");

	if (hMsgA == NULL || hMsgB == NULL || hEndW == NULL || hEndR == NULL)
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
		LPCWSTR lpszReaderName = L"E:\\project\\computer-science\\operation-systems\\lab2\\Administrator\\Debug\\Reader.exe";
		if (!CreateProcess(lpszReaderName, NULL, NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &si2, &pi2)) {
			cout << "The new Reader process is not created." << endl;
			cout << "Press any key to exit." << endl;
			cin.get();
			return GetLastError();
		}

		LPCWSTR lpszWritterName = L"E:\\project\\computer-science\\operation-systems\\lab2\\Administrator\\Debug\\Writer.exe";
		if (!CreateProcess(lpszWritterName, NULL, NULL, NULL, FALSE, CREATE_NEW_CONSOLE, NULL, NULL, &si, &pi))	{
			cout << "The new Writer process is not created." << endl;
			cout << "Press2 any key to exit." << endl;
			cin.get();
			return GetLastError();
		}
	}

	// open end session events
	int ends = 0;
	HANDLE hEnd[2];
	hEnd[0] = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"EndW");
	hEnd[1] = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"EndR");

	while (ends < processCount)	// wait all sessions end
	{
		WaitForMultipleObjects(2, hEnd, TRUE, INFINITE);
		ResetEvent(hEnd[0]);	// reset events for next session
		ResetEvent(hEnd[1]);
		ends++;
		cout << "Writer and Reader session closed" << endl;
	}


	// close all handles
	CloseHandle(hMutexR1);
	CloseHandle(hMutexR2);
	CloseHandle(hMutexW1);
	CloseHandle(hMutexW2);

	CloseHandle(hMsgA);
	CloseHandle(hMsgB);
	CloseHandle(hEndW);
	CloseHandle(hEndR);

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