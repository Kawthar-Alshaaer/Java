package map;

import java.util.Objects;

public class Entry<K,V> implements Comparable<Entry<K,V>> {

	public final K key;
	public final V value;

	public Entry( K key, V value) {
		this.key = Objects.requireNonNull(key);
		this.value = Objects.requireNonNull(value);
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)", key,  value);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Entry))
			return false;
		else {
			@SuppressWarnings("rawtypes")
			Entry that = (Entry) o;
			return key.equals(that.key) && value.equals(that.value);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + key.hashCode();
		result = prime * result + value.hashCode();
		return result;
	}
	public static <K, V> Entry<K, V> entry(K key, V value){
		return new Entry<>(key, value);
	}

	@Override
	public int compareTo(Entry<K,V> other) {
		return this.compareTo(other);
	}


}