package Texts.Table;

import java.util.function.BinaryOperator;

//TODO wichtige erkenntnisse -> diese Tables sind nicht immutable! andere Klasse!
//TODO nicht unbedingt wichtig
/**
 * A Contract specifying how a table with appendable functional Columns and Rows should operate.
 * @param <T>
 */
public interface AppendableFunctional<T> extends FunctionalTable<T>{
    /**
     * Adds a functional row to the table, specifying one {@link BinaryOperator} for all columns.
     * @param operator the {@link BinaryOperator}
     */
    void addFunctionalRow(BinaryOperator<T> operator);

    /**
     * Adds a functional row to the table, specifying one {@link BinaryOperator} for all columns.
     * @param operator the {@link BinaryOperator}
     * @param startRow index of the initial row
     */
    void addFunctionalRow(BinaryOperator<T> operator, int startRow);

    /**
     * Adds a functional row to the table, specifying one {@link BinaryOperator} for all columns.
     * @param operator the {@link BinaryOperator}
     * @param startRow index of the initial row
     * @param endRow index of the terminal row
     */
    void addFunctionalRow(BinaryOperator<T> operator, int startRow, int endRow);

    //TODO FunctionalTable#addFunctionalRow -> Einzelne operatoren adden
}
