package merejy.menuachat.database;

import android.util.JsonReader;
import android.util.JsonWriter;

import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import merejy.menuachat.Exception.ItemAlreadyExist;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Ingredient  implements Serializable {

    private HashMap<Magasin,Double> prix;               //les prix selon les magasin
    private String nom;                                 //le nom de l'ingredient
    private CategorieIngredient categorie;              //la categorie de l'ingredient

    public Ingredient(String nom , CategorieIngredient cat){                    //constructeur
        this.nom = nom;
        this.categorie = cat;
        this.prix = new HashMap<>();
    }

    public String getNom() {//recup le nom de l'ingredient
        return nom;
    }

    public CategorieIngredient getCategorie() {
     //recup categorie de l'ingredient
        return categorie;
    }

    public void addPrix(Double prix , Magasin mag){ //mets un prix pour ce magasin
        this.prix.put(mag,prix);
    }

    public Double getPrix(Magasin mag){ //recup le prix pour un magasin
        if(!this.prix.containsKey(mag)){
            return new Double(Double.NaN);
        }
        return this.prix.get(mag);
    }

    public boolean isCostLess(Magasin mag) {    //permet de savoir si il est le moins cher ici
        double min = Double.MAX_VALUE;
        for (Double magPrix : prix.values()) {
            if (min > magPrix) {
                min = magPrix;
            }
        }
        return prix.containsKey(mag) && prix.get(mag) <= min;
    }

    public static void save (Ingredient i , JsonWriter writer){ //sauvegarde un ingredient
        try {
            writer.beginObject();               //debut ingredient
            writer.name("Nom").value(i.nom);
            writer.name("Categorie").value(i.categorie.toString());
            writer.name("Prix").beginArray();   //debut prix
            for(Magasin m : i.prix.keySet()){
                writer.beginObject();           //debut magasin
                writer.name("nom").value(m.getNom());
                writer.name("localisation").value(m.getLocalisation());
                writer.name("prix").value(i.prix.get(m));
                writer.endObject();             //fin magasin

            }
            writer.endArray(); //fin prix
            writer.endObject(); //fin ingredient
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static void  load (JsonReader reader , Database database){    //charge un ingredient
        try {
            reader.beginObject();
            String nom = null;
            CategorieIngredient categorie = null;
            HashMap<Magasin,Double> prix = new HashMap<>();
            while (reader.hasNext()){
                String name = reader.nextName();
                if(name.equals("Nom")){
                    nom = reader.nextString();
                }
                else if(name.equals("Categorie")){
                    categorie = CategorieIngredient.valueOf(reader.nextString());
                }
                else if (name.equals("Prix")){
                    reader.beginArray();   //debut prix
                    while (reader.hasNext()){
                        reader.beginObject();           //debut magasin

                        String nomMag = null;
                        String localisation = null;
                        Double prixValue = null;
                        while (reader.hasNext()){
                            String name2 = reader.nextName();
                            if(name2.equals("nom")){
                                nomMag = reader.nextString();
                            }
                            else if (name2.equals("localisation")){
                                localisation = reader.nextString();
                            }
                            else if(name2.equals("prix")){
                                prixValue = reader.nextDouble();
                            }
                        }
                        reader.endObject();             //fin magasin
                        if(nomMag != null && localisation != null && prixValue != null){
                            prix.put(new Magasin(nomMag,localisation),prixValue);
                        }

                    }
                    reader.endArray(); //fin prix
                }
            }
            reader.endObject(); //fin ingredient
            if(nom != null && categorie != null){
                try {
                    database.addIngedient(nom,categorie);
                    Ingredient ingredient = database.getIngredient(nom);
                    for(Magasin m : prix.keySet()){
                        Magasin magasin = database.getMagasin(m.getNom(),m.getLocalisation());
                        if(magasin != null){
                            ingredient.addPrix(prix.get(m),magasin);
                        }
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
