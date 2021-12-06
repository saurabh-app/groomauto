package com.example.hp.groomauto.Activitiy;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by maxgen1 on 2/28/2018.
 */

public class InvoceHeader {

    private static Context context;
    private static String bookingID;
    public InvoceHeader(Context context, String bookingId) {
        this.context = context;
        this.bookingID = bookingId;
    }

    // create table
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static PdfPTable createTable() throws DocumentException {


        PdfPTable table = new PdfPTable(2);

        // set the width of the table to 100% of page
        table.setWidthPercentage(100);

        // set relative columns width
        table.setWidths(new float[]{0.5f,0.5f});

        // ----------------Table Header "Title"----------------


        //document.add(new Paragraph(image+"GroomTech Enterprise"));


        Font font = new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD, BaseColor.WHITE);
        // create header cell
        PdfPCell cell = new PdfPCell();

        // set Column span "1 cell = 6 cells width"
        cell.setColspan(2);
        // set style
        Style.headerCellStyle(cell);
        // add to table
        table.addCell(cell);

        //-----------------Table Cells Label/Value------------------

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        table.addCell(createLabelCell("Invoice Number : "+bookingID));
        table.addCell(createLabelCell("Invoice Date : "+formattedDate));


        return table;
    }

    // create cells
    private static PdfPCell createLabelCell(String text) {
        // font
        Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.WHITE);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        // set style
        Style.invoiceCell(cell);
        return cell;
    }
}
