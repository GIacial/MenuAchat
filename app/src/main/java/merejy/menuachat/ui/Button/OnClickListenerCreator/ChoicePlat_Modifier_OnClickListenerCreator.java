package merejy.menuachat.ui.Button.OnClickListenerCreator;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import merejy.menuachat.database.Plat;
import merejy.menuachat.ui.Activity.PlatCreator;
import merejy.menuachat.ui.Activity.ToActivity;

public class ChoicePlat_Modifier_OnClickListenerCreator implements OnClickListenerCreator<Plat> {
    @Override
    public View.OnClickListener createListener(final Plat item, final Activity activity) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlatCreator.setModifyMode(item);
                Intent secondeActivite =  ToActivity.getIntentToGoTo(activity,ToActivity.PLAT_CREATOR);
                if(secondeActivite != null){
                    activity.startActivity(secondeActivite);
                }
            }
        };
    }
}
