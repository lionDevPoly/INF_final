package lion.favouritespots;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by opris_000 on 3/31/2016.
 */
public abstract class TabItem extends Fragment {
    public abstract String getTitle(Context context);
}
