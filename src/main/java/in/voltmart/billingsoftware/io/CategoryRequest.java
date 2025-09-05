package in.voltmart.billingsoftware.io;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequest {

    private String name;
    private String description;
    private String bgColor;
}