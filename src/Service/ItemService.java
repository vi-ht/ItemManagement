/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

/**
 *
 * @author Thanh Vi
 */
import Entity.Item;
import Entity.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
public class ItemService {
      static String driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";
      static String url="jdbc:sqlserver://localhost\\MSSQLSERVER_VI:1433; databaseName=ItemDB;"+" user=sa; password=1102";
      
      public static Connection openConnection() throws SQLException, ClassNotFoundException{
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url);
            return c;
      }
      
       public static List<Item> SearchEmployeesbyName(String keyword, int offset,int fetch) throws Exception{
            List<Item> list = new ArrayList<>();
            int index=0;
            String sql  = "Select * From Items Where itemName Like ? ORDER BY itemName OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
            try(Connection c = openConnection();
                  PreparedStatement sm = c.prepareStatement(sql)){
                  sm.setString(1, "%"+keyword+"%");
                  sm.setInt(2, offset);
                  sm.setInt(3, fetch);
                  ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  Item i = new Item(rs.getString("itemCode"),rs.getString("itemName"), rs.getString("supCode"),
                                                      rs.getString("unit"),rs.getInt("price"), rs.getBoolean("supplying"));
                  list.add(i);
            }
            }return list;
        }
       
       public static int CountEmployeesbyName(String keyword) throws Exception{
            List<Item> list = new ArrayList<>();
            int TOTAL=0;
            String sql  = "SELECT COUNT(*) AS TOTAL FROM Items Where itemName Like ?";
            try(Connection c = openConnection();
                  PreparedStatement sm = c.prepareStatement(sql)){
                  sm.setString(1, "%"+keyword+"%");
                  ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  TOTAL=rs.getInt("TOTAL");
                  System.out.println("total: " +TOTAL);
            }
            }return TOTAL;
        }
       
       public static int SearchIndexSup(String CheckCode) throws Exception{
             List<String> list = new ArrayList<>();
            String sql  = "Select * From Suppliers ORDER BY SupName";
            try(Connection c = openConnection();
                  Statement sm =c.createStatement()){
                  ResultSet rs = sm.executeQuery(sql);
            while(rs.next()){
                  String code=rs.getString("SupCode");
                  list.add(code);
            }     
      }
            int index=0;
             for (int i = 0; i < list.size(); i++) {
                   if(CheckCode.toUpperCase().equals(list.get(i))){
                         index=i;
                         break;
                   }
             }
            return index;
        }
       
             public static int SearchIndexItems(String CheckCode) throws Exception{
             List<String> list = new ArrayList<>();
            String sql  = "Select * From Items ORDER BY itemName";
            try(Connection c = openConnection();
                  Statement sm =c.createStatement()){
                  ResultSet rs = sm.executeQuery(sql);
            while(rs.next()){
                  String code=rs.getString("itemCode");
                  list.add(code);
            }     
      }
            int index=0;
             for (int i = 0; i < list.size(); i++) {
                   if(CheckCode.toUpperCase().equals(list.get(i))){
                         index=i;
                         break;
                   }
             }
             System.out.println("index:"+index);
            return index;
                   
        }
       
       
       public static int SearchIndex(String CheckCode,String keyword) throws Exception{
             System.out.println("search");
             List<String> list = new ArrayList<>();
            String sql  = "Select * From Items Where itemName Like ? ORDER BY itemName ";
            try(Connection c = openConnection();
                  PreparedStatement sm = c.prepareStatement(sql)){
                  sm.setString(1, "%"+keyword+"%");
                  ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  String code=rs.getString("itemCode");
                  list.add(code);
            }     
      }
            int index=0;
             for (int i = 0; i < list.size(); i++) {
                   if(CheckCode.toUpperCase().equals(list.get(i).toUpperCase())){
                         index=i;
                         break;
                   }
             }
             System.out.println("indexaaaa:"+index);
            return index;
        }
      
      public static int deleleItem(String code)throws Exception{
            String sql = "Delete From Items Where itemCode = ?";
            try(Connection c = openConnection();
                    PreparedStatement ps = c.prepareStatement(sql)){
                  ps.setString(1, code);
                  return ps.executeUpdate();
            }
      }
      
      public static int updateItem(Item i)throws Exception{
            String sql = "Update Items set itemName = ?, supCode = ?, unit = ?, price = ?, supplying = ? Where itemCode = ?";
            try(Connection c = openConnection();
                  PreparedStatement ps = c.prepareStatement(sql)){
                  ps.setString(1, i.getIName());
                  ps.setString(2, i.getIsub());
                  ps.setString(3, i.getIUnit());
                  ps.setInt(4, i.getIprice());
                  ps.setBoolean(5, i.isSuplying());
                  ps.setString(6, i.getICode());
                  //--------------------------
                  return ps.executeUpdate();
            }
      }
      
      public static int insertItem(Item i)throws Exception{
            String sql = "Insert Items Values(?,?,?,?,?,?)";
            try(Connection c = openConnection();
                    PreparedStatement ps = c.prepareStatement(sql)){
                  ps.setString(1, i.getICode());
                  ps.setString(2, i.getIName());
                  ps.setString(3, i.getIsub());
                  ps.setString(4, i.getIUnit());
                  ps.setInt(5, i.getIprice());
                  ps.setBoolean(6, i.isSuplying());
                  return ps.executeUpdate();
            }
      }
      
      public static Item getEmployeeByCodeItem(String code)throws Exception{
            String sql = "Select * From Items Where itemCode = ?";
            try(Connection c = openConnection();
                    PreparedStatement sm = c.prepareStatement(sql)){
                  sm.setString(1, code);
                  int index=0;
            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  Item i = new Item(rs.getString("itemCode"),rs.getString("itemName"), rs.getString("supCode"),
                                                      rs.getString("unit"),rs.getInt("price"), rs.getBoolean("supplying"));
                  return i;
            }
            return null;
      }}
      
      public static List<String> checkItemToRemove(String code)throws Exception{
            List<String> list = new ArrayList<>();
            String sql = "Select * From Items Where supCode = ? ORDER BY itemName";
            try(Connection c = openConnection();
                    PreparedStatement sm = c.prepareStatement(sql)){
                  sm.setString(1, code);
            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  list.add(rs.getString("itemCode"));
            }
            return list;
      }}
      
       public static List<Supplier> getAllSup() throws Exception{
            List<Supplier> list = new ArrayList<>();
            String sql  = "Select * From Suppliers ORDER BY SupName";
            try(Connection c = openConnection();
                  Statement sm =c.createStatement()){
                  ResultSet rs = sm.executeQuery(sql);
            while(rs.next()){
                  String code=rs.getString("SupCode");
                  String name=rs.getString("SupName");
                  String adr = rs.getString("Address");
                  boolean coloborating = rs.getBoolean("colloborating");
                  //---------------------------------------------
                  Supplier s = new Supplier(code, name, adr, coloborating);
                  list.add(s);
            }
      }
            return list;
}

     public static Supplier getEmployeeByCodeSup(String code)throws Exception{
            String sql = "Select * From Suppliers Where SupCode = ?";
            try(Connection c = openConnection();
                    PreparedStatement sm = c.prepareStatement(sql)){
                  sm.setString(1, code);
                  int index=0;
            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  Supplier s = new Supplier(rs.getString("SupCode"), rs.getString("SupName"), rs.getString("Address"), rs.getBoolean("colloborating"));
                  return s;
            }
            return null;
      }}  
     
     public static int insertSub(Supplier s)throws Exception{
            String sql = "Insert Suppliers Values(?,?,?,?)";
            try(Connection c = openConnection();
                    PreparedStatement ps = c.prepareStatement(sql)){
                  ps.setString(1, s.getSupCode());
                  ps.setString(2, s.getSupName());
                  ps.setString(3, s.getSupAdr());
                  ps.setBoolean(4, s.isColloborating());
                  return ps.executeUpdate();
            }
      }
      
     public static int updateSub(Supplier s)throws Exception{
            String sql = "Update Suppliers set SupName = ?, Address = ?, colloborating = ? Where SupCode = ?";
            try(Connection c = openConnection();
                     PreparedStatement ps = c.prepareStatement(sql)){
                  ps.setString(1, s.getSupName());
                  ps.setString(2, s.getSupAdr());
                  ps.setBoolean(3, s.isColloborating());
                   ps.setString(4, s.getSupCode());
                  //--------------------------
                  return ps.executeUpdate();
            }
      }
     
      public static int deleleSup(String code)throws Exception{
            String sql = "Delete From Suppliers Where SupCode = ?";
            try(Connection c = openConnection();
                    PreparedStatement ps = c.prepareStatement(sql)){
                  ps.setString(1, code);
                  return ps.executeUpdate();
            }
      }
      public static int count() throws Exception{
            int TOTAL=0;
            String sql  = "SELECT COUNT(*) AS TOTAL FROM Items";
            try(Connection c = openConnection();
                  Statement sm =c.createStatement()){
                  ResultSet rs = sm.executeQuery(sql);
                  while(rs.next()){
                        TOTAL=rs.getInt("TOTAL");
                        System.out.println("total: " +TOTAL);
            }      
            }
            return TOTAL;
      }
      
      public static int countSup() throws Exception{
            int TOTAL=0;
            String sql  = "SELECT COUNT(*) AS TOTAL FROM Suppliers";
            try(Connection c = openConnection();
                  Statement sm =c.createStatement()){
                  ResultSet rs = sm.executeQuery(sql);
                  while(rs.next()){
                        TOTAL=rs.getInt("TOTAL");
                        System.out.println("total: " +TOTAL);
            }      
            }
            return TOTAL;
      }
           
      
      public static List<Item> getForPaging(int offset, int fetch) throws Exception{
            List<Item> list = new ArrayList<>();
            Supplier getSup=null;
            int index=0;
            String sql  = "SELECT * FROM Items ORDER BY itemName OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
             try(Connection c = openConnection();
                    PreparedStatement sm = c.prepareStatement(sql)){
                    sm.setInt(1, offset);
                    sm.setInt(2, fetch);
            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  String code=rs.getString("itemCode");
                  String name=rs.getString("itemName");
                  String supCode = rs.getString("supCode");
                  String unit=rs.getString("unit");
                  int price=rs.getInt("price");
                  boolean Suppling = rs.getBoolean("supplying");
                  //---------------------------------------------
                  Item i = new Item(code, name, supCode,unit, price, Suppling);
                  list.add(i);
            }     
      }
            return list;
}
      
      public static List<Supplier> getForPagingSup(int offset, int fetch) throws Exception{
            List<Supplier> list = new ArrayList<>();
            Supplier getSup=null;
            int index=0;
            String sql  = "SELECT * FROM Suppliers ORDER BY SupName OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
            try(Connection c = openConnection();
                    PreparedStatement sm = c.prepareStatement(sql)){
                    sm.setInt(1, offset);
                    sm.setInt(2, fetch);
            ResultSet rs = sm.executeQuery();
            while(rs.next()){
                  String code=rs.getString("SupCode");
                  String name=rs.getString("SupName");
                  String adr = rs.getString("Address");
                  boolean coloborating = rs.getBoolean("colloborating");
                  //---------------------------------------------
                  Supplier s = new Supplier(code, name, adr, coloborating);
                  list.add(s);
            }     
      }
            return list;
}
}
