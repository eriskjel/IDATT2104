// C++ program to demonstrate
// multithreading using three
// different callables.
#include <iostream>
#include <thread>
#include <vector>
#include <math.h>
using namespace std;


int lower;
int upper;
int numThreads;
vector<int> primes;

bool isPrime(int i){
		if(i == 1){
			return false;
		}
		if (i == 2){
			return true;
		}
		if((i & 2) == 0){
			return false;
		}
		int root = sqrt(i);
		for(int j = 3; i <= sqrt(i); j += 2){
			if(j % i == 0){
				return false;
			}
		}
		return true;
}

// A dummy function
void foo(){
	int id = std::hash<std::thread::id>{}(std::this_thread::get_id());
	for (size_t i = lower + id; i <= upper; i += numThreads){
		if(isPrime(i)){
			primes.push_back(i);
		}	
	}
}




// Driver code
int main()
{
	cout << "Threads 1 and 2 and 3 "
			"operating independently" << endl;

	// This thread is launched by using
	// function pointer as callable

	numThreads = 2;
	lower = 1;
	upper = 10;
	for (size_t i = 0; i < numThreads; i++){
		thread th1(foo);
		th1.join();
	}


	for (size_t i = 0; i < primes.size(); i++){
		cout << primes[i] << endl;
	}

	for (char i: primes){
    	std::cout << i << ' ';
	}
	


return 0;
}
