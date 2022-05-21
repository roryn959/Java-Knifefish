package Model.DataStructures;

import java.util.InputMismatchException;
import java.util.Iterator;

public class LinkedList_<T> implements Iterable<T> {
    private LinkedListItem_<T> first, current;

    public LinkedList_(){
        this.first = null;
        this.current = null;
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
    }

    public void remove(T item) throws InputMismatchException {
        LinkedListItem_<T> tempItem = this.first;
        LinkedListItem_<T> nextItem = this.first.getNext();

        // If there is just one element in list, check it and remove if applicable
        if (nextItem == null){
            if (tempItem.getItem() == item){
                this.first = null;
                return;
            }
        }
        // Else look through until we find our item
        while (nextItem != null){
            if (nextItem.getItem() == item){
                tempItem.setNext(nextItem.getNext());
                return;
            } else {
                tempItem = nextItem;
                nextItem = tempItem.getNext();
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
