package model.data_structures;

import model.interfaces.IBST;
import java.io.Serializable;
import java.util.ArrayList;

public class BST<K extends Comparable<K>, V> implements IBST<K, V, Node<K, V>>, Serializable {

    private Node<K, V> root;
    private int size;
    private String treeInfo;
    private int weight;
    private static final long serialVersionUID = 1;

    public BST() {
        root = null;
        size = 0;
        treeInfo = "";
        weight = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Node<K, V> search(Node<K, V> root, K key) {
        if(root.key().compareTo(key)==0){
            // System.out.println("Es igual");
            return root;
        }else{
            // System.out.println("Compara "+root.value()+" con "+key);
            if(key.compareTo(root.key())>=0){
                //   System.out.println("Se va por la derecha");
                return search(root.right(), key);
            }else{
                // System.out.println("Se va por la Izquierda");
                return search(root.left(),key);
            }
        }

    }

    public Node<K, V> search(K key) {
        if(root.key().compareTo(key)==0){
            // System.out.println("Es igual");
            return root;
        }else{
            // System.out.println("Compara "+root.value()+" con "+key);
            if(key.compareTo(root.key())>=0){
                //   System.out.println("Se va por la derecha");
                return search(root.right(), key);
            }else{
                // System.out.println("Se va por la Izquierda");
                return search(root.left(),key);
            }
        }

    }

    public ArrayList<V> searchApproximate(String query) {
        ArrayList<V> results = new ArrayList<>();
        return searchApproximate(root, query, results);
    }

    public ArrayList<V> searchApproximate(Node<K, V> node, String query, ArrayList<V> results) {
        if (node == null) {
            return results;
        } else if (node.key().toString().contains(query)) {
            results = searchApproximate(node.left(), query, results);
            results.add(node.value());
            return searchApproximate(node.right(), query, results);
        }
        results = searchApproximate(node.left(), query, results);
        results = searchApproximate(node.right(), query, results);
        return results;
    }

    @Override
    public void insert(K key, V value) {
        Node<K, V> node = new Node<>(key, value);
        Node<K, V> trail = null;
        Node<K, V> current = root;
        while (current != null) {
            trail = current;
            if (node.key().compareTo(current.key()) < 0) {
                current = current.left();
            } else {
                current = current.right();
            }
            node.setParent(trail);
        }
        if (trail == null) {
            root = node;
        } else if (node.key().compareTo(trail.key()) < 0) {
            trail.setLeft(node);
        } else {
            trail.setRight(node);
        }

        size++;

    }

    @Override
    public void delete(K key) {
        Node<K, V> toDelete = search(root, key);
        System.out.println("Raiz: "+ root.value());
        System.out.println("El que se elimina: "+toDelete.value());
        delete(toDelete);

    }

    private void delete(Node<K, V> toDelete){
        if(toDelete!=null){
            if(toDelete.left() == null && toDelete.right()== null){
                if(toDelete.parent()!=null){
                    Node<K, V>aux = toDelete.parent();
                    System.out.println("padre: "+aux.value());
                    if(aux.left() != null ){
                        if(aux.left().equals(toDelete)){
                            toDelete.parent().setLeft(null);
                        }else{
                            toDelete.parent().setRight(null);
                        }
                    }else if(aux.right()!=null){
                        toDelete.parent().setRight(null);
                    }

                    //  System.out.println("No tiene hijos entonces entra acá");
                    toDelete.setParent(null);
                    //System.out.println(toDelete.parent());

                }
            }else{
                if(toDelete.left() == null){
                    //   System.out.println("Tiene hijo derecho");
                    Node<K, V> aux = toDelete.right();
                    toDelete.setRight(null);
                    aux.setParent(toDelete.parent());

                }else if(toDelete.right() == null){
                    // System.out.println("Tiene hijo izquierdo");
                    Node<K, V> aux = toDelete.left();
                    toDelete.setLeft(null);
                    aux.setParent(toDelete.parent());
                }else{
                    //System.out.println("Tiene ambos");
                    Node<K, V> successor = successor(toDelete);
                    toDelete.setValue(successor.value());
                    delete(successor);
                }
            }
        }

    }

    public Node<K, V> min() {
        return min(root);
    }

    @Override
    public Node<K, V> min(Node<K, V> root) {
        while (root.left() != null) {
            root = root.left();
        }
        return root;
    }

    public Node<K, V> max() {
        return max(root);
    }

    @Override
    public Node<K, V> max(Node<K, V> root) {
        while (root.right() != null) {
            root = root.right();
        }
        return root;
    }

    @Override
    public Node<K, V> successor(Node<K, V> current) {
        if(current.right() != null){
            return min(current.right());
        }else if(current.parent().key().compareTo(current.key()) > 0){
            return current.parent();
        }else{
            return current.parent().parent();
        }
    }

    @Override
    public Node<K, V> predecessor(Node<K, V> node) {
        if (node.left() != null) {
            return max(node.left());
        }
        Node<K, V> successor = node.parent();
        while (successor != null && node == successor.left()) {
            node = successor;
            successor = successor.parent();
        }
        return successor;
    }

    public String inorder() {
        StringBuilder sb = new StringBuilder();
        return inorderRec(root, sb);
    }

    private String inorderRec(Node<K, V> root, StringBuilder sb) {
        if (root != null) {
            inorderRec(root.left(), sb);
            sb.append(root.key().toString()).append(" ");
            inorderRec(root.right(), sb);
        }
        return sb.toString();
    }

    public String inorderVals() {
        StringBuilder sb = new StringBuilder();
        return inorderRecVals(root, sb);
    }

    private String inorderRecVals(Node<K, V> root, StringBuilder sb) {
        if (root != null) {
            inorderRecVals(root.left(), sb);
            sb.append(root.value().toString()).append(" ");
            inorderRecVals(root.right(), sb);
        }
        return sb.toString();
    }

    public ArrayList<V> toArrayList() {
        ArrayList<V> array = new ArrayList<>();
        return toArrayListRec(root, array);
    }

    private ArrayList<V> toArrayListRec(Node<K, V> root, ArrayList<V> array) {
        if (root != null) {
            toArrayListRec(root.left(), array);
            array.add(root.value());
            toArrayListRec(root.right(), array);
        }
        return array;
    }

    public int getWeight(){
        weight = 0;
        if(root!=null) {
            getWeight(root);
        }

        return weight;
    }

    private void getWeight(Node<K,V> node){
        if(node!=null){
            printInOrder(node.left());
            weight++;
            printInOrder(node.right());
        }
    }

    public String printInOrder(){
        treeInfo = "";
        if(root!=null) {
            printInOrder(root);
        }

        return treeInfo;
    }

    private void printInOrder(Node<K,V> node){
        if(node!=null){
            printInOrder(node.left());
            treeInfo+=node.value().toString()+" ";
            printInOrder(node.right());
        }
    }

    public Node<K, V> root() {
        return root;
    }

    public void setRoot(Node<K, V> root) {
        this.root = root;
    }

    public int size() {
        return size;
    }
}
