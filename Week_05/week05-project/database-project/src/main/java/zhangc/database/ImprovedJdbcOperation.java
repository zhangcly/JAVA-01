package zhangc.database;

import java.sql.*;

public class ImprovedJdbcOperation {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection connection = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        boolean hasError = false;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "aaaaaa");
            connection.setAutoCommit(false);

            pstmt1 = connection.prepareStatement("insert into student(id, `name`, age) values(?, ?, ?)");
            pstmt1.setInt(1, 1);
            pstmt1.setString(2, "zhangsan");
            pstmt1.setInt(3, 18);
            pstmt1.execute();


            pstmt2 = connection.prepareStatement("update student set age = ? where id = ?");
            pstmt2.setInt(1, 20);
            pstmt2.setInt(1, 1);
            pstmt2.execute();

        } catch (SQLException e) {
            connection.rollback();
            hasError = true;
        } finally {
            if (!hasError){
                connection.commit();
            }
            if (pstmt1 != null){
                pstmt1.close();
            }
            if (pstmt2 != null){
                pstmt2.close();
            }
            if (connection != null){
                connection.close();
            }
        }

    }
}
