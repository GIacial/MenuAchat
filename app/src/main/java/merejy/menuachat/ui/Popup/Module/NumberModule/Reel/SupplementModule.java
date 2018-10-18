package merejy.menuachat.ui.Popup.Module.NumberModule.Reel;

import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;

public class SupplementModule implements ReelNumberPopupModule {

    private TextView text_supplments;
    private TextView text_total;
    static private NumberFormat priceFormat = new DecimalFormat("#.##");

    public SupplementModule (TextView text_supplments , TextView text_total){
        this.text_supplments = text_supplments;
        this.text_total = text_total;
    }



    @Override
    public Runnable getMethodOnComfirm(final double val) {
        return new Runnable() {
            @Override
            public void run() {
                NeedingList needingList = NeedingList.getNeeding();
                if(val != 0 && !Double.isNaN(val)){
                    needingList.addSupplements(val);
                    text_supplments.setText(priceFormat.format(needingList.getSupplements()));
                    text_total.setText(priceFormat.format(needingList.getTotal()));
                }
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }

    @Override
    public String getQuestion() {
        return text_supplments.getContext().getResources().getString(R.string.text_question_supplements);
    }

    @Override
    public void configLayout(View layout) {

        final EditText prix = layout.findViewById(R.id.popup_priceEditor);
        prix.setInputType(InputType.TYPE_NUMBER_FLAG_SIGNED);
    }
}
