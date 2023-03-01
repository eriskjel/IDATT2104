#include <iostream>
#include "Worker.cpp"
int main() {

    Worker worker_threads(4);
    Worker event_loop(1);

    worker_threads.start(); // Create 4 internal threads
    event_loop.start();     // Create 1 internal thread

    worker_threads.post_timeout([]{
        std::cout << "Task A\n";
        // Task A
    },1000);
    worker_threads.post([]{
        std::cout << "Task B\n";
        // Task B
        // Might run in parallel with task A
    });

    event_loop.post([]{
        std::cout << "Task C\n";
        // Task C
        // Might run in parallel with task A and B
    });

    event_loop.post_timeout([] {
        std::cout << "Task D\n";
        // Task D
        // Will run after task C
        // Might run in parallel with task A and B
    },100000);


    worker_threads.join(); // Calls join() on the worker threads
    event_loop.join();     // Calls join() on the event thread


    return 0;
}