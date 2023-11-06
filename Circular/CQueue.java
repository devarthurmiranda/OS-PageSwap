package Circular;

public class CQueue {
    private int[] queue;
    private int front;
    private int rear;
    private int capacity;
    private int size;

    public CQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new int[capacity];
        this.front = 0;
        this.rear = -1;
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public int size() {
        return size;
    }

    public void enqueue(int item) {
        if (!isFull()) {
            rear = (rear + 1) % capacity;
            queue[rear] = item;
            size++;
        } else {
            System.out.println("Queue is full. Cannot enqueue.");
        }
    }

    public int dequeue() {
        if (!isEmpty()) {
            int item = queue[front];
            front = (front + 1) % capacity;
            size--;
            return item;
        } else {
            System.out.println("Queue is empty. Cannot dequeue.");
            return -1; // You can choose a different error value if needed.
        }
    }

    public int peek() {
        if (!isEmpty()) {
            return queue[front];
        } else {
            System.out.println("Queue is empty. Cannot peek.");
            return -1; // You can choose a different error value if needed.
        }
    }
}