package work;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static work.ControlDrugs.reformatString;
import static work.IFunctionPostgreSqlPV.*;

public class DrugFilter extends JFrame {
    DefaultListModel defaultListModel = new DefaultListModel();
    private DefaultListModel filteredItemsToAddNewList = new DefaultListModel();
    private JFrame frame;

    private JPanel panelListForSelect1;
    private JScrollPane jScrollPane1, jScrollPane2, jScrollPane3;
    private JList<String> myJList1;
    private JList<String> myJList2;
    private JTextArea jTextArea3;
    private JLabel searchLabel1;
    private JTextField searchTxt1;

    private JPanel panelListSelectedItem2;
    private JPanel panelShowResult3;

    private JButton clear, result;


    private FunctionPostgreSQL dbf = new FunctionPostgreSQL(host, port, user, pass, database);
    private ArrayList<String> drugList = new ArrayList<>(dbf.readColumnDate(nameTable, columnName).stream().sorted().collect(Collectors.toList()));
    private ArrayList<String> myList2 = new ArrayList<>();

    public DrugFilter() {

        initialize();
        this.bindData();

    }// end constructor

    private void initialize() {
        frame = new JFrame("Compatibility Drugs");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1050, 950);
        frame.setLayout(new BorderLayout(8, 10));
        frame.setLocationRelativeTo(null);


        panelListForSelect1 = new JPanel(); // panelListForSelect 1 from 10
        panelListForSelect1.setBackground(Color.GRAY); // panelListForSelect 3 from 10


        myJList1 = new JList<>();
        myJList1.setFont(new Font("Arial", 1, 18));
        myJList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        myJList1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                myJListMouseClicked(evt);
            }
        });

        jScrollPane1 = new JScrollPane(); // jScrollPane 1 from 4
        jScrollPane1.setViewportView(myJList1); // jScrollPane 2 from 4

        searchTxt1 = new JTextField(10); // searchTxt 1 from 6
        searchTxt1.setFont(new Font("Tahoma", 0, 18)); // searchTxt 2 from 6
        searchTxt1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent evt) {
                searchTxtKeyReleased(evt);
            }
        }); // searchTxt 4 from 6

        searchLabel1 = new JLabel(); // searchLabel 1 from 6
        searchLabel1.setFont(new Font("Tahoma", 1, 12)); // searchLabel 2 from 6
        searchLabel1.setForeground(new Color(28, 37, 78)); // searchLabel 3 from 6
        searchLabel1.setText("Search/Filter"); // searchLabel 4 from 6


        panelListSelectedItem2 = new JPanel();
        panelListSelectedItem2.setBackground(Color.ORANGE); // panelListForSelect 3 from 10

        myJList2 = new JList<>();
        myJList2.setFont(new Font("Arial", 1, 18));
        myJList2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jScrollPane2 = new JScrollPane(); // jScrollPane 1 from 4
        jScrollPane2.setViewportView(myJList2); // jScrollPane 2 from 4

        clear = new JButton("Clear All");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filteredItemsToAddNewList.clear();
                jTextArea3.removeAll();
            }
        });


        panelShowResult3 = new JPanel();
        panelShowResult3.setBackground(Color.DARK_GRAY); // panelListForSelect 3 from 10

        jTextArea3 = new JTextArea("Show result", 20, 20);
        jTextArea3.setFont(new Font("Tahoma", 1, 16));

        jScrollPane3 = new JScrollPane(); // jScrollPane 1 from 4
        jScrollPane3.setViewportView(jTextArea3); // jScrollPane 2 from 4


        result = new JButton("Show Result");
        result.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    jTextArea3.setText(compatibilityController(filteredItemsToAddNewList));
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        playLayout(panelListForSelect1, searchLabel1, searchTxt1, jScrollPane1);
        playLayout(panelListSelectedItem2, jScrollPane2, clear);
        playLayoutChangeDimension(panelShowResult3, jScrollPane3, result);

        frame.add(panelListForSelect1, BorderLayout.LINE_START); // panelListForSelect 10 from 10
        frame.add(panelListSelectedItem2, BorderLayout.AFTER_LINE_ENDS); // panelListForSelect 10 from 10
        frame.add(panelShowResult3, BorderLayout.AFTER_LAST_LINE); // panelListForSelect 10 from 10

    }//end method initialize()

    private void bindData() {
        // foreach whit functional operation
        drugList.forEach(defaultListModel::addElement);
        myJList1.setModel(defaultListModel);
        myJList1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

    }// end method private void bindData()

    private void searchFilter(String searchTerm) {
        DefaultListModel filteredItems = new DefaultListModel();

        drugList.stream().forEach((drug) -> {
            String drugName = drug.toString().toLowerCase();
            if (drugName.contains(searchTerm.toLowerCase())) {
                filteredItems.addElement(drug);
            }
        });

        defaultListModel = filteredItems;
        myJList1.setModel(defaultListModel);

    }// end method private void searchFilter(String searchTerm)

    private void myJListMouseClicked(MouseEvent evt) {

        if (!filteredItemsToAddNewList.contains(myJList1.getSelectedValue()))
            filteredItemsToAddNewList.addElement(myJList1.getSelectedValue());

        defaultListModel = filteredItemsToAddNewList;

        myJList2.setModel(defaultListModel);

    } //end method myJListMouseClicked(MouseEvent evt)

    private void searchTxtKeyReleased(KeyEvent evt) {
        searchFilter(searchTxt1.getText()); //searchTxt 3 from 6
    }

    private void playLayout(JPanel jPanel, JLabel jLabel, JTextField jTextField, JScrollPane jScrollPane) {
        GroupLayout jPanelLayout = new GroupLayout(jPanel); // panelListForSelect 4 from 10
        jPanel.setLayout(jPanelLayout); // panelListForSelect 5 from 10
        jPanelLayout.setHorizontalGroup(
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel)// searchLabel 5 from 6
                                                .addComponent(jTextField, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)) // searchTxt 5 from 6
                                        .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)) // jScrollPane 3 from 4
                                .addContainerGap(88, Short.MAX_VALUE))
        );

        jPanelLayout.setVerticalGroup(  // am ramas aici
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE) // searchTxt 6 from 6
                                        .addComponent(jLabel))                    // searchLabel 6 from 6
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)                      // jScrollPane 4 from 4
                                .addContainerGap())
        );
    }// end method playLayout(JPanel jPanel, JLabel jLabel, JTextField jTextField, JScrollPane jScrollPane)

    private void playLayout(JPanel jPanel, JScrollPane jScrollPane, JButton jButton) {
        GroupLayout jPanelLayout = new GroupLayout(jPanel); // panelListForSelect 4 from 10
        jPanel.setLayout(jPanelLayout); // panelListForSelect 5 from 10
        jPanelLayout.setHorizontalGroup(
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanelLayout.createSequentialGroup()
                                                .addComponent(jButton)
                                                .addGap(18, 18, 18))
                                        .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 423, GroupLayout.PREFERRED_SIZE)) // jScrollPane 3 from 4
                                .addContainerGap(88, Short.MAX_VALUE))
        );

        jPanelLayout.setVerticalGroup(  // am ramas aici
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton))                    // searchLabel 6 from 6
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)                      // jScrollPane 4 from 4
                                .addContainerGap())
        );
    }// end method playLayout(JPanel jPanel, JScrollPane jScrollPane, JButton jButton)
    private void playLayoutChangeDimension(JPanel jPanel, JScrollPane jScrollPane, JButton jButton) {
        GroupLayout jPanelLayout = new GroupLayout(jPanel); // panelListForSelect 4 from 10
        jPanel.setLayout(jPanelLayout); // panelListForSelect 5 from 10
        jPanelLayout.setHorizontalGroup(
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addGroup(jPanelLayout.createSequentialGroup()
                                                .addComponent(jButton)
                                                .addGap(18, 18, 18))
                                        .addComponent(jScrollPane, GroupLayout.PREFERRED_SIZE, 1030, GroupLayout.PREFERRED_SIZE)) // jScrollPane 3 from 4
                                .addContainerGap(88, Short.MAX_VALUE))
        );

        jPanelLayout.setVerticalGroup(  // am ramas aici
                jPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(jPanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton))                    // searchLabel 6 from 6
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)                      // jScrollPane 4 from 4
                                .addContainerGap())
        );
    }// end method playLayout(JPanel jPanel, JScrollPane jScrollPane, JButton jButton)

    static public String compatibilityController(DefaultListModel<String> selectedList) throws Exception {
        String result = "";
        String output = "";
        String resultToReturn = "";

        ArrayList<String> as = new ArrayList<>();
        ArrayList<String> compatibleListDrugs = new ArrayList<>();
        ArrayList<String> compatibleListDrugsOnTap = new ArrayList<>();
        ArrayList<String> inCompatibleListDrugs = new ArrayList<>();
        ArrayList<String> conflictingDataDrugs = new ArrayList<>();
        ArrayList<String> noDataAvailableOfDrugs = new ArrayList<>();

        for (int i = 0; i < selectedList.size(); i++) {
            as.add(selectedList.getElementAt(i));
        }


        FunctionPostgreSQL fPSQL = new FunctionPostgreSQL(host, port, user, pass, database);

        for (String sE : as) {
            //System.out.println("select " + sE);
            for (String e : as) {
                // System.out.println("select " + e);
                //System.out.println("selected " + sE + " compare to " + e);
                result = fPSQL.readSpecificDate(nameTable, e, sE);
                switch (result) {
                    case "C":
                        compatibleListDrugs.add(e);
                        break;
                    case "Y":
                        compatibleListDrugsOnTap.add(e);
                        break;
                    case "I":
                        inCompatibleListDrugs.add(e);
                        break;
                    case "!":
                        conflictingDataDrugs.add(e);
                        break;
                    case "":
                        noDataAvailableOfDrugs.add(e);
                        break;
                    default:
                        System.out.println(String.format("Is same drugs %s and %s", sE, e));
                }
            }// end loop for for (String e : selectedList)
            output = String.format("%s Is:\n\tcompatible whit: %s,\n\tcompatible on tap whit: %s,\n\tincompatible whit: %s," +
                            "\n\tconflicting data drugs: %s,\n\tnot registration data whit: %s", sE, reformatString(compatibleListDrugs),
                    reformatString(compatibleListDrugsOnTap), reformatString(inCompatibleListDrugs), reformatString(conflictingDataDrugs),
                    reformatString(noDataAvailableOfDrugs));
            resultToReturn += output + "\n";

            compatibleListDrugs.clear();
            compatibleListDrugsOnTap.clear();
            inCompatibleListDrugs.clear();
            conflictingDataDrugs.clear();
            noDataAvailableOfDrugs.clear();
        }// end loop for (String sE : selectedList)


        return resultToReturn;
    }// end method String compatibilityController(DefaultListModel<String> selectedList) throws Exception


    public void show() {
        frame.setVisible(true);
    }//end method show()

}//end class DrugFilterDemo
