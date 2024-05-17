package ru.nsu.ccfit.chumak.factory.model.producer_consumer;

public class Producer<T> extends Thread {
    private final Warehouse<T> warehouse;
    private Long cooldown;
    private final Produce<T> produce;
    private Integer countProduced = 0;

    public Producer(Warehouse<T> warehouse, long cooldown, Produce<T> produce) {
        this.warehouse = warehouse;
        this.cooldown = cooldown;
        this.produce = produce;
    }

    public synchronized void setCooldown(long cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (countProduced) {
                T item = produce.produce(countProduced);
                warehouse.push(item);
                countProduced++;
            }
            try{
                Thread.sleep(cooldown);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

    }

    public Integer getCountProduced() {
        synchronized (countProduced) {
            return countProduced;
        }
    }
}
