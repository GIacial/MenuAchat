package merejy.menuachat.kernel.NeedingIngredient;

import java.io.Serializable;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.database.Magasin;

public interface InterfaceNeedingIngredient extends Serializable {

    public PriceComparator isLessCost(Magasin mag);

    public boolean isTake();

    public void setTake(boolean take);

    public String getNom();

    public CategorieIngredient getCategorie();

    public void addPrix(Double prix , Magasin mag);

    public Double getPrix(Magasin mag);

    public int getQuantite();
}
