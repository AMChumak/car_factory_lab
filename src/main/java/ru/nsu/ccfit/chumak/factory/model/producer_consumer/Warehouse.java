package ru.nsu.ccfit.chumak.factory.model.producer_consumer;

import java.util.LinkedList;

public class Warehouse<T> {
    private final LinkedList<T> items = new LinkedList<>();
    private final int maxSize;

    public Warehouse(int maxSize) {
        this.maxSize = maxSize;
    }

    public void push(T item) {
        synchronized (items) {
            while(items.size() == maxSize) {
                try{
                    if(Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    items.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            items.addLast(item);
            items.notifyAll();
            this.notifyAll();
        }
    }

    public T peek() {
        synchronized (items) {
            while (items.isEmpty()) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        return null;
                    }
                    items.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            T item = items.getFirst();
            items.notifyAll();
            this.notifyAll();
            return item;
        }
    }

    public int size() {  // Вроде мы просто считываем данные, а потому не особо принципиально если значение будет отличаться на +-1
        synchronized (items) {
            return items.size(); // нет, всё таки надо, так как этот метод используется контроллером склада, а ему надо рассчитывать заказы
        }
    }

    public int getMaxSize() {
        return maxSize;
    }
}
