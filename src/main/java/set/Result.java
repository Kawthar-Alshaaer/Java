package set;

import list.Function;

import java.io.Serializable;
import java.util.function.Supplier;

public abstract class Result<V> implements Serializable {
    @SuppressWarnings("rawtypes")
    private static Result empty = new Empty();
    public abstract V getOrElse(final V defaultValue);
    public abstract V getOrElse(final Supplier<V> defaultValue);
    public abstract <U> Result<U> map(Function<V, U> f);
    public abstract <U> Result<U> flatMap(Function<V, Result<U>> f);

    public Result<V> orElse(Supplier<Result<V>> defaultValue) {
        return map(x -> this).getOrElse(defaultValue);
    }

    public boolean exists(Function<V, Boolean> p) {
        return map(p).getOrElse(false);
    }

    private Result() {
    }

    private static class Empty<A> extends Result<A> {
        public Empty() {
            super();
        }

        public A getOrElse(final A defaultValue) {
            return defaultValue;
        }

        @Override
        public A getOrElse(Supplier<A> defaultValue) {
            return defaultValue.get();
        }

        public <U> Result<U> map(Function<A, U> f) {
            return empty();
        }

        public <U> Result<U> flatMap(Function<A, Result<U>> f) {
            return empty();
        }
        @Override
        public String toString() {
            return "Empty()";
        }

    }

    private static class Failure<A> extends Result<A> {
        private final RuntimeException exception;
        private Failure(String message) {
            super();
            this.exception = new IllegalStateException(message);
        }
        private Failure(RuntimeException e) {
            super();
            this.exception = e;
        }
        private Failure(Exception e) {
            super();
            this.exception = new IllegalStateException(e.getMessage(), e);
        }
        @Override
        public String toString() {
            return String.format("Failure(%s)", exception.getMessage());
        }

        public A getOrElse(A defaultValue) {
            return defaultValue;
        }
        public A getOrElse(Supplier<A> defaultValue) {
            return defaultValue.get();
        }
        public <U> Result<U> map(Function<A, U> f) {
            return failure(exception);
        }
        public <U> Result<U> flatMap(Function<A, Result<U>> f) {
            return failure(exception);
        }
    }

    private static class Success<A> extends Result<A> {
        private final A value;
        private Success(A value) {
            super();
            this.value = value;
        }
        @Override
        public String toString() {
            return String.format("Success(%s)", value.toString());
        }

        public A getOrElse(A defaultValue) {
            return value;
        }
        public A getOrElse(Supplier<A> defaultValue) {
            return value;
        }
        public <U> Result<U> map(Function<A, U> f) {
            try {
                return success(f.apply(this.value));
            } catch (Exception e) {
                return failure(e.getMessage());
            }
        }
        public <U> Result<U> flatMap(Function<A, Result<U>> f) {
            try {
                return f.apply(this.value);
            } catch (Exception e) {
                return failure(e.getMessage());
            }
        }
    }
    public static <V> Result<V> failure(String message) {
        return new Failure<>(message);
    }
    public static <V> Result<V> failure(Exception e) {
        return new Failure<V>(e);
    }
    public static <V> Result<V> failure(RuntimeException e) {
        return new Failure<V>(e);
    }
    public static <V> Result<V> success(V value) {
        return new Success<>(value);
    }

    @SuppressWarnings("unchecked")
    public static <V> Result<V> empty() {
        return empty;
    }

}
