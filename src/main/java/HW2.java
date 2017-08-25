import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Vector;

class HW2 {
    void run(String[] args) throws ParseException {
        if (args.length != 2) {
            System.out.println("Wrong arguments");
            return;
        }
        Parser parser = new Parser();
        try (Scanner scanner = new Scanner(new File(args[0]));
             final Writer writer = new OutputStreamWriter(new FileOutputStream(args[1]))) {
            String str = scanner.nextLine();
            String[] arg = str.split("\\|-");
            String[] ar = arg[0].split(",");
            String[] assumptions = new String[ar.length];
            if (arg.length == 2) {
                for (int i = 0; i < ar.length; i++) {
                    assumptions[i] = parser.parse(ar[i]).getExpression("");
                }
            }
            for (int i = 0; i < ar.length - 2; i++) {
                writer.write(ar[i] + ",");
            }
            String A = "(" + ar[ar.length - 1] + ")";
            if (ar.length > 1) {
                writer.write(ar[ar.length - 2] + "|-" + A + "->(" + arg[1] + ")");
            } else {
                writer.write(A + "->(" + arg[1] + ")");
            }
            writer.write(System.getProperty("line.separator"));
            int number = 1;
            Vector<String> mp = new Vector<>();
            Vector<String> mp1 = new Vector<>();
            while (scanner.hasNextLine()) {
                number++;
                boolean flag = false;
                String st = scanner.nextLine();
                String stf = A + "->(" + st + ")";
                Tree t = parser.parse(st);
                String temp = t.getExpression("");
                mp.add(temp);
                mp1.add(st);
                if (arg.length == 2) {
                    for (int i = 0; i < assumptions.length - 1; i++) {
                        if (assumptions[i].equals(temp)) {
                            writer.write(st + System.getProperty("line.separator"));
                            writer.write("(" + st + ")" + "->(" + A + "->(" + st + "))" + System.getProperty("line.separator"));
                            flag = true;
                            break;
                        }
                    }
                    if (assumptions[assumptions.length - 1].equals(temp)) {
                        String string1 = "(" + A + "->" + A + "->" + A + ")";
                        String string2 = "(" + A + "->((" + A + "->" + A + ")->" + A + "))->(" + A + "->" + A + ")";
                        String string3 = "(" + A + "->((" + A + "->" + A + ")->" + A + "))";
                        writer.write(string1 + System.getProperty("line.separator"));
                        writer.write(string1 + "->" + string2 + System.getProperty("line.separator"));
                        writer.write(string2 + System.getProperty("line.separator"));
                        writer.write(string3 + System.getProperty("line.separator"));
                        flag = true;
                    }
                }
                if (!flag) {
                    for (int i = 1; i < 11; i++) {
                        if (t.isAxiom(i)) {
                            writer.write(st + System.getProperty("line.separator"));
                            writer.write("(" + st + ")->(" + A + "->(" + st + "))" + System.getProperty("line.separator"));
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
                                    writer.write(string1 + System.getProperty("line.separator"));
                                    writer.write(string2 + System.getProperty("line.separator"));
                                    break;
                                }
                            }
                        }
                    }
                }
                writer.write(stf);
                writer.write(System.getProperty("line.separator"));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
