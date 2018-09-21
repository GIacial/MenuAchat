package merejy.menuachat.ui.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.ui.Popup.Module.NumberModule.Reel.SupplementModule;
import merejy.menuachat.ui.Popup.Module.QuestionModule.RemoveSupplementsModule;
import merejy.menuachat.ui.Popup.QuestionPopup;
import merejy.menuachat.ui.Popup.ReelNumberPopup;
import merejy.menuachat.ui.ViewAdapter.PlatAdapter;

public class ListePlatActivity extends ActivitySaveOnClose {

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_plat_activity);

         NeedingList n = NeedingList.getNeeding();

        //ui

        RecyclerView listUi = findViewById(R.id.platList);


        // use a linear layout manager
        listUi.setLayoutManager(new LinearLayoutManager(this));


        //calcul du total
        NumberFormat priceFormat = new DecimalFormat("#.##");
        final TextView total = findViewById(R.id.prixTotal);
        total.setText(priceFormat.format(n.getTotal()));


        //supplements
        final TextView supplements = findViewById(R.id.text_supplements);
        supplements.setText(priceFormat.format(n.getSupplements()));
        supplements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReelNumberPopup.showDialog(new SupplementModule(supplements,total),ListePlatActivity.this);
            }
        });

        TextView textSupplements = findViewById(R.id.text_clearSupplement);
        textSupplements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPopup.showDialog(ListePlatActivity.this,new RemoveSupplementsModule(ListePlatActivity.this,supplements,total));
            }
        });

        //mets un adapter
        listUi.setAdapter(new PlatAdapter(n.getPlats(),listUi,this,total));


        //mise en marche du butoon
        Button b = findViewById(R.id.buttonGenere);
        b.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(ListePlatActivity.this, ListeIngredientActivity.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });

        FloatingActionButton p = findViewById(R.id.platAjout);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(ListePlatActivity.this, AllPlatList.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                startActivity(secondeActivite);
            }
        });
    }

    @Override
    public ToActivity getActivityEnum() {
        return ToActivity.LIST_PLAT;
    }
}
