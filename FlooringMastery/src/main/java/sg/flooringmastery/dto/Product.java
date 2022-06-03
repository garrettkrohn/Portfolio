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
public class Product {
    String productType;
    BigDecimal costPerSquareFoot;
    BigDecimal laborPerSquareFoot;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborPerSquareFoot = laborPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborPerSquareFoot() {
        return laborPerSquareFoot;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setCostPerSquareFoot(BigDecimal costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public void setLaborPerSquareFoot(BigDecimal laborPerSquareFoot) {
        this.laborPerSquareFoot = laborPerSquareFoot;
    }

    @Override
    public String toString() {
        return "productType = " + productType + ", costPerSquareFoot = " + costPerSquareFoot + ", laborPerSquareFoot = " + laborPerSquareFoot;
    }
    
    
    
}
