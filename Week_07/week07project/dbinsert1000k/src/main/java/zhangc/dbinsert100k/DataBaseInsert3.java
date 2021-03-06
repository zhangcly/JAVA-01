package zhangc.dbinsert100k;

import java.sql.*;

public class DataBaseInsert3 {

    public static void main(String[] args) throws SQLException {
        long time = System.currentTimeMillis();
        Connection connection = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.56.101/shop?useSSL=false", "root", "100826");
            stmt = connection.createStatement();
            for (int i = 0; i < 1000000; i ++){
                stmt.addBatch("insert into tb_order(id) values(" + i + ")");
            }
            stmt.executeBatch();
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
        System.out.println("cost " + (System.currentTimeMillis() - time)  + " ms");
    }
}
