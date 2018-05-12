package merejy.menuachat.ui.Activity;

import android.app.Activity;

import merejy.menuachat.database.Database;
import merejy.menuachat.kernel.Needing;

public class ActivitySaveOnClose extends Activity {

    @Override
    protected void onStop() {
        super.onStop();
        Database.save();
        Needing.save();
    }
}
