/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ManagerItem;

import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import Service.ItemService;
import Entity.Item;
import Entity.Supplier;
import java.awt.Dimension;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author Thanh Vi
 */
public class ItemsTable extends javax.swing.JFrame {

      Vector<String> headerItem = new Vector<>();
      Vector dataItem = new Vector();
      Vector<String> headerSup = new Vector<>();
      Vector dataSup = new Vector();
       List<Supplier> listSup;
      boolean addNew = true;
      boolean changed = false;
      int SUCCESS=1;
      Supplier suppliers;
      List<String> CB;
      DefaultTableModel defaultModel1;
      DefaultTableModel defaultModel2;
      int totalPage=0;
      int Last=0;
      int totalPageSearch=0;
      int LastSearch=0;
      int pageNoItems=0;
      int pageNoItemsSearch=0;
      int totalPageSup=0;
      int LastSup=0;
      int pageNoSup=0;
      Vector results = new Vector();
      String keyword="";
      boolean display=false;
      String language;
      String country;
      ResourceBundle resource;
      
      public ItemsTable(String language,String country) {
            initComponents();
            int index=jcbLanguage.getSelectedIndex();
            if(index==0){
                this.language = new String ("vi");
                this.country = new String ("VN");
                jcbLanguage.setSelectedIndex(0);
                  
            }else if(index==1){
                  this.language = new String ("en");
                  this.country = new String ("US");
                  jcbLanguage.setSelectedIndex(1);
            }
            Locale currentLocale;
            
            currentLocale = new Locale(this.language,this.country);
          
            String base ="ManagerItem.ResourceFiles/Resources";
            
            resource = ResourceBundle.getBundle(base, currentLocale);
            setUpGUI(index);
            jComboBox1.addItem("Choose Supplier");
            loadDataSup();
            loadDataItem();
            Paging();
            PagingSup();
            //------------------------------------------
            defaultModel1 = (DefaultTableModel) tblItem.getModel();
            defaultModel1.setDataVector(dataItem, headerItem);
            defaultModel2 = (DefaultTableModel) tblSup.getModel();
            defaultModel2.setDataVector(dataSup, headerSup);
            //-------------------------------------------
            tfCode.setEditable(false);
            tfName.setEditable(false);
            tfUnit.setEditable(false);
            tfPrice.setEditable(false);
            jComboBox1.setEnabled(false);
            jCheckBox1.setEnabled(false);
            Save.setEnabled(false);
            Remove.setEnabled(false);
            SaveS.setEnabled(false);
            RemoveS.setEnabled(false);
            SCode.setEditable(false);
            SName.setEditable(false);
            SAdrr.setEditable(false);
            jCheckBox2.setEnabled(false);
            reset.setEnabled(false);
            }

      void setUpGUI(int index){
            
            
            jLabel1.setText(resource.getString("jLabel1"));
            jLabel2.setText(resource.getString("jLabel2"));
            jLabel3.setText(resource.getString("jLabel3"));
            jLabel4.setText(resource.getString("jLabel4"));
            jLabel5.setText(resource.getString("jLabel5"));
            jLabel6.setText(resource.getString("jLabel6"));
            jLabel11.setText(resource.getString("jLabel11"));
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resource.getString("Itemdetails"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
                                                , javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); 
            jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, resource.getString("jPanel4"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION
                                                , javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); 
            AddNewItem.setText(resource.getString("AddNewItem"));
            Save.setText(resource.getString("Save"));
            Remove.setText(resource.getString("Remove"));
            btnChange.setText(resource.getString("btnChange"));
            jcbLanguage.removeAllItems();
            jcbLanguage.addItem(resource.getString("jcbLanguage0"));
            jcbLanguage.addItem(resource.getString("jcbLanguage1"));
            jcbLanguage.setSelectedIndex(index);
            btnlast.setText(resource.getString("btnlast"));
            btnnext.setText(resource.getString("btnnext"));
            Search.setText(resource.getString("Search"));
            reset.setText(resource.getString("reset"));
           System.out.println(headerItem.size()); 
            if (headerItem.size()>0) {
                  headerItem.removeAllElements();
                  System.out.println(headerItem.size());
            }
            System.out.println(headerSup.size());
            if (headerSup.size()>0) {
                  headerSup.removeAllElements();
                  System.out.println(headerSup.size());
            }
            headerItem.add(resource.getString("Code1"));
            headerItem.add(resource.getString("Name1"));
            headerItem.add(resource.getString("Supplier1"));
            headerItem.add(resource.getString("Unit1"));
            headerItem.add(resource.getString("Price1"));
            headerItem.add(resource.getString("Suppling1"));
            //-----------------------------------------------------------------------
            jLabel7.setText(resource.getString("jLabel7"));
            jLabel8.setText(resource.getString("jLabel8"));
            jLabel10.setText(resource.getString("jLabel10"));
            jLabel9.setText(resource.getString("jLabel9"));
            jLabel12.setText(resource.getString("jLabel12"));
            addNewS.setText(resource.getString("AddNewItem"));
            SaveS.setText(resource.getString("Save"));
            RemoveS.setText(resource.getString("Remove"));
            LAST.setText(resource.getString("btnlast"));
            NEXT.setText(resource.getString("btnnext"));
            headerSup.add(resource.getString("Code1"));
            headerSup.add(resource.getString("Name1"));
            headerSup.add(resource.getString("Adress1"));
            headerSup.add(resource.getString("Colloborating1"));        
            jTabbedPane1.setTitleAt(0, resource.getString("ManagerItems"));
            jTabbedPane1.setTitleAt(1, resource.getString("ManagerSuppliers"));
            
      }
      void loadDataItem(){
            try{
                   List<Item> list = ItemService.getForPaging(0,5);
                   pageNoItems=0;
                   for(Item i:list){
                         Vector row = new Vector();
                         row.add(i.getICode()); 
                         row.add(i.getIName()); 
                         row.add(i.getIsub());
                         row.add(i.getIUnit());
                         row.add(i.getIprice());
                         row.add(i.isSuplying());
                         dataItem.add(row);
                   }
            }catch (Exception e){
                  e.printStackTrace();        
                   }
      }
      
      void loadDataSup(){
            try{
                   List<Supplier> list = ItemService.getAllSup();
                   listSup = list;
                   pageNoSup=0;
                   list  = ItemService.getForPagingSup(0,5);
                   for(Supplier cb:listSup){
                         jComboBox1.addItem(cb.getSupName());
                   }
                   for(Supplier s:list){
                         Vector row = new Vector();
                         row.add(s.getSupCode()); 
                         row.add(s.getSupName()); 
                         row.add(s.getSupAdr());
                         row.add(s.isColloborating());
                         dataSup.add(row);
                   }
            }catch (Exception e){
                  e.printStackTrace();            
                   }
      }
      /**
       * This method is called from within the constructor to initialize the
       * form. WARNING: Do NOT modify this code. The content of this method is
       * always regenerated by the Form Editor.
       */
      @SuppressWarnings("unchecked")
      // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
      private void initComponents() {

            jTabbedPane1 = new javax.swing.JTabbedPane();
            jPanel2 = new javax.swing.JPanel();
            jScrollPane1 = new javax.swing.JScrollPane();
            tblItem = new javax.swing.JTable();
            jPanel3 = new javax.swing.JPanel();
            jLabel1 = new javax.swing.JLabel();
            jLabel2 = new javax.swing.JLabel();
            tfCode = new javax.swing.JTextField();
            tfName = new javax.swing.JTextField();
            jLabel3 = new javax.swing.JLabel();
            jComboBox1 = new javax.swing.JComboBox<>();
            tfPrice = new javax.swing.JTextField();
            tfUnit = new javax.swing.JTextField();
            jLabel4 = new javax.swing.JLabel();
            jLabel5 = new javax.swing.JLabel();
            AddNewItem = new javax.swing.JButton();
            Save = new javax.swing.JButton();
            Remove = new javax.swing.JButton();
            jCheckBox1 = new javax.swing.JCheckBox();
            jLabel6 = new javax.swing.JLabel();
            jPanel4 = new javax.swing.JPanel();
            NameToSearch = new javax.swing.JTextField();
            Search = new javax.swing.JButton();
            reset = new javax.swing.JButton();
            btnlast = new javax.swing.JButton();
            btnnext = new javax.swing.JButton();
            jLabel11 = new javax.swing.JLabel();
            jcbLanguage = new javax.swing.JComboBox<>();
            btnChange = new javax.swing.JButton();
            jPanel1 = new javax.swing.JPanel();
            jScrollPane2 = new javax.swing.JScrollPane();
            tblSup = new javax.swing.JTable();
            LAST = new javax.swing.JButton();
            NEXT = new javax.swing.JButton();
            jPanel5 = new javax.swing.JPanel();
            jLabel7 = new javax.swing.JLabel();
            jLabel8 = new javax.swing.JLabel();
            jLabel9 = new javax.swing.JLabel();
            jLabel10 = new javax.swing.JLabel();
            SCode = new javax.swing.JTextField();
            SName = new javax.swing.JTextField();
            SAdrr = new javax.swing.JTextField();
            jCheckBox2 = new javax.swing.JCheckBox();
            RemoveS = new javax.swing.JButton();
            SaveS = new javax.swing.JButton();
            addNewS = new javax.swing.JButton();
            jLabel12 = new javax.swing.JLabel();

            setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
            setBackground(new java.awt.Color(0, 0, 0));

            jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jPanel2.setBackground(new java.awt.Color(255, 204, 204));

            tblItem.setBackground(new java.awt.Color(255, 153, 153));
            tblItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            tblItem.setModel(new javax.swing.table.DefaultTableModel(
                  new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                  },
                  new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                  }
            ));
            tblItem.setRowHeight(20);
            tblItem.setSelectionBackground(new java.awt.Color(255, 102, 102));
            tblItem.addMouseListener(new java.awt.event.MouseAdapter() {
                  public void mouseClicked(java.awt.event.MouseEvent evt) {
                        tblItemMouseClicked(evt);
                  }
            });
            jScrollPane1.setViewportView(tblItem);

            jPanel3.setBackground(new java.awt.Color(255, 204, 204));
            jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Item Details:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

            jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel1.setText("Item Code:");

            jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel2.setText("Item Name:");

            tfCode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            tfName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel3.setText("Unit:");

            jComboBox1.setBackground(new java.awt.Color(255, 153, 153));
            jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jComboBox1.setForeground(new java.awt.Color(255, 153, 153));

            tfPrice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            tfUnit.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel4.setText("Suppliers:");

            jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel5.setText("Price:");

            AddNewItem.setBackground(new java.awt.Color(255, 153, 153));
            AddNewItem.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            AddNewItem.setText("Add New");
            AddNewItem.setToolTipText("");
            AddNewItem.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        AddNewItemActionPerformed(evt);
                  }
            });

            Save.setBackground(new java.awt.Color(255, 153, 153));
            Save.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            Save.setText("Save");
            Save.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        SaveActionPerformed(evt);
                  }
            });

            Remove.setBackground(new java.awt.Color(255, 153, 153));
            Remove.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            Remove.setText("Remove");
            Remove.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        RemoveActionPerformed(evt);
                  }
            });

            jCheckBox1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jCheckBox1.setToolTipText("");

            jLabel6.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel6.setText("Supplying:");

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                  jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addComponent(AddNewItem, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                              .addGroup(jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addComponent(jLabel1)
                                          .addComponent(jLabel2)
                                          .addComponent(jLabel4)
                                          .addComponent(jLabel3)
                                          .addComponent(jLabel5)
                                          .addComponent(jLabel6))
                                    .addGap(14, 14, 14)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                          .addComponent(tfUnit)
                                          .addComponent(tfPrice)
                                          .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                      .addComponent(jCheckBox1)
                                                      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                                            .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addGap(40, 40, 40)
                                                            .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                      .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(tfCode, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(0, 0, Short.MAX_VALUE)))))
                        .addContainerGap())
            );
            jPanel3Layout.setVerticalGroup(
                  jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(tfCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(tfUnit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(tfPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                              .addComponent(jCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(AddNewItem, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(Save, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(Remove, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jPanel4.setBackground(new java.awt.Color(255, 204, 204));
            jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search Item By Name:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N

            NameToSearch.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            Search.setBackground(new java.awt.Color(255, 153, 153));
            Search.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            Search.setText("Search");
            Search.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        SearchActionPerformed(evt);
                  }
            });

            reset.setBackground(new java.awt.Color(255, 153, 153));
            reset.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            reset.setText("Reset");
            reset.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        resetActionPerformed(evt);
                  }
            });

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                  jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(NameToSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
            );
            jPanel4Layout.setVerticalGroup(
                  jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(NameToSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(Search, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            btnlast.setBackground(new java.awt.Color(255, 153, 153));
            btnlast.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            btnlast.setText("Last");
            btnlast.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnlastActionPerformed(evt);
                  }
            });

            btnnext.setBackground(new java.awt.Color(255, 153, 153));
            btnnext.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            btnnext.setText("Next");
            btnnext.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnnextActionPerformed(evt);
                  }
            });

            jLabel11.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
            jLabel11.setText("ITEMS TABLE");

            jcbLanguage.setBackground(new java.awt.Color(255, 153, 153));
            jcbLanguage.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jcbLanguage.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vienamese", "English" }));
            jcbLanguage.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        jcbLanguageActionPerformed(evt);
                  }
            });

            btnChange.setBackground(new java.awt.Color(255, 153, 153));
            btnChange.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            btnChange.setText("Change");
            btnChange.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        btnChangeActionPerformed(evt);
                  }
            });

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                  jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                              .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                          .addGap(256, 256, 256)
                                          .addComponent(btnlast)
                                          .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                          .addComponent(btnnext))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                          .addContainerGap()
                                          .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 670, javax.swing.GroupLayout.PREFERRED_SIZE))))
                              .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(50, 50, 50)
                                    .addComponent(jLabel11)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jcbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnChange)
                                    .addGap(24, 24, 24)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(44, Short.MAX_VALUE))
            );
            jPanel2Layout.setVerticalGroup(
                  jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(11, 11, 11)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                      .addComponent(jcbLanguage, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                      .addComponent(btnChange, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                          .addComponent(jLabel11))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(16, 16, 16)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                          .addComponent(btnnext)
                                          .addComponent(btnlast))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                              .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Manager Items", jPanel2);

            jPanel1.setBackground(new java.awt.Color(224, 224, 247));

            tblSup.setBackground(new java.awt.Color(204, 153, 255));
            tblSup.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            tblSup.setModel(new javax.swing.table.DefaultTableModel(
                  new Object [][] {
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null},
                        {null, null, null, null}
                  },
                  new String [] {
                        "Title 1", "Title 2", "Title 3", "Title 4"
                  }
            ));
            tblSup.setGridColor(new java.awt.Color(204, 255, 255));
            tblSup.setRowHeight(20);
            tblSup.setSelectionBackground(new java.awt.Color(153, 102, 255));
            tblSup.addMouseListener(new java.awt.event.MouseAdapter() {
                  public void mouseClicked(java.awt.event.MouseEvent evt) {
                        tblSupMouseClicked(evt);
                  }
            });
            jScrollPane2.setViewportView(tblSup);

            LAST.setBackground(new java.awt.Color(204, 153, 255));
            LAST.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            LAST.setText("Last");
            LAST.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        LASTActionPerformed(evt);
                  }
            });

            NEXT.setBackground(new java.awt.Color(204, 153, 255));
            NEXT.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            NEXT.setText("Next");
            NEXT.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        NEXTActionPerformed(evt);
                  }
            });

            jPanel5.setBackground(new java.awt.Color(225, 225, 247));
            jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Supplier Detals:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 18))); // NOI18N
            jPanel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel7.setText("Sup Code:");

            jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel8.setText("Sup Name:");

            jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel9.setText("Colloborating:");

            jLabel10.setBackground(new java.awt.Color(225, 225, 247));
            jLabel10.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            jLabel10.setText("Adress:");

            SCode.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            SName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            SName.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        SNameActionPerformed(evt);
                  }
            });

            SAdrr.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            jCheckBox2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

            RemoveS.setBackground(new java.awt.Color(204, 153, 255));
            RemoveS.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            RemoveS.setText("Remove");
            RemoveS.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        RemoveSActionPerformed(evt);
                  }
            });

            SaveS.setBackground(new java.awt.Color(204, 153, 255));
            SaveS.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            SaveS.setText("Save");
            SaveS.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        SaveSActionPerformed(evt);
                  }
            });

            addNewS.setBackground(new java.awt.Color(204, 153, 255));
            addNewS.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
            addNewS.setText("Add New");
            addNewS.addActionListener(new java.awt.event.ActionListener() {
                  public void actionPerformed(java.awt.event.ActionEvent evt) {
                        addNewSActionPerformed(evt);
                  }
            });

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                  jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel7)
                                    .addGap(37, 37, 37)
                                    .addComponent(SCode))
                              .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel8)
                                    .addGap(32, 32, 32)
                                    .addComponent(SName))
                              .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel10)
                                    .addGap(61, 61, 61)
                                    .addComponent(SAdrr))
                              .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jCheckBox2)
                                    .addGap(0, 0, Short.MAX_VALUE))
                              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                    .addComponent(addNewS, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                    .addComponent(SaveS, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(31, 31, 31)
                                    .addComponent(RemoveS, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
            );
            jPanel5Layout.setVerticalGroup(
                  jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(SCode, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(SName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(SAdrr, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addComponent(jCheckBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                              .addComponent(RemoveS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(SaveS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                              .addComponent(addNewS, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
            );

            jLabel12.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
            jLabel12.setText("SUPPLIERS TABLE");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                  jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                          .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 90, Short.MAX_VALUE)
                                                .addComponent(jLabel12)
                                                .addGap(136, 136, 136))
                                          .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jScrollPane2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))))
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(268, 268, 268)
                                    .addComponent(LAST)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(NEXT)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                  jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                              .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                          .addComponent(NEXT)
                                          .addComponent(LAST)))
                              .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            jTabbedPane1.addTab("Manager Suppliers", jPanel1);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
            );
            layout.setVerticalGroup(
                  layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            pack();
      }// </editor-fold>//GEN-END:initComponents

      private void SNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SNameActionPerformed
            // TODO add your handling code here:
      }//GEN-LAST:event_SNameActionPerformed

      private void AddNewItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewItemActionPerformed
            // TODO add your handling code here:
            display=false;
            Paging(); reload(0,5);
            pageNoItems=0;
            NameToSearch. setText("");
            reset.setEnabled(false);
            tfCode.setEditable(true);
            tfName.setEditable(true);
            tfUnit.setEditable(true);
            tfPrice.setEditable(true);
            jComboBox1.setEnabled(true);
            jCheckBox1.setEnabled(true);
            Save.setEnabled(true);
            Remove.setEnabled(false);
            addNew=true;
            tfCode.setText("");
            tfCode.requestFocus();
            tfName.setText("");
            tfPrice.setText("");   
            tfUnit.setText("");
            jCheckBox1.setSelected(false);
            jComboBox1.setSelectedIndex(0);
      }//GEN-LAST:event_AddNewItemActionPerformed

      private void tblItemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblItemMouseClicked
            addNew = false;
            tfCode.setEditable(false);
            tfName.setEditable(true);
            tfUnit.setEditable(true);
            tfPrice.setEditable(true);
            jComboBox1.setEnabled(true);
            jCheckBox1.setEnabled(true);
            Save.setEnabled(true);
            Remove.setEnabled(true);
            int selectedRow = tblItem.getSelectedRow();
            String code = tblItem.getValueAt(selectedRow, 0).toString();
            String name = tblItem.getValueAt(selectedRow, 1).toString();
            String supplier = tblItem.getValueAt(selectedRow, 2).toString();
            String unit = tblItem.getValueAt(selectedRow, 3).toString();
            String price = tblItem.getValueAt(selectedRow, 4).toString();
            String supplying = tblItem.getValueAt(selectedRow, 5).toString();
            //---------------------------------
            tfCode.setText(code);
            tfName.setText(name);
            tfUnit.setText(unit);
            tfPrice.setText(price);
            int index=0;
            for (int i = 0; i <listSup.size(); i++) {
                  if((supplier).equals(listSup.get(i).getSupCode())){ 
                        index=i;
                        jComboBox1.setSelectedIndex(index+1);
                        break;
                  }
            }
            if(supplying.equals("true")){
                  jCheckBox1.setSelected(true);
            }else  jCheckBox1.setSelected(false);
                  
      }//GEN-LAST:event_tblItemMouseClicked

      private void tblSupMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSupMouseClicked
            addNew = false;
            SCode.setEditable(false);
            SaveS.setEnabled(true);
            RemoveS.setEnabled(true);
            jCheckBox2.setEnabled(true);
            SName.setEditable(true);
            SAdrr.setEditable(true);
            int selectedRow = tblSup.getSelectedRow();
            String code = tblSup.getValueAt(selectedRow, 0).toString();
            String name = tblSup.getValueAt(selectedRow, 1).toString();
            String adress = tblSup.getValueAt(selectedRow, 2).toString();
            String col = tblSup.getValueAt(selectedRow, 3).toString();
            //---------------------------------
            SCode.setText(code);
            SName.setText(name);
            SAdrr.setText(adress);
            if(col.equals("true")){
                  jCheckBox2.setSelected(true);
            }else  jCheckBox2.setSelected(false);
            
      }//GEN-LAST:event_tblSupMouseClicked

      private void addNewSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addNewSActionPerformed
            addNew=true;
            SaveS.setEnabled(true);
            RemoveS.setEnabled(false);
            SCode.setEditable(true);
            SName.setEditable(true);
            SAdrr.setEditable(true);
            jCheckBox2.setEnabled(true);
            reset.setEnabled(false);
            SCode.setText("");
            SCode.setEditable(true);
            SCode.requestFocus();
            SName.setText("");
            SAdrr.setText("");
            jCheckBox2.setSelected(false);
            
      }//GEN-LAST:event_addNewSActionPerformed

      private void SaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveActionPerformed
            
            String Code = tfCode.getText().trim().toUpperCase();
            String Name = tfName.getText().trim();
            String Unit = tfUnit.getText().trim();
            int posCb=jComboBox1.getSelectedIndex();
            boolean Supplying = jCheckBox1.isSelected();
            String StrPrice=tfPrice.getText().trim();
            if(!Code.matches("[A-Z]\\d{4}")){
                  JOptionPane.showMessageDialog(this,resource.getString("nf1"));
                  tfCode.requestFocus();
                  tfCode.setText("");
                  return ;
            }
            if(Name.length()==0){
                  JOptionPane.showMessageDialog(this, resource.getString("nf2"));
                  tfName.requestFocus();
                   tfName.setText("");
                  return ;
            }
            if(jComboBox1.getSelectedIndex()==0){
                  JOptionPane.showMessageDialog(this,resource.getString("nf3"));
                  return;
            }
            if(Unit.length()==0){
                  JOptionPane.showMessageDialog(this, resource.getString("nf4"));
                  return;
            }
            if(!StrPrice.matches("\\d+")){
                  JOptionPane.showMessageDialog(this, resource.getString("nf5"));
                  tfPrice.requestFocus();
                  tfPrice.setText("");
                  return;
            }
            int Price=Integer.parseInt(StrPrice);
            if(Price<=0){
                  JOptionPane.showMessageDialog(this, resource.getString("nf6"));
                  tfPrice.requestFocus();
                  return ;
            }
            Item i = new Item(Code, Name, listSup.get(posCb-1).getSupCode(), Unit, Price, Supplying);
            if(addNew){ 
                  try {   
                  if(ItemService.getEmployeeByCodeItem(Code)!=null){
                        JOptionPane.showMessageDialog(this, resource.getString("nf7"));
                        return;
                  }
                        int result =ItemService.insertItem(i);
                        if(result==SUCCESS){
                              Vector row = new Vector();
                              row.add(i.getICode()); 
                              row.add(i.getIName()); 
                              row.add(i.getIsub());
                              row.add(i.getIUnit());
                              row.add(i.getIprice());
                              row.add(i.isSuplying());
                              dataItem.add(row);
                              JOptionPane.showMessageDialog(this, resource.getString("nf8"));
                              Paging(); 
                              SearchIndexOFItems(i.getICode());
                        }else{
                              JOptionPane.showMessageDialog(this,  resource.getString("nf9"));
                        }
                  } catch (Exception ex) {
                        ex.printStackTrace();
                  }
            }else{
                  try {
                        int result =ItemService.updateItem(i);
                        System.out.println(result);
                        if(result==SUCCESS){
                              int selectedRow  = tblItem.getSelectedRow();
                              Vector row = (Vector) dataItem.get(selectedRow);
                              row.set(1, Name);
                              row.set(2,  listSup.get(posCb-1).getSupCode());
                              row.set(3, Unit);
                              row.set(4, Price);
                              row.set(5, Supplying);
                              JOptionPane.showMessageDialog(this,resource.getString("nf10"));
                              if(display){ 
                                    PagingSearh(keyword); 
                                    reloadSearch(0, 5);
                                    pageNoItemsSearch=0;
                              }else{
                                    Paging(); 
                                    SearchIndexOFItems(i.getICode());
                              }
                        }
                  } catch (Exception ex) {
                        ex.getStackTrace();
                  }
            }
            tblItem.updateUI();//update  from model (data) to view (Jtable)
            tfCode.setText("");
            tfCode.setEditable(true);
            tfCode.requestFocus();
            tfName.setText("");
            tfPrice.setText("");
            tfUnit.setText("");
            jCheckBox1.setSelected(false);
            jComboBox1.setSelectedIndex(0);
            changed = true;
      }//GEN-LAST:event_SaveActionPerformed

      public void SearchIndexOFItems(String CheckCode){
            int index=0; int page=0;
            System.out.println("a");
            try {
                  index=ItemService.SearchIndexItems(CheckCode);
                  for (int j = 0; j <totalPage ; j++) {
                        int recordIndex=(5*(j));
                        System.out.println("b"+j);
                        if(index<=recordIndex+4){
                              pageNoItems=j;
                              System.out.println("pageno:"+pageNoItems);
                              break;
                        }
                  }
                        int offset = pageNoItems*5;
                        if(pageNoItems==totalPage-1){
                              btnnext.setEnabled(false);
                              if (totalPage == 0||totalPage == 1) {
                                       btnlast.setEnabled(false);
                                 } else {
                                       btnlast.setEnabled(true);
                                 }
                              int fetch = Last;
                               if(fetch==0){
                                    reload(offset, 5);
                              }else
                              reload(offset, fetch);
                        }else if(pageNoItems==0){
                              btnlast.setEnabled(false);
                               if (totalPage == 0||totalPage == 1) {
                                       btnnext.setEnabled(false);
                                 } else {
                                       btnnext.setEnabled(true);
                                 }
                              reload(0, 5);
                        }
                        else{
                              reload(offset, 5);
                              btnlast.setEnabled(true);
                        }
            } catch (Exception e) {
                  e.getStackTrace();
            }       
      }
      public void SearchIndexOF(String CheckCode){
            int index=0; int page=0;
            System.out.println("pppppppp");
            System.out.println("tps:"+totalPageSearch);
            try {
                  index=ItemService.SearchIndex(CheckCode,keyword);
                  for (int j = 0; j <totalPageSearch ; j++) {
                        int recordIndex=(5*(j));
                        System.out.println("j:"+j);
                        if(index<=recordIndex+4){
                              pageNoItemsSearch=j;
                              System.out.println("ps:"+pageNoItemsSearch);
                              break;
                        }
                  }
                  try {
                        int offset = pageNoItemsSearch*5;
                        
                        if((pageNoItemsSearch+1)==totalPageSearch-1){
                              btnnext.setEnabled(false);
                              if (totalPageSearch == 0 || totalPageSearch==1) {
                                       btnlast.setEnabled(false);
                                 } else {
                                       btnlast.setEnabled(true);
                                 }
                              int fetch = LastSearch;
                              if(fetch==0){
                                    reloadSearch(offset, 5);
                              }
                              else reloadSearch(offset, fetch);
                        }else if((pageNoItemsSearch--)==0){
                              btnlast.setEnabled(false);
                               if (totalPageSearch == 0 || totalPageSearch==1) {
                                       btnnext.setEnabled(false);
                                 } else {
                                       btnnext.setEnabled(true);
                                 }
                              reloadSearch(0, 5);
                        }
                        else{
                              reloadSearch(offset, 5);
                              btnlast.setEnabled(true);
                        }
                        } catch (Exception e) {
                                          e.getStackTrace();
                                    }
            } catch (Exception e) {
                  e.getStackTrace();
            }
      }
      
      public void SearchIndexOFSup(String CheckCode){
            int index=0; int page=0;
            try {
                  index=ItemService.SearchIndexSup(CheckCode);
                  for (int j = 0; j <totalPageSup ; j++) {
                        int recordIndex=(5*(j));
                        if(index<=recordIndex+4){
                              pageNoSup=j;
                              break;
                        }
                  }
                  try {
                        int offset = pageNoSup*5;
                        if(pageNoSup==totalPageSup-1){
                              NEXT.setEnabled(false);
                              if (totalPageSup == 0) {
                                       LAST.setEnabled(false);
                                 } else {
                                       LAST.setEnabled(true);
                                 }
                              int fetch = LastSup;
                               if(fetch==0){
                                    reloadSup(offset, 5);
                              }else
                              reloadSup(offset, fetch);
                        }else if(pageNoSup==0){
                              LAST.setEnabled(false);
                               if (totalPageSup == 0) {
                                       NEXT.setEnabled(false);
                                 } else {
                                       NEXT.setEnabled(true);
                                 }
                              reloadSup(offset, 5);
                        }
                        else{
                              reloadSup(offset, 5);
                              LAST.setEnabled(true);
                        }
                        } catch (Exception e) {
                                          e.getStackTrace();
                                    }
            } catch (Exception e) {
            }
      }
      
      private void RemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveActionPerformed
            int r = JOptionPane.showConfirmDialog(this, resource.getString("nf12"), resource.getString("nf13"), JOptionPane.YES_NO_OPTION);
            if(r==JOptionPane.YES_OPTION){
                  int selectedRow = tblItem.getSelectedRow();
                  String code= tblItem.getValueAt(selectedRow,0).toString();
                  try {
                        if(ItemService.deleleItem(code)==SUCCESS){
                              dataItem.remove(selectedRow);
                              JOptionPane.showMessageDialog(this, resource.getString("nf11"));
                              if(display){
                                    PagingSearh(keyword);
                                    reloadSearch(0, 5);
                                    pageNoItemsSearch=0;
                              }else{
                                    Paging();
                                    reload(0, 5);
                                    pageNoItems=0;
                              }
                        }
                  } catch (Exception e) {
                  }                
            }
            tfCode.setText("");
            tfCode.setEditable(true);
            tfCode.requestFocus();
            tfName.setText("");
            tfPrice.setText("");   
            tfUnit.setText("");
            jCheckBox1.setSelected(false);
            jComboBox1.setSelectedIndex(0);
      }//GEN-LAST:event_RemoveActionPerformed

      private void SearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SearchActionPerformed
            display=true;
            reset.setEnabled(true);
            keyword= NameToSearch.getText().trim().toUpperCase();
            PagingSearh(keyword);
            loadSearch(keyword);
      }//GEN-LAST:event_SearchActionPerformed

      private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
            display=false;
            Paging(); reload(0,5);
            pageNoItems=0;
            reset.setEnabled(false);
            addNew=true;
            NameToSearch. setText("");
      }//GEN-LAST:event_resetActionPerformed

      private void SaveSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveSActionPerformed
            String Code = SCode.getText().trim().toUpperCase();
            String Name = SName.getText().trim();
            String Adr=SAdrr.getText().trim();
            boolean Col = jCheckBox2.isSelected();
            if(!Code.matches("[A-Z]{2,5}")){
                  JOptionPane.showMessageDialog(this, resource.getString("nf16"));
                  tfCode.requestFocus();
                  tfCode.setText("");
                  return ;
            }
            if(Name.length()==0){
                  JOptionPane.showMessageDialog(this, resource.getString("nf14"));
                  tfName.requestFocus();
                   tfName.setText("");
                  return ;
            }
            if(Adr.length()==0){
                  JOptionPane.showMessageDialog(this, resource.getString("nf15"));
                  return;
            }
            Supplier s = new Supplier(Code, Name, Adr, Col);
            if(addNew){ 
                  try {   
                  if(ItemService.getEmployeeByCodeSup(Code)!=null){
                        JOptionPane.showMessageDialog(this,  resource.getString("nf7"));
                        return;
                  }
                        int result =ItemService.insertSub(s);
                        if(result==SUCCESS){
                              Vector row = new Vector();
                              row.add(s.getSupCode()); 
                              row.add(s.getSupName()); 
                              row.add(s.getSupAdr());
                              row.add(s.isColloborating());
                              dataSup.add(row);
                              JOptionPane.showMessageDialog(this,  resource.getString("nf8"));
                              PagingSup(); 
                              List<Supplier> list = ItemService.getAllSup();
                              listSup = list;
                              SearchIndexOFSup(s.getSupCode());
                              jComboBox1.removeAllItems();
                                    jComboBox1.addItem("Choose Supplier");
                                    for (int i = 0; i <listSup.size(); i++) {
                                          jComboBox1.addItem(listSup.get(i).getSupName());
                                    }
                        }else{
                              JOptionPane.showMessageDialog(this,  resource.getString("nf9"));
                        }
                  } catch (Exception ex) {
                        ex.printStackTrace();
                  }
            }else{
                  try {
                        int result =ItemService.updateSub(s);
                        System.out.println(result);
                        if(result==SUCCESS){
                              int selectedRow  = tblSup.getSelectedRow();
                              Vector row = (Vector) dataSup.get(selectedRow);
                              row.set(1, Name);
                              row.set(2,Adr);
                              row.set(3, Col);
                              JOptionPane.showMessageDialog(this, resource.getString("nf10"));
                              List<Supplier> list = ItemService.getAllSup();
                              listSup = list;
                              jComboBox1.removeAllItems();
                                    jComboBox1.addItem("Choose Supplier");
                                    for (int i = 0; i <listSup.size(); i++) {
                                          jComboBox1.addItem(listSup.get(i).getSupName()); 
                                    }
                        }
                  } catch (Exception ex) {
                        ex.getStackTrace();
                  }
            }
            tblSup.updateUI();//update  from model (data) to view (Jtable)
            SaveS.setEnabled(true);
            RemoveS.setEnabled(false);
            SCode.setText("");
            SName.setText("");
            SAdrr.setText("");
            jCheckBox2.setEnabled(false);
            changed = true;
      }//GEN-LAST:event_SaveSActionPerformed

      private void RemoveSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveSActionPerformed
            int r = JOptionPane.showConfirmDialog(this, resource.getString("nf12"), resource.getString("nf13"), JOptionPane.YES_NO_OPTION);
            if(r==JOptionPane.YES_OPTION){
                  int selectedRow = tblSup.getSelectedRow();
                  String code= tblSup.getValueAt(selectedRow,0).toString();
                  List<String> count=null;
                  try {
                        count = ItemService.checkItemToRemove(code);
                  } catch (Exception e) {
                        e.getStackTrace();
                  }
                  if(count.size()>0){
                        JOptionPane.showConfirmDialog(this, resource.getString("nf17"), resource.getString("nf13"), JOptionPane.YES_NO_OPTION);
                        if(r==JOptionPane.YES_OPTION){
                              int flag=0;
                              for (int i = 0; i < count.size(); i++) {
                                    System.out.println(count.get(i));
                                    String codeItems= count.get(i);
                                    System.out.println(codeItems);
                                    try {
                                          if(ItemService.deleleItem(codeItems)==SUCCESS){
                                                Paging();  
                                          }
                                    } catch (Exception e) {
                                          e.getStackTrace();
                                    }                               
                              }
                              display=false;
                              Paging(); 
                              reload(0,5);
                              pageNoItems=0;
                              reset.setEnabled(false);
                              NameToSearch.setText("");
                              try {
                              if(ItemService.deleleSup(code)==SUCCESS){
                                    dataSup.remove(selectedRow);
                                    tblSup.updateUI();
                                    jComboBox1.removeAllItems();
                                    List<Supplier> list = ItemService.getAllSup();
                                    listSup = list;
                                    jComboBox1.addItem("Choose Supplier");
                                    for (int i = 0; i <listSup.size(); i++) {
                                          jComboBox1.addItem(listSup.get(i).getSupName());   
                                    }
                              }
                        } catch (Exception e) {
                              e.getStackTrace();
                        }      
                              tfCode.setText("");
                              tfCode.setEditable(true);
                              tfCode.requestFocus();
                              tfName.setText("");
                              tfPrice.setText("");   
                              tfUnit.setText("");
                              jCheckBox1.setSelected(false);
                              jComboBox1.setSelectedIndex(0);
                               JOptionPane.showMessageDialog(this, resource.getString("nf18"));
                               PagingSup();
                               reloadSup(0, 5);
                               pageNoSup=0;
                        }
                  }else{
                        try {
                              if(ItemService.deleleSup(code)==SUCCESS){
                                    dataSup.remove(selectedRow);
                                    listSup.remove(selectedRow);
                                    tblSup.updateUI();
                                    jComboBox1.removeAllItems();
                                    jComboBox1.addItem("Choose Supplier");
                                    for (int i = 0; i <listSup.size(); i++) {
                                          jComboBox1.addItem(listSup.get(i).getSupName());
                                    }
                                    changed = true;
                              }
                        } catch (Exception e) {
                              e.getStackTrace();
                        }      
                              tfCode.setText("");
                              tfCode.setEditable(true);
                              tfCode.requestFocus();
                              tfName.setText("");
                              tfPrice.setText("");   
                              tfUnit.setText("");
                              jCheckBox1.setSelected(false);
                              jComboBox1.setSelectedIndex(0);
                               JOptionPane.showMessageDialog(this,resource.getString("nf19") );
                               PagingSup();
                               reloadSup(0, 5);
                               pageNoSup=0;                  
                  }                
            }
            SCode.setText("");
            SCode.setEditable(true);
            SCode.requestFocus();
            SName.setText("");
            SAdrr.setText("");
            jCheckBox2.setSelected(false);
      }//GEN-LAST:event_RemoveSActionPerformed

      private void btnlastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnlastActionPerformed

            if(display){
                  System.out.println("display");
                         try {
                  pageNoItemsSearch--;
                  int offset = pageNoItemsSearch*5;
                  if(pageNoItemsSearch==0){
                        btnlast.setEnabled(false);
                         if (totalPageSearch == 0) {
                                 btnnext.setEnabled(false);
                           } else {
                                 btnnext.setEnabled(true);
                           }
                        reloadSearch(0, 5);
                  }else{
                        reloadSearch(offset, 5);
                        btnnext.setEnabled(true);
                  }
                  } catch (Exception e) {
                                    e.getStackTrace();
                              }
            }else{
                  try {
                  pageNoItems--;
                        System.out.println("1:"+pageNoItems);
                  int offset = pageNoItems*5;
                  if(pageNoItems==0){
                        btnlast.setEnabled(false);
                                 btnnext.setEnabled(true);
                                 reload(0, 5);
                  }else{
                        reload(offset, 5);
                        btnlast.setEnabled(true);
                        btnnext.setEnabled(true);
                  }
                  } catch (Exception e) {
                                    e.getStackTrace();
                              }}
      }//GEN-LAST:event_btnlastActionPerformed

      private void btnnextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnextActionPerformed
            // TODO add your handling code here:
            if(display){
                  try {
                        System.out.println("display");
                        pageNoItemsSearch++; 
                        int offset = pageNoItemsSearch*5;
                        if(pageNoItemsSearch==totalPageSearch-1){
                              btnnext.setEnabled(false);
                              if (totalPageSearch == 0 ||totalPageSearch == 1) {
                                       btnlast.setEnabled(false);
                                 } else {
                                       btnlast.setEnabled(true);
                                 }
                              int fetch = LastSearch;
                              if(fetch==0){
                                    reloadSearch(offset, 5);
                              }else
                              reloadSearch(offset, fetch);
                        }else{
                              reloadSearch(offset, 5);
                              btnnext.setEnabled(true);
                              btnlast.setEnabled(true);
                        }
                        } catch (Exception e) {
                                          e.getStackTrace();
                                    }
            }else{
                  try {
                        pageNoItems++;
                        System.out.println("1:"+pageNoItems);
                        int offset = pageNoItems*5;
                        System.out.println("of:"+offset);
                        if((pageNoItems)==totalPage-1){
                              btnnext.setEnabled(false);
                                       btnlast.setEnabled(true);
                              int fetch = Last;
                              if(fetch==0){
                                    reload(offset, 5);
                              }else reload(offset, fetch);   
                        }else{
                              reload(offset, 5);
                              btnlast.setEnabled(true);
                        }
                        } catch (Exception e) {
                                          e.getStackTrace();
                                    }
            }
      }//GEN-LAST:event_btnnextActionPerformed

      private void NEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NEXTActionPerformed

            try {
                        pageNoSup++;
                        int offset = pageNoSup*5;
                        if(pageNoSup==totalPageSup-1){
                              NEXT.setEnabled(false);
                              if (totalPageSup == 0 ||totalPageSup ==1) {
                                       LAST.setEnabled(false);
                                 } else {
                                       LAST.setEnabled(true);
                                 }
                              int fetch = LastSup;
                              if(fetch==0){
                                    reloadSup(offset, 5);
                              }else
                              reloadSup(offset, fetch);
                        }else{
                              reloadSup(offset, 5);
                              LAST.setEnabled(true);
                        }
                        } catch (Exception e) {
                                          e.getStackTrace();
                                    }
      }//GEN-LAST:event_NEXTActionPerformed

      private void LASTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LASTActionPerformed
            // TODO add your handling code here:
            try {
                  pageNoSup--;
                  int offset = pageNoSup*5;
                  if(pageNoSup==0){
                        LAST.setEnabled(false);
                         if (totalPageSup== 0||totalPageSup== 1) {
                                 NEXT.setEnabled(false);
                           } else {
                                 NEXT.setEnabled(true);
                           }
                        reloadSup(0, 5);
                  }else{
                        reloadSup(offset, 5);
                        NEXT.setEnabled(true);
                  }
                  } catch (Exception e) {
                                    e.getStackTrace();
                              }
      }//GEN-LAST:event_LASTActionPerformed

      private void jcbLanguageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbLanguageActionPerformed
            // TODO add your handling code here:
      }//GEN-LAST:event_jcbLanguageActionPerformed

      private void btnChangeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeActionPerformed
            int index=jcbLanguage.getSelectedIndex();
            if(index==0){
                 this.language = new String ("vi");
                this.country = new String ("VN");
            }  
            else if(index==1){
                  this.language = new String ("en");
                  this.country = new String ("US");
            }
            Locale currentLocale;
            currentLocale = new Locale(this.language,this.country);
            String base ="ManagerItem.ResourceFiles/Resources";
            resource = ResourceBundle.getBundle(base, currentLocale);
            setUpGUI(index);
            defaultModel1 = (DefaultTableModel) tblItem.getModel();
            defaultModel1.setDataVector(dataItem, headerItem);
            defaultModel2 = (DefaultTableModel) tblSup.getModel();
            defaultModel2.setDataVector(dataSup, headerSup);
      }//GEN-LAST:event_btnChangeActionPerformed

      void reload(int offset,int fetch){
            try{
                  dataItem = new Vector();
                   List<Item> list =ItemService.getForPaging(offset, fetch);
                   for(Item i:list){
                         Vector row = new Vector();
                         row.add(i.getICode()); 
                         row.add(i.getIName()); 
                         row.add(i.getIsub());
                         row.add(i.getIUnit());
                         row.add(i.getIprice());
                         row.add(i.isSuplying());
                         dataItem.add(row);
                   }
            }catch (Exception e){
                  e.printStackTrace();        
                   }
            defaultModel1.setDataVector(dataItem, headerItem);
            tblItem.updateUI();
      }
      
       void reloadSup(int offset,int fetch){
            try{
                  List<Supplier> list = ItemService.getAllSup();
                   listSup = list;
                  dataSup = new Vector();
                   list =ItemService.getForPagingSup(offset, fetch);
                   for(Supplier s:list){
                         Vector row = new Vector();
                         row.add(s.getSupCode()); 
                         row.add(s.getSupName()); 
                         row.add(s.getSupAdr());
                         row.add(s.isColloborating());
                         dataSup.add(row);
                   }
            }catch (Exception e){
                  e.printStackTrace();        
                   }
            defaultModel2.setDataVector(dataSup, headerSup);
            tblSup.updateUI();
      }
      
      void reloadSearch(int offset,int fetch){
             results  = new Vector();
            try {
                  for(Item i:ItemService.SearchEmployeesbyName(keyword,offset,fetch)){
                        Vector row = new Vector();
                              row.add(i.getICode()); 
                              row.add(i.getIName()); 
                              row.add(i.getIsub());
                              row.add(i.getIUnit());
                              row.add(i.getIprice());
                              row.add(i.isSuplying());
                              results.add(row);
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }
            if(results.size()==0){
                  JOptionPane.showMessageDialog(this, "No Result!");
            }else{
                  defaultModel1.setDataVector(results, headerItem);
            }
      }
      
      void loadSearch(String keyword){
            Vector results  = new Vector();
            pageNoItemsSearch=0;
            try {
                  for(Item i:ItemService.SearchEmployeesbyName(keyword,0,5)){
                        Vector row = new Vector();
                              row.add(i.getICode()); 
                              row.add(i.getIName()); 
                              row.add(i.getIsub());
                              row.add(i.getIUnit());
                              row.add(i.getIprice());
                              row.add(i.isSuplying());
                              results.add(row);
                  }
            } catch (Exception e) {
                  e.printStackTrace();
            }
            if(results.size()==0){
                  JOptionPane.showMessageDialog(this, "No Result!");
            }else{
                  defaultModel1.setDataVector(results, headerItem);
            }
      }
      
      private void Paging(){
                     try {
                        int totalRecords =ItemService.count();
                        int pageSize=5;
                        Double pageSizeD = (double)pageSize;
                        double total=Math.ceil(totalRecords/pageSizeD);
                        totalPage=(int) total;
                           System.out.println("total page:" + totalPage);
                        int recordIndex=0;
                        Last = totalRecords%5;
                        } catch (Exception e) {
                              e.getStackTrace();
                        }
                     if (totalPage == 0 ||totalPage == 1) {
                           btnlast.setEnabled(false);
                           btnnext.setEnabled(false);
                     } else {
                           btnlast.setEnabled(false);
                           btnnext.setEnabled(true);
                     }
            }
      
      
      private void PagingSup(){
                     try {
                        int totalRecords =ItemService.countSup();
                        int pageSize=5;
                        Double pageSizeD = (double)pageSize;
                        double total=Math.ceil(totalRecords/pageSizeD);
                        totalPageSup=(int) total;
                           System.out.println("sup:" +totalPageSup);
                        int recordIndex=0;
                        LastSup = totalRecords%5;
                        } catch (Exception e) {
                              e.getStackTrace();
                        }
                     if (totalPageSup == 0 ||totalPageSup == 1) {
                           LAST.setEnabled(false);
                           NEXT.setEnabled(false);
                     } else {
                           LAST.setEnabled(false);
                           NEXT.setEnabled(true);
                     }
            }
      
      private void PagingSearh(String keyword){
                     try {
                        int totalRecords =ItemService.CountEmployeesbyName(keyword);
                        int pageSize=5;
                        Double pageSizeD = (double)pageSize;
                        double total=Math.ceil(totalRecords/pageSizeD);
                        totalPageSearch=(int) total;
                           System.out.println("1:"+totalPageSearch);
                        int recordIndex=0;
                        LastSearch = totalRecords%5;
                        } catch (Exception e) {
                              e.getStackTrace();
                        }
                     if (totalPageSearch == 0 || totalPageSearch==1) {
                           btnlast.setEnabled(false);
                           btnnext.setEnabled(false);
                     } else {
                           btnlast.setEnabled(false);
                           btnnext.setEnabled(true);
                     }
            }
      
      
                        
      /**
       * @param args the command line arguments
       */
      public static void main(String args[]) {
            /* Set the Nimbus look and feel */
            //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
            /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
             */
            try {
                  for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                              javax.swing.UIManager.setLookAndFeel(info.getClassName());
                              break;
                        }
                  }
            } catch (ClassNotFoundException ex) {
                  java.util.logging.Logger.getLogger(ItemsTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                  java.util.logging.Logger.getLogger(ItemsTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                  java.util.logging.Logger.getLogger(ItemsTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                  java.util.logging.Logger.getLogger(ItemsTable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>

            /* Create and display the form */
            final String language;
            final String country;
            
            
            java.awt.EventQueue.invokeLater(new Runnable() {
                  public void run() {
                        new ItemsTable("vi","VN").setVisible(true);
                  }
            });
      }

      // Variables declaration - do not modify//GEN-BEGIN:variables
      private javax.swing.JButton AddNewItem;
      private javax.swing.JButton LAST;
      private javax.swing.JButton NEXT;
      private javax.swing.JTextField NameToSearch;
      private javax.swing.JButton Remove;
      private javax.swing.JButton RemoveS;
      private javax.swing.JTextField SAdrr;
      private javax.swing.JTextField SCode;
      private javax.swing.JTextField SName;
      private javax.swing.JButton Save;
      private javax.swing.JButton SaveS;
      private javax.swing.JButton Search;
      private javax.swing.JButton addNewS;
      private javax.swing.JButton btnChange;
      private javax.swing.JButton btnlast;
      private javax.swing.JButton btnnext;
      private javax.swing.JCheckBox jCheckBox1;
      private javax.swing.JCheckBox jCheckBox2;
      private javax.swing.JComboBox<String> jComboBox1;
      private javax.swing.JLabel jLabel1;
      private javax.swing.JLabel jLabel10;
      private javax.swing.JLabel jLabel11;
      private javax.swing.JLabel jLabel12;
      private javax.swing.JLabel jLabel2;
      private javax.swing.JLabel jLabel3;
      private javax.swing.JLabel jLabel4;
      private javax.swing.JLabel jLabel5;
      private javax.swing.JLabel jLabel6;
      private javax.swing.JLabel jLabel7;
      private javax.swing.JLabel jLabel8;
      private javax.swing.JLabel jLabel9;
      private javax.swing.JPanel jPanel1;
      private javax.swing.JPanel jPanel2;
      private javax.swing.JPanel jPanel3;
      private javax.swing.JPanel jPanel4;
      private javax.swing.JPanel jPanel5;
      private javax.swing.JScrollPane jScrollPane1;
      private javax.swing.JScrollPane jScrollPane2;
      private javax.swing.JTabbedPane jTabbedPane1;
      private javax.swing.JComboBox<String> jcbLanguage;
      private javax.swing.JButton reset;
      private javax.swing.JTable tblItem;
      private javax.swing.JTable tblSup;
      private javax.swing.JTextField tfCode;
      private javax.swing.JTextField tfName;
      private javax.swing.JTextField tfPrice;
      private javax.swing.JTextField tfUnit;
      // End of variables declaration//GEN-END:variables
}
