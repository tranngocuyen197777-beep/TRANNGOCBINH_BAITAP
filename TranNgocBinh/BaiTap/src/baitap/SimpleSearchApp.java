/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baitap;

/**
 *
 * @author Admin
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

// Model sản phẩm
class Product {
    String name;
    String website;
    double price;
    String url;

    Product(String name, String website, double price, String url) {
        this.name = name;
        this.website = website;
        this.price = price;
        this.url = url;
    }
}

// Dịch vụ tìm kiếm sản phẩm (mô phỏng)
class SearchService {
    private static final String[] WEBSITES = {"ShopA", "ShopB", "ShopC"};

    public List<Product> search(String keyword) {
        List<Product> results = new ArrayList<>();
        Random rand = new Random();
        for (String site : WEBSITES) {
            int count = 3 + rand.nextInt(3); // mỗi website trả về 3-5 sản phẩm
            for (int i = 1; i <= count; i++) {
                String name = keyword + " Product " + i;
                double price = 10 + rand.nextDouble() * 90; // giá 10-100$
                String url = "https://" + site.toLowerCase() + ".com/" + name.replace(" ", "_");
                results.add(new Product(name, site, price, url));
            }
        }
        return results;
    }
}

// Giao diện chính
public class SimpleSearchApp extends JFrame {
    private DefaultTableModel tableModel;
    private SearchService searchService = new SearchService();

    public SimpleSearchApp() {
        super("Simple Product Search");

        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        JTextField searchField = new JTextField();
        JButton searchBtn = new JButton("Search");
        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchBtn, BorderLayout.EAST);

        // Bảng kết quả
        String[] columns = {"Website", "Product Name", "Price", "URL"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);

        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);

        // Nút tìm kiếm
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String keyword = searchField.getText().trim();
                if (keyword.isEmpty()) {
                    JOptionPane.showMessageDialog(SimpleSearchApp.this, "Please enter a keyword!");
                    return;
                }
                List<Product> results = searchService.search(keyword);
                refreshTable(results);
            }
        });

        setVisible(true);
    }

    private void refreshTable(List<Product> results) {
        tableModel.setRowCount(0);
        for (Product p : results) {
            tableModel.addRow(new Object[]{p.website, p.name, String.format("%.2f", p.price), p.url});
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimpleSearchApp();
            }
        });
    }
}
