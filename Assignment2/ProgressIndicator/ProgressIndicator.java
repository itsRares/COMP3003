import sec.api.Expression;
import sec.api.ObserverHandler;
import sec.api.PluginObserver;

import java.text.DecimalFormat;

public class ProgressIndicator extends PluginObserver {

    public ProgressIndicator(ObserverHandler handler) {
        this.desc = "Provides running progress indicator that displays the percentage complete ";
        this.handler = handler;
        this.handler.attach(this);
    }

    @Override
    public void update() {
        //Format the decimal (because randomly it would be 7.000000001)
        DecimalFormat df = new DecimalFormat("#.#");
        Expression eq = handler.getState(); //Get state

        //Calc the percentage done and show it
        int perDone = (int) ((((double) eq.getInc() / (double) eq.getMax())*2) * 10.0);
        System.out.print("[");
        for (int i = 0; i < 20; i++) {
            if (perDone >= i) {
                System.out.print("#");
            } else {
                System.out.print("-");
            }
        }
        System.out.print("] "+df.format(((double) eq.getInc() / (double) eq.getMax()) * 100.0)+"%\n");
    }
}
