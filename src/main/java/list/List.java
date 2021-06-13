package list;

import set.Result;
import set.Set;

import static set.ListSet.fromList;

public abstract class List<A> {

    // Instanzmethoden
    public abstract int length();

    public abstract boolean elem(A x);

    public abstract boolean any(Function<A, Boolean> f);

    public abstract boolean all(Function<A, Boolean> p);

    public abstract <B> List<B> map(Function<A, B> f);

    public abstract List<A> filter(Function<A, Boolean> f);

    public abstract A finde(Function<A, Boolean> f);

    public abstract Result<A> find(Function<A, Boolean> f);




    // Klassenmethode sum

    public static Integer sum(List<Integer> list) {

        return list.isEmpty() ? 0 : list.head() + sum(list.tail());
    }

    // Klassenmethode prod
    public static Double prod(List<Double> list) {

        return list.isEmpty() ? 1.0 : list.head() * prod(list.tail());
    }

    // Klassenmethode append
    public static <A> List<A> append(List<A> list1, List<A> list2) {

        if (list1 instanceof Nil)
            return list2;

        return new Cons<A>(list1.head(), append(list1.tail(), list2));
    }

    // Klassenmethode concat
    public static <A> List<A> concat(List<List<A>> list) {
        return list.isEmpty() ? list() : append(list.head(), concat(list.tail()));
    }


    // Klassenmethode and
    static Boolean and(List<Boolean> list) {
        return list.isEmpty() ? true : list.head() && and(list.tail());
    }

    // Klassenmethode or
    static Boolean or(List<Boolean> list) {
        return list.isEmpty() ? false : list.head() || or(list.tail());
    }

    // Klassenmethode minimum
    public static Integer minimum(List<Integer> list) {
        if (list.isEmpty())
            throw new IllegalStateException("empty list");
        if (list.length() == 1)
            return list.head();
        if (list.head() < list.tail().head())
            return minimum((list.tail().tail()).cons(list.head()));
        return minimum((list.tail().tail()).cons(list.tail().head()));
    }

    // Klassenmethode maximum
    public static Integer maximum(List<Integer> list) {
        if (list.isEmpty())
            throw new IllegalStateException("empty list");
        if (list.length() == 1)
            return list.head();
        if (list.head() > list.tail().head())
            return maximum((list.tail().tail()).cons(list.head()));
        return maximum((list.tail().tail()).cons(list.tail().head()));
    }
    // Klassenmethode ggt

    public static int ggT(int a, int b) {
        if (a == 0) return b;
        if (b == 0) return a;
        if (a == b) return a;
        if (a > b) return ggT(a - b, b);
        return ggT(b - a, a);
    }

    @SuppressWarnings("rawtypes")
    public static final List NIL = new Nil();

    List() {

    }


    private static class Nil<A> extends List<A> {
        private Nil() {
        }
        // Instanzmethoden Nil

        public int length() {
            return 0;
        }

        public boolean elem(A x) {
            return false;
        }

        public boolean any(Function<A, Boolean> p) {
            return false;
        }

        public boolean all(Function<A, Boolean> p) {
            return true;
        }

        public <B> List<B> map(Function<A, B> f) {
            return list();
        }

        @Override

        public List<A> filter(Function<A, Boolean> f) {
            return list();
        }

        @Override
        public A finde(Function<A, Boolean> f) {
             return null;
        }

        @Override
        public Result<A> find(Function<A, Boolean> f) {
            return Result.empty();
        }
    }

    public Set<A> toSet() {
        return fromList(this);
    }

    public List<A> nub() {
        return this.toSet().toList();
    }


    public List<A> init() {
        throw new IllegalStateException("init called on empty list");
    }

    public A last() {
        throw new IllegalStateException("last called on empty list");
    }

    // Implentation in the Nil for the method take
    public List<A> take(int n) {
        return this;
    }

    // Implentation in the Nil for the method takewhile
    public List<A> takeWhile(Function<A, Boolean> p) {
        return this;
    }

    // Implentation in the Nil for the method dropwhile
    public List<A> dropWhile(Function<A, Boolean> p) {
        return this;
    }

    // Implementation in the Nil for the method drop
    public List<A> drop(int n) {
        return this;
    }

    public List<A> delete(A x) {
        return this;
    }

    public List<A> reverse() {
        return this;
    }

    // Implementation in the Nil for the method equals

    public boolean isEqualTo(List<A> xs) {
//            return xs.toString().equals(this);  kommentar machen
        return xs.isEmpty();
    }

    @SuppressWarnings("unchecked")
    public static <A> List<A> list() {
        return NIL;
    }

    public A head() {
        throw new IllegalStateException("head called en empty list");
    }

    public List<A> tail() {
        throw new IllegalStateException("tail called en empty list");
    }

    public boolean isEmpty() {
        return true;
    }

    public String toString() {
        return "";
    }

    public <B> B foldr(Function<A, Function<B, B>> f, B s) {
        return s;
    }

    public <B> B foldl(Function<B, Function<A, B>> f, B s) {
        return s;
    }

    public List<A> cons(A a) {
        return new Cons<>(a, this);
    }

    // H. Equals-Methode

    public boolean equals(Object o) {
        if (this == o)
            return true;

        return o instanceof Nil;
    }


    public static class Cons<A> extends List<A> {
        private final A head;
        private final List<A> tail;

        Cons(A head, List<A> tail) {
            this.head = head;
            this.tail = tail;
        }

        public A head() {
            return head;
        }

        public List<A> tail() {
            return tail;
        }

        public boolean isEmpty() {
            return false;
        }

        public List<A> setHead(A h) {
            return new Cons<>(h, tail());
        }

        public String toString() {

            String res = "";
            StringBuilder sb = new StringBuilder();
            res = sb.append(head()).append(", ").append(tail()).toString();
            return res;

        }

        /*
         * ! Warum ist die Verwendung der Klasse StringBuilder vorteilhaft?
         *
         * StringBuilder ist schneller und braucht weniger Speischerplatz
         */


        // Instanzmethoden Cons
        public int length() {
            return 1 + tail().length();
        }

        public boolean elem(A x) {
            if (this.head().equals(x)) {
                return true;
            }
            if (!this.tail().isEmpty()) {
                return tail().elem(x);
            } else {
                return false;
            }
        }

        public boolean any(Function<A, Boolean> p) {
            if (p.apply(this.head())) {
                return true;
            } else {
                return this.tail().any(p);
            }
        }

        public boolean all(Function<A, Boolean> p) {

            if (p.apply(head())) {
                return this.tail().isEmpty() ? true : this.tail().all(p);
            } else {
                return false;
            }
        }

        public <B> List<B> map(Function<A, B> f) {
            return new Cons<>(f.apply(head()), tail().map(f));
        }

        @Override
        public List<A> filter(Function<A, Boolean> f) {
            return f.apply(head()) ? new Cons<>(this.head(), this.tail.filter(f)) : this.tail.filter(f);
        }

        @Override

        public A finde(Function<A, Boolean> f) {
            return f.apply(head()) ? this.head : this.tail.finde(f);
        }

        @Override
        public Result<A> find(Function<A, Boolean> f) {
            return this.finde(f) == null ? Result.empty(): Result.success(this.finde(f));
        }

        @Override
        public Set<A> toSet() {
            return fromList(this);
        }

        @Override
        public List<A> init() {
            if (!this.tail.isEmpty()) {
                return new Cons<>(this.head, this.tail.init());
            }
            return list();
        }

        public A last() {
            if (this.length() == 1) {
                return head();
            } else
                return tail().last();
        }

        @Override
        public List<A> take(int n) {
            if (n <= 0)
                return new Cons<>(this.head(), this.tail());
            return new Cons<>(this.head(), this.tail().take(n - 1));
        }

        @Override
        public List<A> drop(int n) {
            if (n > 0)
                return this.tail.drop(n - 1);
            return this;
        }

        @Override
        public List<A> takeWhile(Function<A, Boolean> p) {
            return p.apply(head()) ? new Cons<>(head(), tail().takeWhile(p)) : list();
        }

        @Override
        public List<A> dropWhile(Function<A, Boolean> p) {
            if (p.apply(head))
                return this.tail.dropWhile(p);
            return this;
        }

        public List<A> delete(A d) {
            return this.head().equals(d) ? this.tail.delete(d) : new Cons<>(this.head(), this.tail.delete(d));
        }

        @Override
        public List<A> reverse() {
            return this.isEmpty() ? list() : append(this.tail().reverse(), list(this.head()));

        }

        @Override
        public boolean isEqualTo(List<A> xs) {
            return !xs.isEmpty() && xs.head().equals(this.head) && this.tail().isEqualTo(this.tail());
        }

        public <B> B foldr(Function<A, Function<B, B>> f, B s) {
            return f.apply(head()).apply(foldr(f, s));

        }

        public <B> B foldl(Function<B, Function<A, B>> f, B s) {
            return foldl(f, f.apply(s).apply(head()));
        }

        // H. Equals-Methode
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof Cons))
                return false;
            List<A> xs = (Cons<A>) o;
            return (head().equals(xs.head()) && tail().equals(xs.tail()));

        }


        @SuppressWarnings("unchecked")
        public static <A> List<A> list() {
            return NIL;
        }

        public static <A> List<A> list(A... a) {
            List<A> n = list();
            for (int i = a.length - 1; i >= 0; i--) {
                n = new Cons<>(a[i], n);
            }
            return n;
        }

        public List<A> cons(A a) {
            return new Cons<>(a, this);
        }

        public static <A> List<A> setHead(List<A> list, A h) {
            if (list.isEmpty()) {
                throw new IllegalStateException("setHead called on an empty list");
            } else {
                return new Cons<>(h, list.tail());
            }

        }
        // C. Rechtsfaltung
        // Klassenmethode foldr

        public static <A, B> B foldR(Function<A, Function<B, B>> f, B s, List<A> xs) {
            return xs.isEmpty() ? s : f.apply(xs.head()).apply(foldR(f, s, xs.tail()));
        }

        // sum
        public static Integer sumR(List<Integer> xs) {
            return foldR(x -> y -> x + y, 0, xs);
        }

        // prod
        public static Double prodR(List<Double> xs) {
            return foldR(x -> y -> x * y, 1.0, xs);
        }

        // length
        public static <A> Integer lengthR(List<A> xs) {
            return foldR(x -> n -> n + 1, 0, xs);
        }

        // elem
        public static <A> boolean elemR(A e, List<A> l) {
            return foldR(a -> y -> e.equals(a) || y, false, l);
        }

        // any
        public static <A> boolean anyR(Function<A, Boolean> p, List<A> xs) {
            return foldR(x -> y -> p.apply(x), false, xs);
        }

        // all
        public static <A> boolean allR(Function<A, Boolean> p, List<A> xs) {
            return foldR(x -> y -> p.apply(x), true, xs);
        }

        // and
        public static boolean andR(List<Boolean> list) {
            return foldR(x -> y -> x && y, true, list);
        }

        // or
        public static boolean orR(List<Boolean> list) {
            return foldR(x -> y -> x || y, false, list);
        }

        // append
        public static <A> List<A> appendR(List<A> xs, List<A> ys) {
            return foldR(x -> li -> li.cons(x), ys, xs);
        }

        // concat
        public static <A> List<A> concatR(List<A> xs, List<A> ys) {
            return foldR(x -> li -> li.cons(x), xs, ys);
        }

        // map
        public static <A, B> List<B> mapR(Function<A, B> f, List<A> list) {
            return foldR(x -> xs -> xs.cons(f.apply((A) x)), list(), list);
        }

        // filter
        public static <A> List<A> filterR(Function<A, Boolean> p, List<A> list) {
            return foldR(x -> xs -> p.apply(x) ? xs.cons(x) : xs, list(), list);
        }

        // takeWhile
        public static <A> List<A> takeWhileViaFR(Function<A, Boolean> p, List<A> list) {
            return foldR(x -> xs -> p.apply(x) ? xs.cons(x) : list(), list(), list);
        }

        // toString
        StringBuilder toStringR(List<A> list) {
            return foldR(x -> sb -> sb.insert(0, ", ").insert(0, x), new StringBuilder(), list);
        }


        // D. Linksfaltung

        public static <A, B> B foldL(Function<B, Function<A, B>> f, B s, List<A> xs) {
            return xs.isEmpty() ? s : foldL(f, f.apply(s).apply(xs.head()), xs.tail());
        }

        public <B> B foldL(Function<B, Function<A, B>> f, B s) {
            return foldL(f, s, this);
        }

        // sum
        public static Integer sumL(List<Integer> xs) {
            return foldL(x -> y -> x + y, 0, xs);
        }

        // prod
        public static Double prodL(List<Double> xs) {
            return foldL(x -> y -> x * y, 1.0, xs);
        }

        // length
        public static <A> Integer lengthL(List<A> xs) {
            return foldL(n -> x -> n + 1, 0, xs);
        }

        // elem
        public static <A> boolean elemL(A e, List<A> list) {
            return foldL(y -> a -> e.equals(a) || (Boolean) y, false, list);
        }

        // and
        public static Boolean andL(List<Boolean> list) {
            return foldL(a -> b -> b && a, true, list);
        }

        // or
        public static Boolean orL(List<Boolean> list) {
            return foldL(a -> b -> b || a, true, list);
        }

        // all
        public static <A> boolean allL(Function<A, Boolean> p, List<A> xs) {
            return foldL(y -> x -> p.apply(x), true, xs);
        }

        // any
        public static <A> boolean anyL(Function<A, Boolean> p, List<A> xs) {
            return foldL(y -> x -> p.apply(x), false, xs);
        }

        // last
        public static <A> A lastL(List<A> list) {
            return foldL(y -> x -> x, null, list);
        }

        // reverse
        public static <A> List<A> reverseL(List<A> list) {
            return foldL(xs -> x -> xs.cons(x), list(), list);
        }

        // 3. all and elem implementation using any

        // all
        public static <A> Boolean allany(Function<A, Boolean> func, List<A> list) {

            return !anyL(x -> !func.apply(x), list);
        }

        // elem
        public static <A> Boolean elemany(A elem, List<A> list) {
            return anyR(x -> x.equals(elem), list);
        }

        /*
         * 4. Welchen Vorteil hat die Linksfaltung gegenüber der Rechtsfaltung bei der
         * Linksfaltung steht der Operand an der Linkenseite der List, so durchlaufen
         * wir die List von Anfang an vom Index 0 im Gegenteil yu der Rechtsfaltung, wo
         * der Operand an der Rechtenseite der Liste steht. Die beiden ergebnissen sind
         * identisch, aber bei der Linksfaltung ist die Bearbeitungsdauer schneller.
         */

        // E. concatMap / flatMap

        // concatMap
        public static <A, B> List<B> concatMap(Function<A, List<B>> func, List<A> list) {
            return foldR(x -> y -> append(func.apply(x), y), list(), list);
        }

        // flatMap
        public static <A, B> List<B> flatMap(Function<A, List<B>> f, List<A> list) {
            return foldR(xs -> ys -> append(f.apply(xs), ys), list(), list);
        }

        // F. Statische Fabrikmethoden
        // range method

        @SuppressWarnings({"unchecked", "rawtypes"})
        public static List<Integer> range(int start, int end) {
            return (start > end) ? NIL : new Cons(start, range(start + 1, end));
        }

        // words method
        public static List<String> words(String str) {

            return list(str.trim().split("\\s+"));

        }


        // G. Eulerproblem
        // 1) Lösen Sie mit Hilfe Ihrer Listen-Funktionen das Euler-1-Problem!
        public static Integer Euler1() {
            List<Integer> list = range(1, 999);
            return sum(list.filter(n -> n % 3 == 0 || n % 5 == 0));
        }


    }
}