package com.buaa.queue;

public class CircleArrayQueue {
    private int[] arr;
    private int maxSize;
    private int rear;
    private int front;

    public CircleArrayQueue(int maxSize) {
        this.maxSize = maxSize;
        arr = new int[maxSize];
    }

    public boolean isFull() {
        return ((rear + 1) % maxSize) == front;
    }

    public boolean isEmpty() {
        return rear == front;
    }

    public void add(int n) {
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }
        arr[rear] = n;
        rear = (rear + 1) % maxSize;
    }

    public int get() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，无法取出数据");
        }
        int res = arr[front];
        front = (front + 1) % maxSize;
        return res;
    }

    public int size() {
        return (rear + maxSize - front) % maxSize;
    }

    public void print() {
        if (isEmpty()) {
            System.out.println("队列空，无法取出数据");
            return;
        }
        for (int i = front; i < front + size(); i++) {
            System.out.printf("%d\t", arr[i % maxSize]);
        }
        System.out.println();
    }

    public int head() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，无法取出数据");
        }
        return arr[front];
    }
}
