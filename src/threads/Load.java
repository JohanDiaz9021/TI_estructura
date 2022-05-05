package threads;

import javafx.application.Platform;
import model.objects.Crud;
import ui.FXSplash;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Load extends Thread implements Serializable {

    private boolean loader;
    private final String SAVE_PATH_FILE = "data/persistent/Data.txt";
    private FXSplash fxSplash;
    private ObjectInputStream ois;
    private static final long serialVersionUID = 1;

    public Load(FXSplash splash) {
        this.fxSplash = splash;
    }

    @Override
    public void run() {
        try {
            Crud cr = read();
            Platform.runLater(new Thread(() -> fxSplash.setCr(cr)));
            ois.close();

        } catch (IOException | ClassNotFoundException e) {
            Crud cr = new Crud();
            Platform.runLater(new Thread(() -> fxSplash.setCr(cr)));
        }
    }

    public Crud read() throws IOException, ClassNotFoundException {
        ois = new ObjectInputStream(new FileInputStream(SAVE_PATH_FILE));
        return (Crud) ois.readObject();
    }

}
