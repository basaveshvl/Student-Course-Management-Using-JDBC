Java:


#######################################################################################################


import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SCRS {

    static Scanner sc;
    static Connection c;
    static PreparedStatement ps;

    SCRS() throws SQLException, ClassNotFoundException {

        sc = new Scanner(System.in);
        Class.forName("com.mysql.jdbc.Driver");
        c = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentCourse", "root", "root");

    }

    public int PrintInitial() {
        sc = new Scanner(System.in);

        System.out.println("\n\nChoose one of the below options");
        System.out.println("1. Register Student ");
        System.out.println("2. Login as Student");
        System.out.println("3. Login as Admin");
        System.out.println("4. Exit\n");

        int so = sc.nextInt();
        return so;
    }

    private void loginAsAdmin() throws SQLException {

        System.out.println("\n###########################################");
        System.out.println("Admin Login");

        System.out.println("Enter Username:");
        String uname = sc.next();
        System.out.println("Enter Password:");
        String pass = sc.next();

        String query = "Select a_pass,a_name from Admins where a_username = ?";

        ps = c.prepareStatement(query);
        ps.setString(1, uname);

        ResultSet rs = ps.executeQuery();
        rs.next();
        System.out.println(rs.getString(1));
        if (pass.equals(rs.getString(1))) {

            System.out.println("\nWelcome " + rs.getString(2));
            adminLogin();

        } else
            System.out.println("Invalid Details");

    }

    private void adminLogin() throws SQLException {
        loop: while (true) {

            System.out.println("\nSelect an option");
            System.out.println("1. View Courses");
            System.out.println("2. Create a Course");
            System.out.println("3. Delete a Course");
            System.out.println("4. Add a new Student");
            System.out.println("5. Delete a Student");
            System.out.println("6. Logout\n");

            int option = sc.nextInt();
            switch (option) {
                case 1:
                    viewCourses();
                    break;

                case 2:
                    adminCreateCourse();
                    break;

                case 3:
                    adminDeleteCourse();
                    break;

                case 4:
                    registerStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 6:
                    break loop;

                default:
                    System.out.println("Invalid option");
                    break;
            }

        }

    }

    private void deleteStudent() throws SQLException {
        System.out.println("\n#########################################");
        System.out.println("Deleting a Student");

        System.out.println("Enter the USN of the Student to delete");
        String usn = sc.next();

        String query = "Delete from Student where usn = ?";

        ps = c.prepareStatement(query);
        ps.setString(1, usn);

        int res = ps.executeUpdate();
        if (res == 1)
            System.out.println("Student successfully deleted");
        else
            System.out.println("Error while deleting Student");

    }

    private void adminDeleteCourse() throws SQLException {

        System.out.println("\nDeleting a Course");

        System.out.println("\nEnter Course ID to delete the course");
        int cid = sc.nextInt();

        ps = c.prepareStatement("Delete from Course where c_id=?");
        ps.setInt(1, cid);

        int res = ps.executeUpdate();
        if (res == 1)
            System.out.println("Course Deleted Successfully");
        else
            System.out.println("Error");

    }

    private void adminCreateCourse() throws SQLException {

        System.out.println("\nAdding course");

        System.out.println("Enter Course ID");
        String cid = sc.next();
        System.out.println("Enter Course Name");
        String cname = sc.next();
        System.out.println("Enter Course Price");
        int cprice = sc.nextInt();
        System.out.println("Enter Course Faculty");
        String cfac = sc.next();

        String query = "Insert into Course values(?,?,?,?)";

        ps = c.prepareStatement(query);
        ps.setString(1, cid);
        ps.setString(2, cname);
        ps.setInt(3, cprice);
        ps.setString(4, cfac);

        int res = ps.executeUpdate();
        if (res == 1)
            System.out.println("Course added to the List");
        else
            System.out.println("Error");

    }

    private void viewCourses() throws SQLException {

        System.out.println("\n\n Courses available:\n");

        ps = c.prepareStatement("Select * from Course");
        ResultSet rs = ps.executeQuery();

        System.out.println("ID \t Name \t Price \t Faculty");

        while (rs.next())
            System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getInt(3) + "\t" + rs.getString(4));

    }

    private void studentLogin(String uname) throws SQLException {

        loop: while (true) {
            System.out.println("\nEnter an option");

            System.out.println("\n1. View Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. View Registered Courses");
            System.out.println("4. Delete a Course");
            System.out.println("5. Logout");

            int option = sc.nextInt();
            switch (option) {
                case 1:
                    viewCourses();
                    break;

                case 2:
                    registerCourse(uname);
                    break;

                case 3:
                    viewRegisteredCourses(uname);
                    break;

                case 4:
                    deleteRegistedCourse(uname);
                    break;

                case 5:
                    break loop;

                default:
                    System.out.println("Invalid option");
                    break;
            }
        }

    }

    private void deleteRegistedCourse(String uname) throws SQLException, NullPointerException {

        System.out.println("\nRegisted Courses Are:");

        ps = c.prepareStatement("Select cour1,cour2,cour3 from Student where username = ? ");
        ps.setString(1, uname);

        ResultSet rs = ps.executeQuery();
        rs.next();

        System.out.println("1. " + rs.getString(1));
        System.out.println("2. " + rs.getString(2));
        System.out.println("3. " + rs.getString(3));
        System.out.println("Enter your choice");

        int option = sc.nextInt();

        if (option == 1)
            ps = c.prepareStatement("Update Student set cour1 = 'EMPTY'");
        else if (option == 2)
            ps = c.prepareStatement("Update Student set cour2 = 'EMPTY'");
        else if (option == 3)
            ps = c.prepareStatement("Update Student set cour3 = 'EMPTY'");
        else {
            System.out.println("Invalid option");
            return;
        }

        int res = ps.executeUpdate();
        if (res == 1)
            System.out.println("Course Deleted Successfully");
        else
            System.out.println("Error in Deleting Course");

    }

    private void viewRegisteredCourses(String uname) throws SQLException {

        System.out.println("\nRegisted Courses Are:");

        ps = c.prepareStatement("Select cour1,cour2,cour3 from Student where username = ? ");
        ps.setString(1, uname);

        ResultSet rs = ps.executeQuery();
        rs.next();

        System.out.println("1. " + rs.getString(1));
        System.out.println("2. " + rs.getString(2));
        System.out.println("3. " + rs.getString(3));

    }

    private void registerCourse(String uname) throws SQLException {

        ps = c.prepareStatement("Select cour1,cour2,cour3 from Student where username = ? ");
        ps.setString(1, uname);

        ResultSet rs = ps.executeQuery();
        rs.next();
        String s = "EMPTY";
        if ((s.equals(rs.getString(1))) || (s.equals(rs.getString(2)) || (s.equals(rs.getString(3))))) 
        {

            viewCourses();

            System.out.println("Enter Course ID for which you want to register");
            String cid = sc.next();

            ps = c.prepareStatement("Select c_price from Course where c_id = ?");
            ps.setString(1, cid);

            ResultSet rs2 = ps.executeQuery();

            if (rs2.next()) {
                int res=0;
                if (s.equals(rs.getString(1))) {
                    ps = c.prepareStatement("Update Student set cour1 = ? where username = ?");
                    ps.setString(1, cid);
                    ps.setString(2, uname);
                    res = ps.executeUpdate();
                } else if (s.equals(rs.getString(2))) {
                    ps = c.prepareStatement("Update Student set cour2 = ? where username = ?");
                    ps.setString(1, cid);
                    ps.setString(2, uname);
                    res = ps.executeUpdate();
                } else if (s.equals(rs.getString(3))) {
                    ps = c.prepareStatement("Update Student set cour3 = ? where username = ?");
                    ps.setString(1, cid);
                    ps.setString(2, uname);
                    res = ps.executeUpdate();
                }

                if (res == 1) {

                    System.out.println("You have registered for course");
                    System.out.println("You have to pay Rs." + rs2.getString(1) + " to be eligible for certification.");

                }
                else
                    System.out.println("Error in registering for course");
            }

        } else {
            System.out.println("You have already registered for maximum number of courses");
        }

    }

    private void loginAsStudent() throws SQLException {

        System.out.println("\n###########################################");
        System.out.println("Student Login");

        System.out.println("Enter Username:");
        String uname = sc.next();
        System.out.println("Enter Password:");
        String pass = sc.next();

        String query = "Select pass,Sname from Student where username = ?";

        ps = c.prepareStatement(query);
        ps.setString(1, uname);

        ResultSet rs = ps.executeQuery();
        rs.next();
        System.out.println(rs.getString(1));
        if (pass.equals(rs.getString(1))) {

            System.out.println("\nWelcome " + rs.getString(2));
            studentLogin(uname);

        } else
            System.out.println("Invalid Details");

    }

    private static void registerStudent() throws SQLException {

        System.out.println("\n###########################################");
        System.out.println("  REGISTERING  A  NEW  STUDENT  ");

        System.out.println("\nEnter USN of the Student:");
        String usn = sc.next();
        System.out.println("\nEnter name of the Student");
        String name = sc.next();
        System.out.println("\nEnter E-Mail ID of Student");
        String email = sc.next();
        System.out.println("\nEnter a Username for Student");
        String uname = sc.next();
        System.out.println("\nEnter a Password for Student");
        String pword = sc.next();

        String query = "INSERT INTO STUDENT VALUES(?,?,?,?,?,'EMPTY','EMPTY','EMPTY')";
        ps = c.prepareStatement(query);

        ps.setString(1, usn);
        ps.setString(2, name);
        ps.setString(3, email);
        ps.setString(4, uname);
        ps.setString(5, pword);

        int res = ps.executeUpdate();

        if (res == 1)
            System.out.println(" Student " + name + " registered successfully ");
        else
            System.out.println("Error while registering Student");

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        SCRS scrs = new SCRS();

        System.out.println("######################################################");
        System.out.println("  WELCOME  TO  STUDENT  COURSE  REGISTRATION  SYSTEM  ");

        while (true) {

            int option = scrs.PrintInitial();

            switch (option) {
                case 1:
                    registerStudent();
                    break;

                case 2:
                    scrs.loginAsStudent();
                    break;

                case 3:
                    scrs.loginAsAdmin();
                    break;

                case 4:
                    System.exit(0);
                    break;

                default:
                    System.out.println("Please select correct option");

            }

        }

    }

}

