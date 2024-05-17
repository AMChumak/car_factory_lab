package ru.nsu.ccfit.chumak.threadPool;

public class ThreadPoolTask {
    private final TaskListener listener;
    private final Task task;

    public ThreadPoolTask(Task task, TaskListener listener) {
        if (task == null) {
            throw new IllegalArgumentException("task is null");
        }
        this.task = task;
        this.listener = listener;
    }

    public void prepare() {
        if(listener != null) {
            listener.taskStarted(task);
        }
    }

    public void execute() {
        task.performWork();
    }

    public void interrupted() {
        if(listener != null) {
            listener.taskInterrupted(task);
        }
    }

    public void finish() {
        if(listener != null) {
            listener.taskFinished(task);
        }
    }

    public String getName(){
        return task.getName();
    }
}
