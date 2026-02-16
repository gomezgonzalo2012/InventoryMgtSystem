package com.gonzalodev.InventoryMgtSystem.controllers;

import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.dtos.TransactionRequest;
import com.gonzalodev.InventoryMgtSystem.enums.TransactionStatus;
import com.gonzalodev.InventoryMgtSystem.services.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<Response> purchase(@RequestBody @Valid TransactionRequest transactionRequest){
        Response response = transactionService.purchase(transactionRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/sale")
    public ResponseEntity<Response> sale(@RequestBody @Valid TransactionRequest transactionRequest){
        Response response = transactionService.sell(transactionRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/returnToSupplier")
    public ResponseEntity<Response> returnToSupplier(@RequestBody @Valid TransactionRequest transactionRequest){
        Response response = transactionService.returnToSupplier(transactionRequest);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(required = false) String filter){
        Response response = transactionService.getAllTransactions(page, size, filter);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getTransactionById(@PathVariable Long id){
        Response response = transactionService.getTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-month-year")
    public ResponseEntity<Response> getTransactionMonthAndYear(
            @RequestParam int month,
            @RequestParam int year){
        Response response = transactionService.getTransactionByMonthAndYear(month, year);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateTransactionStatus( @PathVariable Long id, @RequestParam TransactionStatus status){
        Response response = transactionService.updateTransactionStatus(id,status);
        return ResponseEntity.ok(response);
    }
}

