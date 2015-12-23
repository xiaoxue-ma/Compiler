package test;
import static org.junit.Assert.fail;
import java.io.StringReader;
import lexer.Lexer;
import org.junit.Test;
import parser.Parser;
public class ParserTests {
private void runtest(String src) {
runtest(src, true);
}
private void runtest(String src, boolean succeed) {
Parser parser = new Parser();
try {
parser.parse(new Lexer(new StringReader(src)));
if(!succeed) {
fail("Test was supposed to fail, but succeeded");
}
} catch (beaver.Parser.Exception e) {
if(succeed) {
e.printStackTrace();
fail(e.getMessage());
}
} catch (Throwable e) {
e.printStackTrace();
fail(e.getMessage());
}
}

@Test
public void testEmptyModule() {
// test empty module with no imports an declarations
runtest("module Test { }");
//// test case not enclosed by module
runtest("import java; import math;", false);
}
//
@Test
public void testImportsAndDeclarations(){
// test multiple imports
runtest("module test{ import java; import math;}");
// wrong spelling for "import"
runtest("module test{ iport java; import math;}", false);
// test function declaration, with empty parameter list and function body
runtest("module test{ import java; import math; public int testFunction(){ } }");
// function declaration without return type
runtest("module test{ import java; import math; public testFunction(){ } }", false);
// test function declaration, with empty parameter list
runtest("module test{ import java; import math; public int testFunction(){int var;} }");
// test parameter list without closing parenthesis
runtest("module test{ import java; import math; public int testFunction({int var;} }", false);
// local variables cannot be assigned value when declared
runtest("module test{ import java; import math; public int testFunction(){int var = 1;} }", false);
// test field declaration, without initial assignment; testing array type
runtest("module test{ import java; import math; public int integer; public boolean flag; public int[] array; }");
// test array type without closing bracket
runtest("module test{ import java; import math; public int integer; public boolean flag; public int[ array; }", false);
// test field declaration, global variable cannot be assigned value when declared
runtest("module test{ import java; import math; public int integer; public boolean flag = true; public int[] array; }", false);
// test field declaration without accessibility specifier
runtest("module test{ import java; import math; int integer; public boolean flag; public int[] array; }");
// test type declaration
runtest("module test{ public type newType = \"Type1\"; }");
// test correctly self-defined type - TypeName can be an identifier
runtest("module test{ public type newType = \"Type1\"; public Type1 aType1Variable; }");
// test undefined self-defined type - TypeName can be an identifier
runtest("module test{ public type newType = \"Type1\"; public Type2 aType1Variable; }");
// test wrong accessibility
runtest("module test{ import java; import math; private int test_function(){ } }", false);
// test declaration without accessibility specifier
runtest("module test{ import java; import math; int test_function(){ } }");
}
//@Test
//public void testParamaterList(){
//// test parameter list, test expressions
//runtest("module test{ public void testFunction(int count, int[] data, boolean flag){count = count + 1; flag = false; data[12] = 123; } }");
//}

//@Test
//public void testArrayAccess(){
//	runtest("module test{a = 1; }");
//
//}

@Test
public void testStatementsAndExpressions(){
// test array access with [identifier]
runtest("module test{ public void testFunction(int count, int[] data, boolean flag){count = count + 1; flag = false; data[foo] = 123; } }");
// test if else statement, arithmetic expressions
// test function call
// test return statement
runtest("module test{ public void processArray(int[] array){ int num1; int num2; if (compare(num1, num2)){ array[1] = 9;} else{ array[2] = (array[1]*3 + 2)/4;}} public boolean compare(int num1, int num2){ return true;} }");
// test else statement without if
runtest("module test{ public void processArray(int[] array){ int num1; int num2; else{ array[2] = (array[1]*3 + 2)/4;}} public boolean compare(int num1, int num2){ return true;} }", false);
// test while statement and break statement
// test single if statement
// test expression with GEQ and EQEQ
runtest("module test{ public void count(){ int count; count = 10; boolean flag; flag = false; while (count>=3){ if(flag == true){break;} count = count-1; flag = true;} } }");
}
@Test
public void testDanDan()
{
runtest("module test { import jkfdshjkfdh; import haha;}");
runtest("module test { import jkfdshjkfdh; import haha; }");
// runtest("int a;"); // must enclosed in module
runtest("module test { int a; }");
// runtest("module test { a=1; }");
// runtest("module test { a(1, 2); }");
runtest("module test {public int function_name() { } }");
runtest("module test {public int function_name() { } }");
runtest("module test { import jkfdshjkfdh; import haha; public int cw() { } }");
runtest("module test { import jkfdshjkfdh; import haha; public int cw(int cw) {int cw2; } }");
runtest("module test { import jkfdshjkfdh; import haha; public int cw(int cw, cw cw1) {int cw2; } }");
runtest("module test { import jkfdshjkfdh; import haha; public int cw(int cw, cw cw1) {int cw2; } public int[] cw2; public type cw3 = \"hello world\"; }");
runtest("module test { import jkfdshjkfdh; import haha; public int cw(int cw, cw cw1) {int cw2; if(cw2= 1*2) cw = 2;else while(cw = 2) cw = cw+1; } }");
runtest("module test { import jkfdshjkfdh; import haha; public int cw(int cw, cw cw1) {int cw2; if(cw2= 1*2) cw = 2;else while(cw = 2) cw = cw+1; } }");
runtest("module test { public void runtest(string src) { runtest(src, true); } }");
runtest("module test { public void runtest(string src) { int[] a; a[1] = 1;} }"); // refer to LhsExpression
runtest("module test { import java; public int Num1; public int Num2; public void count(){int A = Num1;int B = Num2; while(sub(A,B)){A=A-1;}}public int sub(int num1, int num2){if(num1>num2){return 1;}else{return 0;}}}", false);
}
}