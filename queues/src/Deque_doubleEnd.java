import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    private class Node {

        Node prev;
        Item item;
        Node next;

        public Node(Item i) {
            this.prev = null;
            this.item = i;
            this.next = null;
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
        if (isEmpty()) {
            first = new Node(item);
            last = first;
        } else {
            Node temp = new Node(item);
            temp.next = first;
            first.prev = temp;
            first = temp;
        }
        size += 1;

    }

    // add the item to the back
    public void addLast(Item item) {
        validate(item);
        if (isEmpty()) {
            last = new Node(item);
            first = last;
        } else {
            Node node = new Node(item);
            node.prev = last;
            last.next = node;
            last = node;
        }
        size += 1;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first != null) {
            first.prev = null;
        } else {
            last = null;
        }
        size -= 1;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        if (isEmpty()) throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        if (last != null) {
            last.next = null;
        } else {
            first = null; // Solved: loitering detected on 1 of 100 removeLast() operations
        }
        size -= 1;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {

        private Node pointer = first;

        public boolean hasNext() {
            return pointer != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = pointer.item;
            pointer = pointer.next;
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
        Deque<Integer> test = new Deque<>();
        for (int i = 0; i < 10; i++) {
            test.addLast(i);
        }
        for (Integer i : test) {
            System.out.println(i);
        }
    }
}
