package model.data_structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AVLTest {

    AVL<Integer, Integer> tree;

    void setUp1(){
        tree = new AVL<>();
        tree.insert(1,5);
        tree.insert(1,3);
        tree.insert(1,8);
        tree.insert(2, 6);
        tree.insert(1,1);
    }

    void setUp2(){
        tree = new AVL<>();
        tree.insert(1,5);
        tree.insert(1,3);
        tree.insert(1,8);
        tree.insert(1, 6);
        tree.insert(1,1);
        tree.insert(1, 9);
        tree.insert(10, 10);
    }

    @Test
    void testIsBalanced() {
        setUp1();
        assertTrue(tree.isBalanced());
    }

    @Test
    void testIsBalanced2() {
        setUp2();
        assertTrue(tree.isBalanced());
    }

    @Test
    void insert() {
        setUp1();
        tree.insert(3,11);
        assertEquals(11, tree.search(3).value());
    }

    @Test
    void delete() {
        setUp2();
        tree.delete(10);
        assertEquals(1,tree.max().key());
    }

    @Test
    void testSearch() {
        setUp1();
        assertEquals(6,tree.search(2).value());
    }

    @Test
    void testSuccessor() {
        setUp1();
        assertEquals(1,tree.successor(tree.search(2)).key());
    }

    @Test
    void testMin() {
        setUp1();
        assertEquals(1,tree.min().key());
    }

    @Test
    void testMax() {
        setUp1();
        assertEquals(2,tree.max().key());
    }

}