package com.esgi.domain.serialization;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class Serializer<T extends Serializable> {
    @Getter
    private final String destFilePath;

    private final Class<T> type;

    public Serializer(String destFilePath, Class<T> type) {
        this.destFilePath = destFilePath;
        this.type = type;
    }

    public void serialize(T obj) throws IOException {
        try(var fileOutputStream = new FileOutputStream(destFilePath);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(obj);
        }
    }

    public T deserialize() throws IOException {
        try(var fileInputStream = new FileInputStream(destFilePath);
            var objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            Object obj = objectInputStream.readObject();
            return type.cast(obj);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
