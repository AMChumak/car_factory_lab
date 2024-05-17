package ru.nsu.ccfit.chumak.factory.model.producer_consumer;

public class Consumer<T>  extends Thread {
    private final Warehouse<T> warehouse;
    private Long cooldown;
    private final UseProduct<T> useProduct;
    private Integer countRecieved = 0;

    public Consumer(Warehouse<T> warehouse, Long cooldown, UseProduct<T> useProduct) {
        this.warehouse = warehouse;
        this.cooldown = cooldown;
        this.useProduct = useProduct;

    }

    public synchronized void setCooldown(Long cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            T item = warehouse.peek();
            if (item != null) {
                synchronized (countRecieved) {
                    useProduct.useProduct(item, countRecieved);
                    countRecieved++;
                }
            }

            try{
                Thread.sleep(cooldown);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public Integer getCountRecieved() {
        synchronized (countRecieved) {
            return countRecieved;
        }
    }
}
