package merejy.menuachat.ui.Button;

import android.content.Context;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.kernel.Needing.NeedingIngredient.NeedingIngredient;

public class TakingIngredientButton extends android.support.v7.widget.AppCompatImageButton {

    private boolean take = false;
    private boolean alreadyHave = false;

    public TakingIngredientButton(Context context) {
        super(context);
        this.setImageResource(android.R.drawable.checkbox_off_background);
        this.setBackground(null);
    }

    public void nextState(){
        boolean oldTake = take;
        take = !oldTake && !alreadyHave;
        alreadyHave = oldTake && !alreadyHave;
        updateImage();
    }

    public boolean getTake(){
        return take;
    }

    public boolean getAlreadyHave(){
        return alreadyHave;
    }

    public void setState(InterfaceNeedingIngredient i){
        this.take = i.isTake();
        this.alreadyHave = i.isAtHome();
        updateImage();
    }

    private void updateImage(){
        if(take){
            this.setImageResource(android.R.drawable.checkbox_on_background);
        }
        else if(alreadyHave){
            this.setImageResource(R.drawable.maison_icon);
        }
        else{
            this.setImageResource(android.R.drawable.checkbox_off_background);
        }
    }
}
