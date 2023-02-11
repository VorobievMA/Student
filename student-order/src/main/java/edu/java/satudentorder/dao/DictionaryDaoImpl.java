package edu.java.satudentorder.dao;

import edu.java.satudentorder.Domein.CountryArea;
import edu.java.satudentorder.Domein.PassportOffice;
import edu.java.satudentorder.Domein.RegisterOffice;
import edu.java.satudentorder.Domein.Street;
import edu.java.satudentorder.config.Config;
import edu.java.satudentorder.exception.DaoException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DictionaryDaoImpl implements DictionaryDao{
    private static final String GET_STREET = "select street_code, street_name " +
            "from jc_street where upper (street_name) like upper (?)";

    private static final String GET_PASSPORT = "select * " +
            "from jc_passport_office where p_office_area_ID=?";

    private static final String GET_REGISTER = "select * " +
            "from jc_register_office where r_office_area_ID=?";

    private static final String GET_AREA = "select * " +
            "from jc_country_struct where area_id like ? and area_id<> ?";

    /*private Connection getConnection() throws SQLException {
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jc_student",
                "postgres", "123456");
        return con;
    }*/

    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }
    public List<Street> findStreets(String pattern) throws DaoException{
        List<Street> result = new LinkedList<>();

        try (Connection con = getConnection();
              PreparedStatement stmt = con.prepareStatement(GET_STREET)){
             stmt.setString(1, "%" + pattern + "%");
             ResultSet rs = stmt.executeQuery();
             while (rs.next()) {
                Street str = new Street(rs.getLong("street_code"), rs.getString("street_name"));
                result.add(str);
            }
        } catch (SQLException ex)
        { throw new DaoException(ex);}
       return result;
    }

    @Override
    public List<PassportOffice> findPassportOffices(String areaID) throws DaoException {
        List<PassportOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_REGISTER)){
            stmt.setString(1,  areaID );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PassportOffice str = new PassportOffice(rs.getLong("r_office_id"),
                        rs.getString("r_office_area_id"),
                        rs.getString("r_office_name"));
                result.add(str);
            }
        } catch (SQLException ex)
        { throw new DaoException(ex);}
        return result;
    }

    @Override
    public List<RegisterOffice> findRegisterOffice(String areaID) throws DaoException {
        List<RegisterOffice> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_PASSPORT)){
            stmt.setString(1,  areaID );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RegisterOffice str = new RegisterOffice(rs.getLong("p_office_id"),
                        rs.getString("p_office_area_id"),
                        rs.getString("p_office_name"));
                result.add(str);
            }
        } catch (SQLException ex)
        { throw new DaoException(ex);}
        return result;
    }

    @Override
    public List<CountryArea> findAreas(String areaID) throws DaoException {
        List<CountryArea> result = new LinkedList<>();

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(GET_AREA)){
            String param1 = buildParam(areaID);
            String param2 = areaID;
            stmt.setString(1,  param1 );
            stmt.setString(2,  param2 );
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CountryArea str = new CountryArea(rs.getString("area_id"),
                        rs.getString("area_name"));
                result.add(str);
            }
        } catch (SQLException ex)
        { throw new DaoException(ex);}
        return result;
    }

    private String buildParam(String areaID)throws SQLException{
        if (areaID == null || areaID.trim().isEmpty()){
            return "__0000000000";
        }
        if (areaID.endsWith("0000000000")){
           return areaID.substring(0,2) + "___0000000";
        } else if (areaID.endsWith("0000000")) {
            return areaID.substring(0,5) + "___0000";
        } else if (areaID.endsWith("0000")) {
            return areaID.substring(0,8) + "____";
        }
        throw new SQLException("invalid parametr" + areaID);
    }
}
