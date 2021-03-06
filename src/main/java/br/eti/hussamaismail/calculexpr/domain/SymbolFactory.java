package br.eti.hussamaismail.calculexpr.domain;

import br.eti.hussamaismail.calculexpr.domain.enums.BracketType;
import br.eti.hussamaismail.calculexpr.domain.enums.FunctionType;
import br.eti.hussamaismail.calculexpr.domain.enums.OperatorType;
import br.eti.hussamaismail.calculexpr.exception.InvalidExpressionException;

/**
 * Factory responsible for creating symbols based on a token value.
 * 
 * @author Hussama Ismail
 */
public class SymbolFactory {

  private static SymbolFactory instance;

  public static final String REGEXP_NUMBER = "-?[0-9]+.?[0-9]+";
  public static final String REGEXP_NEGATIVE_NUMBER = "-[0-9]+.?[0-9]+";
  public static final String REGEXP_IDENTIFIER = "[a-zA-Z][a-zA-Z0-9]*|_";
  public static final String REGEXP_OPERATOR_ADDITION = "\\+";
  public static final String REGEXP_OPERATOR_SUBTRACTION = "-";
  public static final String REGEXP_OPERATOR_MULTIPLICATION = "\\*";
  public static final String REGEXP_OPERATOR_DIVISION = "/";
  public static final String REGEXP_FUNCTION_SIN = "sin";
  public static final String REGEXP_FUNCTION_COS = "cos";
  public static final String REGEXP_FUNCTION_LOG = "log";
  public static final String REGEXP_FUNCTION_SQRT = "sqrt";
  public static final String REGEXP_ASSIGNMENT = "=";
  public static final String REGEXP_PARENTHESES_START = "\\(";
  public static final String REGEXP_PARENTHESES_END = "\\)";

  private SymbolFactory() {}

  /**
   * Returns a singleton of SymbolFactory.
   * 
   * @return
   */
  public static SymbolFactory getInstance() {
    if (instance == null) {
      instance = new SymbolFactory();
    }
    return instance;
  }

  /**
   * Create a new object based on a token.
   * 
   * This method returns a symbol for the token passed as argument. Especially. the symbol generated
   * can be a Number, Operator, Function, or a Bracket.
   * 
   * The operators supported are: '+', '-', '/', '*', '='.
   * 
   * The functions supported are: 'sin', 'cos', 'sqrt', 'log'.
   * 
   * @param token
   * @return
   */
  public Symbol createSymbol(final String token) {

    Symbol symbol = null;;

    if (token.matches(REGEXP_NUMBER)) {
      symbol = new Operand(Double.valueOf(token));
    } else if (token.matches(REGEXP_OPERATOR_ADDITION)) {
      symbol = new Operator(OperatorType.ADDITION);
    } else if (token.matches(REGEXP_OPERATOR_SUBTRACTION)) {
      symbol = new Operator(OperatorType.SUBTRACTION);
    } else if (token.matches(REGEXP_OPERATOR_MULTIPLICATION)) {
      symbol = new Operator(OperatorType.MULTIPLICATION);
    } else if (token.matches(REGEXP_OPERATOR_DIVISION)) {
      symbol = new Operator(OperatorType.DIVISION);
    } else if (token.matches(REGEXP_ASSIGNMENT)) {
      symbol = new Operator(OperatorType.ASSIGNMENT);
    } else if (token.matches(REGEXP_FUNCTION_SIN)) {
      symbol = new Function(FunctionType.SIN);
    } else if (token.matches(REGEXP_FUNCTION_COS)) {
      symbol = new Function(FunctionType.COS);
    } else if (token.matches(REGEXP_FUNCTION_LOG)) {
      symbol = new Function(FunctionType.LOG);
    } else if (token.matches(REGEXP_FUNCTION_SQRT)) {
      symbol = new Function(FunctionType.SQRT);
    } else if (token.matches(REGEXP_PARENTHESES_START)) {
      symbol = new Bracket(BracketType.PARENTHESES_START);
    } else if (token.matches(REGEXP_PARENTHESES_END)) {
      symbol = new Bracket(BracketType.PARENTHESES_END);
    } else if (token.matches(REGEXP_IDENTIFIER)) {
      symbol = new Identifier(token);
    } else {
      throw new InvalidExpressionException();
    }

    return symbol;
  }

}
