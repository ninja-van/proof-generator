package utils;

public class HtmlToPdfUtil {

    public static String getDefaultCssStyle() {
        String css = "";
        css += "body, td, p { font-family: arial unicode ms; }";
        css += "pre, tt, code, kbd, samp { font-family: arial unicode ms; font-size: 9pt; line-height: 12pt; }";
        css += "dt { margin: 0; }";
        css += "body { margin: 10%; font-size: 12pt; }";
        css += "p, dl, multicol { margin: 1em 0; }";
        css += "dd { margin-left: 40px; margin-bottom: 0; margin-right: 0; margin-top: 0; }";
        css += "blockquote, figure { margin: 1em 40px; }";
        css += "center { display: block; text-align: center; }";
        css += "blockquote[type='cite'] { border: 3px solid; padding-left: 1em; border-color: blue; border-width: thin; margin: 1em 0; }";
        css += "h1 { font-size: 2em; font-weight: bold; margin: 0.67em 0; }";
        css += "h2 { font-size: 1.5em; font-weight: bold; margin: 0.83em 0; }";
        css += "h3 { font-size: 1.17em; font-weight: bold; margin: 1em 0; }";
        css += "h4 { font-size: 1em; font-weight: bold; margin: 1.33em 0; }";
        css += "h5 { font-size: 0.83em; font-weight: bold; margin: 1.67em 0; }";
        css += "h6 { font-size: 0.67em; font-weight: bold; margin: 2.33em 0; }";
        css += "listing { font-size: medium; margin: 1em 0; white-space: pre; }";
        css += "xmp, pre, plaintext { margin: 1em 0; white-space: pre; }";
        css += "table { margin-bottom: 0; margin-top: 0; margin-left: 0; margin-right: 0; text-indent: 0; }";
        css += "caption { text-align: center; }";
        css += "tr { vertical-align: inherit; }";
        css += "tbody { vertical-align: middle; }";
        css += "thead { vertical-align: middle; }";
        css += "tfoot { vertical-align: middle; }";
        css += "table > tr { vertical-align: middle; }";
        css += "td { padding: 1px; text-align: inherit; vertical-align: inherit; }";
        css += "th { display: table-cell; font-weight: bold; padding: 1px; vertical-align: inherit; }";
        css += "sub { font-size: smaller; vertical-align: sub; }";
        css += "sup { font-size: smaller; vertical-align: super; }";
        css += "nobr { white-space: nowrap; }";
        css += "mark { background: none repeat scroll 0 0 yellow; color: black; }";
        css += "abbr[title], acronym[title] { border-bottom: 1px dotted; }";
        css += "ul, menu, dir { list-style-type: disc; }";
        css += "ul li ul { list-style-type: circle; }";
        css += "ol { list-style-type: decimal; }";
        css += "hr { color: gray; height: 2px; }";

        return css;
    }
}