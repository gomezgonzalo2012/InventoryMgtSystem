package com.gonzalodev.InventoryMgtSystem.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.gonzalodev.InventoryMgtSystem.services.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
    private final Cloudinary cloudinary;
    @Override
    public String uploadFile(MultipartFile file) {
        List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "webp", "avif");
        String extensions = null;

        if(file.getOriginalFilename()!=null){
            String[] splitName = file.getOriginalFilename().split("\\.");
            extensions= splitName[splitName.length -1]; // last index locates the extension
        }
        if(!allowedExtensions.contains(extensions)) throw new IllegalArgumentException(String.format("Extension not allowed", extensions));
        try{
            Map<String, Object> resultUpload = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "inventoryImages"));
            String imageUrl = resultUpload.get("secure_url").toString();
            return imageUrl;
        }catch (Exception e){
            throw new RuntimeException("Unable to upload the file, "+e.getMessage());

        }
    }
}
