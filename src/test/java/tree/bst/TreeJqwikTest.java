package tree.bst;

import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import stack.Stack;

import static list.List.list;
import static tree.bst.Tree.tree;
import static tuple.Tuple.tuple;

public class TreeJqwikTest {
  // Hier Data-Driven-Tests für alle Methoden der Klasse Tree programmieren.
  @Property
  <A extends Comparable<A>> boolean insert(){
      return !tree(1).insert(2).isEmpty();
  }

    @Property
    <A extends Comparable<A>> boolean member(){
        return tree(1,2).member(2) && !tree(1,2).member(3);
    }

    @Property
    <A extends Comparable<A>> boolean treefabrik(){
        return tree(1,2).toString().equals(tree(1).insert(2).toString());
    }

    @Property
    <A extends Comparable<A>> boolean size(){
        return tree(1,2,3).size() == 3;
    }

    @Property
    <A extends Comparable<A>> boolean height(){
        return tree(1,2,3).height() == 2;
    }

    @Property
    <A extends Comparable<A>> boolean delete(){
        return tree(1,2,3).delete(1).size() == 2;
    }
    @Property
    <A extends Comparable<A>> boolean findEq(){
        return tree(1,2,3).findEq(1) == 1 && tree(1,2,3).findEq(4) == null;
    }

    @Property
    <A extends Comparable<A>> boolean findMin(){
        return tree(34,12,354).findMin() == 12;
    }

    @Property
    <A extends Comparable<A>> boolean findMax(){
        return tree(34,12,354).findMax() == 354;
    }

    @Property
    <A extends Comparable<A>> boolean minimum(){
        return minimum(list("a","b","c")) == "a";
    }

    @Property
    <A extends Comparable<A>> boolean maximum(){
        return maximum(list("a","b","c")) == "c";
    }

    @Property
    <A extends Comparable<A>> boolean preorder(){
        return tree(1,-1,2,-2,3).preorder().isEqualTo(list(1, -1, -2, 2, 3));
    }
    @Property
    <A extends Comparable<A>> boolean inorder(){
        return tree(1,-1,2,-2,3).inorder().isEqualTo(list(-2, -1, 1, 2, 3));
    }

    @Property
    <A extends Comparable<A>> boolean postorder(){
        return tree(1,-1,2,-2,3).preorder().isEqualTo(list(1, -1, -2, 2, 3));
    }

    @Property
    <A extends Comparable<A>> boolean sizeLeaf(){
        return tree(1,-1,2,-2,3).sizeLeaf() == 2;
    }

    @Property
    <A extends Comparable<A>> boolean sizeInner(){
        return tree(1,-1,2,-2,3).sizeInner() == 3;
    }

    @Property
    <A extends Comparable<A>> boolean sizeHalf(){
        return tree(1,-1,2,-2,3).sizeHalf() == 2;
    }

    @Property
    <A extends Comparable<A>> boolean sizeFull(){
        return tree(1,-1,2,-2,3).sizeFull() == 1;
    }

    @Property
    <A extends Comparable<A>> boolean sizeEmpty(){
        return tree(1,-1,2,-2,3).sizeEmpty() == 6;
    }

}
