package Texts.Table;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * A contract specifying how a table to do computations on should be built.
 * An extension of normal {@link Table}, which can compute the specified operations on the elements of the table.
 * this must ensure that no cells are <tt style="color: #FF6347">null</tt>.
 * functional columns, if existent, should be executed last.
 * @since 1.0
 * @param <T> The type of the content objects
 */
public interface ComputableTable<T> extends Table<T>{

    /**
     * computes the given {@link BinaryOperator} B on the given column in the {@link Table} C with content {@link T} in the following fashion:
     * <pre>
     *     R_1 = Cell Content on first Cell
     *     R_2 = B(R_1, T_1)
     *     R_3 = B(R_2, T_2)
     *     .
     *     .
     *     .
     *     R_n+1 = B(R_n, T_n)
     * </pre>
     * The last <code>R_n+1</code> being the result. In this case, all cells in the column will be consumed.
     * If there aren't two cells to act upon, the initial value will be returned.
     * @param operator the given {@link BinaryOperator} B
     * @param index the index of the column
     * @return the result of the given computation
     */
    default T computeOnColumn(BinaryOperator<T> operator, int index){
        return computeOnColumn( operator, index , 0);
    };

    /**
     * computes the given {@link BinaryOperator} B on the given column in the {@link Table} C starting on the given row with content {@link T} in the following fashion:
     * <pre>
     *     R_1 = Cell Content on Cell in startRow
     *     R_2 = B(R_1, T_1)
     *     R_3 = B(R_2, T_2)
     *     .
     *     .
     *     .
     *     R_n+1 = B(R_n, T_n)
     * </pre>
     * the last <code>R_n+1</code> being the result. In this case, all cells beneath startRow will be acted upon.
     * If there aren't two cell to act upon, the initial value will be returned.
     * @param operator the given {@link BinaryOperator} B
     * @param index the index of the column
     * @param startRow the index of the initial row
     * @return the result of the given computation
     */
    default T computeOnColumn(BinaryOperator<T> operator, int index, int startRow){
        return computeOnColumn( operator, index, startRow, getRows()-1 );
    };

    /**
     * computes the given {@link BinaryOperator} B on the given column in the {@link Table} C starting on the given row and ending on the given row with content {@link T} in the following fashion:
     * <pre>
     *     R_1 = Cell Content on Cell in startRow
     *     R_2 = B(R_1, T_1)
     *     R_3 = B(R_2, T_2)
     *     .
     *     .
     *     .
     *     R_n+1 = B(R_n, T_n)
     * </pre>
     * the last <code>R_n+1</code> being the result. In this case, all cells between startRow and endRow will be acted upon.
     * If there aren't two cell to act upon, the initial value will be returned.
     * @param operator the given {@link BinaryOperator} B
     * @param index the index of the Column
     * @param startRow the index of the initial row
     * @param endRow the index of the terminal row
     * @return the result of the given computation
     */
    T computeOnColumn(BinaryOperator<T> operator, int index, int startRow, int endRow);

    /**
     * computes the given {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumn(BinaryOperator, int)}.
     * @param operator the given {@link BinaryOperator}
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnColumns(BinaryOperator<T> operator){
        return computeOnColumns( operator, 0 );
    };

    /**
     * computes the given {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumn(BinaryOperator, int, int)}.
     * @param operator the given {@link BinaryOperator}
     * @param startRow the index of the initial row
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnColumns(BinaryOperator<T> operator, int startRow){
        return computeOnColumns( operator, startRow, getRows()-1);
    };

    /**
     * computes the given {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumn(BinaryOperator, int, int, int)}.
     * @param operator the given {@link BinaryOperator}
     * @param startRow the index of the initial row
     * @param endRow the index of the terminal row
     * @return the {@link List} of results of the given computation.
     */
    List<T> computeOnColumns(BinaryOperator<T> operator, int startRow, int endRow);

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumn(BinaryOperator, int)}.
     * This allows setting individual operators for each column.
     * @param operator the given {@link List} of {@link BinaryOperator}
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnColumns(List<BinaryOperator<T>> operator){
        return computeOnColumns( operator, 0 );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumn(BinaryOperator, int, int)}.
     * This allows setting individual operators for each column.
     * @param operator the given {@link List} of {@link BinaryOperator}
     * @param startRow the index of the initial row
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnColumns(List<BinaryOperator<T>> operator, int startRow){
        return computeOnColumns( operator, startRow, getRows()-1 );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumn(BinaryOperator, int, int, int)}.
     * This allows setting individual operators for each column.
     * @param operator the given {@link List} of {@link BinaryOperator}
     * @param startRow the index of the initial row
     * @param endRow the index of the terminal row
     * @return the {@link List} of results of the given computation.
     */
    List<T> computeOnColumns(List<BinaryOperator<T>> operator, int startRow, int endRow);

    /**
     * computes the given {@link BinaryOperator} B on the given row in the {@link Table} C with content {@link T} in the following fashion:
     * <pre>
     *      R_1 = Cell Content on first Cell
     *      R_2 = B(R_1, T_1)
     *      R_3 = B(R_2, T_2)
     *      .
     *      .
     *      .
     *      R_n+1 = B(R_n, T_n)
     * </pre>
     * The last <code>R_n+1</code> being the result. In this case, all cells in the row will be consumed.
     * If there aren't two cells to act upon, the initial value will be returned.
     * @param operator the given {@link BinaryOperator}.
     * @param index the index of the row.
     * @return the result of the given computation.
     */
    default T computeOnRow(BinaryOperator<T> operator, int index){
        return computeOnRow(operator, index, 0 );
    };

    /**
     * computes the given {@link BinaryOperator} B on the given row in the {@link Table} C starting on the given column with content {@link T} in the following fashion:
     * <pre>
     *      R_1 = Cell Content on first Cell
     *      R_2 = B(R_1, T_1)
     *      R_3 = B(R_2, T_2)
     *      .
     *      .
     *      .
     *      R_n+1 = B(R_n, T_n)
     * </pre>
     * The last <code>R_n+1</code> being the result. In this case, all cells in the row will be consumed.
     * If there aren't two cells to act upon, the initial value will be returned.
     * @param operator the given {@link BinaryOperator}.
     * @param index the index of the row.
     * @param startColumn the index of the initial column.
     * @return the result of the given computation.
     */
    default T computeOnRow(BinaryOperator<T> operator, int index, int startColumn){
        return computeOnRow( operator, index, startColumn, getColumns()-1 );
    };

    /**
     * computes the given {@link BinaryOperator} B on the given row in the {@link Table} C starting on the given row and ending on the given row with content {@link T} in the following fashion:
     * <pre>
     *      R_1 = Cell Content on first Cell
     *      R_2 = B(R_1, T_1)
     *      R_3 = B(R_2, T_2)
     *      .
     *      .
     *      .
     *      R_n+1 = B(R_n, T_n)
     * </pre>
     * The last <code>R_n+1</code> being the result. In this case, all cells in the row will be consumed.
     * If there aren't two cells to act upon, the initial value will be returned.
     * @param operator operator the given {@link BinaryOperator}
     * @param index index the index of the row
     * @param startColumn index of the initial column
     * @param endColumn index of the terminal column
     * @return the result of the given computation
     */
    T computeOnRow(BinaryOperator<T> operator, int index, int startColumn, int endColumn);

    /**
     * computes the given {@link BinaryOperator} on all rows in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnRow(BinaryOperator, int)}.
     * @param operator the given {@link BinaryOperator}
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnRows(BinaryOperator<T> operator){
        return computeOnRows(operator, 0);
    };

    /**
     * computes the given {@link BinaryOperator} on all rows in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnRow(BinaryOperator, int, int)}.
     * @param operator the given {@link BinaryOperator}
     * @param startColumn index of the initial column
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnRows(BinaryOperator<T> operator, int startColumn){
        return computeOnRows( operator, startColumn, getColumns()-1 );
    };

    /**
     * computes the given {@link BinaryOperator} on all rows in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnRow(BinaryOperator, int, int, int)}.
     * @param operator the given {@link BinaryOperator}
     * @param startColumn index of the initial column
     * @param endColumn index of the terminal column
     * @return the {@link List} of results of the given computation.
     */
    List<T> computeOnRows(BinaryOperator<T> operator, int startColumn, int endColumn);

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all rows in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnRow(BinaryOperator, int)}.
     * This allows setting individual operators for each row.
     * @param operator the given {@link List} of {@link BinaryOperator}
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnRows(List<BinaryOperator<T>> operator){
        return computeOnRows( operator, 0 );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all rows in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnRow(BinaryOperator, int, int)}.
     * This allows setting individual operators for each row.
     * @param operator the given {@link List} of {@link BinaryOperator}
     * @param startColumn index of the initial column
     * @return the {@link List} of results of the given computation.
     */
    default List<T> computeOnRows(List<BinaryOperator<T>> operator, int startColumn){
        return computeOnRows( operator, startColumn, getColumns()-1 );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all rows in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnRow(BinaryOperator, int, int, int)}.
     * This allows setting individual operators for each row.
     * @param operator the given {@link List} of {@link BinaryOperator}
     * @param startColumn index of the initial column
     * @param endColumn index of the terminal column
     * @return the {@link List} of results of the given computation.
     */
    List<T> computeOnRows(List<BinaryOperator<T>> operator, int startColumn, int endColumn);

    /**
     * computes the given {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator)} and adds the result to the table.
     * @param operator the given {@link BinaryOperator}
     */
    default void addComputedRow(BinaryOperator<T> operator){
        this.addRow( computeOnRows( operator ) );
    };

    /**
     * computes the given {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator, int)} and adds the result to the table.
     * @param operator the given {@link BinaryOperator}
     * @param startRow index of the initial row
     */
    default void addComputedRow(BinaryOperator<T> operator, int startRow){
        this.addRow( computeOnRows( operator, startRow ) );
    };

    /**
     * computes the given {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator, int, int)} and adds the result to the table.
     * @param operator the given {@link BinaryOperator}
     * @param startRow index of the initial row
     * @param endRow index of the terminal row
     */
    default void addComputedRow(BinaryOperator<T> operator, int startRow, int endRow){
        this.addRow( computeOnRows( operator, startRow, endRow ) );
    };

    /**
     * computes the given {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator, int, int)} and adds the result to the table at the specified position.
     * @param operator the given {@link BinaryOperator}
     * @param startRow index of the initial row
     * @param endRow index of the terminal row
     * @param insertRow index of the row, after which this row is inserted
     */
    default void addComputedRow(BinaryOperator<T> operator, int startRow, int endRow, int insertRow){
        this.addRow( insertRow, computeOnRows( operator, startRow, endRow ) );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator)} and adds the result to the table.
     * This allows setting individual operators for each row.
     * @param operators the given {@link List} of {@link BinaryOperator}
     */
    default void addComputedRow(List<BinaryOperator<T>> operators){
        this.addRow( computeOnRows( operators ) );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator, int)} and adds the result to the table.
     * This allows setting individual operators for each row.
     * @param operators the given {@link List} of {@link BinaryOperator}
     * @param startRow index of the initial row
     */
    default void addComputedRow(List<BinaryOperator<T>> operators, int startRow){
        this.addRow( computeOnRows( operators, startRow ) );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator, int, int)} and adds the result to the table.
     * This allows setting individual operators for each row.
     * @param operators the given {@link List} of {@link BinaryOperator}
     * @param startRow index of the initial row
     * @param endRow index of the terminal row
     */
    default void addComputedRow(List<BinaryOperator<T>> operators, int startRow, int endRow){
        this.addRow( computeOnRows( operators, startRow, endRow ) );
    };

    /**
     * computes the given {@link List} of {@link BinaryOperator} on all columns in the {@link Table} C with content {@link T} likewise to {@link ComputableTable#computeOnColumns(BinaryOperator, int, int)} and adds the result to the table at the specified position.
     * This allows setting individual operators for each row.
     * @param operators the given {@link List} of {@link BinaryOperator}
     * @param startRow index of the initial row
     * @param endRow index of the terminal row
     * @param insertRow index of the row, after which this row is inserted
     */
    default void addComputedRow(List<BinaryOperator<T>> operators, int startRow, int endRow, int insertRow){
        this.addRow( insertRow, computeOnRows( operators, startRow, endRow ) );
    };
}
