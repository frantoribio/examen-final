module es.joseluisgs.dam.examenmayo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.sql;
    requires lombok;


    opens es.joseluisgs.dam.examenmayo to javafx.fxml;
    exports es.joseluisgs.dam.examenmayo;
    exports es.joseluisgs.dam.examenmayo.controllers;
    exports es.joseluisgs.dam.examenmayo.models;
    opens es.joseluisgs.dam.examenmayo.controllers to javafx.fxml;

    // Para que exporte bien el JSON, descomenta esto o añade las clases a la lista de exports
    // que vayas a necesitar
    // opens es.joseluisgs.dam.examenmayo.dto to com.google.gson;
    // opens es.joseluisgs.dam.examenmayo.models to com.google.gson;
}