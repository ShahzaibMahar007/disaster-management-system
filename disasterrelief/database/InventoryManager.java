package disasterrelief.database;

import disasterrelief.models.InventoryItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class InventoryManager {

    private InventoryItem head;

    public InventoryManager() {
        // Build the DSA Linked List using data from MySQL!
        loadFromDatabase(); 
    }

    // --- NEW METHOD: Fetch from MySQL and build Linked List ---
    public void loadFromDatabase() {
        head = null;
        InventoryItem current = null;

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM inventory";
            PreparedStatement pst = conn.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String name = rs.getString("item_name");
                int qty = rs.getInt("quantity");
                InventoryItem newItem = new InventoryItem(name, qty);

                // Building the Linked List
                if (head == null) {
                    head = newItem;
                    current = head;
                } else {
                    current.next = newItem;
                    current = current.next;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error: Could not load inventory.");
        }
    }

    // Return items as list for UI use
    public List<InventoryItem> getAllItemsAsList() {
        loadFromDatabase(); // Always ensure we have the freshest data from the DB
        List<InventoryItem> list = new ArrayList<>();
        InventoryItem temp = head;
        while (temp != null) {
            list.add(temp);
            temp = temp.next;
        }
        return list;
    }

    // --- NEW HELPER: Update MySQL Database ---
    private boolean updateDatabase(String itemName, int qtyChange) {
        try {
            Connection conn = DBConnection.getConnection();
            // 1. Get current quantity
            String checkQuery = "SELECT quantity FROM inventory WHERE item_name = ?";
            PreparedStatement checkPst = conn.prepareStatement(checkQuery);
            checkPst.setString(1, itemName);
            ResultSet rs = checkPst.executeQuery();

            if (rs.next()) {
                int currentQty = rs.getInt("quantity");
                int newQty = currentQty + qtyChange;

                if (newQty < 0) return false; // Prevent negative inventory

                // 2. Update database with new quantity
                String updateQuery = "UPDATE inventory SET quantity = ? WHERE item_name = ?";
                PreparedStatement pst = conn.prepareStatement(updateQuery);
                pst.setInt(1, newQty);
                pst.setString(2, itemName);
                pst.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Deduct quantity when allocating
    public boolean deduct(String item, int qty) {
        if (updateDatabase(item, -qty)) { // Update DB (negative number means subtract)
            loadFromDatabase(); // Refresh Linked List
            return true;
        }
        return false;
    }

    // Restock item
    public boolean restockItem(String item, int qty) {
        if (updateDatabase(item, qty)) { // Update DB
            loadFromDatabase(); // Refresh Linked List
            JOptionPane.showMessageDialog(null, item + " successfully restocked!");
            return true;
        }
        return false;
    }

    // Allocate item
    public boolean allocateItem(String itemName, int quantity) {
        return deduct(itemName, quantity); // Just reuse the deduct logic!
    }

    // Find item in the linked list
    private InventoryItem findItem(String name) {
        for (InventoryItem it : getAllItemsAsList()) {
            if (it.getName().equalsIgnoreCase(name)) {
                return it;
            }
        }
        return null;
    }

    // Check availability with message
    public boolean checkAvailability(String itemName, int qty) {
        InventoryItem item = findItem(itemName);
        if (item == null) return false;

        if (item.getQty() == 0) {
            JOptionPane.showMessageDialog(null, itemName + " is OUT OF STOCK. Please restock.");
            return false;
        }

        if (item.getQty() < qty) {
            JOptionPane.showMessageDialog(null, "Not enough " + itemName + ". Available: " + item.getQty());
            return false;
        }

        return true;
    }
}