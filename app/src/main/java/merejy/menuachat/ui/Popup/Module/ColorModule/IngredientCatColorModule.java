package merejy.menuachat.ui.Popup.Module.ColorModule;

import android.graphics.Color;
import android.view.View;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.kernel.ColorManager;

public class IngredientCatColorModule implements ColorModule {

    private CategorieIngredient categorieIngredient;
    private View itemView;

    public IngredientCatColorModule (CategorieIngredient cat, View itemView){
        categorieIngredient = cat;
        this.itemView = itemView;
    }

    @Override
    public Runnable getMethodOnComfirm(final int red, final int green, final int blue) {
        return new Runnable() {
            @Override
            public void run() {
                ColorManager.setColor(categorieIngredient, Color.rgb(red,green,blue));
                itemView.setBackgroundColor(ColorManager.getIngredientColor(categorieIngredient));
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
