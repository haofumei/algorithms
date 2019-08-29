import java.util.Iterator;
import java.util.NoSuchElementException;

public class DequeCycle<Item> implements Iterable<Item> {

    private final Node sentinel;
    private int size;

    // construct an empty deque
    public DequeCycle() {
        sentinel = new Node(null, null, null);
        size = 0;
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    private class Node {

        Node prev;
        Item item;
        Node next;

        public Node(Node p, Item i, Node n) {
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
        validate(item);
        sentinel.next = new Node(sentinel, item, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        this.size += 1;
    }

    // add the item to the back
    public void addLast(Item item) {
        validate(item);
        sentinel.prev = new Node(sentinel.prev, item, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        this.size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item i = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        this.size -= 1;
        return i;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item i = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        this.size -= 1;
        return i;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private final Node pointer = sentinel;

        public boolean hasNext() {
            if (pointer.next == sentinel) {
                return false;
            }
            return true;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = pointer.next.item;
            pointer.next = pointer.next.next;
            return item;
        }
    }

    private void validate(Item i) {
        if (i == null) {
            throw new IllegalArgumentException();
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        DequeCycle<Integer> test = new DequeCycle<>();
        for (int i = 0; i < 10; i++) {
            test.addLast(i);
        }
        for (Integer i : test) {
            System.out.println(i);
        }
    }
}

