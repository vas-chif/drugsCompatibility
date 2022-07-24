package work;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class SelectDrugs {
    public static List<String> DRUGS = new ArrayList<>();
    public static List<String> selectedListDrugs = new ArrayList<>();
    public static int numberDeviceLumen;

    public static List<String> selectDrugs(List<String> originalList, List<String> newList) {

        String userInput = "";
        String selectElement = "";
        String output = "";

        output += "Select drugs of the list: " + originalList + "\nTo close the menu \"List drugs \"stop\"";

        do {
            if (userInput.equalsIgnoreCase("no")) {
                newList.clear();
            }// end if (userInput.equals("no"))

            do {
                selectElement = JOptionPane.showInputDialog(output);

                boolean containsItem = containsElementIgnoreCase(originalList, selectElement, newList);

                if (!containsItem && !selectElement.equalsIgnoreCase("stop"))
                    JOptionPane.showMessageDialog(null, "Drug not exist in this list");

            } while (!selectElement.equalsIgnoreCase("stop"));

            String out = "Are the selected drugs correct? yes/no" + newList;
            userInput = JOptionPane.showInputDialog(out);

        } while (!userInput.equalsIgnoreCase("yes"));

        return newList;
    }// end selectDrugs() method

    private static boolean containsElementIgnoreCase(List<String> aList, String str, List<String> newList) {

        boolean result = false;

        for (String element : aList) {
            result = str.equalsIgnoreCase(element);
            if (result) {
                newList.add(element);
                //System.out.println(newList);
                break;
            }// end if (result)

        }// end loop for (String element : aList)

        return result;
    }//end method containsElementIgnoreCase(List<String> aList, String str)

}// end class SelectDrugs extends FunctionPostgreSQL implements IListDrugs
