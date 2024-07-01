package jihong99.shoppingmall.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ItemRequestDto {
    @NotNull(message = "Name is a required field.")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters.")
    private String name;

    @NotNull(message = "Price is a required field.")
    @Min(value = 0, message = "Price must be greater than or equal to 0.")
    private Integer price;

    @NotNull(message = "Inventory is a required field.")
    @Min(value = 0, message = "Inventory must be greater than or equal to 0.")
    private Integer inventory;

    @NotNull(message = "Keyword is a required field.")
    @Size(min = 3, max = 50, message = "Keyword must be between 3 and 50 characters.")
    private String keyword;

    @NotEmpty(message = "Category IDs cannot be empty.")
    private List<Long> categoryIds;
}