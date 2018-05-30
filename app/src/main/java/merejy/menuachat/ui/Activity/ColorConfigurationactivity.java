package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.ui.ViewAdapter.ColorChoiceAdapter.IngredientCatColorAdapter;
import merejy.menuachat.ui.ViewAdapter.ColorChoiceAdapter.PrixColorAdapter;


public class ColorConfigurationactivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_preferences);

        //adapter
        RecyclerView colorCatIngredient = findViewById(R.id.configCouleurCategoriePlat);
        colorCatIngredient.setLayoutManager(new LinearLayoutManager(this));
        colorCatIngredient.setAdapter( new IngredientCatColorAdapter(CategorieIngredient.values(),this));

        //adapter
        RecyclerView colorPrice = findViewById(R.id.configCouleurPrix);
        colorPrice.setLayoutManager(new LinearLayoutManager(this));
        colorPrice.setAdapter( new PrixColorAdapter(PriceComparator.values(),this));

        Button comfirmer = findViewById(R.id.button_comfirmer);
        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity = ToActivity.getIntentToGoTo(ColorConfigurationactivity.this,ColorConfigurationactivity.nextActivity);
                if(secondActivity != null){
                    secondActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(secondActivity);
                }
            }
        });
    }

    @Override
    ToActivity getActivityEnum() {
        return ToActivity.COLOR_CONFIGURATOR;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ColorManager.save();
    }

    //static
    public static ToActivity nextActivity = null;
}
