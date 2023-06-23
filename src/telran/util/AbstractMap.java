package telran.util;

import java.util.stream.Stream;

public abstract class AbstractMap<K, V> implements Map<K, V> {
	protected Set<Entry<K, V>> set;
	@Override
	public V get(K key) {
		Entry<K, V> entry = set.get(new Entry<>(key, null));
		return entry == null ? null : entry.getValue();
	}

	@Override
	public V put(K key, V value) {
		Entry<K, V> entry = set.get(new Entry<>(key, null));
		V res = null;
		if (entry != null) {
			res = entry.getValue();
			entry.setValue(value);
		} else {
			set.add(new Entry<>(key, value));
		}
		return res;
	}

	@Override
	public boolean containsKey(K key) {
		Entry<K, V> entryPattern = new Entry<>(key, null);
		return set.contains(entryPattern);
	}

	@Override
	public boolean containsValue(V value) {
		Stream<Entry<K,V>> stream  = set.stream();
		boolean res = stream.anyMatch(e -> e.getValue().equals(value));
		//return set.stream().anyMatch(e -> e.getValue().equals(value));
		return res;
	}

	@Override
	public Set<K> keySet() {
		Set<K> res = getKeySet();
		set.stream().map(e -> e.getKey()).forEach(key -> res.add(key));
		return res;
	}

	abstract protected Set<K> getKeySet();

	@Override
	public Collection<V> values() {
		List<V> res = new ArrayList<V>();
		set.stream().map(e -> e.getValue()).forEach(v -> res.add(v));
		return res;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		
		return set;
	}
	@Override
	public V remove(K key) {
		Entry<K, V> entryPattern = new Entry<>(key, null);
		Entry<K, V> entry = set.get(entryPattern);
		V res = null;
		
		if (entry != null) {
			res = entry.getValue();
			set.remove(entry);
		}

		return res;
	}
}
