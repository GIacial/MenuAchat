package merejy.menuachat.ui.ViewAdapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.kernel.Needing;
import merejy.menuachat.kernel.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.ui.Popup.Module.QuestionModule.RemoveNeedingIngedientModule;
import merejy.menuachat.ui.Popup.QuestionPopup;
import merejy.menuachat.ui.Popup.SetPricePopup;

public class IngredientAdapter  extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    private List<InterfaceNeedingIngredient> list;
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
            v.setPadding(10,10,10,10);
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
    public IngredientAdapter(Collection<InterfaceNeedingIngredient> myDataset , RecyclerView view, Activity a, TextView total) {
        list = trie(myDataset.iterator());
        this.view = view;
        this.activity = a;
        this.listTotalPrix = total;


    }

    private List<InterfaceNeedingIngredient> trie(Iterator<InterfaceNeedingIngredient> iterator){
        List<ArrayList<InterfaceNeedingIngredient>> l = new ArrayList<>();
        for(int i = 0; i < CategorieIngredient.values().length ; i++){
            l.add(new ArrayList<InterfaceNeedingIngredient>());
        }
        while (iterator.hasNext()){
            InterfaceNeedingIngredient ingredient = iterator.next();
            l.get(ingredient.getCategorie().ordinal()).add(ingredient);
        }
        List<InterfaceNeedingIngredient> retour = new ArrayList<>();
        for(int i = 0 ; i <l.size() ; i++){
            retour.addAll(l.get(i));
        }
        return retour;
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

        holder.prix.setTextColor(ColorManager.getPriceColor(list.get(position).isLessCost(Needing.getNeeding().getCurrentMag())));


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
                QuestionPopup.showDialog(activity,new RemoveNeedingIngedientModule(activity,view,listTotalPrix,list.get(position)));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
