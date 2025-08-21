package in.voltmart.billingsoftware.service;

import in.voltmart.billingsoftware.io.CategoryRequest;
import in.voltmart.billingsoftware.io.CategoryResponse;

import java.util.List;


public interface CategoryService {
    CategoryResponse addCategory(CategoryRequest request);

    List<CategoryResponse> readCategory();

}
