package lion.favouritespots;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by opris_000 on 4/1/2016.
 */
public class FancyPlacesArrayAdapter extends ArrayAdapter<FancyPlace> implements IOnListModeChangeListener {

    private HashMap<FancyPlace, ListViewItem> listViewItems = new HashMap<>();

    public FancyPlacesArrayAdapter(Context context, int resourceId, List<FancyPlace> items) {
        super(context, resourceId, items);
    }

    @Override
    public void remove(FancyPlace object) {
        listViewItems.remove(object);
        super.remove(object);
    }

    public List<FancyPlace> getSelectedFancyPlaces() {
        ArrayList<FancyPlace> resultList = new ArrayList<>();

        for (Map.Entry<FancyPlace, ListViewItem> entry : listViewItems.entrySet()) {
            if (entry.getValue().isSelected())
                resultList.add(entry.getKey());
        }

        return resultList;
    }


    public View getView(final int position, View convertView, ViewGroup parent) {
        FancyPlace fancyPlace = getItem(position);

        LayoutInflater mInflater = (LayoutInflater)getContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        ListViewItemHolder holder = null;
        ListViewItem item = null;

        // if holder exists: re-use!
        if (convertView != null) {
            holder = (ListViewItemHolder) convertView.getTag();
        } else {
            convertView = mInflater.inflate(R.layout.list_item_fancy_place, null);
            holder = new ListViewItemHolder(convertView);
            convertView.setTag(holder);
        }

        // if listviewitem exists, re-use!
        if (listViewItems.containsKey(fancyPlace)) {
            item = listViewItems.get(fancyPlace);
            item.setHolder(holder);
        } else {
            item = new ListViewItem(getContext(), holder);
            listViewItems.put(fancyPlace, item);
        }

        item.setFancyPlace(fancyPlace);

        return convertView;
    }

    public void toggleSelected(int i)
    {
        listViewItems.get(getItem(i)).toggleAndAnimateSelected();
    }

    @Override
    public void onListModeChange(int newMode) {
        if (newMode == MODE_NORMAL)
        {
            for (Map.Entry<FancyPlace, ListViewItem> entry : listViewItems.entrySet()) {
                entry.getValue().setSelectable(false);
            }

        } else if (newMode == MODE_MULTI_SELECT)
        {
            for (Map.Entry<FancyPlace, ListViewItem> entry : listViewItems.entrySet()) {
                entry.getValue().setSelectable(true);
            }
        }
    }
}