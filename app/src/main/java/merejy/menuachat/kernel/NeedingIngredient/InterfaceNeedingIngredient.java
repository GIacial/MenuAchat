package merejy.menuachat.kernel.NeedingIngredient;

import java.io.Serializable;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.database.Magasin;

 public interface InterfaceNeedingIngredient extends Serializable {

     PriceComparator isLessCost(Magasin mag);

     boolean isTake();

     void setTake(boolean take);

     String getNom();

     CategorieIngredient getCategorie();

     void addPrix(Double prix , Magasin mag);

     Double getPrix(Magasin mag);

     int getQuantite();
}
