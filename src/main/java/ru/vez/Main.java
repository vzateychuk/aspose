package ru.vez;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.DocumentInfo;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.FontStyles;
import com.aspose.pdf.HorizontalAlignment;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextStamp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Main {

    private static final String DIRECTOR_FILE = "/home/osboxes/tmp/director.txt";
    private static final String ENGINEER_FILE = "/home/osboxes/tmp/engineer.txt";
    private static final float FONT_SIZE = 12.0F;

    public static void main(String[] args) throws IOException {

        // Initialize document object
        Document doc = createDocumentWithPages(4);

        // add left stamps to document
        List<String> directorStrings = Files.readAllLines(Paths.get(DIRECTOR_FILE));
        Document directorStamped = Main.addStampsToDocument(doc, directorStrings, HorizontalAlignment.Left);

        // add right stamps to document
        List<String> engineerStrings = Files.readAllLines(Paths.get(ENGINEER_FILE));
        Document allStamped = Main.addStampsToDocument(directorStamped, engineerStrings, HorizontalAlignment.Right);

        // save output document
        allStamped.save("TextStamp_output.pdf");
    }

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

    private static Document addStampsToDocument(Document doc, List<String> strings, int horizontalAlignment) {

        // create stamps from List of strings
        List<TextStamp> textStamps = Main.createTextStampsAligned(strings, horizontalAlignment);

        // iterate through and add stamps to pages
        for (int i = 1; i < doc.getPages().size()+1; i++) {

            Page page = doc.getPages().get_Item(i);
            textStamps.forEach(page::addStamp);
        }
        // return modified document
        return doc;
    }

    private static List<TextStamp> createTextStampsAligned(List<String> strings, int horizontalAlignment) {

        // reverse original string
        List<String> reversedStrings = new ArrayList<>(strings);
        Collections.reverse(reversedStrings);

        // create list of stamps aligned by Y
        final float lineOffset = 1.2f;
        final float yOffset = 10;
        List<TextStamp> stamps = new ArrayList<>();
        for (int idx = 0; idx < reversedStrings.size(); idx++) {

            float yIntent = (FONT_SIZE + lineOffset) * idx + yOffset;
            TextStamp stamp = Main.createTextStampAligned(reversedStrings.get(idx), horizontalAlignment, yIntent);
            stamps.add(stamp);
        }

        return stamps;
    }

    private static TextStamp createTextStampAligned(String string, int horizontalAlignment, float yIntent) {

        TextStamp stamp = new TextStamp(string);

        // set whether stamp is background
        stamp.setBackground(true);

        // set horizontalAlignment
        stamp.setHorizontalAlignment(horizontalAlignment);

        // set margins
        stamp.setLeftMargin(10);
        stamp.setRightMargin(10);

        // set Y intent
        stamp.setYIndent(yIntent);

        // set text properties
        stamp.getTextState().setFont(FontRepository.findFont("Arial"));
        stamp.getTextState().setFontSize(FONT_SIZE);
        stamp.getTextState().setFontStyle(FontStyles.Bold);
        stamp.getTextState().setFontStyle(FontStyles.Italic);
        stamp.getTextState().setForegroundColor(Color.getBlue());

        return stamp;
    }

}
