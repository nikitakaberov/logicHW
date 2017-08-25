import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        if (args.length != 3) {
            System.out.println("Wrong arguments");
            return;
        }
        String[] newArgs = {args[1], args[2]};
        int hw = Integer.parseInt(args[0]);
        switch (hw) {
            case 1:
                HW1 hw1 = new HW1();
                hw1.run(newArgs);
                break;
            case 2:
                HW2 hw2 = new HW2();
                hw2.run(newArgs);
                break;
            case 3:
                HW3 hw3 = new HW3();
                hw3.run(newArgs);
                break;
            case 4:
                HW4 hw4 = new HW4();
                hw4.run(newArgs);
                break;
            case 5:
                HW5 hw5 = new HW5();
                hw5.run(newArgs);
                break;
            default:
                System.out.println("Wrong HW number");
        }
    }
}
