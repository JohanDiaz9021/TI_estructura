package ui;

import com.jfoenix.controls.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.objects.Crud;
import model.objects.Person;

public class FXPlayer implements Initializable {

    private Crud cr;
    private FXController xGUI;
    private String imagePath;
    @FXML
    private StackPane stackPane;

    @FXML
    private Pane pane;

    @FXML
    private ImageView iSave;

    @FXML
    private ImageView iRemove;

    @FXML
    private ImageView iClear;

    @FXML
    private ImageView iUpdate;

    @FXML
    private JFXTextField txtHeight;

    @FXML
    private JFXRadioButton rbMen;

    @FXML
    private ToggleGroup tgActive;

    @FXML
    private JFXRadioButton rbWomen;

    @FXML
    private ImageView iSearch;

    @FXML
    private ImageView iPhoto;

    @FXML
    private JFXTextField txtName;

    @FXML
    private JFXTextField txtNationality;

    @FXML
    private JFXTextField txtLastName;

    @FXML
    private JFXDatePicker dateBirthday;

    private Person personSelected;

    public FXPlayer(Crud cr, FXController xGUI) {
        this.cr = cr;
        this.xGUI = xGUI;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setImages();
        setTxtProperties();
    }

    public void setTxtProperties() {
        txtName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        txtNationality.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtNationality.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });
        txtLastName.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\sa-zA-Z*")) {
                txtLastName.setText(newValue.replaceAll("[^\\sa-zA-Z]", ""));
            }
        });


    }

    public void setImages() {
        iSave.setImage(new Image(new File("resources/img/others/save-disk.png").toURI().toString()));
        iRemove.setImage(new Image(new File("resources/img/others/remove-report.png").toURI().toString()));
        iSearch.setImage(new Image(new File("resources/img/others/search.png").toURI().toString()));
        iClear.setImage(new Image(new File("resources/img/others/clear.png").toURI().toString()));
        iUpdate.setImage(new Image(new File("resources/img/others/update-file.png").toURI().toString()));




    }


    @FXML
    public void onSearchPlayer() throws IOException {
        xGUI.openListPlayers();
    }

    @FXML
    public void onDelete(ActionEvent event) throws IOException {
        if (personSelected != null) {
            cr.removePerson(personSelected);
            xGUI.saveData();
            xGUI.showAlert(true, "¡Eliminado Correctamente!", stackPane);
            clearGui();
            personSelected = null;
        } else {
            xGUI.showAlert(false, "¡No ha seleccionado un jugador!", stackPane);
        }
    }

    @FXML
    public void onSave(ActionEvent event) throws IOException {
        DecimalFormat df = new DecimalFormat("#.00");

        if ((!txtName.getText().equals("")) && (!txtLastName.getText().equals("")) && (!txtHeight.getText().equals(""))
                && (!txtNationality.getText().equals("")) && (tgActive.getSelectedToggle() != null) && (dateBirthday.getValue() != null)) {
            try{
                String sex;
                int code = cr.getTotalPeople() + 1;
                String name = txtName.getText();
                String lastName = txtLastName.getText();
                JFXRadioButton selected = (JFXRadioButton) tgActive.getSelectedToggle();
                if (selected.getText().equals("Men")) {
                    sex = "Men";

                }  else {
                    sex = "Women";
                }


                String birthday = getDate(dateBirthday.getValue());
                String height = df.format(Double.parseDouble(txtHeight.getText())) ;
                String nationality = txtNationality.getText();

                cr.addPerson(new Person(code, name, lastName, sex, birthday, height, nationality));
                clearGui();
                xGUI.saveData();
                xGUI.showAlert(true, "¡Person added!", stackPane);
            }catch (NumberFormatException e){
                xGUI.showAlert(false, "¡El indicador de decimal es un . y no una ,!", stackPane);
            }

        } else {
            xGUI.showAlert(false, "¡Can't add the player!", stackPane);
        }
    }

    private String getDate(LocalDate date){

        String day = "";
        String mount = "";

        if (date.getDayOfMonth() < 10) {
            day = "0" + date.getDayOfMonth();
        }

        if (date.getMonthValue() < 10) {
            mount = "0" + date.getMonthValue();
        }

        return day + "/" + mount + "/" + date.getYear();
    }

    @FXML
    public void onUpdate(ActionEvent event) throws IOException {
        DecimalFormat df = new DecimalFormat("#.00");

        if ((!txtName.getText().equals("")) && (!txtLastName.getText().equals("")) && (!txtHeight.getText().equals(""))
                && (!txtNationality.getText().equals("")) && (tgActive.getSelectedToggle() != null) && (dateBirthday.getValue() != null)) {
            personSelected.setName(txtName.getText());
            personSelected.setLastName(txtLastName.getText());
            personSelected.setSex(((JFXRadioButton) tgActive.getSelectedToggle()).getText());
            personSelected.setBirthday(getDate(dateBirthday.getValue()));
            personSelected.setHeight(df.format(Double.parseDouble(txtHeight.getText())));
            personSelected.setNationality(txtNationality.getText());
            xGUI.saveData();
            xGUI.showAlert(true, "¡Person updated!", stackPane);
            clearGui();
            personSelected = null;
        }


    }

    public void refreshPlayer(Person p){
        personSelected = p;
        txtName.setText(personSelected.getName());
        txtLastName.setText(personSelected.getLastName());
        String height = personSelected.getHeight();
        txtHeight.setText(height.charAt(0) + "." + height.substring(2, 4));
        txtNationality.setText(personSelected.getNationality());
        String day = personSelected.getBirthday().substring(0, 2);
        String mount = personSelected.getBirthday().substring(3, 5);
        String year = personSelected.getBirthday().substring(6, 10);
        dateBirthday.setValue(LocalDate.parse(year + "-" + mount + "-" + day));


        if (personSelected.getSex().equals("Men")) {
            tgActive.selectToggle(rbMen);
        } else {
            tgActive.selectToggle(rbWomen);
        }

    }


    public void clearGui() {
        txtName.setText("");
        txtLastName.setText("");
        txtHeight.setText("");
        txtNationality.setText("");
        dateBirthday.setValue(null);
        tgActive.selectToggle(null);
        iPhoto.setImage(null);
        imagePath = "";
    }

    @FXML
    public void onClear() {
        clearGui();
        personSelected = null;
    }
}
