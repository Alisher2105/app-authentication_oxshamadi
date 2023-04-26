package uz.pdp.service;

import uz.pdp.model.Result;
import uz.pdp.model.User;

import java.sql.*;

public class DbService {
    String url = "jdbc:postgresql://localhost:5432/app-auth";
    String dbUser = "postgres";
    String dbPassword = "postgres";

    public Result registerUser(User user){
        try {
            Class.forName("org.postgresql.Driver");
            // db ga ulanish uchun connection ochdik
            Connection connection = DriverManager.getConnection(url,dbUser,dbPassword);
            // querylarni yuborish uchun statement ochdik
            Statement statement = connection.createStatement();

            //Phone Number orqali tekshirish
            String checkPhoneNumberQuery = "select count(*) from users where phone_number;'"+user.getPhoneNumber()+"'";
            ResultSet resultSet = statement.executeQuery(checkPhoneNumberQuery);
            int countUserByFields = 0;
            while (resultSet.next()){
                countUserByFields = resultSet.getInt(1);
            }
            if(countUserByFields>0){
                return new Result("Phone number alredy exist", false);
            }

            // Username orqali tekshirish
            String checkUsernameQuery = "select count(*) from users where username='"+user.getUsername()+"'";
            ResultSet resultSetUserName = statement.executeQuery(checkUsernameQuery);
            while (resultSetUserName.next()){
                countUserByFields = resultSetUserName.getInt(1);
            }
            if (countUserByFields>0){
                return new Result("Username alredy exist", false);
            }

            // Userni (DB) MO ga saqlash
            String query = "insert into users(username,first_name,last_name,phone_number,password)\n" +
                    "values('"+user.getUsername()+"','"+user.getFirstName()+"','"+user.getLastName()+"','"+user.getPhoneNumber()+"','"+user.getPassword()+"');";
            boolean execute = statement.execute(query);
            System.out.println(execute);
            return new Result("Succesfully registered",true);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
//        return new Result("Error in server",false);
    }
}
