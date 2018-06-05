package merejy.menuachat.kernel.Needing.NeedingPlat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.Exception.NoIngredientAvalilable;
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
    private int nbPersonne;


    public NeedingPlat(Plat p , int nbPersonne) throws NoIngredientAvalilable {
        this.plat = p;
        this.needingIngredients = new HashMap<>();
        this.nbPersonne = nbPersonne;
        calculateNeedingIngredient();
    }

    public void calculateNeedingIngredient() throws NoIngredientAvalilable {
        HashMap<Materiaux,Integer> liste = plat.getIngredients();
        needingIngredients.clear();
        for(Materiaux m : liste.keySet()){
            NeedingIngredient ingredient = findBestNumberIngredient(m,liste.get(m)*nbPersonne);
            addNeedingIngredient(ingredient);
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

    public int getNbPersonne() {
        return nbPersonne;
    }


    //----------------------------------------------------------------------------------------------
    //----------------------------------Private-----------------------------------------------------
    //----------------------------------------------------------------------------------------------

    private Ingredient findBestIngredient(Materiaux m , int quantite){
        HashMap<Integer,List<Ingredient>> allIngredient = m.getIngredientsAssocie();
        Ingredient retour = null;

        if(allIngredient.containsKey(quantite)){
            retour = findBestPrice(allIngredient.get(quantite));

        }
        else{
            int min = Integer.MIN_VALUE;
            for(int quantieM : allIngredient.keySet()){
                if(quantieM > quantite){
                    if(quantieM - quantite < min -quantite){
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

    private NeedingIngredient findBestNumberIngredient(Materiaux m , int quantite){
        Ingredient onlyOne = findBestIngredient(m,quantite);
        System.err.println(quantite);
        NeedingIngredient retour;
        if(onlyOne != null){
            retour = new NeedingIngredient(onlyOne);
            NeedingIngredient plusieur = findIngredientNumber(m,quantite);
            Magasin mag = NeedingList.getNeeding().getCurrentMag();
            if(plusieur.getGrammage() <= retour.getGrammage()){
                if(Double.isNaN(plusieur.getPrix(mag)) || Double.isNaN(retour.getPrix(mag))){
                    retour = plusieur;
                }
                else{
                    if(plusieur.getPrix(mag) < retour.getPrix(mag)){
                        retour = plusieur;
                    }
                }
            }
        }
        else{
            retour = findIngredientNumber(m,quantite);
        }
        return retour;
    }

    private NeedingIngredient findIngredientNumber(Materiaux m , int quantite){
        HashMap<Integer,List<Ingredient>> allIngredient = m.getIngredientsAssocie();
        NeedingIngredient retour = null;
        for (int gramme : allIngredient.keySet()){
            double nbFois = ((double)quantite) / (double) gramme;
            nbFois = Math.ceil(nbFois);
                if (retour == null){

                    retour = new NeedingIngredient(findBestPrice(allIngredient.get(gramme)));
                    retour.addQuantite((int) (nbFois-1));
                }
                else{


                    if(nbFois*gramme <= retour.getGrammage()){
                        System.err.println("Coucou");
                        NeedingIngredient plusieur = new NeedingIngredient(findBestPrice(allIngredient.get(gramme)));
                        plusieur.addQuantite((int) (nbFois-1));

                        Magasin mag = NeedingList.getNeeding().getCurrentMag();
                        if(Double.isNaN(plusieur.getPrix(mag)) || Double.isNaN(retour.getPrix(mag))){
                            retour = plusieur;
                        }
                        else{

                            if(plusieur.getPrix(mag) < retour.getPrix(mag)){

                                retour = plusieur;
                            }
                        }
                    }

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

    private void addNeedingIngredient(NeedingIngredient i) throws NoIngredientAvalilable {
        if(i == null){
            throw new NoIngredientAvalilable();
        }
        if(needingIngredients.containsKey(i.getNom())){
            needingIngredients.get(i.getNom()).addQuantite(1);
        }
        else{
            needingIngredients.put(i.getNom(),i);
        }
    }
}
