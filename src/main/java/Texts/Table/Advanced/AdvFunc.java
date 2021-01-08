package Texts.Table.Advanced;

import java.util.List;
import java.util.function.BinaryOperator;

public abstract class AdvFunc<T> {

    /**
     * whether this object uses a singular operator for all cells.
     */
    protected boolean singular;
    /**
     * the single operator. Shall also be the standard operator
     */
    protected BinaryOperator<T> operator;
    protected List<BinaryOperator<T>> operators;

    protected static <T> T applyOperatorToList(List<T> Arguments, BinaryOperator<T> op){
        switch (Arguments.size()){
            case 0: throw new IllegalArgumentException("Non Empty List Expected");
            case 1: return Arguments.get( 0 );
            case 2: return op.apply( Arguments.get(0),Arguments.get(1) );
            default:
                T r = op.apply(Arguments.get(0),Arguments.get(1));
                for (int i = 2; i < Arguments.size(); i++) {
                    r = op.apply(r,Arguments.get( i ));
                }
                return r;
        }
    }
    public abstract List<BinaryOperator<T>> getStandardOperatorAsList();
    public BinaryOperator<T> getOperator(int index){
        if(singular){
            return operator;
        }else{
            return operators.get( index );
        }
    }
    public List<BinaryOperator<T>> getOperators(){
        if(singular){
            return getStandardOperatorAsList();
        }else{
            return operators;
        }
    }
    public T applyOperator(T Argument1,T Argument2, int index){
        if (singular){
            return operator.apply( Argument1 , Argument2 );
        }else {
            return operators.get( index ).apply( Argument1, Argument2 );
        }
    }
    public T applyOperator(List<T> Arguments, int index){
        if(singular){
            return applyOperatorToList( Arguments, operator );
        }else {
            return applyOperatorToList( Arguments, operators.get( index ) );
        }
    }
}
