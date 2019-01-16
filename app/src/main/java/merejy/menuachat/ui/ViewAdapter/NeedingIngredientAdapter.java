package merejy.menuachat.ui.ViewAdapter;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.database.DataEnum.CategorieIngredient;
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingIngredient.InterfaceNeedingIngredient;
import merejy.menuachat.ui.Button.TakingIngredientButton;
import merejy.menuachat.ui.Popup.Module.NumberModule.Reel.SetIngredientPriceModule;
import merejy.menuachat.ui.Popup.Module.QuestionModule.RemoveNeedingIngedientModule;
import merejy.menuachat.ui.Popup.QuestionPopup;
import merejy.menuachat.ui.Popup.ReelNumberPopup;

public class NeedingIngredientAdapter extends RecyclerView.Adapter<NeedingIngredientAdapter.ViewHolder> {
    private List<InterfaceNeedingIngredient> list;
    private RecyclerView view;
    private Activity activity;
    private TextView listTotalPrix;
    static private NumberFormat priceFormat = new DecimalFormat("#.##");

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView produitName;
        private TextView prix;
        private TextView categorie;
        private TakingIngredientButton take;
        private TextView quantite;
        private LinearLayout v;

        public ViewHolder(LinearLayout v) {
            super(v);
            this.v = v;
            v.setPadding(10,10,10,10);
            this.produitName = new TextView(v.getContext());
            this.produitName.setPadding(10,10,10,10);
            this.produitName.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
            this.prix = new TextView(v.getContext());
            this.prix.setPadding(10,10,10,10);
            this.categorie = new TextView(v.getContext());
            this.categorie.setPadding(10,10,10,10);
            this.take = new TakingIngredientButton(v.getContext());
            this.take.setPadding(10,10,10,10);
            this.quantite = new TextView(v.getContext());
            this.quantite.setPadding(10,10,10,10);
            v.addView(take,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,10));
            v.addView(categorie,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,22));
            v.addView(produitName,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,38));
            v.addView(quantite,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,20));
            v.addView(prix,new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,10));
            take.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            produitName.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            quantite.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            prix.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            categorie.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public NeedingIngredientAdapter(Collection<InterfaceNeedingIngredient> myDataset , RecyclerView view, Activity a, TextView total) {
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
    public NeedingIngredientAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
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
        holder.take.setState(list.get(position));
        holder.produitName.setText(list.get(position).getNom());
        holder.prix.setText(priceFormat.format(list.get(position).getPrix(NeedingList.getNeeding().getCurrentMag())));
        holder.prix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReelNumberPopup.showDialog(new SetIngredientPriceModule(activity,list.get(position),view,listTotalPrix),activity);
            }
        });

        holder.categorie.setText(list.get(position).getCategorie().toString());
        holder.quantite.setText(list.get(position).getQuantite()+"");
        holder.take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.take.nextState();
                list.get(position).setTake(holder.take.getTake());
                list.get(position).setAtHome(holder.take.getAlreadyHave());
                listTotalPrix.setText(priceFormat.format(NeedingList.getNeeding().getTotal()));
            }
        });

        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPopup.showDialog(activity,new RemoveNeedingIngedientModule(activity,view,listTotalPrix,list.get(position)));
            }
        });
        //color
        holder.itemView.setBackgroundColor(ColorManager.getIngredientColor(list.get(position).getCategorie()));
        int textColor = ColorManager.getTextColor(list.get(position).getCategorie());
        holder.produitName.setTextColor(textColor);
        holder.categorie.setTextColor(textColor);
        holder.quantite.setTextColor(textColor);
        holder.prix.setTextColor(ColorManager.getPriceColor(list.get(position).isLessCost(NeedingList.getNeeding().getCurrentMag())));
        //bordure de text pour le prix
        holder.prix.setShadowLayer(1.6f,1.5f,1.3f, textColor);

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}
