import java.text.ParseException;
import java.util.ArrayList;

class PredicateLexicalAnalyzer {
    private String is;
    private int curChar;
    private int curPos;
    private int prevPos;
    PredicateToken curToken;
    ArrayList<String> numbers = new ArrayList<>();

    PredicateLexicalAnalyzer(String is) throws ParseException {
        this.is = is;
        curPos = 0;
        nextChar();
    }

    PredicateLexicalAnalyzer(PredicateLexicalAnalyzer other) throws  ParseException {
        this.is = other.is;
        this.curChar = other.curChar;
        this.curPos = other.curPos;
        this.prevPos = other.prevPos;
        this.curToken = other.curToken;
        this.numbers = other.numbers;
    }

    private boolean isBlank(int c) {
        return c == ' ' || c == '\r' || c == '\n' || c == '\t';
    }

    private void nextChar() throws ParseException {
        curPos++;
        try {
            curChar = is.charAt(curPos - 1);
        } catch (IndexOutOfBoundsException e) {
            curChar = -1;
        }
    }

    void nextToken() throws ParseException {
        while (isBlank(curChar)) {
            nextChar();
        }
        prevPos = curPos - 1;
        switch (curChar) {
            case '(':
                nextChar();
                curToken = PredicateToken.LPAREN;
                break;
            case ')':
                nextChar();
                curToken = PredicateToken.RPAREN;
                break;
            case '|':
                nextChar();
                curToken = PredicateToken.OR;
                break;
            case '!':
                nextChar();
                curToken = PredicateToken.NEG;
                break;
            case '-':
                nextChar();
                nextChar();
                curToken = PredicateToken.IMPL;
                break;
            case '&':
                nextChar();
                curToken = PredicateToken.AND;
                break;
            case '@':
                nextChar();
                curToken = PredicateToken.ALL;
                break;
            case '?':
                nextChar();
                curToken = PredicateToken.ANY;
                break;
            case '0':
                nextChar();
                curToken = PredicateToken.ZERO;
                break;
            case '\'':
                nextChar();
                curToken = PredicateToken.S;
                break;
            case '+':
                nextChar();
                curToken = PredicateToken.PLUS;
                break;
            case '*':
                nextChar();
                curToken = PredicateToken.MUL;
                break;
            case '=':
                nextChar();
                curToken = PredicateToken.EQ;
                break;
            case ',':
                nextChar();
                curToken = PredicateToken.COMMA;
                break;
            case -1:
                curToken = PredicateToken.END;
                break;
            default:
                if (curChar <= 'z' && curChar >= 'a') {
                    numbers.add(findNumber());
                    curToken = PredicateToken.CONST;
                } else {
                    if (curChar <= 'Z' && curChar >= 'A') {
                        numbers.add(findNumber());
                        curToken = PredicateToken.PREDICATE;
                    } else {
                        throw new ParseException("Illegal character " + (char) curChar, curPos);
                    }
                }
        }
    }

    private String findNumber() throws ParseException {
        String s = "";
        do {
            s += (char) curChar;
            nextChar();
        } while (curChar <= '9' && curChar >= '0');
        return s;
    }

    PredicateToken curToken() {
        return curToken;
    }

    int getPrevPos() {
        return prevPos;
    }

    void setCurPos(int p) {
        curPos = p;
    }
}
