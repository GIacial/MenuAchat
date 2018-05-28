package merejy.menuachat.ui.Popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.ui.Activity.ListeIngredientActivity;
import merejy.menuachat.ui.Activity.PlatCreator;
import merejy.menuachat.ui.Activity.ToActivity;

public class QuantitePopUp extends DialogFragment {


    static private Ingredient ingredient;
    static private ToActivity target;
    private  int number = 1;

    static public void showDialog(Activity a , Ingredient ingredient, ToActivity target){
        QuantitePopUp.ingredient = ingredient;
        QuantitePopUp.target = target;
            new QuantitePopUp().show(a.getFragmentManager(),"quantiteDialog");
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
                NeedingIngredient need = new NeedingIngredient(ingredient);
                need.addQuantite(number-1);

                Intent secondeActivite = null ;
                if(need.getQuantite() == 0){
                    Toast.makeText(QuantitePopUp.this.getActivity(),R.string.error_zeroQuantite,Toast.LENGTH_LONG).show();
                }
                switch (QuantitePopUp.target){
                    case LIST_INGREDIENT :
                        secondeActivite = new Intent(QuantitePopUp.this.getActivity(),ListeIngredientActivity.class);
                        if(need.getQuantite() != 0){
                            Needing.getNeeding().add(need);
                        }
                                                break;
                    case PLAT_CREATOR:
                        secondeActivite = new Intent(QuantitePopUp.this.getActivity(),PlatCreator.class);

                        for(int i = 0 ; i<number ; i++){
                            PlatCreator.addIngredient(ingredient);
                        }
                        break;
                    default:break;
                }

                if(secondeActivite != null){
                    secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                    QuantitePopUp.this.getActivity().startActivity(secondeActivite);
                }


            }
        })
                .setNegativeButton(R.string.text_anullation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                            //code d'annulation
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}

