package merejy.menuachat.kernel.Needing.NeedingPlat;

import java.io.Serializable;
import java.util.HashMap;

import merejy.menuachat.database.DataEnum.CategoriePlats;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing.NeedingIngredient.NeedingIngredient;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class NeedingPlat {

    private Plat plat;
    private Plat accompagnement;
    private HashMap<String,NeedingIngredient> needingIngredients;

    public  NeedingPlat(Plat plat , Plat accompagnement){
        this.plat = plat;
        this.accompagnement = accompagnement;
        this.needingIngredients = new HashMap<>();
        this.createNeedingIngredientList(plat);
        if(accompagnement != null){
            this.createNeedingIngredientList(accompagnement);
        }
    }


    public NeedingPlat(Plat p){
        this(p,null);
    }

    private void createNeedingIngredientList(Plat plat){
        for(Ingredient i : plat.getIngredients()){
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
        String nomPlat = this.plat.getNom();
        if(accompagnement != null){
            nomPlat += " + " + accompagnement.getNom();
        }
        return nomPlat;
    }

    public  double getPrix(Magasin m){
        double prix = this.plat.getPrix(m);
        if(accompagnement != null){
            prix += accompagnement.getPrix(m);
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

    public boolean isAccomppagner(){
        return this.plat.isAccompagner();
    }

    public  String getPrincipalName(){
        return  plat.getNom();
    }

    public  String getAccompagnementName(){
        if(accompagnement != null){
            return accompagnement.getNom();
        }
        return "";
    }

}
