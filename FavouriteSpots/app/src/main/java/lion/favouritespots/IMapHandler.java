package lion.favouritespots;

import android.widget.ArrayAdapter;

/**
 * Created by opris_000 on 4/1/2016.
 */
public interface IMapHandler {
    void setCamera(double lat, double lng, float zoom);

    void animateCamera(double lat, double lng, int duration);

    void animateCamera(double lat, double lng, float zoom, int duration);

    void clearMarkers();

    void removeMarker(OsmMarker markerToRemove);

    void addMarker(double lat, double lng, String text, boolean showInfoWindow);

    void setCurrentLocationMarker(double lat, double lng, String title);

    void setAdapter(ArrayAdapter<FancyPlace> in_adapter);

}