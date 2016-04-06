package lion.favouritespots;

import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.os.Environment;

import java.io.File;

/**
 * Created by opris_000 on 3/31/2016.
 */

public class FancyPlacesApplication extends Application {
    public static final int TARGET_PIX_SIZE = 1000;
    public static final String TMP_IMAGE_FILENAME = "tmpFancyPlacesImg.png";
    static public final int MAP_DEFAULT_ZOOM_NEAR = 16;
    static public final int MAP_DEFAULT_ZOOM_FAR = 13;
    static public final int MAP_DEFAULT_DURATION = 3000;

    public static String TMP_IMAGE_FULL_PATH = "";
    public static String TMP_FOLDER ="";
    public static String EXTERNAL_EXPORT_DIR = "";
    private static LocationHandler locationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();

        // tmp file dir
        TMP_FOLDER = getExternalCacheDir().getAbsolutePath();
        TMP_IMAGE_FULL_PATH = TMP_FOLDER + File.separator + lion.favouritespots.FancyPlacesApplication.TMP_IMAGE_FILENAME;

        // external export dir
        EXTERNAL_EXPORT_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + getResources().getString(R.string.app_name) + File.separator;
        (new File(EXTERNAL_EXPORT_DIR)).mkdirs();

        // attach lifecycle callbacks to location handler
        locationHandler = new LocationHandler((LocationManager) getSystemService(Context.LOCATION_SERVICE));
        registerActivityLifecycleCallbacks(locationHandler);
    }

    public LocationHandler getLocationHandler() {
        return locationHandler;
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
