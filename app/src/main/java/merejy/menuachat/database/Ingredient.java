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
    private int quantite;                               //quantite en gramme

    public Ingredient(String nom , CategorieIngredient cat ,Materiaux materiauxAssocie , int quantite) throws ItemAlreadyExist {                    //constructeur
        this(nom,cat,quantite);
        materiauxAssocie.addIngredient(this);
    }

    public Ingredient (String nom , CategorieIngredient cat , int quantite){
        this.nom = nom;
        this.categorie = cat;
        this.prix = new HashMap<>();
        this.quantite = quantite;
    }

    public String getNom() {//recup le nom de l'ingredient
        return nom;
    }

    public int getQuantite() {
        return quantite;
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

    //static

    final static  private String saveTag_Nom = "Nom";
    final static  private String saveTag_Categorie = "Categorie";
    final static  private String saveTag_Prix = "Prix";
    final static  private String saveTag_Quantite = "quantite";
    final static  private String saveTag_MagasinNom = "nom";
    final static  private String saveTag_MagasinLocalisation = "localisation";
    final static  private String saveTag_MagasinPrix = "prix";

    public static void save (Ingredient i , JsonWriter writer){ //sauvegarde un ingredient
        try {
            writer.beginObject();               //debut ingredient
            writer.name(saveTag_Nom).value(i.nom);
            writer.name(saveTag_Categorie).value(i.categorie.toString());
            writer.name(saveTag_Quantite).value(i.quantite);
            writer.name(saveTag_Prix).beginArray();   //debut prix
            for(Magasin m : i.prix.keySet()){
                writer.beginObject();           //debut magasin
                writer.name(saveTag_MagasinNom).value(m.getNom());
                writer.name(saveTag_MagasinLocalisation).value(m.getLocalisation());
                writer.name(saveTag_MagasinPrix).value(i.prix.get(m));
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
            int quantite = 0;
            while (reader.hasNext()){
                String name = reader.nextName();
                switch (name) {
                    case saveTag_Nom:
                        nom = reader.nextString();
                        break;
                    case saveTag_Quantite:
                        quantite = reader.nextInt();
                        break;
                    case saveTag_Categorie:
                        categorie = CategorieIngredient.valueOf(reader.nextString());
                        break;
                    case saveTag_Prix:
                        reader.beginArray();   //debut prix

                        while (reader.hasNext()) {
                            reader.beginObject();           //debut magasin

                            String nomMag = null;
                            String localisation = null;
                            Double prixValue = null;
                            while (reader.hasNext()) {
                                String name2 = reader.nextName();
                                switch (name2) {
                                    case saveTag_MagasinNom:
                                        nomMag = reader.nextString();
                                        break;
                                    case saveTag_MagasinLocalisation:
                                        localisation = reader.nextString();
                                        break;
                                    case saveTag_MagasinPrix:
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
            if(nom != null && categorie != null && quantite != 0){
                try {
                    database.addIngedient(nom,categorie,quantite);
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
