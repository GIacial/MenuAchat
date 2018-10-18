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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import merejy.menuachat.R;
import merejy.menuachat.ui.Popup.Module.NumberModule.Reel.ReelNumberPopupModule;

public class ReelNumberPopup extends DialogFragment {

    static private ReelNumberPopupModule module;

    static public void showDialog(ReelNumberPopupModule module, Activity activity){
        ReelNumberPopup.module = module;

        new ReelNumberPopup().show(activity.getFragmentManager(),"priceDialog");
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
        module.configLayout(layout);
        final EditText prix = layout.findViewById(R.id.popup_priceEditor);



        TextView msg = layout.findViewById(R.id.textQuestion);
        msg.setText(module.getQuestion());

        builder.setView(layout);
        builder.setPositiveButton(R.string.text_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if(!prix.getText().toString().equals("")){

                    Runnable method = null;
                    try{
                       method = module.getMethodOnComfirm(Double.parseDouble(prix.getText().toString()));
                    }
                    catch (NumberFormatException | NullPointerException exception){
                        System.err.println("Le double est mal formé");
                    }
                    if(method != null){
                        method.run();
                    }
                }

            }
        })
                .setNegativeButton(R.string.text_anullation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Runnable method = module.getMethodOnAnuller();
                        if(method != null){
                            method.run();
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onStop() {
        super.onStop();
        ReelNumberPopup.module = null;
    }
}
