package com.amverhagen.tube.collections;

public interface Queue<T> {

	public void enqueue(T e);

	public T dequeue();

	public T peek();

	public boolean isEmpty();

	public int size();
}
