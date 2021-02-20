package zhangc.database;

import java.sql.*;

public class JdbcOperation {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "aaaaaa");
            stmt = connection.createStatement();
            stmt.execute("insert into student(id, `name`, age) values(1, 'zhangsan', 18)");

            stmt.executeUpdate("update student set age = 20 where id = 1");

            rs = stmt.executeQuery("select `name`, age from student");
            while(rs.next()){
                System.out.println(rs.getString("name")+" 年龄："+rs.getInt("age"));
            }

            stmt.execute("delete from student where id = 1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null){
                rs.close();
            }
            if (stmt != null){
                stmt.close();
            }
            if (connection != null){
                connection.close();
            }
        }

    }
}
