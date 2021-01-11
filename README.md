### Util.Texts

This Library is made mostly for personal use. It currently contains the following texts helpers:  
1. Tables  
The _Table_ package contains helper methods for creating tables and doing computations on them:  
    1. This is realised by specifying five **Interfaces**  
        1. [`Table<T>`](src/main/java/Texts/Table/Table.java) A contract that all tables implement. Important to note is, that columns must be specified at build time of the table.  
        2. [`ComputableTable<T>`](src/main/java/Texts/Table/ComputableTable.java) A contract tables abide to, when they're supporting computations.
        3. [`FunctionalTable<T>`](src/main/java/Texts/Table/FunctionalTable.java) A contract table supporting functional rows and columns. Functional rows and columns aren't raw data, instead they determine their content when they're called. Functional rows and columns are immutable.
        4. [`MappableTable<T,M>`](src/main/java/Texts/Table/FunctionalTable.java) A contract tables abide to, when they can be mapped to a class M.
        5. [`AppendableFunctional<T>`](src/main/java/Texts/Table/AppendableFunctional.java) A contract specifying how functional rows should be added to an already built table.
    2. The following **classes** implement these Interfaces:
        1. [_SimpleTable_](src/main/java/Texts/Table/Simple/SimpleTable.java): A simple Table to store data in, and format in a very simple way.  
        2. [_AdvancedTable_](src/main/java/Texts/Table/Advanced/AdvancedTable.java): A Table to store data in and do computations on. It also supports functional rows.  
        3. _StringTable_: A Table solely for formatting data in a neat way. **NOT YET IMPLEMENTED**
    3. Work to be done:
        - [ ] Completing implementing AdvancedTable
            - [ ] Making it functional
            - [ ] Making it StringMappable
        - [ ] Completely implementing StringTable
            - [ ] Creating formatted Cells
            - [ ] Creating formatted Rows
            - [ ] Creating formatted Headers
            - [ ] Something I missed.
2. _To be continued_

-----

### Common Interfaces

These interfaces serve the purpose to unify creation patterns and operation patterns along the library.
There are currently two **Interfaces**:
1. `Builder<T>`  
    Commonly a Builder class will be implemented in a parent class, where this builder can create the desired object.  
    ```java
    public class Parent{
      private final attribute;
      //methods etc.
      public static Builder getBuilder(){
        return new Builder();
      }
      public static class Builder implements Builder<Parent>{
        public Parent build(){
          return new Parent();
        }
      }
    }
    ```
2. `Spec<T>`  
    The Spec (short for specification) is another way to create an object.
    ```java
    public class Parent{
      private final attribute;
      //methods etc.
      public static Parent createParent(Consumer<Spec> spec){
        Spec t = new Spec();
        spec.apply(t);
        return t.create;
      }
      public static class Spec implements Spec<Parent>{
        public Parent create(){
          return new Parent();
        }
      }
    }
    ```
------

### JUnit Tests.  
All classes and Interface must have a Junit Test to test them. Most of the time, exceptions are tested and so called equivalences.
you can find every Unit test in [`Test`](src/test/java).  
Every Interface Test starts with `TI_-interfacename-`.  
Every Class Test starts with `T_-classname-`.
