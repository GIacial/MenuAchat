package merejy.menuachat.ui.ViewAdapter.ColorChoiceAdapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.kernel.ColorManager;
import merejy.menuachat.ui.Popup.ColorPopup;
import merejy.menuachat.ui.Popup.Module.ColorModule.PriceComparatorColorPopupModule;

public class PrixColorAdapter extends RecyclerView.Adapter<PrixColorAdapter.ViewHolder> {
private PriceComparator[] list;
private Activity activity;



public static class ViewHolder extends RecyclerView.ViewHolder {

    private LinearLayout layout;
    private TextView categorieName;

    public ViewHolder(LinearLayout v) {
        super(v);
        this.layout = v;
        v.setPadding(10,10,10,10);
        categorieName = new TextView(v.getContext());
        categorieName.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
        v.addView(categorieName);
    }
}

    // Provide a suitable constructor (depends on the kind of dataset)
    public PrixColorAdapter(PriceComparator[] myDataset , Activity activity) {
        list = myDataset;
        this.activity = activity;



    }


    // Create new views (invoked by the layout manager)
    @Override
    public PrixColorAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        LinearLayout v = new LinearLayout(parent.getContext());
        v.setOrientation(LinearLayout.HORIZONTAL);
        v.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        PrixColorAdapter.ViewHolder vh = new PrixColorAdapter.ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final PrixColorAdapter.ViewHolder holder, final int position) {
        //ici on mets a jour les info des composant du Holder
        holder.categorieName.setText(list[position].toString());
        holder.itemView.setBackgroundColor(ColorManager.getPriceColor(list[position]));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ColorPopup.showDialog(activity,new PriceComparatorColorPopupModule(list[position],holder.itemView));
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return list.length;
    }
}