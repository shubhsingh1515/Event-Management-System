/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package eventmanagementsystem;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class User extends JFrame implements ActionListener {

    private JComboBox<String> itemComboBox;
    private JButton orderButton;
    private JButton log;
    public Connection c;

    public User() {
        try {
            // Establish database connection
             c = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "05aanchal18");


            setTitle("User Page");
            setSize(600, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            itemComboBox = new JComboBox<>();
            orderButton = new JButton("Order Item");

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(5, 5, 5, 5);

            gbc.gridx = 0;
            gbc.gridy = 0;
            add(new JLabel("Select Item:"), gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.gridwidth = 2;
            itemComboBox.setPreferredSize(new Dimension(200, 25));
            add(itemComboBox, gbc);

            gbc.gridx = 1;
            gbc.gridy = 1;
            gbc.gridwidth = 1;
            orderButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    orderItem();
                }
            });
            add(orderButton, gbc);
            
            ImageIcon i1= new ImageIcon(ClassLoader.getSystemResource("electricity_icon/login.png"));
            Image i2= i1.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT);
            log =new JButton("Logout", new ImageIcon(i2));
            log.setBounds(330,160,100,20);
            log.addActionListener(this);
            add(log);

            loadItems();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error connecting to the database", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadItems() {
        try {
            String query = "SELECT nameitem FROM items";
            try (PreparedStatement preparedStatement = c.prepareStatement(query);
                 ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    String itemName = resultSet.getString("nameitem");
                    itemComboBox.addItem(itemName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading items", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void orderItem() {
        String selectedItem = (String) itemComboBox.getSelectedItem();

        if (selectedItem == null) {
            JOptionPane.showMessageDialog(this, "Please select an item to order", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Insert the ordered item into the database
            String query = "INSERT INTO orderlist (itemName) VALUES (?)";
            try (PreparedStatement preparedStatement = c.prepareStatement(query)) {
                preparedStatement.setString(1, selectedItem);
                preparedStatement.executeUpdate();
            }

            JOptionPane.showMessageDialog(this, "Item ordered successfully");

        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error ordering item", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource()==log){
            setVisible(false);
            new login();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new User().setVisible(true));
    }
}