import java.text.ParseException;

class Parser {
    private LexicalAnalyzer lex;

    private Tree S() throws ParseException {
        switch (lex.curToken) {
            case LPAREN:
            case CONST:
            case NEG:
                Tree sub = D();
                if (lex.curToken() == Token.IMPL) {
                    lex.nextToken();
                    Tree cont = S();
                    return new Tree("S", sub, new Tree("->"), cont);
                }
                return sub;
            default:
                throw new AssertionError();
        }
    }

    private Tree D() throws ParseException {
        switch (lex.curToken) {
            case LPAREN:
            case CONST:
            case NEG:
                Tree sub = K();
                if (lex.curToken() == Token.OR) {
                    lex.nextToken();
                    Tree cont = D();
                    return new Tree("D", sub, new Tree("|"), cont);
                }
                return sub;
            default:
                throw new AssertionError();
        }
    }

    private Tree K() throws ParseException {
        switch (lex.curToken) {
            case LPAREN:
            case CONST:
            case NEG:
                Tree sub = N();
                if (lex.curToken() == Token.AND) {
                    lex.nextToken();
                    Tree cont = K();
                    return new Tree("K", sub, new Tree("&"), cont);
                }
                return sub;
            default:
                throw new AssertionError();
        }
    }

    private Tree N() throws ParseException {
        switch (lex.curToken()) {
            case NEG:
                lex.nextToken();
                return new Tree("N", new Tree("!"), N());
            case CONST:
                lex.nextToken();
                return new Tree("N", new Tree(lex.numbers.remove(0) + " "));
            case LPAREN:
                lex.nextToken();
                Tree sub = S();
                if (lex.curToken != Token.RPAREN) {
                    throw new AssertionError();
                }
                lex.nextToken();
                return sub;
            default:
                throw new AssertionError();
        }
    }

    Tree parse(String is) throws ParseException {
        lex = new LexicalAnalyzer(is);
        lex.nextToken();
        Tree s = S();
        if (lex.curToken() != Token.END) {
            throw new AssertionError();
        }
        return s;
    }
}
