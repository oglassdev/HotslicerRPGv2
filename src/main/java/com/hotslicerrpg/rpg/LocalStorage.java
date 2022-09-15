package com.hotslicerrpg.rpg;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LocalStorage<T> implements Storage<T> {
    private final File file;
    public LocalStorage(String pathToFile) {
        this.file = new File(pathToFile);
        this.file.getParentFile().mkdirs();
    }
    public LocalStorage(File file) {
        this.file = file;
        this.file.getParentFile().mkdirs();
    }
    @Override
    public StorageType getStorageType() {
        return StorageType.LOCAL;
    }
    @Override
    public void save(T object) throws IOException {
        FileWriter writer = new FileWriter(file);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        gson.toJson(object,writer);
        writer.close();
    }

    @Override
    public T load(Class<T> clazz) throws IOException {
        if (!file.exists()) return null;
        FileReader reader = new FileReader(file);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        T o = gson.fromJson(reader, clazz);
        reader.close();
        return o;
    }
}
