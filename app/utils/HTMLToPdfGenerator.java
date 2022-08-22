package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
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
import play.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;

import static com.google.common.base.Throwables.getStackTraceAsString;
import static config.Constants.SOURCE_CODE_URL;

class HTMLToPdfGenerator {
    private String fontsPath = null;
    byte[] convertHtmlToPdf(String html) {
        byte[] pdf = null;
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            Pipeline pdfWriterPipeline = configurePdfWriterPipeline(document, writer);
            HtmlPipelineContext context = configureHtmlPipelineContext();
            Pipeline htmlPipeline = configureHtmlPipeline(context, pdfWriterPipeline);
            boolean defaultCss = true;
            CSSResolver resolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(defaultCss);
            Pipeline pipeline = configureCssResolverPipeline(resolver, htmlPipeline);

            XMLParser parser = new XMLParser(new XMLWorker(pipeline, true));
            parser.parse(new StringReader(html));

            document.addAuthor(SOURCE_CODE_URL + " licensed under the MIT License");
            document.addCreator("Please access " + SOURCE_CODE_URL + " for source codes of this service");

            document.close();

            pdf = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        } catch (DocumentException e) {
            Logger.error("Document Exception when getting PdfWriter: ", getStackTraceAsString(e));
        } catch (IOException e) {
            Logger.error("IOException when parsing HTML: ", getStackTraceAsString(e));
        }

        return pdf;
    }

    private Pipeline configurePdfWriterPipeline(Document document, PdfWriter pdfWriter) {
        return new PdfWriterPipeline(document, pdfWriter);
    }

    private Pipeline configureHtmlPipeline(HtmlPipelineContext context, Pipeline pipeline) {
        return new HtmlPipeline(context, pipeline);
    }

    private Pipeline configureCssResolverPipeline(CSSResolver resolver, Pipeline pipeline) {
        return new CssResolverPipeline(resolver, pipeline);
    }

    private HtmlPipelineContext configureHtmlPipelineContext() {
        return new HtmlPipelineContext(configureCssAppliers()).setTagFactory(Tags.getHtmlTagProcessorFactory())
            .setImageProvider(new Base64ImageProvider());
    }

    private CssAppliers configureCssAppliers() {
        if (this.fontsPath == null) {
            return null;
        } else {
            return new CssAppliersImpl(new XMLWorkerFontProvider(this.getClass()
                .getResource(this.fontsPath)
                .toString()));
        }
    }

}