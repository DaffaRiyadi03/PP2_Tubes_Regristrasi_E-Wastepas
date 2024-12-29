package tubes.ewaste.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private String address;
    private LocalDate birthDate;
    private String photoPath;
    private LocalDate createdAt;
    private int role; // 1 = user, 2 = kelola
}
