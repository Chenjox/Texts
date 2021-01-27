package Texts.Table.Advanced;

import Texts.Spec;
import Texts.StringBoxable;
import Texts.Table.ComputableTable;
import Texts.Table.FunctionalTable;
import Texts.Table.MappableTable;
import Texts.Table.Table;
import Texts.Util.StringUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
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

    /**
     * This should not be used.
     * @param standardMapper
     * @param rows
     * @param funcRows
     * @param funcColumns
     */
    private AdvancedTable(Function<T, String> standardMapper, LinkedList<AdvRow<T>> rows, LinkedList<AdvFuncRow<T>> funcRows, LinkedList<AdvFuncColumn<T>> funcColumns) {
        this.columns=0;
        this.standardMapper = standardMapper;
        this.rows = rows;
        this.funcRows = funcRows;
        this.funcColumns = funcColumns;
        this.widthcache = new int[this.columns];
        this.funcwidthcache = new int[this.funcColumns.size()];
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
        cacheIsDirty = true;
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
        if(!(Column.size()==getRows())) throw new IllegalArgumentException("Argument.size(): "+Column.size()+", Expected: " + getRows());
        if(index<0||index>getColumns()-1) throw new IndexOutOfBoundsException("Index: " +index+", Size: "+getRows());
        for (int i = 0; i < rows.size(); i++) {
            rows.get( i ).setCell( index, Column.get( i ) );
        }
        cacheIsDirty = true;
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
        if(!(Row.size()==getColumns())) throw new IllegalArgumentException("Argument.size(): "+Row.size()+", Expected: " + getRows());
        rows.get( index ).setRow( Row );
        cacheIsDirty = true;
    }

    @Override
    public List<T> getRow(int index) {
        if(index<0||index>getRows()) throw new IndexOutOfBoundsException("Index: " +index+", Size: "+getRows());
        return rows.get( index ).getRow();
    }

    @Override
    public void addRow(List<T> Row) {
        if(!(Row.size()==getColumns())) throw new IllegalArgumentException("Argument.size(): "+Row.size()+", Expected: " + getRows());
        rows.add( new AdvRow<>( Row ) );
        cacheIsDirty = true;
    }

    @Override
    public void addRow(int index, List<T> Row) {
        if(!(Row.size()==getColumns())) throw new IllegalArgumentException("Argument.size(): "+Row.size()+", Expected: " + getRows());
        if(index<0||index>getRows()-1) throw new IndexOutOfBoundsException("Index: " +index+", Size: "+getRows());
        rows.add( index, new AdvRow<>( Row ) );
        cacheIsDirty = true;
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
    public int getFunctionalColumns() {
        return funcColumns.size();
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
    public int getFunctionalRows() {
        return funcRows.size();
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
        int endRow = funcRows.get( row ).getEndRow()==AdvFuncRow.ENDROW_FOR_ALL_ROWS ? getRows() : funcRows.get( row ).getEndRow();
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

    //TODO Builder und Spec Docs

    private AdvancedTable(TableBuilder<T> t){
        this.funcRows = t.funcRows;
        this.funcColumns = t.funcColumns;
        this.standardMapper = t.standardMapper;
        this.columns = t.columns;
        this.rows = new LinkedList<>();

        this.widthcache = new int[t.columns];
        this.funcwidthcache = new int[t.funcColumns.size()];
    }

    public static <T> TableBuilder<T> getTableBuilder(Class<T> type){
        return new TableBuilder<>();
    }

    public static class TableBuilder<T> implements Texts.Builder<AdvancedTable<T>>{
        private Function<T,String> standardMapper;
        private LinkedList<AdvFuncRow<T>> funcRows;
        private LinkedList<AdvFuncColumn<T>> funcColumns;
        private int columns;

        private TableBuilder(){
            columns=0;
            funcRows = new LinkedList<>();
            funcColumns = new LinkedList<>();
            standardMapper = Objects::toString;
        }
        public TableBuilder<T> setColumns(int pcolumns){
            this.columns=pcolumns;
            return this;
        }
        public TableBuilder<T> setStandardMapper(Function<T,String> mapper){
            this.standardMapper = mapper;
            return this;
        }
        public TableBuilder<T> addFunctionalRow(Consumer<AdvFuncRow.RowSpec<T>> rowSpec){
            funcRows.add( AdvFuncRow.createFuncRow( rowSpec ) );
            return this;
        }
        public TableBuilder<T> addFunctionalRow(AdvFuncRow.RowBuilder<T> builder){
            funcRows.add( builder.build() );
            return this;
        }
        public TableBuilder<T> addFunctionalRow(BinaryOperator<T> function){
            funcRows.add( AdvFuncRow.createFuncRow( spec -> spec.setEndRow( AdvFuncRow.ENDROW_FOR_ALL_ROWS ).setStandardOperator( function ) ));
            return this;
        }
        public TableBuilder<T> addFunctionalRow(List<BinaryOperator<T>> functions){
            funcRows.add( AdvFuncRow.createFuncRow( spec -> spec.setEndRow( AdvFuncRow.ENDROW_FOR_ALL_ROWS ).setOperators( functions ) ) );
            return this;
        }
        public TableBuilder<T> addFunctionalColumn(Consumer<AdvFuncColumn.ColumnSpec<T>> columnSpec){
            funcColumns.add( AdvFuncColumn.createFuncColumn( columnSpec ) );
            return this;
        }
        public TableBuilder<T> addFunctionalColumn(AdvFuncColumn.ColumnBuilder<T> builder){
            funcColumns.add( builder.build() );
            return this;
        }
        public TableBuilder<T> addFunctionalColumn(BinaryOperator<T> function){
            funcColumns.add( AdvFuncColumn.createFuncColumn( spec -> spec.setEndColumn( columns ).setStandardOperator( function ) ));
            return this;
        }
        public TableBuilder<T> addFunctionalColumn(List<BinaryOperator<T>> functions){
            funcColumns.add( AdvFuncColumn.createFuncColumn( spec -> spec.setEndColumn( columns ).setOperators( functions ) ));
            return this;
        }
        @Override
        public AdvancedTable<T> build(){
            return new AdvancedTable<>( this );
        }
    }

    public static <T> AdvancedTable<T> getTable(Consumer<AdvTableSpec<T>> spec){
        AdvTableSpec<T> s = new AdvTableSpec<T>();
        spec.accept( s );
        return s.create();
    }

    public static class AdvTableSpec<T> implements Spec<AdvancedTable<T>>{
        private final TableBuilder<T> t = new TableBuilder<>();

        public AdvTableSpec<T> setColumns(int pcolumns){
            t.setColumns( pcolumns );
            return this;
        }
        public AdvTableSpec<T> setStandardMapper(Function<T,String> mapper){
            t.setStandardMapper( mapper );
            return this;
        }
        public AdvTableSpec<T> addFunctionalRow(Consumer<AdvFuncRow.RowSpec<T>> rowSpec){
            t.addFunctionalRow( rowSpec );
            return this;
        }
        public AdvTableSpec<T> addFunctionalRow(AdvFuncRow.RowBuilder<T> builder){
            t.addFunctionalRow( builder );
            return this;
        }
        public AdvTableSpec<T> addFunctionalRow(BinaryOperator<T> function){
            t.addFunctionalRow( function );
            return this;
        }
        public AdvTableSpec<T> addFunctionalRow(List<BinaryOperator<T>> functions){
            t.addFunctionalRow( functions );
            return this;
        }
        public AdvTableSpec<T> addFunctionalColumn(Consumer<AdvFuncColumn.ColumnSpec<T>> columnSpec){
            t.addFunctionalColumn( columnSpec );
            return this;
        }
        public AdvTableSpec<T> addFunctionalColumn(AdvFuncColumn.ColumnBuilder<T> builder){
            t.addFunctionalColumn( builder );
            return this;
        }
        public AdvTableSpec<T> addFunctionalColumn(BinaryOperator<T> function){
            t.addFunctionalColumn( function );
            return this;
        }
        public AdvTableSpec<T> addFunctionalColumn(List<BinaryOperator<T>> functions){
            t.addFunctionalColumn( functions );
            return this;
        }
        @Override
        public AdvancedTable<T> create() {
            return t.build();
        }
    }

    private final int[] widthcache;
    private final int[] funcwidthcache;

    private void initWidthCache(){
        if(cacheIsDirty){
            Arrays.fill(widthcache, 0);
            Arrays.fill(funcwidthcache, 0);

            for (int i = 0; i < widthcache.length; i++) {
                widthcache[i] = getColumnWidth( i );
            }
            for (int j = 0; j < funcwidthcache.length; j++) {
                funcwidthcache[j] = getFuncColumnWidth( j );
            }
        }
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

    /**
     * returns the size of the largest string in a column
     * @param index index of the column
     * @return the width of the column
     */
    private int getColumnWidth(int index){
        int r = 0;
        for (int i = 0; i < getRows(); i++) {
            r = Math.max( r, standardMapper.apply( getCell( index, i ) ).length() );
        }
        for (int j = 0; j < getFunctionalRows(); j++) {
            r= Math.max( r,standardMapper.apply(getComputedFunctionalRowEntry( index,j )).length() );
        }
        return r;
    }
    /**
     * returns the size of the largest string in a functional column
     * @param index index of the column
     * @return the width of the column
     */
    private int getFuncColumnWidth(int index){
        int r = 0;
        for (int i = 0; i < getRows(); i++) {
            r = Math.max( r, standardMapper.apply( getComputedFunctionalColumnEntry( index, i ) ).length() );
        }
        return r;
    }

    /**
     * returns the width of the table with the specified string mapper
     * @return the width of the table
     */
    @Override
    public int getWidth() {
        initWidthCache();
        return Arrays.stream( widthcache ).sum()+ Arrays.stream( funcwidthcache ).sum();
    }

    @Override
    public int getHeight() {
        return getRows()+funcRows.size();
    }

    @Override
    public String getLine(int Line) {
        initWidthCache();
        if(Line<getRows()){
            return getRowLine( Line );
        }else {
            return getFuncRowLine( Line-getRows() );
        }
    }

    @Override
    public String toBoxString() {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < getHeight(); i++) {
            b.append( getLine( i ) ).append( '\n' );
        }
        return b.toString();
    }

    private String getRowLine(int row){
        StringBuilder b = new StringBuilder();
        b.append( '|' );
        for (int i = 0; i < getColumns(); i++) {
            b.append( ' ' ).append( StringUtil.StringZuBreite( getMappedCell( i, row ), widthcache[i] ) ).append( ' ' ).append( '|' );
        }
        for (int i = 0; i < getFunctionalColumns(); i++) {
            b.append( ' ' ).append( StringUtil.StringZuBreite( standardMapper.apply( getComputedFunctionalColumnEntry( i, row ) ), funcwidthcache[i] ) ).append( ' ' ).append( '|' );
        }
        return b.toString();
    }

    private String getFuncRowLine(int funcRow){
        StringBuilder b = new StringBuilder();
        b.append( '|' );
        for (int i = 0; i < getColumns(); i++) {
            b.append( ' ' ).append( StringUtil.StringZuBreite( standardMapper.apply( getComputedFunctionalRowEntry( i, funcRow )), widthcache[i] ) ).append( ' ' ).append( '|' );
        }
        for (int i = 0; i < getFunctionalColumns(); i++) {
            b.append( ' ' ).append( StringUtil.CharAsBreite( '-', funcwidthcache[i] ) ).append( ' ' ).append( '|' );
        }
        return b.toString();
    }
}