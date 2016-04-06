package lion.favouritespots;

import android.database.DataSetObserver;
import android.widget.ArrayAdapter;

import lion.favouritespots.R;
import lion.favouritespots.FancyPlace;
import lion.favouritespots.OsmMarker;

import org.osmdroid.api.IGeoPoint;
import org.osmdroid.api.IMapController;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by opris_000 on 4/1/2016.
 */
public class OsmMapHandler extends DataSetObserver implements IMapHandler, OsmMarker.IMarkerSelected {

    private IMapController curMapController = null;
    private MapView curMapView = null;
    private ArrayAdapter<FancyPlace> adapter = null;
    private OnFancyPlaceSelectedListener fancyPlaceSelectedCallback = null;
    private OsmMarker curLocationMarker = null;

    public OsmMapHandler(MapView mapView, OnFancyPlaceSelectedListener listener) {
        curMapView = mapView;
        curMapController = mapView.getController();
        fancyPlaceSelectedCallback = listener;
    }

    @Override
    public void setCamera(double lat, double lng, float zoom) {
        IGeoPoint pt = new GeoPoint(lat, lng);
        curMapController.setZoom((int) zoom);
        curMapController.setCenter(pt);
    }

    @Override
    public void animateCamera(double lat, double lng, int duration) {
        IGeoPoint pt = new GeoPoint(lat, lng);
        curMapController.setCenter(pt);

    }

    @Override
    public void animateCamera(double lat, double lng, float zoom, int duration) {
        IGeoPoint pt = new GeoPoint(lat, lng);
        curMapController.setCenter(pt);
        curMapController.setZoom((int) zoom);
    }

    @Override
    public void clearMarkers() {
        for (int i = 0; i < curMapView.getOverlays().size(); i++) {
            OsmMarker marker = (OsmMarker) curMapView.getOverlays().get(i);
            if (marker != null) {
                marker.setInfoWindowVisible(false);
            }
        }
        curMapView.getOverlays().clear();

    }

    @Override
    public void removeMarker(OsmMarker markerToRemove) {
        for (int i = 0; i < curMapView.getOverlays().size(); i++) {
            OsmMarker marker = (OsmMarker) curMapView.getOverlays().get(i);
            if (marker == markerToRemove) {
                marker.setInfoWindowVisible(false);
                curMapView.getOverlays().remove(marker);
                break;
            }
        }
    }

    protected OsmMarker createCurrentLocationMarker(GeoPoint pt, String text) {
        OsmMarker marker = new OsmMarker(curMapView.getContext(), curMapView);

        marker.setPosition(pt);
        marker.setIcon(curMapView.getContext().getResources().getDrawable(R.drawable.ic_my_location));
        marker.setAnchor(OsmMarker.ANCHOR_CENTER, OsmMarker.ANCHOR_CENTER);
        marker.setTitle(text);
        marker.setId(-1);
        marker.setInfoWindowVisible(false);

        return marker;
    }

    protected OsmMarker createMarker(GeoPoint pt, String text, boolean showInfoWindow, int id) {
        OsmMarker marker = new OsmMarker(curMapView.getContext(), curMapView);

        marker.setPosition(pt);
        marker.setIcon(curMapView.getContext().getResources().getDrawable(R.drawable.ic_pin));
        marker.setAnchor(OsmMarker.ANCHOR_CENTER, OsmMarker.ANCHOR_BOTTOM);
        marker.setTitle(text);
        marker.setId(id);
        marker.setInfoWindowVisible(showInfoWindow);
        marker.setMarkerSelectedListener(this);

        return marker;

    }

    @Override
    public void addMarker(double lat, double lng, String text, boolean showInfoWindow) {
        GeoPoint pt = new GeoPoint(lat, lng);

        addMarker(createMarker(pt, text, showInfoWindow, -1));
    }

    protected void addMarker(OsmMarker marker) {
        curMapView.getOverlays().add(marker);
        curMapView.invalidate();
    }

    @Override
    public void setCurrentLocationMarker(double lat, double lng, String title) {
        removeMarker(curLocationMarker);
        curLocationMarker = createCurrentLocationMarker(new GeoPoint(lat, lng), title);
        addMarker(curLocationMarker);
    }

    @Override
    public void onChanged() {
        super.onChanged();
        updateMarkersFromDataSource();
    }

    @Override
    public void onInvalidated() {
        super.onInvalidated();
        updateMarkersFromDataSource();
    }

    protected void updateMarkersFromDataSource() {
        clearMarkers();
        for (int i = 0; i < adapter.getCount(); i++) {
            FancyPlace fp = adapter.getItem(i);

            GeoPoint pt = new GeoPoint(Double.valueOf(fp.getLocationLat()), Double.valueOf(fp.getLocationLong()));
            addMarker(createMarker(pt, fp.getTitle(), false, i));
        }
    }

    @Override
    public void setAdapter(ArrayAdapter<FancyPlace> in_adapter) {
        adapter = in_adapter;
        adapter.registerDataSetObserver(this);

        updateMarkersFromDataSource();
    }

    @Override
    public void onMarkerSelected(int id) {
        if (fancyPlaceSelectedCallback != null)
            fancyPlaceSelectedCallback.onFancyPlaceSelected(id, OnFancyPlaceSelectedListener.INTENT_VIEW);
    }
}

