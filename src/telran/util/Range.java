package telran.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Range implements Iterable<Integer> {
	private int min;
	private int max;

	public Range (int min, int max) {
		if (min >= max) {
			throw new IllegalArgumentException("min must be less than max");
		}
		this.min = min;
		this.max = max;
	}
	
	private class RangeIterator implements Iterator<Integer> {

		int current = min;
		
		@Override
		public boolean hasNext() {
			return current < max;
		}

		@Override
		public Integer next() {
			if(!hasNext()) {
				throw new NoSuchElementException();
			}
			return current++;
		}
	}
	
	@Override
	public Iterator<Integer> iterator() {
		return new RangeIterator();
	}

	public Integer[] toArray() {
		Integer [] array = new Integer[max - min];
		int index = 0;
		//First way
//		for(Integer num: this) {
//			array[index++] = num;
//		}
		//Second way
		Iterator<Integer> it = iterator();
		while (it.hasNext()) {
			array[index++] = it.next();
		}
		
		return array;
	}
}
