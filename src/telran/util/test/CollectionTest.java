package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

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
		for(int i = 0; i < size; i++) {
			assertEquals(numbers[i], actualArray[i]);
		}
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

	private void runTest(Integer[] expected) {
		Integer [] actual = new Integer[expected.length];		
		collection.toArray(actual);
		assertArrayEquals(expected, actual);
	}	
}
