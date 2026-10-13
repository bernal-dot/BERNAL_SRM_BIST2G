package com.scc;


import com.scc.dao.UserDao;
import com.scc.model.user;
import com.scc.ui.AdminMenu;

import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final UserDao userDao = new UserDao();

    public static void main(String[] args){

        System.out.println("\n================================");
        System.out.println("STUDENT RECORDS MANAGEMENT");
        System.out.println("\n================================");
        System.out.println("1. LOGIN");
        System.out.println("2. REGISTER");
        System.out.println("3. EXIT");
        System.out.print(" Choose an option: ");
        String choice = sc.nextLine();

        switch (choice){
            case "1":
                handleLogin();
                break;
            case "2":
                System.out.print("Enter New Username: ");
                String user = sc.nextLine();
                System.out.print("Enter Password: ");
                String pass = sc.nextLine();

                System.out.print("Select Role [1] teacher [2] admin: ");
                int rolechoice = sc.nextInt();
                String role;
                switch (rolechoice){
                    case 1:
                        role = "teacher";
                        break;
                    case 2:
                        role = "student";
                        break;
                    default:
                        role = null;

                }

                if(role != null){
                    userDao.registerUser(user, pass, role);

                }else {

                    System.out.println("Invalid role selection ");
                }

                break;
        }
    }

    private static void handleLogin(){
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        user user = userDao.login(username, password);

        if(user != null){
            System.out.println("\n -> Welcome "+user.username()+"| ROLE: "+user.roles()+"");
            switch (user.roles()){
                case "ADMIN" -> new AdminMenu(userDao, sc).display(user);
                default -> System.out.println("Account roles not found!");
            }
        }
    }


}
