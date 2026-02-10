import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;

class Student {
    int rollNo;
    String name;
    int m1, m2, m3;

    Student(int rollNo, String name, int m1, int m2, int m3) {
        this.rollNo = rollNo;
        this.name = name;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
    }

    int total() {
        return m1 + m2 + m3;
    }

    double average() {
        return total() / 3.0;
    }

    String result() {
        if(getAverage()>=35)
           return "pass";
        else
           return "fail";

    }

    String toFile() {
        return "Roll No : " + rollNo + "\n" +
               "Name    : " + name + "\n" +
               "Marks   : " + m1 + ", " + m2 + ", " + m3 + "\n" +
               "Total   : " + total() + "\n" +
               "Average : " + average() + "\n" +
               "Result  : " + result() + "\n" +
               "--------------------------\n";
    }
}

public class StudentAppendValidation {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add student data (Append)");
            System.out.println("2. Display stored file");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");

            int choice = getValidChoice(sc);

            switch (choice) {

                case 1:
                    try (FileWriter fw = new FileWriter("students.txt", true)) {

                        int roll = getValidRollNo(sc);
                        String name = getValidName(sc);
                        int m1 = getValidMarks(sc, "Mark 1");
                        int m2 = getValidMarks(sc, "Mark 2");
                        int m3 = getValidMarks(sc, "Mark 3");

                        Student s = new Student(roll, name, m1, m2, m3);
                        fw.write(s.toFile());

                        System.out.println("Student data appended successfully!");

                    } catch (IOException e) {
                        System.out.println("File error occurred!");
                    }
                    break;

                case 2:
                    try (FileReader fr = new FileReader("students.txt")) {
                        int ch;
                        System.out.println("\n--- STUDENT RECORDS ---");
                        while ((ch = fr.read()) != -1) {
                            System.out.print((char) ch);
                        }
                    } catch (IOException e) {
                        System.out.println("No data found or file error!");
                    }
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    sc.close();
                    System.exit(0);
            }
        }
    }

    // ---------- VALIDATION METHODS ----------

    static int getValidChoice(Scanner sc) {
        while (true) {
            try {
                int c = Integer.parseInt(sc.nextLine());
                if (c >= 1 && c <= 3) return c;
                System.out.print("Enter choice (1-3): ");
            } catch (Exception e) {
                System.out.print("Invalid input. Enter choice (1-3): ");
            }
        }
    }

    static int getValidRollNo(Scanner sc) {
        System.out.print("Roll No: ");
        while (true) {
            try {
                int r = Integer.parseInt(sc.nextLine());
                if (r > 0) return r;
                System.out.print("Roll No must be positive. Enter again: ");
            } catch (Exception e) {
                System.out.print("Invalid number. Enter Roll No again: ");
            }
        }
    }

    static String getValidName(Scanner sc) {
        System.out.print("Name: ");
        while (true) {
            String s = sc.nextLine();
            if (s.matches("[a-zA-Z ]+")) return s;
            System.out.print("Invalid name. Letters only. Enter again: ");
        }
    }

    static int getValidMarks(Scanner sc, String field) {
        System.out.print(field + ": ");
        while (true) {
            try {
                int m = Integer.parseInt(sc.nextLine());
                if (m >= 0 && m <= 100) return m;
                System.out.print("Marks must be 0–100. Enter again: ");
            } catch (Exception e) {
                System.out.print("Invalid number. Enter marks again: ");
            }
        }
    }
}
