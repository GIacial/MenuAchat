package merejy.menuachat.ui.ViewAdapter;

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
import merejy.menuachat.database.Ingredient;
import merejy.menuachat.database.Materiaux;
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.ui.Activity.All_IngredientList;
import merejy.menuachat.ui.Popup.EntierNumberPopUp;
import merejy.menuachat.ui.Popup.Module.NumberModule.Entier.QuantiteIngredientModule;

public class MateriauxQuantiteAdapter  extends RecyclerView.Adapter<MateriauxQuantiteAdapter.ViewHolder> {
    private HashMap<Materiaux,Integer> list;
    private Activity activity;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView produitName;
        private TextView quantite;
        private LinearLayout layout;

        public ViewHolder(LinearLayout v) {
            super(v);
            this.layout = v;
            v.setPadding(10,10,10,10);
            this.produitName = new TextView(v.getContext());
            this.produitName.setPadding(10,10,10,10);
            this.quantite = new TextView(v.getContext());
            this.quantite.setPadding(10,10,10,10);
            v.addView(produitName,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(quantite,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MateriauxQuantiteAdapter(HashMap<Materiaux,Integer> myDataset , Activity activity) {
        list = myDataset;
        this.activity = activity;



    }



    // Create new views (invoked by the layout manager)
    @Override
    public MateriauxQuantiteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        // create a new view
        LinearLayout v = new LinearLayout(parent.getContext());
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        MateriauxQuantiteAdapter.ViewHolder vh = new MateriauxQuantiteAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MateriauxQuantiteAdapter.ViewHolder holder, final int position) {
        //ici on mets a jour les info des composant du Holder
        Materiaux materiaux = (Materiaux) list.keySet().toArray()[position];
        holder.produitName.setText(materiaux.getNom());
        holder.quantite.setText(list.get(materiaux)+"");

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.keySet().size();
    }
}