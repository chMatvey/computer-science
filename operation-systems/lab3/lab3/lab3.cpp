#include "pch.h"
#include <windows.h>
#include <iostream>

using namespace std;

struct ArrayStruct
{
	volatile int *array;
	int size;
	int leftBorder;
	int rigttBorder;
	int index;
	CRITICAL_SECTION cs;
	HANDLE hWorkSemaphore;
};

DWORD WINAPI work(ArrayStruct *arrayStruct) {
	EnterCriticalSection(&arrayStruct->cs);

	int *result = new int[arrayStruct->size] { 0 };

	int index = 0;
	int sleep;

	cout << "Please time interval: ";
	cin >> sleep;

	for (int i = 0; i < arrayStruct->size; i++) {
		if (arrayStruct->array[i] >= arrayStruct->leftBorder && arrayStruct->array[i] <= arrayStruct->rigttBorder) {
			result[index] = arrayStruct->array[i];
			arrayStruct->index = i;
			index++;
		}
		else {
			arrayStruct->index = -1;
		}

		ReleaseSemaphore(arrayStruct->hWorkSemaphore, 1, NULL);
		Sleep(sleep * 1000);
	}

	arrayStruct->array = result;
	arrayStruct->size = index;

	LeaveCriticalSection(&arrayStruct->cs);
	return 0;
}

DWORD WINAPI multElement(ArrayStruct *arrayStruct) {
	Sleep(1000);
	EnterCriticalSection(&arrayStruct->cs);

	int result = 0;	
	if (!arrayStruct->size == 0) {
		result = 1;
		for (int i = 0; i < arrayStruct->size; i++) {
			result *= arrayStruct->array[i];
		}
	}
	cout << endl << "Multiplication = " << result << endl;
	
	LeaveCriticalSection(&arrayStruct->cs);
	ReleaseSemaphore(arrayStruct->hWorkSemaphore, 1, NULL);
	return 0;
}

int main() {
	HANDLE hWork, hMult;
	DWORD IDWork, IDMult;

	ArrayStruct arrayStruct;
	arrayStruct.index = -1;

	cout << "Please enter size of array: ";
	cin >> arrayStruct.size;

	volatile int *array;
	array = new int[arrayStruct.size];

	cout << "Please enter " << arrayStruct.size << " elements of array divided by space: ";
	int element;
	for (int i = 0; i < arrayStruct.size; i++) {
		cin >> element;
		array[i] = element;
	}
	arrayStruct.array = array;

	cout << "Please enter left border for search: ";
	cin >> arrayStruct.leftBorder;

	cout << "Please enter right border for search: ";
	cin >> arrayStruct.rigttBorder;

	cout << "Source array: " << endl;
	for (int i = 0; i < arrayStruct.size; i++) {
		cout << array[i] << " ";
	}
	cout << endl;

	arrayStruct.hWorkSemaphore = CreateSemaphore(NULL, 0, 1, L"hWorkSemaphore");
	InitializeCriticalSection(&arrayStruct.cs);

	hWork = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)work, &arrayStruct, 0, &IDWork);
	hMult = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)multElement, &arrayStruct, 0, &IDMult);

	for (int i = 0; i < arrayStruct.size; i++) {
		WaitForSingleObject(arrayStruct.hWorkSemaphore, INFINITE);
		if (arrayStruct.index != -1) {
			cout << "Found element: " << array[arrayStruct.index] << endl;
			cout.flush();
		}
	}
	WaitForSingleObject(arrayStruct.hWorkSemaphore, INFINITE);

	DeleteCriticalSection(&arrayStruct.cs);
	CloseHandle(arrayStruct.hWorkSemaphore);
	CloseHandle(hMult);
	CloseHandle(hWork);
	return 0;
}