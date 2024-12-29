package tubes.ewaste.controller;

import java.util.List;
import java.util.Random;
import java.util.Properties;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import tubes.ewaste.config.DatabaseConfig;
import tubes.ewaste.mapper.UserMapper;
import tubes.ewaste.model.User;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.time.LocalDateTime;
import org.mindrot.jbcrypt.BCrypt;

public class UserController {
    private final SqlSessionFactory factory;

    public UserController() {
        this.factory = DatabaseConfig.getSqlSessionFactory();
    }

    public boolean login(String email, String password) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.validateLogin(email, password) > 0;
        }
    }

    public void register(User user) throws Exception {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
    
            // Hash the password before storing
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword); // Set hashed password
    
            // Generate OTP
            String otp = generateOtp();
            user.setOtp(otp);
            user.setVerified(false);
            user.setCreatedAt(LocalDateTime.now()); // Set created timestamp
    
            // Set OTP expiry (e.g., 10 minutes)
            user.setOtpExpiry(LocalDateTime.now().plusMinutes(10));
    
            // Insert user into database
            mapper.insert(user);
            session.commit();
    
            // Send OTP to email
            sendOtpEmail(user.getEmail(), otp);
        }
    }
    

    public User findUserByEmail(String email) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getByEmail(email);
        }
    }

    public boolean verifyOtp(String email, String inputOtp) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
    
            // Retrieve user by email
            User user = mapper.getByEmail(email);
            if (user == null) {
                return false;
            }
    
            // Check if OTP has expired
            if (user.getOtpExpiry().isBefore(LocalDateTime.now())) {
                return false; // OTP expired
            }
    
            // Verify OTP
            if (user.getOtp().equals(inputOtp)) {
                user.setVerified(true); // Update verification status
                mapper.update(user);
                session.commit();
                return true;
            }
    
            return false;
        }
    }
    

    public void updateProfile(User user) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.update(user);
            session.commit();
        }
    }

    public List<User> getAllUsers() {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            return mapper.getAll();
        }
    }

    public void deleteUser(int userId) {
        try (SqlSession session = factory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            mapper.delete(userId);
            session.commit();
        }
    }

    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    private void sendOtpEmail(String recipient, String otp) throws MessagingException {
        String senderEmail = "retrogamea00@gmail.com"; // Ganti dengan email Anda
        String senderPassword = "Nanamiko12!"; // Ganti dengan password email Anda

        // Setup properties for email session
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create email session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Create email message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
        message.setSubject("Your OTP for Registration");
        message.setText("Your OTP is: " + otp);

        // Send email
        Transport.send(message);
    }
}
