package merejy.menuachat.ui.Popup;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import merejy.menuachat.R;
import merejy.menuachat.ui.Popup.Module.ColorModule.ColorModule;

public class ColorPopup extends DialogFragment {


    static ColorModule module;

    static public void showDialog(Activity a , ColorModule module){
        ColorPopup.module = module;
        new ColorPopup().show(a.getFragmentManager(),"colorDialog");
    }



    private int rouge = 0;
    private int vert = 0;
    private  int bleu = 0;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View layout = inflater.inflate(R.layout.popup_color, null);
        builder.setView(layout);

        //get les object du layout
        final SeekBar red = layout.findViewById(R.id.redBar);
        final SeekBar green = layout.findViewById(R.id.greenBar);
        final SeekBar blue = layout.findViewById(R.id.blueBar);
        final TextView color = layout.findViewById(R.id.colorView);

        SeekBar.OnSeekBarChangeListener colorListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(red.equals(seekBar)){
                    rouge = progress;
                }
                else if(blue.equals(seekBar)){
                    bleu = progress;
                }
                else if(green.equals(seekBar)){
                    vert = progress;
                }
                color.setBackgroundColor(Color.rgb(rouge,vert,bleu));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };

        red.setOnSeekBarChangeListener(colorListener);
        green.setOnSeekBarChangeListener(colorListener);
        blue.setOnSeekBarChangeListener(colorListener);




        builder.setPositiveButton(R.string.text_confirmation, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //code de comfirmation
                Runnable method = module.getMethodOnComfirm(rouge,vert,bleu);
                if(method != null){
                    method.run();
                }
            }
        })
                .setNegativeButton(R.string.text_anullation, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Runnable method = module.getMethodOnAnuller();
                        if(method != null){
                            method.run();
                        }
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();
    }
}