package com.example.hp.groomauto.Activitiy;

/**
 * Created by Android-1 on 2/27/2018.
 */

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;

/**
 * Created by Dell on 25-01-2018.
 */

public class Style {

    public static void headerCellStyle(PdfPCell cell){

        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(1f);

        // background color
        cell.setBackgroundColor(new BaseColor(255, 255, 255));

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(0f);

    }
    public static void footerCellStyle(PdfPCell cell){

        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(7f);

        // background color
        cell.setBackgroundColor(new BaseColor(0,121,182));

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(2f);

    }
    public static void labelCellStyle(PdfPCell cell){
        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // padding
        cell.setPaddingLeft(3f);
        cell.setPaddingTop(0f);

        // background color
        cell.setBackgroundColor(BaseColor.WHITE);

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(0);
        cell.setBorderColorBottom(BaseColor.WHITE);

        // height
        cell.setMinimumHeight(18f);
    }


    public static void valueCellStyle(PdfPCell cell){
        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(5f);

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(0.5f);

        // height
        cell.setMinimumHeight(18f);
    }
    public static void valueGSTTOTAL(PdfPCell cell){
        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(5f);

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(0.5f);

        // height
        cell.setMinimumHeight(18f);
    }

    public static void invoiceCell(PdfPCell cell) {
        // alignment
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        // padding
        cell.setPaddingTop(0f);
        cell.setPaddingBottom(5f);

        BaseColor color = new BaseColor(33, 43, 90);
        cell.setBackgroundColor(color);

        // border
        cell.setBorder(0);
        cell.setBorderWidthBottom(0.5f);

        // height
        cell.setMinimumHeight(18f);
    }
}

