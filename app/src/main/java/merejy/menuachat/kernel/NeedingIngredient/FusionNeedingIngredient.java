package merejy.menuachat.kernel.NeedingIngredient;

import merejy.menuachat.Exception.ItemNotEquals;
import merejy.menuachat.database.CategorieIngredient;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;

public class FusionNeedingIngredient implements InterfaceNeedingIngredient {

    private InterfaceNeedingIngredient ingredient1;
    private InterfaceNeedingIngredient ingredient2;

    public  FusionNeedingIngredient(InterfaceNeedingIngredient i1 , InterfaceNeedingIngredient i2) throws ItemNotEquals {
        if(!i1.getNom().equals(i2.getNom())){
            throw new ItemNotEquals();
        }
        this.ingredient1 = i1;
        this.ingredient2 = i2;
    }


    @Override
    public boolean isLessCost(Magasin mag) {
        return ingredient1.isLessCost(mag);
    }

    @Override
    public boolean isTake() {
        return ingredient1.isTake() && ingredient2.isTake();
    }

    @Override
    public void setTake(boolean take) {
        ingredient2.setTake(take);
        ingredient1.setTake(take);
    }

    @Override
    public String getNom() {
        return ingredient1.getNom();
    }

    @Override
    public CategorieIngredient getCategorie() {
        return ingredient1.getCategorie();
    }

    @Override
    public void addPrix(Double prix, Magasin mag) {
            ingredient1.addPrix(prix,mag);
    }

    @Override
    public Double getPrix(Magasin mag) {
        return ingredient1.getPrix(mag)+ingredient2.getPrix(mag);
    }


    @Override
    public int getQuantite() {
        return ingredient1.getQuantite()+ingredient2.getQuantite();
    }
}
