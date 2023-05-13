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
		add (size,obj);
		return true;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean remove(T pattern) {
		int index = 0;
		boolean found = false;
		Node<T> node = head;
		while (node!=null && !found) {
			if (node.obj == pattern) {
				found = true;
			} else {
				index++;
				node = node.next;
			}
		}
		
		if (found)
			remove (index);
		return found;
	}

	@Override
	public T[] toArray(T[] ar) 
	{
		if (ar.length < size) 
		{
			ar = Arrays.copyOf(ar, size);
		}
		Node<T> current = head;
		int index = 0;
		while (current != null) 
		{
			ar[index++] = current.obj;
			current = current.next;
		}
		if (ar.length > size) 
		{
			ar[size] = null;
		}
		return ar;
	}

	@Override
	public void add(int index, T obj) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		Node<T> node = new Node<T>(obj);
		addNode(index, node);
	}

	@Override
	public T remove(int index) {
		if (index<0 || index>=size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		T obj = removeNode(index);
		return obj;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		
		Node<T> node=getNode(index);
		T obj=node.obj;
		return obj;
	}

	@Override
	public int indexOf(T pattern) {
		int index = 0;
		boolean found = false;
		Node<T> node = head;
		while (node!=null && !found) {
			if (node.obj == pattern) {
				found = true;
			} else {
				index++;
				node = node.next;
			}
		}
		
		if (!found)
			index = -1;
		return index;
	}

	@Override
	public int lastIndexOf(T pattern) {
		int index = size-1;
		boolean found = false;
		Node<T> node = tail;
		while (node!=null && !found) {
			if (node.obj == pattern) {
				found = true;
			} else {
				index--;
				node = node.prev;
			}
		}
		
		if (!found)
			index = -1;
		return index;
	}

	@Override
	public void sort() {
		// TODO Auto-generated method stub

	}

	@Override
	public void sort(Comparator<T> comp) {
		// TODO Auto-generated method stub

	}

	@Override
	public int indexOf(Predicate<T> predicate) {
		int index = 0;
		boolean found = false;
		Node<T> node = head;
		while (node!=null && !found) {
			if (predicate.test(node.obj)) {
				found = true;
			} else {
				index++;
				node = node.next;
			}
		}
		
		if (!found)
			index = -1;
		return index;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int oldSize = size;
		for (int i = size - 1; i >= 0; i--) {
			Node<T> node = getNode(i);
			if (predicate.test(node.obj)) {
				remove(i);
			} 
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
	
	private T removeNode (int index) {
		T obj;
		if (index==0) {
			obj = removeNodeHead();
		}
		else if (index == size-1) {
			obj = removeNodeTail();
		}
		else {
			obj = removeNodeMiddle(index);
		}
		
		size--;
		return obj;
	}
	
	private T removeNodeHead() {
		T obj = head.obj;		
		head = head.next;
		if (head!=null)
			head.prev = null;
		return obj;
	}
	
	private T removeNodeTail()
	{
		T obj = tail.obj;
		tail = tail.prev;
		if (tail!=null)
			tail.next = null;
		return obj;
	}
	
	private T removeNodeMiddle (int index) {
		Node<T> node = getNode(index);
		Node<T> nodeBefore = node.prev;
		Node<T> nodeAfter = node.next;
		nodeBefore.next=nodeAfter;
		nodeAfter.prev=nodeBefore;
		
		return node.obj;
	}

	private Node<T> getNode(int index) {
		return index > size / 2 ? getNodeFromRight(index) :
			getNodeFromLeft(index);
	}	

	private Node<T> getNodeFromLeft(int index) {
		Node<T> current = head;
		for(int i = 0; i < index; i++) {
			current = current.next;
		}
		return current;
	}

	private Node<T> getNodeFromRight(int index) {
		Node<T> current = tail;
		for(int i = size - 1; i > index; i--) {
			current = current.prev;
		}
		return current;
	}
}
