/**
 * Author:  Zhiwen Luo
 * Date:    11/27/2017
 * Purpose: Project Part 2
**/

//ExtraCredit:
//Part1: CheckViolate
//Part2: addEmploye and addProjectHours
//Part3: DeleteEmployee
//part4: DeleteProject
//part5: updateEmployee

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class extraCredit {

    // Connection
    static final String JDBC_URL = "jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g";

    // Database Credentials
    static final String USER = "zluo2";
    static final String PASS = "ozengu";

    public static void main(String[] args) {

        //connection setup
        Connection conn = null;
        Scanner reader = new Scanner(System.in);

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
       
        
        //ExtraCredit prblem1
        System.out.println("\nChecking the buiness rule ...");
        if (CheckViolate(conn))
            System.out.println("violating the buiness rule");
        else System.out.println("No violating the buiness rule");
        
        System.out.println();
        ///////////////////////////////////////////////////////////////////////////////////////////

        
        String fname;
        String minit;
        String lname;
        String ssn;
        String bdate;
        String addr;
        String sex;
        String salary;
        String superssn;
        String dno;
        String pno;


        //ExtraCredit Problem2
        System.out.println("\nadd the employee to the database");
        System.out.println("Please input the first name");
        fname = reader.nextLine();
        System.out.println("Please input the middle name");
        minit = reader.nextLine();
        System.out.println("Please input the last name");
        lname = reader.nextLine();
        System.out.println("Please input the ssn");
        ssn = reader.nextLine();
        System.out.println("Please input the Bdate");
        bdate = reader.nextLine(); 
        System.out.println("Please input the Address");
        addr = reader.nextLine();
        System.out.println("Please input the Sex(M/F)");
        sex = reader.nextLine();
        System.out.println("Please input the Salary");
        salary = reader.nextLine();
        System.out.println("Please input the Super_ssn");
        superssn = reader.nextLine();
        System.out.println("Please input the department number");
        dno = reader.nextLine();
        
        addEmployee(conn,fname,minit,lname,ssn,bdate,addr,sex,salary,superssn,dno);
        
        System.out.println("Please input the project number");
        pno = reader.nextLine();
        System.out.println("Please input the project hours");
        String hours = reader.nextLine();
        
        addProjectHours(conn,ssn,pno,hours);
                
        System.out.println();
        ///////////////////////////////////////////////////////////////////////////////////////////

        //ExtraCredit Problem3
        System.out.println("Delete the employee from the project");
        System.out.println("Please input the ssn of employee");
        ssn = reader.nextLine();
        System.out.println("Please input the pnumber of project");
        pno = reader.nextLine();
        DeleteEmployee(conn,ssn,pno);

        System.out.println();
        ///////////////////////////////////////////////////////////////////////////////////////////

        //ExtraCredit Problem4
        System.out.println("Delete the project from the database");
        System.out.println("Please input the pnumber of project");
        pno = reader.nextLine();
        DeleteProject(conn,pno);
       
       System.out.println();
        ///////////////////////////////////////////////////////////////////////////////////////////

        //ExtraCredit Problem5
        System.out.println("\nUpdate the employee new information to the database");
        System.out.println("Please input the ssn of employee");
        ssn = reader.nextLine();
        System.out.println("Please input the Bdate");
        bdate = reader.nextLine();
        System.out.println("Please input the Address");
        addr = reader.nextLine();
        System.out.println("Please input the Sex(M/F)");
        sex = reader.nextLine();
        System.out.println("Please input the Salary");
        salary = reader.nextLine();
        System.out.println("Please input the Super_ssn");
        superssn = reader.nextLine();
        System.out.println("Please input the department number");
        dno = reader.nextLine();

        updateEmployee(conn,ssn,bdate,addr,sex,salary,superssn,dno);
          
        try{
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("Error: connection already closed!");
        }

        System.out.println("\nConnection successfully closed.\n");
    }
    
    //ExtraCredit Part1
    public static boolean CheckViolate(Connection conn) {
        
        int violateNum = 0;

        try {
            //retrieves the employees who work in the Research department
            Statement qry = conn.createStatement();
            String sql = "SELECT Ssn FROM EMPLOYEE ";
            ResultSet rs = qry.executeQuery(sql);
            ResultSet rs2 = null;

            //print the employee last name and their Ssn
            while(rs.next()) {
                String sql2 = "SELECT COUNT(*) FROM WORKS_ON " +
                                "Where Essn="+rs.getString("Ssn");
                Statement qry2 = conn.createStatement();
                rs2 = qry2.executeQuery(sql2);
                rs2.next();
                System.out.println(rs.getString("Ssn") + " The number of project: "+ rs2.getInt("COUNT(*)"));
                if (rs2.getInt("COUNT(*)") == 0 || rs2.getInt("COUNT(*)") > 2)
                    violateNum ++;
                
            }
            
            System.out.println();
         
            // close result set and statement
            rs2.close();
            rs.close();
            qry.close();
            //conn.close();

        } catch (SQLException e) {
            System.out.println("Error: unable to complete CheckViolate!");
            System.exit(1);
        }
        
        if (violateNum != 0) return true;
        else return false;
    }
    
    //ExtraCredit part2
    public static void addEmployee(Connection conn, String fname, String minit, String lname, String ssn, String bdate, String addr, String sex,
            String salary, String superssn, String dno) {

        System.out.println("\nAdding employee to database...");

        try {
            String sql = "INSERT INTO EMPLOYEE VALUES ('" + fname + "', '" + minit + "', '" + lname + "', '" + ssn
                    + "', '" + bdate + "', '" + addr + "', '" + sex + "', " + salary + ", '" + superssn + "', " + dno
                    + ",' ')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("\nAdd Completely");
        } catch (SQLException e) {
            System.err.println("Error: unable to add employee to database.");
            e.printStackTrace();
            System.exit(1);
        }

    }
    
    public static void addProjectHours(Connection conn, String ssn, String pno, String hours) {

        System.out.println("Adding project hours to WORKS_ON..");

        try {
            String sql = "INSERT INTO WORKS_ON VALUES (" + ssn + ", " + pno + ", "
                    + hours + ")";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("\nAdd project Completely");

        } catch (SQLException e) {
            System.err.println("Error: unable to add employee projects to database.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    //ExtraCredit part3
    public static void DeleteEmployee(Connection conn,String ssn, String Pno){

        System.out.println("\nDeleting employee from database...");

        try {
            
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM WORKS_ON Where Essn='"+ssn+"' AND Pno='"+Pno+"'";
            stmt.executeQuery(sql);
            System.out.println("\nDelete Completely");
            
        } catch (SQLException e) {
            System.err.println("Error: unable to delete employee to database.");
            e.printStackTrace();
            System.exit(1);
        }

    }
    
    //ExtraCredit part4
    public static void DeleteProject(Connection conn, String Pno){

        System.out.println("\nDeleting the project from database...");

        try {
            
            System.out.println("Deleting the employees on the project...");
            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM WORKS_ON Where Pno='"+Pno+"'";
            stmt.executeQuery(sql);
            System.out.println("Delete employees Completely");
            
            Statement stmt2 = conn.createStatement();
            String sql2 = "DELETE FROM PROJECT Where Pnumber='"+Pno+"'";
            stmt2.executeQuery(sql2);
            System.out.println("Delete Completely");
            
        } catch (SQLException e) {
            System.err.println("Error: unable to delete project to database.");
            e.printStackTrace();
            System.exit(1);
        }

    }
    
    //ExtraCredit part5
    public static void updateEmployee(Connection conn, String ssn, String bdate, String addr, String sex,
            String salary, String superssn, String dno) {

        System.out.println("\nUpdating employee to database...");

        try {
            String sql = "UPDATE EMPLOYEE SET BDATE='"+bdate+"', ADDRESS='"
                    + addr+"', SALARY="+salary+", SUPERSSN='"+superssn+"', DNO="+dno+" WHERE SSN='"+ssn+"'";
            
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            System.out.println("\nUpdate Completely");
        } catch (SQLException e) {
            System.err.println("Error: unable to update employee to database.");
            e.printStackTrace();
            System.exit(1);
        }

    }
    
}
