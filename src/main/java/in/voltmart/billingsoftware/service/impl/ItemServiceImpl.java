package in.voltmart.billingsoftware.service.impl;

import in.voltmart.billingsoftware.entity.ItemEntity;
import in.voltmart.billingsoftware.io.ItemRequest;
import in.voltmart.billingsoftware.io.ItemResponse;
import in.voltmart.billingsoftware.io.ItemRequest;
import in.voltmart.billingsoftware.io.ItemResponse;
import in.voltmart.billingsoftware.repository.ItemRepository;
import in.voltmart.billingsoftware.service.ItemService;
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
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    
    
    @Override
    public ItemResponse addItem(ItemRequest request, MultipartFile file) throws IOException {

        String fileName =  UUID.randomUUID().toString()+"."+ StringUtils.getFilenameExtension(file.getOriginalFilename());
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Files.createDirectories(uploadPath);
        Path targetLocation = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        String imgUrl = "http://localhost:8080/api/v1.0/uploads/"+fileName;
        ItemEntity newItem = convertToEntity(request, file);
        newItem.setImgUrl(imgUrl);
        newItem = itemRepository.save(newItem);
        return convertToResponse(newItem);
    }

    @Override
    public List<ItemResponse> fetchAllItems() {
        return  itemRepository.findAll()
                .stream()
                .map(ItemEntity -> convertToResponse(ItemEntity))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteItem(String ItemId) {
        ItemEntity existingItem = itemRepository.findByItemId(ItemId)
                .orElseThrow(() -> new RuntimeException("Item not found: "+ItemId));
        String imgUrl = existingItem.getImgUrl();
        String fileName = imgUrl.substring(imgUrl.lastIndexOf("/")+1);
        Path uploadPath = Paths.get("uploads").toAbsolutePath().normalize();
        Path filePath = uploadPath.resolve(fileName);
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        itemRepository.delete(existingItem);
    }

    private ItemResponse convertToResponse(ItemEntity newItem) {
        return ItemResponse.builder()
                .itemId(newItem.getItemId())
                .name(newItem.getName())
                .description(newItem.getDescription())
                .imgUrl(newItem.getImgUrl())
                .createdAt(newItem.getCreatedAt())
                .updatedAt(newItem.getUpdatedAt())
                .build();
    }

    private ItemEntity convertToEntity(ItemRequest request, MultipartFile file) {
        return ItemEntity.builder()
                .itemId(UUID.randomUUID().toString())
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }
}
