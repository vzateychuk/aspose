package ru.vez;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.Font;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.FontStyles;
import com.aspose.pdf.GraphInfo;
import com.aspose.pdf.HorizontalAlignment;
import com.aspose.pdf.MarginInfo;
import com.aspose.pdf.Page;
import com.aspose.pdf.PageInfo;
import com.aspose.pdf.TextStamp;
import com.aspose.pdf.VerticalAlignment;
import com.aspose.pdf.drawing.Graph;
import com.aspose.pdf.drawing.Rectangle;
import com.aspose.pdf.facades.FormattedText;

public class DrawRect {

    public static void main(String[] args) {
        float width = 300f;
        float height = 85f;
        float radius = 15f;

        Document doc = new Document();
        Page page = doc.getPages().add();
        page.setPageSize(width+2, height+2);
        PageInfo pageInfo = page.getPageInfo();

        // set page MARGIN on all sides as 5f
        pageInfo.setMargin(new MarginInfo(1, 1, 1, 1));

        Graph graph = new Graph(width, height);

        GraphInfo graphInfo = new GraphInfo();
        graphInfo.setColor(Color.getBlue());
        graphInfo.setLineWidth(1f);

        Rectangle rect = new Rectangle(0, 0, width, height);
        rect.setRoundedCornerRadius(radius);
        rect.setGraphInfo(graphInfo);

        graph.getShapes().add(rect);
/*
        FloatingBox box = new FloatingBox(left, top);
        box.getParagraphs().add(graph);
        page.getParagraphs().add(box);
*/
        page.getParagraphs().add(graph);

        FormattedText formattedText = new FormattedText(
            "Владимир Евегеньевич Затейчук" +
                System.lineSeparator() +
                "OOO \"Инновационные кадровые технологии\"" +
                System.lineSeparator() +
                "Будьте нашим кадром"
        );
        TextStamp stamp = createTextStamp(formattedText);
        page.addStamp(stamp);

        doc.save("document_with_rect.pdf");
    }

    private static TextStamp createTextStamp(FormattedText text) {

        final float FONT_SIZE = 12.0F;
        final float TOP_MARGIN = 25.0F;

        TextStamp stamp = new TextStamp(text);

        // set Font
        Font myFont = FontRepository.openFont("fonts/calibri.ttf");
        myFont.setEmbedded(true);
        stamp.getTextState().setFont(myFont);
        stamp.getTextState().setFontSize(FONT_SIZE);
        stamp.getTextState().setFontStyle(FontStyles.Bold);
        stamp.getTextState().setFontStyle(FontStyles.Italic);
        stamp.getTextState().setForegroundColor(Color.getBlue());
        stamp.setTextAlignment(HorizontalAlignment.Center);

        stamp.setHorizontalAlignment(HorizontalAlignment.Center);
        stamp.setVerticalAlignment(VerticalAlignment.Top);
        stamp.setTopMargin(TOP_MARGIN);

        return stamp;
    }

}
