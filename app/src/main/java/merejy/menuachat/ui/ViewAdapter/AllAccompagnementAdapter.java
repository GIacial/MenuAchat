package merejy.menuachat.ui.ViewAdapter;

import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.ui.Activity.All_IngredientList;
import merejy.menuachat.ui.Popup.EntierNumberPopUp;
import merejy.menuachat.ui.Popup.Module.NumberModule.Entier.QuantiteIngredientModule;

public class AllAccompagnementAdapter implements SpinnerAdapter {
    private List<Plat> list;


    public AllAccompagnementAdapter(Collection<Plat> myDataset ) {
        list = trie(myDataset.iterator());

    }

    private List<Plat> trie(Iterator<Plat> iterator){
        List<ArrayList<Plat>> l = new ArrayList<>();
        for(int i = 0; i < CategorieIngredient.values().length ; i++){
            l.add(new ArrayList<Plat>());
        }
        while (iterator.hasNext()){
            Plat ingredient = iterator.next();
            if(ingredient.isAccompagner()){
                l.get(ingredient.getCategories().ordinal()).add(ingredient);
            }
        }
        List<Plat> retour = new ArrayList<>();
        for(int i = 0 ; i <l.size() ; i++){
            retour.addAll(l.get(i));
        }
        return retour;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view =  new TextView(parent.getContext());
        view.setText(list.get(position).getNom());
        return view;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view =  new TextView(parent.getContext());
        view.setText(list.get(position).getNom());
        return view;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
