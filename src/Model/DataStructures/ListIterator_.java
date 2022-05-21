package Model.DataStructures;

import java.util.Iterator;

public class ListIterator_<T> implements Iterator<T> {
    private LinkedListItem_<T> current;

    public ListIterator_(LinkedList_<T> list){
        this.current = list.getFirst();
    }

    public boolean hasNext(){
        return this.current != null;
    }

    public T next(){
        T item = this.current.getItem();
        this.current = this.current.getNext();
        return item;
    }
}
