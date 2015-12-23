/* You do not need to change anything up here. */
package lexer;

import frontend.Token;
import static frontend.Token.Type.*;

%%

%public
%final
%class Lexer
%function nextToken
%type Token
%unicode
%line
%column

%{
	/* These two methods are for the convenience of rules to create toke objects.
	* If you do not want to use them, delete them
	* otherwise add the code in 
	*/
	
	private Token token(Token.Type type) {
		return new Token(type,this.yyline,this.yycolumn,yytext());
	}
	
	/* Use this method for rules where you need to process yytext() to get the lexeme of the token.
	 *
	 * Useful for string literals; e.g., the quotes around the literal are part of yytext(),
	 *       but they should not be part of the lexeme. 
	*/
	private Token token(Token.Type type, String text) {
			if(type.equals(Token.Type.STRING_LITERAL)){
				int length = text.length();
				text = text.substring(1,length - 1);
			}
			return new Token(type,this.yyline,this.yycolumn,text);
	}
%}

/* This definition may come in handy. If you wish, you can add more definitions here. */
WhiteSpace = [ ] | \t | \f | \n | \r
letter = [A-Za-z]
digit = [0-9]
alphanumeric = [0-9A-Za-z]
id_char = [_]
%%
/* put in your rules here.    */

/* punctuation symbols. */
"," 	        {return token(Token.Type.COMMA);}
"["		        {return token(Token.Type.LBRACKET);}
"{"		        {return token(Token.Type.LCURLY);}
"("		        {return token(Token.Type.LPAREN);}
"]"             {return token(Token.Type.RBRACKET);}
"}"             {return token(Token.Type.RCURLY);}
")"             {return token(Token.Type.RPAREN);}
";"             {return token(Token.Type.SEMICOLON);}

/* operators. */
"/"             {return token(Token.Type.DIV);}
"=="            {return token(Token.Type.EQEQ);}
"="             {return token(Token.Type.EQL);}
">="            {return token(Token.Type.GEQ);}
">"             {return token(Token.Type.GT);}
"<="            {return token(Token.Type.LEQ);}
"<"             {return token(Token.Type.LT);}
"-"             {return token(Token.Type.MINUS);}
"!="            {return token(Token.Type.NEQ);}
"+"             {return token(Token.Type.PLUS);}
"*"             {return token(Token.Type.TIMES);}

/* keywords    */
"boolean"       {return token(Token.Type.BOOLEAN);}
"break"         {return token(Token.Type.BREAK);}
"else"          {return token(Token.Type.ELSE);}
"false"         {return token(Token.Type.FALSE);}
"if"            {return token(Token.Type.IF);}
"import"        {return token(Token.Type.IMPORT);}
"int"           {return token(Token.Type.INT);}
"module"        {return token(Token.Type.MODULE);}
"public"        {return token(Token.Type.PUBLIC);}
"return"        {return token(Token.Type.RETURN);}
"true"          {return token(Token.Type.TRUE);}
"type"          {return token(Token.Type.TYPE);}
"void"          {return token(Token.Type.VOID);}
"while"         {return token(Token.Type.WHILE);}

{WhiteSpace}                                        {}
{digit}+					                        { return token(INT_LITERAL,yytext()); }
({letter}|{id_char})({alphanumeric}|{id_char})*     { return token(ID,yytext()); }
\"[^\"]*\"					                        { return token(STRING_LITERAL, yytext()); }

/* You don't need to change anything below this line. */
.							{ throw new Error("unexpected character '" + yytext() + "'"); }
<<EOF>>						{ return token(EOF); }
