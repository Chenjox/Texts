package Texts.Table;

import java.util.Arrays;
import java.util.List;

/**
 * A contract specifying how a table should be built.
 * The number of columns should be decided at the creation time of the table.
 * @param <T> The type of the content objects
 */
public interface Table<T> {

    /**
     * Sets the content of the cell at the specified position.
     * @param Column index of the column
     * @param Row index of the row
     * @param content the new content
     * @throws IndexOutOfBoundsException An exception is thrown if the indices are not valid.
     */
    void setCell(int Column, int Row, T content);

    /**
     * Gets the content of a cell at the specified position
     * @param Column index of the column
     * @param Row index of the row
     * @return the content of the cell
     * @throws IndexOutOfBoundsException An exception is thrown if the indices are not valid.
     */
    T getCell(int Column, int Row);

    /**
     * Returns the number of columns
     * @return The number of columns
     */
    int getColumns();

    /**
     * Sets the content of a whole column at the specified position
     * @param index index of the column
     * @param Column the new column
     * @throws IndexOutOfBoundsException An exception is thrown if the supplied column is bigger than the table size or the index is not valid.
     * @throws IllegalArgumentException An exception is thrown if the supplied list does not match the number of rows
     */
    void setColumn(int index, List<T> Column);

    /**
     * Gets the content of a whole column at the specified position
     * @param index index of the column
     * @return the specified column
     * @throws IndexOutOfBoundsException An exception is thrown if the index is not valid.
     */
    List<T> getColumn(int index);

    /**
     * Returns the number of rows
     * @return the number of rows
     */
    int getRows();

    /**
     * Sets the content of a whole row at the specified position
     * @param index index of the row
     * @param Row the new row
     * @throws IndexOutOfBoundsException An exception is thrown if the supplied row is bigger than the table size or the index is not valid.
     * @throws IllegalArgumentException An exception is thrown if the supplied list does not match the number of columns
     */
    void setRow(int index, List<T> Row);

    /**
     * Gets the content of a whole row at the specified position
     * @param index index of the row
     * @return the specified row
     * @throws IndexOutOfBoundsException An exception is thrown if the index is not valid.
     */
    List<T> getRow(int index);

    /**
     * Adds the row at the end of the table
     * @param Row the row to be added
     * @throws IllegalArgumentException An exception is thrown if the supplied list does not match the number of columns
     */
    void addRow(List<T> Row);

    /**
     * Inserts the row at the specified position. If the row is inserted at index <code>i</code>, it should be between <code>i</code> and <code>i+1</code>.
     * To replace a row use {@link Table#setRow(int, List)} instead.
     * @param index index of the row, after which this row is inserted
     * @param Row the new row
     * @throws IndexOutOfBoundsException An exception is thrown if the the index is not valid.
     * @throws IllegalArgumentException An exception is thrown if the supplied list does not match the number of columns
     */
    void addRow(int index, List<T> Row);

    /**
     * Adds the row at the end of the table
     * Merely a convenience method, it is advised to use {@link Table#addRow(List)} instead;
     * @param Row the row to be added as a comma separated list
     * @throws IllegalArgumentException An exception is thrown if the supplied list does not match the number of columns
     */
    @SuppressWarnings("unchecked")
    default void addRow(T... Row){
        addRow( Arrays.asList( Row ) );
    }

    /*
    /**
     * Converts the dynamic table to a static array
     * @return the table as array of {@link E}
     */
    //<E extends T> E[][] toArray(E[][] a);

}
