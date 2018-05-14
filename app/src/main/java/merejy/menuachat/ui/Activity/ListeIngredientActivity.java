package merejy.menuachat.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.ui.ViewAdapter.IngredientAdapter;

public class ListeIngredientActivity extends ActivitySaveOnClose {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_ingredient_activity);



         Needing n = Needing.getNeeding();

     //recup de la liste
        RecyclerView listUi = findViewById(R.id.ingredientList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        listUi.setHasFixedSize(true);

        // use a linear layout manager
        listUi.setLayoutManager(new LinearLayoutManager(this));


        //total
        TextView total = findViewById(R.id.prixTotal);
        total.setText(n.getTotal()+"");

        //mets un adapter
        listUi.setAdapter(new IngredientAdapter(n.getIngredients(),listUi,this,total));



        //button plat
        Button p = findViewById(R.id.button_plat);
        p.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(ListeIngredientActivity.this, ListePlatActivity.class);
                startActivity(secondeActivite);
            }
        });

        FloatingActionButton plus = findViewById(R.id.IngredientAjout);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = new Intent(ListeIngredientActivity.this, All_IngredientList.class);
                All_IngredientList.target = ToActivity.LIST_INGREDIENT;
                startActivity(secondeActivite);
            }
        });

    }
}
