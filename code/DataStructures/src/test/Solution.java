package test;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.Queue;

class Checkout {
    private Queue<Integer> queue;

    public Checkout() {
        queue = new ArrayDeque<>();
    }

    public int get_max() {
        if (queue.isEmpty()) {
            return -1;
        }
        return Collections.max(queue);
    }

    public void add(int value) {
        queue.offer(value);
    }

    public int remove() {
        if (queue.isEmpty()) {
            return -1;
        }
        return queue.poll();
    }
}
