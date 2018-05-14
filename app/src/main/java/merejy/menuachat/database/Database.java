package merejy.menuachat.database;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;

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

import merejy.menuachat.kernel.Needing;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Database  implements Serializable {

    private HashMap<String,Ingredient> ingredients;
    private HashMap<String,Plat> plats;
    private HashMap<String,HashMap<String,Magasin>> magasins;//nom,loc
    private static Database d = null;
    private static String saveName = "DatabaseMenuAchat.sav";

    private Database (){
        this.ingredients = new HashMap<>();
        this.plats = new HashMap<>();
        this.magasins = new HashMap<>();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static Database getDatabase(){
        if(d == null){
            Database.load();
            if(d == null){
                d = new Database();
            }
        }
        return d;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void save (){
        if(d != null){
            try {
                ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory(),saveName)));
                save.writeObject(d);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void load(){
        if(d == null){
            try {
                ObjectInputStream save = new ObjectInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory(),saveName)));
                d = (Database) save.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    public void addMagasin(String nom , String localisation){
       if(this.magasins.containsKey(nom)){
           //contient le nom
           HashMap<String,Magasin> c = this.magasins.get(nom);
           if(!c.containsKey(localisation)){
               //contient pas la localisation
               c.put(localisation,new Magasin(nom,localisation));
           }

       }
       else{
           //contient pas le nom
           HashMap<String,Magasin> c = new HashMap<String, Magasin>();
           c.put(localisation,new Magasin(nom, localisation));
           this.magasins.put(nom,c);
       }
    }

    public void addIngedient(String nom , CategorieIngredient cat ){
        if(!ingredients.containsKey(nom)){
            this.ingredients.put(nom,new Ingredient(nom,cat));
        }
    }

    public void addPlat(String nom , CategoriePlats cat ,  List<Ingredient> i){
        if(!plats.containsKey(nom)){
            this.plats.put(nom,new Plat(nom, cat, i));
        }
    }

    public Collection<Ingredient> getAllIngredient(){
        return  ingredients.values();
    }

    public  Collection<Plat> getAllPlat(){
        return  plats.values();
    }

    public  Collection<String> getAllMagasin(){
        return magasins.keySet();
    }

    public  Ingredient getIngredient(String nom){
        return ingredients.get(nom);
    }

    public  Collection<String> getAllMagasinLocation(String nomMag){
        return magasins.get(nomMag).keySet();
    }

    public  Magasin getMagasin(String nomMag ,String localisation){
        return magasins.get(nomMag).get(localisation);
    }
}
