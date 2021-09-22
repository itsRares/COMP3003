package sec.app;

import sec.api.*;

public class HomeMenu {
    public void start() {
        menu();
    }

    private void menu() {
        //Other menues
        EvalMenu calculate = new EvalMenu();
        PluginMenu pluginMenu = new PluginMenu();

        int choice = 0;
        while (choice != 3) {
            Helpers.clearScreen();
            System.out.println("---------------------------------------");
            System.out.println("           Welcome to equal");
            System.out.println("---------------------------------------");
            System.out.println("1. Execute Equation");
            System.out.println("2. Plugin Manager");
            System.out.println("3. Exit");

            System.out.print("What's your choice? (1-3) > ");
            choice = Helpers.getInt();
            
            switch (choice) {
                case 1:
                    calculate.start();
                    break;
                case 2:
                    pluginMenu.start();
                    break;
                case 3:
                    System.out.println("---------------------------------------");
                    System.out.println("      Thanks! Have a great day.");
                    break;
            }
        }
       
    }
}
