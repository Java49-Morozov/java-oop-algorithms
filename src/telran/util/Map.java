package telran.util;

import java.util.Objects;

public interface Map<K, V> {
	static class  Entry<K, V> implements Comparable<Entry<K,V>>{
		@Override
		public int hashCode() {
			return Objects.hash(key);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Entry other = (Entry) obj;
			return Objects.equals(key, other.key);
		}
		public V getValue() {
			return value;
		}
		public void setValue(V value) {
			this.value = value;
		}
		public K getKey() {
			return key;
		}
		public Entry(K key, V value) {
			super();
			this.key = key;
			this.value = value;
		}
		private K key;
		private V value;
		@Override
		public int compareTo(Entry<K, V> o) {
			
			return ((Comparable<K>)this.key).compareTo(o.key);
		}
		
	}
	V get(K key);
	default V getOrDefault(K key, V defaultValue) {
		V res = get(key);
		if (res == null) {
			res = defaultValue;
		}
		return res;
	}
	V put(K key, V value);
	default V putIfAbsent(K key, V value) {
		V res = get(key);
		if (res == null) {
			put(key, value);
		}
		return res;
	}
	boolean containsKey(K key);
	boolean containsValue(V value);
	Set<K> keySet();
	Collection<V> values();
	Set<Entry<K, V>> entrySet();
	V remove(K key);
}
