import Texts.Table.Advanced.AdvFuncColumn;

import java.util.ArrayList;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {
        AdvFuncColumn<Integer> t = AdvFuncColumn.createColumn(
                spec ->
                        spec.setStandardOperator(Integer::sum)
                                .setEndColumn( 2 )
                                .setStartColumn( 1 )
        );
        System.out.println(t.applyOperator( Arrays.asList( 4,5,6,7,8,9,0 ), 0 ));
    }
}
