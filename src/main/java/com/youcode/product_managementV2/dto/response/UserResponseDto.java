package com.youcode.product_managementV2.dto.response;




import com.youcode.product_managementV2.Entity.UserRole;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
    private Long id;
    private String login;
    private Boolean active;
    private UserRole role;
}
