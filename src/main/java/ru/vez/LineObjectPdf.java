package ru.vez;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.HorizontalAlignment;
import com.aspose.pdf.Page;
import com.aspose.pdf.PageInfo;
import com.aspose.pdf.TextStamp;
import com.aspose.pdf.VerticalAlignment;
import com.aspose.pdf.drawing.Graph;
import com.aspose.pdf.drawing.Line;
import com.aspose.pdf.facades.FormattedText;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class LineObjectPdf {
    private static final String DIRECTOR_FILE = "/home/osboxes/tmp/director.txt";

    public static void main(String[] args) throws IOException {
        // Create Document instance
        Document doc = createStampedDocFrom( Files.readAllLines(Paths.get(DIRECTOR_FILE)) );
        // save resultant PDF file
        doc.save("Line_Across_Page.pdf");
    }

    private static Document createStampedDocFrom(List<String> strings) {

        Document doc = new Document();

        // Add page to pages collection of PDF file
        Page page = doc.getPages().add();
        page.setPageSize(200, 100);

        // set page margin on all sides as 0
        page.getPageInfo().getMargin().setLeft(10);
        page.getPageInfo().getMargin().setRight(10);
        page.getPageInfo().getMargin().setBottom(10);
        page.getPageInfo().getMargin().setTop(10);

        // create Graph object with Width and Height equal to page dimensions
        PageInfo pageInfo = page.getPageInfo();
        float leftX = 0;
        float bottomY = 0;
        float rightX = (float) ( pageInfo.getWidth() - pageInfo.getMargin().getLeft() - pageInfo.getMargin().getRight() );
        float topY = (float) pageInfo.getPureHeight();

        Graph graph = new Graph(rightX, topY);

        List<Line> lines = List.of(
            new Line( new float[] { leftX, bottomY, leftX, topY }),
            new Line(new float[] { leftX, topY, rightX, topY }),
            new Line( new float[] { rightX, topY, rightX, bottomY }),
            new Line( new float[] { rightX, bottomY, leftX, bottomY })
        );

        lines.forEach( line -> {
            line.getGraphInfo().setColor( Color.getBlue() );
            graph.getShapes().add(line);
        } );

        // add Graph object to paragraphs collection of page
        page.getParagraphs().add(graph);

        // Add stamp to the pdf
        FormattedText directorText = Utils.createFormattedText( strings );
        TextStamp stamp = Utils.createTextStamp(directorText);
        stamp.setHorizontalAlignment(HorizontalAlignment.Center);
        stamp.setVerticalAlignment(VerticalAlignment.Top);
        stamp.setTopMargin(2*pageInfo.getMargin().getTop());
        Utils.addStampToDocumentPages(doc, stamp);

        return doc;
    }
}
