package set;



import list.Function;
import list.List;

import static list.List.*;
import static list.List.Cons.*;


public class ListSet<A> implements Set<A> {
    private final List<A> list;
    private ListSet() {
        list = list();
    }
    private ListSet(List<A> lis) {
        this.list = lis;
    }

    //O(n)
    @Override
    public Set<A> insert(A e) {
        return new ListSet<>(this.list.delete(e).cons(e));
    }

    //O(n)
    @Override
    public Set<A> delete(A e) {
        return new ListSet<>(this.toList().delete(e));
    }

    //O(n)
    @Override
    public boolean member(A e) {
    //return !this.delete(e).isEqualTo(this);
        return any(x -> x.equals(e));
    }

    //O(n)
    @Override
    public int size() {
        return this.toList().length();
    }

    //O(n) 
    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    //O(n)
    @Override
    public A findEq(A e) {
        return list.finde(n -> n.equals(e));
    }

    //O(1)
    @Override
    public List<A> toList() {
        return this.list;
    }


   public boolean isSubsetOf(Set<A> s) {
       return this.all(s::member);
   }

    public boolean isEqualTo(Set<A> s) {
        return isSubsetOf(s) && s.isSubsetOf(this);}


    //B)	Statische Fabrikmethoden
    public static <A> Set<A> empty() {
        return new ListSet<>();
    }
    public static <A> Set<A> fromList(List<A> xs) {
        return new ListSet<>(xs).foldl(a -> x -> a.insert(x) , empty());
    }
    public static <A> Set<A> fromSet(Set<A> s) {
        return s.equals(new ListSet<>()) ? s: new ListSet<>(s.toList());
    }
    public static <A> Set<A> set(A... es) {
        return fromList(list(es));
    }

    //C)    toString-Methode

    //O(n)
    @Override
    public String toString() {
        return "{" + this.list + '}';
    }

    //D)    Prädikate

    @Override
    public boolean all(Function<A, Boolean> p) {
        return this.toList().all(p);
    }

    @Override
    public boolean any(Function<A, Boolean> p) {
        return this.toList().any(p);
    }

    //D)	Mengenbeziehungen

    public static <A> boolean isSubsetOf(Set<A> xs, Set<A> ys) {
        return xs.all(x -> ys.member(x));
    }

    public static <A> boolean isEqualTo(Set<A> xs, Set<A> ys) {
        return isSubsetOf(xs, ys) && isSubsetOf(ys, xs);}


    public boolean disjoint(Set<A> s) {
        return this.all(n -> !s.member(n));
    }

    //F)	Falten, Filtern und „Mappen“
    public <B> B foldr(Function<A, Function<B, B>> f, B s) {
        return foldR(f, s ,list);
    }

    public  <B> B foldl(Function<B, Function<A, B>> f, B s) {
        return foldL(f, s ,list);
    }

    public static <A> Set<A> filter(Function<A, Boolean> f, Set<A> xs) {
        return new ListSet<> (xs.toList().filter(f));
    }

    public  <A, B> Set<B> map(Function<A, B> f, Set<A> xs) {
        return fromList(xs.toList().map(f));
    }

    // G)	Mengenverknüpfungen
    public static <A> Set<A> union(Set<A> xs, Set<A> ys) {
        return xs.foldr(x -> a -> a.insert(x), ys);
    }

    public static <A> Set<A> intersection(Set<A> xs, Set<A> ys) {

        return filter(x -> ys.member(x),xs);
    }


    //H)     Equals- Methode

    @Override

    public boolean equals(Object o) {
       return (o instanceof Set) && this.isEqualTo((Set<A>) o);}


    //O(n)
    public static Set<String> wordSet(String s) {
        return fromList(words(s));
    }

    public static <A> Set<A> unions(List<Set<A>> xss) {
        return foldL(x -> y -> union(x, y), ListSet.empty(), xss);
    }

    @Override
    public Result<A> lookupEq(A x) {
        return this.list.find(a -> a.equals(x));
    }





    public static void main(String[] args) {
        Set<Integer> a = set(1,2);
        Set<Integer> b = set(1,2);
        Set<Integer> c = set(3,4);
        System.out.println(intersection(b, a));
        System.out.println( b.lookupEq(2));
}



}
