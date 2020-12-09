package ru.vez;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.DocumentInfo;
import com.aspose.pdf.Font;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.FontStyles;
import com.aspose.pdf.Page;
import com.aspose.pdf.PageInfo;
import com.aspose.pdf.TextFragment;
import com.aspose.pdf.TextStamp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static final String DIRECTOR_FILE = "/home/osboxes/tmp/director.txt";
    private static final Font FONT = FontRepository.findFont("Arial");
    private static final float FONT_SIZE = 12.0F;

    public static void main(String[] args) throws IOException {

        // Initialize document object
        Document doc = createDocumentWithPages(4);

        // create strings to be added to stamp
        List<String> directorStrings = Files.readAllLines(Paths.get(DIRECTOR_FILE));

        // add stamps to document
        Document docStamped = addStringStampsToDocument(doc, directorStrings);

        // save output document
        docStamped.save("TextStamp_output.pdf");
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
        for (int i = 0; i < 4; i++) {
            Page page = doc.getPages().add();
            PageInfo pageInfo = page.getPageInfo();
            // Add text to new page
            page.getParagraphs().add(new TextFragment("Hello page: " + i));
        }
        return doc;
    }

    private static Document addStringStampsToDocument(Document doc, List<String> strings) {
        // calculate position
        final double originX = 10;
        final double originY = 10;
        final float offset = 1.2f;

        // reverse original string
        List<String> reversed = new ArrayList<>(strings);
        Collections.reverse(reversed);

        // create stamps
        List<TextStamp> textStamps = reversed.stream()
            .map(Main::createTextStamp)
            .collect(Collectors.toList());

        // add stamps to particular page
        for (int i = 1; i < doc.getPages().size()+1; i++) {

            Page page = doc.getPages().get_Item(i);

            for (int j = 0; j < textStamps.size(); j++) {
                TextStamp stamp = textStamps.get(j);

                double yIntent = (FONT_SIZE + offset) * j + originY;
                stamp.setXIndent(originX);
                stamp.setYIndent(yIntent);

                page.addStamp(stamp);
            }

        }

        return doc;
    }

    private static TextStamp createTextStamp(String string) {

        TextStamp textStamp = new TextStamp(string);

        // set whether stamp is background
        textStamp.setBackground(true);

        // set origin
        textStamp.setXIndent(10);
        textStamp.setYIndent(10);

        // rotate stamp
        // textStamp.setRotate(Rotation.on90);
        // set text properties
        textStamp.getTextState().setFont(FontRepository.findFont("Arial"));
        textStamp.getTextState().setFontSize(FONT_SIZE);
        textStamp.getTextState().setFontStyle(FontStyles.Bold);
        textStamp.getTextState().setFontStyle(FontStyles.Italic);
        textStamp.getTextState().setForegroundColor(Color.getBlue());

        return textStamp;
    }

}
