package merejy.menuachat.ui.ViewAdapter;

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
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Activity.All_IngredientList;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.ui.Button.OnClickListenerCreator.OnClickListenerCreator;
import merejy.menuachat.ui.Popup.EntierNumberPopUp;
import merejy.menuachat.ui.Popup.Module.NumberModule.Entier.QuantiteIngredientModule;

public class ChoiceIngredientAdapter extends RecyclerView.Adapter<ChoiceIngredientAdapter.ViewHolder> {
    private List<Ingredient> list;
    private AbstractActivity activity;
    private OnClickListenerCreator<Ingredient> choiceAction;

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
            v.addView(categorie,new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(produitName,new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT,2));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChoiceIngredientAdapter(Collection<Ingredient> myDataset , AbstractActivity activity , OnClickListenerCreator<Ingredient> choiceAction) {
        list = trie(myDataset.iterator());
        this.activity = activity;
        this.choiceAction = choiceAction;
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
    public ChoiceIngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        final Ingredient ingredient = list.get(position);
        holder.produitName.setText(ingredient.getNom());
        holder.categorie.setText(list.get(position).getCategorie().toString());
        holder.layout.setOnClickListener(choiceAction.createListener(list.get(position),activity));
        holder.itemView.setBackgroundColor(ColorManager.getIngredientColor(list.get(position).getCategorie()));

        holder.categorie.setTextColor(ColorManager.getTextColor(ingredient.getCategorie()));
        holder.produitName.setTextColor(ColorManager.getTextColor(ingredient.getCategorie()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}