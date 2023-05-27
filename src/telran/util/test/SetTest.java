package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.*;

import telran.util.Collection;
import telran.util.Set;

abstract class SetTest extends CollectionTest  {
	private static final int BIG_LENGTH = 100000;
	Set<Integer> set = getSet();
	abstract protected <T> Set<T> getSet();
	   

	@Override
	protected Collection<Integer> getCollection() {
		return set;
	}
	
	@Test
	void testAdd() {
		assertFalse(set.add(numbers[0]));
		assertEquals(numbers.length, set.size());
		assertTrue(set.add(777));
		assertEquals(numbers.length + 1, set.size());
	}
	
	@Test
	void iteratorTest() {
		Integer[] expected = {-20, 7, 10, 30, 50, 100};
		Iterator<Integer> it1 = set.iterator();
		Iterator<Integer> it2 = set.iterator();
		Integer[] actual = new Integer[6];
		int index = 0;
		actual[index++] = it2.next();
		actual[index++] = it2.next();
		actual[index++] = it2.next(); 
		actual[index++] = it2.next();
		actual[index++] = it2.next();
		actual[index++] = it2.next();
		Arrays.sort(actual);
		assertArrayEquals(expected, actual);
		
		index = 0;
		actual[index++] = it1.next();
		actual[index++] = it1.next();
		actual[index++] = it1.next(); 
		actual[index++] = it1.next();
		actual[index++] = it1.next();
		actual[index++] = it1.next();
		Arrays.sort(actual);
		assertArrayEquals(expected, actual);
		
		assertThrows(NoSuchElementException.class, () -> it2.next());
	}
	
	@Test
	void testRemoveIf1() {
		set.add(35);
		set.add(45);
		Integer[] expected = {7, 35, 45};
		assertTrue(set.removeIf(a -> a % 2 == 0));
		runTest(expected);
	}
	
	@Test
	void testRemoveIf2() {
		Integer[] expected = {7, 10, -20, 100};
		assertTrue(set.removeIf(a -> a == 50));
		assertTrue(set.removeIf(a -> a == 30));
		runTest(expected);
	}

	@Test
	void testToArrayForBigArray() {
		Integer bigArray[] = new Integer[BIG_LENGTH];
		for(int i = 0; i < BIG_LENGTH; i++) {
			bigArray[i] = 10;
		}
		Integer actualArray[] = set.toArray(bigArray);
		Arrays.sort(actualArray,0,set.size());
		int size = set.size();
		Integer expected[] = Arrays.copyOf(numbers, numbers.length);
		Arrays.sort(expected);
		for(int i = 0; i < size; i++) {
			assertEquals(expected[i], actualArray[i]);
		}
		assertNull(actualArray[size]);
		assertTrue(bigArray == actualArray);
	}
	
	@Test
	void testToArrayForEmptyArray() {
		Integer actualArray[] = set.toArray(new Integer[0]);
		Arrays.sort(actualArray);
		Integer expected[] = Arrays.copyOf(numbers, numbers.length);
		Arrays.sort(expected);
		assertArrayEquals(expected, actualArray);
	}
	
	@Override
	protected void runTest(Integer[] expected) {
		Integer [] actual = new Integer[expected.length];		
		set.toArray(actual);
		
		Arrays.sort(actual);
		Integer [] expectedCopy = new Integer[expected.length];				
		System.arraycopy(expected, 0, expectedCopy, 0, expected.length);
		Arrays.sort(expected);
		
		assertArrayEquals(expected, actual);
	}	
}
