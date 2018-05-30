package merejy.menuachat.kernel;

import android.app.Activity;
import android.app.admin.DeviceAdminInfo;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;

import merejy.menuachat.R;
import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.CategoriePlats;
import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.database.Database;

public class ColorManager implements Serializable {

    private static ColorManager colorManager = null;
    private static String fileSave = "ColorConfig.sav";

    static public int getPriceColor(PriceComparator priceComparator){
        int color = 0;
        if(colorManager != null){
            color = colorManager.getPriceColorInst(priceComparator);
        }
        return color;
    }

    static public int getIngredientColor(CategorieIngredient priceComparator){
        int color = 0;
        if(colorManager != null){
            color = colorManager.getIngredientColorInst(priceComparator);
        }
        return color;
    }


    static public void load (Activity activity){
        if(colorManager == null){
            try {
                ObjectInputStream stream = new ObjectInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory()+File.separator+ Database.dossierSave,fileSave)));
                colorManager = (ColorManager) stream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(colorManager == null){
                colorManager = new ColorManager(activity);
            }
        }
    }

    static public void save (){
        if(colorManager != null){
            try {
                File dossier = new File(Environment.getExternalStorageDirectory(), Database.dossierSave);
                if(!dossier.exists() || !dossier.isDirectory()){
                    dossier.mkdir();
                }
                ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory()+File.separator+ Database.dossierSave,fileSave)));
                stream.writeObject(colorManager);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //non static
    private HashMap<PriceComparator,Integer> priceColor = new HashMap<>();
    private HashMap<CategorieIngredient,Integer> ingredientCategorieColor = new HashMap<>();
    private HashMap<CategoriePlats,Integer> platsCategoriecolor = new HashMap<>();


    private ColorManager(Activity activity){
        for (PriceComparator priceComparator : PriceComparator.values()){
            switch (priceComparator){
                case CHEAPEST: this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.goodPrice));
                break;
                case NO_PRICE:this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.noPrice));
                    break;
                case EXPANSIVE:this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.badPrice));
                    break;
                default:this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.normalPrice));
                    break;
            }
        }
        for (CategorieIngredient categorieIngredient : CategorieIngredient.values()){
            switch (categorieIngredient){
                case PAIN:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_pain));
                    break;
                case BOISSON:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_boisson));
                    break;
                case VIANDE:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_viande));
                    break;
                case LEGUMES:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_legumes));
                    break;
                case BONBON:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_bonbon));
                    break;
                case FRUITS:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_fruit));
                    break;
                case GATEAU:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_gateau));
                    break;
                case POISSON:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_poisson));
                    break;
                case PRODUIT_LAITIER:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_laitier));
                    break;
                case SURGELE:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_surgeler));
                    break;
                case CONSERVE:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_conserve));
                    break;
                case FECULENT:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_feculent));
                    break;
                case CONDIMENT:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_condiment));
                    break;
                case HYGIENE:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_higiene));
                    break;
                case ANIMAUX:this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.ingredientCat_animaux));
                    break;
                default: this.ingredientCategorieColor.put(categorieIngredient,activity.getResources().getColor(R.color.default_categorie));
                    break;
            }
        }
    }

    private int getPriceColorInst(PriceComparator priceComparator){
        return this.priceColor.get(priceComparator);
    }

    private int getIngredientColorInst(CategorieIngredient categorieIngredient){
        return this.ingredientCategorieColor.get(categorieIngredient);
    }
}
