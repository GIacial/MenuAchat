package merejy.menuachat.ui.Button.OnClickListenerCreator;

import android.view.View;

import merejy.menuachat.ui.Activity.AbstractActivity;

public interface OnClickListenerCreator <T> {

      View.OnClickListener createListener (final T item, final AbstractActivity activity);
}
