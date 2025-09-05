package in.voltmart.billingsoftware.io;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private String categoryId;
    private String name;
    private String description;
    private String bgColor;
    private String imgUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
