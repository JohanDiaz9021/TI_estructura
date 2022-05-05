package model.data_structures;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {
    
    private BST<Integer,Integer> tree;

    public void setup1() {

        tree = new BST<>();
    }

    public void setup2() {

        tree = new BST<>();
        tree.insert(3,3);
        tree.insert(5,5);


    }

    public void setup3() {

        tree = new BST<>();
        tree.insert(3,3);
        tree.insert(5,5);
        tree.insert(61,61);
        tree.insert(2,2);
        tree.insert(7,7);
        tree.insert(8,8);


    }
    @Test
    public void insert1(){
        setup1();
        tree.insert(3,3);
        tree.insert(3,5);
        assertFalse(tree.isEmpty());


    }
    @Test
    public void delete(){
        setup2();
        tree.delete(5);
        assertEquals(1,tree.getWeight());
    }

    @Test
    public void search(){
        setup2();
        assertTrue(tree.search(5)!=null);
    }

    @Test
    public void successor1(){
        setup3();
        assertEquals(3, tree.successor(tree.search(2)).value());
    }
    @Test
    public void successor2(){
        setup3();
        assertEquals(7, tree.successor(tree.search(5)).value());
    }
    @Test
    public void successor3(){
        setup3();
        assertEquals(5, tree.successor(tree.search(3)).value());
    }
}