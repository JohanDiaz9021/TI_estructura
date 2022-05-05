package model.data_structures;

import java.io.Serializable;

public class MeLinkedLists<E> implements Serializable {
    private static final long serialVersionUID = 4L;
    private LinkedListNode<E> head;
    private LinkedListNode<E> tail;
    private int size;

    public MeLinkedLists() {
        head = null;
        size = 0;
        tail = null;
    }

    public LinkedListNode<E> getHead() {
        return head;
    }

    public LinkedListNode<E> getTail() {
        return tail;
    }

    public boolean isEmpty() {
        return (head == null)? true: false;
    }

    public int size() {
        return size;
    }

    public void add(E e) {

        if (head == null) {
            head = new LinkedListNode<E>(e);
            tail = head;

        }else{
            add(e, tail, 0);

        }

        size += 1;
    }

    private void add(E e, LinkedListNode<E> temp, int vali) {

        if(vali == 0){
            temp.setNext(new LinkedListNode<E>(e));
            temp.getNext().setPrevious(temp);
            tail = temp.getNext();
            vali += 1;
        }

        if(temp.getPrevious() != null && vali == 1){
            add(e, temp.getPrevious(), vali);

        }else{
            head = temp;
        }

    }

    public int indexOf(E e){
        return indexOf(e, head, 0);
    }

    private int indexOf(E e, LinkedListNode<E> temp, int contador){
        if(e.equals(temp.getItem())){
            return contador;

        }else{
            return indexOf(e, temp.getNext(), contador + 1);

        }
    }

    public E get(int index){

        return get(index, head);

    }

    private E get(int index, LinkedListNode<E> temp){

        if(index == 0){
            return temp.getItem();

        }else{
            return get(index - 1, temp.getNext());

        }

    }

    public LinkedListNode<E> getNode(int index){

        return getNode(index, head);

    }

    private LinkedListNode<E> getNode(int index, LinkedListNode<E> temp){

        if(index == 0){
            return temp;

        }else{
            return getNode(index - 1, temp.getNext());

        }

    }

    public void remove(int index){
        if(index == 0){
            head = head.getNext();
        }else{
            getNode(index).getPrevious().setNext(getNode(index).getNext());
            getNode(index).getNext().setPrevious(getNode(index).getPrevious());
        }

        size -= 1;
    }

}

class LinkedListNode<E> implements Serializable{

    private static final long serialVersionUID = 5L;
    private E item;
    private LinkedListNode<E> next;
    private LinkedListNode<E> previous;
    private LinkedListNode<E> root;

    public LinkedListNode(E element) {
        this.item = element;
        this.next = null;
        this.previous = null;

    }

    public LinkedListNode<E> getRoot() {
        return root;
    }

    public void setRoot(LinkedListNode<E> root) {
        this.root = root;
    }

    public void setItem(E item) {
        this.item = item;
    }

    public E getItem() {
        return item;
    }

    public LinkedListNode<E> getNext() {
        return next;
    }

    public void setNext(LinkedListNode<E> next) {
        this.next = next;
    }

    public LinkedListNode<E> getPrevious() {
        return previous;
    }

    public void setPrevious(LinkedListNode<E> previous) {
        this.previous = previous;
    }
}
