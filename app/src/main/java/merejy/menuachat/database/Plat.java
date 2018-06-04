package merejy.menuachat.database;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.database.DataEnum.CategoriePlats;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Plat implements Serializable{

    private String nom;
    private HashMap<Materiaux,Integer> materiaux;
    private CategoriePlats categories;

    public Plat(String nom , CategoriePlats cat , HashMap<Materiaux,Integer> i,int nombrePersonne){
        this.nom = nom;
        this.categories = cat;
        this.materiaux = new HashMap<>();
        for(Materiaux materiaux : i.keySet()){
            this.materiaux.put(materiaux, (int) Math.ceil(i.get(materiaux)/(double)nombrePersonne));
        }
    }

    public String getNom() {
        return nom;
    }


    public  HashMap<Materiaux,Integer> getIngredients() {
        return materiaux;
    }

    public CategoriePlats getCategories() {
        return categories;
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


    //static
    static  final private String saveTag_nom = "Nom";
    static  final private String saveTag_categorie = "Categorie";
    static  final private String saveTag_materiaux = "Ingredients";
    static  final private String saveTag_materiaux_nom = "Nom";
    static  final private String saveTag_materiaux_quantite = "Grammage";

    public static void save (Plat i , JsonWriter writer){
        try {
            writer.beginObject();   //debut plat
            writer.name(saveTag_nom).value(i.nom);
            writer.name(saveTag_categorie).value(i.categories.toString());
            writer.name(saveTag_materiaux).beginArray();    //debut ingredient
            for (Materiaux ingredient : i.materiaux.keySet()){
                writer.beginObject();
                writer.name(saveTag_materiaux_nom).value(ingredient.getNom());
                writer.name(saveTag_materiaux_quantite).value(i.materiaux.get(ingredient));
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
            HashMap<Materiaux,Integer> ingredients = new HashMap<>();
            while (reader.hasNext()){
                String name = reader.nextName();
                switch (name) {
                    case saveTag_nom:
                        nom = reader.nextString();
                        break;
                    case saveTag_categorie:
                        categoriePlats = CategoriePlats.valueOf(reader.nextString());
                        break;
                    case saveTag_materiaux:
                        reader.beginArray();    //debut ingredient

                        while (reader.hasNext()) {
                            reader.beginObject();
                            Materiaux ingredient = null;
                            int quantite = 0;
                            while (reader.hasNext()) {
                                String name2 = reader.nextName();
                                if (name2.equals(saveTag_materiaux_nom)) {
                                    ingredient = database.getMateriaux(reader.nextString());

                                }
                                else if(name2.equals(saveTag_materiaux_quantite)){
                                    quantite = reader.nextInt();
                                }
                            }
                            if(ingredient != null && quantite != 0){
                                ingredients.put(ingredient,quantite);
                            }
                            reader.endObject();
                        }
                        reader.endArray();       //fin ingredient

                        if (nom != null && categoriePlats != null && ingredients.size() >= 1) {
                            try {
                                database.addPlat(nom, categoriePlats, ingredients,1);
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
