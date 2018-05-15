package merejy.menuachat.kernel;

import java.io.Serializable;

import merejy.menuachat.database.CategorieIngredient;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class NeedingIngredient implements Serializable {

    private Ingredient i;
    private boolean take;
    private int quantite;

    public NeedingIngredient (Ingredient i){
        this.i = i;
        this.take = false;
        this.quantite = 1;
    }

    public boolean isTake() {
        return take;
    }

    public void setTake(boolean take) {
        this.take = take;
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

    public boolean isLessCost(Magasin mag){
        return i.isCostLess(mag);
    }


}
