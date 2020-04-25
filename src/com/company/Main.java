package com.company;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        int number = 1;

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        String url = "jdbc:mysql://195.19.44.146:3306/user1?serverTimezone=UTC";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url,"user1","user1");
            statement = connection.createStatement();

            System.out.println("Информация по рейсу №" + number + ":");
            resultSet = statement.executeQuery("SELECT Aeroport.nazvanie, Gorod.strana, vremya_vyleta, data_vyleta FROM Aeroport JOIN Gorod ON Aeroport.Gorod_gorod=Gorod.gorod JOIN Rejs ON Rejs.aeroport_vyleta=Aeroport.kod JOIN Polet ON Polet.Rejs_nomer=Rejs.nomer WHERE Rejs.nomer='"+ number + "'");
            while (resultSet.next()) {
                System.out.println("Рейс вылетает из " + resultSet.getString("nazvanie") + " страна " + resultSet.getString("strana") + " в " + resultSet.getString("vremya_vyleta") + " дата " + resultSet.getString("data_vyleta"));
            }

            resultSet = statement.executeQuery("SELECT Aeroport.nazvanie, Gorod.strana, vremya_prileta FROM Aeroport JOIN Gorod ON Aeroport.Gorod_gorod=Gorod.gorod JOIN Rejs ON  Rejs.aeroport_prileta=Aeroport.kod WHERE Rejs.nomer='"+ number + "'");
            while (resultSet.next()) {
                System.out.println("Рейс прилетает в " + resultSet.getString("nazvanie") + " страна " + resultSet.getString("strana") + " в " + resultSet.getString("vremya_prileta"));
            }

            System.out.println("Сотрудники: ");
            resultSet = statement.executeQuery("SELECT familja, imya, dolzhnost, stazh FROM Ekipazh JOIN Sotrudnik ON Sotrudnik.Ekipazh_kod=Ekipazh.kod JOIN Rejs ON Rejs.nomer=Ekipazh.Rejs_nomer WHERE Rejs.nomer='"+ number + "'");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("familja") + " " + resultSet.getString("imya")  + " должность: " + resultSet.getString("dolzhnost") + " стаж: " + resultSet.getString("stazh"));
            }

            resultSet = statement.executeQuery("SELECT Rejs.tip, dalnost, chastota, sostojanie, model, Samolet.tip, vmestimost, nazvanie FROM Rejs JOIN Polet ON Polet.Rejs_nomer=Rejs.nomer JOIN Samolet ON Rejs.Samolet_nomer=Samolet.nomer JOIN Kompanja ON Samolet.Kompanja_kod=Kompanja.kod WHERE Rejs.nomer='"+ number + "'");
            while (resultSet.next()) {
                System.out.println("Тип рейса " + resultSet.getString("Rejs.tip") + " дальность " + resultSet.getString("dalnost")  + " частота " + resultSet.getString("chastota") + " состояние рейса " + resultSet.getString("sostojanie"));
                System.out.println("Тип самолёта " + resultSet.getString("Samolet.tip") + " вместимость " + resultSet.getString("vmestimost")  + " название компании " + resultSet.getString("nazvanie"));
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
