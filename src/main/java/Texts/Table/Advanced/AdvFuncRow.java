package Texts.Table.Advanced;

import Texts.Builder;
import Texts.Spec;

import java.util.LinkedList;
import java.util.List;
import java.util.function.BinaryOperator;

/**
 * A Class defining how a row containing operators should be built;
 * @param <T>
 */
public class AdvFuncRow<T> extends AdvFunc<T> {

    //TODO Docs!!
    private int columns;

    private int startRow;
    private int endRow;

    @Override
    public List<BinaryOperator<T>> getStandardOperatorAsList() {
        LinkedList<BinaryOperator<T>> R = new LinkedList<>();
        for (int i = 0; i < columns; i++) {
            R.add( operator );
        }
        return R;
    }

    public List<BinaryOperator<T>> getRow(){
        if(singular){
            LinkedList<BinaryOperator<T>> r = new LinkedList<>();
            for (int i = 0; i < columns; i++) {
                r.add( operator );
            }
            return r;
        }else{
            return operators;
        }
    }
    public int getStartRow() {
        return startRow;
    }
    public int getEndRow(){
        return endRow;
    }
    //Constructor for Builder Class
    private AdvFuncRow(RowBuilder<T> B){
        this.endRow=B.endRow;
        this.startRow=B.startRow;
        this.singular=B.singular;
        if(singular){
            this.operator=B.operator;
        }else{
            this.operator=B.operator;
            this.operators=B.operators;
        }
    }
    //Builder Getter
    public static <T> RowBuilder<T> getRowBuilder(){
        return new RowBuilder<>();
    }
    public static class RowBuilder<T> implements Builder<AdvFuncRow<T>>{
        private int startRow;
        private int endRow;
        private boolean singular;
        private BinaryOperator<T> operator;
        private List<BinaryOperator<T>> operators;
        private RowBuilder(){
            startRow = 0;
            endRow = 0;
            singular = true;
            operator = null;
            operators = null;
        }
        public RowBuilder<T> setStartRow(int startRow){
            this.startRow=startRow;
            return this;
        }
        public RowBuilder<T> setEndRow(int endRow){
            this.endRow=endRow;
            return this;
        }
        public RowBuilder<T> setStandardOperator(BinaryOperator<T> operator){
            this.singular = true;
            this.operator = operator;
            return this;
        }
        public RowBuilder<T> setOperators(List<BinaryOperator<T>> operators){
            this.singular = false;
            this.operators = operators;
            return this;
        }
        @Override
        public AdvFuncRow<T> build() {
            return new AdvFuncRow<T>(this);
        }
    }
    public static class RowSpec<T> implements Spec<AdvFuncRow<T>>{
        private RowBuilder<T> builder;
        private RowSpec(){
            builder = new RowBuilder<>();
        }
        public RowSpec<T> setStartRow(int startRow){
            this.builder.setStartRow( startRow );
            return this;
        }
        public RowSpec<T> setEndRow(int endRow){
            this.builder.setEndRow( endRow );
            return this;
        }
        public RowSpec<T> setStandardOperator(BinaryOperator<T> operator){
            this.builder.setStandardOperator( operator );
            return this;
        }
        public RowSpec<T> setOperators(List<BinaryOperator<T>> operators){
            this.builder.setOperators( operators );
            return this;
        }
        @Override
        public AdvFuncRow<T> create() {
            return new AdvFuncRow<>( builder );
        }
    }
}
