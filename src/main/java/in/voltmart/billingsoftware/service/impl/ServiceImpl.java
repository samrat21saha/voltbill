package in.voltmart.billingsoftware.service.impl;

import in.voltmart.billingsoftware.entity.CategoryEntity;
import in.voltmart.billingsoftware.io.CategoryRequest;
import in.voltmart.billingsoftware.io.CategoryResponse;
import in.voltmart.billingsoftware.repository.CategoryRepository;
import in.voltmart.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryRequest request) {
        CategoryEntity newCategory = convertToEntity(request);
        newCategory = categoryRepository.save(newCategory);
        return convertToResponse(newCategory);
    }

    @Override
    public List<CategoryResponse> readCategory() {
      return  categoryRepository.findAll()
              .stream()
              .map(categoryEntity -> convertToResponse(categoryEntity))
              .collect(Collectors.toList());


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

    private CategoryEntity convertToEntity(CategoryRequest request) {
        return CategoryEntity.builder()
                .categoryId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .bgColor(request.getBgColor())
                .build();
    }
}
