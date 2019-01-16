package merejy.menuachat.Exception;

import java.util.List;

public class IngredientWasUseInPlat extends Exception {

    private List<String> platsName;

    public IngredientWasUseInPlat (List<String> platsName){
        this.platsName = platsName;
    }

    public List<String> getPlatsName() {
        return platsName;
    }
}
