package com.example.hp.groomauto.Activitiy;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import static android.content.Context.MODE_PRIVATE;
/**
 * Created by Android-1 on 2/27/2018.
 */

public class TableBuilder {

    private static Context context;
    private static double totalAmount;

    public TableBuilder(Context context) {
        this.context = context;
    }

    // create table
    public static PdfPTable createTable() throws DocumentException {



        PdfPTable table = new PdfPTable(1);

        // set the width of the table to 100% of page
        table.setWidthPercentage(100);

        // set relative columns width
        table.setWidths(new float[]{1.0f});

        // ----------------Table Header "Title"----------------

        SharedPreferences prefs = context.getSharedPreferences("UserData", MODE_PRIVATE);

        String Username = prefs.getString("first_name", null) + " " + prefs.getString("last_name", null);
        String EmailID = prefs.getString("email", null);
        String UserPhone = prefs.getString("phone", null);


        Log.e("USerData","Name : "+Username);
        Log.e("USerData","Email : "+EmailID);

        Font font = new Font(Font.FontFamily.HELVETICA, 17, Font.BOLD, BaseColor.BLACK);
        // create header cell
        PdfPCell cell = new PdfPCell();
        // set Column span "1 cell = 6 cells width"
        cell.setColspan(1);
        // set style
        Style.headerCellStyle(cell);
        // add to table
        table.addCell(cell);

        //-----------------Table Cells Label/Value------------------

        table.addCell(createLabelCell("Name : "+Username));
        table.addCell(createLabelCell("Contact Number : "+UserPhone));
        table.addCell(createLabelCell("Email-ID : "+EmailID));

        return table;
    }

    // create cells
    private static PdfPCell createLabelCell(String text) {
        // font
        Font font = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD, BaseColor.BLACK);

        // create cell
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);

        // set style
        Style.labelCellStyle(cell);
        return cell;
    }

}

