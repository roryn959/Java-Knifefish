package Model.DataStructures;

import java.util.InputMismatchException;
import java.util.Iterator;

public class LinkedList_<T> implements Iterable<T> {
    private LinkedListItem_<T> first, current;
    private int length;

    public LinkedList_(){
        this.first = null;
        this.current = null;
        this.length = 0;
    }

    public Iterator<T> iterator(){
        return new ListIterator_<T>(this);
    }

    public LinkedListItem_<T> getFirst(){
        return this.first;
    }

    public T getFirstItem(){
        return this.first.getItem();
    }

    public T getItemByIndex(int index){
        LinkedListItem_<T> currentNode = this.first;
        while (index>0){
            currentNode = currentNode.getNext();
            index = index-1;
        }
        return currentNode.getItem();
    }

    public int getLength(){
        return this.length;
    }

    public void add(T item){
        if (this.first == null){
            this.first = new LinkedListItem_<>(item);
        } else {
            LinkedListItem_<T> tempItem = this.first;
            LinkedListItem_<T> nextItem = this.first.getNext();
            while (nextItem != null){
                tempItem = nextItem;
                nextItem = tempItem.getNext();
            }
            // By the end of the loop we should have reached the end of the list, with nextItem being null
            tempItem.setNext(new LinkedListItem_<>(item));
        }
        this.length = this.length + 1;
    }

    public void remove(T item) throws InputMismatchException {
        LinkedListItem_<T> currentItem = this.first;
        LinkedListItem_<T> nextItem = this.first.getNext();

        // Check first item in list
        if (currentItem.getItem()==item){
            this.first = this.first.getNext();
            this.length = this.length-1;
            return;
        }
        // Else look through until we find our item
        while (nextItem!=null){
            if (nextItem.getItem()==item){
                currentItem.setNext(nextItem.getNext());
                this.length = this.length-1;
                return;
            } else {
                currentItem = nextItem;
                nextItem = currentItem.getNext();
            }
        }
        System.out.println("Removal failed. Items which are in the positions list:");
        for (T newItem : this){
            System.out.print(newItem + ", ");
        }
        throw new InputMismatchException("Value " + item + " does not exist in list.");
    }

    public LinkedList_<T> clone(){
        LinkedList_<T> newList = new LinkedList_<>();
        for (T item : this){
            newList.add(item);
        }
        return newList;
    }
}
