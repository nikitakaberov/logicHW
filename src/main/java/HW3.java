import java.io.*;
import java.text.ParseException;
import java.util.*;

class HW3 {
    private Vector<Tree> proof;
    private Parser parser = new Parser();

    void run(String[] args) throws ParseException {
        if (args.length != 2) {
            System.out.println("Wrong arguments");
            return;
        }
        proof = new Vector<>();
        Vector<Vector<Tree>> proofs = new Vector<>();
        try (Scanner scanner = new Scanner(new File(args[0]));
             final Writer writer = new OutputStreamWriter(new FileOutputStream(args[1]))) {
            String expression = scanner.nextLine();
            writer.write("|-" + expression + System.getProperty("line.separator"));
            Tree expressionTree = parser.parse(expression);
            Map<String, Boolean> assumptions = new HashMap<>();
            Set<String> variable = new HashSet<>();
            String[] vars = expression.replaceAll("->|&|\\||\\(|\\)|!", " ").split(" ");
            for (String i : vars) {
                if (i.length() > 0) {
                    variable.add(i + " ");
                    assumptions.put(i + " ", false);
                }
            }
            int collections = 1 << (variable.size());
            for (int i = 1; i <= collections; i++) {
                if (!expressionTree.getResult(assumptions)) {
                    writer.write("Высказывание ложно при ");
                    int j = 0;
                    for (String s : variable) {
                        writer.write(s.replace(" ", ""));
                        writer.write(assumptions.get(s) ? "=И" : "=Л");
                        if (j != variable.size() - 1) {
                            writer.write(", ");
                        }
                        j++;
                    }
                    return;
                }
                int j = 0;
                for (String s : variable) {
                    assumptions.put(s, (i >> j) % 2 == 1);
                    j++;
                }
            }
            collections = 1 << (variable.size());
            for (int i = 0; i < collections; i++) {
                int j = 0;
                for (String s : variable) {
                    assumptions.put(s, (i >> j) % 2 == 1);
                    j++;
                }
                createProof(expressionTree, assumptions);
                Vector<Tree> tr = new Vector<>();
                for (Tree t : proof) {
                    tr.add(t);
                }
                proofs.add(tr);
                proof.clear();
            }

            int k = -1;
            for (String s : variable) {
                k++;
                Map<String, Boolean> new_assumptions = new HashMap<>();
                Vector<Vector<Tree>> new_proofs = new Vector<>();
                for (int i = 0; i < proofs.size(); i += 2) {
                    int j = 0;
                    for (String str : variable) {
                        if (j > k) {
                            new_assumptions.put(str, (i >> (j - k)) % 2 == 1);
                        }
                        j++;
                    }
                    Vector<Tree> pr = merge(proofs.get(i), proofs.get(i + 1), s, new_assumptions);
                    new_proofs.add(pr);
                }
                proofs = new_proofs;
            }
            for (Vector<Tree> tree : proofs) {
                if (tree != null) {
                    for (Tree t : tree) {
                        writer.write(t.getString() + System.getProperty("line.separator"));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Vector<Tree> deduction(Vector<Tree> a, String A, Map<String, Boolean> assumptions1) throws ParseException {
        String[] assumptions = new String[assumptions1.size()];
        int y = 0;
        for (String s : assumptions1.keySet()) {
            if (s != null) {
                assumptions[y++] = assumptions1.get(s) ? s : ("!" + s);
            }
        }
        Vector<Tree> new_proofs = new Vector<>();
        int number = 1;
        Vector<String> mp = new Vector<>();
        Vector<String> mp1 = new Vector<>();
        for (Tree t : a) {
            number++;
            boolean flag = false;
            String st = t.getString();
            String stf = A + "->(" + st + ")";
            String temp = t.getExpression("");
            mp.add(temp);
            mp1.add(st);

            for (String assumption : assumptions) {
                if (assumption.equals(temp) && !assumption.equals(A)) {
                    new_proofs.add(parser.parse(st));
                    new_proofs.add(parser.parse("(" + st + ")" + "->(" + A + "->(" + st + "))"));
                    flag = true;
                    break;
                }
            }
            if (A.equals(temp)) {
                String string1 = "(" + A + "->" + A + "->" + A + ")";
                String string2 = "(" + A + "->((" + A + "->" + A + ")->" + A + "))->(" + A + "->" + A + ")";
                String string3 = "(" + A + "->((" + A + "->" + A + ")->" + A + "))";
                new_proofs.add(parser.parse(string1));
                new_proofs.add(parser.parse(string1 + "->" + string2));
                new_proofs.add(parser.parse(string2));
                new_proofs.add(parser.parse(string3));
                flag = true;
            }

            if (!flag) {
                for (int i = 1; i < 11; i++) {
                    if (t.isAxiom(i)) {
                        new_proofs.add(parser.parse(st));
                        new_proofs.add(parser.parse("(" + st + ")->(" + A + "->(" + st + "))"));
                        flag = true;
                        break;
                    }
                }
            }
            for (int i = number - 3; i >= 0 && !flag; i--) {
                for (int j = number - 3; j >= 0; j--) {
                    if (i != j) {
                        if (mp.get(i).length() + temp.length() + 2 == mp.get(j).length()) {
                            String s = "->" + mp.get(i) + temp;
                            if (s.equals(mp.get(j))) {
                                flag = true;
                                String string1 = "(" + A + "->(" + mp1.get(i) + "))->((" + A + "->((" + mp1.get(i) + ")->(" +
                                        st + ")))->(" + A + "->(" + st + ")))";
                                String string2 = "((" + A + "->((" + mp1.get(i) + ")->(" +
                                        st + ")))->(" + A + "->(" + st + ")))";
                                new_proofs.add(parser.parse(string1));
                                new_proofs.add(parser.parse(string2));
                                break;
                            }
                        }
                    }
                }
            }
            new_proofs.add(parser.parse(stf));
        }
        return new_proofs;
    }

    private Vector<Tree> merge(Vector<Tree> b, Vector<Tree> a, String s, Map<String, Boolean> assumptions) throws ParseException {
        String C = a.get(a.size() - 1).getString();
        a = deduction(a, s, assumptions);
        b = deduction(b, "!" + s, assumptions);
        String D = a.get(a.size() - 1).getString();
        String E = b.get(b.size() - 1).getString();
        for (Tree t : b) {
            a.add(t);
        }
        a.add(parser.parse(D + "->" + E + "->(" + s + "|!" + s + "->" + C + ")"));
        a.add(parser.parse(E + "->(" + s + "|!" + s + "->" + C + ")"));
        a.add(parser.parse("(" + s + "|!" + s + "->" + C + ")"));

        for (String str : Proofs.A_or_NotA) {
            a.add(parser.parse(str.replace("a", s)));
        }
        a.add(parser.parse(C));
        return a;
    }

    private boolean createProof(Tree expr, Map<String, Boolean> assumptions) throws ParseException {
        if (expr.node.equals("N") && expr.children.size() == 1) {
            if (assumptions.get(expr.children.get(0).node)) {
                proof.add(expr);
                return true;
            } else {
                Tree t = parser.parse("!" + expr.children.get(0).node);
                proof.add(t);
                return false;
            }
        }
        if (expr.node.equals("N") && expr.children.size() == 2) {
            boolean A = createProof(expr.children.get(1), assumptions);
            if (A) {
                for (String s : Proofs.A_to_NotA) {
                    proof.add(parser.parse(s.replace("a", expr.children.get(1).getString())));
                }
            }
            return !A;
        }
        boolean A = createProof(expr.children.get(0), assumptions);
        boolean B = createProof(expr.children.get(2), assumptions);
        if (expr.node.equals("S")) {
            if (A) {
                if (B) {
                    for (String s : Proofs.A_to_B) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                } else {
                    for (String s : Proofs.A_to_NotB) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                }
            } else {
                if (B) {
                    for (String s : Proofs.notA_to_B) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                } else {
                    for (String s : Proofs.notA_to_NotB) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                }
            }
        }
        if (expr.node.equals("D")) {
            if (A) {
                for (String s : Proofs.A_or_B) {
                    proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                }
            } else {
                if (B) {
                    for (String s : Proofs.notA_or_B) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                } else {
                    for (String s : Proofs.notA_or_NotB) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                }
            }
        }
        if (expr.node.equals("K")) {
            if (A) {
                if (B) {
                    for (String s : Proofs.A_and_B) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                } else {
                    for (String s : Proofs.A_and_NotB) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                }
            } else {
                if (B) {
                    for (String s : Proofs.notA_and_B) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                } else {
                    for (String s : Proofs.notA_and_NotB) {
                        proof.add(parser.parse(s.replace("a", expr.children.get(0).getString()).replace("b", expr.children.get(2).getString())));
                    }
                }
            }
        }
        return proof.get(proof.size() - 1).getExpression("").equals(expr.getExpression(""));
    }

}
