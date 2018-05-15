package merejy.menuachat.kernel;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.Exception.ItemNotfound;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.database.Plat;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Needing implements Serializable {

    private List<NeedingPlat> plats;
    private HashMap<String,NeedingIngredient> ingredients;
    private Magasin currentMag = null;



    static private Needing n = null;
    final static String saveName = "ListeCourse.sav";

    private Needing(){
        this.plats = new ArrayList<>();
        this.ingredients = new HashMap<>();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Needing getNeeding(){
        if(n == null){
            Needing.load();
            if(n == null){
                n = new Needing();
            }
        }
        return n;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void load(){
        if(n == null){
            try {
                ObjectInputStream save = new ObjectInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory(),saveName)));
                n = (Needing) save.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void clear(){
        this.plats.clear();
        this.ingredients.clear();
    }

    public void add(NeedingPlat p){
        if(!this.plats.contains(p)){
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

    public void remove(NeedingIngredient i) throws ItemNotfound{
        if(this.ingredients.containsKey(i.getNom())){
            ingredients.remove(i.getNom());
        }
        else{
            throw  new ItemNotfound();
        }
    }

    public List<NeedingPlat> getPlats(){
        return  plats;
    }

    public Collection<NeedingIngredient> getIngredients(){
        List<NeedingIngredient> ing = new ArrayList<>(ingredients.values());
        for(NeedingPlat p : plats){
           for(NeedingIngredient i : p.getNeedingIngredients().values()){
               ing.add(i);
           }
        }
        return ing;
    }

    public double  getTotal(){
        double total = 0;
        for(NeedingPlat p : plats){
            if(p.isTake()){
                total += p.getPrix(currentMag);
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
}
