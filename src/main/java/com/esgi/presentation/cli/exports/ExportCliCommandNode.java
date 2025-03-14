package com.esgi.presentation.cli.exports;

import com.esgi.domain.Entity;
import com.esgi.presentation.cli.CliCommandNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class ExportCliCommandNode<T extends Entity> extends CliCommandNode {
    public static final String NAME = "export";

    private final Gson gson;

    public ExportCliCommandNode(String description) {
        super(NAME, description);

        this.gson = new GsonBuilder().create();
    }

    protected void export(List<T> models, String pathToFile) throws IOException {
        Files.createDirectories(Paths.get(pathToFile).getParent());

        try (FileWriter fileWriter = new FileWriter(pathToFile);
             JsonWriter jsonWriter = new JsonWriter(fileWriter)) {

            jsonWriter.beginArray();
            models.forEach(model -> gson.toJson(model, model.getClass(), jsonWriter));
            jsonWriter.endArray();
        }
    }
}
