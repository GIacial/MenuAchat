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

import merejy.menuachat.ui.Activity.All_IngredientList;
import merejy.menuachat.ui.Activity.ListeIngredientActivity;
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient;
import merejy.menuachat.ui.Activity.PlatCreator;

public class ChoiceIngredientAdapter  extends RecyclerView.Adapter<ChoiceIngredientAdapter.ViewHolder> {
    private List<Ingredient> list;
    private All_IngredientList activity;

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
            this.produitName = new TextView(v.getContext());
            this.produitName.setPadding(10,10,10,10);
            this.categorie = new TextView(v.getContext());
            this.categorie.setPadding(10,10,10,10);
            v.addView(categorie,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(produitName,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ChoiceIngredientAdapter(Collection<Ingredient> myDataset , All_IngredientList activity) {
        list = new ArrayList<>();
        this.activity = activity;
        Iterator i = myDataset.iterator();
        while(i.hasNext()){
            list.add((Ingredient) i.next());
        }
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
        holder.produitName.setText(list.get(position).getNom());
        holder.categorie.setText(list.get(position).getCategorie().toString());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondeActivite = null ;
                switch (All_IngredientList.target){
                   case LIST_INGREDIENT :
                    secondeActivite = new Intent(activity,ListeIngredientActivity.class);
                    Needing.getNeeding().add(new NeedingIngredient(list.get(position)));
                    break;
                    case PLAT_CREATOR:
                        secondeActivite = new Intent(activity,PlatCreator.class);
                        PlatCreator.addIngredient(list.get(position));
                        break;
                    default:break;
                }
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