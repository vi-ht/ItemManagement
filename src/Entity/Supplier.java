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
public class Supplier {
      String SupCode ="", SupName="", SupAdr="";
      boolean colloborating=true;

      public Supplier() {
      }
      
      public Supplier(String code, String Name, String Adr, boolean Col) {
            this.SupCode=code;
            this.SupName=Name;
            this.SupAdr=Adr;
            this.colloborating=Col;
      }

      public String getSupCode() {
            return SupCode;
      }
      public void setSupCode(String SupCode) {
            this.SupCode = SupCode;
      }
      public String getSupName() {
            return SupName;
      }
      public void setSupName(String SupName) {
            this.SupName = SupName;
      }
      public String getSupAdr() {
            return SupAdr;
      }
      public void setSupAdr(String SupAdr) {
            this.SupAdr = SupAdr;
      }
      public boolean isColloborating() {
            return colloborating;
      }
      public void setColloborating(boolean colloborating) {
            this.colloborating = colloborating;
      }

      @Override
      public String toString() {
            return SupCode +"-"+ SupName ;
      }

      
      
}
