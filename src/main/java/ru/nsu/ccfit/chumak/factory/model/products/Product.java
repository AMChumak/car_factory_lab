package ru.nsu.ccfit.chumak.factory.model.products;

public abstract class Product {
    private final String id;

    public Product(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
