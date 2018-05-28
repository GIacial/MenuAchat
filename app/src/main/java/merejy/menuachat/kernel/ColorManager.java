package merejy.menuachat.kernel;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import java.util.HashMap;

import merejy.menuachat.R;
import merejy.menuachat.database.DataEnum.PriceComparator;

public class ColorManager {

    static ColorManager colorManager = null;

    static public int getPriceColor(PriceComparator priceComparator){
        int color = 0;
        if(colorManager != null){
            color = colorManager.getPriceColorInst(priceComparator);
        }
        return color;
    }


    static public void load (Activity activity){
        if(colorManager == null){
            colorManager = new ColorManager(activity);
        }
    }

    //non static
    private HashMap<PriceComparator,Integer> priceColor = new HashMap<>();
    private boolean load = false;

    private ColorManager(){

    }

    private ColorManager(Activity activity){
        for (PriceComparator priceComparator : PriceComparator.values()){
            switch (priceComparator){
                case CHEAPEST: this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.goodPrice));
                break;
                case NO_PRICE:this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.noPrice));
                    break;
                case EXPANSIVE:this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.badPrice));
                    break;
                default:this.priceColor.put(priceComparator,activity.getResources().getColor(R.color.normalPrice));
                    break;
            }
        }
    }

    public int getPriceColorInst(PriceComparator priceComparator){
        return this.priceColor.get(priceComparator);
    }
}
