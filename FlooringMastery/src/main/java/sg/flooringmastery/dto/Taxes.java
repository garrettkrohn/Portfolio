 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sg.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author garrettkrohn
 */
public class Taxes {
    String state;
    String stateName;
    BigDecimal taxRate;

    public Taxes(String state, String stateName, BigDecimal taxRate) {
        this.state = state;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getState() {
        return state;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Override
    public String toString() {
        return "state = " + state + ", stateName = " + stateName + ", taxRate = " + taxRate + '}';
    }
    
    
}
