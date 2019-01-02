package merejy.menuachat.ui.Button.OnClickListenerCreator;

import android.app.Activity;
import android.view.View;

public interface OnClickListenerCreator <T> {

      View.OnClickListener createListener (final T item, final Activity activity);
}
