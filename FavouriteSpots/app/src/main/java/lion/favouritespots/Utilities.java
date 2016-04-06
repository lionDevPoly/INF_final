package lion.favouritespots;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by opris_000 on 4/1/2016.
 */
public class Utilities {

    public static String shuffleFileName(String prefix, String suffix) {
        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        return prefix + timeStamp + suffix;
    }

    public static void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

}