package work;

import javax.swing.*;

import static work.ControlDrugs.*;


public abstract class StartApp extends SelectDrugs implements IFunctionPostgreSqlPV {

    public static void main(String[] args) throws Exception {

        JTextArea outputJArea = new JTextArea(15,50);

        String output = "";

        FunctionPostgreSQL fpSQL = new FunctionPostgreSQL(host, port, user, pass, database);
        DRUGS = fpSQL.readColumnDate("drugscompatibility", "name_of_drugs");
        System.out.println("Drugs: " + DRUGS);
        numberDeviceLumen = 3;
        DRUGS.add(0, "name_of_drugs");

       // output += fpSQL.readTable("Drugs", DRUGS);
        output += "\n\nNumber device lumen" + numberDeviceLumen;
        output += "\nSelected List Drugs " + selectDrugs(DRUGS, selectedListDrugs);
        output+= "\n" + compatibilityController(selectedListDrugs);
        outputJArea.setText(output);
        outputJArea.setEditable(false);
        JOptionPane.showMessageDialog(null,outputJArea,"Result",JOptionPane.INFORMATION_MESSAGE);

        System.exit(0);

    }// end main method

}//end class StartApp
