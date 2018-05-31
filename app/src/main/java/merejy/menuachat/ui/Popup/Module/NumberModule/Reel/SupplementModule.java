package merejy.menuachat.ui.Popup.Module.NumberModule.Reel;

import android.app.Activity;
import android.widget.TextView;

import merejy.menuachat.kernel.Needing;

public class SupplementModule implements ReelNumberPopupModule {

    private TextView text_supplments;
    private TextView text_total;

    public SupplementModule (TextView text_supplments , TextView text_total){
        this.text_supplments = text_supplments;
        this.text_total = text_total;
    }



    @Override
    public Runnable getMethodOnComfirm(final double val) {
        return new Runnable() {
            @Override
            public void run() {
                Needing needing = Needing.getNeeding();
                if(val != 0 && !Double.isNaN(val)){
                    needing.addSupplements(val);
                    text_supplments.setText(""+needing.getSupplements());
                    text_total.setText(""+needing.getTotal());
                }
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
