package com.esgi.presentation.cli.imports;

import com.esgi.domain.Entity;
import com.esgi.presentation.cli.CliCommandNode;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public abstract class ImportCliCommandNode <T extends Entity> extends CliCommandNode {
    public static final String NAME = "import";
    private final Gson gson;

    public ImportCliCommandNode(String description) {
        super(NAME, description);
        this.gson = new Gson();
    }

    protected <T> List<T> importData(List<T> model, String path,Type typeOfT) throws IOException {
        try {
            String json = Files.readString(Path.of(path));
            List<T> data = gson.fromJson(json,typeOfT);
            model.addAll(data);
            return model;
        } catch (com.google.gson.JsonSyntaxException e) {
            throw new IOException("Invalid JSON format", e);
        }
    }
}
