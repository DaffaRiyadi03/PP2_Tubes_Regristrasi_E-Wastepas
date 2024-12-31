package tubes.ewaste.view;

import tubes.ewaste.controller.ItemController;
import tubes.ewaste.controller.ItemTypeController;
import tubes.ewaste.model.Item;
import tubes.ewaste.model.ItemType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ItemFormDialog extends JDialog {
    private final ItemController itemController;
    private final ItemTypeController itemTypeController;

    private JTextField nameField;
    private JTextArea descriptionField;
    private JComboBox<ItemType> itemTypeComboBox;
    private JButton saveButton;
    private JButton cancelButton;
    private Item item; // Untuk mode update

    public ItemFormDialog(JFrame parent, ItemController itemController, ItemTypeController itemTypeController, String title) {
        super(parent, title, true);
        this.itemController = itemController;
        this.itemTypeController = itemTypeController;

        setTitle(title);
        setSize(400, 300);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        initComponents();
        loadItemTypes();
    }


    private void initComponents() {
        nameField = new JTextField(20);
        descriptionField = new JTextArea(5, 20);
        itemTypeComboBox = new JComboBox<>();
        saveButton = new JButton("Simpan");
        cancelButton = new JButton("Batal");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Nama Item:"));
        panel.add(nameField);
        panel.add(new JLabel("Deskripsi:"));
        panel.add(new JScrollPane(descriptionField));
        panel.add(new JLabel("Jenis Item:"));
        panel.add(itemTypeComboBox);
        panel.add(saveButton);
        panel.add(cancelButton);

        add(panel);

        // Listener untuk tombol Save
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveItem();
            }
        });

        // Listener untuk tombol Cancel
        cancelButton.addActionListener(e -> dispose());
    }

    private void loadItemTypes() {
        List<ItemType> itemTypes = itemTypeController.getAllItemTypes(); // Ambil data dari DB
        itemTypeComboBox.removeAllItems(); // Bersihkan comboBox
    
        for (ItemType itemType : itemTypes) {
            itemTypeComboBox.addItem(itemType); // Tambahkan item type ke comboBox
        }
    }
    

    private void saveItem() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        ItemType selectedItemType = (ItemType) itemTypeComboBox.getSelectedItem(); // Ambil item type terpilih

        if (name.isEmpty() || description.isEmpty() || selectedItemType == null) {
            JOptionPane.showMessageDialog(this, "Semua kolom harus diisi!");
            return;
        }

        if (item == null) {
            // Tambahkan item baru
            Item newItem = new Item();
            newItem.setName(name);
            newItem.setDescription(description);
            newItem.setItemTypeId(selectedItemType.getId());
            itemController.addItem(newItem);
        } else {
            // Update item yang ada
            item.setName(name);
            item.setDescription(description);
            item.setItemTypeId(selectedItemType.getId());
            itemController.updateItem(item);
        }

        dispose(); // Tutup dialog setelah simpan/update
    }

    // Set data untuk mode update
    // Set nama item di form
    public void setItemName(String name) {
        nameField.setText(name);
    }

    // Set deskripsi item di form
    public void setItemDescription(String description) {
        descriptionField.setText(description);
    }

    // Set item type yang dipilih di comboBox
    public void setItemTypeComboBoxSelectedItem(ItemType itemType) {
        itemTypeComboBox.setSelectedItem(itemType);
    }

    // Set data untuk mode update
    public void setItem(Item item) {
        this.item = item;
        if (item != null) {
            nameField.setText(item.getName());
            descriptionField.setText(item.getDescription());
            
            // Ambil ItemType berdasarkan ID menggunakan getItemTypesById
            ItemType itemType = itemTypeController.getItemTypesById(item.getItemTypeId());
            
            if (itemType != null) {
                itemTypeComboBox.setSelectedItem(itemType);
            } else {
                JOptionPane.showMessageDialog(this, "Jenis Item dengan ID ini tidak ditemukan!");
            }
        }
    }


    // Getter untuk nilai field
    public String getItemName() {
        return nameField.getText();
    }

    public String getItemDescription() {
        return descriptionField.getText();
    }

    public ItemType getSelectedItemType() {
        return (ItemType) itemTypeComboBox.getSelectedItem();
    }

    // Listener untuk tombol Save dari parent view
    public void addSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
}
