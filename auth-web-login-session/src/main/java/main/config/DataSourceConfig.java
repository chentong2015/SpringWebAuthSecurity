package main.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Configuration
public class DataSourceConfig {

    // 连接本地嵌入到项目的SQLite数据库
    @Bean
    public DataSource dataSource() throws IOException {
        String dbFilePath = "auth-web-login-session/database/session.db";
        Path dbPath = FileSystems.getDefault().getPath(dbFilePath);

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:sqlite:" + dbPath.toAbsolutePath());

        testDatabaseConnection(dataSource);
        return dataSource;
    }

    // 测试连接到本地的测试数据库
    private void testDatabaseConnection(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();
            System.out.println(connection.getAutoCommit());
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM t_role");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("name"));
            }
            statement.close();
            connection.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
