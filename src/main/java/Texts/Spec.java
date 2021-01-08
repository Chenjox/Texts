package Texts;

/**
 * A contract specifying how an object should be built.
 * A functional Interface for creating specs, essentially a builder
 * without explicitly using the "<tt style="color: #FF6347">new</tt>" keyword or "<tt style="color: #FFA500">build</tt><tt>()</tt>" method.
 * @param <T> the type of the resulting object.
 */
@FunctionalInterface
public interface Spec<T> {
    /**
     * @return the resulting object.
     */
    T create();
}
