package sec.app;

import java.lang.reflect.Constructor;
import java.util.List;

import sec.api.*;

public class PluginMenu {
    public void start() {
        menu();
    }

    private void menu() {
        int choice = 0;
        while (choice != 3) {
            Helpers.clearScreen();
            System.out.println("---------------------------------------");
            System.out.println("            Manage Plugins");
            System.out.println("---------------------------------------");
            System.out.println("1. View Plugins");
            System.out.println("2. Load Plugin");
            System.out.println("3. Back");
 
            System.out.print("What's your choice? (1-3) > ");
            choice = Helpers.getInt();
            
            switch (choice) {
                case 1:
                    viewMenu();
                    break;
                case 2:
                    loadMenu();
                    break;
            }
        }
    }

    private void loadMenu() {
        Helpers.clearScreen();
        System.out.println("---------------------------------------");
        System.out.println("            Load a Plugin");
        System.out.println("---------------------------------------");
        try {
            System.out.print("Input package name > ");
            String name = Helpers.getString();

            //First create a class
            Class<?> cls = Class.forName(name);
            //Then get the constructor
            Constructor<?> c2 = cls.getConstructor(ObserverHandler.class);
            //Lastly create a new instance with constructor
            c2.newInstance(ObserverHandler.getInstance());

            System.out.println("Success! Plugin has been loaded in.");
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        System.out.print("\nPress enter to continue ... ");
        Helpers.getString();
    }

    private void viewMenu() {
        Helpers.clearScreen();
        System.out.println("---------------------------------------");
        System.out.println("             View Plugins");
        System.out.println("---------------------------------------");

        List<Observer> observers = ObserverHandler.getInstance().getObservers();

        if (observers.size() == 0) {
            System.out.println("There are currently no plugins loaded ... Load some!");
        } else {
            System.out.println("There are " + observers.size() + " loaded. They are:");
            for (Observer observer : observers) {
                //Depending on type depends on what todo
                if (observer instanceof PluginObserver) {
                    System.out.println("- " + ((PluginObserver) observer).getClass().getSimpleName() + " (Plugin) - " + observer.desc);
                } else if (observer instanceof FunctionObserver) {
                    System.out.println("- " + ((FunctionObserver) observer).getClass().getSimpleName() + " (Function) - " + observer.desc);
                }
                
            }
        }

        System.out.print("\nPress enter to continue ... ");
        Helpers.getString();
    }
}