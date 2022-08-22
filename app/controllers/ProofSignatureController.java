package controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.CssAppliers;
import com.itextpdf.tool.xml.html.CssAppliersImpl;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import helpers.ResultWrapper;
import lombok.SneakyThrows;
import models.ProofSignature;
import models.ProofSignatureRequest;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;
import utils.Base64ImageProvider;
import utils.HeaderFooter;
import utils.HtmlToPdfUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import static config.Constants.SOURCE_CODE_URL;
import static play.mvc.Controller.request;
import static play.mvc.Http.Context.Implicit.response;

public class ProofSignatureController {

    public Result getProofSignatures() {
        String json = request().body().asJson().toString();
        ObjectMapper objectMapper = new ObjectMapper();

        ProofSignatureRequest request;
        try {
            request = objectMapper.readerFor(ProofSignatureRequest.class).readValue(json);
        } catch (IOException e) {
            return Results.status(500);
        }

        ResultWrapper result = generateProofSignatures(request.getProofsSignatures());

        response().setHeader("Content-Disposition", "inline; filename=proofs.pdf");

        Result playResult = Results.status(Http.Status.OK, (byte[]) result.getResult());
        return playResult.as("application/pdf");
    }

    @SneakyThrows
    private ResultWrapper generateProofSignatures(List<ProofSignature> proofs) {
        Document document = new Document(PageSize.A4.rotate(), 35, 35, 35, 25);
        document.setMarginMirroringTopBottom(true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, byteArrayOutputStream);
        HeaderFooter event = new HeaderFooter();
        pdfWriter.setBoxSize("footer", new com.itextpdf.text.Rectangle(36, 44, 809, 788));
        pdfWriter.setPageEvent(event);
        document.open();

        // CSS
        CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

        // HTML
        XMLWorkerFontProvider fontProvider = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
        fontProvider.register(new File("public/fonts/NotoSans-Black.ttf").toString());
        CssAppliers cssAppliers = new CssAppliersImpl(fontProvider);
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(cssAppliers);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
        htmlContext.setImageProvider(new Base64ImageProvider());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, pdfWriter);
        HtmlPipeline html2 = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html2);

        // generate html

        String html = buildHtmlPodsTable(proofs);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(new ByteArrayInputStream(html.getBytes()));
        event.setPageNumber(1);

        document.addAuthor(SOURCE_CODE_URL + " licensed under the MIT License");
        document.addCreator("Please access " + SOURCE_CODE_URL + " for source codes of this service");

        document.close();

        return new ResultWrapper<>(byteArrayOutputStream.toByteArray());

    }

    private String buildHtmlPodsTable(List<ProofSignature> proofs) {
        String html = "<html><body>";

        // styling
        html += "<style>";
        html += HtmlToPdfUtil.getDefaultCssStyle();
        html += ".text_right { text-align: right; }";
        html += ".text_center { text-align: center; }";
        html += ".border { border: 1px solid #000; }";
        html += ".bold { font-weight: bold; }";

        html += ".resup_driver_list_table td { font-size: 8px; border: 1px solid #000; word-break: break-all;}";
        html += ".resup_driver_list_table tr.header td.bold { border-bottom: 1px solid; font-weight: bold; }";
        html += "</style>";

        html += "<div style='height: 10px;'>&nbsp;</div>";
        html += "<table cellspacing='0' cellpadding='8' style='width: 100%;' class='resup_driver_list_table text_center'>";
        html += "<tr class='header'>";
        html += "<td class='bold' style='width: 10%;'>Tracking Id</td>";
        html += "<td class='bold' style='width: 10%;'>Status</td>";
        html += "<td class='bold' style='width: 8%;'>POD Time</td>";
        html += "<td class='bold' style='width: 8%;'>Start Date/Time</td>";
        html += "<td class='bold' style='width: 8%;'>End Date/Time</td>";
        html += "<td class='bold' style='width: 8%;'>Route Id</td>";
        html += "<td class='bold' style='width: 8%;'>Driver</td>";
        html += "<td class='bold' style='width: 8%;'>From Name</td>";
        html += "<td class='bold' style='width: 8%;'>From Email</td>";
        html += "<td class='bold' style='width: 8%;'>Recipient Name</td>";
        html += "<td class='bold' style='width: 10%;'>Verification Method</td>";
        html += "<td class='bold' style='width: 10%;'>Signature (Image)</td>";

        html += "</tr>";

        StringBuilder htmlTableEntriesBuilder = new StringBuilder();

        for (ProofSignature proof : proofs) {
            htmlTableEntriesBuilder.append(getHtmlPodsTableEntry(proof));
        }

        html += htmlTableEntriesBuilder.toString();

        html += "</table>";

        html += "<div style='font-size:12px;'>Source codes of this service can be accessed at " + SOURCE_CODE_URL + " and may be licensed pursuant to open source MIT license.</div>";

        html += "<div style='page-break-after:always'></div>";
        html += "</body></html>";
        return html;
    }

    private String getHtmlPodsTableEntry(ProofSignature proof) {
        String html = "<tr>";
        html += "<td>" + (proof.getTrackingId() == null ? "-" : proof.getTrackingId()) + "</td>";
        html += "<td>" + (proof.getStatus() == null ? "-" : proof.getStatus()) + "</td>";
        html += "<td>" + (proof.getProofDate() == null ? "" : proof.getProofDate()) + "</td>";
        html += "<td>" + (proof.getDeliveryStart() == null ? "-" : proof.getDeliveryStart()) + "</td>";
        html += "<td>" + (proof.getDeliveryEnd() == null ? "-" : proof.getDeliveryEnd()) + "</td>";
        html += "<td>" + (proof.getRouteId() == null ? "-" : proof.getRouteId()) + "</td>";
        html += "<td>" + (proof.getDriverName() == null ? "-" : proof.getDriverName()) + "</td>";
        html += "<td>" + (proof.getSentFrom() == null ? "-" : proof.getSentFrom()) + "</td>";
        html += "<td>" + (proof.getSentFromEmail() == null ? "-" : proof.getSentFromEmail()) + "</td>";
        html += "<td>" + (proof.getConsigneeName() == null ? "-" : proof.getConsigneeName()) + "</td>";
        html += "<td>" + (proof.getVerificationMethod() == null ? "-" : proof.getVerificationMethod()) + "</td>";
        html += "<td>" + (proof.getSignatureUrl() == null ? "-" : "<img src='" + proof.getSignatureUrl() + "' />") + "</td>";
        html += "</tr>";

        return html;
    }

}
