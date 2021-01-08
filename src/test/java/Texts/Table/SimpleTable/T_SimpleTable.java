package Texts.Table.SimpleTable;

import Texts.StringBoxable;
import Texts.TI_StringBoxable;
import Texts.Table.ContractTests.TI_Table.TI_Table;
import Texts.Table.Simple.SimpleTable;
import Texts.Table.Table;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.List;

@DisplayName("SimpleTable Test:")
public class T_SimpleTable implements TI_Table<String>, TI_StringBoxable {

    private SimpleTable<String> t;

    public T_SimpleTable(){
        t = SimpleTable.<String>getTableBuilder().setColumns( 3 ).build();
        t.addRow( "Row 1","Row 2","Row 3" );
    }

    @Test
    @DisplayName( "Constructor Test" )
    public void test_constructor(){
        SimpleTable<String> g = SimpleTable.<String>getTableBuilder().setColumns( 3 ).build();
        g.addRow( "Row 1","Row 2","Row 3" );
        Assertions.assertEquals( t.getCell( 0,0 ) , g.getCell( 0,0 ) );
    }

    @Test
    @DisplayName( "Create Spec Test" )
    public void test_spec(){
        SimpleTable<String> a = SimpleTable.createTable( spec -> spec.setColumns( 3 ));
        a.addRow( "Row 1","Row 2","Row 3" );
        Assertions.assertEquals( t.getCell( 0,0 ), a.getCell( 0,0 ) );
    }

    @Test
    @DisplayName( "toBoxString Test" )
    public void test_toBoxString(){
        t.addRow( "Help", "I need", "Help" );
        Assertions.assertEquals( "| Row 1 | Row 2  | Row 3 |\n| Help  | I need | Help  |",t.toBoxString() );
        //System.out.println(t.getStringBox());
    }

    @Override
    public Table<String> getTestTable() {
        SimpleTable<String> r = SimpleTable.<String>getTableBuilder().setColumns( 3 ).build();
        r.addRow( "Test 1","Test 2","Test 3" );
        r.addRow( "Test 2","Test 3","Test 4" );
        return r;
    }

    @Override
    public List<String> getTestList() {
        return Arrays.asList( "Test 3","Test 4","Test 5" );
    }

    @Override
    public List<String> getTestListColumn() {
        return Arrays.asList( "Just","Right" );
    }

    @Override
    public List<String> getTestListColumnFaulty() {
        return Arrays.asList( "Too Small" );
    }

    @Override
    public List<String> getTestListFaulty_1() {
        return Arrays.asList( "Too Small", "Faulty" );
    }

    @Override
    public List<String> getTestListFaulty_2() {
        return Arrays.asList( "Too Big", "Faulty", "List", "Curse" );
    }

    @Override
    public String getTestEntry() {
        return "null";
    }

    @Override
    public String getExpectedString() {
        return "| Row 1 | Row 2  | Row 3 |\n| Help  | I need | Help  |";
    }

    @Override
    public StringBoxable getTestSubject() {
        t.addRow( "Help", "I need", "Help" );
        return t;
    }
}
