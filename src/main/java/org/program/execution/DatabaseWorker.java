package org.program.execution;

import org.apache.log4j.Logger;
import org.program.stones.PreciousStone;
import org.program.stones.SemiPreciousStone;
import org.program.stones.Stone;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseWorker {
    private static final Logger logger = Logger.getLogger(DatabaseWorker.class);
    private static final String driverName = "com.mysql.jdbc.Driver";
    private static final String databaseName = "jdbc:mysql://localhost::3306/PreciousStoneController";
    private static final String user = "admin";
    private static final String password = "password";

    public static void writeIntoDatabase(Stone stone, Boolean addIntoNecklace) {
        logger.info("Запис каменя у базу даних");

        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseName, user, password);
            Statement statement = connection.createStatement();
            String query = "INSERT INTO Stones VALUES('" + stone.getName() + "'," +
                    "                                '" + stone.getClass().getSimpleName() + "'," +
                    "                                '" + stone.getColor() + "'," +
                    "                                " + stone.getWeight() + "," +
                    "                                " + stone.getValue() + "," +
                    "                                " + stone.getTransparency() + "," +
                    "                                " + addIntoNecklace + ")";
            statement.executeUpdate(query);
        } catch (Exception e) {
            logger.info("Помилка запису каменя у базу даних:" + e.toString());
        }

        logger.info("Запис каменя у базу даних відбувся успішно");
    }

    public static List<Stone> readFromDatabase(Boolean isNecklace) {
        logger.info("Зчитування каменів з бази даних");

        List<Stone> storage = new ArrayList<>();

        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseName, user, password);
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM Stones";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String type = resultSet.getString("Type");
                String color = resultSet.getString("Color");
                int weight = resultSet.getInt("Weight");
                int value = resultSet.getInt("Value");
                int transparency = resultSet.getInt("Transparency");
                boolean isInNecklace = resultSet.getBoolean("IsInNecklace");

                if (isNecklace && isInNecklace) {
                    if (type.equals(PreciousStone.class.getSimpleName())) {
                        storage.add(new PreciousStone(name, color, weight, value, transparency));
                    } else if (type.equals(SemiPreciousStone.class.getSimpleName())) {
                        storage.add(new SemiPreciousStone(name, color, weight, value, transparency));
                    }
                } else if (!isNecklace && !isInNecklace) {
                    if (type.equals(PreciousStone.class.getSimpleName())) {
                        storage.add(new PreciousStone(name, color, weight, value, transparency));
                    } else if (type.equals(SemiPreciousStone.class.getSimpleName())) {
                        storage.add(new SemiPreciousStone(name, color, weight, value, transparency));
                    }
                }
            }
        } catch (Exception e) {
            logger.info("Помилка зчитування каменів з бази даних:" + e.toString());
        }

        logger.info("Зчитування каменів з бази даних відбулося успішно");

        return storage;
    }

    public static void deleteFile(Stone stone) {
        logger.info("Видалення каменя з бази даних");
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseName, user, password);
            Statement statement = connection.createStatement();
            String query = "DELETE * FROM Stones WHERE Name = '" + stone.getName() + "' AND" +
                    "                                   Type = '" + stone.getClass().getSimpleName() + "' AND" +
                    "                                   Color = '" + stone.getColor() + "' AND" +
                    "                                   Weight = " + stone.getValue() + " AND" +
                    "                                   Value = " + stone.getValue() + " AND" +
                    "                                   Transparency = " + stone.getTransparency() + ";";
            statement.executeUpdate(query);
        } catch (Exception e) {
            logger.info("Помилка видалення каменя з бази даних:" + e.toString());
        }

        logger.info("Видалення каменя з бази даних відбулося успішно");
    }

    public static void cleanNecklace() {
        logger.info("Видалення каменів з намиста і повернення їх у колекцію");
        try {
            Class.forName(driverName);
            Connection connection = DriverManager.getConnection(databaseName, user, password);
            Statement statement = connection.createStatement();
            String query = "UPDATE Stones SET isInNecklace = false;";
            statement.executeUpdate(query);
        } catch (Exception e) {
            logger.info("Помилка видалення каменів з намиста і повернення їх у колекцію:" + e.toString());
        }

        logger.info("Видалення каменів з намиста і повернення їх у колекцію відбулося успішно");
    }
}