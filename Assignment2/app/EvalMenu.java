package sec.app;

import sec.api.*;

import java.util.List;

public class EvalMenu {
    private ScriptHandler sh = new ScriptHandler();
    private ObserverHandler ph = ObserverHandler.getInstance();

    public void start() {
        menu();
    }

    private void menu() {
        int choice = 0;
        while (choice != 3) {
            Helpers.clearScreen();
            System.out.println("---------------------------------------");
            System.out.println("        Calculate an Expression");
            System.out.println("---------------------------------------");
            System.out.println("1. Input Expression");
            System.out.println("2. Example : (x-2)+21");
            System.out.println("3. Back");

            System.out.print("What's your choice? (1-3) > ");
            choice = Helpers.getInt();
            
            switch (choice) {
                case 1:
                    InputEquation();
                    break;
                case 2:
                    Example();
                    break;
            }
        }
    }

    private void Example() {
        Helpers.clearScreen();
        System.out.println("---------------------------------------");
        System.out.println("          Example (x-2)+21");
        System.out.println("---------------------------------------");

        int max, min, inc;

        System.out.print("1. Input a max value > ");
        max = Helpers.getInt();
        System.out.print("2. Input a min value > ");
        min = Helpers.getInt();
        System.out.print("3. Input a increment value > ");
        inc = Helpers.getInt();

        String ex = "(x-2)+21";
        //Then calc eq
        runEquation(ex, max, min, inc);
    }

    private void InputEquation() {
        Helpers.clearScreen();
        System.out.println("---------------------------------------");
        System.out.println("          Calculate Expression");
        System.out.println("---------------------------------------");

        int max = 1, min = 1, inc = 1;
        String ex;

        System.out.print("1. Input Expression > ");
        ex = Helpers.getString().toLowerCase();
        if (ex.contains("x")) {
            System.out.print("2. Input a max value > ");
            max = Helpers.getInt();
            System.out.print("3. Input a min value > ");
            min = Helpers.getInt();
            System.out.print("4. Input a increment value > ");
            inc = Helpers.getInt();
        }

        //First set methods
        List<Observer> observers = ph.getObservers();
        for (Observer observer : observers) {
            if (observer instanceof FunctionObserver) {
                sh.importMethod(((FunctionObserver) observer).getClass().getCanonicalName(),"*");
            }
        }

        //Then calc eq
        runEquation(ex, max, min, inc);
    }

    private void runEquation(String ex, int max, int min, int inc) {
        try {
            Expression eqObj = new Expression(ex, max, min, inc); //New expression
            ph.setEquation(eqObj); //Set the state in the observers
            if (ex.contains("x")) {
                for (int x = min; x <= max; x += inc) {
                    //Replace X with value
                    String newEq = ex.replace("x", "(" + x + ")");
                    //Run the script
                    double result = sh.runScript(newEq);
                    System.out.println("x:"+x+", y:"+result);
                    //Set state aka notify plugins
                    ph.setState(result, x);
                }
            } else {
                //Run the script
                double result = sh.runScript(ex);
                System.out.println("x:"+inc+", y:"+result);
                //Set state aka notify plugins
                ph.setState(result, inc);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.toString());
        }
        System.out.print("\nPress enter to continue ... ");
        Helpers.getString();
    }
}