package com.gonzalodev.InventoryMgtSystem.services;

import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.dtos.SupplierDTO;

public interface SupplierService {
    Response addSupplier(SupplierDTO supplier);

    Response updateSupplier(Long id, SupplierDTO supplier);

    Response deleteSupplier(Long id);

    Response getAllSuppliers();

    Response getSupplierById(Long id);

}
