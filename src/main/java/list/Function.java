package list;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import list.Function;

public interface Function <T , U> {
    U apply(T arg);


}