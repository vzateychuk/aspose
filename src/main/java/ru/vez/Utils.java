package ru.vez;

import com.aspose.pdf.Color;
import com.aspose.pdf.Document;
import com.aspose.pdf.FontRepository;
import com.aspose.pdf.FontStyles;
import com.aspose.pdf.Page;
import com.aspose.pdf.TextStamp;
import com.aspose.pdf.facades.FormattedText;

import java.util.List;

public class Utils {
    private static final float FONT_SIZE = 12.0F;

    static FormattedText createFormattedText(List<String> strings) {

        // instantiate FormattedText object with strings
        FormattedText text = new FormattedText();
        strings.forEach(text::addNewLineText);

        return text;
    }

    static Document addStampToDocumentPages(Document doc, TextStamp stamp) {

        // iterate through and add stamps to pages
        for (int i = 1; i < doc.getPages().size()+1; i++) {

            Page page = doc.getPages().get_Item(i);
            page.addStamp(stamp);
        }
        // return modified document
        return doc;
    }

    static TextStamp createTextStamp(FormattedText text) {

        TextStamp stamp = new TextStamp(text);

        // set whether stamp is background
        stamp.setBackground(true);

        // set horizontalAlignment
        // stamp.setHorizontalAlignment(horizontalAlignment);

        // set margins
        stamp.setLeftMargin(10);
        stamp.setRightMargin(10);

        // set text properties
        stamp.getTextState().setFont(FontRepository.findFont("Arial"));
        stamp.getTextState().setFontSize(FONT_SIZE);
        stamp.getTextState().setFontStyle(FontStyles.Bold);
        stamp.getTextState().setFontStyle(FontStyles.Italic);
        stamp.getTextState().setForegroundColor(Color.getBlue());

        return stamp;
    }
}
