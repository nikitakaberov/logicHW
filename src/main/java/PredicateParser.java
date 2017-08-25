import java.text.ParseException;

class PredicateParser {
    private PredicateLexicalAnalyzer lex;

    private Tree S() throws ParseException {
        switch (lex.curToken) {
            case LPAREN:
            case CONST:
            case NEG:
            case ALL:
            case ANY:
            case PREDICATE:
            case ZERO:
                Tree sub = D();
                if (lex.curToken() == PredicateToken.IMPL) {
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
            case ALL:
            case ANY:
            case PREDICATE:
            case ZERO:
                Tree sub = K();
                if (lex.curToken() == PredicateToken.OR) {
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
            case ALL:
            case ANY:
            case PREDICATE:
            case ZERO:
                Tree sub = N();
                if (lex.curToken() == PredicateToken.AND) {
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
            case ALL:
                lex.nextToken();
                if (lex.curToken == PredicateToken.CONST) {
                    lex.nextToken();
                    Tree subN = N();
                    return new Tree("N", new Tree("@"), new Tree(lex.numbers.remove(lex.numbers.size() - 1) + " "), subN);
                } else {
                    throw new AssertionError();
                }
            case ANY:
                lex.nextToken();
                if (lex.curToken == PredicateToken.CONST) {
                    lex.nextToken();
                    Tree subN = N();
                    return new Tree("N", new Tree("?"), new Tree(lex.numbers.remove(lex.numbers.size() - 1) + " "), subN);
                } else {
                    throw new AssertionError();
                }
            case PREDICATE:
            case ZERO:
            case CONST:
                return P();
            case LPAREN:
                PredicateLexicalAnalyzer newLex = new PredicateLexicalAnalyzer(lex);
                int sz = lex.numbers.size();
                Tree p = P();
                if (p != null) {
                    return p;
                }
                while (sz != lex.numbers.size()) {
                    lex.numbers.remove(lex.numbers.size() - 1);
                }
                lex = new PredicateLexicalAnalyzer(newLex);
                lex.nextToken();
                Tree sub = S();
                if (lex.curToken != PredicateToken.RPAREN) {
                    throw new AssertionError();
                }
                lex.nextToken();
                return sub;
            default:
                throw new AssertionError();
        }
    }

    private Tree P() throws ParseException {
        switch (lex.curToken) {
            case PREDICATE:
                lex.nextToken();
                if (lex.curToken != PredicateToken.LPAREN) {
                    return new Tree(lex.numbers.remove(lex.numbers.size() - 1) + " ");
                }
                lex.nextToken();
                Tree t = T();
                if (lex.curToken == PredicateToken.RPAREN) {
                    lex.nextToken();
                    return new Tree("P", new Tree(lex.numbers.remove(lex.numbers.size() - 1) + " "), new Tree("("), t, new Tree(")"));
                }
                if (lex.curToken == PredicateToken.COMMA) {
                    lex.nextToken();
                    Tree sub = Ts();
                    if (lex.curToken == PredicateToken.RPAREN) {
                        lex.nextToken();
                        return new Tree("P", new Tree(lex.numbers.remove(lex.numbers.size() - 1) + " "), new Tree("("), t, sub, new Tree(")"));
                    } else {
                        throw new AssertionError();
                    }
                }
            case CONST:
            case ZERO:
            case LPAREN:
                Tree sub = T();
                if (sub == null) {
                    return null;
                }
                if (lex.curToken != PredicateToken.EQ) {
                    throw new AssertionError();
                }
                lex.nextToken();
                Tree sub1 = T();
                return new Tree("P", sub, new Tree("="), sub1);
            default:
                return null;
        }
    }

    private Tree Ts() throws ParseException {
        Tree sub = T();
        if (lex.curToken == PredicateToken.COMMA) {
            lex.nextToken();
            Tree subs = Ts();
            return new Tree("Ts", new Tree(","), sub, subs);
        }
        return new Tree("Ts", new Tree(","), sub);
    }

    private Tree T() throws ParseException {
        switch (lex.curToken) {
            case CONST:
            case ZERO:
            case LPAREN:
                Tree sub = SL();
                if (lex.curToken == PredicateToken.PLUS) {
                    lex.nextToken();
                    Tree t = T();
                    return new Tree("T", sub, new Tree("+"), t);
                }
                return sub;
        }
        return null;
    }

    private Tree SL() throws ParseException {
        switch (lex.curToken) {
            case CONST:
            case ZERO:
            case LPAREN:
                Tree sub = M();
                if (lex.curToken == PredicateToken.MUL) {
                    lex.nextToken();
                    Tree t = SL();
                    return new Tree("T", sub, new Tree("*"), t);
                }
                return sub;
        }
        return null;
    }

    private Tree M() throws ParseException {
        switch (lex.curToken) {
            case CONST:
                lex.nextToken();
                String s = lex.numbers.remove(lex.numbers.size() - 1) + " ";
                if (lex.curToken == PredicateToken.S) {
                    while (lex.curToken == PredicateToken.S) {
                        s += "\'";
                        lex.nextToken();
                    }
                    return new Tree(s);
                }
                if (lex.curToken == PredicateToken.LPAREN) {
                    lex.nextToken();
                    Tree subT = T();
                    Tree sub = null;
                    if (lex.curToken == PredicateToken.RPAREN) {
                        lex.nextToken();
                    } else {
                        if (lex.curToken == PredicateToken.COMMA) {
                            lex.nextToken();
                            sub = Ts();
                            lex.nextToken();
                        }
                    }
                    if (lex.curToken == PredicateToken.S) {
                        String h = "";
                        while (lex.curToken == PredicateToken.S) {
                            h += "\'";
                            lex.nextToken();
                        }
                        if (sub == null) {
                            return new Tree("M", new Tree(s), new Tree("("), subT, new Tree(")"), new Tree(h));
                        } else {
                            return new Tree("M", new Tree(s), new Tree("("), subT, sub, new Tree(")"), new Tree(h));
                        }
                    }
                    if (sub == null) {
                        return new Tree("M", new Tree(s), new Tree("("), subT, new Tree(")"));
                    } else {
                        return new Tree("M", new Tree(s), new Tree("("), subT, sub, new Tree(")"));
                    }
                }
                return new Tree(s);
            case ZERO:
                lex.nextToken();
                String str = "0";
                while (lex.curToken == PredicateToken.S) {
                    str += "\'";
                    lex.nextToken();
                }
                return new Tree(str);
            case LPAREN:
                lex.nextToken();
                Tree sub = T();
                if (lex.curToken == PredicateToken.RPAREN) {
                    lex.nextToken();
                    if (lex.curToken == PredicateToken.S) {
                        String add = "";
                        while (lex.curToken == PredicateToken.S) {
                            add += "\'";
                            lex.nextToken();
                        }
                        if (sub != null && sub.children == null) {
                            return new Tree(sub.node + add);
                        }
                        return new Tree("M", sub, new Tree(add));
                    }
                    return sub;
                }
                return null;
        }
        return null;
    }

    Tree parse(String is) throws ParseException {
        lex = new PredicateLexicalAnalyzer(is);
        lex.nextToken();
        Tree s = S();
        if (lex.curToken() != PredicateToken.END) {
            throw new AssertionError();
        }
        return s;
    }
}
