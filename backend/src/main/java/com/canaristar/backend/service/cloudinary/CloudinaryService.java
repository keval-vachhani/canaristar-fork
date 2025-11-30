package com.canaristar.backend.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    private final Uploader uploader;

    public CloudinaryService(Cloudinary cloudinary) {
        this.uploader = cloudinary.uploader();
    }

    public String uploadImage(String folder, MultipartFile file) throws Exception {

       try{
           Map result = uploader.upload(
                   file.getBytes(),
                   Map.of(
                           "folder", folder,
                           "resource_type", "image"
                   )
           );

           return result.get("secure_url").toString();
       } catch (Exception e) {
           throw new Exception("Error uploading image: " + e.getMessage());
       }

    }

    public String extractPublicId(String url) {
        try {
            String partAfterUpload = url.substring(url.indexOf("/upload/") + 8);

            if (partAfterUpload.startsWith("v")) {
                int firstSlash = partAfterUpload.indexOf("/");
                partAfterUpload = partAfterUpload.substring(firstSlash + 1);
            }

            partAfterUpload = partAfterUpload.replaceAll("\\.[a-zA-Z0-9]+$", "");

            return partAfterUpload;
        } catch (Exception e) {
            return null;
        }
    }

    public String deleteImage(String publicId) {
        try {
            Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            if ("ok".equals(result.get("result"))) {
                return "true";
            } else {
                return "Cloudinary could not delete image: " + result.get("result");
            }

        } catch (Exception e) {
            return "Error deleting image: " + e.getMessage();
        }
    }
}
