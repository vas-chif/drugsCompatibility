package work;

import java.util.ArrayList;
import java.util.List;

import static work.IFunctionPostgreSqlPV.*;

public class ControlDrugs extends SelectDrugs {
    static List<String> compatibleListDrugs = new ArrayList<>();
    static List<String> CompatibleListDrugsOnTap = new ArrayList<>();
    static List<String> inCompatibleListDrugs = new ArrayList<>();
    static List<String> conflictingDataDrugs = new ArrayList<>();
    static List<String> noDataAvailableOfDrugs = new ArrayList<>();


    public static String compatibilityController(List<String> selectedList) throws Exception {
        String result = "";
        String output = "";
        String returner = "";

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
                        CompatibleListDrugsOnTap.add(e);
                        break;
                    case "I":
                        inCompatibleListDrugs.add(e);
                        break;
                    case "!":
                        conflictingDataDrugs.add(e);
                        break;
                    case " ":
                        noDataAvailableOfDrugs.add(e);
                        break;
                    default:
                        System.out.println(String.format("Is same drugs %s and %s", sE, e));
                }
            }// end loop for for (String e : selectedList)
            output = String.format("%s Is:\n\tcompatible whit %s,\n\tcompatible on tap whit %s,\n\tincompatible whit %s," +
                            "\n\tconflicting data drugs %s,\n\tnot registration data whit %s", sE, compatibleListDrugs,
                    CompatibleListDrugsOnTap, inCompatibleListDrugs, conflictingDataDrugs, noDataAvailableOfDrugs);
            returner += output + "\n";

            compatibleListDrugs.clear();
            CompatibleListDrugsOnTap.clear();
            inCompatibleListDrugs.clear();
            conflictingDataDrugs.clear();
            noDataAvailableOfDrugs.clear();
        }// end loop for (String sE : selectedList)


        return returner;
    }// end controllerCompatibility(List<String> selectedList)


}// end class ControlDrugs extends  SelectDrugs
