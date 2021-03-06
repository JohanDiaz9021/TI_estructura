package ui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import model.objects.Crud;
import model.objects.Person;

public class FXController implements Initializable {

    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView iLogo = new ImageView();
    @FXML
    private ImageView iPlayer = new ImageView();
    @FXML
    private ImageView iTeam = new ImageView();
    @FXML
    private ImageView iStat = new ImageView();
    @FXML
    private JFXTextField txtAmount;
    @FXML
    private Pane pProgressBar;

    private final String SAVE_PATH_FILE = "data/persistent/Data.txt";
    @SuppressWarnings("FieldMayBeFinal")
    private Crud fb;
    private FXPlayer xPlayer;
    private FXListPlayers xListPlayer;

    public FXController(Crud fb) {
        this.fb = fb;
        xPlayer = new FXPlayer(this.fb, this);
        xListPlayer = new FXListPlayers(this.fb, this);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setImages();

    }

    public void setImages() {
        iLogo.setImage(new Image(new File("resources/img/logo/logo_small.png").toURI().toString()));
        iPlayer.setImage(new Image(new File("resources/img/others/person.png").toURI().toString()));

    }

    public void setFb(Crud fb) {
        this.fb = fb;
    }

    public void saveData() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_PATH_FILE));
        oos.writeObject(this.fb);
        oos.close();
    }

    public void newStage(Parent root) {
        Stage newStage = new Stage();
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Simulation");
        newStage.getIcons().add(new Image(new File("resources/img/logo/logo_small_icon_only.png").toURI().toString()));
        newStage.setResizable(false);
        newStage.show();
    }

    public void openListPlayers() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/ListPlayers.fxml"));
        fxmlLoader.setController(xListPlayer);
        Parent root = fxmlLoader.load();
        newStage(root);
    }

    public void showAlert(boolean success, String msg, StackPane stackPane) {
        JFXDialogLayout content = new JFXDialogLayout();
        JFXButton button = new JFXButton("Okay");
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(button);
        String header = (success) ? "??Listo!" : "??Error!";
        content.setHeading(new Text(header));
        content.setBody(new Text(msg));
        dialog.show();
    }

    @FXML
    public void onBPlayers(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fxml/Players.fxml"));
        fxmlLoader.setController(xPlayer);
        Parent root = fxmlLoader.load();
        newStage(root);
    }

    @FXML
    public void onBStats(ActionEvent event) {
        //???
    }

    @FXML
    public void onImport(ActionEvent event) throws IOException {
        try {
            int amount = Integer.parseInt(txtAmount.getText());
            pProgressBar.setVisible(true);
            boolean imported = fb.importData(fb, amount);
            String msg = (imported) ? "Yei" : "Oh no";
            pProgressBar.setVisible(false);
            showAlert(imported, msg, stackPane);
            txtAmount.setText("");
            saveData();
        }catch (NumberFormatException e){
            this.showAlert(false, "??Formato del numero incorrecto!", stackPane);

        }
    }


    public void refreshPlayer(Person p) {
        xPlayer.refreshPlayer(p);
    }
}
