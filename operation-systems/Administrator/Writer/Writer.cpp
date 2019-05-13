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
	HANDLE hMutexWriter;
	HANDLE hMsgEvent, hEndEvent;

	hMutexWriter = OpenMutex(SYNCHRONIZE, FALSE, L"MutexWriter");
	if (hMutexWriter == NULL) {
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

	if (!WaitForSingleObject(hMutexWriter, INFINITE) == WAIT_OBJECT_0) {
		cout << "Wait for single object failed." << endl;
		cout << "Press any key to exit." << endl;
		cin.get();
		return GetLastError();
	}

	hMsgEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"MessageEvent");
	strcpy_s(fName, "Message.txt");

	hEndEvent = OpenEvent(EVENT_MODIFY_STATE, FALSE, L"EndWriteEvent");

	if (hMsgEvent == NULL || hEndEvent == NULL) {
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
		SetEvent(hMsgEvent);
		Sleep(1000);
		i++;
	}

	SetEvent(hEndEvent);
	ReleaseMutex(hMutexWriter);
	CloseHandle(hMutexWriter);
	CloseHandle(hMsgEvent);
	CloseHandle(hEndEvent);
	return 0;
}