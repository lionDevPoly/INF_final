package lion.favouritespots;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by opris_000 on 3/31/2016.
 */
public class ListViewItemHolder {
    public ImageView thumbnailView;
    public TextView titleTextView;
    public LinearLayout backgroundLayoutView;
    public ListViewItemHolder(View v) {
        thumbnailView = (ImageView) v.findViewById(R.id.li_fp_thumbnail);
        titleTextView = (TextView) v.findViewById(R.id.li_fp_title);
        backgroundLayoutView = (LinearLayout) v.findViewById(R.id.li_background);
    }
}