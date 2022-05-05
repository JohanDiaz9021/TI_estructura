package ui;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.objects.Crud;
import model.objects.Person;

public class FXListPlayers implements Initializable {

    private Crud cr;
    private FXController xGUI;
    @FXML
    private StackPane stackPane;
    @FXML
    private JFXComboBox cbSearch;
    @FXML
    private JFXTextField txtSearch;
    @FXML
    private TableView<Person> tblPlayer;
    @FXML
    private TableColumn<Person, String> tcFullName;
    @FXML
    private TableColumn<Person, String> tcSex;
    @FXML
    private TableColumn<Person, Integer> tcCode;
    @FXML
    private TableColumn<Person, LocalDate> tcbirthday;
    @FXML
    private TableColumn<Person, Double> tcHeight;
    @FXML
    private TableColumn<Person, String> tcNationality;
    
    private Person playerSelected;
    
    public FXListPlayers(Crud cr, FXController xGUI) {
        this.cr = cr;
        this.xGUI = xGUI;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCBSearch();
        onTableSearch(cr.getRBTPersonName().searchApproximate(""));
        txtSearch.setDisable(true);
    }

    public void setCBSearch() {
        List<String> types = new ArrayList<>();
        types.add("Code");
        types.add("Name");
        types.add("First Name");
        types.add("Last Name");
        ObservableList<String> list = FXCollections.observableArrayList(types);
        cbSearch.setItems(list);
    }

    @FXML
    public void select(ActionEvent event) {
        if (cbSearch.getValue().equals("Name") || cbSearch.getValue().equals("Code") || cbSearch.getValue().equals("First Name") || cbSearch.getValue().equals("Last Name")) {
            txtSearch.setText("");
            txtSearch.setDisable(false);
        } else {
            txtSearch.setDisable(true);
        }
    }

    @FXML
    public void onSelectPlayer(MouseEvent event) {
        if (event.getClickCount() == 2) {
            playerSelected = tblPlayer.getSelectionModel().getSelectedItem();
            if (playerSelected != null) {
                xGUI.refreshPlayer(playerSelected);
                xGUI.showAlert(true, "Se ha seleccionado correctamente el jugador", stackPane);
            }
        }
    }

    @FXML
    public void onSearch(KeyEvent event) {
        ArrayList<Person> s = new ArrayList<>();
        
        if (cbSearch.getValue().equals("Code")) {
            s = cr.getAVLPersonCode().searchApproximate(txtSearch.getText());
        } else if (cbSearch.getValue().equals("Name")) {
            s = cr.getRBTPersonFullName().searchApproximate(txtSearch.getText());
        } else if (cbSearch.getValue().equals("First Name")) {
            s = cr.getRBTPersonName().searchApproximate(txtSearch.getText());
        } else if (cbSearch.getValue().equals("Last Name")) {
            s = cr.getBSTPersonLastName().searchApproximate(txtSearch.getText());
        }
        onTableSearch(s);
    }

    public void onTableSearch(ArrayList<Person> arr) {
        ObservableList<Person> tableTeam = FXCollections.observableArrayList(arr);

        tblPlayer.setItems(tableTeam);
        tcCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        tcFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        tcSex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        tcbirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        tcHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        tcNationality.setCellValueFactory(new PropertyValueFactory<>("nationality"));

        tblPlayer.refresh();
    }
}
