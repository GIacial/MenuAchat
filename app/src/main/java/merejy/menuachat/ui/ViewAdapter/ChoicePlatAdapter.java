package merejy.menuachat.ui.ViewAdapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.database.CategorieIngredient;
import merejy.menuachat.database.CategoriePlats;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Plat;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient;
import merejy.menuachat.kernel.NeedingPlat;
import merejy.menuachat.ui.Activity.AllPlatList;
import merejy.menuachat.ui.Activity.All_IngredientList;
import merejy.menuachat.ui.Activity.ListeIngredientActivity;
import merejy.menuachat.ui.Activity.ListePlatActivity;

public class ChoicePlatAdapter  extends RecyclerView.Adapter<ChoicePlatAdapter.ViewHolder> {
    private List<Plat> list;
    private AllPlatList activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView produitName;
        private TextView categorie;
        private LinearLayout layout;

        public ViewHolder(LinearLayout v) {
            super(v);
            this.layout = v;
            v.setPadding(10,10,10,10);
            this.produitName = new TextView(v.getContext());
            this.produitName.setPadding(10,10,10,10);
            this.categorie = new TextView(v.getContext());
            this.categorie.setPadding(10,10,10,10);
            v.addView(categorie,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(produitName,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChoicePlatAdapter(Collection<Plat> myDataset , AllPlatList activity) {
        list = trie(myDataset.iterator());
        this.activity = activity;
    }

    private List<Plat> trie(Iterator<Plat> iterator){
        List<ArrayList<Plat>> l = new ArrayList<>();
        for(int i = 0; i < CategoriePlats.values().length ; i++){
            l.add(new ArrayList<Plat>());
        }
        while (iterator.hasNext()){
            Plat plat = iterator.next();
            l.get(plat.getCategories().ordinal()).add(plat);
        }
        List<Plat> retour = new ArrayList<>();
        for(int i = 0 ; i <l.size() ; i++){
            retour.addAll(l.get(i));
        }
        return retour;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChoicePlatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        LinearLayout v = new LinearLayout(parent.getContext());
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //ici on mets a jour les info des composant du Holder
        holder.produitName.setText(list.get(position).getNom());
        holder.categorie.setText(list.get(position).getCategories().toString());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Needing.getNeeding().add(new NeedingPlat(list.get(position)));
                Intent secondeActivite = new Intent(activity,ListePlatActivity.class);

                secondeActivite.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);        //permet de fermer les activity
                activity.startActivity(secondeActivite);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
