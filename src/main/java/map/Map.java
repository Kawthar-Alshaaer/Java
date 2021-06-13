package map;

import fpinjava.Function;
import list.List;
import set.Set;
import tuple.Tuple;

import static tuple.Tuple.tuple;

public interface Map<K,V>  {
  Map<K,V> insert(K key, V value);
  Map<K,V> insertWith(Function<V, Function<V, V>> f, K key, V value);
  boolean isEqualTo(Map<K, V> o) ;
  boolean isEmpty();

}