import java.util.*;

class Tree {
    String node;
    List<Tree> children;

    Tree(String node, Tree... children) {
        this.node = node;
        this.children = Arrays.asList(children);
    }

    Tree(String node) {
        this.node = node;
    }

    String getString() {
        if (this.children == null) {
            return this.node.replace(" ", "");
        }
        if (this.children.size() == 2) {
            return "!(" + this.children.get(1).getString() + ")";
        }
        if (this.children.size() == 1) {
            return "(" + this.children.get(0).getString() + ")";
        }
        if (this.children.size() == 3) {
            return "(" + this.children.get(0).getString() + this.children.get(1).node + this.children.get(2).getString() + ")";
        }
        return "";
    }

    String getPredicateString() {
        if (this.children == null) {
            return this.node.replace(" ", "");
        }
        if (this.children.size() == 2) {
            if (this.node.equals("Ts")) {
                if (this.children.get(1).node.contains("\'")) {
                    return this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString();
                }
                return this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString();
            } else {
                if (this.children.get(1).node.contains("\'")) {
                    return this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString();
                }
                return "(" + this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString() + ")";
            }
        }
        if (this.children.size() == 1) {
            return "(" + this.children.get(0).getPredicateString() + ")";
        }
        if (this.children.size() == 3) {
            if (this.node.equals("Ts")) {
                return this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString()
                        + this.children.get(2).getPredicateString();
            } else {
                return "(" + this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString()
                        + this.children.get(2).getPredicateString() + ")";
            }
        }
        if (this.children.size() == 4) {
            return "(" + this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString()
                    + this.children.get(2).getPredicateString() + this.children.get(3).getPredicateString() + ")";
        }
        if (this.children.size() == 5) {
            return "(" + this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString()
                    + this.children.get(2).getPredicateString() + this.children.get(3).getPredicateString()
                    + this.children.get(4).getPredicateString() + ")";
        }
        if (this.children.size() == 6) {
            return "(" + this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString()
                    + this.children.get(2).getPredicateString() + this.children.get(3).getPredicateString()
                    + this.children.get(4).getPredicateString() + this.children.get(5).getPredicateString() + ")";
        }
        if (this.children.size() == 7) {
            return "(" + this.children.get(0).getPredicateString() + this.children.get(1).getPredicateString()
                    + this.children.get(2).getPredicateString() + this.children.get(3).getPredicateString()
                    + this.children.get(4).getPredicateString() + this.children.get(5).getPredicateString()
                    + this.children.get(6).getPredicateString() + ")";
        }
        return "";
    }

    String getExpression(String s) {
        if (this.children == null) {
            s += this.node;
            return s;
        }
        if (this.children.size() == 2) {
            s += "!";
            s = this.children.get(1).getExpression(s);
            return s;
        }
        if (this.children.size() == 1) {
            s = this.children.get(0).getExpression(s);
            return s;
        }
        if (this.children.size() == 3) {
            s += this.children.get(1).node;
            s = this.children.get(0).getExpression(s);
            s = this.children.get(2).getExpression(s);
            return s;
        }
        return s;
    }

    String getPredicateExpression(String s) {
        if (this.children == null) {
            s += this.node;
            return s;
        }
        if (this.children.size() == 2) {
            s = this.children.get(0).getPredicateExpression(s);
            s = this.children.get(1).getPredicateExpression(s);
            return s;
        }
        if (this.children.size() == 1) {
            s = this.children.get(0).getPredicateExpression(s);
            return s;
        }
        if (this.children.size() == 3) {
            if (this.children.get(0).node.equals("?") || this.children.get(0).node.equals("@")) {
                s += this.children.get(0).node;
                s = this.children.get(1).getPredicateExpression(s);
                s = this.children.get(2).getPredicateExpression(s);
                return s;
            } else {
                s += this.children.get(1).node;
                s = this.children.get(0).getPredicateExpression(s);
                s = this.children.get(2).getPredicateExpression(s);
                return s;
            }
        }
        if (this.children.size() == 4) {
            s = this.children.get(0).getPredicateExpression(s);
            s = this.children.get(1).getPredicateExpression(s);
            s = this.children.get(2).getPredicateExpression(s);
            s = this.children.get(3).getPredicateExpression(s);
            return s;
        }
        if (this.children.size() == 5) {
            s = this.children.get(0).getPredicateExpression(s);
            s = this.children.get(1).getPredicateExpression(s);
            s = this.children.get(2).getPredicateExpression(s);
            s = this.children.get(3).getPredicateExpression(s);
            s = this.children.get(4).getPredicateExpression(s);
            return s;
        }
        if (this.children.size() == 6) {
            s = this.children.get(0).getPredicateExpression(s);
            s = this.children.get(1).getPredicateExpression(s);
            s = this.children.get(2).getPredicateExpression(s);
            s = this.children.get(3).getPredicateExpression(s);
            s = this.children.get(4).getPredicateExpression(s);
            s = this.children.get(5).getPredicateExpression(s);
            return s;
        }
        if (this.children.size() == 7) {
            s = this.children.get(0).getPredicateExpression(s);
            s = this.children.get(1).getPredicateExpression(s);
            s = this.children.get(2).getPredicateExpression(s);
            s = this.children.get(3).getPredicateExpression(s);
            s = this.children.get(4).getPredicateExpression(s);
            s = this.children.get(5).getPredicateExpression(s);
            s = this.children.get(6).getPredicateExpression(s);
            return s;
        }
        return s;
    }

    boolean getResult(Map<String, Boolean> a) {
        if (this.children == null) {
            return a.get(this.node);
        }
        if (this.children.size() == 1) {
            return this.children.get(0).getResult(a);
        }
        if (this.children.size() == 2) {
            return !this.children.get(1).getResult(a);
        }
        boolean first = this.children.get(0).getResult(a);
        boolean second = this.children.get(2).getResult(a);
        switch (this.children.get(1).node) {
            case "->":
                return !first || second;
            case "|":
                return first || second;
            default:
                return first && second;
        }
    }

    boolean isAxiom(int i) {
        switch (i) {
            case 1:
                return isAxiom1();
            case 2:
                return isAxiom2();
            case 3:
                return isAxiom3();
            case 4:
                return isAxiom4();
            case 5:
                return isAxiom5();
            case 6:
                return isAxiom6();
            case 7:
                return isAxiom7();
            case 8:
                return isAxiom8();
            case 9:
                return isAxiom9();
            case 10:
                return isAxiom10();
            default:
                return false;
        }
    }

    private boolean isAxiom1() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("->")) {
            return false;
        }
        return t.children.get(2).getExpression("").equals(this.children.get(0).getExpression(""));
    }

    private boolean isAxiom2() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree AB = this.children.get(0);
        if (AB.children == null || AB.children.size() != 3 || !AB.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = AB.children.get(0);
        Tree B = AB.children.get(2);
        Tree tmp = this.children.get(2);
        if (tmp.children == null || tmp.children.size() != 3 || !tmp.children.get(1).node.equals("->")) {
            return false;
        }
        Tree ABC = tmp.children.get(0);
        if (ABC.children == null || ABC.children.size() != 3 || !ABC.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A1 = ABC.children.get(0);
        ABC = ABC.children.get(2);
        if (ABC.children == null || ABC.children.size() != 3 || !ABC.children.get(1).node.equals("->")) {
            return false;
        }
        Tree B1 = ABC.children.get(0);
        Tree C1 = ABC.children.get(2);
        Tree AC = tmp.children.get(2);
        if (AC.children == null || AC.children.size() != 3 || !AC.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A2 = AC.children.get(0);
        Tree C2 = AC.children.get(2);
        return A.getExpression("").equals(A1.getExpression("")) && A.getExpression("").equals(A2.getExpression(""))
                && B.getExpression("").equals(B1.getExpression("")) && C1.getExpression("").equals(C2.getExpression(""));
    }

    private boolean isAxiom3() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(0);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("&")) {
            return false;
        }
        return t.children.get(0).getExpression("").equals(this.children.get(2).getExpression(""));
    }

    private boolean isAxiom4() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(0);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("&")) {
            return false;
        }
        return t.children.get(2).getExpression("").equals(this.children.get(2).getExpression(""));
    }

    private boolean isAxiom5() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        String A = this.children.get(0).getExpression("");
        Tree t = this.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("->")) {
            return false;
        }
        String B = t.children.get(0).getExpression("");
        t = t.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("&")) {
            return false;
        }
        return A.equals(t.children.get(0).getExpression("")) && B.equals(t.children.get(2).getExpression(""));
    }

    private boolean isAxiom6() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(2);
        ;
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("|")) {
            return false;
        }
        return t.children.get(0).getExpression("").equals(this.children.get(0).getExpression(""));
    }

    private boolean isAxiom7() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("|")) {
            return false;
        }
        return t.children.get(2).getExpression("").equals(this.children.get(0).getExpression(""));
    }

    private boolean isAxiom8() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree AQ = this.children.get(0);
        if (AQ.children == null || AQ.children.size() != 3 || !AQ.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = AQ.children.get(0);
        Tree Q = AQ.children.get(2);
        Tree tmp = this.children.get(2);
        if (tmp.children == null || tmp.children.size() != 3 || !tmp.children.get(1).node.equals("->")) {
            return false;
        }
        Tree BQ = tmp.children.get(0);
        if (BQ.children == null || BQ.children.size() != 3 || !BQ.children.get(1).node.equals("->")) {
            return false;
        }
        Tree B = BQ.children.get(0);
        Tree Q1 = BQ.children.get(2);
        Tree ABQ = tmp.children.get(2);
        if (ABQ.children == null || ABQ.children.size() != 3 || !ABQ.children.get(1).node.equals("->")) {
            return false;
        }
        Tree Q2 = ABQ.children.get(2);
        Tree AB = ABQ.children.get(0);
        if (AB.children == null || AB.children.size() != 3 || !AB.children.get(1).node.equals("|")) {
            return false;
        }
        Tree A1 = AB.children.get(0);
        Tree B1 = AB.children.get(2);
        return Q.getExpression("").equals(Q1.getExpression("")) && Q.getExpression("").equals(Q2.getExpression(""))
                && B.getExpression("").equals(B1.getExpression("")) && A1.getExpression("").equals(A.getExpression(""));
    }

    private boolean isAxiom9() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = this.children.get(0);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("->")) {
            return false;
        }
        String a = A.children.get(0).getExpression("");
        String b = "!" + A.children.get(2).getExpression("");
        A = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("->")) {
            return false;
        }
        A = A.children.get(0);
        Tree t = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("->")) {
            return false;
        }
        String a1 = A.children.get(0).getExpression("");
        String b1 = A.children.get(2).getExpression("");
        if (!a.equals(a1) || !b.equals(b1)) {
            return false;
        }
        t = t.children.get(2);
        a = "!" + a;
        return a.equals(t.getExpression(""));
    }

    private boolean isAxiom10() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        String NotNotA = this.children.get(0).getExpression("");
        String A = this.children.get(2).getExpression("!!");
        return NotNotA.equals(A);
    }

    boolean isPredicateAxiom(int i) {
        switch (i) {
            case 1:
                return isPredicateAxiom1();
            case 2:
                return isPredicateAxiom2();
            case 3:
                return isPredicateAxiom3();
            case 4:
                return isPredicateAxiom4();
            case 5:
                return isPredicateAxiom5();
            case 6:
                return isPredicateAxiom6();
            case 7:
                return isPredicateAxiom7();
            case 8:
                return isPredicateAxiom8();
            case 9:
                return isPredicateAxiom9();
            case 10:
                return isPredicateAxiom10();
            case 11:
                return isPredicateAxiom11();
            case 12:
                return isPredicateAxiom12();
            case 13:
                return isPredicateAxiom13();
            case 14:
                return isPredicateAxiom14();
            case 15:
                return isPredicateAxiom15();
            case 16:
                return isPredicateAxiom16();
            case 17:
                return isPredicateAxiom17();
            case 18:
                return isPredicateAxiom18();
            case 19:
                return isPredicateAxiom19();
            case 20:
                return isPredicateAxiom20();
            case 21:
                return isPredicateAxiom21();
            default:
                return false;
        }
    }

    private boolean isPredicateAxiom1() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("->")) {
            return false;
        }
        return t.children.get(2).getPredicateString().equals(this.children.get(0).getPredicateString());
    }

    private boolean isPredicateAxiom2() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree AB = this.children.get(0);
        if (AB.children == null || AB.children.size() != 3 || !AB.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = AB.children.get(0);
        Tree B = AB.children.get(2);
        Tree tmp = this.children.get(2);
        if (tmp.children == null || tmp.children.size() != 3 || !tmp.children.get(1).node.equals("->")) {
            return false;
        }
        Tree ABC = tmp.children.get(0);
        if (ABC.children == null || ABC.children.size() != 3 || !ABC.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A1 = ABC.children.get(0);
        ABC = ABC.children.get(2);
        if (ABC.children == null || ABC.children.size() != 3 || !ABC.children.get(1).node.equals("->")) {
            return false;
        }
        Tree B1 = ABC.children.get(0);
        Tree C1 = ABC.children.get(2);
        Tree AC = tmp.children.get(2);
        if (AC.children == null || AC.children.size() != 3 || !AC.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A2 = AC.children.get(0);
        Tree C2 = AC.children.get(2);
        return A.getPredicateString().equals(A1.getPredicateString()) && A.getPredicateString().equals(A2.getPredicateString())
                && B.getPredicateString().equals(B1.getPredicateString()) && C1.getPredicateString().equals(C2.getPredicateString());
    }

    private boolean isPredicateAxiom3() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(0);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("&")) {
            return false;
        }
        return t.children.get(0).getPredicateString().equals(this.children.get(2).getPredicateString());
    }

    private boolean isPredicateAxiom4() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(0);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("&")) {
            return false;
        }
        return t.children.get(2).getPredicateString().equals(this.children.get(2).getPredicateString());
    }

    private boolean isPredicateAxiom5() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        String A = this.children.get(0).getPredicateString();
        Tree t = this.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("->")) {
            return false;
        }
        String B = t.children.get(0).getPredicateString();
        t = t.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("&")) {
            return false;
        }
        return A.equals(t.children.get(0).getPredicateString()) && B.equals(t.children.get(2).getPredicateString());
    }

    private boolean isPredicateAxiom6() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(2);
        ;
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("|")) {
            return false;
        }
        return t.children.get(0).getPredicateString().equals(this.children.get(0).getPredicateString());
    }

    private boolean isPredicateAxiom7() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree t = this.children.get(2);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("|")) {
            return false;
        }
        return t.children.get(2).getPredicateString().equals(this.children.get(0).getPredicateString());
    }

    private boolean isPredicateAxiom8() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree AQ = this.children.get(0);
        if (AQ.children == null || AQ.children.size() != 3 || !AQ.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = AQ.children.get(0);
        Tree Q = AQ.children.get(2);
        Tree tmp = this.children.get(2);
        if (tmp.children == null || tmp.children.size() != 3 || !tmp.children.get(1).node.equals("->")) {
            return false;
        }
        Tree BQ = tmp.children.get(0);
        if (BQ.children == null || BQ.children.size() != 3 || !BQ.children.get(1).node.equals("->")) {
            return false;
        }
        Tree B = BQ.children.get(0);
        Tree Q1 = BQ.children.get(2);
        Tree ABQ = tmp.children.get(2);
        if (ABQ.children == null || ABQ.children.size() != 3 || !ABQ.children.get(1).node.equals("->")) {
            return false;
        }
        Tree Q2 = ABQ.children.get(2);
        Tree AB = ABQ.children.get(0);
        if (AB.children == null || AB.children.size() != 3 || !AB.children.get(1).node.equals("|")) {
            return false;
        }
        Tree A1 = AB.children.get(0);
        Tree B1 = AB.children.get(2);
        return Q.getPredicateString().equals(Q1.getPredicateString()) && Q.getPredicateString().equals(Q2.getPredicateString())
                && B.getPredicateString().equals(B1.getPredicateString()) && A1.getPredicateString().equals(A.getPredicateString());
    }

    private boolean isPredicateAxiom9() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = this.children.get(0);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("->")) {
            return false;
        }
        String a = A.children.get(0).getExpression("");
        String b = "!" + A.children.get(2).getExpression("");
        A = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("->")) {
            return false;
        }
        A = A.children.get(0);
        Tree t = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("->")) {
            return false;
        }
        String a1 = A.children.get(0).getExpression("");
        String b1 = A.children.get(2).getExpression("");
        if (!a.equals(a1) || !b.equals(b1)) {
            return false;
        }
        t = t.children.get(2);
        a = "!" + a;
        return a.equals(t.getExpression(""));
    }

    private boolean isPredicateAxiom10() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree tmp = this.children.get(0);
        if (tmp.children == null || tmp.children.size() != 2 || !tmp.children.get(0).node.equals("!")) {
            return false;
        }
        tmp = tmp.children.get(1);
        if (tmp.children == null || tmp.children.size() != 2 || !tmp.children.get(0).node.equals("!")) {
            return false;
        }
        String NotNotA = tmp.children.get(1).getPredicateString();
        String A = this.children.get(2).getPredicateString();
        return NotNotA.equals(A);
    }

    private boolean isPredicateAxiom11() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        if (this.children.get(0).children == null || !this.children.get(0).children.get(0).node.equals("@")) {
            return false;
        }
        Set<String> s = new HashSet<>();
        Set<String> s1 = new HashSet<>();
        return this.children.get(0).children.get(2).refresh(s).equals(this.children.get(2).refresh(s1));
    }

    private boolean isPredicateAxiom12() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        if (this.children.get(2).children == null || !this.children.get(2).children.get(0).node.equals("?")) {
            return false;
        }
        Set<String> s1 = new HashSet<>();
        Set<String> s2 = new HashSet<>();
        return this.children.get(2).children.get(2).refresh(s1).equals(this.children.get(0).refresh(s2));
    }

    private boolean isPredicateAxiom13() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = this.children.get(0);
        Tree B = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("=")) {
            return false;
        }
        if (B.children == null || B.children.size() != 3 || !B.children.get(1).node.equals("=")) {
            return false;
        }
        Tree A1 = A.children.get(0);
        Tree A2 = A.children.get(2);
        Tree B1 = B.children.get(0);
        Tree B2 = B.children.get(2);
        return B1.getPredicateExpression("").equals(A1.getPredicateExpression("") + "\'") && B2.getPredicateExpression("").equals(A2.getPredicateExpression("") + "\'");
    }

    private boolean isPredicateAxiom14() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = this.children.get(0);
        Tree B = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("=")) {
            return false;
        }
        if (B.children == null || B.children.size() != 3 || !B.children.get(1).node.equals("->")) {
            return false;
        }
        Tree C = B.children.get(0);
        Tree D = B.children.get(2);
        if (C.children == null || C.children.size() != 3 || !C.children.get(1).node.equals("=")) {
            return false;
        }
        if (D.children == null || D.children.size() != 3 || !D.children.get(1).node.equals("=")) {
            return false;
        }
        String A1 = A.children.get(0).getPredicateExpression("");
        String B1 = A.children.get(2).getPredicateExpression("");
        String A2 = C.children.get(0).getPredicateExpression("");
        String C1 = C.children.get(2).getPredicateExpression("");
        String B2 = D.children.get(0).getPredicateExpression("");
        String C2 = D.children.get(2).getPredicateExpression("");
        return A1.equals(A2) && B1.equals(B2) && C1.equals(C2);
    }

    private boolean isPredicateAxiom15() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        Tree A = this.children.get(0);
        Tree B = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("=")) {
            return false;
        }
        if (B.children == null || B.children.size() != 3 || !B.children.get(1).node.equals("=")) {
            return false;
        }
        Tree A1 = A.children.get(0);
        Tree A2 = A.children.get(2);
        Tree B1 = B.children.get(0);
        Tree B2 = B.children.get(2);
        return A1.getPredicateExpression("").equals(B1.getPredicateExpression("") + "\'") && A2.getPredicateExpression("").equals(B2.getPredicateExpression("") + "\'");
    }

    private boolean isPredicateAxiom16() {
        if (this.children == null || this.children.size() != 2 || !this.children.get(0).node.equals("!")) {
            return false;
        }
        if (this.children.get(1).children == null || this.children.get(1).children.size() != 3
                || !this.children.get(1).children.get(1).node.equals("=")) {
            return false;
        }
        Tree A = this.children.get(1).children.get(0);
        Tree B = this.children.get(1).children.get(2);
        return B.node.equals("0") && A.children == null && (A.node + "%").contains(" \'%");
    }

    private boolean isPredicateAxiom17() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("=")) {
            return false;
        }
        Tree A = this.children.get(0);
        Tree B = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("+")) {
            return false;
        }
        Tree A1 = A.children.get(2);
        A = A.children.get(0);
        if (A1.children != null || A.children != null && !(A1.getPredicateExpression("") + "%").contains(" \'%")) {
            return false;
        }
        if (B.children == null || B.children.size() != 2 || !B.children.get(1).node.equals("\'")) {
            return false;
        }
        B = B.children.get(0);
        if (B.children == null || B.children.size() != 3 || !B.children.get(1).node.equals("+")) {
            return false;
        }
        String a = A.getPredicateExpression("");
        String b = A1.getPredicateExpression("");
        String a1 = B.children.get(0).getPredicateExpression("");
        String b1 = B.children.get(2).getPredicateExpression("") + "\'";
        return a.equals(a1) && b.equals(b1);
    }

    private boolean isPredicateAxiom18() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("=")) {
            return false;
        }
        String A = this.children.get(2).getPredicateExpression("");
        Tree t = this.children.get(0);
        if (t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("+") || !t.children.get(2).node.equals("0")) {
            return false;
        }
        String A1 = t.children.get(0).getPredicateExpression("");
        return A.equals(A1);
    }

    private boolean isPredicateAxiom19() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("=") || !this.children.get(2).node.equals("0")) {
            return false;
        }
        Tree t = this.children.get(0);
        return !(t.children == null || t.children.size() != 3 || !t.children.get(1).node.equals("*"));
    }

    private boolean isPredicateAxiom20() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("=")) {
            return false;
        }
        Tree A = this.children.get(0);
        Tree B = this.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("*")) {
            return false;
        }
        Tree A1 = A.children.get(2);
        A = A.children.get(0);
        if (A1.children != null || A.children != null && !(A1.getPredicateExpression("") + "%").contains(" \'%")) {
            return false;
        }
        if (B.children == null || B.children.size() != 3 || !B.children.get(1).node.equals("+")) {
            return false;
        }
        Tree B1 = B.children.get(2);
        B = B.children.get(0);
        if (B1.children != null) {
            return false;
        }
        String a2 = B1.getPredicateExpression("");
        if (B.children == null || B.children.size() != 3 || !B.children.get(1).node.equals("*")) {
            return false;
        }
        String a = A.getPredicateExpression("");
        String b = A1.getPredicateExpression("");
        String a1 = B.children.get(0).getPredicateExpression("");
        String b1 = B.children.get(2).getPredicateExpression("") + "\'";
        return a.equals(a1) && b.equals(b1) && a1.equals(a2);
    }

    private boolean isPredicateAxiom21() {
        if (this.children == null || this.children.size() != 3 || !this.children.get(1).node.equals("->")) {
            return false;
        }
        String a = this.children.get(2).getPredicateString();
        Tree A = this.children.get(0);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("&")) {
            return false;
        }
        String zero = A.children.get(0).getPredicateString();
        A = A.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(0).node.equals("@")) {
            return false;
        }
        String var = A.children.get(1).node.replace(" ", "");
        A = A.children.get(2);
        if (A.children == null || A.children.size() != 3 || !A.children.get(1).node.equals("->")) {
            return false;
        }

        String a1 = A.children.get(0).getPredicateString();
        String a2 = A.children.get(2).getPredicateString();
        return !(!a1.replace(var, "(" + var + "\')").equals(a2) && !a1.equals(a)) && a.replace(var, "0").equals(zero);
    }

    private String refresh(Set<String> notFree) {
        if (this.children == null) {
            return this.node.replace(" ", "");
        }
        String s = "(";
        for (int i = 0; i < this.children.size(); i++) {
            if (this.children.get(i).node.equals("?") || this.children.get(i).node.equals("@")) {
                notFree.add(this.children.get(i + 1).node.replace(" ", ""));
            }
            if (this.children.get(i).node.charAt(0) <= 'z' && this.children.get(i).node.charAt(0) >= 'a'
                    && !notFree.contains(this.children.get(i).node.replace(" ", ""))
                    || this.children.get(i).node.equals("T")
                    || this.children.get(i).node.equals("SL")
                    || (this.children.get(i).node.equals("M") && this.children.get(i).children.size() < 3)
                    || this.children.get(i).node.contains("0")) {
                if (this.children.get(i).node.equals("T")) {
                    for (String string : notFree) {
                        if (this.children.get(i).getPredicateExpression("").contains(string + " ")) {
                            Set<String> set = new HashSet<>(notFree);
                            s += this.children.get(i).refresh(set);
                            break;
                        }
                    }
                    s += "^";
                } else {
                    s += "^";
                }
            } else {
                if (this.children.get(i).node.equals("M")) {
                    Set<String> set = new HashSet<>(notFree);
                    String t = this.children.get(i).refresh(set);
                    if (!t.matches("(.*)\\w(.*)")) {
                        s += "^";
                    } else {
                        s += t;
                    }
                } else {
                    Set<String> set = new HashSet<>(notFree);
                    s += this.children.get(i).refresh(set);
                }
            }
        }
        return s + ")";
    }

    Map<String, Boolean> getFree(Map<String, Boolean> old) {
        if (this.children == null) {
            if (!old.containsKey(this.node.replace(" ", ""))) {
                if (this.node.charAt(0) <= 'z' && this.node.charAt(0) >= 'a') {
                    old.put(this.node.replace(" ", ""), true);
                }
            }
            return old;
        }
        if ((this.children.get(0).node.equals("@") || this.children.get(0).node.equals("?"))) {
            if (!old.containsKey(this.children.get(1).node.replace(" ", "")) || !old.get(this.children.get(1).node.replace(" ", ""))) {
                old.put(this.children.get(1).node.replace(" ", ""), false);
                old = this.children.get(2).getFree(old);
                return old;
            }
        } else {
            Map<String, Boolean> s = new HashMap<>();
            for (Tree i : this.children) {
                Map<String, Boolean> newm = new HashMap<>(old);
                Map<String, Boolean> map = i.getFree(newm);
                if (map.size() > 0) {
                    for (String str : map.keySet()) {
                        if (!s.containsKey(str) || !s.get(str)) {
                            s.put(str, map.get(str));
                        }
                    }
                }
            }
            if (old.size() > 0) {
                for (String str : old.keySet()) {
                    if (!s.containsKey(str) || !s.get(str)) {
                        s.put(str, old.get(str));
                    }
                }
            }
            return s;
        }
        return old;
    }
}