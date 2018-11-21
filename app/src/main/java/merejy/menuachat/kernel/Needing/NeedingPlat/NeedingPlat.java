package merejy.menuachat.kernel.Needing.NeedingPlat;

import java.io.Serializable;
import java.util.HashMap;

import merejy.menuachat.database.DataEnum.CategoriePlats;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.kernel.Needing.NeedingState;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class NeedingPlat {

    private Plat plat;
    private Plat accompagnement;
    private HashMap<String,NeedingIngredient> needingIngredients;

    public  NeedingPlat(Plat plat , Plat accompagnement , HashMap<String,NeedingState> isTake){
        this.plat = plat;
        this.accompagnement = accompagnement;
        this.needingIngredients = new HashMap<>();
        this.createNeedingIngredientList(plat, isTake);
        if(accompagnement != null){
            this.createNeedingIngredientList(accompagnement, isTake);
        }
    }


    public NeedingPlat(Plat p){
        this(p,null,new HashMap<String, NeedingState>());
    }


    public NeedingPlat(Plat p, Plat accompagnement){
        this(p,accompagnement,new HashMap<String, NeedingState>());
    }

    private void createNeedingIngredientList(Plat plat , HashMap<String,NeedingState> isTake){
        for(Ingredient i : plat.getIngredients()){
            if(needingIngredients.containsKey(i.getNom())){
                needingIngredients.get(i.getNom()).addQuantite(1);
            }
            else{
                NeedingIngredient ni = new NeedingIngredient(i);
                if(isTake.containsKey(ni.getNom())){
                   NeedingState.configNeeding(ni,isTake.get(ni.getNom()));
                }
                needingIngredients.put(i.getNom(),ni);
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
