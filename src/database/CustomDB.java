package database;

import helper.Utility;
import services.FileService;
import tree.RedBlackTree;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CustomDB {
    public static String[] columns;
    public static String[] dataTypes;
    public static Set<String> setOfColumns;
    public static List<String> rows;
    public static String tableName;
    private static String index;
    public static int idxOfIndex;
    public static RedBlackTree tree;

    private static void createTable(String tableName) {
        Scanner scan = new Scanner(System.in);
        FileService.createFile(tableName);
        System.out.println("Created new Table - " + tableName);

        System.out.println("Give the column names separated by commas.\nFor example: Name,Age,Sex");
        String columnsString = scan.nextLine();
        String[] columns = columnsString.split(",");

        System.out.println("Give their types separated by commas.\nFor example: String,Integer,Character\n" +
                "NOTE: This custom database supports only String, Integer and Character data types :D");
        boolean areValidDataTypes = false;
        String dataTypesString = null;
        while (!areValidDataTypes) {
            dataTypesString = scan.nextLine();
            String[] dataTypes = dataTypesString.split(",");
            if (dataTypes.length == columns.length) {
                areValidDataTypes = true;
            }
            for (int i = 0; i < dataTypes.length && areValidDataTypes; i++) {
                if (!dataTypes[i].equalsIgnoreCase("string") &&
                        !dataTypes[i].equalsIgnoreCase("integer") &&
                        !dataTypes[i].equalsIgnoreCase("character")) {
                    areValidDataTypes = false;
                }
            }
            if (!areValidDataTypes) {
                System.out.println("Wrong input format! Try again.");
            }
        }

        FileService.writeInFile(tableName, columnsString + "\n" + dataTypesString + "\n");
    }

    public static void viewTable() {
        tree.inOrderTraverse();
    }

    public static void search(String searchForColumn, String tableName, String[] columns, List<String> rows) {
        System.out.println("SEARCHING...");
        if (searchForColumn.equals("*")) {
            viewTable();
        } else {
            int i = 0;
            for (; i < columns.length; i++) {
                if (columns[i].equals(searchForColumn)) {
                    break;
                }
            }
            for (int j = 2; j < rows.size(); j++) {
                System.out.println(rows.get(j).split(",")[i]);
            }
        }
    }

    public static void search(String searchForColumn, String[] columns, List<String> rows,
                              String column, String columnCondition) {
        System.out.println("SEARCHING...");
        if (column.equals(index)) {
            System.out.println("TREE");
            if (searchForColumn.equals("*")) {
                tree.search(columnCondition, true, -1);
            } else {
                for (int i = 0; i < columns.length; i++) {
                    if (searchForColumn.equals(columns[i])) {
                        tree.search(columnCondition, false, i);
                        break;
                    }
                }
            }
        } else {
            int idxOfColumn = 0;
            for (; idxOfColumn < columns.length; idxOfColumn++) {
                if (column.equals(columns[idxOfColumn])) {
                    break;
                }
            }

            if (!searchForColumn.equals("*")) {
                int i = 0;
                for (; i < columns.length; i++) {
                    if (searchForColumn.equals(columns[i])) {
                        break;
                    }
                }

                for (int j = 2; j < rows.size(); j++) {
                    String[] row = rows.get(j).split(",");
                    if (row[idxOfColumn].equals(columnCondition)) {
                        System.out.println(row[i]);
                    }
                }
            } else {
                for (int j = 2; j < rows.size(); j++) {
                    String[] row = rows.get(j).split(",");
                    if (row[idxOfColumn].equals(columnCondition)) {
                        System.out.println(rows.get(j));
                    }
                }
            }
        }
    }

    public static String selectTable() {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter the table name: ");
        tableName = scan.nextLine();
        while (tableName.contains(".")) {
            System.out.print("Do not use any file extension, just write the table name: ");
            tableName = scan.nextLine();
        }
        tableName += Utility.EXTENSION;

        File table = new File(tableName);
        if (!table.exists()) {
            CustomDB.createTable(tableName);
        }

        try {
            rows = FileService.readFromFile(tableName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        columns = rows.get(0).split(",");
        dataTypes = rows.get(1).split(",");
        setOfColumns = new HashSet<>(Arrays.asList(columns));
        System.out.println("Table's columns:\n" + Arrays.toString(columns));
        System.out.println("Their types:\n" + Arrays.toString(dataTypes));
        chooseIndex();
        return tableName;
    }

    private static void chooseIndex() {
        tree = new RedBlackTree();
        Scanner scan = new Scanner(System.in);
        do {
            System.out.print("Choose the index (enter the column name): ");
            index = scan.nextLine();
        } while (!setOfColumns.contains(index));
        idxOfIndex = 0;
        for (; idxOfIndex < columns.length; idxOfIndex++) {
            if (index.equals(rows.get(0).split(",")[idxOfIndex])) {
                break;
            }
        }
        for (int i = 2; i < rows.size(); i++) {
            tree.insert(rows.get(i).split(",")[idxOfIndex], rows.get(i));
        }
    }
}
