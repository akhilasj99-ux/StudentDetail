import java.util.Scanner;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
//
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
        return (average() >= 35) ? "Pass" : "Fail";
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

public class StudentMenuFile {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Add student data");
            System.out.println("2. Display stored file");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {

                case 1:
                    try {
                        FileWriter fw = new FileWriter("students.txt", true);

                        System.out.print("Roll No: ");
                        int roll = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Name: ");
                        String name = sc.nextLine();

                        System.out.print("Mark 1: ");
                        int m1 = sc.nextInt();
                        System.out.print("Mark 2: ");
                        int m2 = sc.nextInt();
                        System.out.print("Mark 3: ");
                        int m3 = sc.nextInt();

                        Student s = new Student(roll, name, m1, m2, m3);
                        fw.write(s.toFile());
                        fw.close();

                        System.out.println("Student data saved successfully!");

                    } catch (IOException e) {
                        System.out.println("File error occurred!");
                    }
                    break;

                case 2:
                    try {
                        FileReader fr = new FileReader("students.txt");
                        int ch;
                        System.out.println("\n--- STUDENT RECORDS ---");
                        while ((ch = fr.read()) != -1) {
                            System.out.print((char) ch);
                        }
                        fr.close();
                    } catch (IOException e) {
                        System.out.println("No file found or error reading file!");
                    }
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

