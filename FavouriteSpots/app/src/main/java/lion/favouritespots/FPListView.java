package lion.favouritespots;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by opris_000 on 3/31/2016.
 */
public class FPListView extends TabItem {

    private OnFancyPlaceSelectedListener fancyPlaceSelectedCallback = null;
    private ListView fancyPlacesList = null;
    private MainWindow parent = null;
    private List<IOnListModeChangeListener> onListModeChangeListeners = new ArrayList<>();



    public static FPListView newInstance() {
        FPListView result = new FPListView();

        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fancy_places_list_view, container, false);

        // add places to list
        fancyPlacesList = (ListView) v.findViewById(R.id.fp_list_view);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fp_list_fab);
        fab.attachToListView(fancyPlacesList);

        // set on click listener
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fancyPlaceSelectedCallback.onFancyPlaceSelected(0, OnFancyPlaceSelectedListener.INTENT_CREATE_NEW);
            }
        });


        // set adapter
        fancyPlacesList.setAdapter(parent.fancyPlaceArrayAdapter);

        // add on mode change listener
        onListModeChangeListeners.add(parent.fancyPlaceArrayAdapter);
        changeListMode(IOnListModeChangeListener.MODE_NORMAL);

        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            fancyPlaceSelectedCallback = (MainWindow) activity;
            onListModeChangeListeners.add((MainWindow) activity);

            parent = (MainWindow) activity;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.fp_list_view_title);
    }

    public void setMultiSelectMode(int newMode) {
        changeListMode(newMode);
    }

    protected void changeListMode(int newMode) {
        if (newMode == IOnListModeChangeListener.MODE_NORMAL) {
            // add click listener
            fancyPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    fancyPlaceSelectedCallback.onFancyPlaceSelected(position, OnFancyPlaceSelectedListener.INTENT_VIEW);
                }
            });

            fancyPlacesList.setOnItemLongClickListener(
                    new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                            changeListMode(IOnListModeChangeListener.MODE_MULTI_SELECT);
                            parent.fancyPlaceArrayAdapter.toggleSelected(i);
                            return true;
                        }
                    });
        }
        if (newMode == IOnListModeChangeListener.MODE_MULTI_SELECT) {
            fancyPlacesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    parent.fancyPlaceArrayAdapter.toggleSelected(i);
                }
            });
            fancyPlacesList.setOnItemLongClickListener(null);
        }

        notifyListeners(newMode);
    }

    protected void notifyListeners(int newMode) {
        for (int i = 0; i < onListModeChangeListeners.size(); i++)
            onListModeChangeListeners.get(i).onListModeChange(newMode);
    }
}
