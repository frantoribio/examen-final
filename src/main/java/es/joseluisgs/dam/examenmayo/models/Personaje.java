package es.joseluisgs.dam.examenmayo.models;

import javafx.beans.property.*;

import java.time.LocalDate;

public class Personaje {

    private int id;
    private final StringProperty name;
    private final StringProperty nick;
    private final ObjectProperty<LocalDate> date;
    private final ObjectProperty<HOUSES> house;
    private final IntegerProperty height;
    private final StringProperty spell;

    public Personaje(int id, String name, String nick, LocalDate date, HOUSES house, int height, String spell) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.nick = new SimpleStringProperty(nick);
        this.date = new SimpleObjectProperty<>(date);
        this.house =  new SimpleObjectProperty<>(house);
        this.height = new SimpleIntegerProperty(height);
        this.spell = new SimpleStringProperty(spell);
    }

    public Personaje(String name, String nick, LocalDate date, HOUSES house, int height, String spell) {
        this.name = new SimpleStringProperty(name);
        this.nick = new SimpleStringProperty(nick);
        this.date = new SimpleObjectProperty<>(date);
        this.house =  new SimpleObjectProperty<>(house);
        this.height = new SimpleIntegerProperty(height);
        this.spell = new SimpleStringProperty(spell);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getNick() {
        return nick.get();
    }

    public StringProperty nickProperty() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick.set(nick);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public HOUSES getHouse() {
        return house.get();
    }

    public ObjectProperty<HOUSES> houseProperty() {
        return house;
    }

    public void setHouse(HOUSES house) {
        this.house.set(house);
    }

    public int getHeight() {
        return height.get();
    }

    public IntegerProperty heightProperty() {
        return height;
    }

    public void setHeight(int height) {
        this.height.set(height);
    }

    public String getSpell() {
        return spell.get();
    }

    public StringProperty spellProperty() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell.set(spell);
    }
}
