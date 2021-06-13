package set;

import list.Function;
import list.List;

public interface Set<A> {
    Set<A> insert(A e);
    Set<A> delete(A e);
    boolean member(A x);
    int size();
    boolean isSubsetOf(Set<A> s);
    boolean isEmpty();
    A findEq(A e);
    List<A> toList();
    boolean isEqualTo(Set<A> set);
    boolean any(Function<A, Boolean> p);
    boolean all(Function<A, Boolean> p);
    Result<A> lookupEq(A x);
    <B> B foldr(Function<A, Function<B, B>> f, B s);
    <B> B foldl(Function<B, Function<A, B>> f, B s);
    <A, B> Set<B> map(Function<A, B> f, Set<A> xs);


}
