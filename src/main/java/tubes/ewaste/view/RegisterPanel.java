package tubes.ewaste.view;

import tubes.ewaste.controller.UserController;
import tubes.ewaste.model.User;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegisterPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserController userController;

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextArea addressArea;
    private JTextField birthDateField;
    private JButton registerButton;
    private JButton backButton;
    private JTextField otpField; // Untuk input OTP
    private JButton verifyOtpButton; // Tombol untuk verifikasi OTP
    private String registeredEmail; // Tambahkan variabel untuk email yang didaftarkan


    public RegisterPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        emailField = new JTextField(20);
        passwordField = new JPasswordField(20);
        confirmPasswordField = new JPasswordField(20);
        addressArea = new JTextArea(3, 20);
        addressArea.setLineWrap(true);
        birthDateField = new JTextField(20);
        registerButton = new JButton("Register");
        backButton = new JButton("Back to Login");
        otpField = new JTextField(20); // Untuk input OTP
        verifyOtpButton = new JButton("Verify OTP"); // Tombol untuk verifikasi OTP

        // Set preferred sizes
        Dimension fieldSize = new Dimension(250, 35);
        nameField.setPreferredSize(fieldSize);
        emailField.setPreferredSize(fieldSize);
        passwordField.setPreferredSize(fieldSize);
        confirmPasswordField.setPreferredSize(fieldSize);
        birthDateField.setPreferredSize(fieldSize);
        otpField.setPreferredSize(fieldSize); // Set size untuk OTP field

        Dimension buttonSize = new Dimension(250, 40);
        registerButton.setPreferredSize(buttonSize);
        backButton.setPreferredSize(buttonSize);
        verifyOtpButton.setPreferredSize(buttonSize); // Set size untuk tombol OTP
    }

    private void setupLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Title Panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Register New Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.gridwidth = GridBagConstraints.REMAINDER;
        formGbc.fill = GridBagConstraints.HORIZONTAL;
        formGbc.insets = new Insets(5, 5, 5, 5);

        formPanel.add(new JLabel("Name:"), formGbc);
        formPanel.add(nameField, formGbc);
        formPanel.add(new JLabel("Email:"), formGbc);
        formPanel.add(emailField, formGbc);
        formPanel.add(new JLabel("Password:"), formGbc);
        formPanel.add(passwordField, formGbc);
        formPanel.add(new JLabel("Confirm Password:"), formGbc);
        formPanel.add(confirmPasswordField, formGbc);
        formPanel.add(new JLabel("Address:"), formGbc);
        formPanel.add(new JScrollPane(addressArea), formGbc);
        formPanel.add(new JLabel("Birth Date (YYYY-MM-DD):"), formGbc);
        formPanel.add(birthDateField, formGbc);
        formPanel.add(Box.createVerticalStrut(20), formGbc);
        formPanel.add(registerButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);
        formPanel.add(backButton, formGbc);
        formPanel.add(Box.createVerticalStrut(10), formGbc);

        // OTP Input
        formPanel.add(new JLabel("Enter OTP:"), formGbc);
        formPanel.add(otpField, formGbc);
        formPanel.add(verifyOtpButton, formGbc);

        // Add to main panel
        add(titlePanel, gbc);
        add(Box.createVerticalStrut(20), gbc);
        add(formPanel, gbc);
    }

    private void setupListeners() {
        registerButton.addActionListener(e -> {
            if (validateInput()) {
                registerUser();
            }
        });

        backButton.addActionListener(e -> {
            clearFields();
            mainFrame.showLogin();
        });

        verifyOtpButton.addActionListener(e -> verifyOtp());
    }

    private boolean validateInput() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                new String(passwordField.getPassword()).isEmpty() ||
                new String(confirmPasswordField.getPassword()).isEmpty() ||
                addressArea.getText().isEmpty() || birthDateField.getText().isEmpty()) {

            JOptionPane.showMessageDialog(this,
                    "Please fill all fields",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!new String(passwordField.getPassword())
                .equals(new String(confirmPasswordField.getPassword()))) {
            JOptionPane.showMessageDialog(this,
                    "Passwords do not match",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            LocalDate.parse(birthDateField.getText());
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid date format. Please use YYYY-MM-DD",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private void registerUser() {
        try {
            User user = new User();
            user.setName(nameField.getText());
            user.setEmail(emailField.getText());
            user.setPassword(new String(passwordField.getPassword()));
            user.setAddress(addressArea.getText());
            user.setBirthDate(LocalDate.parse(birthDateField.getText()));
    
            // Menyimpan email untuk verifikasi OTP nanti
            registeredEmail = emailField.getText(); // Menyimpan email
    
            userController.register(user);
    
            JOptionPane.showMessageDialog(this,
                    "Registration successful! Please check your email for OTP.",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
    
            clearFields();
            mainFrame.showLogin();
    
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Registration failed: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void verifyOtp() {
        String otp = otpField.getText().trim();
        if (otp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter OTP", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        try {
            boolean verified = userController.verifyOtp(registeredEmail, otp); // Gunakan registeredEmail
            if (verified) {
                JOptionPane.showMessageDialog(this, "OTP verified successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                mainFrame.showLogin(); // Optionally navigate to login
            } else {
                JOptionPane.showMessageDialog(this, "Invalid or expired OTP", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error verifying OTP: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        addressArea.setText("");
        birthDateField.setText("");
        otpField.setText(""); // Clear OTP field
    }
}
