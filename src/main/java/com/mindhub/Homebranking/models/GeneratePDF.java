package com.mindhub.Homebranking.models;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.util.Set;
import java.util.stream.Stream;


public class GeneratePDF {

    public static void createPDF(Set<Transaction> transactionsAccount, Client clientCurrent, HttpServletResponse httpServletResponse) throws Exception{
        httpServletResponse.setContentType("application/pdf");
        httpServletResponse.setHeader("Content-Disposition","attachment; filename=transactions.pdf");
        Font fontTitle = FontFactory.getFont("Helvetica",20, Color.BLACK);
        Font fontBodyText = FontFactory.getFont("Helvetica",14, Color.BLACK);
        Paragraph title = new Paragraph("Your Transactions",fontTitle);
        Paragraph bodyText = new Paragraph("Client: " + clientCurrent.getFirstName() + " " + clientCurrent.getLastName() ,fontBodyText);
        bodyText.setSpacingAfter(20);

        PdfPTable tableTransactions = new PdfPTable(4);
        Stream.of("Amount","Description", "Date", "Type").forEach(tableTransactions::addCell);
        transactionsAccount.forEach(transaction -> {
            tableTransactions.addCell(String.valueOf(transaction.getAmount()));
            tableTransactions.addCell(transaction.getDescription());
            tableTransactions.addCell(String.valueOf(transaction.getTransactionDate()));
            tableTransactions.addCell(String.valueOf(transaction.getType()));
        });

        Document documentPdf = new Document();
        documentPdf.setPageSize(PageSize.A4);

        PdfWriter.getInstance(documentPdf,httpServletResponse.getOutputStream());
        documentPdf.open();
        documentPdf.add(title);
        documentPdf.add(bodyText);
        documentPdf.add(tableTransactions);
        documentPdf.close();
    }
}
