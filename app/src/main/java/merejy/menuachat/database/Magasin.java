package merejy.menuachat.database;

import android.util.JsonReader;
import android.util.JsonWriter;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

import merejy.menuachat.Exception.ItemAlreadyExist;

/**
 * Created by Jeremy on 22/04/2018.
 */

public class Magasin  implements Serializable {

    private String nom;
    private String localisation;

    public Magasin(String nom , String localisation){
        this.nom = nom;
        this.localisation = localisation;
    }

    public String getNom() {
        return nom;
    }

    public String getLocalisation() {
        return localisation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Magasin)) return false;
        Magasin magasin = (Magasin) o;
        return Objects.equals(nom, magasin.nom) &&
                Objects.equals(localisation, magasin.localisation);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nom, localisation);
    }

    public static void save (Magasin i , JsonWriter writer){
        try {
            writer.beginObject();   //debut magasin
            writer.name("Nom").value(i.getNom());
            writer.name("Localisation").value(i.getLocalisation());
            writer.endObject();    //fin magasin
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  static void  load (JsonReader reader , Database database){

        try {
            String localisation = null;
            String nomMag = null;
            reader.beginObject();   //debut magasin
            while (reader.hasNext()){
                String nom = reader.nextName();
                if(nom.equals("Nom")){
                    nomMag = reader.nextString();
                }
                else if( nom.equals("Localisation")){
                    localisation = reader.nextString();
                }
            }
            reader.endObject();    //fin magasin
            if(localisation != null && nomMag != null){
                try {
                    database.addMagasin(nomMag,localisation);
                } catch (ItemAlreadyExist itemAlreadyExist) {
                    itemAlreadyExist.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
