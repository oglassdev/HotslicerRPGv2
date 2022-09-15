package com.hotslicerrpg.rpg;

import java.io.IOException;

public interface Storage<T> {
    enum StorageType {
        LOCAL,MYSQL
    }
    StorageType getStorageType();
    void save(T object) throws IOException;
    T load(Class<T> clazz) throws IOException;
}