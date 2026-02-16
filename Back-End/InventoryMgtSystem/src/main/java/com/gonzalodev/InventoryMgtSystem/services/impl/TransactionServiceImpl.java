package com.gonzalodev.InventoryMgtSystem.services.impl;

import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.dtos.TransactionDTO;
import com.gonzalodev.InventoryMgtSystem.dtos.TransactionRequest;
import com.gonzalodev.InventoryMgtSystem.enums.TransactionStatus;
import com.gonzalodev.InventoryMgtSystem.enums.TransactionType;
import com.gonzalodev.InventoryMgtSystem.exceptions.NameValueRequiredException;
import com.gonzalodev.InventoryMgtSystem.exceptions.NotFoundException;
import com.gonzalodev.InventoryMgtSystem.models.Product;
import com.gonzalodev.InventoryMgtSystem.models.Supplier;
import com.gonzalodev.InventoryMgtSystem.models.Transaction;
import com.gonzalodev.InventoryMgtSystem.models.User;
import com.gonzalodev.InventoryMgtSystem.repositories.ProductRepository;
import com.gonzalodev.InventoryMgtSystem.repositories.SupplierRepository;
import com.gonzalodev.InventoryMgtSystem.repositories.TransactionRepository;
import com.gonzalodev.InventoryMgtSystem.repositories.UserRepository;
import com.gonzalodev.InventoryMgtSystem.services.TransactionService;
import com.gonzalodev.InventoryMgtSystem.specification.TransactionFilter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final ModelMapper modelMapper;


    @Override
    public Response purchase(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Long supplierId = transactionRequest.getSupplierId();
        Integer quantity = transactionRequest.getQuantity();

        if (supplierId == null ) throw new NameValueRequiredException("Supplier id is required");
        if (productId == null ) throw new NameValueRequiredException("Product id is required");

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new NotFoundException("Product not found"));
        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(()-> new NotFoundException("Supplier not found"));

        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);
        // change when have Spring security set up
        User user = userRepository.findById(1L)
                .orElseThrow(()-> new NotFoundException("User not found"));

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.PURCHASE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .supplier(supplier)
                .user(user)
                .description(transactionRequest.getDescription())
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .note(transactionRequest.getNote())
                .build();
        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Purchase made successfully")
                .build();
    }

    @Override
    public Response sell(TransactionRequest transactionRequest) {
        Long productId = transactionRequest.getProductId();
        Integer quantity = transactionRequest.getQuantity();

        if (productId == null ) throw new NameValueRequiredException("Product id is required");
        Product product = productRepository.findById(transactionRequest.getProductId())
                .orElseThrow(()-> new NotFoundException("Product not found"));
        if(product.getStockQuantity()-quantity <= 0){
            throw new NotFoundException("Out of stock");
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
        // change when have Spring security set up
        User user = userRepository.findById(1L)
                .orElseThrow(()-> new NotFoundException("User not found"));

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.SALE)
                .status(TransactionStatus.COMPLETED)
                .product(product)
                .user(user)
                .description(transactionRequest.getDescription())
                .totalProducts(quantity)
                .totalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)))
                .note(transactionRequest.getNote())
                .build();
        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Sale completed successfully")
                .build();
    }

    @Override
    public Response returnToSupplier(TransactionRequest transactionRequest) {

        if(transactionRequest.getTransactionId()==null)  throw new NameValueRequiredException("Transaction id is required");
        Integer quantity = transactionRequest.getQuantity();
        Transaction transactionDb = transactionRepository.findById(transactionRequest.getTransactionId())
                .orElseThrow(()-> new NotFoundException("Transaction not found"));
        if(!transactionRequest.getProductId().equals(transactionDb.getProduct().getId())) throw new NameValueRequiredException("Product doesn't exist int this transaction");
        if(!transactionRequest.getSupplierId().equals(transactionDb.getSupplier().getId())) throw new NameValueRequiredException("Supplier doesn't exist int this transaction");

        Product product = productRepository.findById(transactionRequest.getProductId())
                .orElseThrow(()-> new NotFoundException("Product not found"));
        Supplier supplier = supplierRepository.findById(transactionRequest.getSupplierId())
                .orElseThrow(()-> new NotFoundException("Supplier not found"));

        if(product.getStockQuantity()-quantity <= 0){
            throw new NotFoundException("Out of stock");
        }
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
        // change when have Spring security set up
        User user = userRepository.findById(1L)
                .orElseThrow(()-> new NotFoundException("User not found"));

        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.RETURN_TO_SUPPLIER)
                .status(TransactionStatus.PROCESSING)
                .product(product)
                .supplier(supplier)
                .user(user)
                .description(transactionRequest.getDescription())
                .totalProducts(quantity)
                .totalPrice(BigDecimal.ZERO)
                .note(transactionRequest.getNote())
                .build();
        transactionRepository.save(transaction);

        return Response.builder()
                .status(200)
                .message("Purchase made successfully")
                .build();
    }

    @Override
    public Response getAllTransactions(int page, int size, String filter) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        // filter
        Specification<Transaction> spec = TransactionFilter.byFilter(filter);
        Page<Transaction> transactions = transactionRepository.findAll(spec, pageable);

        List<TransactionDTO> transactionDtoList = modelMapper.map(transactions.getContent(), new TypeToken<List<TransactionDTO>>(){}.getType());
        // avoid circular reference
        transactionDtoList.forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setProduct(null);
            transactionDTO.setSupplier(null);
        });
        return  Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDtoList)
                .totalElements(transactions.getTotalElements())
                .totalPages(transactions.getTotalPages())
                .build();
    }

    @Override
    public Response getTransactionById(Long id) {
        Transaction transaction= transactionRepository.findById(id)
                .orElseThrow(()-> new NotFoundException("Transaction not found"));
        TransactionDTO transactionDTO = modelMapper.map(transaction, TransactionDTO.class);
        transactionDTO.setUser(null);
        transactionDTO.setProduct(null);
        transactionDTO.setSupplier(null);

        return  Response.builder()
                .status(200)
                .message("success")
                .transaction(transactionDTO)
                .build();
    }

    @Override
    public Response getTransactionByMonthAndYear(int month, int year) {
        Specification<Transaction> spec = TransactionFilter.byMonthAndYear(month, year);
        List<Transaction> transactions = transactionRepository.findAll(spec);
        List<TransactionDTO> transactionDtoList = modelMapper.map(transactions, new TypeToken<List<TransactionDTO>>(){}.getType());
        // avoid circular reference
        transactionDtoList.forEach(transactionDTO -> {
            transactionDTO.setUser(null);
            transactionDTO.setProduct(null);
            transactionDTO.setSupplier(null);
        });
        return  Response.builder()
                .status(200)
                .message("success")
                .transactions(transactionDtoList)
                .build();
    }

    @Override
    public Response updateTransactionStatus(Long id, TransactionStatus status) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new NotFoundException("Transaction not found"));
        transaction.setStatus(status);
        transaction.setUpdatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);
        return Response.builder()
                .status(200)
                .message("Transaction updated successfully")
                .build();
    }
}
