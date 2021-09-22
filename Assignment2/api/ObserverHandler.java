package sec.api;

import java.util.ArrayList;
import java.util.List;

public class ObserverHandler {
    private List<Observer> observers;
    private Expression state;

    //create an object of SingleObject
    private static ObserverHandler instance = new ObserverHandler();

    //make the constructor private so that this class cannot be
    //instantiated
    private ObserverHandler(){
        this.observers = new ArrayList<Observer>();
    }

    //Get the only object available
    public static ObserverHandler getInstance(){
        return instance;
    }

    public Expression getState() {
        return state;
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void setState(double result, int inc) {
        this.state.setResult(result);
        this.state.setInc(inc);
        notifyAllObservers();
    }

    public void setEquation(Expression eqObj) {
        this.state = eqObj;
    }

    public void attach(Observer observer){
        observers.add(observer);		
    }

    public void notifyAllObservers(){
        for (Observer observer : observers) {
            //Only notify plugins not functions
            if (observer instanceof PluginObserver) {
                ((PluginObserver) observer).update();
            }
        }
    }
}