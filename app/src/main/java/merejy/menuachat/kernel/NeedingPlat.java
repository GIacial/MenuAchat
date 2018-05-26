package merejy.menuachat.kernel;

import java.io.Serializable;
import java.util.HashMap;

import merejy.menuachat.database.CategoriePlats;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.NeedingIngredient.NeedingIngredient;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class NeedingPlat implements Serializable{

    private Plat plat;
    private HashMap<String,NeedingIngredient> needingIngredients;

    public NeedingPlat(Plat p){
        this.plat = p;
        this.needingIngredients = new HashMap<>();
        for(Ingredient i : p.getIngredients()){
            if(needingIngredients.containsKey(i.getNom())){
                needingIngredients.get(i.getNom()).addQuantite(1);
            }
            else{
                needingIngredients.put(i.getNom(),new NeedingIngredient(i));
            }

        }
    }

    public HashMap<String,NeedingIngredient> getNeedingIngredients(){
        return needingIngredients;
    }

    public boolean isTake() {
        boolean take = true;
        for(NeedingIngredient i : needingIngredients.values()){
            take = take && i.isTake();
        }
        return take;
    }

    public CategoriePlats getCategories() {
        return plat.getCategories();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NeedingPlat)) return false;

        NeedingPlat that = (NeedingPlat) o;

        return plat.equals(that.plat);
    }

    @Override
    public int hashCode() {
        return plat.hashCode();
    }

    public String getNom(){
        return this.plat.getNom();
    }

    public  double getPrix(Magasin m){
        return this.plat.getPrix(m);
    }

    public double getCurrentIngredientTakePrice(Magasin m){
        double res = 0;
        for(NeedingIngredient i : needingIngredients.values()){
            if(i.isTake()){
                res += i.getPrix(m);
            }
        }
        return res;
    }

}
