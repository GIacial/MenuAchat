package merejy.menuachat.ui.Popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;

import merejy.menuachat.R;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingPlat.NeedingPlat;
import merejy.menuachat.ui.Activity.ToActivity;
import merejy.menuachat.ui.ViewAdapter.AllAccompagnementAdapter;

public class ChoixAccompagnementPopup extends DialogFragment {

    static private Plat platPopup;
    private Plat plat;

    static public void showDialog(Activity a , Plat p ){
        platPopup = p;
        new ChoixAccompagnementPopup().show(a.getFragmentManager(),"ChoixAccompagnementDialog");
    }


    private boolean choiceAccompagnement = false;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        plat = platPopup;
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View layout = inflater.inflate(R.layout.popup_plat_accompagnement, null);
        builder.setView(layout);

        final Spinner accompagnement = layout.findViewById(R.id.accompagnement);
        TextView texte = layout.findViewById(R.id.textQuestion);
        final TextView accompagnementText = layout.findViewById(R.id.textingredientAccompagnement);

        String question = "Voulez vous ajoutez "+plat.getNom()+ " à la liste ?\n\n Composition :\n";
        final List<Ingredient> ingredientList = plat.getIngredients();

        for(Ingredient i : ingredientList){
            question += i.getNom() +"\n";
        }
        texte.setText(question);

        accompagnement.setAdapter(new AllAccompagnementAdapter(Database.getDatabase().getAllPlat()));



        accompagnement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choiceAccompagnement = true;
                final List<Ingredient> ingredientListAccompagnement = ((Plat)parent.getItemAtPosition(position)).getIngredients();
                String ingredientText = new String();
                for(Ingredient i : ingredientListAccompagnement){
                    ingredientText += i.getNom() +"\n";
                }
                accompagnementText.setText(ingredientText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choiceAccompagnement = false;
            }
        });


        builder.setPositiveButton(R.string.text_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //code de comfirmation
                Plat accompagnementPlat = (Plat)accompagnement.getSelectedItem();
                NeedingList.getNeeding().add(new NeedingPlat(plat,accompagnementPlat));
                Intent secondeAct = ToActivity.getIntentToGoTo(ChoixAccompagnementPopup.this.getActivity(),ToActivity.LIST_PLAT);
                if(secondeAct != null){
                    ChoixAccompagnementPopup.this.getActivity().startActivity(secondeAct);
                }

            }
        })
                .setNegativeButton(R.string.text_anullation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
