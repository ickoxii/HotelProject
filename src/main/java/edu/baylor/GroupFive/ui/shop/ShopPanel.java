package edu.baylor.GroupFive.ui.shop;

import javax.swing.*;

import edu.baylor.GroupFive.database.controllers.StockController;
import edu.baylor.GroupFive.models.Stock;
import edu.baylor.GroupFive.ui.shop.dialogs.AddToCartDialog;
import edu.baylor.GroupFive.ui.shop.dialogs.CheckoutDialog;
import edu.baylor.GroupFive.ui.shop.dialogs.RemoveFromCartDialog;
import edu.baylor.GroupFive.ui.utils.Page;
import edu.baylor.GroupFive.ui.utils.buttons.PanelButton;
import edu.baylor.GroupFive.ui.utils.interfaces.PagePanel;
import edu.baylor.GroupFive.ui.utils.table.FormPane;
import edu.baylor.GroupFive.ui.utils.table.HotelModel;
import edu.baylor.GroupFive.ui.utils.table.HotelTable;
import edu.baylor.GroupFive.util.logging.G5Logger;

import java.awt.*;

/**
 * Panel for the shop.
 * 
 * Implements {@link edu.baylor.GroupFive.ui.utils.interfaces.PagePanel}.
 *
 * @see edu.baylor.GroupFive.ui.utils.interfaces.PagePanel
 * @author Brendon
 * @author Siri
 */
public class ShopPanel extends JPanel implements PagePanel {


    private Page page;
    private HotelTable table;
    private HotelTable cartTable;
    private AddShopModel model;
    private JLabel subtotalLabel;
    //private ProductTable table;

    private String[] columnNames = {
            "Product ID",
            "Description",
            "Cost",
            "# In Stock"
    };

    private String[] cartColumnNames = {
        "Product ID",
        "Description",
        "Cost",
        "# in Cart"
    };

    final Class<?>[] columnClass = new Class[] {
            String.class, String.class, String.class, Integer.class
    };


    /**
     * Constructs a new ShopPanel with the specified delegate
     *
     * // @param delegate Page
     */
    public ShopPanel(Page page) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.page = page;

        // Create a model of the data.
        model = new AddShopModel(columnNames, columnClass);

        // Create a table with a sorter.
        table = new HotelTable(model);

        cartTable = new HotelTable(new HotelModel(cartColumnNames, columnClass));

        // Add the table to a scroll pane.
        JScrollPane scrollPane = new JScrollPane(table);
        JScrollPane cartScrollPane = new JScrollPane(cartTable);

        // Add some glue
        add(Box.createVerticalGlue());

        // Add a title
        JLabel title = new JLabel("Products");
        title.setFont(new java.awt.Font("Arial", Font.BOLD, 36));
        title.setAlignmentX(Panel.CENTER_ALIGNMENT);
        add(title);

        // Add some glue
        add(Box.createVerticalGlue());

        // Add the scroll pane to this panel.
        add(scrollPane);
        add(cartScrollPane);

        // Add the form pane
        add(new FormPane(table, table.getSorter(), columnNames));

        // Add the button panel.
        addButtonPanel();

        // Add some glue
        add(Box.createVerticalGlue());

    }


    public void updateSubTotal(){
        double subTotal = 0;
        for(int row = 0; row < cartTable.getRowCount(); row++){
            int numItem = (Integer) cartTable.getValueAt(row, 3);
            double unitPrice = Double.parseDouble((String) cartTable.getValueAt(row, 2));
            subTotal += numItem*unitPrice;
        }
        double finalSubTotal = subTotal;
        SwingUtilities.invokeLater(() -> {
            this.subtotalLabel.setText("Subtotal: "+ finalSubTotal);
        });

    }


    // TODO: Implement AddToCartDialog first - Siri
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();

        this.subtotalLabel = new JLabel("Subtotal: 0.00");
        buttonPanel.add(this.subtotalLabel);

        PanelButton addToCartButton = new PanelButton("Add To Cart");
        addToCartButton.addActionListener(e -> {
            // Show the dialog to add a room.
            AddToCartDialog dialog = new AddToCartDialog(this, table, cartTable, subtotalLabel);
            dialog.setVisible(true);
        });
        addToCartButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel.add(addToCartButton);

        PanelButton removeFromCartButton = new PanelButton("Remove From Cart");
        removeFromCartButton.addActionListener(e -> {
            RemoveFromCartDialog dialog = new RemoveFromCartDialog(this, cartTable, subtotalLabel);
            dialog.setVisible(true);
        });
        removeFromCartButton.setPreferredSize(new Dimension(300, 100));
        buttonPanel.add(removeFromCartButton);

        PanelButton checkoutButton = new PanelButton("Checkout");
        checkoutButton.addActionListener(e -> {
            CheckoutDialog dialog = new CheckoutDialog(this, cartTable, subtotalLabel);
            dialog.setVisible(true);
        });
        checkoutButton.setPreferredSize(new Dimension(200, 100));
        buttonPanel.add(checkoutButton);



        add(buttonPanel);
    }

    /**
     * Clears the panel (but does nothing atm).
     */
    @Override
    public void clear() {
        // TODO Auto-generated method stub
    }

}
