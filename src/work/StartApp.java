package work;

import javax.swing.*;

public abstract class StartApp implements IFunctionPostgreSqlPV {

    public static void main(String[] args) throws Exception {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                DrugFilter drugFilter = new DrugFilter();
                drugFilter.show();
            }
        });

    }// end main method

}//end class StartApp
