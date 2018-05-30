package merejy.menuachat.ui.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import merejy.menuachat.R;
import merejy.menuachat.kernel.ColorManager;

abstract public class AbstractActivity extends AppCompatActivity {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        ColorManager.load(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /**
     * Gestion du menu pour passer d'une activité à une autre.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(getActivityEnum() != ToActivity.COLOR_CONFIGURATOR){
            ColorConfigurationactivity.nextActivity = this.getActivityEnum();
            Intent home = null;
            switch (item.getItemId()) {
                case R.id.colorCongigurateur:
                    home = ToActivity.getIntentToGoTo(this,ToActivity.COLOR_CONFIGURATOR);
                    break;
                case R.id.magasinSelector:
                    home = ToActivity.getIntentToGoTo(this,ToActivity.MAGASIN_CHOICE);
                    break;

                default:
                    Toast.makeText(this,"Menu Inutile",Toast.LENGTH_SHORT).show();
                    break;

            }
            if( home != null){
                home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(home);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    abstract ToActivity getActivityEnum();
}
