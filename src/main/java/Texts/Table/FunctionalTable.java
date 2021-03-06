package Texts.Table;

import java.util.List;
import java.util.function.BinaryOperator;

/**
 * A contract specifying how a functional table should be built.
 * A functional Row or Column acts on the elements of the table and should be computed row first column second.
 * This Interface allows for a immutable implementation of functionial rows / columns
 * @param <T> The type of the content objects
 */
public interface FunctionalTable<T> extends ComputableTable<T> {

    /* TODO Docs checken. Überprüfen ob es zu Pauchus Anmerkungen passt
    //Du sollst ja im Interface nicht jedes Detail der Implementierung erklären
    //deswegen ist es ja nur ein interface
    //Was das interface macht, ist, sicherstellen, dass eine funktion vorhanden ist
    //um somit kompatibilität mit anderen funktionen zu garantieren
    //funktion im sinne von eigenschaft
    //nicht im programmiersinn
    //wobei das auch nicht ganz falsch wäre */

    /**
     * @return returns the number of functional columns
     */
    int getFunctionalColumns();

    /**
     * Returns the {@link List} of {@link BinaryOperator} for the specified functional column.
     * @param index index of the functional column. This is a <span style="color: #FF6347">different</span> index from normal columns.
     * @return the {@link List} of {@link BinaryOperator}s
     */
    List<BinaryOperator<T>> getFunctionalColumn(int index);

    /**
     * Returns the {@link List} of computed {@link T} for the specified functional column.
     * @param index index of the functional column. This is a <span style="color: #FF6347">different</span> index from normal columns.
     * @return the {@link List} of {@link T}s
     */
    List<T> getComputedFunctionalColumn(int index);

    /**
     * Returns the {@link List} of computed {@link T} for the specified functional column.
     * @param column index of the functional column. This is a <span style="color: #FF6347">different</span> index from normal columns.
     * @param row index of the row inside the column. This is the same index as the number of rows.
     * @return the {@link List} of {@link T}s
     */
    T getComputedFunctionalColumnEntry(int column, int row);

    /**
     * @return returns the number of functional rows
     */
    int getFunctionalRows();

    /**
     * Returns the {@link List} of {@link BinaryOperator} for the specified functional row.
     * @param index index of the functional row. This is a <span style="color: #FF6347">different</span> index from normal rows.
     * @return the {@link List} of {@link BinaryOperator}s
     */
    List<BinaryOperator<T>> getFunctionalRow(int index);

    /**
     * Returns the {@link List} of computed {@link T} for the specified functional row.
     * @param index index of the functional row. This is a <span style="color: #FF6347">different</span> index from normal rows.
     * @return the {@link List} of {@link T}s
     */
    List<T> getComputedFunctionalRow(int index);

    /**
     * Returns the {@link List} of computed {@link T} for the specified functional row.
     * @param column index of the column inside the row. This is the same index as the number of columns.
     * @param row index of the functional row. This is a <span style="color: #FF6347">different</span> index from normal rows.
     * @return the {@link List} of {@link T}s
     */
    T getComputedFunctionalRowEntry(int column, int row);
}
