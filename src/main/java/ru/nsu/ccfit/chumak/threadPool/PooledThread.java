package ru.nsu.ccfit.chumak.threadPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PooledThread extends Thread {
    private final static Logger logger = LogManager.getLogger(PooledThread.class);

    private final TasksQueue tasksQueue;

    public PooledThread(TasksQueue tasksQueue) {
        this.tasksQueue = tasksQueue;
    }

    private void performTask(ThreadPoolTask threadPoolTask){
        if(threadPoolTask != null){
            threadPoolTask.prepare();
            threadPoolTask.execute();
            threadPoolTask.finish();
        } else {
            throw new NullPointerException();
        }
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            ThreadPoolTask nextTask = tasksQueue.peek();
            try {
                performTask(nextTask);
            } catch (NullPointerException e) {
                logger.warn(e);
            }
        }
    }




}
