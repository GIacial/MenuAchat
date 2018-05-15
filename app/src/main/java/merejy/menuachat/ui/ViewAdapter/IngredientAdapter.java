package merejy.menuachat.ui.ViewAdapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.Exception.ItemNotfound;
import merejy.menuachat.R;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient;
import merejy.menuachat.ui.Activity.MagasinCreator;
import merejy.menuachat.ui.Popup.SetPricePopup;

public class IngredientAdapter  extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<NeedingIngredient> list;
    private RecyclerView view;
    private Activity activity;
    private TextView listTotalPrix;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView produitName;
        private TextView prix;
        private TextView categorie;
        private CheckBox take;
        private TextView quantite;
        private LinearLayout v;

        public ViewHolder(LinearLayout v) {
            super(v);
            this.v = v;
            this.produitName = new TextView(v.getContext());
            this.produitName.setPadding(10,10,10,10);
            this.prix = new TextView(v.getContext());
            this.prix.setPadding(10,10,10,10);
            this.categorie = new TextView(v.getContext());
            this.categorie.setPadding(10,10,10,10);
            this.take = new CheckBox(v.getContext());
            this.take.setChecked(false);
            this.take.setClickable(true);
            this.take.setPadding(10,10,10,10);
            this.quantite = new TextView(v.getContext());
            this.quantite.setPadding(10,10,10,10);
            v.addView(take,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(categorie,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(produitName,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(quantite,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(prix,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public IngredientAdapter(Collection<NeedingIngredient> myDataset ,RecyclerView view,Activity a,TextView total) {
        list = new ArrayList<>();
        this.view = view;
        this.activity = a;
        this.listTotalPrix = total;
        Iterator i = myDataset.iterator();
        while(i.hasNext()){
            list.add((NeedingIngredient) i.next());
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        holder.take.setChecked(list.get(position).isTake());
        holder.produitName.setText(list.get(position).getNom());
        holder.prix.setText(list.get(position).getPrix(Needing.getNeeding().getCurrentMag())+"");
        holder.prix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetPricePopup.showDialog(list.get(position),view,activity,listTotalPrix);
            }
        });
        if(list.get(position).isLessCost(Needing.getNeeding().getCurrentMag())){
            holder.prix.setTextColor(activity.getResources().getColor(R.color.goodPrice));
        }
        else{

            holder.prix.setTextColor(activity.getResources().getColor(R.color.badPrice));
        }

        holder.categorie.setText(list.get(position).getCategorie().toString());
        holder.quantite.setText(list.get(position).getQuantite()+"");
        holder.take.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setTake(isChecked);
                listTotalPrix.setText(Needing.getNeeding().getTotal()+"");
            }
        });
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Needing.getNeeding().remove(list.get(position));
                } catch (ItemNotfound itemNotfound) {
                    Toast.makeText(activity, R.string.error_ingredientNotFound,Toast.LENGTH_LONG).show();
                }
                view.setAdapter(new IngredientAdapter(Needing.getNeeding().getIngredients(),view,activity,listTotalPrix));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
