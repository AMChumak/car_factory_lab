package ru.nsu.ccfit.chumak.factory.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.chumak.factory.model.producer_consumer.Warehouse;
import ru.nsu.ccfit.chumak.factory.model.products.Accessory;
import ru.nsu.ccfit.chumak.factory.model.products.Bodywork;
import ru.nsu.ccfit.chumak.factory.model.products.Car;
import ru.nsu.ccfit.chumak.factory.model.products.Engine;
import ru.nsu.ccfit.chumak.threadPool.Task;
import ru.nsu.ccfit.chumak.threadPool.TaskListener;
import ru.nsu.ccfit.chumak.threadPool.ThreadPool;

public class WarehouseController extends Thread implements TaskListener {
    private final static Logger logger = LogManager.getLogger(WarehouseController.class);

    private final Warehouse<Engine> enginesWarehouse;
    private final Warehouse<Bodywork> bodyworksWarehouse;
    private final Warehouse<Accessory> accessoriesWarehouse;
    private final Warehouse<Car> carWarehouse;
    private final ThreadPool assemblyShop;

    private Integer countStoppedTasks = 0;
    private Integer countGivenTasks = 0;
    private boolean startCond = true;

    public WarehouseController(Warehouse<Car> warehouse, ThreadPool assemblyShop, Warehouse<Engine> enginesWarehouse, Warehouse<Bodywork> bodyworksWarehouse, Warehouse<Accessory> accessoriesWarehouse) {
        this.carWarehouse = warehouse;
        this.assemblyShop = assemblyShop;
        this.enginesWarehouse = enginesWarehouse;
        this.bodyworksWarehouse = bodyworksWarehouse;
        this.accessoriesWarehouse = accessoriesWarehouse;
    }

    @Override
    public void run() {
        while (Thread.currentThread().isInterrupted()) {
            synchronized (carWarehouse) {
                while(carWarehouse.size() == carWarehouse.getMaxSize() && !startCond) {
                    try {
                        carWarehouse.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                startCond = false;
                int range;
                synchronized (countStoppedTasks) {
                    range = carWarehouse.getMaxSize() - carWarehouse.size() - (countGivenTasks - countStoppedTasks);
                }

                for(int i = 0; i < range ; ++i) {
                    Assembly assemblyTask = new Assembly(enginesWarehouse, bodyworksWarehouse, accessoriesWarehouse, carWarehouse, Integer.toString(countGivenTasks+1));
                    assemblyShop.addTask(assemblyTask, this);
                    countGivenTasks++;
                }
            }

        }
    }



    @Override
    public void taskStarted(Task task) {
        logger.trace("taskStarted" + task);
    }

    @Override
    public void taskFinished(Task task) {
        synchronized (countStoppedTasks) {
            countStoppedTasks++;
        }
        logger.trace("taskFinished" + task);
    }

    @Override
    public void taskInterrupted(Task task) {
        synchronized (countStoppedTasks) {
            countStoppedTasks++;
        }
        logger.trace("taskInterrupted" + task);
    }
}
