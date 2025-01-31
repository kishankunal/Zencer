package com.mrkunal.zencer.service;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;
import com.mrkunal.zencer.dto.request.bill.BillItemRequest;
import com.mrkunal.zencer.dto.request.bill.CreateBillRequest;
import com.mrkunal.zencer.dto.response.BillItemResponse;
import com.mrkunal.zencer.dto.response.BillResponse;
import com.mrkunal.zencer.dto.response.CompleteBillResponse;
import com.mrkunal.zencer.model.Entity.*;
import com.mrkunal.zencer.repository.BillingRepo;
import com.mrkunal.zencer.repository.ProductRepo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BillingService {

    private final BillingRepo billingRepo;
    private final ProductRepo productRepo;
    private final UserService userService;

    public BillingService(BillingRepo billingRepo, ProductRepo productRepo, UserService userService) {
        this.billingRepo = billingRepo;
        this.productRepo = productRepo;
        this.userService = userService;
    }

    public BillResponse createBill(CreateBillRequest billRequest, Shop shop) {
        List<String> billItems = new ArrayList<>();
        List<String> errors = new ArrayList<>();
        User user = userService.getUserFromUserId(billRequest.getUserId());
        if(user == null){
            throw new RuntimeException("User with user id: " + billRequest.getUserId() + " not found");
        }
        double totalPrice = 0L;
        try {
            for(BillItemRequest billItemRequest : billRequest.getBillItems()){
                Product product = productRepo.getProductFromId(billItemRequest.getProductId().toString());
                if(product.getQuantity() >= billItemRequest.getQuantity()){
                    BillItem item = billingRepo.addBillItem(new BillItem.Builder()
                            .itemId(UUID.randomUUID().toString())
                            .priceAtPurchase(product.getPrice())
                            .quantity(billItemRequest.getQuantity())
                            .product(product)
                            .build());
                    productRepo.decreaseProductQuantity(product, billItemRequest.getQuantity());
                    billItems.add(item.getItemId());
                    totalPrice += billItemRequest.getQuantity()* product.getPrice();
                } else {
                    errors.add("Product with id " + product.getProductId() + " has insufficient Quantity");
                }
            }
        } catch (Exception e){
            errors.add(e.getMessage());
        }
        if(errors.size() == billRequest.getBillItems().size()){
            throw new RuntimeException(errors.toString());
        }

        Bill bill = billingRepo.createBill(billItems, (long) totalPrice, user, shop);

        return new BillResponse.Builder()
                .billId(bill.getBillId())
                .createdAt(bill.getCreatedAt())
                .errorDetails(errors)
                .shopId(shop.getShopId())
                .userId(user.getUserId())
                .totalPrice(bill.getTotalPrice())
                .build();
    }

    public Bill getBillById(String billId) {
        return billingRepo.getBillById(billId);
    }

    public List<BillItemResponse> getBillItemDetails(List<String> billItems) {
        List<BillItemResponse> billItemResponses = new ArrayList<>();
        for(String itemId : billItems){
            BillItem item = billingRepo.getBillItemByItemId(itemId);
            billItemResponses.add(new BillItemResponse.Builder()
                    .productName(item.getProduct().getProductName())
                    .productId(item.getProduct().getProductId().toString())
                    .itemId(itemId)
                    .priceAtPurchase(item.getPriceAtPurchase())
                    .quantity(item.getQuantity()).build());
        }
        return billItemResponses;
    }

    public byte[] generateInvoice(CompleteBillResponse billResponse){
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

            // Create PDF Document
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // **Add Zencer Header**
            document.add(new Paragraph("ZENCER INVOICE")
                    .setBold().setFontSize(20)
                    .setTextAlignment(TextAlignment.CENTER));

            document.add(new Paragraph("\n"));

            // **Add User & Shop Details**
            document.add(new Paragraph("Customer: " + billResponse.getUserName())
                    .setBold().setFontSize(12));
            document.add(new Paragraph("Mobile: " + billResponse.getUserMobileNumber()));
            document.add(new Paragraph("Shop: " + billResponse.getShopName())
                    .setBold().setFontSize(12));
            document.add(new Paragraph("Shop Address: " + billResponse.getShopAddress()));
            document.add(new Paragraph("Bill Date: " + billResponse.getCreatedAt()));

            document.add(new Paragraph("\n"));

            // **Create Bill Items Table**
            Table table = new Table(UnitValue.createPercentArray(new float[]{2, 4, 2, 2}))
                    .useAllAvailableWidth();
            table.addHeaderCell(new Cell().add(new Paragraph("Product ID")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Product Name")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Quantity")).setBold());
            table.addHeaderCell(new Cell().add(new Paragraph("Price")).setBold());

            // **Add Items to Table**
            for (BillItemResponse item : billResponse.getBillItems()) {
                table.addCell(item.getProductId());
                table.addCell(item.getProductName());
                table.addCell(String.valueOf(item.getQuantity()));
                table.addCell(String.valueOf(item.getPriceAtPurchase()));
            }

            document.add(table);

            // **Total Price**
            document.add(new Paragraph("\nTotal Price: ₹" + billResponse.getTotalPrice())
                    .setBold().setFontSize(14));

            // **Add Footer**
            document.add(new Paragraph("\nThank you for shopping with us!")
                    .setItalic().setFontSize(10)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

            // Convert to byte array and return response
            byte[] pdfBytes = baos.toByteArray();
            return pdfBytes;


        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF: " + e.getMessage());
        }
    }
}
