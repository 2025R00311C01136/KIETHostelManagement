package com.kiet.hostel;

import java.sql.*;
import java.util.Scanner;

public class HostelManagement {

    static Scanner sc = new Scanner(System.in);

    static Connection con = DBConnection.getConnection();


    // ==========================================
    // PART (a): INSERT HOSTEL STUDENT RECORDS
    // ==========================================

    public static void insertStudents() {

        String sql = "INSERT INTO HostelStudent " +
                "(student_id, student_name, roll_number, hostel_name, room_number, fee) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try {

            // Insert at least three records

            for (int i = 1; i <= 3; i++) {

                System.out.println("\nEnter Student " + i + " Details");

                System.out.print("Enter Student ID: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.print("Enter Student Name: ");
                String name = sc.nextLine();

                System.out.print("Enter Roll Number: ");
                String roll = sc.nextLine();

                System.out.print("Enter Hostel Name: ");
                String hostel = sc.nextLine();

                System.out.print("Enter Room Number: ");
                String room = sc.nextLine();

                System.out.print("Enter Hostel Fee: ");
                double fee = Double.parseDouble(sc.nextLine());


                PreparedStatement ps = con.prepareStatement(sql);

                ps.setInt(1, id);
                ps.setString(2, name);
                ps.setString(3, roll);
                ps.setString(4, hostel);
                ps.setString(5, room);
                ps.setDouble(6, fee);


                try {

                    int rows = ps.executeUpdate();

                    if (rows > 0) {

                        System.out.println("Student Inserted Successfully!");

                    }

                } catch (SQLException e) {

                    // Handle Duplicate Student ID

                    if (e.getErrorCode() == 1062) {

                        System.out.println("Duplicate Student ID! Record Already Exists.");

                    } else {

                        e.printStackTrace();
                    }
                }

                ps.close();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


    // ==========================================
    // PART (b): CONDITIONAL RETRIEVAL AND SORTING
    // ==========================================

    public static void retrieveStudents() {

        System.out.print("\nEnter Hostel Name: ");

        String hostel = sc.nextLine();


        String sql = "SELECT * FROM HostelStudent " +
                "WHERE hostel_name = ? AND fee > 50000 " +
                "ORDER BY fee DESC";


        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, hostel);

            ResultSet rs = ps.executeQuery();


            boolean found = false;

            System.out.println("\nStudents Having Fee Greater Than 50000");

            System.out.println("-------------------------------------");


            while (rs.next()) {

                found = true;

                System.out.println("Student ID: " +
                        rs.getInt("student_id"));

                System.out.println("Student Name: " +
                        rs.getString("student_name"));

                System.out.println("Roll Number: " +
                        rs.getString("roll_number"));

                System.out.println("Hostel Name: " +
                        rs.getString("hostel_name"));

                System.out.println("Room Number: " +
                        rs.getString("room_number"));

                System.out.println("Hostel Fee: " +
                        rs.getDouble("fee"));

                System.out.println("-------------------------------------");
            }


            if (!found) {

                System.out.println("No Matching Students Found!");

            }

            rs.close();

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    // ==========================================
    // PART (c): UPDATE HOSTEL FEE
    // ==========================================

    public static void updateFee() {

        System.out.print("\nEnter Student ID: ");

        int id = Integer.parseInt(sc.nextLine());


        System.out.print("Enter New Hostel Fee: ");

        double fee = Double.parseDouble(sc.nextLine());


        String sql = "UPDATE HostelStudent SET fee = ? " +
                "WHERE student_id = ?";


        try {

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setDouble(1, fee);

            ps.setInt(2, id);


            int rows = ps.executeUpdate();


            if (rows > 0) {

                System.out.println("Hostel Fee Updated Successfully!");

            } else {

                System.out.println("Student Record Does Not Exist!");

            }

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    // ==========================================
    // PART (d): SEARCH AND MODIFY HOSTEL DETAILS
    // ==========================================

    public static void updateRoom() {

        System.out.print("\nEnter Student ID: ");

        int id = Integer.parseInt(sc.nextLine());


        // First Retrieve Student Details

        String selectSQL = "SELECT * FROM HostelStudent " +
                "WHERE student_id = ?";


        try {

            PreparedStatement ps1 = con.prepareStatement(selectSQL);

            ps1.setInt(1, id);

            ResultSet rs = ps1.executeQuery();


            if (rs.next()) {

                System.out.println("\nStudent Found!");

                System.out.println("Student ID: " +
                        rs.getInt("student_id"));

                System.out.println("Student Name: " +
                        rs.getString("student_name"));

                System.out.println("Roll Number: " +
                        rs.getString("roll_number"));

                System.out.println("Hostel Name: " +
                        rs.getString("hostel_name"));

                System.out.println("Old Room Number: " +
                        rs.getString("room_number"));

                System.out.println("Fee: " +
                        rs.getDouble("fee"));


                // Accept New Room Number

                System.out.print("\nEnter New Room Number: ");

                String newRoom = sc.nextLine();


                // Update Room Number

                String updateSQL = "UPDATE HostelStudent " +
                        "SET room_number = ? WHERE student_id = ?";


                PreparedStatement ps2 = con.prepareStatement(updateSQL);

                ps2.setString(1, newRoom);

                ps2.setInt(2, id);


                int rows = ps2.executeUpdate();


                if (rows > 0) {

                    System.out.println("\nRoom Number Updated Successfully!");

                }


                // Display Updated Details

                String displaySQL = "SELECT * FROM HostelStudent " +
                        "WHERE student_id = ?";


                PreparedStatement ps3 = con.prepareStatement(displaySQL);

                ps3.setInt(1, id);

                ResultSet rs2 = ps3.executeQuery();


                if (rs2.next()) {

                    System.out.println("\nUpdated Student Details");

                    System.out.println("------------------------");

                    System.out.println("Student ID: " +
                            rs2.getInt("student_id"));

                    System.out.println("Student Name: " +
                            rs2.getString("student_name"));

                    System.out.println("Roll Number: " +
                            rs2.getString("roll_number"));

                    System.out.println("Hostel Name: " +
                            rs2.getString("hostel_name"));

                    System.out.println("Room Number: " +
                            rs2.getString("room_number"));

                    System.out.println("Fee: " +
                            rs2.getDouble("fee"));
                }


                rs2.close();

                ps3.close();

                ps2.close();

            } else {

                System.out.println("Student Record Not Found!");

            }

            rs.close();

            ps1.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    // ==========================================
    // PART (e): AGGREGATE QUERY AND CONDITIONAL DELETE
    // ==========================================

    public static void totalFeeAndDelete() {


        // Calculate Total Hostel Fee

        String sumSQL = "SELECT SUM(fee) AS total_fee " +
                "FROM HostelStudent";


        try {

            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sumSQL);


            if (rs.next()) {

                System.out.println("\nTotal Hostel Fee Collected: Rs. " +
                        rs.getDouble("total_fee"));

            }

            rs.close();

            st.close();


            // Accept Student ID for Deletion

            System.out.print("\nEnter Student ID to Delete: ");

            int id = Integer.parseInt(sc.nextLine());


            // Delete Only If Fee Is Greater Than Zero

            String deleteSQL = "DELETE FROM HostelStudent " +
                    "WHERE student_id = ? AND fee > 0";


            PreparedStatement ps = con.prepareStatement(deleteSQL);

            ps.setInt(1, id);


            int rows = ps.executeUpdate();


            if (rows > 0) {

                System.out.println("Student Record Deleted Successfully!");

            } else {

                System.out.println("Record Cannot Be Deleted!");

                System.out.println("Student Not Found or Fee is Not Greater Than Zero.");

            }

            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }


    // ==========================================
    // MAIN METHOD: MENU-DRIVEN APPLICATION
    // ==========================================

    public static void main(String[] args) {


        // Check Database Connection

        if (con == null) {

            System.out.println("Database Connection Failed!");

            return;
        }


        int choice = 0;


        do {

            System.out.println("\n======================================");

            System.out.println("      KIET HOSTEL MANAGEMENT SYSTEM");

            System.out.println("======================================");

            System.out.println("1. Insert Hostel Student Records");

            System.out.println("2. Retrieve Students Based on Hostel and Fee");

            System.out.println("3. Update Hostel Fee");

            System.out.println("4. Search and Modify Room Number");

            System.out.println("5. Display Total Fee and Delete Student");

            System.out.println("6. Exit");

            System.out.println("======================================");


            System.out.print("Enter Your Choice: ");


            try {

                choice = Integer.parseInt(sc.nextLine());

            } catch (NumberFormatException e) {

                System.out.println("Please Enter a Valid Number!");

                continue;
            }


            switch (choice) {

                case 1:

                    insertStudents();

                    break;


                case 2:

                    retrieveStudents();

                    break;


                case 3:

                    updateFee();

                    break;


                case 4:

                    updateRoom();

                    break;


                case 5:

                    totalFeeAndDelete();

                    break;


                case 6:

                    System.out.println("Application Closed Successfully!");

                    break;


                default:

                    System.out.println("Invalid Choice! Try Again.");

            }


        } while (choice != 6);


        // Close Database Connection

        try {

            con.close();

            sc.close();

            System.out.println("Database Connection Closed!");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}