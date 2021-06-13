package set;

import tree.bst.Tree;
import list.Function;
import list.List;

import static list.List.Cons.*;
import static set.ListSet.*;
import static tree.bst.Tree.*;


public class TreeSet<A extends Comparable<A>> implements SortedSet<A> {
    private final Tree<A> tree;
    private TreeSet() {
        this.tree = Tree.empty();
    }
    private TreeSet(Tree<A> tree) {
        this.tree = tree;
    }

    @Override
    public A findMax() {
        return tree.findMax();
    }

    @Override
    public A findMin() {
        return tree.findMin();
    }

    @Override
    public Set<A> insert(A e) {
        return new TreeSet<>(tree.insert(e));
    }

    @Override
    public Set<A> delete(A e) {
        return new TreeSet<>(tree.delete(e));
    }

    @Override
    public boolean member(A x) {
        return this.tree.member(x);
    }

    @Override
    public int size() {
        return this.tree.size();
    }

    @Override
    public boolean isSubsetOf(Set<A> s) {
            return this.all(s::member);

    }

    @Override
    public boolean isEmpty() {
        return this.tree.isEmpty();
    }

    @Override
    public A findEq(A e) {
        return this.tree.findEq(e);
    }

    @Override
    public List<A> toList() {
        return this.tree.preorder();
    }

    @Override
    public boolean isEqualTo(Set<A> set) {
        return this.toList().equals(set.toList());
    }

    @Override
    public boolean any(Function<A, Boolean> p) {
        return this.toList().any(p);
    }

    @Override
    public boolean all(Function<A, Boolean> p) {
        return this.toList().all(p);
    }

    @Override
    public Result<A> lookupEq(A x) {
        return this.tree.lookupEq(x);
    }

    @Override
    public <B> B foldr(Function<A, Function<B, B>> f, B s) {
        return foldR(f,s,this.toList());
    }

    @Override
    public <B> B foldl(Function<B, Function<A, B>> f, B s) {
        return foldL(f,s,this.toList());
    }

    @Override
    public <A1, B> Set<B> map(Function<A1, B> f, Set<A1> xs) {
        return null;
    }

    @Override
    public Result<A> lookupMax() {
        return this.tree.lookupMax();
    }
    @Override
    public Result<A> lookupMin() {
        return this.tree.lookupMin();
    }


    // B) Statische Fabrikmethoden

    public static <A extends Comparable<A>> SortedSet<A> empty() {
        return new TreeSet<>();
    }
    public static <A extends Comparable<A>> SortedSet<A> fromList(List<A> xs) {
        return null;
    }

    public static <A extends Comparable<A>> SortedSet<A> fromSet(Set<A> s) {
        return s.equals(new TreeSet<A>()) ? (SortedSet<A>) s : new TreeSet<A>(tree(s.toList()));
    }

    public static <A extends Comparable<A>> SortedSet<A> set(A... es) {
        return fromList(List.Cons.list(es));
    }

    public static SortedSet<String> wordSet(String s) {
        return fromList(words(s));
    }

    //C) toString- Methode

    @Override
    public String toString() {
        return this.tree.toString();
    }

    //H) Equals- Methode

    @Override

    public boolean equals(Object o) {
        return (o instanceof Set) && this.isEqualTo((Set<A>) o);}

}
