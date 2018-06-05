package merejy.menuachat.ui.ViewAdapter.Needing.Plat;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import merejy.menuachat.database.DataEnum.CategoriePlats;
import merejy.menuachat.kernel.Needing.NeedingList;
import merejy.menuachat.kernel.Needing.NeedingPlat.NeedingPlat;
import merejy.menuachat.ui.Popup.Module.QuestionModule.RemoveNeedingPlatModule;
import merejy.menuachat.ui.Popup.QuestionPopup;

public class PlatAdapter  extends RecyclerView.Adapter<PlatAdapter.ViewHolder> {
    private List<NeedingPlat> list;
    private RecyclerView view;
    private Activity activity;
    private TextView prixTotalDisplay;
    static private NumberFormat priceFormat = new DecimalFormat("#.##");

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView produitName;
        private TextView prix;
        private TextView categorie;
        private CheckBox take;
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
            this.take.setClickable(false);
            this.take.setPadding(10,10,10,10);
            v.addView(take,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(categorie,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(produitName,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
            v.addView(prix,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,1));
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlatAdapter(List<NeedingPlat> myDataset, RecyclerView view,Activity activity , TextView prixTotalDisplay ) {
        list = trie(myDataset.iterator());
        this.view = view;
        this.activity = activity;
        this.prixTotalDisplay = prixTotalDisplay;
    }

    private List<NeedingPlat> trie(Iterator<NeedingPlat> iterator){
        List<ArrayList<NeedingPlat>> l = new ArrayList<>();
        for(int i = 0; i < CategoriePlats.values().length ; i++){
            l.add(new ArrayList<NeedingPlat>());
        }
        while (iterator.hasNext()){
            NeedingPlat plat = iterator.next();
            l.get(plat.getCategories().ordinal()).add(plat);
        }
        List<NeedingPlat> retour = new ArrayList<>();
        for(int i = 0 ; i <l.size() ; i++){
            retour.addAll(l.get(i));
        }
        return retour;
    }


    // Create new views (invoked by the layout manager)
    @Override
    public PlatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        LinearLayout v = new LinearLayout(parent.getContext());
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder,  int position) {
        //ici on mets a jour les info des composant du Holder
        holder.take.setChecked(list.get(position).isTake());
        holder.produitName.setText(list.get(position).getNom());
        holder.prix.setText(priceFormat.format(list.get(position).getPrix(NeedingList.getNeeding().getCurrentMag())));
        holder.categorie.setText(list.get(position).getCategories().toString());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuestionPopup.showDialog(activity,new RemoveNeedingPlatModule(view,prixTotalDisplay,list.get(holder.getAdapterPosition()),activity));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.size();
    }
}