package merejy.menuachat.database;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.database.DataEnum.CategoriePlats;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Plat implements Serializable{

    private String nom;
    private List<Ingredient> ingredients;
    private CategoriePlats categories;
    private boolean accompagner;

    public Plat(String nom , CategoriePlats cat , List<Ingredient> i , boolean accompagner){
        this.nom = nom;
        this.categories = cat;
        this.ingredients = i;
        this.accompagner = accompagner;
    }

    public String getNom() {
        return nom;
    }

    public Double getPrix(Magasin mag){
        double p = Double.NaN;
        if(mag != null){
            p = 0.0;
            for(Ingredient i : ingredients){
                p += i.getPrix(mag);
            }
        }

        return p;
    }

    public  List<Ingredient> getIngredients() {
        return ingredients;
    }

    public CategoriePlats getCategories() {
        return categories;
    }

    public boolean isAccompagner(){
        return  accompagner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Plat)) return false;

        Plat plat = (Plat) o;

        return nom.equals(plat.nom);
    }

    @Override
    public int hashCode() {
        return nom.hashCode();
    }

    public void modifyPlat(Plat nouveau){
        this.ingredients = nouveau.ingredients;
        this.categories = nouveau.categories;
        this.accompagner = nouveau.accompagner;
        this.nom = nouveau.nom;
    }




    //static

    public static void save (Plat i , JsonWriter writer){
        try {
            writer.beginObject();   //debut plat
            writer.name("Nom").value(i.nom);
            writer.name("Categorie").value(i.categories.toString());
            writer.name("Accompagner").value(i.accompagner);
            writer.name("Ingredients").beginArray();    //debut ingredient
            for (Ingredient ingredient : i.ingredients){
                writer.beginObject();
                writer.name("Nom").value(ingredient.getNom());
                writer.endObject();
            }
            writer.endArray();       //fin ingredient
            writer.endObject();     //fin plats
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public  static void  load (JsonReader reader , Database database){
        try {
            reader.beginObject();   //debut plat
            String nom = null;
            CategoriePlats categoriePlats = null;
            boolean accompagner = false;
            List<Ingredient> ingredients = new ArrayList<>();
            while (reader.hasNext()){
                String name = reader.nextName();
                switch (name) {
                    case "Nom":
                        nom = reader.nextString();
                        break;
                    case "Categorie":
                        categoriePlats = CategoriePlats.valueOf(reader.nextString());
                        break;
                    case "Accompagner":
                        accompagner = reader.nextBoolean();
                        break;
                    case "Ingredients":
                        reader.beginArray();    //debut ingredient

                        while (reader.hasNext()) {
                            reader.beginObject();
                            while (reader.hasNext()) {
                                String name2 = reader.nextName();
                                if (name2.equals("Nom")) {
                                    Ingredient ingredient = database.getIngredient(reader.nextString());
                                    if (ingredient != null) {
                                        ingredients.add(ingredient);
                                    }
                                }
                            }
                            reader.endObject();
                        }
                        reader.endArray();       //fin ingredient

                        if (nom != null && categoriePlats != null && ingredients.size() >= 1) {
                            try {
                                database.addPlat(nom, categoriePlats, ingredients,accompagner);
                            } catch (ItemAlreadyExist itemAlreadyExist) {
                                itemAlreadyExist.printStackTrace();
                            }
                        }
                        break;
                }
            }

            reader.endObject();     //fin plats
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
