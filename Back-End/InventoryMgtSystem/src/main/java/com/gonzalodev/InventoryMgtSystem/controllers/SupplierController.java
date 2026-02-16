package com.gonzalodev.InventoryMgtSystem.controllers;

import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.dtos.SupplierDTO;
import com.gonzalodev.InventoryMgtSystem.services.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/suppliers")
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> saveSupplier(@RequestBody @Valid SupplierDTO supplier){
        Response response = supplierService.addSupplier(supplier);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateSupplier(@PathVariable Long id, @RequestBody @Valid SupplierDTO supplier){
        Response response = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/all")
    public ResponseEntity<Response> getAllSuppliers(){
        Response response = supplierService.getAllSuppliers();
        return ResponseEntity.ok(response);
    }
    @GetMapping("{id}")
    public ResponseEntity<Response> getSupplierById (@PathVariable Long id){
        Response response = supplierService.getSupplierById(id);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteSupplier(@PathVariable Long id){
        Response response = supplierService.deleteSupplier(id);
        return ResponseEntity.ok(response);
    }

}
