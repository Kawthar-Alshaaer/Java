package queue;

import list.JqwikUtils;
import list.List;
import net.jqwik.api.*;
import tuple.Tuple;

import java.io.Serializable;
import java.util.Arrays;

import static list.JqwikUtils.equalLists;
import static list.List.list;
import static org.junit.jupiter.api.Assertions.*;
import static tuple.Tuple.tuple;

public abstract class ADTQueueJqwikTest {

    final int maxSize = 10;
    protected abstract <A> Queue<A> empty();

    private <A> Queue<A> queue(A... data){
        return queue(List.Cons.list(data));
    }

    private <A> Queue<A> queue(List<A> data){
        return this.<A>empty().enQueueAll(data);
    }

    @Provide
    <A> Arbitrary<A> as(){
        return (Arbitrary<A>) ints();
    }

    private Arbitrary<Integer> ints() {
        return Arbitraries.integers().between(0,100);
    }

    private Arbitrary<String> strings() {
        return Arbitraries.strings().withCharRange('a','z').ofMinLength(2).ofMaxLength(5);
    }

    @Provide
    <A> Arbitrary<Queue<A>> queues(){
        return queues(as(),maxSize);
    }

    private <A> Arbitrary<Queue<A>> queues(Arbitrary<A> xa, int maxSize) {
        return JqwikUtils.lists(xa,maxSize).map(xs->queue(xs));
    }

    @Provide
    <A extends Serializable> Arbitrary<Tuple<Queue<A>,Queue<A>>> equalQueues(){
        return equalQueues(as(),maxSize);
    }

    private <A extends Serializable> Arbitrary<Tuple<Queue<A>,Queue<A>>> equalQueues(Arbitrary<A> xa, int maxSize){
        return equalLists(xa,maxSize).map(t->tuple(queue(t.fst),queue(t.snd)));
    }

    @Provide
    Arbitrary<List<Integer>> lists(){
        return JqwikUtils.lists(as(),5);
    }

    @Property // nicht aendern!
    public boolean queuesFromUnEqualArraysAreUnEqual(@ForAll Integer[] a1, @ForAll Integer[] a2){
        Assume.that(!Arrays.equals(a1,a2));
        return !queue(a1).equals(queue(a2));
    }

    @Property // nicht aendern!
    public <A> boolean queuesFromUnEqualJavaListsAreUnEqual(@ForAll java.util.List<A> a1, @ForAll java.util.List<A> a2){
        Assume.that(!a1.equals(a2));
        return !queue(a1).equals(queue(a2));
    }

    @Property  // nicht aendern!
    public <A> boolean equalQueuesAreEqual(@ForAll("equalQueues") Tuple<Queue<A>,Queue<A>> t){
        return t.fst.equals(t.snd);
    }

    @Property // nicht aendern!
    public boolean testToList(@ForAll Integer[] a){
        return List.Cons.list(a).isEqualTo(queue(a).toList());
    }

    // isEmpty(empty) = true
    @Example
    public boolean isEmpty_empty(){
        return false;
    }

    // ∀q:Queue<A>, ∀x:A : isEmpty(enQueue(x,q)) = false
    @Property
    public <A> boolean isEmpty_enQueue(@ForAll("queues") Queue<A> q, @ForAll("as") A x) {
        return false;
    }

    // ∀x:A : deQueue(enQueue(x,empty)) = empty
    @Property
    public <A> boolean deQueue_enQueue_empty(@ForAll("as") A x) {
        return false;
    }

    // ∀x:A : front(enQueue(x,empty)) = x
    @Property
    public <A> boolean front_enQueue_empty(@ForAll("as") A x) {
        return false;
    }

    // ∀q:Queue<A>, ∀x:A, ∀y:A : front(enQueue(y,enQueue(x,q))) = front(enQueue(x, q))
    @Property
    public <A> boolean front_enQueue_enQueue(@ForAll("queues") Queue<A> q, @ForAll("as") A x, @ForAll("as") A y){
        return false;
    }

    // ∀q:Queue<A>, ∀x:A, ∀y:A : deQueue(enQueue(y,enQueue(x,q)))	= enQueue(y,deQueue(enQueue(x,q)))
    @Property
    public <A> boolean deQueue_enQueue_enQueue(@ForAll("queues") Queue<A> q, @ForAll("as") A x, @ForAll("as") A y){
        return false;
    }

    // ∀q:Queue<A> : deQueueFront(q)) = front(q), deQueue(q), falls q nicht leer
    @Property
    public <A> boolean deQueueFront(@ForAll("queues") Queue<A> q){
        return false;
    }

    // ∀q:Queue<A> : enQueue(deQueueFront(q) = deQueue(enQueue(front(q),q)), falls q nicht leer
    @Property
    public <A> boolean enQueue_deQueueFront(@ForAll("queues") Queue<A> q){
        return false;
    }

    // ∀q : Queue<A> enqueueAll([],q) = q
    @Property
    public <A> boolean equeueAll(@ForAll("queues") Queue<A> q){
        return false;
    }

    // ∀q : Queue<A>, ∀xs : List<A>  enqueueAll(x:xs,q) = enqueueAll(xs, enqueue(x,q)), falls xs nicht leer
    @Property
    public <A> boolean enqueueAll(@ForAll("queues") Queue<A> q, @ForAll("lists") List<A> xs) {
        return false;
    }

    // toList(empty) = []
    @Example
    public boolean toList() {
        return false;
    }

    // ∀q : Queue<A>, ∀x : A   toList(enqueue(x,q)) = toList(q) ++ [x]
    @Property
    public <A> boolean toList(@ForAll("queues") Queue<A> q, @ForAll("as") A x){
        return false;
    }

    // dequeue(empty)	= error
    @Example
    public void dequeue_empty() {
        fail();
    }

    // front(empty)	= error
    @Example
    public void front_empty() {
        fail();
    }
}
