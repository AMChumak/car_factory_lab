package ru.nsu.ccfit.chumak.threadPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class ThreadPool {
    private final static Logger logger = LogManager.getLogger(ThreadPool.class);
    

    private Integer countThreads;
    private final ArrayList<PooledThread> threads;
    private final TasksQueue queue;

    public ThreadPool(int countThreads) {
        queue = new TasksQueue();
        this.countThreads = countThreads;
        threads = new ArrayList<>();
        for (int i = 0; i < countThreads; i++) {
            PooledThread newThread = new PooledThread(queue);
            threads.add(newThread);
            newThread.start();
        }
    }

    public void addTask(Task task, TaskListener listener) {
        queue.poll(new ThreadPoolTask(task,listener));
    }

    public void  addTask(Task task) {
        queue.poll(new ThreadPoolTask(task, null));
    }

    public void interruptAll() {
        for (PooledThread thread : threads) {
            thread.interrupt();
        }
    }

    public void joinAll(){
        for (PooledThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                logger.error(e);
            }
        }
    }
}
