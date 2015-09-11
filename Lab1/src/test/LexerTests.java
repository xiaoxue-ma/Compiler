package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.StringReader;

import lexer.Lexer;

import org.junit.Test;

import frontend.Token;
import frontend.Token.Type;
import static frontend.Token.Type.*;

/**
 * This class contains unit tests for your lexer. You
 * are strongly encouraged to write your own tests.
 */
public class LexerTests {
	// helper method to run tests; no need to change this
	private final void runtest(String input, Token... output) {
		Lexer lexer = new Lexer(new StringReader(input));
		int i=0;
		Token actual, expected;
		try {
			do {
				assertTrue(i < output.length);
				expected = output[i++];
				try {
					actual = lexer.nextToken();
					assertEquals(expected, actual);
				} catch(Error e) {
					if(expected != null)
						fail(e.getMessage());
					return;
				}
			} while(!actual.isEOF());
		} catch (IOException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	/** Example unit test. */
	@Test
	public void testKWs() {
		// first argument to runtest is the string to lex; the remaining arguments
		// are the expected tokens
		runtest("module false\nreturn while",
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 1, 0, "return"),
				new Token(WHILE, 1, 7, "while"),
				new Token(EOF, 1, 12, ""));
	}

		

	
	//////////////////////////////////////////////
	//The following test cases are extracted from Li Siyao's repo
	@Test
	public void testKWs2(){
		// testing key words - 2
		runtest("boolean else void int",
				new Token(BOOLEAN, 0, 0, "boolean"),
				new Token(ELSE, 0, 8, "else"),
				new Token(VOID, 0, 13, "void"),
				new Token(INT, 0, 18, "int"),
				new Token(EOF, 0, 21, ""));
			
	}
	
	@Test
	public void testWhiteSpace(){
		// testing whitespace
		runtest("module false\treturn\nwhile",   // space \t \n
				new Token(MODULE, 0, 0, "module"),
				new Token(FALSE, 0, 7, "false"),
				new Token(RETURN, 0, 13, "return"),
				new Token(WHILE, 1, 0, "while"),
				new Token(EOF, 1, 5, ""));
	}
	
	@Test
	public void testOperatorPunctuation(){
		runtest("/ <= * ( , }",
				new Token(DIV, 0, 0, "/"),
				new Token(LEQ, 0, 2, "<="),
				new Token(TIMES, 0, 5, "*"),
				new Token(LPAREN, 0, 7, "("),
				new Token(COMMA, 0, 9, ","),
				new Token(RCURLY, 0, 11, "}"),
				new Token(EOF, 0, 12, ""));
	}
	
	@Test
	public void testIdentifier(){
		runtest("module123 integer Boolean Int_123",
				new Token(ID, 0, 0, "module123"),
				new Token(ID, 0, 10, "integer"),
				new Token(ID, 0, 18, "Boolean"),
				new Token(ID, 0, 26, "Int_123"),
				new Token(EOF, 0, 33, ""));
	}
	
	@Test
	public void testWrongIdetifier(){
		runtest("1module123",
				(Token)null);
	}
	
	@Test 
	public void testIntegerLiteral(){
		runtest("module 1234 integer 234",
				new Token(MODULE, 0, 0, "module"),
				new Token(INT_LITERAL, 0, 7, "1234"),
				new Token(ID, 0, 12,"integer"),
				new Token(INT_LITERAL, 0, 20, "234"),
				new Token(EOF, 0, 23, ""));
	}
		
	@Test
	public void testStringLiteralWithDoubleQuote() {
		// testing string """
		runtest("\"\"\"",
				(Token)null);
	}
	
	@Test
	public void testStringLiteralWithDoubleQuote1(){
		// testing string "str"str"str"
		runtest("\"str\"str\"str\"",  
				(Token)null);
	}
	
	// ASK
	// no \n or no line break in between?
	// and why only one NULL will pass the test?
	@Test
	public void testStringLiteralEscapeCharacter() {
		// testing escape character \n
		// testing "
		//         "
		runtest("\"\n\"",
				(Token)null,
				new Token(STRING_LITERAL, 0, 1, "\n"),
				(Token)null,
				new Token(EOF, 1, 1, ""));
	}
	
	@Test
	public void testStringLiteralEscapeCharacter1() {
		// testing escape character \\
		runtest("\"\\n\"",
				new Token(STRING_LITERAL, 0, 0, "\\n"),
				new Token(EOF, 0, 4, ""));
	}
	
	@Test
	public void testStringLiteralEscapeCharacter2() {
		// testing valid string literal "abc"
		runtest("\"abc\"",
				new Token(STRING_LITERAL, 0, 0, "abc"),
				new Token(EOF, 0, 5, ""));
	}
	
	@Test
	public void testStringLiteralEscapeCharacter3() {
		// testing valid string literal with embedded escape sequence
		runtest("\"abc\\ndef\"",
				new Token(STRING_LITERAL, 0, 0, "abc\\ndef"),
				new Token(EOF, 0, 10, ""));
	}
}
