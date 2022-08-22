package utils;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import play.Logger;

import java.io.IOException;

import static com.google.common.base.Throwables.getStackTraceAsString;

public class Base64ImageProvider extends AbstractImageProvider {

    private final static String BASE_64_TOKEN = "base64,";

    @Override
    public Image retrieve(String src) {
        int pos = src.indexOf(BASE_64_TOKEN);
        try {
            if (src.startsWith("data") && pos > 0) {
                byte[] img = Base64.decode(src.substring(pos + BASE_64_TOKEN.length()));
                return Image.getInstance(img);
            } else {
                return Image.getInstance(src);
            }
        } catch (IOException | BadElementException e) {
            Logger.error("Error decoding html image for PDF generation: " + getStackTraceAsString(e));
            return null;
        }

    }

    @Override
    public String getImageRootPath() {
        return null;
    }
}