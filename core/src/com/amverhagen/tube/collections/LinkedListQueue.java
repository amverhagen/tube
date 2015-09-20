package com.amverhagen.tube.collections;

import java.util.LinkedList;

public class LinkedListQueue<T> implements Queue<T> {

	private LinkedList<T> elements;

	public LinkedListQueue() {
		elements = new LinkedList<T>();
	}

	@Override
	public void enqueue(T e) {
		elements.add(e);
	}

	@Override
	public T dequeue() {
		return elements.removeFirst();
	}

	@Override
	public T peek() {
		return elements.getFirst();
	}

	@Override
	public boolean isEmpty() {
		if (elements.size() == 0) {
			return true;
		}
		return false;
	}

	@Override
	public int size() {
		return elements.size();
	}

}
