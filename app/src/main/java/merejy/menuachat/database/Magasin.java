package merejy.menuachat.database;

import java.io.Serializable;
import java.util.Objects;

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
}
