package com.bridaldress.controller;

import com.bridaldress.Entities.Order;
import com.bridaldress.service.OrderService;
import com.bridaldress.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/reports")
public class PdfReportController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PdfService pdfService;

    @GetMapping("/orders")
    public ResponseEntity<byte[]> generateOrdersReport() {
        Set<Order> ordersSet = orderService.getAll();
        List<Order> orders = ordersSet.stream().collect(Collectors.toList());

        byte[] pdfBytes = pdfService.generateOrdersReport(orders);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "orders_report.pdf");

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}

