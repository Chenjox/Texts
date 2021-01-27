package Texts.Table.Simple;

import Texts.Spec;
import Texts.StringBoxable;
import Texts.Table.Table;
import Texts.Util.StringUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A Mundane Simple Texts.Table to store values in. Can be formatted into a standard <tt>String</tt>
 * @see StringBoxable
 * @see Table
 * @param <T> Type of the Stored Content
 */
public class SimpleTable<T> implements Table<T>, StringBoxable {

    //TODO Docs überarbeiten!
    //Du solltest im der Klasse dokumentieren, wie du das interface implementierst
    //Das Interface hat in der Doku, wie es funktionieren sollte
    //Das kann sich unterscheiden
    //Das kann gleich sein
    //Doppelt hält besser
    /**
     * The list containing the cells of the table
     */
    private final LinkedList<LinkedList<T>> cells;

    @Override
    public void setCell(int Column, int Row, T content) {
        cells.get( Column ).set( Row, content );
        cacheIsDirty = true;
    }

    @Override
    public T getCell(int Column, int Row) {
        return cells.get( Column ).get( Row );
    }

    @Override
    public int getColumns() {
        return cells.size();
    }

    @Override
    public void setColumn(int index, List<T> Column) {
        if(!(Column.size()==getRows())) throw new IllegalArgumentException("Argument.size(): "+Column.size()+", Expected: " + getRows());
        if(index<0||index>getColumns()-1) throw new IndexOutOfBoundsException("Index: " +index+", Size: "+getRows());
        LinkedList<T> nColumn = new LinkedList<>();
        for (int i = 0; i < getRows(); i++) {
            nColumn.add( Column.get( i ) );
        }
        cells.set( index , nColumn);
        cacheIsDirty = true;
    }

    @Override
    public List<T> getColumn(int index) {
        return cells.get( index );
    }

    @Override
    public int getRows() {
        return !cells.isEmpty() ? cells.get( 0 ).size() : 0;
    }

    @Override
    public void setRow(int index, List<T> Row) {
        if(!(Row.size()==getColumns())) throw new IllegalArgumentException("Argument.size(): "+Row.size()+", Expected: " + getRows());
        for (int i = 0; i < cells.size(); i++) {
            cells.get( i ).set( index, Row.get(i) );
        }
        cacheIsDirty = true;
    }

    @Override
    public List<T> getRow(int index) {
        if(index<0||index>getRows()) throw new IndexOutOfBoundsException("Index: " +index+", Size: "+getRows());
        ArrayList<T> r = new ArrayList<>();
        for (int i = 0; i < cells.size(); i++) {
            r.add( cells.get( i ).get( index ) );
        }
        return r;
    }

    @Override
    public void addRow(List<T> Row) {
        if(!(Row.size()==getColumns())) throw new IllegalArgumentException("Argument.size(): "+Row.size()+", Expected: " + getRows());
        for (int i = 0; i < cells.size(); i++) {
            cells.get( i ).add( Row.get( i ) );
        }
        cacheIsDirty = true;
    }

    @Override
    public void addRow(int index, List<T> Row) {
        if(!(Row.size()==getColumns())) throw new IllegalArgumentException("Argument.size(): "+Row.size()+", Expected: " + getRows());
        if(index<0||index>getRows()-1) throw new IndexOutOfBoundsException("Index: " +index+", Size: "+getRows());
        for (int i = 0; i < cells.size(); i++) {
            cells.get( i ).add( index, Row.get( i ) );
        }
        cacheIsDirty = true;
    }

    //A string cache for {@link getLine()}
    /**
     * a string array for easy implementation of {@link StringBoxable#getLine(int)}
     */
    private String[] cache;
    /**
     * whether the cache must be updated
     */
    private boolean cacheIsDirty = true;

    /**
     * returns the size of the largest string in a column
     * @param index index of the column
     * @param m the map function
     * @return the width of the column
     */
    private int getColumnWidth(int index, Function<T,String> m){
        int r = 0;
        for (int i = 0; i < getRows(); i++) {
            r = Math.max( r, m.apply( getCell( index, i ) ).length() );
        }
        return r;
    }

    /**
     * <pre>
     *     &lt;---    Width    ---&gt;
     *     | Cell 11 | Cell 12 |
     *     | Cell 21 | Cell 22 |
     * </pre>
     * @return the {@link StringBoxable#getWidth()} of the table
     */
    @Override
    public int getWidth() {
        return getWidth(Objects::toString);
    }

    /**
     * returns the width of the table with the specified string mapper
     * @param m the mapping function
     * @return the width of the table
     */
    public int getWidth(Function<T,String> m) {
        int r = 1; //Leftmost limiter
        for (int i = 0; i < getColumns(); i++) {
            int temp = 0;
            for (int j = 0; j < getRows(); j++) {
                temp= Math.max( temp,m.apply(getCell( i,j )).length() );
            }
            //Limiter + Margin on each side
            r += temp+1+2;
        }
        return r;
    }
    @Override
    public int getHeight() {
        return getRows();
    }
    @Override
    public String getLine(int Line) {
        if(cache==null||cacheIsDirty)initCache(toBoxString());
        return cache[Line];
    }
    @Override
    public String toBoxString() {
        return toBoxString(Objects::toString);
    }

    /**
     * Returns the content of the table in the following fashion:
     * <pre>
     *      &lt;---    Width    ---&gt;
     *      | Cell 11 | Cell 12 |
     *      | Cell 21 | Cell 22 |
     * </pre>
     * @param m the mapping function
     * @return a fully formatted string, for use with {@link System#out#println(String)}
     */
    public String toBoxString(Function<T,String> m){
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < getRows()-1; i++) {
            b.append( '|' );
            for (int j = 0; j < getColumns(); j++) {
                b.append( ' ' ).append( StringUtil.StringZuBreite( m.apply(getCell( j,i )), getColumnWidth( j,m )) ).append( ' ' ).append( '|' );
            }
            b.append( '\n' );
        }
        b.append( '|' );
        for (int i = 0; i < getColumns(); i++) {
            b.append( ' ' ).append( StringUtil.StringZuBreite( m.apply(getCell( i,getRows()-1 )), getColumnWidth( i,m )) ).append( ' ' ).append( '|' );
        }
        String s = b.toString();
        initCache( s );
        return b.toString();
    }

    /**
     * refreshes the cache, if it is marked dirty
     * @param s the new BoxString
     */
    private void initCache(String s){
        if(cacheIsDirty) cache = s.split( "\\n" );
    }
    //Builder Class

    /**
     * creates an instance of this class out of the nested builder class.
     * @see Builder#build()
     * @param B the Builder Object
     */
    private SimpleTable(Builder<T> B){
        cells = new LinkedList<>();
        cells.addAll( B.bcells );
    }
    //Builder Getter

    /**
     * creates an builder instance of this class to start the builder chain.
     * @param <T> the Type of the Texts.Table
     * @return the builder of {@link SimpleTable}
     */
    public static <T> Builder<T> getTableBuilder(){
        return new Builder<>();
    };

    /**
     * The builder class to create and configure the table. Comes with standard values
     * @param <T>
     */
    public static class Builder<T> implements Texts.Builder<SimpleTable<T>>{
        private LinkedList<LinkedList<T>> bcells;
        public Builder(){
            bcells = new LinkedList<>();
        }

        /**
         * Sets the number of columns in a table
         * @param columns number of columns
         * @return this object
         */
        public Builder<T> setColumns(int columns){
            bcells = new LinkedList<>();
            for (int i = 0; i < columns; i++) {
                bcells.add( new LinkedList<>() );
            }
            return this;
        };
        public SimpleTable<T> build(){
            return new SimpleTable<>(this);
        };
    }

    //Spec Class

    /**
     * creates a SimpleTable given the specified construction. The {@link Spec#create()} method should not be called.
     * @see Spec
     * @param spec the spec instruction. shall not be <tt style="color:#FF6347">null</tt>
     * @param <T> the cell type of the table
     * @return the constructed table
     */
    public static <T> SimpleTable<T> createTable(Consumer<SimpleTableCreateSpec<T>> spec){
        SimpleTableCreateSpec<T> a = new SimpleTableCreateSpec<>();
        spec.accept( a );
        return a.create();
    }
    //Spec Getter
    public static class SimpleTableCreateSpec<T> implements Spec<SimpleTable<T>>{
        private Builder<T> b;
        private SimpleTableCreateSpec(){
            b = new Builder<>();
        }
        /**
         * Sets the number of columns in a table
         * @param columns number of columns
         * @return this spec
         */
        public SimpleTableCreateSpec<T> setColumns(int columns){
            b.setColumns( columns );
            return this;
        };
        @Override
        public SimpleTable<T> create() {
            return b.build();
        }
    }
}
