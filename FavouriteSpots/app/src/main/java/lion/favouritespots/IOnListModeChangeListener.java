package lion.favouritespots;

/**
 * Created by opris_000 on 4/1/2016.
 */
public interface IOnListModeChangeListener {
    int MODE_NORMAL = 0;
    int MODE_MULTI_SELECT = 1;

    void onListModeChange(int newMode);
}
