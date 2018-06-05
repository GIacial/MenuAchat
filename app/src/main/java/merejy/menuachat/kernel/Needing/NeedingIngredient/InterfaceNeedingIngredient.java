package merejy.menuachat.kernel.Needing.NeedingIngredient;

import java.io.Serializable;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.kernel.Needing.NeedingInterface;

public interface InterfaceNeedingIngredient extends Serializable, NeedingInterface {

     PriceComparator isLessCost(Magasin mag);

     CategorieIngredient getCategorie();

     void addPrix(Double prix , Magasin mag);

     int getQuantite();

     int getGrammage();
}
