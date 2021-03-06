package zhangc.dbinsert100k;

import java.sql.*;

public class DataBaseInsert4 {

    public static void main(String[] args) throws SQLException {
        long time = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.56.101/shop?useSSL=false", "root", "100826");
            pstmt = connection.prepareStatement("insert into tb_order(id) values(?)");
            for (int i = 0; i < 1000000; i ++){
                pstmt.setInt(1, i);
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null){
                pstmt.close();
            }
            if (connection != null){
                connection.close();
            }
        }
        System.out.println("cost " + (System.currentTimeMillis() - time)  + " ms");
    }
}
