
import java.sql.*;
import java.util.Scanner;

public class StudentMarkSheetSQL {

    // Database credentials
    static final String URL = "jdbc:mysql://localhost:3306/testdb?useSSL=false&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "Akhilasj2629";

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // Try-with-resources for Connection ensures it will be closed automatically
        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Connected Successfully to the database!");

            // Create 'students' table if it does not exist
            createTableIfNotExists(con);

            // Menu loop
            while (true) {
                showMenu();
                String choice = sc.nextLine().trim();
                switch (choice) {
                    case "1":
                        addStudents(con);
                        break;
                    case "2":
                        viewAllStudents(con);
                        break;
                    case "3":
                        System.out.println("Exiting program...");
                        return;
                    default:
                        System.out.println("Invalid choice. Try again.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found.");
        }
    }

    // Method to create table if it doesn't exist
    static void createTableIfNotExists(Connection con) throws SQLException {
        String createTable = "CREATE TABLE IF NOT EXISTS students ("
                + "id INT AUTO_INCREMENT PRIMARY KEY, "
                + "name VARCHAR(50) UNIQUE, "
                + "m1 INT, m2 INT, m3 INT, "
                + "total INT, avg DOUBLE)";
        try (Statement stmt = con.createStatement()) {
            stmt.executeUpdate(createTable);
        }
    }

    // Display menu
    static void showMenu() {
        System.out.println("\n--- MENU ---");
        System.out.println("1. Add student data");
        System.out.println("2. Display all students in table format");
        System.out.println("3. Exit");
        System.out.print("Enter choice: ");
    }

    // Add student data
    static void addStudents(Connection con) {
        int n;
        while (true) {
            try {
                System.out.print("Enter number of students to add: ");
                n = Integer.parseInt(sc.nextLine().trim());
                if (n <= 0) throw new Exception("Number must be greater than 0");
                break;
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        String insertSQL = "INSERT INTO students(name, m1, m2, m3, total, avg) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(insertSQL)) {

            for (int i = 0; i < n; i++) {
                System.out.println("\nStudent " + (i + 1));

                // Name input
                String name;
                while (true) {
                    System.out.print("Enter student name: ");
                    name = sc.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Error: Name cannot be empty.");
                    } else if (!name.matches("[a-zA-Z ]+")) {
                        System.out.println("Error: Name must contain only letters and spaces.");
                    } else {
                        break;
                    }
                }

                // Marks input
                int m1 = getMark("Subject 1");
                int m2 = getMark("Subject 2");
                int m3 = getMark("Subject 3");

                int total = m1 + m2 + m3;
                double avg = Math.round((total / 3.0) * 100) / 100.0; // Rounded to 2 decimals

                // Set parameters and execute
                ps.setString(1, name);
                ps.setInt(2, m1);
                ps.setInt(3, m2);
                ps.setInt(4, m3);
                ps.setInt(5, total);
                ps.setDouble(6, avg);

                try {
                    ps.executeUpdate();
                    System.out.println("Student added successfully!");
                } catch (SQLIntegrityConstraintViolationException e) {
                    System.out.println("Warning: Student with this name already exists, skipping.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    // Get validated mark input
    static int getMark(String subject) {
        int mark = -1;
        while (mark < 0) {
            try {
                System.out.print("Enter " + subject + " mark (0-100): ");
                mark = Integer.parseInt(sc.nextLine().trim());
                if (mark < 0 || mark > 100) {
                    System.out.println("Error: Mark must be between 0 and 100.");
                    mark = -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid integer.");
            }
        }
        return mark;
    }

    // Display all students in table format
    static void viewAllStudents(Connection con) {
        String selectSQL = "SELECT * FROM students ORDER BY id";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {

            System.out.printf("%-5s %-15s %-7s %-7s %-7s %-7s %-7s%n", "ID", "NAME", "M1", "M2", "M3", "TOTAL", "AVG");
            System.out.println("------------------------------------------------------");

            boolean hasRecords = false;
            while (rs.next()) {
                hasRecords = true;
                System.out.printf(
                        "%-5d %-15s %-7d %-7d %-7d %-7d %-7.2f%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("m1"),
                        rs.getInt("m2"),
                        rs.getInt("m3"),
                        rs.getInt("total"),
                        rs.getDouble("avg")
                );
            }

            if (!hasRecords) {
                System.out.println("No student records found.");
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
}




OUTPUT


mysql> DESCRIBE students;
+-------+-------------+------+-----+---------+----------------+
| Field | Type        | Null | Key | Default | Extra          |
+-------+-------------+------+-----+---------+----------------+
| id    | int         | NO   | PRI | NULL    | auto_increment |
| name  | varchar(50) | YES  | UNI | NULL    |                |
| m1    | int         | YES  |     | NULL    |                |
| m2    | int         | YES  |     | NULL    |                |
| m3    | int         | YES  |     | NULL    |                |
| total | int         | YES  |     | NULL    |                |
| avg   | double      | YES  |     | NULL    |                |
+-------+-------------+------+-----+---------+----------------+
7 rows in set (0.00 sec)

mysql> SELECT * FROM STUDENTS;
+----+--------+------+------+------+-------+--------------------+
| id | name   | m1   | m2   | m3   | total | avg                |
+----+--------+------+------+------+-------+--------------------+
|  1 | AKHILA |   23 |   34 |   12 |    69 |                 23 |
|  2 | ALBIN  |   54 |   34 |   23 |   111 |                 37 |
|  3 | ABIN   |   34 |   56 |   67 |   157 | 52.333333333333336 |
+----+--------+------+------+------+-------+--------------------+
3 rows in set (0.00 sec)

mysql>


PS C:\Users\abinj\desktop\JAVADB>java -cp ".;mysql-connector-j-8.3.0.jar"  StudentMarkSheetSQL
Connected Successfully!

--- MENU ---
1. Add student data
2. Display all students in table format
3. Exit
Enter choice: 1
Enter number of students to add: 1

Student 1
Enter student name: AMMU
Enter Subject 1 mark: 34
Enter Subject 2 mark: 12
Enter Subject 3 mark: 65
Student added successfully!

--- MENU ---
1. Add student data
2. Display all students in table format
3. Exit
Enter choice: 2
ID    NAME            M1      M2      M3      TOTAL   AVG
------------------------------------------------------
1     AKHILA          23      34      12      69      23.00
2     ALBIN           54      34      23      111     37.00
3     ABIN            34      56      67      157     52.33
4     AMMU            34      12      65      111     37.00

--- MENU ---
1. Add student data
2. Display all students in table format
3. Exit
Enter choice: 3
Exiting program...
PS C:\Users\abinj\desktop\JAVADB>