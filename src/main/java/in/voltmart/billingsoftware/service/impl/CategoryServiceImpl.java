package in.voltmart.billingsoftware.service.impl;

import in.voltmart.billingsoftware.entity.CategoryEntity;
import in.voltmart.billingsoftware.io.CategoryRequest;
import in.voltmart.billingsoftware.io.CategoryResponse;
import in.voltmart.billingsoftware.repository.CategoryRepository;
import in.voltmart.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;



    @Override
    public CategoryResponse add(CategoryRequest request, MultipartFile file)throws IOException {

        String fileName =  UUID.randomUUID().toString()+"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        String imgUrl = "http://localhost:8080/api/v1.0/uploads/"+fileName;
        CategoryEntity newCategory = convertToEntity(request, file);
        newCategory.setImgUrl(imgUrl);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }




    @Override
    public List<CategoryResponse> read() {
      return  categoryRepository.findAll()
              .stream()
              .map(categoryEntity -> convertToResponse(categoryEntity))
              .collect(Collectors.toList());


    }

    @Override
    public void delete(String categoryId) {
        CategoryEntity existingCategory = categoryRepository.findByCategoryId(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found: "+categoryId));
        String imgUrl = existingCategory.getImgUrl();
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        categoryRepository.delete(existingCategory);
    }

    private CategoryResponse convertToResponse(CategoryEntity newCategory) {
        return CategoryResponse.builder()
                .categoryId(newCategory.getCategoryId())
                .name(newCategory.getName())
                .description(newCategory.getDescription())
                .bgColor(newCategory.getBgColor())
                .createdAt(newCategory.getCreatedAt())
                .updatedAt(newCategory.getUpdatedAt())
                .build();
    }

    private CategoryEntity convertToEntity(CategoryRequest request, MultipartFile file) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
