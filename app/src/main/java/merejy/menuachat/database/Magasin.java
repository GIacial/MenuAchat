package merejy.menuachat.database;

import java.io.Serializable;

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
}
