package com.altynkez.rgis.vassaeve.utils;

import com.altynkez.rgis.vassaeve.entity.Patient;
import com.altynkez.rgis.vassaeve.helper.PatientHelper;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author vassaeve
 */
public class DbUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbUtils.class);

    private static String className;
    private static String user;
    private static String password;
    private static String url;

    
    /**
     * @return соединение к БД
     * @throws ClassNotFoundException
     * @throws SQLException
     * @throws IOException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        InputStream is = DbUtils.class.getResourceAsStream("/liquibase.properties");
        Properties connectionProperties = new Properties();
        connectionProperties.load(is);

        className = connectionProperties.getProperty("driver");
        user = connectionProperties.getProperty("username");
        password = connectionProperties.getProperty("password");
        url = connectionProperties.getProperty("url");

        Class.forName(className);
        Properties connectionProps = new Properties();
        connectionProps.put("user", user);
        connectionProps.put("password", password);

        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }

    public static int countPatient() throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            rs = conn.createStatement().executeQuery("select count(*) from PATIENT");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    
    
    public static List<Patient> loadAllPatients(Map<String, String> filter) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        ResultSet rs = null;
        List<Patient> result = new ArrayList<>(0);
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder("select * from PATIENT ");
            if (filter != null) {
                StringBuilder whereClause = new StringBuilder();
                filter.forEach((t, u) -> {
                    if (!StringUtils.isEmpty(t) && !StringUtils.isEmpty(u)) {
                        whereClause.append(" and ").append(t).append(" like '%").append(u).append("%'");
                    }
                });
                if (whereClause.length() != 0) {
                    sql.append(" WHERE ");

                    sql.append(whereClause.substring(" and ".length()));
                }
            }
            rs = conn.createStatement().executeQuery(sql.toString());
            while (rs.next()) {
                result.add(PatientHelper.createPatientEntity(rs));
            }
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                }
            }
        }
        return result;
    }

}
