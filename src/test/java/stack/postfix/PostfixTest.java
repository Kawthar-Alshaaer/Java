package stack.postfix;

import org.assertj.core.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static stack.Postfix.*;


import net.jqwik.api.Data;
import net.jqwik.api.ForAll;
import net.jqwik.api.FromData;
import net.jqwik.api.Property;
import net.jqwik.api.Table;
import net.jqwik.api.Tuple;
import net.jqwik.api.Tuple.Tuple2;
public class PostfixTest {
    @Data
    Iterable<Tuple2<String, Double>> Postfix() {
        return Table.of(Tuple.of("3 4 +", 7.0), Tuple.of("2 6 -", -4.0), Tuple.of("10 4 3 + 2 * -", -4.0),
                Tuple.of("90 34 12 33 55 66 + * - + -", 4037.0), Tuple.of("2 2 3 ^ ^", 256.0), Tuple.of("3 ! 4 +", 10.0),
                Tuple.of("4 12 + 4 4 * /", 1.0), Tuple.of("5 3 14 11 2 + * - /", -0.027932960893854747),
                Tuple.of("1 4 8 + /", 0.08333333333333333));
    }

    @Property
    @FromData("Postfix")
    void Postfix_Data_Test(@ForAll String str, @ForAll Double result) {
        assertEquals(eval(str), result);
    }

}
