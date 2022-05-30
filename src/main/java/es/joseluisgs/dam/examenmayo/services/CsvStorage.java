package es.joseluisgs.dam.examenmayo.services;

import es.joseluisgs.dam.examenmayo.models.HOUSES;
import es.joseluisgs.dam.examenmayo.models.Personaje;
import es.joseluisgs.dam.examenmayo.utils.Properties;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static java.util.Collections.emptyList;

public class CsvStorage implements ICsvStorage{

    @Override
    public boolean save(Path path, List<Personaje> item) {
        var header =
                "id,nombre,apodo,fNacimiento,casa,altura,hechizo".replace(',', Properties.CSV_SEPARATOR);
        var csv = new StringBuilder(header);
        var csvList = item.stream().map(this::toCSV).toList();
        for (String s : csvList) {
            csv.append("\n");
            csv.append(s);
        }
        try (var fw = new FileWriter(path.toFile())) {
            fw.write(csv.toString());
            return true;
        } catch (Exception e) {
            System.out.println("Error writing the file");
        }
        return false;
    }

    @Override
    public List<Personaje> load(Path Path) {
        try {
            return Files.lines(Path).skip(1).map(this::parse).toList();
        } catch (IOException e) {
            System.out.println("Error reading the file");
        }
        return emptyList();
    }

    private Personaje parse(String line) {
        var fields = line.split(String.valueOf(Properties.CSV_SEPARATOR));
        var id = Integer.parseInt(fields[1]);
        var name = fields[2];
        var nick = fields[3];
        var date = LocalDate.parse(fields[4]);
        var house = HOUSES.valueOf(fields[5]);
        var height = Integer.parseInt(fields[7]);
        var spell = fields[0];
        return new Personaje(id, name, nick, date, house, height, spell);
    }

    private String toCSV(Personaje dto) {
        return dto.getId() + Properties.CSV_SEPARATOR +
                dto.getName() + Properties.CSV_SEPARATOR +
                dto.getNick() + Properties.CSV_SEPARATOR +
                dto.getDate() + Properties.CSV_SEPARATOR +
                dto.getHouse() + Properties.CSV_SEPARATOR +
                dto.getHeight() + Properties.CSV_SEPARATOR +
                dto.getSpell() + Properties.CSV_SEPARATOR;
    }
}
