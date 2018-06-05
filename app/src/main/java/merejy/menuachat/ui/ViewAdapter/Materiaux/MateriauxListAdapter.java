package merejy.menuachat.ui.ViewAdapter.Materiaux;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.DataEnum.CategorieMateriaux;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Materiaux;
import merejy.menuachat.ui.Activity.Abstract.AbstractActivity;
import merejy.menuachat.ui.Popup.EntierNumberPopUp;
import merejy.menuachat.ui.Popup.Module.NumberModule.Entier.GrammeQuantityModule;

public class MateriauxListAdapter  extends RecyclerView.Adapter<MateriauxListAdapter.ViewHolder> {
    private List<Materiaux> list;
    private AbstractActivity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView produitName;
        private TextView categorie;

        public ViewHolder(LinearLayout v) {
            super(v);
            v.setPadding(10,10,10,10);
            this.produitName = new TextView(v.getContext());
            this.produitName.setPadding(10,10,10,10);
            this.categorie = new TextView(v.getContext());
            this.categorie.setPadding(10,10,10,10);
            v.addView(produitName,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(categorie,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MateriauxListAdapter(Collection<Materiaux> myDataset , AbstractActivity activity) {
        list = trie(myDataset.iterator());
        this.activity = activity;



    }

    private List<Materiaux> trie(Iterator<Materiaux> iterator){
        List<ArrayList<Materiaux>> l = new ArrayList<>();
        for(int i = 0; i < CategorieMateriaux.values().length ; i++){
            l.add(new ArrayList<Materiaux>());
        }
        while (iterator.hasNext()){
            Materiaux ingredient = iterator.next();
            l.get(ingredient.getCategorieMateriaux().ordinal()).add(ingredient);
        }
        List<Materiaux> retour = new ArrayList<>();
        for(int i = 0 ; i <l.size() ; i++){
            retour.addAll(l.get(i));
        }
        return retour;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public MateriauxListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
        // create a new view
        LinearLayout v = new LinearLayout(parent.getContext());
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new MateriauxListAdapter.ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MateriauxListAdapter.ViewHolder holder, final int position) {
        //ici on mets a jour les info des composant du Holder
        Materiaux materiaux = list.get(position);
        holder.produitName.setText(materiaux.getNom());
        holder.categorie.setText(materiaux.getCategorieMateriaux().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntierNumberPopUp.showDialog(activity,new GrammeQuantityModule(activity,list.get(holder.getAdapterPosition())));
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}