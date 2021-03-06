package merejy.menuachat.kernel.Needing;

import android.os.Environment;
import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import merejy.menuachat.Exception.ItemNotEquals;
import merejy.menuachat.Exception.ItemNotfound;
import merejy.menuachat.database.Database;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Magasin;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing.NeedingIngredient.FusionNeedingIngredient;
import merejy.menuachat.kernel.Needing.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.kernel.Needing.NeedingIngredient.NeedingIngredient;
import merejy.menuachat.kernel.Needing.NeedingPlat.NeedingPlat;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class NeedingList implements Serializable {

    private List<NeedingPlat> plats;
    private HashMap<String,NeedingIngredient> ingredients;
    private Magasin currentMag = null;
    private double supplements = 0;


    private NeedingList(){
        this.plats = new ArrayList<>();
        this.ingredients = new HashMap<>();
    }

    public void clear(){
        this.plats.clear();
        this.ingredients.clear();
        this.resetSupplements();
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
        double total = Double.NaN;
        if(currentMag != null){
            total = supplements;
            for(NeedingPlat p : plats){
                double platPrix = p.getPrix(currentMag);

                total += p.getCurrentIngredientTakePrice(currentMag);

            }
            for(NeedingIngredient i : ingredients.values()){
                double ingredientPrix = i.getPrix(currentMag);
                if(i.isTake() &&  !Double.isNaN(ingredientPrix)){
                    total += ingredientPrix;
                }
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

    public  void addSupplements(double supplements){
        this.supplements += supplements;
    }

    public  double getSupplements (){
        return  supplements;
    }

    public  void resetSupplements(){
        supplements = 0;
    }

    //static

    static private NeedingList n = null;
    private final static String saveName = "ListeCourse.sav";

    private final static String saveTag_supplements = "Supplements";
    private final static String saveTag_ingredient = "Ingrédients";
    private final static String saveTag_nomIngredient = "Nom";
    private final static String saveTag_quantite = "Quantité";
    private final static String saveTag_nomPlat = "Nom";
    private final static String saveTag_plat = "Plats";
    private final static String saveTag_platAccompagnement = "Accompagnement";
    private final static String saveTag_atHome = "AtHomeState";
    private final static String saveTag_isTake = "isTakeState";
    private final static String saveTag_IngredientStateList = "ingredientListState";
    private final static String saveTag_IngredientState = "ingredientState";

    public static NeedingList getNeeding(){
        if(n == null){
            NeedingList.load();
            if(n == null){
                n = new NeedingList();
            }
        }
        return n;
    }

    public static void save (){
        if(n != null){
            try {
                File dossier = new File(Environment.getExternalStorageDirectory(), Database.dossierSave);
                if(!dossier.exists() || !dossier.isDirectory()){
                    dossier.mkdir();
                }
                JsonWriter writer = new JsonWriter(new FileWriter(new File(Environment.getExternalStorageDirectory()+File.separator+ Database.dossierSave,saveName)));
                writer.beginObject();   //debut databNeeding
                writer.name(saveTag_supplements).value(n.supplements);
                writer.name(saveTag_ingredient).beginArray();//debut ingredient
                for(NeedingIngredient i : n.ingredients.values()){
                    //save des ingredient
                    writer.beginObject();
                    writer.name(saveTag_nomIngredient).value(i.getNom());
                    writer.name(saveTag_quantite).value(i.getQuantite());
                    if(i.isAtHome()){
                        writer.name(saveTag_atHome).value(i.isAtHome());
                    }
                    else{
                        if(i.isTake()){
                            writer.name(saveTag_isTake).value(i.isTake());
                        }
                    }
                    writer.endObject();
                }
                writer.endArray();//fin ingredient
                writer.name(saveTag_plat).beginArray();//debut plats
                for(NeedingPlat p : n.plats){
                    //save des plats
                    writer.beginObject();
                    if(p.isAccomppagner()){
                        writer.name(saveTag_nomPlat).value(p.getPrincipalName());
                        writer.name(saveTag_platAccompagnement).value(p.getAccompagnementName());
                    }
                    else{
                        writer.name(saveTag_nomPlat).value(p.getNom());
                    }

                    writer.name(saveTag_IngredientStateList).beginArray();
                    for( NeedingIngredient ni : p.getNeedingIngredients().values()){
                        writer.beginObject();
                        writer.name(saveTag_ingredient).value(ni.getNom());
                        writer.name(saveTag_IngredientState).value(NeedingState.getConfigNeeding(ni).toString());
                        writer.endObject();
                    }
                    writer.endArray();


                    writer.endObject();
                }
                writer.endArray();//fin plats
                writer.endObject();//fin needing
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static void load(){
        File fichier = new File(Environment.getExternalStorageDirectory()+File.separator+ Database.dossierSave,saveName);
        if(n == null && fichier.exists()){
            try {
                n = new NeedingList();
                JsonReader reader = new JsonReader(new FileReader(fichier));
                reader.beginObject();   //debut Needding
                while(reader.hasNext()){
                    String name = reader.nextName();
                    switch (name) {
                        case saveTag_supplements:
                            n.supplements = reader.nextDouble();
                            break;
                        case saveTag_ingredient:
                            reader.beginArray();//debut ingredient
                            while (reader.hasNext()) {
                                //load des ingredient
                                reader.beginObject();
                                Ingredient ingredient = null;
                                int quantite = 0;
                                boolean isTake = false;
                                boolean atHome = false;
                                while(reader.hasNext()){
                                    String name2 = reader.nextName();
                                    switch (name2){
                                        case saveTag_nomIngredient :
                                            ingredient = Database.getDatabase().getIngredient(reader.nextString());
                                            break;
                                        case saveTag_quantite :
                                            quantite = reader.nextInt();
                                            break;
                                        case saveTag_atHome: atHome = reader.nextBoolean();
                                            break;
                                        case  saveTag_isTake: isTake = reader.nextBoolean();
                                            break;
                                        default: System.err.println(name2 + "n'est pas connu");

                                    }

                                }
                               if(quantite != 0 && ingredient != null){
                                    NeedingIngredient needingIngredient = new NeedingIngredient(ingredient);
                                   needingIngredient.addQuantite(quantite-1);
                                   needingIngredient.setAtHome(atHome);
                                   needingIngredient.setTake(isTake);
                                    n.add(needingIngredient);
                               }
                               reader.endObject();
                            }
                            reader.endArray();//fin ingredient

                            break;
                        case saveTag_plat:
                            reader.beginArray();//debut plats

                            while (reader.hasNext()) {
                                //load des plats
                                reader.beginObject();
                                Plat plat = null;
                                Plat accompagnement = null;
                                HashMap<String,NeedingState> isTake = new HashMap<>();
                                while (reader.hasNext()){
                                    String name2 = reader.nextName();
                                    switch (name2){
                                        case saveTag_nomPlat : plat =  Database.getDatabase().getPlat(reader.nextString());
                                            break;
                                        case saveTag_platAccompagnement: accompagnement =  Database.getDatabase().getPlat(reader.nextString());
                                            break;
                                        case  saveTag_IngredientStateList:
                                            reader.beginArray();
                                            while (reader.hasNext()){
                                                reader.beginObject();
                                                String nomIngredient = null;
                                                NeedingState needingState = NeedingState.NEEDING_STATE_NONE;
                                                while (reader.hasNext()){
                                                    String name3 = reader.nextName();
                                                    switch (name3){
                                                        case saveTag_ingredient : nomIngredient = reader.nextString();
                                                            break;
                                                        case saveTag_IngredientState: try {
                                                            needingState = NeedingState.valueOf(reader.nextString());
                                                        }
                                                        catch (IllegalArgumentException e){
                                                            System.err.println(e);
                                                        }
                                                            break;
                                                            default:break;
                                                    }
                                                }
                                                reader.endObject();
                                                if(nomIngredient != null){
                                                    isTake.put(nomIngredient,needingState);
                                                }
                                            }
                                            reader.endArray();
                                            break;
                                        default: System.err.println(name2 + "n'est pas connu");
                                    }
                                }
                                if(plat != null){
                                    NeedingPlat needingPlat = new NeedingPlat(plat , accompagnement, isTake);
                                    n.add(needingPlat);
                                }
                                reader.endObject();
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
