package com.zetyun.rt.rocksdb;

import java.sql.*;
import java.util.Arrays;
import java.util.UUID;

public class TiDBWriteExample {
//    public static void main(String[] args) {
//        Connection conn = null;
//        Statement stmt = null;
//        try {
//            try {
//                Class.forName("com.mysql.jdbc.Driver");
//            } catch (Exception e) {
//                System.out.println(e);
//            }
//            conn = (Connection) DriverManager.getConnection("jdbc:mysql://172.20.3.19:4000/huzx", "root", "");
//            System.out.println("Connection is created successfully:");
//            stmt = (Statement) conn.createStatement();
//            String query1 = "INSERT INTO InsertDemo " + "VALUES (1, 'John', 34)";
//            stmt.executeUpdate(query1);
//            query1 = "INSERT INTO InsertDemo " + "VALUES (2, 'Carol', 42)";
//            stmt.executeUpdate(query1);
//            System.out.println("Record is inserted in the table successfully..................");
//        } catch (SQLException excep) {
//            excep.printStackTrace();
//        } catch (Exception excep) {
//            excep.printStackTrace();
//        } finally {
//            try {
//                if (stmt != null)
//                    conn.close();
//            } catch (SQLException se) {
//            }
//            try {
//                if (conn != null)
//                    conn.close();
//            } catch (SQLException se) {
//                se.printStackTrace();
//            }
//        }
//        System.out.println("Please check it in the MySQL Table......... ……..");
//    }


    private Connection connect = null;
    private Statement statement = null;
    private ResultSet resultSet = null;

    final private String host = "172.20.3.19:4000";
    final private String user = "root";
    final private String passwd = "";


    public static void main(String[] args) throws Exception{

        final String input = "Hello Worlz";
        byte[] result = Arrays.copyOf(input.getBytes(), input.getBytes().length);
        ++result[input.getBytes().length - 1];
        System.out.println("Huzx=>" + new String(result));

        // TiDBWriteExample obj = new TiDBWriteExample();

        // obj.insert2DB();
        /*
        String query="SELECT * FROM `InsertDemo`";
        obj.readDataFromDB(query);
         */
    }

    public void insert2DB() throws Exception {
        String insertStart = "INSERT INTO InsertDemo01 VALUES ("; // + "1, 'John', 34)";
        String insertEnd = ")";


        // This will load the MySQL driver, each DB has its own driver
        // Class.forName("com.mysql.jdbc.Driver");
        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

        // Setup the connection with the DB
        connect = DriverManager
                .getConnection("jdbc:mysql://" + host + "/huzx?"
                        + "user=" + user + "&password=" + passwd + "&useUnicode=true&characterEncoding=UTF-8");
        // Statements allow to issue SQL queries to the database
        statement = connect.createStatement();

        UUID uuid = UUID.randomUUID();
        for (long l = 1; l < 100000000L; l++) {
            String value = uuid.toString() + uuid.toString() + uuid.toString() + uuid.toString() + uuid.toString();
            String result = l + ",'" + value + "'," + l;
            String command = insertStart + result + insertEnd;
            statement.executeUpdate(command);
        }



    }

    public void readDataFromDB(String query) throws Exception {
        try {
            // This will load the MySQL driver, each DB has its own driver
            // Class.forName("com.mysql.jdbc.Driver");
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://" + host + "/huzx?"
                            + "user=" + user + "&password=" + passwd + "&useUnicode=true&characterEncoding=UTF-8");
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            // Result set get the result of the SQL query
            resultSet = statement
                    .executeQuery(query);
            System.out.println("id,name,email,title,code");
            int i=0;
            // ResultSet is initially before the first data set
            while (resultSet.next()) {

                // It is possible to get the columns via name
                // also possible to get the columns via the column number
                // which starts at 1
                // e.g. resultSet.getSTring(2);
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");

                //For Displaying the values
                i= i+1;
                System.out.print("row: " + i + "==>");
                System.out.println(id + "," + name + "," + age);

            }

        } catch (Exception e) {
            throw e;
        } finally {
            close();
        }

    }

    // You need to close the resultSet
    private void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
        } catch (Exception e) {

        }
    }
}
