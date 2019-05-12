#include "pch.h"
#include <windows.h>
#include <iostream>
#include <fstream>
#include <tchar.h>
#include <strsafe.h>
#include <stdlib.h>
#include <stdio.h>

constexpr auto MAX_BUF = 256;

using namespace std;

int main() {
	HANDLE hMutex[2];
	HANDLE hMsg, hEnd;
	hMutex[0] = OpenMutex(SYNCHRONIZE, FALSE, L"MutexW1");
	hMutex[1] = OpenMutex(SYNCHRONIZE, FALSE, L"MutexW2");

	if (hMutex[0] == NULL || hMutex[1] == NULL) {
		cout << "Open mutex failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	FILE *f;
	char fName[16];
	FILE *stream;
	errno_t err;

	cout << "Waiting for ending other Writers..." << endl;

	while (true) {
		if (WaitForSingleObject(hMutex[0], 500) == WAIT_OBJECT_0) {
			hMsg = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"MessageA");
			strcpy_s(fName, "MessageA.txt");
			break;
		}
		if (WaitForSingleObject(hMutex[1], 500) == WAIT_OBJECT_0) {
			hMsg = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"MessageB");
			strcpy_s(fName, "MessageB.txt");
			break;
		}
	}

	hEnd = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"EndW");
	if (hMsg == NULL || hEnd == NULL) {
		cout << "Open event failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	int i = 0;
	TCHAR buf[MAX_BUF] = { 0 };
	GetEnvironmentVariable(L"msgCount", buf, MAX_BUF);
	LPTSTR endPtr;
	int msgCount = _tcstod(buf, &endPtr);

	while (i < msgCount) {
		cout << "Enter ";;
		cout << msgCount - i;
		cout << " message(s):" << endl;
		wscanf_s(L"%s", buf, 255);
		err = fopen_s(&stream, fName, "wt");
		fwrite(buf, sizeof(TCHAR), _tcslen(buf), stream);
		fclose(stream);
		cout << "Sending message..." << endl;
		SetEvent(hMsg);
		Sleep(1000);
		i++;
	}

	SetEvent(hEnd);
	ReleaseMutex(hMutex[0]);
	ReleaseMutex(hMutex[1]);
	CloseHandle(hMutex[0]);
	CloseHandle(hMutex[1]);
	CloseHandle(hMsg);
	CloseHandle(hEnd);
	return 0;
}