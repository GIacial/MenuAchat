package merejy.menuachat.kernel;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.Exception.ItemNotEquals;
import merejy.menuachat.Exception.ItemNotfound;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.kernel.NeedingIngredient.FusionNeedingIngredient;
import merejy.menuachat.kernel.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.kernel.NeedingIngredient.NeedingIngredient;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Needing implements Serializable {

    private List<NeedingPlat> plats;
    private HashMap<String,NeedingIngredient> ingredients;
    private Magasin currentMag = null;
    private Needing(){
        this.plats = new ArrayList<>();
        this.ingredients = new HashMap<>();
    }

    public void clear(){
        this.plats.clear();
        this.ingredients.clear();
    }

    public void add(NeedingPlat p){
        if(p != null){
            plats.add(p);
        }

    }

    public void add(NeedingIngredient i){
        if(!this.ingredients.containsKey(i.getNom())){
            ingredients.put(i.getNom(),i);
        }
        else{
            ingredients.get(i.getNom()).addQuantite(i.getQuantite());
        }
    }

    public void remove(NeedingPlat p)throws ItemNotfound{
        if(this.plats.contains(p)){
            plats.remove(p);
        }else{
            throw  new ItemNotfound();
        }
    }

    public void remove(String ingredientName) throws ItemNotfound{
        if(this.ingredients.containsKey(ingredientName)){
            ingredients.remove(ingredientName);
        }
        else{
            throw  new ItemNotfound();
        }
    }

    public List<NeedingPlat> getPlats(){
        return  plats;
    }

    public Collection<InterfaceNeedingIngredient> getIngredients(){
        HashMap<String,InterfaceNeedingIngredient> ing = new HashMap<>();
        for(NeedingIngredient i : ingredients.values()){
            ing.put(i.getNom(),i);
        }
        for(NeedingPlat p : plats){
            for(NeedingIngredient i : p.getNeedingIngredients().values()){
                if(ing.containsKey(i.getNom())){
                    try {
                        ing.put(i.getNom(),new FusionNeedingIngredient(i,ing.get(i.getNom())));
                    } catch (ItemNotEquals itemNotEquals) {
                        itemNotEquals.printStackTrace();
                    }
                }
                else{
                    ing.put(i.getNom(),i);
                }
            }
        }
        return ing.values();
    }

    public double  getTotal(){
        double total = 0;
        for(NeedingPlat p : plats){
            if(p.isTake()){
                total += p.getPrix(currentMag);
            }
            else{
                total += p.getCurrentIngredientTakePrice(currentMag);
            }
        }
        for(NeedingIngredient i : ingredients.values()){
            if(i.isTake()){
                total += i.getPrix(currentMag);
            }
        }
        return total;
    }

    public Magasin getCurrentMag() {
        return currentMag;
    }

    public void setCurrentMag(Magasin currentMag) {
        this.currentMag = currentMag;
    }

    //static

    static private Needing n = null;
    private final static String saveName = "ListeCourse.sav";

    public static Needing getNeeding(){
        if(n == null){
            Needing.load();
            if(n == null){
                n = new Needing();
            }
        }
        return n;
    }

    public static void save (){
        if(n != null){
            try {
                ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory(),saveName)));
                save.writeObject(n);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void load(){
        if(n == null){
            try {
                ObjectInputStream save = new ObjectInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory(),saveName)));
                n = (Needing) save.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
