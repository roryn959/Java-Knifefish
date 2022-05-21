package Model.DataStructures;

public class LinkedListItem_<T> {
    private final T item;
    private LinkedListItem_ next;

    public LinkedListItem_(T item){
        this.item = item;
        this.next = null;
    }

    public void setNext(LinkedListItem_<T> nextItem){
        this.next = nextItem;
    }

    public LinkedListItem_ getNext(){
        return this.next;
    }

    public T getItem(){
        return this.item;
    }
}