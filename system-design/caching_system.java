// High-Level Design (HLD):
// Requirements:
// The cache should be able to store key-value pairs.
// It should have a maximum capacity.
// When the cache reaches its capacity, it should evict the least recently used item.
// Operations: put(key, value), get(key)

// Components:
// Cache Manager: Manages the cache operations.
// Cache: Data structure to store key-value pairs.
// Node: A node structure to represent each key-value pair.
// Hash Map: To store the key-value pairs for fast retrieval.
// Doubly Linked List: To maintain the order of usage.
// Algorithm:
// For put(key, value):
// If the key is already present in the cache, update its value and move it to the front of the list.
// If the key is not present:
// If the cache is full, remove the least recently used item from the end of the list and insert the new item at the front.
// If the cache is not full, insert the new item at the front.
// For get(key):
// If the key is present, return its value and move the corresponding node to the front.
// If the key is not present, return null.

// Node class representing each key-value pair
class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

// Doubly Linked List class
class DoublyLinkedList {
    Node head;
    Node tail;

    public DoublyLinkedList() {
        head = new Node(-1, -1); // dummy head
        tail = new Node(-1, -1); // dummy tail
        head.next = tail;
        tail.prev = head;
    }

    // Add node to the front
    public void addToFront(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    // Remove node from the list
    public void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // Move node to the front
    public void moveToFront(Node node) {
        removeNode(node);
        addToFront(node);
    }

    // Remove last node and return its key
    public int removeLast() {
        Node lastNode = tail.prev;
        removeNode(lastNode);
        return lastNode.key;
    }
}

// Cache class
class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> cache;
    private final DoublyLinkedList dll;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        dll = new DoublyLinkedList();
    }

    public int get(int key) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            dll.moveToFront(node);
            return node.value;
        }
        return -1;
    }

    public void put(int key, int value) {
        if (cache.containsKey(key)) {
            Node node = cache.get(key);
            node.value = value;
            dll.moveToFront(node);
        } else {
            if (cache.size() == capacity) {
                int evictedKey = dll.removeLast();
                cache.remove(evictedKey);
            }
            Node newNode = new Node(key, value);
            dll.addToFront(newNode);
            cache.put(key, newNode);
        }
    }
}
