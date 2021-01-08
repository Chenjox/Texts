package Texts.Table.ContractTests.TI_Table;

import Texts.Table.Table;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

@DisplayName( "Texts.Table<T> Equalities:" )
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public interface TI_Table_Equality<T> {

    /**
     * @return an empty Texts.Table with 3 columns
     */
    Table<T> getTestTable();
    List<T> getTestList();
    T getTestEntry();

    @Test
    @Order( 1 )
    @DisplayName( "1: setCell Equality" )
    default void T_setCell(){
        Table<T> t = getTestTable();
        t.addRow( getTestList() );
        t.setCell( 0,0,getTestEntry() );
        Assertions.assertEquals( t.getCell( 0, 0 ), getTestEntry() );
    }
    @Test
    @Order( 2 )
    @DisplayName( "2: setRow Equality" )
    default void T_setRow(){
        Table<T> t = getTestTable();
        t.addRow( Arrays.asList( getTestEntry(),getTestEntry(),getTestEntry() ) );
        List<T> l = t.getRow( t.getRows()-1 );
        boolean b = true;
        for (int i = 0; i < t.getColumns(); i++) {
            b = l.get( i ).equals( getTestEntry() );
        }
        Assertions.assertTrue( b );
    }
    /*
    @Test
    @Order( 1 )
    @DisplayName( "1: setColumn Equality" )
    default void T_setColumn(){

    }
    @Test
    @Order( 1 )
    @DisplayName( "1: setCell Equality" )
    default void T_addRow(){

    }
    */
}
