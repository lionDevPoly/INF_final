package lion.favouritespots;

import java.util.List;

/**
 * Created by opris_000 on 4/1/2016.
 */
public interface IImporter {
    List<FancyPlace> ReadFancyPlaces(String fileName);
}

