package tree.bst;

import list.List;
import set.Result;

import static list.List.*;
import static list.List.Cons.foldL;

public abstract class Tree<A extends Comparable<A>> {
    @SuppressWarnings("rawtypes")
    private static final Tree EMPTY = new Empty();

    public abstract A value();
    abstract Tree<A> left();
    abstract Tree<A> right();
    public abstract Tree<A> insert(A a);
    public abstract boolean member(A a);
    public abstract int size();
    public abstract int height();
    protected abstract Tree<A> removeMerge(Tree<A> ta);
    public abstract boolean isEmpty();
    public abstract Tree<A> delete(A a);
    public abstract A findEq(A x);
    public abstract A findMax();
    public abstract A findMin();
    public abstract List<A> preorder();
    public abstract List<A> inorder();
    public abstract List<A> postorder();
    public abstract int sizeLeaf();
    public abstract int sizeInner();
    public abstract int sizeHalf();
    public abstract int sizeFull();
    public abstract int sizeEmpty();
    public abstract Result<A> lookupEq(A x);

    public abstract Result<A> lookupMax();
    public abstract Result<A> lookupMin();


    @SuppressWarnings("unchecked")
    public static <A extends Comparable<A>> Tree<A> empty() {
        return EMPTY;
    }

    public static <A extends Comparable<A>> Tree<A> tree(List<A> list) {
        return foldL(t -> s -> t.insert(s) ,empty(), list);
    }
    @SafeVarargs
    public static <A extends Comparable<A>> Tree<A> tree(A... as) {
        return tree(Cons.list(as));
    }

    private static class Empty<A extends Comparable<A>> extends Tree<A> {
        @Override
        public A value() {
            throw new IllegalStateException("value() called on empty");
        }
        @Override
        Tree<A> left() {
            throw new IllegalStateException("left() called on empty");
        }
        @Override
        Tree<A> right() {
            throw new IllegalStateException("right() called on empty");
        }

        @Override
        public String toString() {
            return "E";
        }

        @Override
        public Tree<A> insert(A a) {
            return new T<>(empty(), a, empty());
        }

        @Override
        public boolean member(A a) {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public int height() {
            return -1;
        }

        @Override
        protected Tree<A> removeMerge(Tree<A> ta) {
            return ta;
        }

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public Tree<A> delete(A a) {
            return this;
        }

        @Override
        public A findEq(A x) {
            return null;
        }

        @Override
        public A findMax() {
            throw new IllegalStateException("empty tree");
        }

        @Override
        public A findMin() {
            throw new IllegalStateException("empty tree");
        }

        @Override
        public List<A> preorder() {
            return list();
        }

        @Override
        public List<A> inorder() {
            return list();
        }

        @Override
        public List<A> postorder() {
            return list();
        }

        @Override
        public int sizeLeaf() {
            return 0;
        }

        @Override
        public int sizeInner() {
            return 0;
        }

        @Override
        public int sizeHalf() {
            return 0;
        }

        @Override
        public int sizeFull() {
            return 0;
        }

        @Override
        public int sizeEmpty() {
            return 1;
        }

        @Override
        public Result<A> lookupEq(A x) {
            return Result.empty();
        }

        @Override
        public Result<A> lookupMax() {
            return Result.empty();
        }

        @Override
        public Result<A> lookupMin() {
            return Result.empty();
        }


    }
    private static class T<A extends Comparable<A>> extends Tree<A> {
        private final Tree<A> left;
        private final Tree<A> right;
        private final A value;
        private T(Tree<A> left, A value, Tree<A> right) {
            this.left = left;
            this.right = right;
            this.value = value;
        }
        @Override
        public A value() {
            return value;
        }
        @Override
        Tree<A> left() {
            return left;
        }
        @Override
        Tree<A> right() {
            return right;
        }

        @Override
        public String toString() {
            return String.format("(T %s %s %s)", left, value, right);
        }

        @Override
        public Tree<A> insert(A insertedValue) {
            return insertedValue.compareTo(this.value) < 0
                    ? new T<>(left.insert(insertedValue), this.value, right)
                    : insertedValue.compareTo(this.value) > 0
                    ? new T<>(left, this.value, right.insert(insertedValue))
                    : new T<>(this.left, insertedValue, this.right);
        }

        @Override
        public boolean member(A value) {
            return value.compareTo(this.value) < 0
                    ? left.member(value)
                    : value.compareTo(this.value) <= 0 || right.member(value);
        }

        @Override
        public int size() {
            return 1 + left.size() + right.size();
        }

        @Override
        public int height() {
            return 1 + Math.max(left.height(), right.height());
        }

        @Override
        protected Tree<A> removeMerge(Tree<A> ta) {
            if (ta.isEmpty()) {
                return this;
            }
            if (ta.value().compareTo(value) < 0) {
                return new T<>(left.removeMerge(ta), value, right);
            } else if (ta.value().compareTo(value) > 0) {
                return new T<>(left, value, right.removeMerge(ta));
            }
            throw new IllegalStateException("We shouldn't be here");
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Tree<A> delete(A a) {
            if (a.compareTo(this.value) < 0) {
                return new T<>(left.delete(a), value, right);
            } else if (a.compareTo(this.value) > 0) {
                return new T<>(left, value, right.delete(a));
            } else {
                return left.removeMerge (right);
            }
        }

        @Override
        public A findEq(A x) {
            if (x.compareTo(this.value) < 0) {
                return this.left.findEq(x);
            } else if (x.compareTo(this.value) > 0) {
                return this.right.findEq(x);
            } else if (x.compareTo(this.value) == 0){
                return x;
            } else {
                return null;
            }
        }

        @Override
        public A findMax() {
            return this.right().isEmpty() ? this.value : this.right().findMax();
        }

        @Override
        public A findMin() {
            return this.left().isEmpty() ? this.value : this.left().findMin();
        }

        @Override
        public List<A> preorder() {
            return append(left().preorder(), right().preorder()).cons(this.value());
        }

        @Override
        public List<A> inorder() {
            return append(left().inorder(),append(Cons.list(this.value()), right().inorder()));
        }

        @Override
        public List<A> postorder() {
            return append(left().postorder(),append(right().postorder(), Cons.list(this.value())));
        }

        @Override
        public int sizeLeaf() {
            if(left.isEmpty() && right.isEmpty()) return 1;
            return left.sizeLeaf() + right.sizeLeaf();
        }

        @Override
        public int sizeInner() {
            if(right.isEmpty() && left.isEmpty()) return 0;
            return 1 + left.sizeInner() + right.sizeInner();
        }

        @Override
        public int sizeHalf() {
            if((!right.isEmpty() && left.isEmpty()) || (right.isEmpty() && !left.isEmpty()))
                return left.sizeHalf() + 1 + right.sizeHalf();
            return left.sizeHalf() + right.sizeHalf();
        }

        @Override
        public int sizeFull() {
            if((!right.isEmpty() && !left.isEmpty()))
                return left.sizeFull() + 1 + right.sizeFull();
            return left.sizeFull() + right.sizeFull();
        }

        @Override
        public int sizeEmpty() {
            return left.sizeEmpty() + right.sizeEmpty();
        }

        @Override
        public Result<A> lookupEq(A x) {
            return findEq(x) == null ? Result.empty() : Result.success(x);
        }

        @Override
        public Result<A> lookupMax() {
            return right.lookupMax().orElse(() -> Result.success(value));
        }

        @Override
        public Result<A> lookupMin() {
            return left.lookupMin().orElse(() -> Result.success(value));
        }
    }
}