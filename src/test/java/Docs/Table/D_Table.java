package Docs.Table;

import Texts.Table.Advanced.AdvancedTable;
import Texts.Table.ComputableTable;
import Texts.Table.FunctionalTable;
import Texts.Table.MappableTable;
import Texts.Table.Simple.SimpleTable;
import Texts.Table.Table;
import ch.ifocusit.plantuml.classdiagram.ClassDiagramBuilder;

import java.lang.reflect.Modifier;

public class D_Table {

    public static void main(String[] args) {
        BuildUml_1();
    }
    public static void BuildUml_1(){
        final String diagram = new ClassDiagramBuilder()
                .addClasses( Table.class, ComputableTable.class, FunctionalTable.class, MappableTable.class, SimpleTable.class, AdvancedTable.class )
                .addFieldPredicate( c -> !(c.getFieldType().getModifiers()==Modifier.PRIVATE) )
                .build();
        System.out.println(diagram);
    }
}
