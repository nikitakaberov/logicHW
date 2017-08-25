import java.io.*;
import java.text.ParseException;
import java.util.*;

class HW4 {
    void run(String[] args) throws ParseException {
        if (args.length != 2) {
            System.out.println("Wrong arguments");
            return;
        }
        PredicateParser parser = new PredicateParser();
        try (Scanner scanner = new Scanner(new File(args[0]));
             final Writer writer = new OutputStreamWriter(new FileOutputStream(args[1]))) {
            String str = scanner.nextLine();
            String[] arg = str.split("\\|-");
            String tmp = "";
            int balance = 0;
            for (int i = 0; i < arg[0].length(); i++) {
                if (arg[0].charAt(i) == '(') {
                    balance++;
                }
                if (arg[0].charAt(i) == ')') {
                    balance--;
                }
                if (balance == 0 && arg[0].charAt(i) == ',') {
                    tmp += "%";
                } else {
                    tmp += arg[0].charAt(i);
                }
            }
            arg[0] = tmp;
            String[] ar = arg[0].replace(" ", "").split("\\%");
            String[] assumptions = new String[ar.length];
            Tree A = null;
            String assumption = "";
            if (arg.length == 2 && !str.substring(0, 2).equals("|-")) {
                for (int i = 0; i < ar.length - 1; i++) {
                    assumptions[i] = parser.parse(ar[i]).getPredicateExpression("");
                }
                assumptions[ar.length - 1] = parser.parse(ar[ar.length - 1]).getPredicateExpression("");
                A = parser.parse(ar[ar.length - 1]);
                assumption = "(" + ar[ar.length - 1] + ")";
            }
            Map<String, Boolean> free = null;
            if (A != null) {
                free = A.getFree(new HashMap<String, Boolean>());
            }
            int number = 1;
            Vector<String> mp = new Vector<>();
            Vector<Tree> mp1 = new Vector<>();
            Vector<Tree> mp2 = new Vector<>();
            Vector<String> lines = new Vector<>();
            Vector<Integer> check = new Vector<>();
            while (scanner.hasNextLine()) {
                boolean flag = false;
                String st = scanner.nextLine();
                lines.add(st);
                number++;
                Tree t = parser.parse(st);
                String temp = t.getPredicateExpression("");
                mp.add(temp);
                if (t.children != null && t.children.size() == 3 && t.children.get(1).node.equals("->")) {
                    mp1.add(t.children.get(0));
                    mp2.add(t.children.get(2));
                } else {
                    mp1.add(null);
                    mp2.add(null);
                }
                if (arg.length == 2 && !str.substring(0, 2).equals("|-")) {
                    for (int i = 0; i < assumptions.length; i++) {
                        if (assumptions[i].equals(temp)) {
                            if (i == assumptions.length - 1) {
                                check.add(1);
                            } else {
                                check.add(0);
                            }
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) {
                    for (int i = 1; i < 22; i++) {
                        if (t.isPredicateAxiom(i)) {
                            check.add(2);
                            flag = true;
                            break;
                        }
                    }
                }
                String modp1 = "";
                String modp2 = "";
                if (t.children != null && t.children.size() == 3 && t.children.get(1).node.equals("->")) {
                    if (t.children.get(2).children != null && t.children.get(2).children.get(0).node.equals("@")) {
                        modp1 = t.children.get(2).children.get(1).node.replace(" ", "");
                    }
                    if (t.children.get(0).children != null && t.children.get(0).children.get(0).node.equals("?")) {
                        modp2 = t.children.get(0).children.get(1).node.replace(" ", "");
                    }
                }
                for (int i = number - 3; i >= 0 && !flag; i--) {
                    if (mp1.get(i) != null && !modp1.equals("")
                            && (free == null || !free.containsKey(modp1) || !free.get(modp1))
                            && (mp1.get(i).getFree(new HashMap<String, Boolean>()).keySet().contains(modp1) && !mp1.get(i).getFree(new HashMap<String, Boolean>()).get(modp1)
                            || !mp1.get(i).getPredicateExpression("").contains(modp1 + " "))) {
                        if (mp1.get(i).getPredicateExpression("").equals(t.children.get(0).getPredicateExpression("")) &&
                                t.children.get(2).getPredicateExpression("").equals("@" + modp1 + " " + mp2.get(i).getPredicateExpression(""))) {
                            flag = true;
                            check.add(3);
                            break;
                        }
                    }
                    if (mp2.get(i) != null && !modp2.equals("")
                            && (free == null || !free.containsKey(modp2) || !free.get(modp2))
                            && (mp2.get(i).getFree(new HashMap<String, Boolean>()).keySet().contains(modp2) && !mp2.get(i).getFree(new HashMap<String, Boolean>()).get(modp2)
                            || !mp2.get(i).getPredicateExpression("").contains(modp2 + " "))) {
                        if (t.children.get(0).getPredicateExpression("").equals("?" + modp2 + " " + mp1.get(i).getPredicateExpression("")) &&
                                mp2.get(i).getPredicateExpression("").equals(t.children.get(2).getPredicateExpression(""))) {
                            flag = true;
                            check.add(4);
                            break;
                        }
                    }

                    for (int j = number - 3; j >= 0; j--) {
                        if (i != j) {
                            if (mp.get(i).length() + temp.length() + 2 == mp.get(j).length()) {
                                String s = "->" + mp.get(i) + temp;
                                if (s.equals(mp.get(j))) {
                                    flag = true;
                                    check.add(-(i + 1));
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!flag) {
                    writer.write("Вывод некорректен начиная с формулы номер " + (number - 1));
                    System.out.println("Something wrong");
                    return;
                }
            }
            if (arg.length == 2 && !str.substring(0, 2).equals("|-")) {
                String first = "";
                for (int i = 0; i < ar.length - 1; i++) {
                    if (i != ar.length - 2) {
                        first += (ar[i] + ",");
                    } else {
                        first += ar[i];
                    }
                }
                writer.write((first + "|-(" + ar[ar.length - 1] + ")->(" + arg[1] + ")" + System.getProperty("line.separator")).replace(" ", ""));
            }
            if (A == null) {
                writer.write(str + System.getProperty("line.separator"));
                for (String line : lines) {
                    writer.write(line + System.getProperty("line.separator"));
                }
            } else {
                for (int i = 0; i < lines.size(); i++) {
                    switch (check.get(i)) {
                        case 0:
                            writer.write(lines.get(i) + System.getProperty("line.separator"));
                            writer.write("(" + lines.get(i) + ")" + "->(" + assumption + "->(" + lines.get(i) + "))" + System.getProperty("line.separator"));
                            break;
                        case 1:
                            String string1 = "(" + assumption + "->" + assumption + "->" + assumption + ")";
                            String string2 = "(" + assumption + "->((" + assumption + "->" + assumption + ")->" + assumption + "))->(" + assumption + "->" + assumption + ")";
                            String string3 = "(" + assumption + "->((" + assumption + "->" + assumption + ")->" + assumption + "))";
                            writer.write(string1 + System.getProperty("line.separator"));
                            writer.write(string1 + "->" + string2 + System.getProperty("line.separator"));
                            writer.write(string2 + System.getProperty("line.separator"));
                            writer.write(string3 + System.getProperty("line.separator"));
                            break;
                        case 2:
                            writer.write(lines.get(i) + System.getProperty("line.separator"));
                            writer.write("(" + lines.get(i) + ")->(" + assumption + "->(" + lines.get(i) + "))" + System.getProperty("line.separator"));
                            break;
                        case 3:
                            Tree g = parser.parse(lines.get(i));
                            String B = g.children.get(0).getPredicateString();
                            String C = g.children.get(2).children.get(2).getPredicateString();
                            String val = g.children.get(2).children.get(1).node.replace(" ", "");
                            for (String exists : PredicateProofs.any_rule) {
                                writer.write(exists.replace("@", "@" + val).replace("#", assumption).replace("^", B).replace("%", C) + System.getProperty("line.separator"));
                            }
                            break;
                        case 4:
                            Tree g1 = parser.parse(lines.get(i));
                            String B1 = g1.children.get(0).children.get(2).getPredicateString();
                            String val1 = g1.children.get(0).children.get(1).node.replace(" ", "");
                            String C1 = g1.children.get(2).getPredicateString();
                            for (String exists : PredicateProofs.exists_rule) {
                                writer.write(exists.replace("?", "?" + val1).replace("#", assumption).replace("^", B1).replace("%", C1) + System.getProperty("line.separator"));
                            }
                            break;
                        default:
                            String string11 = "(" + assumption + "->(" + lines.get(-check.get(i) - 1) + "))->((" + assumption + "->((" + lines.get(-check.get(i) - 1) + ")->(" +
                                    lines.get(i) + ")))->(" + assumption + "->(" + lines.get(i) + ")))";
                            String string22 = "((" + assumption + "->((" + lines.get(-check.get(i) - 1) + ")->(" +
                                    lines.get(i) + ")))->(" + assumption + "->(" + lines.get(i) + ")))";
                            writer.write(string11 + System.getProperty("line.separator"));
                            writer.write(string22 + System.getProperty("line.separator"));
                    }
                    writer.write(assumption + "->(" + lines.get(i) + ")" + System.getProperty("line.separator"));
                }
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
