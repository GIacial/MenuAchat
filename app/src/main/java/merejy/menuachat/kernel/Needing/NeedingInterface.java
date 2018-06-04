package merejy.menuachat.kernel.Needing;


import merejy.menuachat.database.Magasin;

public interface NeedingInterface {

    boolean isTake();

    void setTake(boolean take);

    boolean isAtHome();

    void setAtHome(boolean atHome);

    String getNom();

    Double getPrix(Magasin mag);
}
