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
	HANDLE hMutex[2];
	HANDLE hMsg, hEnd;

	hMutex[0] = OpenMutex(SYNCHRONIZE, FALSE, L"MutexR1");
	hMutex[1] = OpenMutex(SYNCHRONIZE, FALSE, L"MutexR2");

	if (hMutex[0] == NULL || hMutex[1] == NULL) {
		cout << "Open mutex failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}


	FILE *f;	// file w/ message
	char fName[16];	// filename

	cout << "Waiting for ending other Readers..." << endl;
	while (true)	// while all mutex' is busy
	{
		if (WaitForSingleObject(hMutex[0], 500) == WAIT_OBJECT_0)
		{
			hMsg = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"MessageA");	// open MessageA event
			strcpy(fName, "MessageA.txt");
			break;
		}
		if (WaitForSingleObject(hMutex[1], 500) == WAIT_OBJECT_0)	// same fro B
		{
			hMsg = OpenEvent(EVENT_ALL_ACCESS, FALSE, L"MessageB");
			strcpy(fName, "MessageB.txt");
			break;
		}
	}

	hEnd = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"EndR");	// open end reader event
	if (hMsg == NULL || hEnd == NULL) {
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

		DWORD dwRes = WaitForSingleObject(hMsg, INFINITE);
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

		ResetEvent(hMsg);	// reset event, wait new msg
		i++;
	}

	SetEvent(hEnd);	// end reader process
	cout << "All messages was received" << endl;
	Sleep(5000);
	// close handles
	ReleaseMutex(hMutex[0]);
	ReleaseMutex(hMutex[1]);
	CloseHandle(hMutex[0]);
	CloseHandle(hMutex[1]);
	CloseHandle(hMsg);
	CloseHandle(hEnd);

	return 0;
}