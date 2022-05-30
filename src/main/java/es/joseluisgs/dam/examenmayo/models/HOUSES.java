package es.joseluisgs.dam.examenmayo.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Arrays;
import java.util.Locale;

public enum HOUSES {

    GRYFFINDOR("Gryffindor"),
    HUFFLEPUFF("hufflepuff"),
    RAVENCLAW("ravenclaw"),
    SLYTHERIN("slytherin");

    private final String value;
    HOUSES(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ObservableList<String> getSample(){
        return FXCollections.observableList(Arrays.stream(HOUSES.values()).map(HOUSES::getValue).toList());
    }

    public static HOUSES parse(String s){
        if(s.toLowerCase(Locale.ROOT) == GRYFFINDOR.getValue().toLowerCase(Locale.ROOT)) return GRYFFINDOR;
        if(s.toLowerCase(Locale.ROOT) == SLYTHERIN.getValue().toLowerCase(Locale.ROOT)) return SLYTHERIN;
        if(s.toLowerCase(Locale.ROOT) == HUFFLEPUFF.getValue().toLowerCase(Locale.ROOT)) return HUFFLEPUFF;
        if(s.toLowerCase(Locale.ROOT) == RAVENCLAW.getValue().toLowerCase(Locale.ROOT)) return RAVENCLAW;
        return null;
    }
}
