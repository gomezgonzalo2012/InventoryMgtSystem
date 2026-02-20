package com.gonzalodev.InventoryMgtSystem.services;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
    public String uploadFile(MultipartFile file);
}
