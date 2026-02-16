package com.gonzalodev.InventoryMgtSystem.repositories;

import com.gonzalodev.InventoryMgtSystem.models.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {
}
