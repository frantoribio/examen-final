package es.joseluisgs.dam.examenmayo.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import es.joseluisgs.dam.examenmayo.models.Personaje;
import es.joseluisgs.dam.examenmayo.utils.LocalDateAdapter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;

public class JsonStorage implements IJsonStorage{

    @Override
    public boolean save(Path path, List<Personaje> item) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();
        String json = gson.toJson(item);
        try {
            Files.writeString(path, json);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Personaje> load(Path path) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();
        String json;
        try {
            json = Files.readString(path);
            return gson.fromJson(json, new TypeToken<List<Personaje>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return emptyList();
    }
}
