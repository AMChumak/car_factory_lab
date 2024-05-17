package ru.nsu.ccfit.chumak.factory.model.producer_consumer;

@FunctionalInterface
public interface Produce<T> {
    T produce(Integer countProduced);
}
