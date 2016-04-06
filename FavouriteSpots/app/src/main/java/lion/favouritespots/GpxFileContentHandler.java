package lion.favouritespots;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by opris_000 on 4/1/2016.
 */
public class GpxFileContentHandler implements ContentHandler {
    private String currentValue;
    private FancyPlace curFancyPlace;
    private List<FancyPlace> curFancyPlaceList;
    private boolean isInsideFPTag;
    private String baseDir;

    public GpxFileContentHandler(String _baseDir) {
        curFancyPlaceList = new ArrayList<>();
        isInsideFPTag = false;
        baseDir = _baseDir;
    }

    public List<FancyPlace> getFancyPlaceList()
    {
        return curFancyPlaceList;
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes atts) throws SAXException {

        if (localName.equalsIgnoreCase("wpt") && !isInsideFPTag) {
            curFancyPlace = new FancyPlace();
            curFancyPlace.setLocationLat(atts.getValue("lat").trim());
            curFancyPlace.setLocationLong(atts.getValue("lon").trim());
            isInsideFPTag = true;
        }

        if (localName.equalsIgnoreCase("link") && isInsideFPTag)
        {
            ImageFile tmpImg = new ImageFile(baseDir + File.separator + atts.getValue("href").trim().substring(5));
            String tmpImgFileName = FancyPlacesApplication.TMP_FOLDER + File.separator + Utilities.shuffleFileName("Img_", "");
            tmpImg.copy(tmpImgFileName);
            curFancyPlace.setImage(new ImageFile(tmpImgFileName));
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (localName.equalsIgnoreCase("name") && isInsideFPTag)
            curFancyPlace.setTitle(currentValue.trim());

        if (localName.equalsIgnoreCase("desc") && isInsideFPTag )
            curFancyPlace.setNotes(currentValue.trim());

        if (localName.equalsIgnoreCase("wpt") && isInsideFPTag)
        {
            curFancyPlaceList.add(curFancyPlace);
            isInsideFPTag = false;
        }

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        currentValue = new String(ch, start, length);
    }

    @Override
    public void startDocument() throws SAXException {
        // TODO Auto-generated method stub
    }

    @Override
    public void endDocument() throws SAXException {
        // TODO Auto-generated method stub
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        // TODO Auto-generated method stub
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length)
            throws SAXException {
        // TODO Auto-generated method stub
    }

    @Override
    public void processingInstruction(String target, String data)
            throws SAXException {
        // TODO Auto-generated method stub
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        // TODO Auto-generated method stub
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        // TODO Auto-generated method stub
    }

    @Override
    public void startPrefixMapping(String prefix, String uri)
            throws SAXException {
        // TODO Auto-generated method stub
    }

}
