package com.scc.ui;

import com.scc.dao.UserDao;
import com.scc.model.user;
import java.util.Scanner;

public class Authentication {

    private final UserDao userDao;
    private final Scanner sc;

    public Authentication(UserDao userDao, Scanner sc){
        this.userDao = userDao;
        this.sc =sc;

    }

    public void  start(){

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
                        role = "admin";
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

    private void handleLogin(){
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
