package ru.nsu.ccfit.chumak.threadPool;

import java.util.LinkedList;

public class TasksQueue {
    private final LinkedList<ThreadPoolTask> tasks = new LinkedList<>();

    public void poll(ThreadPoolTask task){
        synchronized (tasks) {
            tasks.addLast(task);
            tasks.notifyAll();
        }
    }

    public ThreadPoolTask peek(){
        synchronized (tasks) {
            while (tasks.isEmpty()) {
                try{
                    if(Thread.currentThread().isInterrupted()) {
                        return null;
                    }
                    tasks.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            ThreadPoolTask task = tasks.removeFirst();
            tasks.notifyAll();
            return task;
        }
    }


}
