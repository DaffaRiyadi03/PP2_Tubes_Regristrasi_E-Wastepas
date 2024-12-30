package tubes.ewaste.controller;

import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.OtpMapper;
import tubes.ewaste.mapper.UserMapper;
import tubes.ewaste.model.Otp;
import tubes.ewaste.model.User;
import tubes.ewaste.service.MailService;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {
    private final SqlSessionFactory factory;
    private final MailService mailService;

    // Constructor
    public UserController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
        this.mailService = new MailService(); // Initialize MailService
    }

    // Login method
    public boolean login(String email, String password) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.validateLogin(email, password) > 0;
        }
    }

    // Register method with OTP functionality
    public void register(User user) throws Exception {
        try (SqlSession session = factory.openSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            OtpMapper otpMapper = session.getMapper(OtpMapper.class);
    
            // Hash password
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
            user.setCreatedAt(LocalDateTime.now());
    
            // Insert user
            userMapper.insert(user);
    
            // Generate OTP
// Generate and save OTP
String otpCode = generateOtp();
Otp otp = new Otp();
otp.setEmail(user.getEmail());
otp.setOtpCode(otpCode);
otp.setExpiresAt(LocalDateTime.now().plusMinutes(60));  // Ensure this is set properly
otp.setStatus("ACTIVE");
otpMapper.insert(otp);

            session.commit();
    
            // Send OTP via email
            mailService.sendOtpEmail(user.getEmail(), otpCode);
        }
    }
    
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate 6 digit OTP
        return String.valueOf(otp);
    }
    

    public boolean verifyOtp(String email, String otpCode) {
        try (SqlSession session = factory.openSession()) {
            OtpMapper otpMapper = session.getMapper(OtpMapper.class);
            UserMapper userMapper = session.getMapper(UserMapper.class);
    
            // Find active OTP
            Otp otp = otpMapper.findActiveOtpByEmail(email);
            if (otp == null || otp.getExpiresAt().isBefore(LocalDateTime.now())) {
                return false; // OTP expired or not found
            }
    
            if (otp.getOtpCode().equals(otpCode)) {
                otpMapper.updateStatus(otp.getId(), "USED"); // Mark as used
                session.commit();
    
                // Update user's is_verified to "YES"
                userMapper.updateVerificationStatus(email, "YES");
                session.commit();
    
                return true;
            }
    
            return false; // Invalid OTP
        }
    }
    
    


    // Method to find user by email
    public User findUserByEmail(String email) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getByEmail(email);
        }
    }

    // Method to update user profile
    public void updateProfile(User user) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
        }
    }

    // Method to get all users
    public List<User> getAllUsers() {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getAll();
        }
    }

    // Method to delete user
    public void deleteUser(int userId) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(userId);
            session.commit();
        }
    }

}
