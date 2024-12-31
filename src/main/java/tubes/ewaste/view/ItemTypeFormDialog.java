package tubes.ewaste.view;

import tubes.ewaste.controller.ItemTypeController;
import tubes.ewaste.controller.CategoryController;
import tubes.ewaste.model.ItemType;
import tubes.ewaste.model.Category;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemTypeFormDialog extends JDialog {
    private final ItemTypeController itemTypeController;
    private final CategoryController categoryController;

    private JTextField nameField;
    private JTextArea descriptionField;
    private JComboBox<Category> categoryComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private ItemType itemType; // For updating an existing ItemType

    public ItemTypeFormDialog(JFrame parent, ItemTypeController itemTypeController, CategoryController categoryController, String title) {
        super(parent, title, true);
        this.itemTypeController = itemTypeController;
        this.categoryController = categoryController;

        setTitle(title);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        loadCategories();
    }

    private void initComponents() {
        nameField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        categoryComboBox = new JComboBox<>();
        saveButton = new JButton("Simpan");
        cancelButton = new JButton("Batal");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Nama Jenis Item:"));
        panel.add(nameField);
        panel.add(new JLabel("Deskripsi:"));
        panel.add(new JScrollPane(descriptionField));
        panel.add(new JLabel("Kategori:"));
        panel.add(categoryComboBox);
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);

        // Save button listener to handle saving or updating the item type
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItemType();
            }
        });

        // Cancel button listener to close the dialog
        cancelButton.addActionListener(e -> dispose());
    }

    private void loadCategories() {
        List<Category> categories = categoryController.getAllCategories();  // Fetch categories from DB
        categoryComboBox.removeAllItems();  // Clear existing items
    
        for (Category category : categories) {
            categoryComboBox.addItem(category);  // Add category objects to comboBox
        }
    }
    

    private void saveItemType() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        Category selectedCategory = (Category) categoryComboBox.getSelectedItem();  // Get selected category
    
        if (name.isEmpty() || description.isEmpty() || selectedCategory == null) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!");
            return;
        }
    
        if (itemType == null) {
            // Create new ItemType
            ItemType newItemType = new ItemType();
            newItemType.setName(name);
            newItemType.setDescription(description);
            newItemType.setCategoryId(selectedCategory.getId());  // Use category ID
            itemTypeController.addItemType(newItemType);
        } else {
            // Update existing ItemType
            itemType.setName(name);
            itemType.setDescription(description);
            itemType.setCategoryId(selectedCategory.getId());  // Use category ID
            itemTypeController.updateItemType(itemType);
        }
    
        dispose();
    }
    

    // Set data for the update form
    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
        if (itemType != null) {
            nameField.setText(itemType.getName());
            descriptionField.setText(itemType.getDescription());
            categoryComboBox.setSelectedItem(itemType.getCategory()); // Set the category in the combo box
        }
    }

    // Getters for ItemType Form data
    public String getItemTypeName() {
        return nameField.getText();
    }

    public String getItemTypeDescription() {
        return descriptionField.getText();
    }

    public Category getSelectedCategory() {
        return (Category) categoryComboBox.getSelectedItem();
    }
    
    public void setItemTypeName(String name) {
        nameField.setText(name);
    }

    public void setItemTypeDescription(String description) {
        descriptionField.setText(description);
    }

    public void setCategoryComboBoxSelectedItem(Category category) {
        categoryComboBox.setSelectedItem(category);
    }

    // Add this method to create listener for save button from the parent view
    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
}
