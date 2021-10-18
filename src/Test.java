import database.CustomDB;
import helper.DBInfo;
import helper.Utility;
import services.FileService;

import java.util.*;

public class Test {
    public static void main(String[] args) {
        Utility.printWelcome();
        Scanner scan = new Scanner(System.in);

        CustomDB.selectTable();

        while (true) {
            System.out.print("\nEnter an option: ");
            String option = scan.nextLine();

            if (option.equalsIgnoreCase(DBInfo.EXIT_OPTION)) {
                System.exit(0);
            } else if (option.equalsIgnoreCase(DBInfo.VIEW_OPTION)) {
                CustomDB.viewTable();
            } else if (option.equalsIgnoreCase(DBInfo.SWITCH_TABLE_OPTION)) {
                CustomDB.selectTable();
            } else {
                String[] optionArr = option.split(" ");
                if (optionArr[0].equalsIgnoreCase(DBInfo.ADD_OPTION)) {
                    if (optionArr.length == 2 && optionArr[1].split(",").length == CustomDB.columns.length &&
                            Utility.checkForRightInputData(optionArr[1].split(","), CustomDB.dataTypes)) {
                        FileService.writeInFile(CustomDB.tableName, optionArr[1] + "\n");
                        CustomDB.rows.add(optionArr[1]);
                        CustomDB.tree.insert(optionArr[1].split(",")[CustomDB.idxOfIndex], optionArr[1]);
                    }
                } else if (optionArr[0].equalsIgnoreCase(DBInfo.SEARCH_OPTION)) {
                    if (optionArr.length == 2 &&
                            (optionArr[1].equals("*") || CustomDB.setOfColumns.contains(optionArr[1]))) {
                        CustomDB.search(optionArr[1], CustomDB.tableName, CustomDB.columns, CustomDB.rows);
                    } else if (optionArr.length == 6 &&
                            (optionArr[1].equals("*") || CustomDB.setOfColumns.contains(optionArr[1])) &&
                            optionArr[2].equalsIgnoreCase("where") && CustomDB.setOfColumns.contains(optionArr[3]) &&
                            optionArr[4].equalsIgnoreCase("is")) {
                        CustomDB.search(optionArr[1], CustomDB.columns, CustomDB.rows, optionArr[3], optionArr[5]);
                    }
                }
            }
        }
    }
}
