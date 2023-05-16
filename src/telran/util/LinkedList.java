package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Predicate;

public class LinkedList<T> implements List<T> {
	Node<T> head;
	Node<T> tail;
	int size;

	private static class Node<T> {
		T obj;
		Node<T> next;
		Node<T> prev;
		Node(T obj) {
			this.obj = obj;
		}
	}
	
	@Override
	public boolean add(T obj) {
		add (size, obj);
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean remove(T pattern) {
		boolean res = false;
		int index=indexOf (pattern);
		if (index > -1) {
			res = true;
			remove (index);
		}
		return res;
	}

	@Override
	public T[] toArray(T[] ar) {
		if (ar.length < size) {
			ar = Arrays.copyOf(ar, size);
		}
		Node<T> current = head;
		int index = 0;
		while (current != null) {
			ar[index++] = current.obj;
			current = current.next;
		}
		if (ar.length > size) {
			ar[size] = null;
		}
		return ar;
	}

	@Override
	public void add(int index, T obj) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		Node<T> node = new Node<>(obj);
		addNode(index, node);
	}

	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		Node<T> node = getNode(index);
		removeNode(node);
		T res = node.obj;
		node.obj = null;
		return res;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		return getNode(index).obj;
	}

	@Override
	public int indexOf(T pattern) {
		return indexOf(obj -> isEqual(obj, pattern));
	}
	
	@Override
	public int lastIndexOf(T pattern) {
		return lastIndexOf(obj -> isEqual(obj, pattern));
	}

	@Override
	public void sort() {
		sort((Comparator<T>)Comparator.naturalOrder());
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sort(Comparator<T> comp) {
		Object[] ar = new Object[size];
		toArray((T[]) ar);
		Arrays.sort(ar);
		Node<T> node = head;
		int index = 0;
		while (node != null) {
			node.obj = (T) ar[index++];
			node = node.next;
		}
	}	

	@Override
	public int indexOf(Predicate<T> predicate) {
		int index = 0;
		Node<T> current = head;
		while (current != null && !predicate.test(current.obj)) {
			current = current.next;
			index++;
		}
		return current == null ? -1 : index;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		int index = size - 1;
		Node<T> current = tail;
		while (current != null && !predicate.test(current.obj)) {
			current = current.prev;
			index--;
		}
		return current == null ? -1 : index;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		Node<T> current = head;
		Node<T> next = null;
		int oldSize = size;
		while (current != null) {
			next = current.next;
			if (predicate.test(current.obj)) {
				removeNode(current);
			}
			current = next;

		}
		return oldSize > size;
	}

	/////////////////////////////////////////
	
	private void addNode(int index, Node<T> node) 
	{
		if (head == null) {
			head = tail = node;
		} else {
			if (index == 0) {
				addNodeHead(node);
			} 
			else if (index == size) {
				addNodeTail(node);
			} 
			else {
				addNodeMiddle(index, node);
			}
		}
		size++;
	}

	private void addNodeHead(Node<T> node) {
		node.next = head;
		head.prev = node;
		head = node;
	}
	private void addNodeTail(Node<T> node) {
		node.prev = tail;
		tail.next = node;
		tail = node;
	}
	private void addNodeMiddle(int index, Node<T> node) {
		Node<T> nodeA = getNode(index);
		Node<T> nodeBefore = nodeA.prev;
		node.prev = nodeBefore;
		node.next = nodeA;
		nodeBefore.next = node;
		nodeA.prev = node;		
	}
	
	private Node<T> getNode(int index) {

		return index > size / 2 ? getNodeFromRight(index) : getNodeFromLeft(index);
	}

	private Node<T> getNodeFromLeft(int index) {
		Node<T> current = head;
		for (int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	private Node<T> getNodeFromRight(int index) {
		Node<T> current = tail;
		for (int i = size - 1; i > index; i--) {
			current = current.prev;
		}
		return current;
	}

	private void removeHead() {
		Node<T> newHead = head.next;
		if (newHead != null) {
			newHead.prev = null;
		}
		head.next = null;
		head = newHead;

	}

	private void removeTail() {
		Node<T> newTail = tail.prev;
		if (newTail != null) {
			newTail.next = null;
		}
		tail.prev = null;
		tail = newTail;
	}

	private void removeMiddle(Node<T> node) {
		Node<T> nodeBefore = node.prev;
		Node<T> nodeAfter = node.next;
		nodeBefore.next = nodeAfter;
		nodeAfter.prev = nodeBefore;
		node.next = node.prev = null;
	}

	private void removeNode(Node<T> node) {
		if (node == head) {
			removeHead();
		} else if (node == tail) {
			removeTail();
		} else {
			removeMiddle(node);
		}
		size--;
	}

	private boolean isEqual(T object, T pattern) {

		return pattern == null  ? object == pattern : pattern.equals(object);
	}
}