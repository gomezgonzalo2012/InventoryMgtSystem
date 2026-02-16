package com.gonzalodev.InventoryMgtSystem.services.impl;

import com.gonzalodev.InventoryMgtSystem.dtos.Response;
import com.gonzalodev.InventoryMgtSystem.dtos.SupplierDTO;
import com.gonzalodev.InventoryMgtSystem.exceptions.NotFoundException;
import com.gonzalodev.InventoryMgtSystem.models.Supplier;
import com.gonzalodev.InventoryMgtSystem.repositories.SupplierRepository;
import com.gonzalodev.InventoryMgtSystem.services.SupplierService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.modelmapper.TypeToken;


import java.util.List;

@RequiredArgsConstructor
@Service
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    @Override
    public Response addSupplier(SupplierDTO supplier) {
        Supplier supplierToSave = modelMapper.map(supplier, Supplier.class);
        supplierRepository.save(supplierToSave);
        return Response.builder()
                .status(204)
                .message("Supplier successfully created")
                .build();
    }

    @Override
    public Response updateSupplier(Long id, SupplierDTO supplier) {
        Supplier existingSupplier = supplierRepository.findById(id).orElseThrow(()-> new NotFoundException("Supplier not found"));
        if(supplier.getAddress() != null) existingSupplier.setAddress(supplier.getAddress());
        if(supplier.getContactInfo() != null) existingSupplier.setContactInfo(supplier.getContactInfo());
        if(supplier.getName() != null) existingSupplier.setName(supplier.getName());
        supplierRepository.save(existingSupplier);

        return Response.builder()
                .status(200)
                .message("Supplier updated successfully")
                .build();
    }

    @Override
    public Response deleteSupplier(Long id) {
        Supplier existingSupplier = supplierRepository.findById(id).orElseThrow(()-> new NotFoundException("Supplier not found"));
        supplierRepository.delete(existingSupplier);
        return Response.builder()
                .status(200)
                .message("Supplier deleted successfully")
                .build();
    }

    @Override
    public Response getAllSuppliers() {
        List<Supplier> suppliers= supplierRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        List<SupplierDTO> supplierDTOList = modelMapper.map(suppliers, new TypeToken<List<SupplierDTO>>(){}.getType());
        return Response.builder()
                .status(200)
                .message("success")
                .suppliers(supplierDTOList)
                .build();
    }

    @Override
    public Response getSupplierById(Long id) {
        Supplier existingSupplier = supplierRepository.findById(id).orElseThrow(()-> new NotFoundException("Supplier not found"));
        SupplierDTO supplierDTO = modelMapper.map(existingSupplier, SupplierDTO.class);

        return Response.builder()
                .status(200)
                .message("success")
                .supplier(supplierDTO)
                .build();
    }
}
