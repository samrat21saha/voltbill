package in.voltmart.billingsoftware.service;

import in.voltmart.billingsoftware.io.ItemRequest;
import in.voltmart.billingsoftware.io.ItemResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ItemService {
    ItemResponse addItem(ItemRequest request, MultipartFile file) throws IOException;

    List<ItemResponse> fetchAllItems();

    void deleteItem(String ItemId);
}
