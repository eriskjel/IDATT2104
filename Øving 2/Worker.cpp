
#ifndef EXERCISE_2_WORKER_H
#define EXERCISE_2_WORKER_H
#include <functional>
#include <iostream>
#include <list>
#include <vector>
#include <thread>
#include <mutex>
#include <condition_variable>
#include "Task.cpp"





using namespace std;


class Worker{

    int num_threads;
    list<Task> task_queue;
    vector<thread> thread_pool;
    condition_variable cv;
    mutex task_mutex;
    bool running = true;

public:
    explicit Worker(int num_threads){
        this->num_threads = num_threads;
    };

    void post(const function<void()>& task){
        post_timeout(task, 0);
    }
    void post_timeout(const function<void()>& task, int timeout){
        // Thread-safe
        std::unique_lock<std::mutex> lock(this->task_mutex);
        this->task_queue.emplace_back(task, timeout);
        this->cv.notify_one();
    }
    void start(){
        for(int i = 0; i < this->num_threads; i++) {
            this->thread_pool.emplace_back([&] {
                while(true) {
                    Task task{};
                    {
                        std::unique_lock<std::mutex> lock(this->task_mutex);
                        while(this->task_queue.empty()) {
                            if(!running) return;
                            this->cv.wait(lock);
                        }
                        task = *task_queue.begin();
                        task_queue.pop_front();
                    }
                    this->cv.notify_one();
                    if (task.task) {
                        task.perform_task();
                    }
                }
            });
        }
    }
    void stop(){
        std::unique_lock<std::mutex> lock(this->task_mutex);
        while(!this->task_queue.empty()) {
            this->cv.wait(lock);
        }
        this->running = false;
        cv.notify_all();
    }
    void join(){
        std::thread t([&] {
            this->stop();
            for(auto& thread: this->thread_pool) {
                thread.join();
            }
        });
        t.join();
    }
};
#endif //EXERCISE_2_WORKER_H