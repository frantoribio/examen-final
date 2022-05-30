package es.joseluisgs.dam.examenmayo.controllers;


import es.joseluisgs.dam.examenmayo.HogwartsApplication;
import es.joseluisgs.dam.examenmayo.managers.DataBaseManager;
import es.joseluisgs.dam.examenmayo.models.HOUSES;
import es.joseluisgs.dam.examenmayo.models.Personaje;
import es.joseluisgs.dam.examenmayo.repositories.IPersonajeRepository;
import es.joseluisgs.dam.examenmayo.repositories.PersonajeRepository;
import es.joseluisgs.dam.examenmayo.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;

public class HogwartsController {

    @FXML
    private TextField nickField;
    //FXML
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField spellField;
    @FXML
    private TableView<Personaje> personajeTable;
    @FXML
    private TableColumn<Personaje, String> nombreCol;
    @FXML
    private TableColumn<Personaje, HOUSES> casaCol;
    @FXML
    private Label idLabel;
    @FXML
    private TextField idField;
    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<String> houseCB;
    @FXML
    private TextField heightField;
    @FXML
    private ImageView logoImg;

    //Specific
    private DataBaseManager db = DataBaseManager.getInstance();
    private IPersonajeRepository personajeRepository = new PersonajeRepository(db);
    private ICsvStorage csvStorage = new CsvStorage();
    private IJsonStorage jsonStorage = new JsonStorage();

    private TxtStorage txtStorage = new TxtStorage();
    private Personaje personajeSeleccionado;
    private ObservableList<Personaje> personajeList;

    @FXML
    private void initialize() {
        try {
            personajeList = personajeRepository.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
            personajeList = FXCollections.emptyObservableList();
        }

        initCells();

        personajeTable.setItems(personajeList);
    }

    private void initCells() {

        nombreCol.setCellValueFactory( cellData -> cellData.getValue().nameProperty() );
        houseCB.setItems(HOUSES.getSample());
        houseCB.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onHouseSelected(newValue)
        );
        personajeTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> onPersonajeSeleccionado(newValue)
        );
    }

    @FXML
    private void cambiarSeleccion() {
        personajeSeleccionado = personajeTable.getSelectionModel().getSelectedItem();
        idLabel.setText(String.valueOf(personajeSeleccionado.getId()));
        nickField.setText(personajeSeleccionado.getNick());
        nameField.setText(personajeSeleccionado.getName());
        spellField.setText(personajeSeleccionado.getSpell());
        houseCB.setValue(personajeSeleccionado.getHouse().getValue());
        datePicker.setValue(personajeSeleccionado.getDate());
    }

    private void onHouseSelected(String newValue) {
        changeImgTo(newValue);
    }

    private void changeImgTo(String newValue) {
        logoImg.setImage(new Image(HogwartsApplication.class.getResource("images/"+newValue+".png").toString()));
    }

    private void onPersonajeSeleccionado(Personaje per){
        personajeSeleccionado = per;
    }
    public void onImport() {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Archivo CSV");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = filechooser.showOpenDialog(idLabel.getScene().getWindow());

        if (file != null) {
            personajeList = FXCollections.observableList(csvStorage.load(Path.of(file.getAbsolutePath())));
            personajeTable.setItems(personajeList);
        }
    }

    public void onExport() {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Archivo JSON");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));
        File file = filechooser.showOpenDialog(idLabel.getScene().getWindow());

        if (file != null) {
            jsonStorage.save(file.toPath(), personajeList.stream().toList());
        }
    }

    public void onShowInforme() {
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Archivo TXT");
        filechooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));
        File file = filechooser.showOpenDialog(idLabel.getScene().getWindow());

        if (file != null) {
            txtStorage.save(file.toPath(), personajeList.stream().toList());
        }
    }

    public void onSalir(ActionEvent actionEvent) {

    }

    public void onSave(ActionEvent actionEvent) {
        if(validateFields()){
            Personaje p = new Personaje(
                    nameField.getText(),
                    nickField.getText(),
                    datePicker.getValue(),
                    HOUSES.parse(houseCB.getValue()),
                    Integer.parseInt(heightField.getText()),
                    spellField.getText()
            );
            try {
                p = personajeRepository.save(p).get();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            personajeList.add(
                    p
            );
        }
    }

    private boolean validateFields() {
        if(nameField.getText().isBlank()){
            new Alert(Alert.AlertType.ERROR, "No has especificado un nombre").show();
            return false;
        }
        if(nickField.getText().isBlank() || personajeList.stream().anyMatch(it -> Objects.equals(it.getNick(), nickField.getText()))){
            new Alert(Alert.AlertType.ERROR, "No has especificado un apodo o esta repetido").show();
            return false;
        }
        if(datePicker.getValue().isAfter(LocalDate.now()) || datePicker.getValue() == null){
            new Alert(Alert.AlertType.ERROR, "No has especificado una fecha").show();
            return false;
        }
        if(houseCB.getValue().isBlank()){
            new Alert(Alert.AlertType.ERROR, "No has especificado una casa").show();
            return false;
        }
        if(heightField.getText().isBlank() || !heightField.getText().matches("\\d+") || (Integer.parseInt(heightField.getText()) < 100 || Integer.parseInt(heightField.getText()) > 200)){
            new Alert(Alert.AlertType.ERROR, "No has especificado una edad valida").show();
            return false;
        }
        if(spellField.getText().isBlank()) {
            new Alert(Alert.AlertType.ERROR, "No has especificado un hechizo").show();
            return false;
        }
        return true;
    }

    public void onEdit(ActionEvent actionEvent) {
        if(validateFields()){
            personajeSeleccionado.setName(nameField.getText());
            personajeSeleccionado.setNick(nameField.getText());
            personajeSeleccionado.setDate(datePicker.getValue());
            personajeSeleccionado.setHeight(Integer.parseInt(heightField.getText()));
            personajeSeleccionado.setHouse(HOUSES.parse(houseCB.getValue()));
            personajeSeleccionado.setSpell(nameField.getText());
            try {
                personajeRepository.update(personajeSeleccionado);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public void onDelete() {
        personajeList.remove(personajeSeleccionado);
    }

    public void onClean() {
        nameField.setText("");
        nickField.setText("");
        datePicker.setValue(LocalDate.now());
        houseCB.setValue(null);
        heightField.setText("");
        spellField.setText("");
    }
}