import sec.api.FunctionObserver;
import sec.api.ObserverHandler;

public class SECMath extends FunctionObserver
{
    public SECMath(ObserverHandler handler) {
        this.desc = "Provides additional mathematical functions: \n\t- factorial(int) \n\t- fibonacci(int)";
        this.handler = handler;
        this.handler.attach(this);
    }

    //https://www.java67.com/2016/05/fibonacci-series-in-java-using-recursion.html
    public static double fibonacci(int num) {
        if (num == 1 || num == 2){
            return 1;
        }

        return fibonacci(num-1) + fibonacci(num -2); //tail recursion
    }

    //https://www.programiz.com/java-programming/examples/factorial-recursion
    public static double factorial(int num) {
        if (num >= 1) {
            return num * factorial(num - 1);
        } else {
            return 1;
        }
    }
    
}