package merejy.menuachat.database;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.PriceComparator;

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
            return Double.NaN;
        }
        return this.prix.get(mag);
    }

    public PriceComparator isCostLess(Magasin mag) {    //permet de savoir si il est le moins cher ici
        double min = Double.MAX_VALUE;
        boolean best = true;                    //permet de savoir si c'est le seul
        for (Double magPrix : prix.values()) {
            if (min >= magPrix) {
                if(min > magPrix){
                    best = true;
                    min = magPrix;
                }
                else {
                    best = false;
                }
            }
        }
        PriceComparator result = PriceComparator.NO_PRICE;
        if(prix.containsKey(mag)){
            if(prix.get(mag) <= min){
                if(best){
                    result = PriceComparator.CHEAPEST;
                }
                else{
                    result = PriceComparator.CHEAPER;
                }
            }
            else{
                result = PriceComparator.EXPANSIVE;
            }
        }
        return result;
    }

    public  void modifyIngredient(Ingredient nouveau){
        this.nom = nouveau.nom;
        this.categorie = nouveau.categorie;
    }

    //static

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
                switch (name) {
                    case "Nom":
                        nom = reader.nextString();
                        break;
                    case "Categorie":
                        categorie = CategorieIngredient.valueOf(reader.nextString());
                        break;
                    case "Prix":
                        reader.beginArray();   //debut prix

                        while (reader.hasNext()) {
                            reader.beginObject();           //debut magasin

                            String nomMag = null;
                            String localisation = null;
                            Double prixValue = null;
                            while (reader.hasNext()) {
                                String name2 = reader.nextName();
                                switch (name2) {
                                    case "nom":
                                        nomMag = reader.nextString();
                                        break;
                                    case "localisation":
                                        localisation = reader.nextString();
                                        break;
                                    case "prix":
                                        prixValue = reader.nextDouble();
                                        break;
                                }
                            }
                            reader.endObject();             //fin magasin
                            if (nomMag != null && localisation != null && prixValue != null) {
                                prix.put(new Magasin(nomMag, localisation), prixValue);
                            }

                        }
                        reader.endArray(); //fin prix

                        break;
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
