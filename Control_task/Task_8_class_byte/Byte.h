#pragma once
#include <string>

using namespace std;

class ByteException {};

class Byte
{
private:
	bool sign;
	string binary;

	void convert_to_binary(int value);
	int convert_from_binary();
public:
	explicit Byte();
	explicit Byte(int value, bool is_signed = true);
	explicit Byte(string binary_string, bool is_signed = true);

	int getBit(int number);
	void setBit(int number, bool value); 

	string getValue();
	int getInt();
	void setValue(int value);
	void setValue(string binary_string);
	void setSign(bool is_signed);

	Byte operator | (const Byte& b) const;
	Byte operator & (const Byte& b) const;

	friend ostream& operator << (ostream& out, const Byte& byte);
};