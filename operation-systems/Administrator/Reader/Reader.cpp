#include "pch.h"
#include <windows.h>
#include <iostream>
#include <tchar.h>
#include <strsafe.h>

#pragma warning(disable : 4996)

constexpr auto MAX_BUF = 256;

using namespace std;

int main()
{
	HANDLE hMutexReader;
	HANDLE hMsgEvent, hEndEvent;

	hMutexReader = OpenMutex(SYNCHRONIZE, FALSE, L"MutexReader");

	if (hMutexReader == NULL) {
		cout << "Open mutex failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	FILE *f;	// file w/ message
	char fName[16];	// filename

	cout << "Waiting for ending other Readers..." << endl;

	if (!WaitForSingleObject(hMutexReader, INFINITE) == WAIT_OBJECT_0) {
		cout << "Wait for single object failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	hMsgEvent = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"MessageEvent");	// open MessageA event
	strcpy(fName, "Message.txt");

	hEndEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"EndReaderEvent");	// open end reader event

	if (hMsgEvent == NULL || hEndEvent == NULL) {
		cout << "Open event failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	int i = 0;
	TCHAR buf[MAX_BUF] = { 0 };	// buffer
	GetEnvironmentVariable(L"msgCount", buf, MAX_BUF);	// total msg
	LPTSTR endPtr;
	int msgCount = _tcstod(buf, &endPtr);	// to int

	while (i < msgCount)	// wait all msgs
	{
		cout << "Waiting message...\r\n" << endl;

		DWORD dwRes = WaitForSingleObject(hMsgEvent, INFINITE);
		if (dwRes != WAIT_OBJECT_0) {
			cout << "Wait for single object failed\r\nERROR: %d ERRORCODE: %d" << endl;
			cout << dwRes << endl;
			cout << GetLastError() << endl;
		}

		f = fopen(fName, "rt");	// open msg file
		memset(buf, 0, sizeof(buf));	// null previous buffer
		fread(buf, sizeof(TCHAR), MAX_BUF, f);	// read msg from file
		fclose(f);	// close file

		_tprintf(L"Message recieved: %s\r\n", buf);	// print msg

		ResetEvent(hMsgEvent);	// reset event, wait new msg
		i++;
	}

	SetEvent(hEndEvent);	// end reader process
	cout << "All messages was received" << endl;
	Sleep(5000);
	// close handles
	ReleaseMutex(hMutexReader);
	CloseHandle(hMutexReader);
	CloseHandle(hMsgEvent);
	CloseHandle(hEndEvent);

	return 0;
}