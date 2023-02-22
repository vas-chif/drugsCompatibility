package work;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static work.IFunctionPostgreSqlPV.*;

public class ControlDrugs {
    static List<String> compatibleListDrugs = new ArrayList<>();
    static List<String> compatibleListDrugsOnTap = new ArrayList<>();
    static List<String> inCompatibleListDrugs = new ArrayList<>();
    static List<String> conflictingDataDrugs = new ArrayList<>();
    static List<String> noDataAvailableOfDrugs = new ArrayList<>();


    private String compatibilityController(List<String> selectedList) throws Exception {
        String result = "";
        String output = "";
        String resultToReturn = "";

        FunctionPostgreSQL fPSQL = new FunctionPostgreSQL(host, port, user, pass, database);

        for (String sE : selectedList) {
            //System.out.println("select " + sE);
            for (String e : selectedList) {
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
    }// end controllerCompatibility(List<String> selectedList)

    static public String compatibilityController(DefaultListModel<String> selectedList) throws Exception {
        String result = "";
        String resultToReturn = "";
        String output = "";

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

            output = String.format("%s is: ", sE);
            if (!compatibleListDrugs.isEmpty()) {
                output += String.format("\n\tcompatible whit: %s   ", reformatString(compatibleListDrugs));
            }
            if (!compatibleListDrugsOnTap.isEmpty()) {
                output += String.format("\n\tcompatible on tap whit: %s    ", reformatString(compatibleListDrugsOnTap));
            }
            if (!inCompatibleListDrugs.isEmpty()) {
                output += String.format("\n\tincompatible whit: %s ", reformatString(inCompatibleListDrugs));
            }
            if (!conflictingDataDrugs.isEmpty()) {
                output += String.format("\n\tconflicting data drugs: %s    ", reformatString(conflictingDataDrugs));
            }
            if (!noDataAvailableOfDrugs.isEmpty()) {
                output += String.format("\n\tnot registration data whit: %s    ", reformatString(noDataAvailableOfDrugs));
            }

            resultToReturn += output + "\n";

            compatibleListDrugs.clear();
            compatibleListDrugsOnTap.clear();
            inCompatibleListDrugs.clear();
            conflictingDataDrugs.clear();
            noDataAvailableOfDrugs.clear();
        }// end loop for (String sE : selectedList)

        System.out.println();

        return resultToReturn;
    }// end method String compatibilityController(DefaultListModel<String> selectedList) throws Exception

    public static String reformatString(List<String> l) {
        String s = "";
        for (String e : l) {
            s += e + "; ";
        }
        return s;
    }

}// end class ControlDrugs extends  SelectDrugs
