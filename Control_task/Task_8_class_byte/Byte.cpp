#include "Byte.h"
#include <iostream>

int gint(char ch) {
	return int(ch - '0');
}

void check(string& binary) {
	if (binary.size() > 8) throw ByteException();
	for (int i = 0; i < binary.size(); i++) {
		if (binary[i] != '0' && binary[i] != '1') throw ByteException();
	}
	for (int i = binary.size(); i < 8; i++) {
		binary = "0" + binary;
	}
}

Byte::Byte() {
	binary = "00000000";
	sign = true;
}

Byte::Byte(int value, bool is_signed) { 
	sign = is_signed;
	convert_to_binary(value);
}

Byte::Byte(string binary_string, bool is_signed) {
	check(binary_string);
	binary = binary_string;
	sign = is_signed;
}

void Byte::convert_to_binary(int value) {
	if (value < -128 || value > 255) throw ByteException(); 
	if (value < 0) value += 256;

	binary = "";
	while (value > 0) {
		if (value % 2) binary += "1";
		else binary += "0";
		value /= 2;
	}

	for (int i = binary.size(); i < 8; i++) {
		binary += "0";
	}

	reverse(binary.begin(), binary.end());
}

int Byte::convert_from_binary() {
	int temp = 0, k = 64; 
	bool negative = (sign && gint(binary[0]));

	for (int i = 1; i < 8; i++) {
		if (negative) temp -= gint(binary[i]) * k;
		else temp += gint(binary[i]) * k;
		k /= 2;
	}
	if (!sign) temp += gint(binary[0]) * 128;
	if (negative) temp--;
	return temp;
}

int Byte::getBit(int number) {
	if (number < 0 || number > 7) throw ByteException();
	return gint(binary[number]);
}

void Byte::setBit(int number, bool value) {
	string before = "", after = "";
	if (number > 0) {
		before = binary.substr(0, number);
	}
	if (number < 7) {
		after = binary.substr(number + 1);
	}
	binary = before + to_string(value) + after;
}

ostream& operator << (ostream& out, const Byte& byte) {
	out << "0b" << byte.binary;
	return out;
}

string Byte::getValue() {
	return "0b" + binary;
}

int Byte::getInt() {
	return convert_from_binary();
}

void Byte::setValue(int number) {
	convert_to_binary(number);
}

void Byte::setValue(string binary_string) {
	check(binary_string);
	binary = binary_string;
}

void Byte::setSign(bool is_signed) {
	sign = is_signed;
}

Byte Byte::operator | (const Byte& b) const {
	string temp = "";
	for (int i = 0; i < 8; i++) {
		temp += to_string(gint(this->binary[i]) || gint(b.binary[i]));
	}
	Byte new_byte(temp, this->sign);
	return new_byte;
}

Byte Byte::operator & (const Byte& b) const {
	string temp = "";
	for (int i = 0; i < 8; i++) {
		temp += to_string(gint(this->binary[i]) && gint(b.binary[i]));
	}
	Byte new_byte(temp, this->sign);
	return new_byte;
}