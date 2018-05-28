package merejy.menuachat.database;

import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.Exception.ItemAlreadyExist;
import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.CategoriePlats;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Database  implements Serializable {

    private HashMap<String,Ingredient> ingredients;                         //tous les ingredient
    private HashMap<String,Plat> plats;                                     //tous les plats
    private HashMap<String,HashMap<String,Magasin>> magasins;               //nom,loc   => tous les magasin

    private Database (){                                                    //constructeur
        this.ingredients = new HashMap<>();
        this.plats = new HashMap<>();
        this.magasins = new HashMap<>();
    }

    public void addMagasin(String nom , String localisation) throws ItemAlreadyExist{               //ajoute un magasin
        if(this.magasins.containsKey(nom)){
            //contient le nom
            HashMap<String,Magasin> c = this.magasins.get(nom);
            if(!c.containsKey(localisation)){
                //contient pas la localisation
                c.put(localisation,new Magasin(nom,localisation));
            }
            else{
                throw  new ItemAlreadyExist();
            }

        }
        else{
            //contient pas le nom
            HashMap<String,Magasin> c = new HashMap<>();
            c.put(localisation,new Magasin(nom, localisation));
            this.magasins.put(nom,c);
        }
    }

    public void addIngedient(String nom , CategorieIngredient cat ) throws ItemAlreadyExist{        //ajoute un ingredient
        if(!ingredients.containsKey(nom)){
            this.ingredients.put(nom,new Ingredient(nom,cat));
        }
        else{
            throw new ItemAlreadyExist();
        }
    }

    public void addPlat(String nom , CategoriePlats cat , List<Ingredient> i) throws ItemAlreadyExist{     //ajoute un plat
        if(!plats.containsKey(nom)){
            this.plats.put(nom,new Plat(nom, cat, i));
        }
        else{
            throw new ItemAlreadyExist();
        }
    }

    public Collection<Ingredient> getAllIngredient(){           //donne tous les ingredient
        return  ingredients.values();
    }

    public  Collection<Plat> getAllPlat(){                      //donne tous les plats
        return  plats.values();
    }

    public  Collection<String> getAllMagasin(){                 //donne tous les noms magasin
        return magasins.keySet();
    }

    public  Ingredient getIngredient(String nom){               //donne l'ingredient qui posssede ce nom
        return ingredients.get(nom);
    }

    public  Collection<String> getAllMagasinLocation(String nomMag){        //donne toutes les localisation d'un magasin
        return magasins.get(nomMag).keySet();
    }

    public  Magasin getMagasin(String nomMag ,String localisation){         //donne l'instance du magasin à cette localisation
        Magasin m = null;
        if(magasins.containsKey(nomMag)){
            if(magasins.get(nomMag).containsKey(localisation)){
                m = magasins.get(nomMag).get(localisation);
            }
        }
        return m;
    }


    //static


    private static Database d = null;                                       //la database
    private static String saveName = "DatabaseMenuAchat.sav";               //fichier de la database

    public static Database getDatabase(){                                   //permet d'obtenir l'instance de la database
        if(d == null){
            Database.load();
            if(d == null){
                d = new Database();
            }
        }
        return d;
    }

    public static void save (){                                             //sauvegarde la database
        if(d != null){
          /*  try {
                ObjectOutputStream save = new ObjectOutputStream(new FileOutputStream(new File(Environment.getExternalStorageDirectory(),saveName)));
                save.writeObject(d);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            try {
                JsonWriter writer = new JsonWriter(new FileWriter(new File(Environment.getExternalStorageDirectory(),saveName)));
                writer.beginObject();   //debut database
                writer.name("Magasins").beginArray();   //debut magasin
                for(HashMap<String ,Magasin > magasinList : d.magasins.values()){
                    for(Magasin m : magasinList.values()){
                        //save des magasin
                        Magasin.save(m,writer);
                    }
                }
                writer.endArray();  //fin magasin
                writer.name("Ingrédients").beginArray();//debut ingredient
                for(Ingredient i : d.ingredients.values()){
                    //save des ingredient
                    Ingredient.save(i,writer);
                }
                writer.endArray();//fin ingredient
                writer.name("Plats").beginArray();//debut plats
                for(Plat p : d.plats.values()){
                    //save des plats
                    Plat.save(p,writer);
                }
                writer.endArray();//fin plats
                writer.endObject();//fin database
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void load(){                                          //charge la database
        if(d == null){
           /* try {
                ObjectInputStream save = new ObjectInputStream(new FileInputStream(new File(Environment.getExternalStorageDirectory(),saveName)));
                d = (Database) save.readObject();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }*/
            try {
                d = new Database();
                JsonReader reader = new JsonReader(new FileReader(new File(Environment.getExternalStorageDirectory(),saveName)));
                reader.beginObject();   //debut database
                while(reader.hasNext()){
                    String name = reader.nextName();
                    switch (name) {
                        case "Magasins":
                            reader.beginArray();   //debut magasin

                            while (reader.hasNext()) {
                                //load des magasin
                                Magasin.load(reader, d);

                            }
                            reader.endArray();  //fin magasin

                            break;
                        case "Ingrédients":
                            reader.beginArray();//debut ingredient

                            while (reader.hasNext()) {
                                //save des ingredient
                                Ingredient.load(reader, d);
                            }
                            reader.endArray();//fin ingredient

                            break;
                        case "Plats":
                            reader.beginArray();//debut plats

                            while (reader.hasNext()) {
                                //save des plats
                                Plat.load(reader, d);
                            }
                            reader.endArray();//fin plats

                            break;
                    }
                }
                reader.endObject();//fin database
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
