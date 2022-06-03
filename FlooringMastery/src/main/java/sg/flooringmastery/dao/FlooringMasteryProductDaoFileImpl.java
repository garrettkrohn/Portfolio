/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sg.flooringmastery.dao;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import sg.flooringmastery.dto.Product;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryProductDaoFileImpl implements FlooringMasteryProductDao {
    
    public static final String DELIMITER = ",";
    public final String PRODUCT_FILE = "Products.txt";
    public List<Product> productList = new ArrayList<Product>();
    
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException {
        
        List<Product> productList = new ArrayList<Product>();
        
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                new BufferedReader(
                    new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
            "Could not load the Products into memory", e);
        }
        String currentLine;
        
        Product currentProduct;
        String throwAway = scanner.nextLine();
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            productList.add(currentProduct);
        }
        
        scanner.close();
        return productList;
    }
    
    public Product unmarshallProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);
        String productName = productTokens[0];
        BigDecimal costPerSquareFoot = new BigDecimal(productTokens[1]);
        BigDecimal laborPerSquareFoot = new BigDecimal(productTokens[2]);
        Product returnProduct = new Product(productName, costPerSquareFoot, laborPerSquareFoot);
        return returnProduct;
    }
}
