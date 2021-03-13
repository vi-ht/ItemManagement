/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Thanh Vi
 */
public class Item {
      String ICode="", IName="", IUnit = "";
      String Isub = null;
      int Iprice=0;
      boolean suplying = false;

      public Item() {
      }
      
      public Item(String Code, String Name, String sub,String unit, int price, boolean suplying) {
            this.ICode=Code;
            this.IName=Name;
            this.suplying=suplying;
            this.Isub=sub;
            this.Iprice=price;
            this.IUnit=unit;
      }

      public String getICode() {
            return ICode;
      }
      public void setICode(String ICode) {
            this.ICode = ICode;
      }
      public String getIName() {
            return IName;
      }
      public void setIName(String IName) {
            this.IName = IName;
      }
      public String getIUnit() {
            return IUnit;
      }
      public void setIUnit(String IUnit) {
            this.IUnit = IUnit;
      }
      public String getIsub() {
            return Isub;
      }
      public void setIsub(String Isub) {
            this.Isub = Isub;
      }
      public int getIprice() {
            return Iprice;
      }
      public void setIprice(int Iprice) {
            this.Iprice = Iprice;
      }
      public boolean isSuplying() {
            return suplying;
      }
      public void setSuplying(boolean suplying) {
            this.suplying = suplying;
      }
      
      
      
}