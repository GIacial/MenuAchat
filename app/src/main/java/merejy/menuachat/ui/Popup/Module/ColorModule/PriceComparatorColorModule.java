package merejy.menuachat.ui.Popup.Module.ColorModule;

import android.graphics.Color;
import android.view.View;

import merejy.menuachat.database.DataEnum.PriceComparator;
import merejy.menuachat.kernel.ColorManager;

public class PriceComparatorColorModule implements ColorModule {
    private PriceComparator priceComparator;
    private View itemView;

    public PriceComparatorColorModule (PriceComparator cat, View itemView){
        priceComparator = cat;
        this.itemView = itemView;
    }

    @Override
    public Runnable getMethodOnComfirm(final int red, final int green, final int blue) {
        return new Runnable() {
            @Override
            public void run() {
                ColorManager.setColor(priceComparator, Color.rgb(red,green,blue));
                itemView.setBackgroundColor(ColorManager.getPriceColor(priceComparator));
            }
        };
    }

    @Override
    public Runnable getMethodOnAnuller() {
        return null;
    }
}
