package Texts.Table.ContractTests.TI_Table;

import Texts.Table.Table;
import org.junit.jupiter.api.*;

import java.util.List;

@DisplayName( "Texts.Table<T> Exceptions:" )
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public interface TI_Table_Exceptions<T> {

    /**
     * Expects to return a Texts.Table with 3 Columns and 2 Rows only
     * @return the specified Texts.Table
     */
    Table<T> getTestTable();

    /**
     * Expects to return a List with 3 Entries only
     * @return the specified List
     */
    List<T> getTestList();

    /**
     * Expects to return a List with 2 Entries only
     * @return the specified List
     */
    List<T> getTestListColumn();

    /**
     * Expects to return a List with 1 Entriy only
     * @return the specified List
     */
    List<T> getTestListColumnFaulty();

    /**
     * Expects to return a Row with 2 Entries only
     * @return the specified List
     */
    List<T> getTestListFaulty_1();

    /**
     * Expects to return a Row with 4 Entries only
     * @return the specified List
     */
    List<T> getTestListFaulty_2();

    /**
     * Expects to return a Element of type T
     * @return the specified Element
     */
    T getTestEntry();

    @Test
    @Order(1)
    @DisplayName( "1: getCell first Index" )
    default void T_getCellFirst(){
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getCell( getTestTable().getColumns(),0 );
        },"FAILED INDEX TOO HIGH" );
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getCell( -1,0 );
        },"FAILED INDEX TOO LOW" );
    }
    @Test
    @Order(2)
    @DisplayName( "2: getCell second Index" )
    default void T_getCellSecond(){
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getCell( 0, getTestTable().getRows() );
        },"FAILED INDEX TOO HIGH" );
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getCell( 0,-1 );
        },"FAILED INDEX TOO LOW" );
    }
    @Test
    @Order(3)
    @DisplayName( "3: getRow Index" )
    default void T_getRowIndex(){
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getRow( getTestTable().getRows() );
        },"FAILED INDEX TOO HIGH" );
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getRow( -1 );
        },"FAILED INDEX TOO LOW" );
    }
    @Test
    @Order(4)
    @DisplayName( "4: setRow Index" )
    default void T_setRowIndex(){
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().setRow( getTestTable().getRows(), getTestList() );
        },"FAILED INDEX TOO HIGH" );
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().setRow( -1, getTestList() );
        },"FAILED INDEX TOO LOW" );
    }
    @Test
    @Order(5)
    @DisplayName( "5: setRow Arguments" )
    default void T_setRowArgument(){
        Assertions.assertThrows( IllegalArgumentException.class,()->{
            getTestTable().setRow( 1, getTestListFaulty_2() );
        },"FAILED ARGUMENT LIST TOO BIG" );
        Assertions.assertThrows( IllegalArgumentException.class,()->{
            getTestTable().setRow( 1, getTestListFaulty_1() );
        },"FAILED ARGUMENT LIST TOO SMALL" );
    }
    @Test
    @Order(6)
    @DisplayName( "6: getColumn Index" )
    default void T_getColumnIndex(){
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getColumn( getTestTable().getColumns() );
        },"FAILED INDEX TOO HIGH" );
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().getColumn( -1 );
        },"FAILED INDEX TOO LOW" );
    }
    @Test
    @Order(7)
    @DisplayName( "7: setColumn Index" )
    default void T_setColumnIndex(){
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().setColumn( getTestTable().getColumns(), getTestListColumn() );
        },"FAILED INDEX TOO HIGH" );
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().setColumn( -2, getTestListColumn() );
        },"FAILED INDEX TOO LOW" );
    }
    @Test
    @Order(8)
    @DisplayName( "8: setColumn Arguments" )
    default void T_setColumnArgument(){
        Assertions.assertThrows( IllegalArgumentException.class,()->{
            getTestTable().setColumn( 1, getTestListFaulty_2() );
        },"FAILED ARGUMENT LIST TOO BIG" );
        Assertions.assertThrows( IllegalArgumentException.class,()->{
            getTestTable().setColumn( 1, getTestListColumnFaulty() );
        },"FAILED ARGUMENT LIST TOO SMALL" );
    }
    //TODO AddRow Tests, und flexibler machen der Tests
    @Test
    @Order(9)
    @DisplayName( "9: addRow Arguments" )
    default void T_addRowArgument(){
        Assertions.assertThrows( IllegalArgumentException.class,()->{
            getTestTable().addRow( getTestListFaulty_2() );
        },"FAILED ARGUMENT LIST TOO BIG" );
        Assertions.assertThrows( IllegalArgumentException.class,()->{
            getTestTable().addRow( getTestListFaulty_1() );
        },"FAILED ARGUMENT LIST TOO SMALL" );
    }
    @Test
    @Order(10)
    @DisplayName( "10: addRow Insert index" )
    default void T_addRowInsertIndex(){
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().addRow( getTestTable().getRows(), getTestList() );
        },"FAILED INDEX TOO HIGH" );
        Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
            getTestTable().addRow( -2, getTestList() );
        },"FAILED INDEX TOO LOW" );
    }
}
