package com.library.management.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequestDto {

    @NotBlank(message = "İstifadəçi adı boş ola bilməz")
    private String username;

    @NotBlank(message = "Şifrə boş ola bilməz")
    @Size(min = 8, max = 32, message = "Şifrə ən azı 8, ən çox 32 simvoldan ibarət olmalıdır")
    private String password;
}
