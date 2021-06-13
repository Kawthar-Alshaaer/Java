package stack;

import list.List;
import tuple.Tuple;

import static list.List.list;
import static tuple.Tuple.tuple;

public class ListStack<A> implements Stack<A>{
    final private List<A> list;

    private ListStack(){
        this.list = List.list();
    }

    private ListStack(List<A> list) {
        this.list = list;
    }

    public static <A> Stack<A> empty() {
        return new ListStack<>();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Stack<A> push(A e) {
        return new ListStack<>(list.cons(e));
    }



    @Override
    public Stack<A> pushAll(List<A> xs) {
        return xs.isEmpty() ? this : new ListStack<A>(List.append(xs, list));
    }

    @Override
    public Stack<A> pushAll(A ...es) {
        return pushAll(List.Cons.list(es));
    }


    @Override
    public Stack<A> pop() {
        if(list.isEmpty()) throw new IllegalStateException("error pop");
        return new ListStack<>(list.tail());
    }

    @Override
    public A top() {
        if(list.isEmpty()) throw new IllegalStateException("error top");
        return list.head();
    }

    @Override
    public Tuple<A, Stack<A>> popTop() {
        if(this.isEmpty()) throw new IllegalStateException("error popTop");
        return new Tuple<>(this.top(), this.pop());
    }


    @Override
    public Tuple<List<A>, Stack<A>> popTopAll() { return null;
    }



    @Override
    public List<A> toList() {
        return this.list;
    }

    @Override
    public boolean isEqualTo(Stack<A> s) {
        return this.toList().equals(s.toList());
    }

    @Override
    public int size() {
        return this.list.length();
    }
    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Stack))
            return false;
        else {
            Stack that = (Stack) o;
            return this.isEqualTo(that);
        }
    }


}
