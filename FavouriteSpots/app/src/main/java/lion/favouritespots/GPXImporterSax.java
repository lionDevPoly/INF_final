package lion.favouritespots;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by opris_000 on 4/1/2016.
 */
public class GPXImporterSax implements IImporter {
    @Override
    public List<FancyPlace> ReadFancyPlaces(String fileName) {

        File tmpFolder = new File(FancyPlacesApplication.TMP_FOLDER + File.separator + "import");
        tmpFolder.mkdirs();
        List<FancyPlace> result = new ArrayList<>();
        if (!Compression.unzip(fileName, tmpFolder.getAbsolutePath()))
            return result;

        result = ReadGXPFile(new File(tmpFolder.getAbsolutePath() + File.separator + "FancyPlaces.gpx"));


        Utilities.deleteRecursive(tmpFolder);

        return result;
    }



    List<FancyPlace> ReadGXPFile(File file)
    {
        List<FancyPlace> resultList = new ArrayList<>();

        try {
            System.setProperty("org.xml.sax.driver","org.xmlpull.v1.sax2.Driver");
            XMLReader xmlReader = XMLReaderFactory.createXMLReader();

            GpxFileContentHandler gpxFileContentHandler = new GpxFileContentHandler(file.getParent());
            xmlReader.setContentHandler(gpxFileContentHandler);

            FileReader fileReader = new FileReader(file);
            InputSource inputSource = new InputSource(fileReader);
            xmlReader.parse(inputSource);

            resultList = gpxFileContentHandler.getFancyPlaceList();
        } catch (SAXException | IOException ex) {
            ex.printStackTrace();
        }

        return resultList;
    }
}
