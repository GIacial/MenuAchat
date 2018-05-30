package merejy.menuachat.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.kernel.ColorManager;


public class ColorConfigurationactivity extends AbstractActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_preferences);

        Button comfirmer = findViewById(R.id.button_comfirmer);
        comfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent secondActivity = ToActivity.getIntentToGoTo(ColorConfigurationactivity.this,ColorConfigurationactivity.nextActivity);
                if(secondActivity != null){
                    secondActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(secondActivity);
                }
            }
        });
    }

    @Override
    ToActivity getActivityEnum() {
        return ToActivity.COLOR_CONFIGURATOR;
    }

    @Override
    protected void onStop() {
        super.onStop();
        ColorManager.save();
    }

    //static
    public static ToActivity nextActivity = null;
}
