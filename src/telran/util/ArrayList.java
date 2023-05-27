package telran.util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class ArrayList<T> implements List<T> {
	private static final int DEFAULT_CAPACITY = 16;
	private T[] array;
	private int size;
	
	private class ArrayListIterator implements Iterator<T> {
		int currentItem=0;
		boolean flNext = false;
		@Override
		public boolean hasNext() {
			return currentItem < size;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			flNext = true;
			return array[currentItem++];
		}
		
		@Override
		public void remove() {
			if (!flNext) {
				throw new IllegalStateException();
			}
			
			ArrayList.this.remove (--currentItem);
			flNext = false;
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList(int capacity) {
		array = (T[]) new Object[capacity];
	}

	public ArrayList() {
		this(DEFAULT_CAPACITY);
	}

	@Override
	public boolean add(T obj) {
		if (size == array.length) {
			reallocate();
		}
		array[size] = obj;
		size++;
		return true;
	}

	@Override
	public boolean removeIf(Predicate<T> predicate) {
		int oldSize = size;
		int k = 0;
		for (int i=0; i<size; i++) {
			if (!predicate.test(array[i])) {
				array[k] = array[i];
				k++;
			}
		}
		size = k;
		
		for (int i=size; i<oldSize; i++) {
			array[i] = null;
		}
		return size < oldSize;
	}

	private void reallocate() {
		array = Arrays.copyOf(array, array.length * 2);
	}

	@Override
	public void add(int index, T obj) {
		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException(index);
		}
		if (size == array.length) {
			reallocate();
		}
		System.arraycopy(array, index, array, index + 1, size - index);
		array[index] = obj;
		size++;
	}
	
	@Override
	public T remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		T res = array[index];

		System.arraycopy(array, index + 1, array, index, size - index - 1);
		size--;
		array[size]=null;
		return res;
	}

	@Override
	public T get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}
		T res = array[index];
		return res;
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public void sort(Comparator<T> comp) {
		int n = size;
		boolean flUnSort = true;
		do {
			flUnSort = false;
			n--;
			for(int i = 0; i < n; i++) {
				if (comp.compare(array[i], array[i + 1]) > 0) {
					swap(i);
					flUnSort = true;
				}
			}
		}while(flUnSort);		
	}

	private void swap(int i) {
		T tmp = array[i];
		array[i] = array[i + 1];
		array[i + 1] = tmp;		
	}

	@Override
	public int indexOf(Predicate<T> predicate) {
		int res = -1;
		int index = 0;
		while (index < size && res == -1) {
			if (predicate.test(array[index])) {
				res = index;
			}
			index++;
		}
		return res;
	}

	@Override
	public int lastIndexOf(Predicate<T> predicate) {
		int res = -1;
		int index = size - 1;
		while (index >= 0 && res == -1) {
			if (predicate.test(array[index])) {
				res = index;
			}
			index--;
		}
		return res;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ArrayListIterator();
	}
}
