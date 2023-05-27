package telran.util;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSet<T> implements Set<T> {
	private static final int DEFAULT_HASHTABLE_SIZE = 16;
	private LinkedList<T>[] hashTable;
	private int size;
	
	private class HashSetIterator implements Iterator<T> {
		int currentIndex = 0;
		Iterator<T> currentIt;
		boolean flNext = false;
		
		public HashSetIterator() {
			while (hashTable[currentIndex] == null && currentIndex < hashTable.length) {
				currentIndex++;
			}
			currentIt = (Iterator<T>) hashTable[currentIndex].iterator();
		}
		
		@Override
		public boolean hasNext() {
			boolean res = false;
			if (currentIt.hasNext()) {
				res = true;
			} else {
				currentIndex++;
				while (currentIndex < hashTable.length && hashTable[currentIndex] == null) {
					currentIndex++;
				}
				if (currentIndex < hashTable.length) {
					currentIt = (Iterator<T>) hashTable[currentIndex].iterator();
					res = true;
				}
			}
			return res;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			
			flNext = true;
			T obj = currentIt.next();
			return obj;
		}
		
		@Override
		public void remove() {
			if (!flNext) {
				throw new IllegalStateException();
			}
			
			currentIt.remove();
			size--;
			if (hashTable[currentIndex].size() == 0) {
				hashTable[currentIndex] = null;
				int currentIndexSav = currentIndex;
				while (currentIndex >= 0 && hashTable[currentIndex] == null) {
					currentIndex--;
				}
				currentIndex = (currentIndex < 0) ? currentIndexSav + 1 : currentIndex + 1;
				while (currentIndex < hashTable.length && hashTable[currentIndex] == null) {
					currentIndex++;	
				}
				if (currentIndex >= 0 && currentIndex < hashTable.length)
					currentIt = (Iterator<T>) hashTable[currentIndex].iterator();
			}
			flNext = false;
		}		
	}
	
	@SuppressWarnings("unchecked")
	public HashSet (int hashTableSize) {
		hashTable = new LinkedList[hashTableSize];
	}
	
	public HashSet( ) {
		this (DEFAULT_HASHTABLE_SIZE);
	}
	
	@Override
	public Iterator<T> iterator() {
		return new HashSetIterator();
	}
	
	@Override
	public boolean add(T obj) {
		if (size >= 0.75*hashTable.length) {
			recreation();
		}
		boolean res = false;
		int hashIndex = getHashTableIndex(obj);
		if (hashTable[hashIndex] == null) {
			hashTable[hashIndex] = new LinkedList<>();
		}
		if (!hashTable[hashIndex].contains(obj)) {
			hashTable[hashIndex].add(obj);
			res = true;
			size++;
		}
		return res;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean remove(T pattern) {
		boolean res = false;
		int index = getHashTableIndex(pattern);
		if (hashTable[index] != null) {
			res = hashTable[index].remove(pattern);
			if (res) {
				size--;
				if (hashTable[index].size() == 0) {
					hashTable[index] = null;
				}
			}
		}
		return res;
	}

	@Override
	public boolean contains(T pattern) {
		int index = getHashTableIndex(pattern);
		return hashTable[index] != null && hashTable[index].contains(pattern);
	}

	/*@Override
	public T[] toArray(T[] ar) {
		int size = size();
		if (ar.length < size) {
			ar = Arrays.copyOf(ar, size);
		}
		
		int index = 0;
		for (int i=0; i<hashTable.length; i++) {
			if (hashTable[i] != null) {
				Iterator<T> it = hashTable[i].iterator();
				while (it.hasNext()) {
					ar[index++] = (T) it.next();
				}
			}
		}
		
		if (ar.length > size) {
			ar[size] = null;
		}
		return ar;
	}*/

	//////////////////////////////////////////////////////////////////
	
	private int getHashTableIndex(T obj) {
		return Math.abs(obj.hashCode()) % hashTable.length;
	}
	
	private void recreation() {
		HashSet<T> tmp = new HashSet<>(hashTable.length * 2);
		for (int i=0; i<hashTable.length; i++) {
			if (hashTable[i] != null) {
				for (int k=0; k<hashTable[i].size; k++) {
					T obj = hashTable[i].get(k);
					tmp.add(obj);
				}
			}
		}
		
		this.hashTable = tmp.hashTable;
	}
}
