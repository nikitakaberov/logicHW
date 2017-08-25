import java.io.*;
import java.text.ParseException;
import java.util.Scanner;
import java.util.Vector;

class HW1 {
    void run(String[] args) throws ParseException {
        if (args.length != 2) {
            System.out.println("Wrong arguments");
            return;
        }
        Parser parser = new Parser();
        try (Scanner scanner = new Scanner(new File(args[0]));
             final Writer writer = new OutputStreamWriter(new FileOutputStream(args[1]))) {
            String str = scanner.nextLine();
            writer.write(str + System.getProperty("line.separator"));
            String[] arg = str.split("\\|-");
            String[] ar = arg[0].split(",");
            String[] assumptions = new String[ar.length];
            if (arg.length == 2 && !str.substring(0, 2).equals("|-")) {
                for (int i = 0; i < ar.length; i++) {
                    assumptions[i] = parser.parse(ar[i]).getExpression("");
                }
            }
            int number = 1;
            Vector<String> mp = new Vector<>();
            while (scanner.hasNextLine()) {
                boolean flag = false;
                String st = scanner.nextLine();
                writer.write("(" + (number++) + ") " + st + " ");
                Tree t = parser.parse(st);
                String temp = t.getExpression("");
                mp.add(temp);
                if (arg.length == 2 && !str.substring(0, 2).equals("|-")) {
                    for (int i = 0; i < assumptions.length; i++) {
                        if (assumptions[i].equals(temp)) {
                            writer.write("(Предп. " + (i + 1) + ")");
                            flag = true;
                            break;
                        }
                    }
                }
                if (!flag) {
                    for (int i = 1; i < 11; i++) {
                        if (t.isAxiom(i)) {
                            writer.write("(Сх. акс. " + i + ")");
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
                                    writer.write("(M.P. " + (i + 1) + ", " + (j + 1) + ")");
                                    break;
                                }
                            }
                        }
                    }
                }
                if (!flag) {
                    writer.write("(Не доказано)");
                }
                writer.write(System.getProperty("line.separator"));
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
