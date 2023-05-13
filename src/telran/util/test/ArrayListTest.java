package telran.util.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.*;

class ArrayListTest {
	private static final int BIG_LENGTH = 100000;
	List<Integer> list;
	Integer[] numbers = {10, -20, 7, 50, 100, 30};
	@BeforeEach
	void setUp() {
		list = new ArrayList<>(1);
		for( int i = 0; i < numbers.length; i++) {
			list.add(numbers[i]);
		}
	}
	@Test
	void testAdd() {
	assertTrue(list.add(numbers[0]));
	assertEquals(numbers.length + 1, list.size());
	}
	@Test
	void testAddIndex() {
		Integer [] expected0_500 = {500, 10, -20, 7, 50, 100, 30};
		Integer [] expected0_500_3_700 = {500, 10, -20, 700, 7, 50, 100, 30};
		Integer [] expected0_500_3_700_8_300 = {500, 10, -20, 700, 7, 50, 100, 30, 300};
		list.add(0, 500);
		runTest(expected0_500);
		list.add(3, 700);
		runTest(expected0_500_3_700);
		list.add(8, 300);
		runTest(expected0_500_3_700_8_300);
		assertThrowsExactly(IndexOutOfBoundsException.class,
				() ->list.add(list.size() + 1, 100));
		assertThrowsExactly(IndexOutOfBoundsException.class,
				() ->list.add(-1, 100));
		
	}
	@Test
	void testRemoveIndex() {
		Integer [] expectedNo10 = { -20, 7, 50, 100, 30};
		Integer [] expectedNo10_50 = { -20, 7,  100, 30};
		Integer [] expectedNo10_50_30 = { -20, 7,  100};
		assertEquals(10, list.remove(0));
		runTest(expectedNo10);
		assertEquals(50, list.remove(2));
		runTest(expectedNo10_50);
		assertEquals(30, list.remove(3));
		runTest(expectedNo10_50_30);
		assertThrowsExactly(IndexOutOfBoundsException.class,
				() -> list.remove(3));
		assertThrowsExactly(IndexOutOfBoundsException.class,
				() -> list.remove(-3));
		
	}
	@Test
	void testGetIndex() {
		assertEquals(10, list.get(0));
		assertThrowsExactly(IndexOutOfBoundsException.class,
				() -> list.get(list.size()));
		assertThrowsExactly(IndexOutOfBoundsException.class,
				() -> list.get(-1));
	}
	@Test
	void testRemovePattern() {
		Integer [] expectedNo10 = { -20, 7, 50, 100, 30};
		Integer [] expectedNo10_50 = { -20, 7,  100, 30};
		Integer [] expectedNo10_50_30 = { -20, 7,  100};
		assertTrue(list.remove(numbers[0]));
		runTest(expectedNo10);
		Integer objToRemove = 50;
		assertTrue(list.remove(objToRemove));
		runTest(expectedNo10_50);
		assertTrue(list.remove((Integer)30));
		runTest(expectedNo10_50_30);
		assertFalse(list.remove((Integer)50));
	}
	@Test
	void testIndexOf() {
		list.add(3, 1280);
		assertEquals(3, list.indexOf(1280));
		assertEquals(-1, list.indexOf((Integer)null));
	}
	@Test
	void testLastIndexOf() {
		list.add(3, 10);
		assertEquals(3, list.lastIndexOf(10));
		assertEquals(-1, list.lastIndexOf((Integer)null));
	}
	@Test
	void testToArrayForBigArray() {
		Integer bigArray[] = new Integer[BIG_LENGTH];
		for(int i = 0; i < BIG_LENGTH; i++) {
			bigArray[i] = 10;
		}
		Integer actualArray[] = list.toArray(bigArray);
		int size = list.size();
		for(int i = 0; i < size; i++) {
			assertEquals(numbers[i], actualArray[i]);
		}
		assertNull(actualArray[size]);
		assertTrue(bigArray == actualArray);
	}
	@Test
	void testToArrayForEmptyArray() {
		Integer actualArray[] =
				list.toArray(new Integer[0]);
		assertArrayEquals(numbers, actualArray);
	}
	@Test
	void testSort() {
		Integer expected[] = {-20, 7, 10, 30,  50, 100 };
		list.sort();
		assertArrayEquals(expected,
				list.toArray(new Integer[0]));
	}
	@Test
	void testSortPersons() {
		List<Person> persons = new ArrayList<>();
		Person p1 = new Person(123, 25, "Vasya");
		Person p2 = new Person(124, 20, "Asaf");
		Person p3 = new Person(120, 50, "Arkady");
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		Person expected[] = {p3, p1, p2};
		persons.sort();
		assertArrayEquals(expected, persons.toArray(new Person[0]));
	}
	@Test
	void testSortPersonsByAge() {
		List<Person> persons = new ArrayList<>();
		Person p1 = new Person(123, 25, "Vasya");
		Person p2 = new Person(124, 20, "Asaf");
		Person p3 = new Person(120, 50, "Arkady");
		persons.add(p1);
		persons.add(p2);
		persons.add(p3);
		Person expected[] = {p3, p1, p2};
		persons.sort((prs1, prs2) -> Integer.compare(prs2.getAge(), prs1.getAge()));
	
		assertArrayEquals(expected,
				persons.toArray(new Person[0]));
		
	}
	@Test
	void testEvenOddSorting() {
		Integer[] expected = { -20,  10, 30, 50, 100, 7, -17};
		list.add(-17);
		//list.sort((a, b) -> evenOddCompare(a, b));
//			list.sort((a, b) -> {
//				int res = Math.abs(a % 2) - Math.abs(b % 2);
//				if (res == 0) {
//					res = a % 2 == 0 ? a - b : b - a;
//				}
//				return res;
//			});
		list.sort(ArrayListTest::evenOddCompare);
		assertArrayEquals(expected, list.toArray(new Integer[0]));
	}
	@Test
	void testIndexOfPredicate()  {
		assertEquals(1, list.indexOf(a -> a < 0));
		list.add(-17);
		assertEquals(-1, list.indexOf(a -> a % 2 != 0 && a > 7));
	}
	@Test
	void testRemoveIfAll() {
		assertTrue(list.removeIf(a -> true));
		assertEquals(0, list.size());
	}
	@Test
	void testRemoveIfPredicate() {
		Integer[] expected = {10, -20,  50, 100, 30};
		assertFalse(list.removeIf(a -> a % 2 != 0
				&& a >= 10));
		assertTrue(list.removeIf(a -> a % 2 != 0));
		runTest(expected);
		
	}
	private void runTest(Integer[] expected) {
		int size = list.size() ;
		Integer [] actual = new Integer[expected.length];
		
		for(int i = 0; i < size; i++) {
			actual[i] = list.get(i);
		}
		assertArrayEquals(expected, actual);
		
	}
	static private int evenOddCompare(Integer a, Integer b) {
		int res = Math.abs(a % 2) - Math.abs(b % 2);
		if (res == 0) {
			res = a % 2 == 0 ? a - b : b - a;
		}
		return res;
	}
}
