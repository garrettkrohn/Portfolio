/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package sg.flooringmastery.dao;

import java.util.List;
import sg.flooringmastery.dto.Taxes;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public interface FlooringMasteryTaxesDao {
    public List<Taxes> getAllStates() throws FlooringMasteryPersistenceException;
    
    public Taxes unmarshallTaxes(String taxesAsText);
}
