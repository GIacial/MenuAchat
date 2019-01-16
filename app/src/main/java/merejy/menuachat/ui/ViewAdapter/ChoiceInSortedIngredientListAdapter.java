package merejy.menuachat.ui.ViewAdapter;

import android.opengl.Visibility;
import android.support.transition.Slide;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import merejy.menuachat.R;
import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.ui.Activity.AbstractActivity;
import merejy.menuachat.ui.Activity.All_IngredientList;
import merejy.menuachat.ui.Activity.IngredientCreator;
import merejy.menuachat.ui.Button.OnClickListenerCreator.OnClickListenerCreator;
import merejy.menuachat.ui.Popup.EntierNumberPopUp;
import merejy.menuachat.ui.Popup.Module.NumberModule.Entier.QuantiteIngredientModule;

public class ChoiceInSortedIngredientListAdapter extends RecyclerView.Adapter<ChoiceInSortedIngredientListAdapter.ViewHolder> {
    private List<List<Ingredient>> list;
    private List<AtomicBoolean> show;
    private AbstractActivity activity;
    private OnClickListenerCreator<Ingredient> choiceAction;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView categorie;
        private LinearLayout layout;

        public ViewHolder(LinearLayout v) {
            super(v);
            this.layout = v;
            v.setPadding(10, 10, 10, 10);
            this.categorie = new TextView(v.getContext());
            v.addView(categorie, new LinearLayout.LayoutParams(10, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChoiceInSortedIngredientListAdapter(Collection<Ingredient> myDataset, AbstractActivity activity , OnClickListenerCreator<Ingredient> choiceAction) {
        this.show = new ArrayList<>();
        list = trie(myDataset.iterator());
        this.activity = activity;
        this.choiceAction = choiceAction;


    }

    private List<List<Ingredient>> trie(Iterator<Ingredient> iterator) {
        List<List<Ingredient>> l = new ArrayList<>();
        for (int i = 0; i < CategorieIngredient.values().length; i++) {
            l.add(new ArrayList<Ingredient>());
            show.add(new AtomicBoolean(false));
        }
        while (iterator.hasNext()) {
            Ingredient ingredient = iterator.next();
            l.get(ingredient.getCategorie().ordinal()).add(ingredient);
        }

        return l;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ChoiceInSortedIngredientListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                             int viewType) {
        // create a new view
        LinearLayout v = new LinearLayout(parent.getContext());
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ChoiceInSortedIngredientListAdapter.ViewHolder vh = new ChoiceInSortedIngredientListAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ChoiceInSortedIngredientListAdapter.ViewHolder holder, final int position) {
        //ici on mets a jour les info des composant du Holder

        //On cherche la categorie
        int reelPosition = position;
        int numCat = 0;
        boolean first = true;
        while (reelPosition > 0){

            if(show.get(numCat).get()){
                reelPosition -= list.get(numCat).size();
            }
            if( reelPosition > 0){
                numCat ++;
            }
            reelPosition --; //1 pour enlever la categorie
        }

        final CategorieIngredient categorieIngredient = CategorieIngredient.values()[numCat];

        //on doit savoir si on fait une categorie ou un ingredient

        holder.layout.removeAllViews();
        holder.layout.addView(holder.categorie);

        if(reelPosition == 0){
            //categorie

            holder.categorie.setText(categorieIngredient.toString());
            holder.categorie.setGravity(android.view.Gravity.CENTER);
            holder.categorie.setTextColor(ColorManager.getTextColor(categorieIngredient));
            holder.itemView.setBackgroundColor(ColorManager.getIngredientColor(categorieIngredient));
            ImageButton image = new ImageButton(holder.layout.getContext());
            if( show.get(numCat).get()){
                image.setImageResource(android.R.drawable.arrow_up_float);
            }
            else{
                image.setImageResource(android.R.drawable.arrow_down_float);
            }
            holder.layout.addView(image);
            View.OnClickListener menuDeroulant  = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AtomicBoolean bool = show.get(categorieIngredient.ordinal());
                    bool.set(!bool.get());
                    ChoiceInSortedIngredientListAdapter.this.notifyDataSetChanged();
                }
            };
            holder.layout.setOnClickListener(menuDeroulant);
            image.setOnClickListener(menuDeroulant);
        }
        else{
            //ingredient
            final  Ingredient ingredient = list.get(numCat).get(list.get(numCat).size() + reelPosition);
            holder.categorie.setText(ingredient.getNom());
            holder.categorie.setGravity(Gravity.NO_GRAVITY);
            holder.categorie.setTextColor(ColorManager.getTextColor(ingredient.getCategorie()));
            holder.layout.setOnClickListener(choiceAction.createListener(ingredient,activity));
            holder.itemView.setBackgroundColor(ColorManager.getIngredientColor(ingredient.getCategorie()));
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        int size = 0;
        for(int catNum = 0 ; catNum < CategorieIngredient.values().length ; catNum ++){
            size ++;
            if(show.get(catNum).get()){
                size += list.get(catNum).size();
            }
        }
        return size;
    }
}