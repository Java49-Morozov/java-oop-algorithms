package telran.util;

public interface Collection<T> {
	boolean add(T obj);
	int size();
	boolean remove(T pattern);
	T[] toArray(T[] array);
}
