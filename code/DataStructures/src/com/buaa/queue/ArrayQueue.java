package com.buaa.queue;

import java.util.Scanner;

public class ArrayQueue {
    private int maxSize; //表示数组的最大容量
    private int front; //队列头
    private int rear; // 队列尾
    private int[] arr; //该数组用于存放数据，模拟队列

    // 创建队列的构造器
    public ArrayQueue(int arrMaxSize) {
        maxSize = arrMaxSize;
        arr = new int[maxSize];
        front = -1; //指向队列头部，分析出front是指向队列头的前一个位置
        rear = -1; //指向队列尾，指向队列尾的数据（包含）
    }

    //判断队列是否满
    public boolean isFull() {
        return rear == (maxSize - 1);
    }

    //判断队列是否为空
    public boolean isEmpty() {
        return front == rear;
    }

    //添加数据到队列
    public void add(int n) {
        // 判断队列是否满
        if (isFull()) {
            System.out.println("队列满，不能加入数据");
            return;
        }
        arr[++rear] = n;
    }

    // 获取数据出队列
    public int get() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，不能取出数据");
        }
        return arr[++front];
    }

    //显示队列所有数据
    public void print() {
        if (isEmpty()) {
            System.out.println("队列空，没有数据");
            return;
        }
        for (int i = front + 1; i <= rear; i++) {
            System.out.printf("arr[%d] = %d\n", i, arr[i]);
        }
    }

    //显示队列的头部（不是取数据）
    public int head() {
        if (isEmpty()) {
            throw new RuntimeException("队列空，没有数据");
        }
        return arr[front + 1];
    }
}
