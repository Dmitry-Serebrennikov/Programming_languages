#include "Byte.h"
#include <iostream>
#include <vector>

using namespace std;

int main() {
	vector <Byte> arr_unsigned(256);
	vector <Byte> arr_signed(256);

	for (int i = 0; i <= 255; i++) {
		arr_unsigned[i].setSign(false);
		arr_unsigned[i].setValue(i);
	}

	for (int i = -128; i <= 127; i++) {
		arr_signed[i + 128].setSign(true);
		arr_signed[i + 128].setValue(i);
	}


	cout << "__________ARR_UNSIGNED_TEST__________\n\n";
	for (int i = 0; i <= 255; i++) {
		cout << arr_unsigned[i] << "\t" << arr_unsigned[i].getInt() << "\t" << arr_unsigned[i].getValue() << "\n";
	}

	cout << "\n\n\n__________ARR_SIGNED_TEST__________\n\n";
	for (int i = 0; i <= 255; i++) {
		cout << arr_signed[i] << "\t" << arr_signed[i].getInt() << "\t" << arr_signed[i].getValue() << "\n";
	}


	cout << "\n\n\n__________OPERATORS_TEST__________\n\n";
	Byte NA("00011010");
	Byte NB("11001110");
	Byte NAOB = NA | NB;
	Byte NAAB = NA & NB;
	cout << NA << "\t" << NA.getInt() << "\n";
	cout << NB << "\t" << NB.getInt() << "\n";
	cout << NAOB << "\t" << NAOB.getInt() << "\n";
	cout << NAAB << "\t" << NAAB.getInt() << "\n";
}