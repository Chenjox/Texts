package Texts;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName( "StringBoxable Contract:" )
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public interface TI_StringBoxable {

    String getExpectedString();
    StringBoxable getTestSubject();
    @Test
    @Order(1)
    @DisplayName( "1: toBoxString" )
    default void T_toBoxString(){
        Assertions.assertEquals( getExpectedString(), getTestSubject().toBoxString(), "FAILED STRINGBOX MATCH" );
    }

    @Disabled
    @Test
    @Order(2)
    @DisplayName( "2: getWidth" )
    default void T_getWidth(){
        //TODO
    }
    @ParameterizedTest(name = "3: p{index} -> {0}")
    @ValueSource(ints = {-3,-2,-1,0,1,2,3,4,5,6,7,8,100,200,400})
    @Order(3)
    default void T_getLines(int Line){
        String[] s = getExpectedString().split( "\\n" );
        if(Line>=0&&Line<=s.length-1){
            Assertions.assertEquals( s[Line],getTestSubject().getLine( Line ),"FAILED EXPECTED LINE MATCH" );
        }else{
            Assertions.assertThrows( IndexOutOfBoundsException.class,()->{
                getTestSubject().getLine( Line );
            }, "FAILED THROW INDEXOUTOFBOUNDSEXCEPTION" );
        }
    }
}
