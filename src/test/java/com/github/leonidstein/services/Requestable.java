package com.github.leonidstein.services;

@FunctionalInterface
public interface Requestable<T extends Requestable<T>> {

    T makeRequest();
}
