package com.jiinfotech.restomanager.utils;

import org.springframework.stereotype.Service;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class BillGenerateServices {

    public ByteArrayInputStream generateAttractiveBill(String restaurantName, String address, String phone, String website, String[] items, Double[] prices, double total) {
        Document document = new Document(PageSize.A4);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Fonts (using simple default font suitable for thermal printers)
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);

            // Add Restaurant Name
            Paragraph restaurantTitle = new Paragraph(restaurantName, titleFont);
            restaurantTitle.setAlignment(Element.ALIGN_CENTER);
            document.add(restaurantTitle);

            // Add Address and Contact Information
            Paragraph restaurantAddress = new Paragraph(address, bodyFont);
            restaurantAddress.setAlignment(Element.ALIGN_CENTER);
            document.add(restaurantAddress);

            Paragraph contactInfo = new Paragraph("Phone: " + phone + " | Website: " + website, bodyFont);
            contactInfo.setAlignment(Element.ALIGN_CENTER);
            document.add(contactInfo);

            document.add(new Paragraph(" ")); // Empty line

            // Add Table-like Styling (Thermal printers usually don't support complex tables, so this is kept simple)
            document.add(new Paragraph("Item                     Price", bodyFont));
            document.add(new Paragraph("--------------------------------------", bodyFont)); // Line separator

            // Add Table Body with Item Names and Prices (aligned for easy reading on thermal print)
            for (int i = 0; i < items.length; i++) {
                String line = String.format("%-25s %6.2f", items[i], prices[i]);
                document.add(new Paragraph(line, bodyFont));
            }

            document.add(new Paragraph("--------------------------------------", bodyFont)); // Line separator

            // Add Total
            String totalLine = String.format("Total:                  %.2f", total);
            document.add(new Paragraph(totalLine, bodyFont));

            document.add(new Paragraph(" ")); // Empty line

            // Footer (small text)
            Paragraph footerText = new Paragraph("Thank you for dining with us!", bodyFont);
            footerText.setAlignment(Element.ALIGN_CENTER);
            document.add(footerText);

            document.add(new Paragraph("Visit again!", bodyFont));

            // Close document
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
