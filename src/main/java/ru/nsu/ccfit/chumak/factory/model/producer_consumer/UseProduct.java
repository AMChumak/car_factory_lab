package ru.nsu.ccfit.chumak.factory.model.producer_consumer;

@FunctionalInterface
public interface UseProduct<T> {
    void useProduct(T product, Integer countRecieved);
}
