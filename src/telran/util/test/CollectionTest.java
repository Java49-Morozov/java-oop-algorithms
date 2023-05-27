package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import telran.util.*;


public abstract class CollectionTest {
	private static final int BIG_LENGTH = 100000;
	Collection<Integer> collection;
	Integer[] numbers = {10, -20, 7, 50, 100, 30};

	@BeforeEach
	void setUp() throws Exception {
		collection = getCollection();
		for( int i = 0; i < numbers.length; i++) {
			collection.add(numbers[i]);
		}
	}

	protected abstract Collection<Integer> getCollection();

	@Test
	void testAdd() {
		assertTrue(collection.add(numbers[0]));
		assertEquals(numbers.length + 1, collection.size());
	}

	@Test
	//!!!
	@Disabled
	void testSize() {
		fail("Not yet implemented");
	}

	@Test
	void testRemove() {
		Integer [] expectedNo10 = { -20, 7, 50, 100, 30};
		Integer [] expectedNo10_50 = { -20, 7,  100, 30};
		Integer [] expectedNo10_50_30 = { -20, 7,  100};
		assertTrue(collection.remove(numbers[0]));
		runTest(expectedNo10);
		Integer objToRemove = 50;
		assertTrue(collection.remove(objToRemove));
		runTest(expectedNo10_50);
		assertTrue(collection.remove((Integer)30));
		runTest(expectedNo10_50_30);
		assertFalse(collection.remove((Integer)50));
	}

	@Test
	void testToArray() {
		Integer ar[] = new Integer[BIG_LENGTH];
		for(int i = 0; i < BIG_LENGTH; i++) {
			ar[i] = 10;
		}
		Integer actualArray[] = collection.toArray(ar);
		int size = collection.size();
		runTest(numbers);
		assertNull(actualArray[size]);
		assertTrue(ar == actualArray);
	}

	@Test
	void testRemoveIf() {
		Integer[] expected = {10, -20, 50, 100, 30};
		assertFalse(collection.removeIf(a -> a % 2 != 0 && a >= 10));
		assertTrue(collection.removeIf(a -> a % 2 != 0));
		runTest(expected);
	}
	
	@Test
	void toArrayTest() {
		Integer[] expected = {10, -20, 7, 50, 100, 30};
		runTest(expected);
	}
	
	@Test
	void iteratorTest() {
		Iterator<Integer> it1 = collection.iterator();
		Iterator<Integer> it2 = collection.iterator();
		it2.next();
		it2.next();
		it2.next(); 
		it2.next();
		it2.next();
		assertEquals(10, it1.next());
		assertEquals(30, it2.next());
		assertThrows(NoSuchElementException.class, () -> it2.next());
	}
	
	@Test
	@Disabled
	void testRemoveIfBigArray() {
		int BIG = 1000000;
		Integer ar[] = new Integer[BIG];
		for(int i = 0; i < BIG; i++) {
			ar[i] = 10;
		}
		int index = collection.size();
		while (collection.size()<BIG) {
			collection.add(ar[index++]);
		}
	
		collection.removeIf(a -> a % 1 <= 1);
		assertEquals(0,collection.size());
	}
		
	protected void runTest(Integer[] expected) {
		Integer [] actual = new Integer[expected.length];		
		collection.toArray(actual);
		assertArrayEquals(expected, actual);
	}	
}
