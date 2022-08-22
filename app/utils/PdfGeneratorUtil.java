package utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import models.Proof;

import java.text.DecimalFormat;
import java.util.HashMap;

import static config.Constants.SOURCE_CODE_URL;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PdfGeneratorUtil {

    public static byte[] generateDeliveryReportPdf(Proof proof) {
        HashMap<String, String> resourcesHashMap = getLanguageResources(proof.getCountry());
        StringBuilder html = new StringBuilder();

        html.append(" " +
            "<style>\n" +
            "     h1 { font-size: 25px; font-weight: normal }\n" +
            "     h3, .h3 { letter-spacing: -0.5px; font-size: 15px; font-weight: normal; color: #BA0C2F }\n" +
            "     td { font-size: 12px; font-family: arialuni; vertical-align: top;}\n" +
            "     .text_center { text-align: center }\n" +
            "     .border_bottom { border-bottom: 0.5px solid #BA0C2F }\n" +
            " </style>\n" +
            "\n" +
            " <table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"width: 100%; margin:0px; background-color: #BA0C2F; color: #fff\">\n" +
            "     <tr><td colspan=\"3\" style=\"height: 10px\"></td></tr>\n" +
            "     <tr>\n" +
            "         <td style=\"width: 3%\"></td>");
        html.append("" +
            "<td style=\"width: 97%; font-family: ubuntub; font-size: 50px; line-height: 47px\">\n" +
            "    " + proof.getCompanyName() + "\n" +
            "</td>");

        html.append("" +
            "    </tr>\n" +
            "    <tr>\n" +
            "        <td style=\"width: 3%\"></td>\n" +
            "        <td style=\"width: 97%; letter-spacing: -0.5px; font-size: 25px; line-height: 22px\">\n" +
            "            " + resourcesHashMap.get("delivery_report") + "\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "    <tr><td colspan=\"3\" style=\"height: 16px\"></td></tr>\n" +
            "</table>\n" +
            "\n" +
            "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"width: 100%;margin:0px; padding: 0\"><tr><td style=\"width: 3%\"></td><td style=\"width: 94%\">\n" +
            "\n" +
            "<table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"width: 100%;margin:0px\">\n" +
            "    <tr>\n" +
            "        <td>\n" +
            "            <div style=\"font-size: 14px\"></div>\n" +
            "            <table cellspacing=\"0\" cellpadding=\"5\" border=\"0\" style=\"width: 100%;margin:0px\">\n" +
            "                <tr>\n" +
            "                    <td style=\"width: 50%\">\n" +
            "                        <h3>" + resourcesHashMap.get("proof_of_delivery") + "</h3>\n" +
            "                        <div style=\"line-height: 12px\">" + resourcesHashMap.get("delivered_on") + " - " + proof.getDeliveredOn() + "</div>\n" +
            "                        <div style=\"line-height: 12px\">" + resourcesHashMap.get("delivered_by") + " - " + proof.getDeliveredBy() + "</div>");


        if (proof.getDestinationLat() != null && proof.getDestinationLng() != null) {
            html.append("" +
                "<div style=\"line-height: 12px\">" + resourcesHashMap.get("latitude") + " - " + proof.getDestinationLat() + "</div>\n" +
                "<div style=\"line-height: 12px\">" + resourcesHashMap.get("longitude") + " - " + proof.getDestinationLng() + "</div>");
        }

        html.append("<div style=\"line-height: 12px;\">" + resourcesHashMap.get("no_of_parcels") + " - " + proof.getNumberOfParcels() + "</div>");

        if (proof.getAmountPaid() != null) {
            html.append("" +
                "<div style=\"line-height: 12px\">" + resourcesHashMap.get("amount_paid") + " - " + proof.getAmountPaid() + "</div>\n");
        }
        html.append("" +
            "</td>\n" +
            "<td style=\"width: 30%\">\n" +
            "    <h3>" + resourcesHashMap.get("delivery_to") + "</h3>\n" +
            "    <div style=\"line-height: 12px\">" + proof.getDeliveredTo() + "</div>\n" +
            "    <div style=\"line-height: 12px\">" + proof.getDeliveredWhere() + "</div>\n");

        html.append("</td>\n" +
            "<td style=\"width: 20%\">");
        if (proof.getPlacedAt() != null) {
            html.append("" +
                "<h3>" + resourcesHashMap.get("placed_at_location") + "</h3>\n" +
                "<div style=\"line-height: 12px\">" + proof.getPlacedAt() + "</div>\n" +
                "<div style=\"line-height: 12px\">" + proof.getDriverComments() + "</div>");
        } else if (proof.getImageUrl() != null) {
            if (proof.getRecievedBy() == null) {
                html.append("  " +
                    "<h3>" + resourcesHashMap.get("received_by") + "</h3>\n" +
                    (proof.getRecievedBy() != null ? "<div style=\"line-height: 12px;\">" + proof.getRecievedBy() + "</div>" : ""));
            }
            html.append("<h3>" + resourcesHashMap.get("signature") + "</h3><div class=\"text_center\"><img src=\"" + proof.getImageUrl() + "\" style=\"height: 87px;\" /></div>");
        }

        html.append("" +
            "                </td>\n" +
            "            </tr>\n" +
            "        </table>\n" +
            "    </td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "    <td>\n" +
            "        <div style=\"font-size: 12px\"></div>\n" +
            "        <table cellspacing=\"0\" cellpadding=\"10\" border=\"0\" style=\"width: 100%;margin:0px\">\n" +
            "            <tr>\n" +
            "                <td class=\"h3\" style=\"width: 80%; line-height: 5px\">" + resourcesHashMap.get("delivered_from") + "</td>\n" +
            "                <td class=\"h3\" style=\"width: 20%; line-height: 5px\">" + resourcesHashMap.get("tracking_id") + "</td>\n" +
            "            </tr>");

        proof.getOrders().forEach(order -> {
            html.append("" +
                "<tr>\n" +
                "    <td class=\"border_bottom\">\n" +
                order.getShipperName() +
                "    </td>\n" +
                "    <td class=\"border_bottom\">" + order.getTrackingId() + "</td>\n" +
                "</tr>");
        });

        html.append("            </table>\n" +
            "        </td>\n" +
            "    </tr>\n" +
            "</table>\n" +
            "\n" +
            "</td><td style=\"width: 3%\"></td></tr></table>");

        html.append("<br/><br/><div style='font-size:12px;'>Source codes of this service can be accessed at " + SOURCE_CODE_URL + " and may be licensed pursuant to open source MIT license.</div>");

        HTMLToPdfGenerator htmlToPdfGenerator = new HTMLToPdfGenerator();
        return htmlToPdfGenerator.convertHtmlToPdf(html.toString());
    }

    private static HashMap<String, String> getLanguageResources(String country) {
        HashMap<String, String> resourcesHashMap = new HashMap<>();
        switch (country.toUpperCase()) {
            case "ID":
                resourcesHashMap.put("received_by", "Diterima Oleh");
                resourcesHashMap.put("signature", "Tanda Tangan");
                resourcesHashMap.put("placed_at_location", "Ditaruh di tempat");
                resourcesHashMap.put("amount_paid", "Jumlah Dibayar");
                resourcesHashMap.put("proof_of_delivery", "POD");
                resourcesHashMap.put("delivered_on", "Diantar Pada");
                resourcesHashMap.put("delivered_by", "Diantar Oleh");
                resourcesHashMap.put("latitude", "Latitude");
                resourcesHashMap.put("longitude", "Longitude");
                resourcesHashMap.put("no_of_parcels", "Jumlah Paket");
                resourcesHashMap.put("delivery_to", "Diantar Ke");
                resourcesHashMap.put("delivered_from", "Diantar Dari");
                resourcesHashMap.put("tracking_id", "Tracking ID");
                resourcesHashMap.put("delivery_report", "Laporan Pengantaran");
                break;

            case "TH":
                resourcesHashMap.put("received_by", "รับโดย");
                resourcesHashMap.put("signature", "ลายเซ็น");
                resourcesHashMap.put("placed_at_location", "วางในสถานที่");
                resourcesHashMap.put("amount_paid", "จำนวนเงินที่จ่าย");
                resourcesHashMap.put("proof_of_delivery", "หลักฐานของการจัดส่ง");
                resourcesHashMap.put("delivered_on", "วันที่ส่งสินค้า");
                resourcesHashMap.put("delivered_by", "นำส่งโดย");
                resourcesHashMap.put("latitude", "ละติจูด");
                resourcesHashMap.put("longitude", "ลองติจูด");
                resourcesHashMap.put("no_of_parcels", "จำนวนพัสดุ");
                resourcesHashMap.put("delivery_to", "นำส่งถึง");
                resourcesHashMap.put("delivered_from", "ส่งมาจาก");
                resourcesHashMap.put("tracking_id", "รหัสติดตามพัสดุ");
                resourcesHashMap.put("delivery_report", "รายงานการส่งสินค้า");
                break;

            case "VN":
                resourcesHashMap.put("received_by", "Được nhận bởi");
                resourcesHashMap.put("signature", "Chữ ký");
                resourcesHashMap.put("placed_at_location", "Đặt lại ở vị trí giao hàng");
                resourcesHashMap.put("amount_paid", "Số tiền đã trả");
                resourcesHashMap.put("proof_of_delivery", "Bằng chứng giao hàng");
                resourcesHashMap.put("delivered_on", "Được giao vào lúc");
                resourcesHashMap.put("delivered_by", "Được giao bởi");
                resourcesHashMap.put("latitude", "Vĩ độ");
                resourcesHashMap.put("longitude", "Kinh độ");
                resourcesHashMap.put("no_of_parcels", "Số bưu kiện");
                resourcesHashMap.put("delivery_to", "Được chuyển tới");
                resourcesHashMap.put("delivered_from", "Được giao từ");
                resourcesHashMap.put("tracking_id", "Mã tra cứu");
                resourcesHashMap.put("delivery_report", "Báo cáo giao hàng");
                break;

            default:
                resourcesHashMap.put("received_by", "Received by");
                resourcesHashMap.put("signature", "Signature");
                resourcesHashMap.put("placed_at_location", "Placed at Location");
                resourcesHashMap.put("amount_paid", "Amount Paid");
                resourcesHashMap.put("proof_of_delivery", "Proof of Delivery");
                resourcesHashMap.put("delivered_on", "Delivered On");
                resourcesHashMap.put("delivered_by", "Delivered By");
                resourcesHashMap.put("latitude", "Latitude");
                resourcesHashMap.put("longitude", "Longtitude");
                resourcesHashMap.put("no_of_parcels", "Number of Parcels");
                resourcesHashMap.put("delivery_to", "Delivery To");
                resourcesHashMap.put("delivered_from", "Delivered From");
                resourcesHashMap.put("tracking_id", "Tracking ID");
                resourcesHashMap.put("delivery_report", "Delivery Report");
                break;
        }
        return resourcesHashMap;

    }

}