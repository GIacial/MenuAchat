package merejy.menuachat.kernel;

import android.app.Activity;
import android.graphics.Color;
import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    static public void setColor(CategorieIngredient categorieIngredient , int color){
        if(colorManager != null){
            colorManager.setColorInst(categorieIngredient,color);
        }
    }

    static public void setColor(PriceComparator priceComparator , int color){
        if(colorManager != null){
            colorManager.setColorInst(priceComparator,color);
        }
    }

    static public int getTextColor(CategorieIngredient categorieIngredient){
        int color = 0;
        if( colorManager != null){
            color = colorManager.getTextColorForCategorieColorInst(categorieIngredient);
        }
        return color;
    }

    //save
    private final  static String SAVETAG_INGREDIENT = "Ingredient";
    private final  static String SAVETAG_PRIX = "Prix";
    private final  static String SAVETAG_CATEGORIE_INGREDIENT = "CategorieIngredient";
    private final  static String SAVETAG_CATEGORIE_PRIX = "CategoriePrix";
    private final  static String SAVETAG_COLOR = "Color";


    static public void load (Activity activity){
        if(colorManager == null){
            try {

                colorManager = new ColorManager(activity);
                JsonReader reader = new JsonReader(new FileReader(new File(Environment.getExternalStorageDirectory()+File.separator+ Database.dossierSave,fileSave)));
                reader.beginObject();   //debut database

                while (reader.hasNext()){
                    String name = reader.nextName();
                    switch (name){
                        case SAVETAG_INGREDIENT:
                            reader.beginArray();
                            while (reader.hasNext()){
                                reader.beginObject();
                                CategorieIngredient catIngredient = null;
                                Integer color = null;
                                while (reader.hasNext()){
                                    String name2 = reader.nextName();
                                    switch (name2){
                                        case SAVETAG_CATEGORIE_INGREDIENT:
                                            catIngredient = CategorieIngredient.valueOf(reader.nextString());
                                            break;
                                        case SAVETAG_COLOR:
                                            color = reader.nextInt();
                                            break;
                                        default:System.err.println(name2 + "n'est pas reconnu dans "+ name );
                                    }
                                }
                                if(catIngredient != null && color != null){
                                    colorManager.setColorInst(catIngredient,color);
                                }
                                reader.endObject();
                            }
                            reader.endArray();
                            break;
                        case SAVETAG_PRIX:
                            reader.beginArray();
                            while (reader.hasNext()){
                                reader.beginObject();
                                PriceComparator priceComparator = null;
                                Integer color = null;
                                while (reader.hasNext()){
                                    String name2 = reader.nextName();
                                    switch (name2){
                                        case SAVETAG_CATEGORIE_PRIX:
                                            priceComparator = PriceComparator.valueOf(reader.nextString());
                                            break;
                                        case SAVETAG_COLOR:
                                            color = reader.nextInt();
                                            break;
                                        default:System.err.println(name2 + "n'est pas reconnu dans "+ name );
                                    }
                                }
                                if(priceComparator != null && color != null){
                                    colorManager.setColorInst(priceComparator,color);
                                }
                                reader.endObject();
                            }
                            reader.endArray();
                            break;
                        default:System.err.println(name + "n'est pas reconnu");

                    }
                }

                reader.endObject();
                reader.close();
            } catch (IOException e) {
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
                JsonWriter writer = new JsonWriter(new FileWriter(new File(Environment.getExternalStorageDirectory()+File.separator+ Database.dossierSave,fileSave)));
                writer.beginObject();   //debut database

                //save ingredient color
                writer.name(SAVETAG_INGREDIENT).beginArray();
                for (CategorieIngredient cat : CategorieIngredient.values()){
                    writer.beginObject();   //save la color pour la cat
                    writer.name(SAVETAG_CATEGORIE_INGREDIENT).value(cat.toString());
                    writer.name(SAVETAG_COLOR).value(colorManager.getIngredientColorInst(cat));
                    writer.endObject();
                }
                writer.endArray();
                //save price color
                writer.name(SAVETAG_PRIX).beginArray();
                for (PriceComparator comparator : PriceComparator.values()){
                    writer.beginObject();
                    writer.name(SAVETAG_CATEGORIE_PRIX).value(comparator.toString());
                    writer.name(SAVETAG_COLOR).value(colorManager.getPriceColorInst(comparator));
                    writer.endObject();
                }
                writer.endArray();

                writer.endObject();
                writer.close();
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
        if(priceColor.containsKey(priceComparator)){

            return this.priceColor.get(priceComparator);
        }
        return 0;
    }

    private int getIngredientColorInst(CategorieIngredient categorieIngredient){
        if(ingredientCategorieColor.containsKey(categorieIngredient)) {
            return this.ingredientCategorieColor.get(categorieIngredient);
        }
        return 0;
    }

    private void setColorInst(CategorieIngredient categorieIngredient , int color){
        this.ingredientCategorieColor.put(categorieIngredient,color);
    }

    private void setColorInst(PriceComparator priceComparator , int color){
        this.priceColor.put(priceComparator,color);
    }

    private int getTextColorForCategorieColorInst(CategorieIngredient categorieIngredient){
        int background = getIngredientColorInst(categorieIngredient);
        int gray = (Color.red(background) + Color.green(background) + Color.green(background))/3;
        int res = 255 - gray;
        if (res >= 97 && res <= 157){//gris du milieu on mets du noir
            res = 0;
        }
        return Color.rgb(res,res,res) ;
    }
}
