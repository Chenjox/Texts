package Texts.Table.AdvancedTable;

import Texts.Table.Advanced.AdvancedTable;
import Texts.Table.ContractTests.TI_Table.TI_Table;
import Texts.Table.Simple.SimpleTable;
import Texts.Table.Table;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


@DisplayName( "AdvancedTable Test:" )
public class T_AdvancedTable implements TI_Table<String> {

    private AdvancedTable<String> t;

    public T_AdvancedTable(){
        t = AdvancedTable.<String>getTableBuilder(String.class).setColumns( 3 ).build();
        t.addRow( "Row 1","Row 2","Row 3" );
    }

    @Test
    @DisplayName( "Constructor Test" )
    public void test_constructor(){
        SimpleTable<String> g = SimpleTable.<String>getTableBuilder().setColumns( 3 ).build();
        g.addRow( "Row 1","Row 2","Row 3" );
        Assertions.assertEquals( t.getCell( 0, 0 ) , g.getCell( 0, 0 ) );
    }

    @Override
    public Table<String> getTestTable() {
        AdvancedTable<String> t = AdvancedTable.getTableBuilder(String.class).setColumns( 3 ).build();
        t.addRow( "test 1","test 2","test 3" );
        t.addRow( "test 4","test 5","test 6" );
        return t;
    }

    @Override
    public List<String> getTestList() {
        return Arrays.asList( "3", "4", "5" );
    }

    @Override
    public List<String> getTestListColumn() {
        return Arrays.asList( "3", "4" );
    }

    @Override
    public List<String> getTestListColumnFaulty() {
        return Arrays.asList( "3" );
    }

    @Override
    public List<String> getTestListFaulty_1() {
        return Arrays.asList( "3", "4" );
    }

    @Override
    public List<String> getTestListFaulty_2() {
        return Arrays.asList( "3", "4", "5", "6" );
    }

    @Override
    public String getTestEntry() {
        return "null";
    }
}
