package model.data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RBTTest {


    RBT<Integer, Integer> tree;

    void setUp1(){
        tree = new RBT();
        tree.insert(1,5);
        tree.insert(1,3);
        tree.insert(1,8);
        tree.insert(2, 6);
        tree.insert(1,1);
    }

    void setUp2(){
        tree = new RBT<>();
        tree.insert(1,5);
        tree.insert(1,3);
        tree.insert(1,8);
        tree.insert(1, 6);
        tree.insert(1,1);
        tree.insert(1, 9);
        tree.insert(10, 10);
    }

    @Test
    void insert() {
        setUp1();
        tree.insert(3,11);
        assertEquals(11, tree.search(3));
    }

    @Test
    void delete() {
        setUp2();
        tree.remove(10);
        assertNull(tree.search(tree.getRoot(), 10));
    }

    @Test
    void testSearch() {
        setUp1();
        assertEquals(6,tree.search(2));
    }

    @Test
    void testMin() {
        setUp1();
        assertEquals(1,tree.minimumV());
    }


}
