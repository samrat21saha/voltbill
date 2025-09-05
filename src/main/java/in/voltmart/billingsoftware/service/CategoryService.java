package in.voltmart.billingsoftware.service;

import in.voltmart.billingsoftware.io.CategoryRequest;
import in.voltmart.billingsoftware.io.CategoryResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public interface CategoryService {
    CategoryResponse add(CategoryRequest request, MultipartFile file) throws IOException;

    List<CategoryResponse> read();

    void delete(String categoryId);
}
