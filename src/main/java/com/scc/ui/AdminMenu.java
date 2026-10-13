package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.model.user;
import java.util.Scanner;

public class AdminMenu {
    private final UserDao userDao;
    private final Scanner scanner;


    public AdminMenu(UserDao userDao, Scanner scanner){
        this.userDao = userDao;
        this.scanner = scanner;
    }

    public void display(user admin){
        boolean loggedIn = true;
        while (loggedIn){
            System.out.println("\n=================================");
            System.out.println("           ADMIN DASHBOARD         ");
            System.out.println("\n=================================");
            System.out.println("1. View All Users");
            System.out.println("2. View Pending Users Approvals");
            System.out.println("3. Approve Users Registration");
            System.out.println("4. Add New Users");
            System.out.println("5.Update Users Role / status");
            System.out.println("6. Delete Users Account");
            System.out.println("7. Logout");
            System.out.print("Choose an Option (1-7): ");

            String choice = scanner.nextLine().trim();
        }
    }
}
