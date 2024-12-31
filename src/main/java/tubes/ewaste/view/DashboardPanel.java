package tubes.ewaste.view;

import tubes.ewaste.controller.UserController;
import tubes.ewaste.controller.CategoryController;
import tubes.ewaste.controller.ItemController;
import tubes.ewaste.controller.ItemTypeController; // Tambahkan ItemTypeController
import tubes.ewaste.model.User;
import tubes.ewaste.model.Category;
import tubes.ewaste.model.Item;
import tubes.ewaste.model.ItemType; // Tambahkan model ItemType

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class DashboardPanel extends JPanel {
    private final MainFrame mainFrame;
    private final UserController userController;
    private final CategoryController categoryController;
    private final ItemController itemController;
    private final ItemTypeController itemTypeController; // Tambahkan ItemTypeController

    private JTabbedPane tabbedPane;

    // Components for Users Tab
    private JTable usersTable;
    private DefaultTableModel userTableModel;
    private JButton refreshUserButton;
    private JButton updateUserButton;
    private JButton deleteUserButton;

    // Components for Categories Tab
    private JTable categoriesTable;
    private DefaultTableModel categoryTableModel;
    private JButton refreshCategoryButton;
    private JButton addCategoryButton;
    private JButton deleteCategoryButton;

    // Components for Item Types Tab
    private JTable itemTypeTable;
    private DefaultTableModel itemTypeTableModel;
    private JButton refreshItemTypeButton;
    private JButton addItemTypeButton;
    private JButton deleteItemTypeButton;

    // Components for Items Tab
    private JTable itemsTable;
    private DefaultTableModel itemTableModel;
    private JButton refreshItemButton;
    private JButton addItemButton;
    private JButton deleteItemButton;

    // Logout Button
    private JButton logoutButton;

    public DashboardPanel(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.userController = new UserController();
        this.categoryController = new CategoryController();
        this.itemController = new ItemController();
        this.itemTypeController = new ItemTypeController(); // Inisialisasi ItemTypeController

        initComponents();
        setupLayout();
        setupListeners();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        // Users Tab
        userTableModel = new DefaultTableModel(new String[]{"ID", "Nama", "Email", "Alamat", "Tanggal Lahir", "Foto"}, 0);
        usersTable = new JTable(userTableModel);
        refreshUserButton = new JButton("Refresh Akun");
        updateUserButton = new JButton("Rubah Akun");
        deleteUserButton = new JButton("Hapus Akun");

        // Categories Tab
        categoryTableModel = new DefaultTableModel(new String[]{"ID", "Nama Kategori", "Deskripsi"}, 0);
        categoriesTable = new JTable(categoryTableModel);
        refreshCategoryButton = new JButton("Refresh Kategori");
        addCategoryButton = new JButton("Tambah Kategori");
        deleteCategoryButton = new JButton("Hapus Kategori");

        // Item Types Tab
        itemTypeTableModel = new DefaultTableModel(new String[]{"ID", "Nama Jenis Item", "Kategori", "Deskripsi"}, 0);
        itemTypeTable = new JTable(itemTypeTableModel);
        refreshItemTypeButton = new JButton("Refresh Jenis Item");
        addItemTypeButton = new JButton("Tambah Jenis Item");
        deleteItemTypeButton = new JButton("Hapus Jenis Item");

        // Items Tab
        itemTableModel = new DefaultTableModel(new String[]{"ID", "Nama Item", "Kategori", "Harga", "Stok"}, 0);
        itemsTable = new JTable(itemTableModel);
        refreshItemButton = new JButton("Refresh Item");
        addItemButton = new JButton("Tambah Item");
        deleteItemButton = new JButton("Hapus Item");

        // Logout Button
        logoutButton = new JButton("Logout");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        // Users Tab Layout
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.add(new JScrollPane(usersTable), BorderLayout.CENTER);
        userPanel.add(createButtonPanel(refreshUserButton, updateUserButton, deleteUserButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Akun", userPanel);

        // Categories Tab Layout
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.add(new JScrollPane(categoriesTable), BorderLayout.CENTER);
        categoryPanel.add(createButtonPanel(refreshCategoryButton, addCategoryButton, deleteCategoryButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Kategori", categoryPanel);

        // Item Types Tab Layout
        JPanel itemTypePanel = new JPanel(new BorderLayout());
        itemTypePanel.add(new JScrollPane(itemTypeTable), BorderLayout.CENTER);
        itemTypePanel.add(createButtonPanel(refreshItemTypeButton, addItemTypeButton, deleteItemTypeButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Jenis Item", itemTypePanel);

        // Items Tab Layout
        JPanel itemPanel = new JPanel(new BorderLayout());
        itemPanel.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        itemPanel.add(createButtonPanel(refreshItemButton, addItemButton, deleteItemButton), BorderLayout.SOUTH);
        tabbedPane.addTab("Kelola Item", itemPanel);

        // Add TabbedPane and Logout Button
        add(tabbedPane, BorderLayout.CENTER);
        add(logoutButton, BorderLayout.SOUTH);
    }

    private JPanel createButtonPanel(JButton... buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        for (JButton button : buttons) {
            panel.add(button);
        }
        return panel;
    }

    private void setupListeners() {
        // User Tab Listeners
        refreshUserButton.addActionListener(e -> loadUsers());
        updateUserButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Rubah Akun Belum Diimplementasikan"));
        deleteUserButton.addActionListener(e -> deleteUser());

        // Category Tab Listeners
        refreshCategoryButton.addActionListener(e -> loadCategories());
        addCategoryButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Tambah Kategori Belum Diimplementasikan"));
        deleteCategoryButton.addActionListener(e -> deleteCategory());

        // Item Type Tab Listeners
        refreshItemTypeButton.addActionListener(e -> loadItemTypes()); // Perbaiki metode load
        addItemTypeButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Tambah Jenis Item Belum Diimplementasikan"));
        deleteItemTypeButton.addActionListener(e -> deleteItemType());

        // Item Tab Listeners
        refreshItemButton.addActionListener(e -> loadItems());
        addItemButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Fitur Tambah Item Belum Diimplementasikan"));
        deleteItemButton.addActionListener(e -> deleteItem());

        // Logout Listener
        logoutButton.addActionListener(e -> mainFrame.showLogin());
    }

    public void loadUsers() {
        userTableModel.setRowCount(0); // Clear existing data
        List<User> users = userController.getAllUsers();
        for (User user : users) {
            userTableModel.addRow(new Object[]{
                    user.getId(),
                    user.getName(),
                    user.getEmail(),
                    user.getAddress(),
                    user.getBirthDate(),
                    user.getPhotoPath()
            });
        }
    }

    private void loadCategories() {
        categoryTableModel.setRowCount(0); // Clear existing data
        List<Category> categories = categoryController.getAllCategories();
        for (Category category : categories) {
            categoryTableModel.addRow(new Object[]{
                    category.getId(),
                    category.getName(),
                    category.getDescription()
            });
        }
    }

    private void loadItemTypes() {
        itemTypeTableModel.setRowCount(0); // Clear existing data
        List<ItemType> itemTypes = itemTypeController.getItemTypesByCategory(1); // Ganti dengan kategori yang sesuai
        for (ItemType itemType : itemTypes) {
            itemTypeTableModel.addRow(new Object[]{
                    itemType.getId(),
                    itemType.getName(),
                    itemType.getCategoryId(),
                    itemType.getDescription()
            });
        }
    }

    private void loadItems() {
        itemTableModel.setRowCount(0); // Clear existing data
        int selectedRow = itemTypeTable.getSelectedRow(); // Dapatkan baris yang dipilih dari tabel jenis item
        if (selectedRow != -1) {
            // Ambil ID jenis item yang dipilih
            int itemTypeId = (int) itemTypeTable.getValueAt(selectedRow, 0);
            // Panggil controller untuk mendapatkan item berdasarkan jenis item
            List<Item> items = itemController.getItemsByType(itemTypeId);
            for (Item item : items) {
                itemTableModel.addRow(new Object[]{
                        item.getId(),
                        item.getName(),
                        item.getCategoryName(),
                        item.getPrice(),
                        item.getStock()
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item terlebih dahulu!");
        }
    }
    

    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            int userId = (int) userTableModel.getValueAt(selectedRow, 0);
            userController.deleteUser(userId);
            loadUsers();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih akun yang ingin dihapus!");
        }
    }

    private void deleteCategory() {
        int selectedRow = categoriesTable.getSelectedRow();
        if (selectedRow != -1) {
            int categoryId = (int) categoryTableModel.getValueAt(selectedRow, 0);
            categoryController.deleteCategory(categoryId);
            loadCategories();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih kategori yang ingin dihapus!");
        }
    }

    private void deleteItemType() {
        int selectedRow = itemTypeTable.getSelectedRow();
        if (selectedRow != -1) {
            int itemTypeId = (int) itemTypeTableModel.getValueAt(selectedRow, 0);
            itemTypeController.deleteItemType(itemTypeId);
            loadItemTypes();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih jenis item yang ingin dihapus!");
        }
    }

    private void deleteItem() {
        int selectedRow = itemsTable.getSelectedRow();
        if (selectedRow != -1) {
            int itemId = (int) itemTableModel.getValueAt(selectedRow, 0);
            itemController.deleteItem(itemId);
            loadItems();
        } else {
            JOptionPane.showMessageDialog(this, "Silahkan pilih item yang ingin dihapus!");
        }
    }
}
