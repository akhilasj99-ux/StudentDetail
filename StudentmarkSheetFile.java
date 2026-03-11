import java.util.*;
import java.io.*;
// helloo
// Base class
class Student {
    String name;
    int id;
    protected static int idCounter = 1000;

    void getStudentDetails(Scanner sc) {
        id = idCounter++;

        while (true) {
            try {
                System.out.print("Enter student name: ");
                name = sc.nextLine().trim();

                if (name.isEmpty()) {
                    throw new Exception("Name cannot be empty");
                }

                if (!name.matches("[a-zA-Z ]+")) {
                    throw new Exception("Name must contain only letters");
                }

                break;

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    // Initialize ID counter from file
    static void initializeIdCounterFromFile(String fileName) {
        File file = new File(fileName);

        if (!file.exists())
            return;

        String lastLine = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lastLine = line;
            }

            if (lastLine != null) {
                String[] parts = lastLine.split(",");
                int lastId = Integer.parseInt(parts[0]);
                idCounter = lastId + 1;
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Warning: Could not initialize ID counter.");
        }
    }
}

// Derived class
class MarkSheet extends Student {
    int m1, m2, m3;

    void getMarks(Scanner sc) {
        while (true) {
            try {
                System.out.print("Enter subject 1 mark: ");
                m1 = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Enter subject 2 mark: ");
                m2 = Integer.parseInt(sc.nextLine().trim());

                System.out.print("Enter subject 3 mark: ");
                m3 = Integer.parseInt(sc.nextLine().trim());

                break;

            } catch (NumberFormatException e) {
                System.out.println("Error: Marks must be integers.");
            }
        }
    }

    int getTotal() {
        return m1 + m2 + m3;
    }

    double calculateAvg() {
        return getTotal() / 3.0;
    }

    String toFileString() {
        return id + "," + name + "," + m1 + "," + m2 + "," + m3 + "," + getTotal() + "," + calculateAvg();
    }

    static MarkSheet fromFileString(String line) {
        String[] parts = line.split(",");
        MarkSheet s = new MarkSheet();

        s.id = Integer.parseInt(parts[0]);
        s.name = parts[1];
        s.m1 = Integer.parseInt(parts[2]);
        s.m2 = Integer.parseInt(parts[3]);
        s.m3 = Integer.parseInt(parts[4]);

        return s;
    }
}

// Main class
public class StudentmarkSheetFile {

    static String fileName = "students_marksheet.txt";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        Student.initializeIdCounterFromFile(fileName);

        while (true) {
            showMenu();
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1":
                    addStudents();
                    break;
                case "2":
                    viewAllStudents();
                    break;
                case "3":
                    System.out.println("Exiting program...");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void showMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add student data (Append)");
        System.out.println("2. Display stored file");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
    }

    static void addStudents() {
        int n;

        while (true) {
            try {
                System.out.print("Enter number of students to add: ");
                n = Integer.parseInt(sc.nextLine().trim());

                if (n <= 0)
                    throw new Exception("Number must be > 0");

                break;

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {

            for (int i = 0; i < n; i++) {
                System.out.println("\nStudent " + (i + 1));

                MarkSheet ms = new MarkSheet();
                ms.getStudentDetails(sc);
                ms.getMarks(sc);

                writer.write(ms.toFileString());
                writer.newLine();
            }

            System.out.println("Student data saved successfully.");

        } catch (IOException e) {
            System.out.println("Error writing to file.");
        }
    }

    static void viewAllStudents() {

        File file = new File(fileName);

        if (!file.exists()) {
            System.out.println("No records found.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;
            System.out.println("\n--- Student Records ---");

            while ((line = reader.readLine()) != null) {
                MarkSheet ms = MarkSheet.fromFileString(line);

                System.out.println("ID: " + ms.id);
                System.out.println("Name: " + ms.name);
                System.out.println("Marks: " + ms.m1 + ", " + ms.m2 + ", " + ms.m3);
                System.out.println("Total: " + ms.getTotal());
                System.out.println("Average: " + ms.calculateAvg());
                System.out.println("-----------------------");
            }

        } catch (IOException e) {
            System.out.println("Error reading file.");
        }
    }
}
