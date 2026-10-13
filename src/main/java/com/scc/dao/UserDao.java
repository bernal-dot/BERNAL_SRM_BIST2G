
package com.scc.dao;

import com.scc.model.user;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDao {

    public UserDao() {
        try (Connection conn = DatabaseConnection.getConnection()) {
            defaultAdminGenerator(conn);
        } catch (SQLException e) {
            System.err.println("Error seeding default admin:");
            e.printStackTrace();
        }
    }

    public void defaultAdminGenerator(Connection conn) {
        String checkSql =
                "SELECT COUNT(*) FROM users WHERE roles = 'ADMIN'";

        String insertSql =
                "INSERT INTO users (username, password, roles, status) " +
                        "VALUES (?, ?, ?, ?)";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {

            if (rs.next() && rs.getInt(1) == 0) {
                try (PreparedStatement pstmt =
                             conn.prepareStatement(insertSql)) {

                    pstmt.setString(1, "admin");
                    pstmt.setString(2, "admin123");
                    pstmt.setString(3, "ADMIN");
                    pstmt.setString(4, "APPROVED");

                    pstmt.executeUpdate();
                    System.out.println("-> Default Admin created");
                }
            }

        } catch (SQLException e) {
            System.err.println("Error creating default admin:");
            e.printStackTrace();
        }
    }

    public boolean registerUser(
            String username, String password, String role) {

        String sql =
                "INSERT INTO users (username, password, roles, status) " +
                        "VALUES (?, ?, ?, 'PENDING')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role.toUpperCase());

            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println(
                        "-> Successful Registration, waiting for Approval");
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Registration failed:");
            e.printStackTrace();
        }

        return false;
    }

    public user login(String username, String password) {

        String sql = "SELECT id, username, password, roles, status " +
                        "FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (!rs.next()) {
                    System.out.println(
                            "-> Invalid username or password");
                    return null;
                }

                String status = rs.getString("status");

                if (status == null ||
                        !status.equalsIgnoreCase("APPROVED")) {

                    if ("PENDING".equalsIgnoreCase(status)) {
                        System.out.println(
                                "-> Account is pending, waiting for Approval");
                    } else {
                        System.out.println(
                                "-> Account is not approved or is inactive");
                    }

                    return null;
                }

                user loggedInUser = new user(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("roles"),
                        status
                );

                System.out.println("-> Login successful!");
                return loggedInUser;
            }

        } catch (SQLException e) {
            System.err.println("Database error during login:");
            System.err.println("Error type: " +
                    e.getClass().getName());
            System.err.println("Error message: " +
                    e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
