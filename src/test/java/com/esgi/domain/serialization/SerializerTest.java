package com.esgi.domain.serialization;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializerTest {

    private record SerializableTestClass(String name, Integer age, boolean isMarried) implements Serializable {
    }

    @TempDir
    private File tempFolder;

    @Test
    public void should_serialize() throws IOException, ClassNotFoundException {
        // Arrange
        String destFilePath = tempFolder.getAbsolutePath() + "/serialized_test.txt";
        var serializer = new Serializer<>(destFilePath, SerializableTestClass.class);

        var serializableObj = new SerializableTestClass("NAME", 28, true);

        // Act
        serializer.serialize(serializableObj);

        // Assert
        boolean fileExists = new File(destFilePath).exists();
        Assertions.assertThat(fileExists).isTrue();

        var deserializedObj = this.deserialize(destFilePath);
        Assertions.assertThat(deserializedObj).isEqualTo(serializableObj);
    }

    @Test
    public void should_deserialize() throws IOException {
        // Arrange
        String destFilePath = tempFolder.getAbsolutePath() + "/serialized_test.txt";
        var serializer = new Serializer<>(destFilePath, SerializableTestClass.class);

        var expectedObject = new SerializableTestClass("NAME", 28, false);
        this.serialize(expectedObject, destFilePath);

        // Act
        var deserializedObject = serializer.deserialize();

        // Assert
        Assertions.assertThat(deserializedObject)
                .isNotNull()
                .isEqualTo(expectedObject);
    }

    @Test
    public void should_serialize_and_deserialize() throws IOException {
        ///  TI to test the full process

        // Arrange
        String destFilePath = tempFolder.getAbsolutePath() + "/serialized_test.txt";
        var serializer = new Serializer<>(destFilePath, SerializableTestClass.class);
        var originalObject = new SerializableTestClass("NAME", 28, false);

        // Act
        serializer.serialize(originalObject);
        var deserializedObject = serializer.deserialize();

        // Assert
        Assertions.assertThat(deserializedObject)
                .isNotNull()
                .isEqualTo(originalObject);
    }

    private void serialize(SerializableTestClass obj, String destFilePath) {
        try(var fileOutputStream = new FileOutputStream(destFilePath);
            var objectOutputStream = new ObjectOutputStream(fileOutputStream)
        ) {
            objectOutputStream.writeObject(obj);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SerializableTestClass deserialize(String filePath) throws IOException, ClassNotFoundException {
        try (var fileInputStream = new FileInputStream(filePath);
             var objectInputStream = new ObjectInputStream(fileInputStream)) {
            return (SerializableTestClass) objectInputStream.readObject();
        }
    }
}
