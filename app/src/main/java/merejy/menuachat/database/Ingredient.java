package merejy.menuachat.database;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Ingredient  implements Serializable {

    private HashMap<Magasin,Double> prix;
    private String nom;
    private CategorieIngredient categorie;

    public Ingredient(String nom , CategorieIngredient cat){
        this.nom = nom;
        this.categorie = cat;
        this.prix = new HashMap<>();
    }

    public String getNom() {
        return nom;
    }

    public CategorieIngredient getCategorie() {
        return categorie;
    }

    public void addPrix(Double prix , Magasin mag){
        this.prix.put(mag,prix);
    }

    public Double getPrix(Magasin mag){
        if(!this.prix.containsKey(mag)){
            return new Double(Double.NaN);
        }
        return this.prix.get(mag);
    }

    public boolean isCostLess(Magasin mag){
        double min = Double.MAX_VALUE;
        for(Double magPrix : prix.values()){
            if(min > magPrix){
                min = magPrix;
            }
        }
        return  prix.containsKey(mag) && prix.get(mag)<= min;
    }
}
