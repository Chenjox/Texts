package Examples.Table;

import Texts.Table.Advanced.AdvancedTable;
import Texts.Table.Rendering.AsciiDoc.AsciiDocTable;

public class E_SimpleTable {
    public static void main(String[] args) {
        AdvancedTable<Integer> t = AdvancedTable.getTable( spec -> spec
                .setColumns( 4 )
                .addFunctionalColumn( (i1, i2) -> (i2*i1)/(i1+i2) )
                .addFunctionalColumn( Integer::sum )
                .addFunctionalRow( Integer::sum )
                .addFunctionalRow( (i1, i2) -> i1*i2 )
                .setStandardMapper( Object::toString )
        );
        t.addRow( 1,2,3,4 );
        t.addRow( 5,6,7,8 );
        t.addRow( 9,10,12,13 );
        t.addComputedRow( (integer, integer2) -> integer+integer2*2 );
        t.addComputedRow( (integer, integer2) -> integer+integer2*2 );

        System.out.println(t.toBoxString());

        AsciiDocTable r = new AsciiDocTable( t , "empty");
        r.setTitle( "Numbers" );
        r.setHasFooter( true );
        r.setHasHeader( true );
        System.out.println(r.render());
        //System.out.println(t.toBoxString());
    }
}
