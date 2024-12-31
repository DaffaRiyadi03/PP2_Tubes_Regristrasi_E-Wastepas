package tubes.ewaste.model;

public class Item {
    private int id;
    private String name;
    private String description;
    private int itemTypeId;  
    private ItemType itemType; 

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(int itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setCategory(ItemType itemType) {
        this.itemType = itemType;
    }
}
