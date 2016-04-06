package lion.favouritespots;

import java.util.List;

/**
 * Created by opris_000 on 4/1/2016.
 */
public interface IExporter {
    boolean WriteToFile(List<FancyPlace> fpList, String fileNameWithExt);

    boolean WriteToFile(FancyPlace fancyPlace, String fileNameWithExt);
}

