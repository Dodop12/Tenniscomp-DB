package tenniscomp.utils;

/**
 * A simple record class representing an immutable pair of two objects.
 * 
 * @param <X> The type of the first generic element
 * @param <Y> The type of the second generic element
 * @param x   The value of the first element
 * @param y   The value of the second element
 */
public record Pair<X, Y>(X x, Y y) {

}
