package merejy.menuachat.database;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.database.DataEnum.CategorieMateriaux;

public class Materiaux {

    private String nom;
    private CategorieMateriaux categorieMateriaux;
    private HashMap<Integer,List<Ingredient>> ingredientsAssocie;


    public Materiaux (String nom , CategorieMateriaux categorieMateriaux){
        this.nom = nom;
        this.categorieMateriaux = categorieMateriaux;
        this.ingredientsAssocie = new HashMap<>();
    }


    public String getNom() {
        return nom;
    }

    public CategorieMateriaux getCategorieMateriaux() {
        return categorieMateriaux;
    }

    public void addIngredient(Ingredient ingredient) throws ItemAlreadyExist {
        if(ingredientsAssocie.containsKey(ingredient.getQuantite())){
            List<Ingredient> list = ingredientsAssocie.get(ingredient.getQuantite());
            if(list.contains(ingredient)){
                throw new ItemAlreadyExist();
            }
            list.add(ingredient);
        }
        else{
            List<Ingredient> ingredients = new ArrayList<Ingredient>();
            ingredients.add(ingredient);
            ingredientsAssocie.put(ingredient.getQuantite(),ingredients);
        }

    }

    public HashMap<Integer,List<Ingredient> > getIngredientsAssocie(){
        return  ingredientsAssocie;
    }

    @Override
    public String toString() {
        return nom;
    }

    //static

    final static  private String saveTag_Nom = "Nom";
    final static  private String saveTag_Categorie = "Categorie";
    final static private  String saveTag_IngredientAssocie = "Ingredient";
    final static private  String saveTag_IngredientAssocie_Nom = "Nom";

    public static void save (Materiaux i , JsonWriter writer){ //sauvegarde un ingredient
        try {
            writer.beginObject();               //debut ingredient
            writer.name(saveTag_Nom).value(i.nom);
            writer.name(saveTag_Categorie).value(i.categorieMateriaux.toString());
            writer.name(saveTag_IngredientAssocie).beginArray();
            for(int quantite : i.ingredientsAssocie.keySet()){
                for(Ingredient ingredient : i.ingredientsAssocie.get(quantite)){
                    writer.beginObject();
                    writer.name(saveTag_IngredientAssocie).value(ingredient.getNom());
                    writer.endObject();
                }
            }
            writer.endArray();
            writer.endObject(); //fin ingredient
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static void  load (JsonReader reader , Database database){    //charge un ingredient
        try {
            reader.beginObject();
            String nom = null;
            CategorieMateriaux categorie = null;
            HashMap<Magasin,Double> prix = new HashMap<>();
            List<Ingredient> ingredients = new ArrayList<>();
            while (reader.hasNext()){
                String name = reader.nextName();
                switch (name) {
                    case saveTag_Nom:
                        nom = reader.nextString();
                        break;
                    case saveTag_Categorie:
                        categorie = CategorieMateriaux.valueOf(reader.nextString());
                        break;
                    case saveTag_IngredientAssocie:

                        reader.beginArray();
                        while (reader.hasNext()){
                            reader.beginObject();
                            while (reader.hasNext()){
                                switch (reader.nextName()){
                                    case saveTag_IngredientAssocie:
                                        Ingredient ingredient = database.getIngredient(reader.nextString());
                                        if(ingredient != null){
                                            ingredients.add(ingredient);
                                        }
                                        break;
                                }
                            }
                            reader.endObject();
                        }
                        reader.endArray();

                }
            }
            reader.endObject(); //fin ingredient
            if(nom != null && categorie != null){
                //creation et enregistrement des materiaux
                try {
                    database.addMateriaux(nom,categorie);
                    Materiaux materiaux = database.getMateriaux(nom);
                    for(Ingredient ingredient : ingredients){
                        materiaux.addIngredient(ingredient);
                    }
                } catch (ItemAlreadyExist itemAlreadyExist) {
                    itemAlreadyExist.printStackTrace();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
