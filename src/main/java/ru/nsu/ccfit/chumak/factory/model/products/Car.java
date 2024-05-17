package ru.nsu.ccfit.chumak.factory.model.products;

public class Car extends Product {
    private final Engine engine;
    private final Bodywork bodywork;
    private final Accessory accessory;

    public Car(String id, Engine engine, Bodywork bodywork, Accessory accessory) {
        super("car-"+id);
        this.engine = engine;
        this.bodywork = bodywork;
        this.accessory = accessory;
    }

    public Engine getEngine() {
        return engine;
    }

    public Bodywork getBodywork() {
        return bodywork;
    }

    public Accessory getAccessory() {
        return accessory;
    }
}
