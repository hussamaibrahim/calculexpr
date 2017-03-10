package br.eti.hussamaismail.calculexpr.parse;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class contains a small abstraction for a lexical analyzer used by the ExpressionEvaluator.
 * 
 * @author Hussama Ismail
 */
public class LexicalAnalyzer {

  private static final String REGEXP_NUMBER = "-?[0-9]+.?[0-9]";
  private static final String REGEXP_OPERATOR = "\\+|-|\\*|/";
  private static final String REGEXP_FUNCTION = "sin|cos|sqrt|log";
  private static final String REGEXP_IDENTIFIER = "[a-zA-Z][a-zA-Z0-9]*";
  private static final String REGEXP_ASSIGNMENT = "=";
  private static final String REGEXP_BEGIN_EXPRESSION = "\\(";
  private static final String REGEXP_END_EXPRESSION = "\\)";

  private static LexicalAnalyzer instance;

  private LexicalAnalyzer() {}

  public static LexicalAnalyzer getInstance() {
    if (instance == null) {
      instance = new LexicalAnalyzer();
    }
    return instance;
  }

  /**
   * Retrieve all tokens of an expression.
   * 
   * @param expression
   * @return
   * @throws IOException
   */
  public List<Token> getTokens(final String expression) throws IOException {
    final List<Token> tokens = new ArrayList<>();
    final StringReader stringReader = new StringReader(expression);
    final StreamTokenizer tokenizer = new StreamTokenizer(stringReader);
    while (StreamTokenizer.TT_EOF != tokenizer.nextToken()) {
      switch (tokenizer.ttype) {
        case StreamTokenizer.TT_WORD:
          tokens.add(recognizeToken(tokenizer.sval));
          break;
        case StreamTokenizer.TT_NUMBER:
          tokens.add(recognizeToken(tokenizer.nval));
          break;
        default:
          tokens.add(recognizeToken((char) tokenizer.ttype));
      }
    }
    stringReader.close();
    return tokens;
  }

  /**
   * This method is responsible to recognize the type of the token
   * 
   * @param value
   * @return
   */
  public Token recognizeToken(final Object value) {
    final String valueString = String.valueOf(value);
    final Token token = new Token();
    token.setValue(valueString);
    if (valueString.matches(REGEXP_NUMBER)) {
      token.setType(TokenType.NUMBER);
    } else if (valueString.matches(REGEXP_OPERATOR)) {
      token.setType(TokenType.OPERATOR);
    } else if (valueString.matches(REGEXP_FUNCTION)) {
      token.setType(TokenType.FUNCTION);
    } else if (valueString.matches(REGEXP_IDENTIFIER)) {
      token.setType(TokenType.IDENTIFIER);
    } else if (valueString.matches(REGEXP_ASSIGNMENT)) {
      token.setType(TokenType.ASSIGNMENT);
    } else if (valueString.matches(REGEXP_BEGIN_EXPRESSION)) {
      token.setType(TokenType.BEGIN_EXPRESSION);
    } else if (valueString.matches(REGEXP_END_EXPRESSION)) {
      token.setType(TokenType.END_EXPRESSION);
    } else {
      throw new RuntimeException(value + " is not a valid token");
    }
    return token;
  }

}