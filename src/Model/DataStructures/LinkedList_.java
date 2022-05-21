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

    public void addItem(T item){
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

    public void removeItemByValue(T item) throws InputMismatchException {
        LinkedListItem_<T> tempItem = this.first;
        LinkedListItem_<T> nextItem = this.first.getNext();
        while (nextItem != null){
            if (nextItem.getItem() == item){
                tempItem.setNext(nextItem.getNext());
                return;
            } else {
                tempItem = nextItem;
                nextItem = tempItem.getNext();
            }
        }
        throw new InputMismatchException("Value " + item + "does not exist in list.");
    }
}
