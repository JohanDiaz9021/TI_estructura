package model.objects;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import javafx.stage.FileChooser;
import model.data_structures.AVL;
import model.data_structures.BST;
import model.data_structures.RBT;
import threads.FileUpload;

public class Crud implements Serializable {

    int totalPeople = 0;
    private RBT<String, Person> RBTPersonName;
    private AVL<Integer, Person> AVLPersonCode;
    private BST<String, Person> BSTPersonLastName;
    private RBT<String, Person> RBTPersonFullName;
    private static final long serialVersionUID = 1;

    public Crud() {
        RBTPersonName = new RBT<>();
        RBTPersonFullName = new RBT<>();
        BSTPersonLastName = new BST<>();
        AVLPersonCode = new AVL<>();
    }

    public File fileChooser() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Open File Client");
        return fc.showOpenDialog(null);
    }

    public boolean importData(Crud fb, int amount) {
        try {
            String names = fileChooser().getAbsolutePath();
            String lastNames = fileChooser().getAbsolutePath();
            BufferedReader br1 = new BufferedReader(new FileReader(names));
            BufferedReader br2 = new BufferedReader(new FileReader(lastNames));
            FileUpload fup = new FileUpload(br1, br2, fb, amount);
            fup.start();
            int num = 0;
            while (fup.isAlive()) {
                num++;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTotalPeople() {
        return totalPeople;
    }

    public void setTotalPeople(int totalPeople) {
        this.totalPeople = totalPeople;
    }

    public RBT<String, Person> getRBTPersonName() {
        return RBTPersonName;
    }

    public void setRBTPersonName(RBT<String, Person> BSTPersonName) {
        this.RBTPersonName = BSTPersonName;
    }

    public AVL<Integer, Person> getAVLPersonCode() {
        return AVLPersonCode;
    }

    public void setAVLPersonCode(AVL<Integer, Person> RBTPersonCode) {
        this.AVLPersonCode = RBTPersonCode;
    }

    public BST<String, Person> getBSTPersonLastName() {
        return BSTPersonLastName;
    }

    public void setBSTPersonLastName(BST<String, Person> BSTPersonLastName) {
        this.BSTPersonLastName = BSTPersonLastName;
    }

    public RBT<String, Person> getRBTPersonFullName() {
        return RBTPersonFullName;
    }

    public void setRBTPersonFullName(RBT<String, Person> AVLPersonFullName) {
        this.RBTPersonFullName = RBTPersonFullName;
    }

    public void addPerson(Person person) {
        setTotalPeople(getTotalPeople() + 1);
        getRBTPersonFullName().insert(person.getFullName(), person);
        getBSTPersonLastName().insert(person.getLastName(), person);
        getRBTPersonName().insert(person.getName(), person);
        getAVLPersonCode().insert(person.getCode(), person);
    }

    public void removePerson(Person person) {
        getAVLPersonCode().delete(person.getCode());
        getRBTPersonName().remove(person.getName());
        getBSTPersonLastName().delete(person.getLastName());
        getRBTPersonFullName().remove(person.getFullName());
        setTotalPeople(getTotalPeople() - 1);
    }

}
