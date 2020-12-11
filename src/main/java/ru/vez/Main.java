package ru.vez;

import com.aspose.pdf.Document;
import com.aspose.pdf.DocumentInfo;
import com.aspose.pdf.HorizontalAlignment;
import com.aspose.pdf.Page;
import com.aspose.pdf.PdfPageStamp;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextStamp;
import com.aspose.pdf.facades.FormattedText;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class Main {

    private static final String DIRECTOR_FILE = "/home/osboxes/tmp/director.txt";
    private static final String ENGINEER_FILE = "/home/osboxes/tmp/engineer.txt";

    public static void main(String[] args) throws IOException {

        // Initialize document object
        Document doc = createDocumentWithPages(4);

        // add left stamps to document
        FormattedText directorText = Utils.createFormattedText( Files.readAllLines(Paths.get(DIRECTOR_FILE)) );
        TextStamp directorStamp = Utils.createTextStamp(directorText);
        directorStamp.setHorizontalAlignment(HorizontalAlignment.Left);
        Document directorStampedDoc = Utils.addStampToDocumentPages(doc, directorStamp);

        // add right stamps to document
        FormattedText engineerText = Utils.createFormattedText( Files.readAllLines(Paths.get(ENGINEER_FILE)) );
        TextStamp engineerStamp = Utils.createTextStamp(engineerText);
        engineerStamp.setHorizontalAlignment(HorizontalAlignment.Right);
        Document engineerStampedDoc = Utils.addStampToDocumentPages(directorStampedDoc, engineerStamp);

        Document stampPdfDoc = new Document("Line_Across_Page.pdf");
        Document allStampedDoc = Main.addPdfStampToDoc(engineerStampedDoc, stampPdfDoc);

        // save output document
        allStampedDoc.save("TextStamp_output.pdf");
    }

    private static Document addPdfStampToDoc(Document doc, Document stampPdf) {
        PdfPageStamp pageStamp = new PdfPageStamp(stampPdf.getPages().get_Item(1));

        // iterate through and add stamps to pages
        for (int i = 1; i < doc.getPages().size()+1; i++) {

            Page page = doc.getPages().get_Item(i);
            page.addStamp(pageStamp);
        }
        // return modified document
        return doc;
    }

    // region Private

    private static Document createDocumentWithPages(int pageAmount) {
        Document doc = new Document();
        DocumentInfo docInfo = doc.getInfo();
        docInfo.setAuthor("vzateychuk");
        docInfo.setCreationDate(new Date());
        docInfo.setKeywords("vovka");
        docInfo.setSubject("test");
        docInfo.setTitle("vzateychuk title");

        //Add page
        for (int i = 0; i < pageAmount; i++) {
            Page page = doc.getPages().add();
            // Add text to new page
            page.getParagraphs().add(new TextFragment("Hello page: " + i));
            page.getParagraphs().add(new TextFragment("// For complete examples and data files, " +
                "please go to https://github.com/aspose-pdf/Aspose.Pdf-for-Java\n" +
                "// Create Document instance\n" +
                "Document doc = new Document();\n" +
                "// Add page to pages collection of PDF file\n" +
                "Page page = doc.getPages().add();\n" +
                "// set page margin on all sides as 0\n" +
                "page.getPageInfo().getMargin().setLeft(0);\n" +
                "page.getPageInfo().getMargin().setRight(0);\n" +
                "page.getPageInfo().getMargin().setBottom(0);\n" +
                "page.getPageInfo().getMargin().setTop(0);\n" +
                "// create Graph object with Width and Height equal to page dimensions\n" +
                "Graph graph = new Graph((float) page.getPageInfo().getWidth(), (float) page.getPageInfo().getHeight());\n" +
                "// create first line object starting from Lower-Left to Top-Right corner of page\n" +
                "Line line = new Line(new float[] { (float) page.getRect().getLLX(), 0, (float) page.getPageInfo().getWidth(), (float) page.getRect().getURY() });\n" +
                "// add line to shapes collection of Graph object\n" +
                "graph.getShapes().add(line);\n" +
                "// draw line from Top-Left corner of page to Bottom-Right corner of page\n" +
                "Line line2 = new Line(new float[] { 0, (float) page.getRect().getURY(), (float) page.getPageInfo().getWidth(), (float) page.getRect().getLLX() });\n" +
                "// add line to shapes collection of Graph object\n" +
                "graph.getShapes().add(line2);\n" +
                "// add Graph object to paragraphs collection of page\n" +
                "page.getParagraphs().add(graph);\n" +
                "// save resultant PDF file\n" +
                "doc.save(\"Line_Across_Page.pdf\");"));
        }
        return doc;
    }

    // endregion
}
