package tubes.ewaste.model;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String address;
    private LocalDate birthDate;
    private String photoPath;
    private LocalDateTime createdAt; // Changed to LocalDateTime for more precise timestamp
    private int role; // 1 = user, 2 = kelola

    // Tambahan untuk OTP dan status verifikasi
    private String otp;
    private boolean isVerified;
    private LocalDateTime otpExpiry; // New field for OTP expiration
}
