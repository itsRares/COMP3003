package sec.api;

import java.util.UUID;

public class Expression {

    private UUID id;
    private String expression;
    private int max;
    private int min;
    private int inc;
    private double result;

    public Expression(String expression, int max, int min, int inc) {
        id = UUID.randomUUID();
        this.expression = expression;
        this.max = max;
        this.min = min;
        this.inc = inc;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public int getMax() {
        return max;
    }

    public int getInc() {
        return inc;
    }

    public void setInc(int inc) {
        this.inc = inc;
    }

    public int getMin() {
        return min;
    }

    public double getResult() {
        return result;
    }
}