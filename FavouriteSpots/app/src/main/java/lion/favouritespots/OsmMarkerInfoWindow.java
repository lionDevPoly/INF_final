package lion.favouritespots;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

import lion.favouritespots.R;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by opris_000 on 3/31/2016.
 */
public class OsmMarkerInfoWindow {

    protected View mView;
    protected boolean mIsVisible;
    protected MapView mMapView;



    private TextView titleView = null;

    public OsmMarkerInfoWindow(MapView mapView, OsmMarker parentMarker) {
        this.mMapView = mapView;
        this.mIsVisible = false;

        ViewGroup parent = (ViewGroup) mapView.getParent();
        LayoutInflater inflater = LayoutInflater.from(mapView.getContext());

        this.mView = inflater.inflate(R.layout.marker_fancy_place, parent, false);
        this.mView.setTag(this);


        titleView = (TextView) mView.findViewById(R.id.info_window_title);
        titleView.setOnClickListener(parentMarker);

    }


    public void setTitle(String title) {
        titleView.setText(title);
    }


    public void open(Object object, GeoPoint position, int offsetX, int offsetY) {
        this.close();
        MapView.LayoutParams lp = new MapView.LayoutParams(-2, -2, position, 8, offsetX, offsetY);
        this.mMapView.addView(this.mView, lp);
        this.mIsVisible = true;
    }

    public void close() {
        if(this.mIsVisible) {
            this.mIsVisible = false;
            ((ViewGroup)this.mView.getParent()).removeView(this.mView);
        }

    }

    public boolean isOpen() {
        return this.mIsVisible;
    }

    public static void closeAllInfoWindowsOn(MapView mapView) {
        ArrayList opened = getOpenedInfoWindowsOn(mapView);
        Iterator var3 = opened.iterator();

        while(var3.hasNext()) {
            OsmMarkerInfoWindow infoWindow = (OsmMarkerInfoWindow)var3.next();
            infoWindow.close();
        }

    }

    public static ArrayList<OsmMarkerInfoWindow> getOpenedInfoWindowsOn(MapView mapView) {
        int count = mapView.getChildCount();
        ArrayList<OsmMarkerInfoWindow> opened = new ArrayList<>(count);

        for(int i = 0; i < count; ++i) {
            View child = mapView.getChildAt(i);
            Object tag = child.getTag();
            if(tag != null && tag instanceof OsmMarkerInfoWindow) {
                OsmMarkerInfoWindow infoWindow = (OsmMarkerInfoWindow)tag;
                opened.add(infoWindow);
            }
        }

        return opened;
    }


}
