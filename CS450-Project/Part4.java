/**
 * Author:  Zhiwen Luo
 * Date:    11/25/2017
 * Purpose: Project Part 4
**/

import oracle.jdbc.OracleDriver;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Part4 {

    // Connection
    static final String JDBC_URL = "jdbc:oracle:thin:@apollo.ite.gmu.edu:1521:ite10g";

    //private Driver driver = null;
    private Connection conn = null;

    public boolean connectToDatabase(String USER, String PASS) {

        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error: unable to close connection.");
            }
        }

        try {
            System.out.println("Attempting to obtain driver...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("Driver obtained.\n");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Driver could not be loaded!");
            return false;
        }

        // attempt to connect
        try {
            System.out.println("Attempting to connect to database...");
            conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
            System.out.println("Connection successful\n");
        } catch (SQLException e) {
            System.out.println("Error: unable to connect to database!");
            return false;
        }

        return true;
    }

    public void closeConnection() {
        if(conn != null) {
            try {
                conn.close();
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Error: unable to close connection.");
            }
        }
    }

    public boolean validateManager(long ssn) {
        // query vars
        String sql = null;
        Statement qry = null;
        ResultSet rs = null;

        try {
            qry = conn.createStatement();
            sql = "SELECT Mgrssn FROM DEPARTMENT";
            rs = qry.executeQuery(sql);

            while(rs.next()) {
                if(rs.getLong("Mgrssn") == ssn) {
                    return true;
                }
            }

            qry.close();
            rs.close();
            // if this stage is reached, then ssn was not found
            return false;
        } catch (SQLException e) {
            System.err.println("Error: unable to validate manager");
            return false;
        }
    }

    public void addEmployee(String fname, String minit, String lname, String ssn, String bdate, String addr,
                            String sex, String salary, String superssn, String dno) {

        System.out.println("\nAdding employee to database...");

        try {
            String sql = "INSERT INTO EMPLOYEE VALUES ('" + fname + "', '" + minit + "', '" + lname + "', " +
                         ssn + ", '" + bdate + "', '" + addr + "', '" + sex + "', " + salary + ", " +
                         superssn + ", " + dno + ", ' ')";
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.err.println("Error: unable to add employee to database.");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public void addProjectHours(String ssn, ArrayList<String> projects) {

        System.out.println("Adding project hours to WORKS_ON");

//        int index = 0;
//        Iterator<String> itr = projects.iterator();
//        while(itr.hasNext()) {
//            System.out.println("[" + index++ + "]: " + itr.next());
//        }

        int numProjects = projects.size()/3;
        int projCount = 1;

        for(int i = 0; i < numProjects; i++ ) {

            if(Double.parseDouble(projects.get(projCount+1)) < 0.01) {
                projCount += 3;
                continue;
            }

            try {
                String sql = "INSERT INTO WORKS_ON VALUES (" + ssn + ", " + projects.get(projCount++) +
                        ", " + projects.get(projCount) +")";
                projCount += 2;

                Statement stmt = conn.createStatement();
                stmt.executeUpdate(sql);

            } catch (SQLException e) {
                System.err.println("Error: unable to add employee projects to database.");
                e.printStackTrace();
                System.exit(1);
            }
        }

    }

    public void addDependent(String ssn, String name, String sex, String bdate, String relation) {

        System.out.println("Adding employee dependents to database\n");

        try {
            String sql = "INSERT INTO DEPENDENT VALUES (" + ssn + ", '" + name + "', '" + sex +
                    "', '" + bdate + "', '" + relation + "')";

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.err.println("Error: unable to add dependent to database.");
            e.printStackTrace();
            System.exit(1);
        }

    }

    public ArrayList<String> getDependent(String ssn) {
        // query vars
        String sql      = null;
        Statement qry   = null;
        ResultSet rs    = null;

        ArrayList<String> al = new ArrayList<>();

        try {
            qry = conn.createStatement();
            sql = "SELECT * FROM DEPENDENT WHERE Essn=" + ssn;
            rs = qry.executeQuery(sql);

            while(rs.next()) {
                al.add(rs.getString("Dependent_name"));
                al.add(rs.getString("Sex"));
                al.add(rs.getString("Bdate"));
                al.add(rs.getString("Relationship"));
            }

            // close result set and statement
            rs.close();
            qry.close();
            //conn.close();
        } catch (SQLException e) {
            System.err.println("Error: unable to retrieve dependents");
            e.printStackTrace();
            System.exit(1);
        }

        return al;
    }

    public ArrayList<String> getProjects(String ssn) {
        // query vars
        String sql      = null;
        Statement qry   = null;
        ResultSet rs    = null;

        ArrayList<String> al = new ArrayList<>();

        try {
            qry = conn.createStatement();
            sql = "SELECT Pname, Pnumber FROM EMPLOYEE JOIN PROJECT ON Dno=Dnum " +
                    "WHERE Ssn=" + ssn;
            rs = qry.executeQuery(sql);

            while(rs.next()) {
                al.add(rs.getString("Pname"));
                al.add(rs.getString("Pnumber"));
            }

            // close result set and statement
            rs.close();
            qry.close();
            //conn.close();
        } catch (SQLException e) {
            System.err.println("Error: unable to retrieve projects!");
            e.printStackTrace();
            System.exit(1);
        }

        return al;
    }

    public ArrayList<String> getProjectsAndHours(String ssn) {
        // query vars
        String sql      = null;
        Statement qry   = null;
        ResultSet rs    = null;

        ArrayList<String> al = new ArrayList<>();

        try {
            qry = conn.createStatement();
            sql = "SELECT Pname, Hours FROM WORKS_ON JOIN PROJECT ON Pno=Pnumber " +
                    "WHERE Essn=" + ssn;
            rs = qry.executeQuery(sql);

            while(rs.next()) {
                al.add(rs.getString("Pname"));
                al.add(rs.getString("Hours"));
            }

            // close result set and statement
            rs.close();
            qry.close();
            //conn.close();
        } catch (SQLException e) {
            System.err.println("Error: unable to retrieve projects and hours");
            return null;
        }

        return al;
    }

    public ArrayList<String> getEmployeeInfo(String ssn) {
        // query vars
        String sql      = null;
        Statement qry   = null;
        ResultSet rs    = null;

        ArrayList<String> al = new ArrayList<>();

        try {
            qry = conn.createStatement();
            sql = "SELECT * FROM EMPLOYEE WHERE Ssn=" + ssn;
            rs = qry.executeQuery(sql);

            while(rs.next()) {
                al.add(rs.getString("Fname"));
                al.add(rs.getString("Minit"));
                al.add(rs.getString("Lname"));
                al.add(rs.getString("Ssn"));
                al.add(rs.getString("Bdate").substring(0, 11));
                al.add(rs.getString("Address"));
                al.add(rs.getString("Sex"));
                al.add(rs.getString("Salary"));
                al.add(rs.getString("Superssn"));
                al.add(rs.getString("Dno"));
            }

            // close result set and statement
            rs.close();
            qry.close();
            //conn.close();
        } catch (SQLException e) {
            System.err.println("Error: unable to retrieve employee information.");
            System.exit(1);
        }

        return al;
    }

    public static void main(String[] args) {
        Part4 p4 = new Part4();
    }
}
