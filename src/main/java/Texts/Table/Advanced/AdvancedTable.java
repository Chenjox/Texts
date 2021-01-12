package Texts.Table.Advanced;

import Texts.Spec;
import Texts.StringBoxable;
import Texts.Table.ComputableTable;
import Texts.Table.FunctionalTable;
import Texts.Table.MappableTable;
import Texts.Table.Table;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A general purpose table made for computation. Rows can be added, functional rows and columns must be specified at creation time of the table. Can be formatted into a standard <tt>String</tt>
 * @see Table
 * @see ComputableTable
 * @see FunctionalTable
 * @see MappableTable
 * @see StringBoxable
 * @param <T> the content type of the table
 */
public class AdvancedTable<T> implements FunctionalTable<T>, MappableTable<T,String>, StringBoxable {

    private final Function<T,String> standardMapper;
    private final LinkedList<AdvRow<T>> rows; //here are the cells
    private final LinkedList<AdvFuncRow<T>> funcRows;
    private final LinkedList<AdvFuncColumn<T>> funcColumns;
    private final int columns;

    private AdvancedTable(Function<T, String> standardMapper, LinkedList<AdvRow<T>> rows, LinkedList<AdvFuncRow<T>> funcRows, LinkedList<AdvFuncColumn<T>> funcColumns) {
        this.columns=0;
        this.standardMapper = standardMapper;
        this.rows = rows;
        this.funcRows = funcRows;
        this.funcColumns = funcColumns;
    }

    /**
     * This Sets the content of the cell at the specified position. If you want to get the content of functional rows, use
     * @param Column index of the column
     * @param Row index of the row
     * @param content the new content
     */
    @Override
    public void setCell(int Column, int Row, T content) {
        rows.get( Row ).setCell( Column,content );
    }

    @Override
    public T getCell(int Column, int Row) {
        return rows.get(Row).getCell( Column );
    }

    @Override
    public int getColumns() {
        return columns;
    }

    @Override
    public void setColumn(int index, List<T> Column) {
        for (int i = 0; i < rows.size(); i++) {
            rows.get( i ).setCell( index, Column.get( i ) );
        }
    }

    @Override
    public List<T> getColumn(int index) {
        LinkedList<T> r = new LinkedList<>();
        for (int i = 0; i < rows.size(); i++) {
            r.add( rows.get( i ).getCell( index ) );
        }
        return r;
    }

    @Override
    public int getRows() {
        return rows.size();
    }

    @Override
    public void setRow(int index, List<T> Row) {
        rows.get( index ).setRow( Row );
    }

    @Override
    public List<T> getRow(int index) {
        return rows.get( index ).getRow();
    }

    @Override
    public void addRow(List<T> Row) {
        rows.add( new AdvRow<>( Row ) );
    }

    @Override
    public void addRow(int index, List<T> Row) {
        rows.add( index, new AdvRow<>( Row ) );
    }

    @Override
    public T computeOnColumn(BinaryOperator<T> operator, int index, int startRow, int endRow) {
        List<T> c = getColumn( index );
        T r = c.get( startRow );
        for (int i = 1; i < endRow; i++) {
            r = operator.apply(r, c.get(i));
        }
        return r;
    }
    @Override
    public List<T> computeOnColumns(BinaryOperator<T> operator, int startRow, int endRow) {
        LinkedList<T> R = new LinkedList<>();
        for (int i = 0; i < getColumns(); i++) {
            R.add( computeOnColumn( operator, i, startRow, endRow ) );
        }
        return R;
    }

    @Override
    public List<T> computeOnColumns(List<BinaryOperator<T>> operator, int startRow, int endRow) {
        LinkedList<T> R = new LinkedList<>();
        for (int i = 0; i < getColumns(); i++) {
            R.add( computeOnColumn( operator.get( i ), i, startRow, endRow ) );
        }
        return R;
    }

    @Override
    public T computeOnRow(BinaryOperator<T> operator, int index, int startColumn, int endColumn) {
        List<T> c = getRow( index );
        T r = c.get( startColumn );
        for (int i = 1; i < endColumn; i++) {
            r = operator.apply(r, c.get(i));
        }
        return r;
    }

    @Override
    public List<T> computeOnRows(BinaryOperator<T> operator, int startColumn, int endColumn) {
        LinkedList<T> R = new LinkedList<>();
        for (int i = 0; i < getRows(); i++) {
            R.add( computeOnRow( operator, i, startColumn, endColumn ) );
        }
        return R;
    }

    @Override
    public List<T> computeOnRows(List<BinaryOperator<T>> operator, int startColumn, int endColumn) {
        LinkedList<T> R = new LinkedList<>();
        for (int i = 0; i < getRows(); i++) {
            R.add( computeOnRow( operator.get( i ), i, startColumn, endColumn ) );
        }
        return R;
    }

    @Override
    public List<BinaryOperator<T>> getFunctionalColumn(int index) {
        return funcColumns.get( index ).getOperators();
    }

    @Override
    public List<T> getComputedFunctionalColumn(int index) {
        LinkedList<T> t = new LinkedList<>();
        for (int i = 0; i < getRows(); i++) {
            t.add( getComputedFunctionalColumnEntry( index, i ) );
        }
        return t;
    }

    @Override
    public T getComputedFunctionalColumnEntry(int column, int row){
        BinaryOperator<T> operator = funcColumns.get( column ).getOperator( row );
        int startColumn = funcColumns.get( column ).getStartColumn();
        int endColumn = funcColumns.get( column ).getEndColumn();
        return computeOnRow(operator, row ,startColumn, endColumn);
    }
    @Override
    public List<BinaryOperator<T>> getFunctionalRow(int index) {
        return funcRows.get(index).getOperators();
    }

    @Override
    public List<T> getComputedFunctionalRow(int index) {
        LinkedList<T> t = new LinkedList<>();
        for (int i = 0; i < getColumns(); i++) {
            t.add( getComputedFunctionalRowEntry( i, index ) );
        }
        return t;
    }

    @Override
    public T getComputedFunctionalRowEntry(int column, int row){
        BinaryOperator<T> operator = funcRows.get( row ).getOperator( column );
        int startRow = funcRows.get( row ).getStartRow();
        int endRow = funcRows.get( row ).getEndRow();
        return computeOnColumn( operator, column, startRow, endRow );
    }


    @Override
    public Function<T, String> getMapper() {
        return standardMapper;
    }
    @Override
    public <N> List<N> MapColumn(int column, Function<T, N> mapper) {
        return getColumn( column ).stream().map( mapper ).collect( Collectors.toList());
    }
    @Override
    public <N> List<N> MapRow(int row, Function<T, N> mapper) {
        return getRow( row ).stream().map( mapper ).collect( Collectors.toList());
    }

    /**
     * a string array for easy implementation of {@link StringBoxable#getLine(int)}
     */
    private String[] cache;
    /**
     * whether the cache must be updated
     */
    private boolean cacheIsDirty = true;
    //TODO ohne ausgabe bringt das nicht viel oder?

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public String getLine(int Line) {
        return null;
    }

    @Override
    public String toBoxString() {
        return null;
    }

    //TODO Builder und Spec

    public static class Builder<T> implements Texts.Builder<AdvancedTable<T>>{

        @Override
        public AdvancedTable<T> build(){
            return null;
        }
    }

    public static class AdvTableSpec<T> implements Spec<T>{

        @Override
        public T create() {
            return null;
        }
    }
}
