package com.altynkez.rgis.vassaeve.utils;

import com.altynkez.rgis.vassaeve.entity.Cases;
import com.altynkez.rgis.vassaeve.entity.Patient;
import com.altynkez.rgis.vassaeve.entity.Services;
import com.altynkez.rgis.vassaeve.helper.CasesHelper;
import com.altynkez.rgis.vassaeve.helper.PatientHelper;
import com.altynkez.rgis.vassaeve.helper.ServicesHelper;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.reflect.FieldUtils;
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

    private static int updatePs(PreparedStatement ps, EntityDescriptions entityDescriptions, Object entity, boolean excludePK) throws IllegalAccessException, SQLException {
        Map<String, EntityDescriptions.FieldDescription> descriptions = entityDescriptions.getFieldsDescriptions();
        String pkfieldName = entityDescriptions.getPrimaryKeyFieldName();
        EntityDescriptions.FieldDescription fieldDescription;
        int i = 1;
        for (String field : descriptions.keySet()) {
            if (excludePK && field.equals(pkfieldName)) {
                //? todo
            } else {
                fieldDescription = descriptions.get(field);
                Object value = FieldUtils.readField(entity, field, true);
                switch (fieldDescription.getType().getTypeName()) {
                    case "java.lang.String":
                        if (Objects.nonNull(value)) {
                            ps.setString(i, value.toString());//???
                        }
                        break;
                    default:
                        ps.setObject(i, value);
                }
                i++;
            }
        }
        return i;
    }

    public static void createOneEntity(EntityDescriptions entityDescriptions, Object entity) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(entityDescriptions.getINSERT_SQL());

            updatePs(ps, entityDescriptions, entity, false);

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
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

    public static void editOneEntity(EntityDescriptions entityDescriptions, Object entity) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(entityDescriptions.getUPDATE_SQL());

            int i = updatePs(ps, entityDescriptions, entity, true);
            ps.setString(i, FieldUtils.readField(entity, entityDescriptions.getPrimaryKeyFieldName(), true).toString());

            ps.executeUpdate();
        } finally {
            if (ps != null) {
                try {
                    ps.close();
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

    public static List<Cases> loadAllCases(Map<String, String> filter) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        ResultSet rs = null;
        List<Cases> result = new ArrayList<>(0);
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder("select * from CASES ");
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
                result.add(CasesHelper.createCasesEntity(rs));
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

    public static List<Services> loadAllServices(Map<String, String> filter) throws ClassNotFoundException, SQLException, IOException, IllegalAccessException {
        Connection conn = null;
        ResultSet rs = null;
        List<Services> result = new ArrayList<>(0);
        try {
            conn = getConnection();
            StringBuilder sql = new StringBuilder("select * from SERVICES ");
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
                result.add(ServicesHelper.createServicesEntity(rs));
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
