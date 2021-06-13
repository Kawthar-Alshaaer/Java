package stack;


import list.Function;
import list.List;
import stack.ListStack;
import stack.Stack;

import static list.List.Cons.words;
import static stack.ListStack.empty;

public class Postfix {

    public static final Function<Double, Function<Double, Double>> add = a -> b -> a + b;
    public static final Function<Double, Function<Double, Double>> sub = a -> b -> a - b;
    public static final Function<Double, Function<Double, Double>> mul = a -> b -> a * b;
    public static final Function<Double, Function<Double, Double>> div = a -> b -> a / b;
    public static final Function<Double, Function<Double, Double>> pow = a -> b -> Math.pow(a, b);
    public static double fact(double n) {
        if(n < 0) return 0;
        return (n == 1) ? 1: n * fact(n - 1);
    }
    public static final Function<Double, Double> fact = a -> fact(a);


    public static double eval_(Stack<Double> s, List<String> expr) {
        if(expr.isEmpty()) return s.top();
        if(expr.head().equals("+")) {
            Double tmp = add.apply(s.top()).apply(s.pop().top());
            return eval_(s.pop().pop().push(tmp), expr.tail());
        }
        if(expr.head().equals("-")) {
            Double tmp = sub.apply(s.pop().top()).apply(s.top());
            return eval_(s.pop().pop().push(tmp), expr.tail());
        }
        if(expr.head().equals("*")) {
            Double tmp = mul.apply(s.top()).apply(s.pop().top());
            return eval_(s.pop().pop().push(tmp), expr.tail());
        }
        if(expr.head().equals("/")) {
            Double tmp = div.apply(s.pop().top()).apply(s.top());
            return eval_(s.pop().pop().push(tmp), expr.tail());
        }
        if(expr.head().equals("^")) {
            Double tmp = pow.apply(s.pop().top()).apply(s.top());
            return eval_(s.pop().pop().push(tmp), expr.tail());
        }
        if(expr.head().equals("!")) {
            Double tmp = fact.apply(s.top());
            return eval_(s.pop().push(tmp), expr.tail());
        }
        return eval_(s.push(Double.parseDouble(expr.head())), expr.tail());
    }

    public static double eval(String expr) {
        List<String> liststr = words(expr);
        return eval_(empty(), liststr);
    }


    public static void main(String[] args) {
        System.out.println(eval("9 10 -"));
    }
}
