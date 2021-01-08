package Texts;

/**
 * A contract specifying how an object builder should be built.
 * A Interface for all Builders to adhere to the main convention of building an object.
 * @param <T> the type of the resulting object.
 */
public interface Builder<T> {

    /**
     * The final function that creates the object built.
     * @return the built object
     */
    T build();
}
