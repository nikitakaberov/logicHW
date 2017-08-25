import java.io.*;
import java.util.Scanner;

class HW5 {
    private String[] begin = {"0=0->0=0->0=0",
            "a=b->a=c->b=c",
            "(a=b->a=c->b=c)->(0=0->0=0->0=0)->(a=b->a=c->b=c)",
            "(0=0->0=0->0=0)->(a=b->a=c->b=c)",
            "(0=0->0=0->0=0)->@c(a=b->a=c->b=c)",
            "(0=0->0=0->0=0)->@b@c(a=b->a=c->b=c)",
            "(0=0->0=0->0=0)->@a@b@c(a=b->a=c->b=c)",
            "@c(a=b->a=c->b=c)",
            "@c(a=b->a=c->b=c)->(a=b->a=a->b=a)",
            "a=b->a=a->b=a",
            "@a@b@c(a=b->a=c->b=c)",
            "@a@b@c(a=b->a=c->b=c)->@b@c(a+0=b->a+0=c->b=c)",
            "(0=0->0=0->0=0)->@a@b@c(a=b->a=c->b=c)",
            "@b@c(a+0=b->a+0=c->b=c)->@c(a+0=a->a+0=c->a=c)",
            "@b@c(a+0=b->a+0=c->b=c)",
            "@c(a+0=a->a+0=c->a=c)",
            "@c(a+0=a->a+0=c->a=c)->(a+0=a->a+0=a->a=a)",
            "a+0=a->a+0=a->a=a",
            "a+0=a",
            "a+0=a->a=a",
            "a=a",
            "a=a->a=b->a=a",
            "a=b->a=a",
            "(a=b->a=a)->(a=b->a=a->b=a)->(a=b->b=a)",
            "(a=b->a=a->b=a)->(a=b->b=a)",
            "a=b->b=a",
            "(a=b->b=a)->(0=0->0=0->0=0)->(a=b->b=a)",
            "(0=0->0=0->0=0)->(a=b->b=a)",
            "(0=0->0=0->0=0)->@b(a=b->b=a)",
            "(0=0->0=0->0=0)->@a@b(a=b->b=a)",
            "@a@b(a=b->b=a)",
            "@a@b(a=b->b=a)->@b(x=b->b=x)",
            "@b(x=b->b=x)",
            "@b(x=b->b=x)->(x=y->y=x)",
            "x=y->y=x",
            "(x=y->y=x)->(0=0->0=0->0=0)->(x=y->y=x)",
            "(0=0->0=0->0=0)->(x=y->y=x)",
            "(0=0->0=0->0=0)->@y(x=y->y=x)",
            "(0=0->0=0->0=0)->@x@y(x=y->y=x)",
            "@x@y(x=y->y=x)"};

    private String[] middle = {"a+b'=(a+b)'",
            "a+b'=(a+b)'->(A->B->A)->a+b'=(a+b)'",
            "(A->B->A)->a+b'=(a+b)'",
            "(A->B->A)->@b(a+b'=(a+b)')",
            "A->B->A",
            "@b(a+b'=(a+b)')",
            "@b(a+b'=(a+b)')->(a+o'=(a+o)')",
            "a+o'=(a+o)'",
            "@x@y(x=y->y=x)->@y((a+o')=y->y=(a+o'))",
            "@y((a+o')=y->y=(a+o'))",
            "@y((a+o')=y->y=(a+o'))->(a+o')=(a+o)'->(a+o)'=(a+o')",
            "(a+o')=(a+o)'->(a+o)'=(a+o')",
            "(a+o)'=(a+o')",
            "a=b->a=c->b=c",
            "(a=b->a=c->b=c)->(A->B->A)->(a=b->a=c->b=c)",
            "(A->B->A)->(a=b->a=c->b=c)",
            "(A->B->A)->@c(a=b->a=c->b=c)",
            "(A->B->A)->@b@c(a=b->a=c->b=c)",
            "(A->B->A)->@a@b@c(a=b->a=c->b=c)",
            "(A->B->A)",
            "@a@b@c(a=b->a=c->b=c)",
            "@a@b@c(a=b->a=c->b=c)->@b@c((a+o)'=b->(a+o)'=c->b=c)",
            "@b@c((a+o)'=b->(a+o)'=c->b=c)",
            "@b@c((a+o)'=b->(a+o)'=c->b=c)->@c((a+o)'=(a+o')->(a+o)'=c->(a+o')=c)",
            "@c((a+o)'=(a+o')->(a+o)'=c->(a+o')=c)",
            "@c((a+o)'=(a+o')->(a+o)'=c->(a+o')=c)->((a+o)'=(a+o')->(a+o)'=d'->(a+o')=d')",
            "((a+o)'=(a+o')->(a+o)'=d'->(a+o')=d')",
            "(a+o)'=d'->(a+o')=d'",
            "a+o=d",
            "a=b->a'=b'",
            "(a=b->a'=b')->(A->B->A)->(a=b->a'=b')",
            "(A->B->A)->(a=b->a'=b')",
            "(A->B->A)->@b(a=b->a'=b')",
            "(A->B->A)->@a@b(a=b->a'=b')",
            "@a@b((a=b)->(a'=b'))",
            "@a@b((a=b)->(a'=b'))->@b((a+o=b)->(a+o)'=b')",
            "@b((a+o=b)->(a+o)'=b')",
            "@b((a+o=b)->(a+o)'=b')->((a+o=d)->(a+o)'=d')",
            "(a+o=d)->(a+o)'=d'",
            "(a+o)'=d'",
            "a+o'=d'"};

    private String[] end = {"a+O=X",
            "(a+O=X)->(A->B->A)->(a+O=X)",
            "(A->B->A)->(a+O=X)",
            "(A->B->A)->@a(a+O=X)",
            "(A->B->A)",
            "@a(a+O=X)",
            "@a(a+O=X)->(Y+O=Z)",
            "Y+O=Z"};

    void run(String[] args) {
        if (args.length != 2) {
            System.out.println("Wrong arguments");
            return;
        }
        try (Scanner scanner = new Scanner(new File(args[0]));
             final Writer writer = new OutputStreamWriter(new FileOutputStream(args[1]))) {
            String[] param = scanner.nextLine().split(" ");
            int a = Integer.parseInt(param[0]);
            int b = Integer.parseInt(param[1]);
            writer.write("|-0");
            String postfixA = "0";
            String postfixAB = "0";
            for (int i = 0; i < a; i++) {
                writer.write("'");
                postfixA += "'";
            }
            writer.write("+0");
            for (int i = 0; i < b; i++) {
                writer.write("'");
            }
            writer.write("=0");
            for (int i = 0; i < a + b; i++) {
                writer.write("'");
                postfixAB += "'";
            }
            writer.write(System.getProperty("line.separator"));
            for (String i : begin) {
                writer.write(i + System.getProperty("line.separator"));
            }

            String postfix = "";
            for (int i = 0; i < b; i++) {
                for (String s : middle) {
                    writer.write(s.replace("o", "0" + postfix).replace("d", "a" + postfix) + System.getProperty("line.separator"));
                }
                postfix += "'";
            }
            for (String i : end) {
                writer.write(i.replace("X", "a" + postfix).replace("O", "0" + postfix).replace("Y", postfixA).replace("Z", postfixAB) + System.getProperty("line.separator"));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
