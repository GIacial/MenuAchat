package merejy.menuachat.kernel.Needing.NeedingIngredient;

import java.io.Serializable;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class NeedingIngredient implements Serializable,InterfaceNeedingIngredient {

    private Ingredient i;
    private boolean take;
    private int quantite;
    private boolean atHome;

    public NeedingIngredient (Ingredient i){
        this.i = i;
        this.take = false;
        this.quantite = 1;
        this.atHome = false;
    }

    public boolean isTake() {
        return take;
    }

    public void setTake(boolean take) {
        this.take = take;
    }

    @Override
    public boolean isAtHome() {
        return atHome;
    }

    @Override
    public void setAtHome(boolean atHome) {
            this.atHome = atHome;
    }

    public String getNom() {
        return i.getNom();
    }

    public CategorieIngredient getCategorie() {
        return i.getCategorie();
    }

    public void addPrix(Double prix , Magasin mag){
       i.addPrix(prix,mag);
    }

    public Double getPrix(Magasin mag){
       return i.getPrix(mag)*quantite;
    }

    public void addQuantite(int i){
        quantite += i;
    }

    public int getQuantite(){
        return  quantite;
    }

    public PriceComparator isLessCost(Magasin mag){
        return i.isCostLess(mag);
    }



}
