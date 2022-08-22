package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class HeaderFooter extends PdfPageEventHelper {
    private int pagenumber;
    private int routeId;

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getRouteId() {
        return this.routeId;
    }

    public void setPageNumber(int pagenumber) {
        this.pagenumber = pagenumber;
    }

    public int getPageNumber() {
        return this.pagenumber;
    }

    public void onStartPage(PdfWriter writer, Document document) {
        pagenumber++;
    }

}