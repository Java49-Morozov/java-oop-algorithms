package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.Range;
import java.util.NoSuchElementException;

class RangeTest {
	Range range = new Range(10, 14);

	@Test
	void constructorExceptionTest() {
		assertThrowsExactly(IllegalArgumentException.class, () -> new Range(10, 10));
	}

	@Test
	void testIterator() {
		Iterator<Integer> it1 = range.iterator();
		Iterator<Integer> it2 = range.iterator();
		it2.next();it2.next();it2.next(); it2.next();
		assertEquals(10, it1.next());
		assertThrows(NoSuchElementException.class, () -> it2.next());
	}

	@Test
	void testToArray() {
		Integer[] expected = {10, 11, 12, 13};
		assertArrayEquals(expected, range.toArray());
	}
}
