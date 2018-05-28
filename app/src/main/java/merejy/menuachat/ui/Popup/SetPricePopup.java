package merejy.menuachat.ui.Popup;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.kernel.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.ui.ViewAdapter.IngredientAdapter;

public class SetPricePopup extends DialogFragment {

    static private InterfaceNeedingIngredient actuel = null;
    static private  RecyclerView view;
    static private TextView total;

    static public void showDialog(InterfaceNeedingIngredient actuel , RecyclerView view , Activity a, TextView total){
        SetPricePopup.actuel = actuel;
        SetPricePopup.view = view;
        SetPricePopup.total = total;

        new SetPricePopup().show(a.getFragmentManager(),"priceDialog");
    }


    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View layout = inflater.inflate(R.layout.popup_prix, null);
        final EditText prix = layout.findViewById(R.id.popup_priceEditor);
        builder.setView(layout);
        builder.setPositiveButton(R.string.text_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                if(actuel != null && prix!= null && !prix.getText().toString().equals("") && view != null ){
                    actuel.addPrix(Double.parseDouble(prix.getText().toString()),Needing.getNeeding().getCurrentMag());
                    view.setAdapter(new IngredientAdapter(Needing.getNeeding().getIngredients(),view,SetPricePopup.this.getActivity(),total));
                    total.setText(Needing.getNeeding().getTotal()+"");
                }
                SetPricePopup.view = null;
                SetPricePopup.total = null;
            }
        })
                .setNegativeButton(R.string.text_anullation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            SetPricePopup.view = null;
                            SetPricePopup.total = null;
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}
