#include <iostream>
#include <fstream>
#include <vector>
#include <utility>
#include <algorithm>
#pragma warning(disable : 4996)

using namespace std;

struct Point {
	int peak;
	int index;
};

bool cmp(Point a, Point b) {
	return a.peak > b.peak;
}

void print(const vector <int>& arr, const vector <int>& water_level, int max_water_level, long long int res) {
	for (int i = 0; i < max_water_level + 1; i++) {
		for (int j = 1; j < arr.size() - 1; j++) {
			if (max_water_level - i >= arr[j] + water_level[j]) {
				cout << "0 ";
			}
			else if (max_water_level - i >= arr[j]) {
				cout << "W ";
			}
			else {
				cout << "L ";
			}
		}
		cout << "\n";
	}
	cout << "Total number of water blocks in landscape after rain: " << res << endl;
}

int main() {
	freopen("input.txt", "r", stdin);

	int temp, max_water_level = 0;
	vector <int> arr(1, 0);
	while (cin >> temp) {
		arr.push_back(temp);
		max_water_level = max(max_water_level, temp);
	}
	arr.push_back(0);

	vector <Point> peaks;
	for (int i = 1; i < arr.size() - 1; i++) {

		if (arr[i] >= arr[i - 1] && arr[i] >= arr[i + 1] && (arr[i] != arr[i - 1] || arr[i] != arr[i + 1])) {
			peaks.push_back({ arr[i], i });
		}
	}

	if (peaks.size() < 2) {
		cout << "0";
		return 0;
	}

	sort(peaks.begin(), peaks.end(), cmp);
	long long int res = 0;
	int left_index = peaks[0].index;
	int right_index = peaks[0].index;

	//output
	vector <int> water_level(arr.size(), 0);

	for (int i = 1; i < peaks.size(); i++) {
		if (peaks[i].index > right_index) {
			for (int j = right_index + 1; j < peaks[i].index; j++) { 
				water_level[j] = max(peaks[i].peak - arr[j], 0);
				res += water_level[j];
			}
			right_index = peaks[i].index;
		}
		else if (peaks[i].index < left_index) {
			for (int j = left_index - 1; j > peaks[i].index; j--) {
				water_level[j] = max(peaks[i].peak - arr[j], 0);
				res += water_level[j];
			}
			left_index = peaks[i].index;
		}
	}

	//cout << res;
	print(arr, water_level, max_water_level, res); 
	fclose(stdin);
}