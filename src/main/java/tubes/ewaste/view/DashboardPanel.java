package tubes.ewaste.view;

import tubes.ewaste.controller.UserController;
import tubes.ewaste.model.User;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DashboardPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserController userController;

    private JTable usersTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton logoutButton;

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        tableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Email", "Alamat", "Tanggal Lahir", "Foto", "Dibuat Pada"}, 0);
        usersTable = new JTable(tableModel);
        usersTable.setFillsViewportHeight(true);
        
        refreshButton = new JButton("Refresh");
        updateButton = new JButton("Rubah");
        deleteButton = new JButton("Hapus");
        logoutButton = new JButton("Logout");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Set padding and border for the table
        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(refreshButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        buttonPanel.add(updateButton, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        buttonPanel.add(deleteButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        buttonPanel.add(logoutButton, gbc);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadUsers();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = usersTable.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) tableModel.getValueAt(selectedRow, 0);
                    String name = (String) tableModel.getValueAt(selectedRow, 1);
                    String email = (String) tableModel.getValueAt(selectedRow, 2);
                    String address = (String) tableModel.getValueAt(selectedRow, 3);
                    String birthDate = (String) tableModel.getValueAt(selectedRow, 4);
                    String photoPath = (String) tableModel.getValueAt(selectedRow, 5);
                    String createdAt = (String) tableModel.getValueAt(selectedRow, 6);

                    User user = new User();
                    user.setId(userId);
                    user.setName(name);
                    user.setEmail(email);
                    user.setAddress(address);
                    user.setBirthDate(birthDate != null && !birthDate.isEmpty() ? LocalDate.parse(birthDate) : null);
                    user.setPhotoPath(photoPath != null && !photoPath.isEmpty() ? photoPath : null);
                    user.setCreatedAt(createdAt != null && !createdAt.isEmpty() ? LocalDateTime.parse(createdAt) : null);

                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Silahkan pilih item yang ingin di rubah!");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = usersTable.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) tableModel.getValueAt(selectedRow, 0);
                    userController.deleteUser(userId);
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "Silahkan pilih item yang ingin di hapus!");
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.showLogin(); // Kembali ke halaman login
            }
        });
    }

    public void loadUsers() { 
        tableModel.setRowCount(0); // Clear existing data
        
        List<User> users = userController.getAllUsers();
        
        for (User user : users) { 
            String birthDate = (user.getBirthDate() != null) ? user.getBirthDate().toString() : "";
            String photoPath = (user.getPhotoPath() != null) ? user.getPhotoPath() : "";
            String createdAt = (user.getCreatedAt() != null) ? user.getCreatedAt().toString() : "";
            tableModel.addRow(new Object[]{
                user.getId(), 
                user.getName(), 
                user.getEmail(), 
                user.getAddress(), 
                birthDate,
                photoPath,
                createdAt
            });
        }
    }
}
