package merejy.menuachat.database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Plat implements Serializable{

    private String nom;
    private List<Ingredient> ingredients;
    private CategoriePlats categories;

    public Plat(String nom , CategoriePlats cat , List<Ingredient> i){
        this.nom = nom;
        this.categories = cat;
        this.ingredients = i;
    }

    public String getNom() {
        return nom;
    }

    public Double getPrix(Magasin mag){
        double p = 0.0;
        for(Ingredient i : ingredients){
            p += i.getPrix(mag);
        }
        return new Double(p);
    }

    public  List<Ingredient> getIngredients() {
        return ingredients;
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
}
