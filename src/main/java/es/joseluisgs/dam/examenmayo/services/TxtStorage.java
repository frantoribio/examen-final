package es.joseluisgs.dam.examenmayo.services;

import es.joseluisgs.dam.examenmayo.models.Personaje;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.Year;
import java.time.chrono.ChronoLocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class TxtStorage implements ITxtStorage {

    @Override
    public boolean save(Path path, List<Personaje> item) {
        String toWrite = getString(item);
        try(var fw = new FileWriter(path.toFile())){
            fw.write(toWrite);
            return true;
        } catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Personaje> load(Path Path) {
        return null;
    }

    private String getString(List<Personaje> item){
        StringBuilder s = new StringBuilder();
        s.append("Listado:").append("\n");
        item.stream().sorted(Comparator.comparing(Personaje::getNick)).forEach(it -> s.append(it).append("\n"));
        s.append("Personaje mas alto:").append(item.stream().max(Comparator.comparing(Personaje::getHeight))).append("\n");
        var stats = item.stream().mapToDouble(Personaje::getHeight);
        s.append("Altura Máxima:").append(stats.max()).append("\n");
        s.append("Altura Minima:").append(stats.min()).append("\n");
        s.append("Altura Media:").append(stats.average()).append("\n");
        s.append("Listado por casa:").append("\n");
        item.stream().sorted(Comparator.comparing(Personaje::getHouse)).forEach(it -> s.append(it).append("\n"));
        s.append("Personajes con Patronus:").append("\n");
        item.stream().filter(it -> Objects.equals(it.getSpell(), "Patronus")).forEach(it -> s.append(it).append("\n"));
        s.append("Personajes despues de 1980:").append("\n");
        item.stream().filter(it -> LocalDate.EPOCH.isAfter(ChronoLocalDate.from(Year.of(1980)))).forEach(it -> s.append(it).append("\n"));
        return s.toString();
    }
}
