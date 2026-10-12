/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gamezone.model;
import java.time.LocalDate;

/**
 *
 * @author irind
 */
public class PercentageDiscount extends Promotion {
        private double percentage;
        
        public PercentageDiscount(String id, String name, LocalDate startDate, LocalDate endDate, double percentage){
        
            super(id,name,startDate,endDate);
            this.percentage = percentage;
        }
        
        public double getpercentage(){return percentage;}
        public void setpercentage(double percentage){this.percentage = percentage;}
        
        @Override
        public double calculateDiscount(Sale sale){
            if (sale == null){
                return 0;
            }
            
            double subtotal = sale.calculateprice();
            return subtotal*(percentage/100);
        }
       
        public String getpromotionType(){
            return "PERCENTAGE";
        }
    
}
