package com.github.puzzle.paradox.core.event;
/*
 * CODE FROM FABRIC MODIFIED, ORIGINAL LICENSE:
 * the Apache License, Version 2.0
 *
 * Modified by repletsin5 and nanobass
 */
import java.lang.reflect.Array;
import java.util.Objects;
import java.util.function.Function;

public class ArrayBackedEvent<T> extends Event<T> {
    private final Function<T[], T> invokerFactory;
    private final Object lock = new Object();
    private T[] handlers;
    private final EventData<T> data;

    @SuppressWarnings("unchecked")
    ArrayBackedEvent(Class<? super T> type, Function<T[], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.handlers = (T[]) Array.newInstance(type, 0);
        data = new EventData<>(type);
        update();
    }

    void update() {
        this.invoker = invokerFactory.apply(handlers);
    }

    @Override
    public void subscribe(T listener) {
        Objects.requireNonNull(listener, "Tried to register a null listener!");

        synchronized (lock) {
            data.addListener(listener);
            data.listeners.toArray(handlers);
            update();
        }
    }

    @Override
    public void unsubscribe(T listener) {
        Objects.requireNonNull(listener, "Tried to unregister a null listener!");

        synchronized (lock) {
            data.addListener(listener);
            data.listeners.toArray(handlers);
            update();
        }
    }
}