package model.objects;

import model.data_structures.BST;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrudTest {


    private Crud cr;
    private int amount;

    public void setup1() {
        cr = new Crud();
        cr.addPerson(new Person(1, "Perez", "123456789", "Men", "10/10/2020", "1.70", "Colimbia"));
        cr.addPerson(new Person(2, "Maria", "123456789", "Men", "10/10/2020", "1.70", "Colimbia"));
        cr.addPerson(new Person(3, "Arturo", "123456789", "Men", "10/10/2020", "1.70", "Colimbia"));

    }

    public void setup2() {
        cr = new Crud();
        amount = 10;

    }


    @Test
    public void addPerson1(){
        setup1();
        assertEquals(3, cr.getRBTPersonName().getSize());
        assertEquals(3, cr.getAVLPersonCode().size());
        assertEquals(3, cr.getBSTPersonLastName().size());
        assertEquals(3, cr.getRBTPersonFullName().getSize());


    }

    @Test
    public void import1(){
        setup2();
        assertFalse(cr.importData(cr, amount));


    }

}
