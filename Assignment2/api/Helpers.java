package sec.api;

import java.util.Scanner;

public class Helpers {
    //Clears the screen
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    //Gets a int
    public static int getInt() {
        boolean completed = true;
        int choice = 0;
        while (completed) {
            try {
                Scanner scan = new Scanner(System.in);
                choice = scan.nextInt();
                completed = false;
            } catch (Exception e) {
                System.out.print("No, input a number please ... ");
            }
        }
        return choice;
    }

    //Gets a string
    public static String getString() {
        boolean completed = true;
        String choice = null;
        while (completed) {
            try {
                Scanner scan = new Scanner(System.in);
                choice = scan.nextLine();
                completed = false;
            } catch (Exception e) {
                System.out.println("No, input a string please ... ");
            }
        }
        return choice;
    }
}