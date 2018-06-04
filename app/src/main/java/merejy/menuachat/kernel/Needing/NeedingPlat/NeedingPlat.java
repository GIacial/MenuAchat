package merejy.menuachat.kernel.Needing.NeedingPlat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.database.DataEnum.CategoriePlats;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.database.Materiaux;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.kernel.Needing.NeedingList;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class NeedingPlat implements Serializable{

    private Plat plat;
    private HashMap<String,NeedingIngredient> needingIngredients;


    public NeedingPlat(Plat p){
        this.plat = p;
        this.needingIngredients = new HashMap<>();
        calculateNeedingIngredient();
    }

    public void calculateNeedingIngredient(){
        HashMap<Materiaux,Integer> liste = plat.getIngredients();
        for(Materiaux m : liste.keySet()){
            addNeedingIngredient(findBestIngredient(m,liste.get(m)));
        }
    }

    private Ingredient findBestIngredient(Materiaux m , int quantite){
        HashMap<Integer,List<Ingredient>> allIngredient = m.getIngredientsAssocie();
        Ingredient retour = null;
        if(allIngredient.containsKey(quantite)){
            retour = findBestPrice(allIngredient.get(quantite));
        }
        else{
            int min = 0;
            for(int quantieM : allIngredient.keySet()){
                if(quantieM > quantite){
                    if(quantite - quantieM < quantite - min){
                        min = quantieM;
                    }
                }
            }
            if( min != 0){
                retour = findBestPrice(allIngredient.get(min));
            }
        }
        return  retour;
    }

    private  Ingredient findBestPrice (List<Ingredient> ingredients){
        Ingredient ingredient = null;
        Magasin magasin = NeedingList.getNeeding().getCurrentMag();
        for(Ingredient i : ingredients){
            if(ingredient == null){
                ingredient = i;
            }
            else{
                if(Double.isNaN(ingredient.getPrix(magasin)) && !Double.isNaN(i.getPrix(magasin))){
                    ingredient = i;
                }
                else{
                    if(ingredient.getPrix(magasin) > i.getPrix(magasin)){
                        ingredient = i;
                    }
                }
            }
        }
        return  ingredient;
    }

    private void addNeedingIngredient(Ingredient i){
        if(needingIngredients.containsKey(i.getNom())){
            needingIngredients.get(i.getNom()).addQuantite(1);
        }
        else{
            needingIngredients.put(i.getNom(),new NeedingIngredient(i));
        }
    }

    public HashMap<String,NeedingIngredient> getNeedingIngredients(){
        return needingIngredients;
    }

    public boolean isTake() {
        boolean take = true;
        for(NeedingIngredient i : needingIngredients.values()){
            take = take && (i.isTake() || i.isAtHome());
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
        double prix = 0;
        for (NeedingIngredient needingIngredient : this.needingIngredients.values()){
            prix += needingIngredient.getPrix(m);
        }
        return prix;
    }

    public double getCurrentIngredientTakePrice(Magasin m){
        double res = 0;
        for(NeedingIngredient i : needingIngredients.values()){
            if(i.isTake()){
                double prix = i.getPrix(m);
                if(!Double.isNaN(prix)){
                    res += prix;
                }
            }
        }
        return res;
    }

}
