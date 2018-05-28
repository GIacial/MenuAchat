package merejy.menuachat.ui.Popup;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import merejy.menuachat.R;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.ui.Activity.ToActivity;
import merejy.menuachat.ui.Popup.QuestionModule.QuestionPopupModule;

public class QuestionPopup extends DialogFragment {


    static QuestionPopupModule module;

    static public void showDialog(Activity a ,QuestionPopupModule module){
        QuestionPopup.module = module;
        new QuestionPopup().show(a.getFragmentManager(),"quantiteDialog");
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
        View layout = inflater.inflate(R.layout.popup_question, null);
        builder.setView(layout);

        //get les object du layout
        TextView question = layout.findViewById(R.id.textQuestion);
        if(module.getQuestion() != null){
            question.setText(module.getQuestion());
        }
        else{
            question.setText(R.string.error_noQuestion);
        }


        builder.setPositiveButton(R.string.text_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //code de comfirmation
                Runnable method = module.getMethodOnComfirm();
                if(method != null){
                    method.run();
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
}
