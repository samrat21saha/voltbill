package in.voltmart.billingsoftware.controller;

import in.voltmart.billingsoftware.io.CategoryRequest;
import in.voltmart.billingsoftware.io.CategoryResponse;
import in.voltmart.billingsoftware.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {


    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse addCategory(@RequestBody CategoryRequest request){
        return categoryService.addCategory(request);
    }


}
