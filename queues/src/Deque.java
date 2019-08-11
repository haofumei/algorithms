import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private final Node<Item> sentinel;
    private int size;

    // construct an empty deque
    public Deque() {
        sentinel = new Node<>(null, null, null);
        size = 0;
    }

    private class Node<Item> {

        Node<Item> prev;
        Item item;
        Node<Item> next;

        public Node(Node<Item> p, Item i, Node<Item> n) {
            this.prev = p;
            this.item = i;
            this.next = n;
        }
    }

    // is the deque empty?
    public boolean isEmpty() {
        return this.size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        sentinel.next = new Node<>(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        this.size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        sentinel.prev = new Node<>(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        this.size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item i = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        this.size -= 1;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item i = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        this.size -= 1;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {

        return null;
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}

