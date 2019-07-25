/**
 * Author:  Zhiwen Luo
 * Date:    11/23/2017
 * Purpose: Project Part 2
**/

import java.sql.*;

public class Part2 {

    // Connection
    static final String JDBC_URL = "jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g";

    // Database Credentials
    static final String USER = "zluo2";
    static final String PASS = "ozengu";

    public static void main(String[] args) {

        //connection setup
        Connection conn = null;

        //set the database diver
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver could not be loaded!");
        }

        // attempt to connect
        try {
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
        } catch (SQLException e) {
            System.out.println("Error: unable to connect to database!");
            System.exit(1);
        }

        System.out.println("Connection successful.");

        System.out.println("\nWorking on Part 2 problem 1 ...");
        problemOne(conn);

        System.out.println("\nWorking on Part 2 problem 2 ...");
        problemTwo(conn);

        try{
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("Error: connection already closed!");
        }

        System.out.println("\nConnection successfully closed.\n");
    }

    public static void problemOne(Connection conn) {

        try {
            //retrieves the employees who work in the Research department
            Statement qry = conn.createStatement();
            String sql = "SELECT Lname, Ssn FROM EMPLOYEE JOIN DEPARTMENT ON Dno=Dnumber " +
                  "WHERE Dname = \'Research\'";
            ResultSet rs = qry.executeQuery(sql);

            System.out.println("\nEmployees who work in the research department:\n");
            System.out.println("Lname\tSsn");
            //print the employee last name and their Ssn
            while(rs.next()) {
                System.out.println(rs.getString("Lname") + "\t" + rs.getInt("Ssn"));
            }

            // close result set and statement
            rs.close();
            qry.close();
            //conn.close();

        } catch (SQLException e) {
            System.out.println("Error: unable to complete query one!");
            System.exit(1);
        }
    }

    public static void problemTwo(Connection conn) {

        try {
            //retrieves the employees who work in departments located in 
            //Houston and on the project ‘ProductZ’.
            Statement qry = conn.createStatement();
            String sql = "SELECT Lname, Ssn, Hours " +
                  "FROM EMPLOYEE, WORKS_ON, PROJECT, DEPT_LOCATIONS " +
                  "where Ssn=Essn AND Pno=Pnumber AND Dno=Dnumber AND Dlocation=\'Houston\' AND Pname=\'ProductZ\'";
            ResultSet rs = qry.executeQuery(sql);

            System.out.println("\nEmployees who work in Houston on ProductZ:\n");
            System.out.println("Lname\tSsn\t\tHours");
            //print their last name, Ssn, and the number of hours that
            //the employee works on that project.
            while(rs.next()) {
                System.out.println(rs.getString("Lname") + "\t" + rs.getInt("Ssn") + "\t" + rs.getInt("Hours"));
            } 

            // close result set and statement
            rs.close();
            qry.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Error: unable to complete query two!");
            System.exit(1);
        }
    }
}
