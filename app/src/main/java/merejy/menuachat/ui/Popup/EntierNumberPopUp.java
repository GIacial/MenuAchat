package merejy.menuachat.ui.Popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import merejy.menuachat.R;
import merejy.menuachat.ui.Popup.Module.NumberModule.Entier.EntierNumberPopupModule;

public class EntierNumberPopUp extends DialogFragment {



    private  int number = 1;
    static private EntierNumberPopupModule module;

    static public void showDialog(Activity a , EntierNumberPopupModule module){
            EntierNumberPopUp.module = module;
            new EntierNumberPopUp().show(a.getFragmentManager(),"quantiteDialog");
    }



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View layout = inflater.inflate(R.layout.popup_quantite, null);
        builder.setView(layout);

        ImageButton plus = layout.findViewById(R.id.buttonPlus);
        ImageButton moins = layout.findViewById(R.id.buttonMoins);
        final TextView text = layout.findViewById(R.id.textQuantite);
        text.setText(number+"");
        TextView msg = layout.findViewById(R.id.textQuestion);
        msg.setText(module.getQuestion());

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number ++;
                text.setText(number+"");
            }
        });

        moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number --;
                if(number < 0){
                    number = 0;
                }
                text.setText(number+"");
            }
        });


        builder.setPositiveButton(R.string.text_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //code de comfirmation
                Runnable method = module.getMethodOnComfirm(number);
                if(method != null){
                    method.run();
                }

            }
        })
                .setNegativeButton(R.string.text_anullation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            //code d'annulation
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
        EntierNumberPopUp.module = null;
    }
}

