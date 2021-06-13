package map;

import fpinjava.Function;
import list.List;
import set.ListSet;
import set.Set;
import tuple.Tuple;

import static list.List.list;

public class ListMap<K,V> implements Map<K,V> {

    private final Set<Entry<K, V>> set;

    private ListMap() {
        set = ListSet.empty();
    }

    private ListMap(Set<Entry<K, V>> s) {
        set = s;
    }

    public Set<Entry<K, V>> entrySet() {
        return set;
    }

    public <K1,V1> Map<K1,V1> fromEntrySet(Set<Entry<K1,V1>> s) {
        return new ListMap<>(s);
    }

    public V get(K key) {
        return this.toList().filter(tuple -> tuple.fst == key).head().snd;
    }

    public List<Tuple<K,V>> toList() {
        return set.map(x -> Tuple.tuple(x.key, x.value), set).toList();
    }

    public List<K> keys() {
        return set.map(entry -> entry.key, set).toList();
    }

    public Set<K> keysSet(){
        return set.map(entry -> entry.key, set);
    }
    public List<V> elems() {
        return set.map(entry -> entry.value, set).toList();
    }

    public static <K, V> Map<K, V> empty() {
        return new ListMap<>();
    }

    public <K, V> Map<K, V> fromList(List<Tuple<K, V>> list) {
        return new ListMap<>(list.map(x -> Entry.entry(x.fst, x.snd)).toSet());
    }

    public static Map<String, Integer> wordMap(String s) {
        return new ListMap<>();
    }

    public boolean isEqualTo(Map<K, V> o) {
        return false;
    }

    public Map<K, V> insert(K key, V value) {
        return new ListMap<K, V>(set.insert(new Entry<>(key, value)));
    }
    public Map<K, V> insertWith(Function<V, Function<V, V>> f, K key, V value) {
        return new ListMap<>();
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean all(Function<K,Function<V, Boolean>> p) {
        return false;
    }

    public boolean isSubmapOf(Map<K,V> m) {
        return false;
    }

    public boolean allKeys(Function<K, Boolean> p) {
        return false;
    }


    public String toString() {
        return this.set.toString();
    }




    public static void main(String[] args) {
        Set<Entry<Integer, String>> set = ListSet.set(Entry.entry(1,"hello"), Entry.entry(2,"Hi"), Entry.entry(3,"goodbye"));
        ListMap<Integer, String> map = new ListMap<>(set);
        System.out.println(map.get(2));
    }
}
