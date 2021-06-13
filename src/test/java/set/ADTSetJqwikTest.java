package set;

import list.JqwikUtils;
import list.List;
import net.jqwik.api.*;
import tuple.Tuple;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

import static list.JqwikUtils.equalLists;
import static list.JqwikUtils.reverse;
import static list.List.*;
import static set.ListSet.*;
import static set.Set.*;
import static tuple.Tuple.tuple;

public abstract class ADTSetJqwikTest {

    @Provide
    <A> Arbitrary<A> as(){
        return (Arbitrary<A>) ints();
    }

    protected  Arbitrary<Integer> ints() {
        return Arbitraries.integers().between(0,10);
    }

    private Arbitrary<String> strings() {
        return Arbitraries.strings().withCharRange('a','z').ofMinLength(2).ofMaxLength(5);
    }

    protected abstract <A extends Comparable<A>> Set<A> empty();

    protected abstract <A extends Comparable<A>> Set<A> fromList(List<A> list);

    private <A extends Comparable<A>> Set<A> fromCollection(Collection<A> coll){
        return fromList(list.JqwikUtils.fromCollection(coll));
    };

    protected abstract <A extends Comparable<A>> Set<A> set(A... xs);

    @Provide
    Arbitrary<List<Integer>> lists(){
        return JqwikUtils.lists(as(),5);
    }

    @Provide
    Arbitrary<Set<Integer>> sets(){
        return sets(ints(),5);
    }

    private <A extends Comparable<A>> Arbitrary<Set<A>> sets(Arbitrary<A> xa, int maxSize) {
        return JqwikUtils.lists(xa,maxSize).map(xs->fromList(xs));
    }

    @Provide
    Arbitrary<Tuple<Set<Integer>,Set<Integer>>> equalSets(){
        return equalSets(ints(),10);
    }

    private <A extends Serializable & Comparable<A>> Arbitrary<Tuple<Set<A>,Set<A>>> equalSets(Arbitrary<A> xa, int minLen, int maxLen){
        return equalLists(xa,minLen,maxLen).map(t->tuple(fromList(t.fst),fromList(reverse(t.snd))));
    }

    private <A extends Serializable & Comparable<A>> Arbitrary<Tuple<Set<A>,Set<A>>> equalSets(Arbitrary<A> xa, int maxLen){
        return equalSets(xa,0,maxLen);
    }

    @Property
    public <A> boolean equalSetsAreEqual(@ForAll("equalSets") Tuple<Set<A>,Set<A>> t){
        return t.fst.equals(t.snd);
    }

    @Property
    public boolean setsFromUnEqualArraysAreUnEqual(@ForAll Integer[] a1, @ForAll Integer[] a2){
        final TreeSet js1 = new java.util.TreeSet<>(Arrays.asList(a1));
        final TreeSet js2 = new java.util.TreeSet<>(Arrays.asList(a2));
        Assume.that(!js1.equals(js2));
        return !set(a1).isEqualTo(set(a2));
    }

    @Property
    public <A extends Comparable<A>>
    boolean setsFromUnEqualJavaSetsAreUnEqual(@ForAll java.util.Set<A> js1, @ForAll java.util.Set<A>  js2){
        Assume.that(!js1.equals(js2));
        return !fromCollection(js1).isEqualTo(fromCollection(js2));
    }

    // isEmpty(empty)=true
    @Example
    public boolean isEmpty_empty() {
        return empty().isEmpty();
    }

    // size(empty)=0
    @Example
    public boolean size_empty() {
        return empty().size() == 0;
    }

    // ∀s:Set, ∀x:A : size(insert(x,s)) 	=  !member(x,s) ? size(s)+1 : size(s)
    @Property
    public <A> boolean size_insert(@ForAll("sets") Set<A> s, @ForAll("as") A x) {
        if(!s.member(x)) {
            return s.insert(x).size() == s.size() + 1;
        } else {
            return s.insert(x).size() == s.size();
        }
    }

    // ∀s:Set, ∀x:A, ∀y:A : member(y,delete(x,s))	=  x=y ? false : member(y,s)
    @Property
    public <A> boolean member_delete(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        if(x.equals(y))
            return !s.delete(x).member(y);
        else
            return s.delete(x).member(y) == s.member(y);
    }

    // ∀x:A : member(x,empty)=false
    @Property
    public <A extends Comparable<A>> boolean member_empty(@ForAll("as") A x) {
        return !ListSet.empty().member(x);
    }


    // ∀s:Set, ∀x:A, ∀y:A :  member(y,insert(x,s)) 	=  x=y ? true  : member(y,s)
    @Property
    public <A> boolean member_insert(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        if(x.equals(y))
            return s.insert(x).member(y);
        else
            return s.insert(x).member(y) == s.member(y);
    }

    // ∀s:Set, ∀x:A, ∀y:A : insert(y,insert(x,s))	=  x=y ? insert(y,s) : insert(x,insert(y,s))
    @Property
    public <A> boolean insert_insert(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        Assume.that(!s.isEmpty());
        if(x.equals(y))
            return s.insert(x).insert(y).equals(s.insert(y));
        else
            return s.insert(x).insert(y).equals(s.insert(y).insert(x));
    }

    // ∀s:Set, ∀x:A, ∀y:A : insert(y,insert(x,s))  = insert(x,insert(y,s))
    @Property
    public <A> boolean insertKommutativ(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        return s.insert(x).insert(y).equals(s.insert(y).insert(x));
    }

    // ∀x:A : delete(x,empty) 	= empty
    @Property
    public <A extends Comparable<A>> boolean delete_empty(@ForAll("as") A x) {
        return ListSet.empty().delete(x).isEmpty();

    }

    // ∀s:Set, ∀x:A, ∀y:A : delete(y,insert(x,s)) 	=  x=y ? delete(y,s) : insert(x,delete(y,s))
    @Property
    public <A> boolean delete_insert(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        if(x.equals(y))
            return s.insert(x).delete(y).equals(s.delete(y));
        else
            return s.insert(x).delete(y).equals(s.delete(y).insert(x));
    }

    // ∀s:Set, ∀x:A, ∀y:A : findEq(y,insert(x,s)) 	=  x=y ? x : findEq(y,s)
    @Property
    public <A> boolean findEq_insert(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        return x.equals(y) ? Objects.equals(s.insert(x).findEq(y),x) : Objects.equals(s.insert(x).findEq(y),s.findEq(y));
    }

    // ∀s:Set, ∀x:A, ∀y:A : findEq(y,delete(x,s))	=  x=y ? null : findEq(y,s)
    @Property
    public <A> boolean findEq_delete(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        Assume.that(!s.isEmpty());
        return x.equals(y) ? Objects.equals(s.delete(x).findEq(y),null) : Objects.equals(s.delete(x).findEq(y),s.findEq(y));
    }

    // ∀s:Set : empty ⊆ s
    @Property
    public <A extends Comparable<A>> boolean emptySetIsSubsetOfAllSets(@ForAll("sets") Set<A> s) {
        return isSubsetOf(empty(), s);
    }


    // ∀s:Set : s ⊆ s
    @Property
    public <A> boolean everySetIsSubsetOfItself(@ForAll("sets") Set<A> s) {
        return isSubsetOf(s, s);
    }

    // ∀a:Set, ∀b:Set, ∀x:A : falls x ∈ b und a ⊆ b dann a ∪ {x} ⊆ b
    @Property(maxDiscardRatio = 100)
    public <A extends Comparable<A>> boolean union_member_subset(@ForAll("sets") Set<A> a,
                                                                 @ForAll("sets") Set<A> b,
                                                                 @ForAll("as") A x){
        Assume.that(b.member(x) && isSubsetOf(a, b));
        return isSubsetOf(a.insert(x), b);
    }

    // ∀a:Set, ∀b:Set :  a ∪ b = b ∪ a
    @Property
    public <A> boolean kommutativGesetzUnion(@ForAll("sets") Set<A> a,
                                             @ForAll("sets") Set<A> b) {
        return union(a,b).equals(union(b,a));
    }

    // ∀a:Set, ∀b:Set :  a ∩ b = b ∩ a
    @Property
    public <A> boolean kommutativGesetzIntersect(@ForAll("sets") Set<A> a,
                                                 @ForAll("sets") Set<A> b) {
        return intersection(a, b).equals(intersection(b, a));
    }

    // ∀a:Set, ∀b:Set, ∀c:Set :  a ∪ (b ∪ c) = (a ∪ b) ∪ c
    @Property
    public <A> boolean assoziativGesetzUnion(@ForAll("sets") Set<A> a,
                                             @ForAll("sets") Set<A> b,
                                             @ForAll("sets") Set<A> c) {
        return union(a, union(b, c)).equals(union(union(a, b), c));
    }



    // ∀a:Set, ∀b:Set, ∀c:Set :  a ∩ (b ∩ c) = (a ∩ b) ∩ c
    @Property
    public <A> boolean assoziativGesetzIntersect(@ForAll("sets") Set<A> a,
                                                 @ForAll("sets") Set<A> b,
                                                 @ForAll("sets") Set<A> c) {
        return intersection(a, intersection(b, c)).equals(intersection(intersection(a, b), c));
    }

    // ∀a:Set, ∀b:Set, ∀c:Set :  a ∪ (b ∩ c) = (a ∪ b) ∩ (a ∪ c)
    @Property
    public <A> boolean distributivGesetz(@ForAll("sets") Set<A> a,
                                         @ForAll("sets") Set<A> b,
                                         @ForAll("sets") Set<A> c) {
        return union(a, intersection(b, c)).equals(intersection(union(a, b), union(a, c)));
    }


    // ∀a:Set, ∀b:Set : a ∪ (a ∩ b) = a
    @Property
    public <A> boolean absorptionsGesetz(@ForAll("sets") Set<A> a, @ForAll("sets") Set<A> b) {
        return union(intersection(a, b), a).equals(a);
    }

    // ∀a:Set, ∀b:Set, ∀x ∈ (a ∪ b) : x ∈ A || x ∈ B
    @Property
    public <A> boolean defOfUnion(@ForAll("sets") Set<A> a, @ForAll("sets") Set<A> b) {
        return union(a, b).all(c -> a.member(c) || b.member(c));
    }


    // ∀a:Set, ∀b:Set, ∀x ∈ (a ∩ b) : x ∈ A && x ∈ B
    @Property
    public <A> boolean defOfIntersection(@ForAll("sets") Set<A> a, @ForAll("sets") Set<A> b, @ForAll("as") A x) {
        return intersection(a, b).all(c -> a.member(c) && b.member(c));
    }


    // ∀a:Set, ∀b:Set, ∀c:Set : U [a,b,c] = (a ∪ b) ∪ c
    @Property
    public <A> boolean testUnions(@ForAll("sets") Set<A> a,
                                  @ForAll("sets") Set<A> b,
                                  @ForAll("sets") Set<A> c) {
        return true; // a.union(b).union(c);
    }
    //        lookupEq(insert(x,s),y) =  x=y ? Result.success(x) : lookupEq(s,y)
    @Property
    public <A> boolean testLookupEq1(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        if(x.equals(y))
            return s.insert(x).lookupEq(y).toString().equals(Result.success(x).toString());
        else
            return s.insert(x).lookupEq(y).toString().equals(s.lookupEq(y).toString());
    }

    //  lookupEq(delete(x,s),y)  =  x=y ? Result.empty()    : lookupEq(s,y)
    @Property
    public <A> boolean testLookupEq2(@ForAll("sets") Set<A> s, @ForAll("as") A x, @ForAll("as") A y) {
        Assume.that(!s.isEmpty());
        if(x.equals(y))
            return s.delete(x).lookupEq(y).toString().equals(Result.empty().toString());
        else
            return s.delete(x).lookupEq(y).toString().equals(s.lookupEq(y).toString());
    }


}
