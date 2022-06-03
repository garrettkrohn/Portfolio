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
import sg.flooringmastery.dto.Taxes;
import sg.flooringmastery.service.FlooringMasteryPersistenceException;

/**
 *
 * @author garrettkrohn
 */
public class FlooringMasteryTaxesDaoFileImpl implements FlooringMasteryTaxesDao {
    
    public static final String DELIMITER = ",";
    public final String TAXES_FILE = "Taxes.txt";
    private List<Taxes> taxesList = new ArrayList<Taxes>();
    
    public List<Taxes> getAllStates() throws FlooringMasteryPersistenceException {
        
        List<Taxes> taxesList = new ArrayList<Taxes>();
        
        Scanner scanner;
        
        try {
            scanner = new Scanner(
                new BufferedReader(
                    new FileReader(TAXES_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
            "Could not load the Taxes into memory", e);
        }
        String currentLine;
        
        Taxes currentTaxes;
        String throwAway = scanner.nextLine();
        while(scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTaxes = unmarshallTaxes(currentLine);
            taxesList.add(currentTaxes);
        }
        scanner.close();
        return taxesList;
        
    }
    
        
    public Taxes unmarshallTaxes(String taxesAsText){
        String[] taxTokens = taxesAsText.split(DELIMITER);
        String state = taxTokens[0];
        String stateName = taxTokens[1];
        BigDecimal taxRate = new BigDecimal(taxTokens[2]);
        Taxes returnTaxes = new Taxes(state, stateName, taxRate);
        return returnTaxes;
    }
}
