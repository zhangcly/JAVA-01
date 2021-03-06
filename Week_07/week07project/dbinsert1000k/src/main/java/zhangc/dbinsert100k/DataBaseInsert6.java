package zhangc.dbinsert100k;

import java.sql.*;

public class DataBaseInsert6 {

    public static void main(String[] args) throws SQLException {
        long time = System.currentTimeMillis();
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://192.168.56.101/shop?useSSL=false", "root", "100826");
            for (int i = 0; i < 200; i ++){
                StringBuilder sb = new StringBuilder("insert into tb_order(id) values ");
                for (int j = 0; j < 5000; j ++){
                    sb.append("(?),");
                }
                String sql = sb.toString().substring(0, sb.length()-1);
                pstmt = connection.prepareStatement(sql);
                for (int j = 0; j < 5000; j ++){
                    pstmt.setInt(j+1, i * 5000 + j);
                }
                pstmt.addBatch();
                pstmt.executeBatch();
                pstmt.close();
            }
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
