package merejy.menuachat.ui.ViewAdapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.ui.Activity.PlatCreator;
import merejy.menuachat.ui.Popup.Module.QuestionModule.RemoveIngredientPlatModule;
import merejy.menuachat.ui.Popup.QuestionPopup;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.ViewHolder> {
    private List<Ingredient> list;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView produitName;
        private TextView prix;
        private TextView categorie;

        public ViewHolder(LinearLayout v) {
            super(v);
            v.setPadding(10,10,10,10);
            this.produitName = new TextView(v.getContext());
            this.produitName.setPadding(10,10,10,10);
            this.prix = new TextView(v.getContext());
            this.prix.setPadding(10,10,10,10);
            this.categorie = new TextView(v.getContext());
            this.categorie.setPadding(10,10,10,10);

            v.addView(categorie,new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(produitName,new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(prix,new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)

    public IngredientListAdapter(Collection<Ingredient> myDataset , Activity activity ) {
        list = trie(myDataset.iterator());
        this.activity = activity;

    }

    private List<Ingredient> trie(Iterator<Ingredient> iterator){
        List<ArrayList<Ingredient>> l = new ArrayList<>();
        for(int i = 0; i < CategorieIngredient.values().length ; i++){
            l.add(new ArrayList<Ingredient>());
        }
        while (iterator.hasNext()){
            Ingredient ingredient = iterator.next();
            l.get(ingredient.getCategorie().ordinal()).add(ingredient);
        }
        List<Ingredient> retour = new ArrayList<>();
        for(int i = 0 ; i <l.size() ; i++){
            retour.addAll(l.get(i));
        }
        return retour;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IngredientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        // create a new view
        LinearLayout v = new LinearLayout(parent.getContext());
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        IngredientListAdapter.ViewHolder vh = new IngredientListAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final IngredientListAdapter.ViewHolder holder, final int position) {
        //ici on mets a jour les info des composant du Holder
        holder.produitName.setText(list.get(position).getNom());
        holder.prix.setText(list.get(position).getPrix(NeedingList.getNeeding().getCurrentMag())+"");
        holder.categorie.setText(list.get(position).getCategorie().toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPopup.showDialog(activity ,new RemoveIngredientPlatModule(activity,IngredientListAdapter.this,list.get(position),list));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
