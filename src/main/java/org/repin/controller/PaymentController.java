package org.repin.controller;

import org.repin.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @GetMapping("/calculate")
    ResponseEntity<BigDecimal> getPayment(@RequestParam("salary") BigDecimal annualSalary,
                                          @RequestParam("days") int vacationDaysCount,
                                          @Nullable @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate startDate,
                                          @Nullable @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate endDate){

        return ResponseEntity.ok(paymentService
                .getVacationPayment(annualSalary, vacationDaysCount, startDate, endDate));
    }

}
