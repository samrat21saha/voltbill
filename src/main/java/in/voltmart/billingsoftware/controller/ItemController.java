package in.voltmart.billingsoftware.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.voltmart.billingsoftware.io.ItemRequest;
import in.voltmart.billingsoftware.io.ItemResponse;
import in.voltmart.billingsoftware.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/item")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }


    @PostMapping("/admin/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public ItemResponse addItem(@RequestPart("Item") String ItemString,
                                        @RequestPart("file") MultipartFile file){

        ObjectMapper objectMapper = new ObjectMapper();
        ItemRequest request = null;
        try {
            request = objectMapper.readValue(ItemString, ItemRequest.class);
            return itemService.addItem(request, file);
        } catch (JsonProcessingException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Exception occurred while parsing the json: "+ex.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @GetMapping
    public List<ItemResponse> fetchCategories() {
        return itemService.fetchAllItems();
    }

    @DeleteMapping("/admin/categories{ItemId}")
    public void  remove(@PathVariable String ItemId) {
        try {
            itemService.deleteItem(ItemId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,e.getMessage());
        }
    }
}
