/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sg.flooringmastery.dao;

import java.util.List;
import sg.flooringmastery.dto.Product;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public interface FlooringMasteryProductDao {
    
    public List<Product> getAllProducts() throws FlooringMasteryPersistenceException;
    
    public Product unmarshallProduct(String productAsText);
}
