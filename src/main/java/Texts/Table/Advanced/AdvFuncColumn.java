package Texts.Table.Advanced;

import Texts.Builder;
import Texts.Spec;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;

/**
 * A Functional Column to adhere to the {@link Texts.Table.FunctionalTable FunctionalTable} Contract in {@link AdvancedTable}
 * @param <T> the type of the Row
 */
public class AdvFuncColumn<T> extends AdvFunc<T> {
    /**
     * describes the rows of this column. should be equal to {@link AdvancedTable#getRows()}.
     */
    private int rows;
    /**
     * describes the starting row of this column
     */
    private final int startColumn;
    /**
     * describes the ending row of this column
     */
    private final int endColumn;

    @Override
    public List<BinaryOperator<T>> getStandardOperatorAsList() {
        LinkedList<BinaryOperator<T>> R = new LinkedList<>();
        for (int i = 0; i < rows; i++) {
            R.add( operator );
        }
        return R;
    }

    public List<BinaryOperator<T>> getColumn(){
        if(singular){
            LinkedList<BinaryOperator<T>> r = new LinkedList<>();
            for (int i = 0; i < rows; i++) {
                r.add( operator );
            }
            return r;
        }else{
            return operators;
        }
    }
    public void addRow(BinaryOperator<T> t){
        rows++;
        appendOperator( t );
    }
    public int getStartColumn() {
        return startColumn;
    }
    public int getEndColumn(){
        return endColumn;
    }
    private void appendOperator(BinaryOperator<T> t){
        if(!singular){
            operators.add( t );
        }
    }
    //Constructor for Builder Class
    private AdvFuncColumn(ColumnBuilder<T> B){

        this.endColumn=B.endColumn;
        this.startColumn=B.startColumn;
        this.singular=B.singular;
        if(singular){
            this.operator=B.operator;
        }else{
            this.operator=B.operator;
            this.operators=B.operators;
        }
    }
    //Builder Getter
    public static <T> ColumnBuilder<T> getColumnBuilder(){
        return new ColumnBuilder<>();
    }
    public static class ColumnBuilder<T> implements Builder<AdvFuncColumn<T>>{
        private int startColumn;
        private int endColumn;
        private boolean singular;
        private BinaryOperator<T> operator;
        private List<BinaryOperator<T>> operators;
        private ColumnBuilder(){
            startColumn = 0;
            endColumn = 0;
            singular = true;
            operator = null;
            operators = null;
        }
        public ColumnBuilder<T> setStartColumn(int startColumn){
            this.startColumn=startColumn;
            return this;
        }
        public ColumnBuilder<T> setEndColumn(int endColumn){
            this.endColumn=endColumn;
            return this;
        }
        public ColumnBuilder<T> setStandardOperator(BinaryOperator<T> operator){
            this.singular = true;
            this.operator = operator;
            return this;
        }
        public ColumnBuilder<T> setOperators(List<BinaryOperator<T>> operators){
            this.singular = false;
            this.operators = operators;
            return this;
        }
        @Override
        public AdvFuncColumn<T> build() {
            return new AdvFuncColumn<T>( this );
        }
    }
    public static <T> AdvFuncColumn<T> createFuncColumn(Consumer<ColumnSpec<T>> spec){
        ColumnSpec<T> s = new ColumnSpec<>();
        spec.accept( s );
        return s.create();
    }
    public static class ColumnSpec<T> implements Spec<AdvFuncColumn<T>>{
        private final ColumnBuilder<T> builder;
        private ColumnSpec(){
            this.builder = new ColumnBuilder<>();
        }
        public ColumnSpec<T> setStartColumn(int startColumn){
            this.builder.setStartColumn( startColumn );
            return this;
        }
        public ColumnSpec<T> setEndColumn(int endColumn){
            this.builder.setEndColumn( endColumn );
            return this;
        }
        public ColumnSpec<T> setStandardOperator(BinaryOperator<T> operator){
            this.builder.setStandardOperator( operator );
            return this;
        }
        public ColumnSpec<T> setOperators(List<BinaryOperator<T>> operators){
            this.builder.setOperators( operators );
            return this;
        }
        @Override
        public AdvFuncColumn<T> create() {
            return builder.build();
        }
    }
}
