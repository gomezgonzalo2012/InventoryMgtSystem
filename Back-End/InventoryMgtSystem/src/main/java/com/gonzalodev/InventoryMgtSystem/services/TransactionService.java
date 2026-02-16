package com.gonzalodev.InventoryMgtSystem.services;

import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.dtos.TransactionRequest;
import com.gonzalodev.InventoryMgtSystem.enums.TransactionStatus;

public interface TransactionService {
    Response purchase(TransactionRequest transactionRequest);

    Response sell(TransactionRequest transactionRequest);

    Response returnToSupplier(TransactionRequest transactionRequest);

    Response getAllTransactions(int page, int size, String filter);

    Response getTransactionById(Long id);

    Response getTransactionByMonthAndYear(int month, int  year);

    Response updateTransactionStatus(Long id, TransactionStatus status);

}
