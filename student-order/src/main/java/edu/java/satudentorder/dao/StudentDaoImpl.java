package edu.java.satudentorder.dao;

import edu.java.satudentorder.Domein.*;
import edu.java.satudentorder.Domein.other.Adult;
import edu.java.satudentorder.config.Config;
import edu.java.satudentorder.exception.DaoException;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDaoImpl implements StudentOrderDao{
    private static final String INSERT_ORDER =
            "INSERT INTO jc_student_order(\n" +
                    "student_order_status, student_order_date, h_sur_name, h_given_name, h_patronymic, h_date_of_birth, h_passport_seria, h_passport_number, h_passport_date, h_passport_office_id, h_post_index, h_street_code, h_building, h_extension, h_apartment,h_university_id,h_student_number, w_sur_name, w_given_name, w_patronymic, w_date_of_birth, w_passport_seria, w_passport_number, w_passport_date, w_passport_office_id, w_post_index, w_street_code, w_building, w_extension, w_apartment,w_university_id,w_student_number, certificate_id, register_office_id, marriage_date)" +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?,?,?);";

    private static final String INSERT_CHAILD = "INSERT INTO jc_student_child(\n" +
            "student_order_id, c_sur_name, c_given_name, c_patronymic, c_date_of_birth, c_certificate_number, c_certificate_date, c_register_office_id, c_post_index, c_street_code, c_building, c_extension, c_apartment)\n" +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private static final String SELECT_ORDERS = "  select ro.r_office_area_ID, ro.r_office_name, so.*, \n" +
            " po_h.p_office_area_ID as h_p_office_area_ID, po_h.p_office_name as h_p_office_name,\n" +
            " po_w.p_office_area_ID as w_p_office_area_ID, po_w.p_office_name as w_p_office_name\n" +
            " from jc_student_order so\n" +
            " inner join jc_register_office ro on ro.r_office_ID = so.register_office_ID\n" +
            " inner join jc_passport_office po_h on po_h.p_office_ID = so.h_passport_office_ID\n" +
            " inner join jc_passport_office po_w on po_w.p_office_ID = so.w_passport_office_ID\n" +
            " where student_order_status = ? order by student_order_date LIMIT ?";

    private static final String SELECT_CHILD =
            "select soc.*, ro.r_office_area_ID, ro.r_office_name\n" +
                    "from jc_student_child soc \n" +
                    "inner join jc_register_office ro on ro.r_office_ID = soc.c_register_office_ID\n" +
                    "where student_order_ID in  ";

    private static final String SELECT_ORDERS_FULL = "  select ro.r_office_area_ID, ro.r_office_name, so.*, \n" +
            " po_h.p_office_area_ID as h_p_office_area_ID, po_h.p_office_name as h_p_office_name,\n" +
            " po_w.p_office_area_ID as w_p_office_area_ID, po_w.p_office_name as w_p_office_name,\n" +
            " soc.*, ro_c.r_office_area_ID, ro_c.r_office_name\n" +
            " from jc_student_order so\n" +
            " inner join jc_register_office ro on ro.r_office_ID = so.register_office_ID\n" +
            " inner join jc_passport_office po_h on po_h.p_office_ID = so.h_passport_office_ID\n" +
            " inner join jc_passport_office po_w on po_w.p_office_ID = so.w_passport_office_ID\n" +
            " inner join jc_student_child soc on soc.student_order_ID = so.student_order_ID\n" +
            " inner join jc_register_office ro_c on ro_c.r_office_ID = soc.c_register_office_ID\n" +
            " where student_order_status = ? order by so.student_order_ID LIMIT ?";


    private Connection getConnection() throws SQLException {
        return ConnectionBuilder.getConnection();
    }
    @Override
    public long saveStudentOrder(StudentOrder so) throws DaoException {
        Long result = -1L;

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(INSERT_ORDER, new String[] {"student_order_id"}))
        {
            con.setAutoCommit(false);
            try {
                stmt.setInt(1, StudentOrderStatus.START.ordinal());
                stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));

                SetPAramForAdult (stmt, 3, so.getHusband());
                SetPAramForAdult (stmt, 18, so.getWife());

            /*Adult wife = so.getWife();
            stmt.setString(16, wife.getSurNAme());
            stmt.setString(17, wife.getGivenNAme());
            stmt.setString(18, wife.getPatronymic());
            stmt.setDate(19, java.sql.Date.valueOf(wife.getDateOfBirth()));
            stmt.setString(20, wife.getPassportSeria());
            stmt.setString(21, wife.getPassportNumber());
            stmt.setDate(22, java.sql.Date.valueOf(wife.getIssueDate()));
            stmt.setLong(23, wife.getIssueDepartment().getOfficeID());
            Address w_address = wife.getAddress();
            stmt.setString(24, w_address.getPostcode());
            stmt.setLong(25, w_address.getStreet().getStreet_code());
            stmt.setString(26, w_address.getBuilding());
            stmt.setString(27,w_address.getExtension());
            stmt.setString(28,w_address.getApartment());*/

                stmt.setString(33, so.getMarriageCertificateID());
                stmt.setLong(34, so.getMarriageOffice().getOfficeId());
                stmt.setDate(35, Date.valueOf(so.getMarriageDate()));

                stmt.executeUpdate();

                ResultSet gkRs = stmt.getGeneratedKeys();
                if(gkRs.next()){
                    result = gkRs.getLong(1);
                }
                gkRs.close();

                saveChildren(con, so, result);
                con.commit();
            } catch (SQLException ex) {
                con.rollback();
                throw ex;
            }

        } catch (SQLException ex){
                throw new DaoException(ex);
            }
        return result;
    }
    private void setParamsForChild(PreparedStatement stmt, Child child) throws SQLException {
        setParamsForPerson(stmt, 2, child);
        stmt.setString(6, child.getCertificateNumber());
        stmt.setDate(7, Date.valueOf(child.getIssueDate()));
        stmt.setLong(8, child.getIssueDepartment().getOfficeId());
        setParamsForAddress(stmt,9, child);
    }
    private void setParamsForPerson(PreparedStatement stmt, int start, Person person) throws SQLException {
        stmt.setString(start, person.getSurNAme());
        stmt.setString(start +1, person.getGivenNAme());
        stmt.setString(start +2, person.getPatronymic());
        stmt.setDate(start +3, Date.valueOf(person.getDateOfBirth()));
    }
    private void setParamsForAddress(PreparedStatement stmt, int start, Person person) throws SQLException {
        Address adult_address = person.getAddress();
        stmt.setString(start , adult_address.getPostcode());
        stmt.setLong(start +1, adult_address.getStreet().getStreet_code());
        stmt.setString(start + 2, adult_address.getBuilding());
        stmt.setString(start + 3, adult_address.getExtension());
        stmt.setString(start + 4, adult_address.getApartment());
    }
    private void saveChildren(Connection con, StudentOrder so, Long soId) throws SQLException{
        try (PreparedStatement stmt = con.prepareStatement(INSERT_CHAILD)){
            for (Child child: so.getChildren()){
                stmt.setLong(1,soId);
                setParamsForChild(stmt,child);
                stmt.addBatch();
            }
            stmt.executeBatch();
        }
    }
    private void SetPAramForAdult(PreparedStatement stmt, int start, Adult adult) throws SQLException {
        setParamsForPerson(stmt, start, adult);
        stmt.setString(start + 4, adult.getPassportSeria());
        stmt.setString(start + 5, adult.getPassportNumber());
        stmt.setDate(start + 6, Date.valueOf(adult.getIssueDate()));
        stmt.setLong(start + 7, adult.getIssueDepartment().getOfficeID());
        setParamsForAddress(stmt, start + 8, adult);
        stmt.setLong(start+13, adult.getUniversity().getUniversityId());
        stmt.setString(start+14, adult.getStudentid());
    }
    @Override
    public List<StudentOrder> getStudentOrders() throws DaoException {
//        return getStudentOrdersTwoSelect();
        return getStudentOrdersOneSelect();
    }

    private List<StudentOrder> getStudentOrdersOneSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS_FULL)){

            Map<Long,StudentOrder> maps = new HashMap<>();
            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            int limit = Integer.parseInt(Config.getProperty(Config.DB_LIMIT));
            stmt.setInt(2,limit);

            ResultSet rs  = stmt.executeQuery();
            int counter = 0;
            while (rs.next()){
                Long soId = rs.getLong("student_order_id");
                if (!maps.containsKey(soId)) {
                    StudentOrder so = getFullStudentOrder(rs);

                    result.add(so);
                    maps.put(soId, so);
                }
                StudentOrder so = maps.get(soId);
                so.addChild(fillChild(rs));
                counter++;
            }
//            findChildren(con, result);
            if (counter >= limit){
                result.remove(result.size() - 1);
            }
            rs.close();

        }catch (SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    private List<StudentOrder> getStudentOrdersTwoSelect() throws DaoException {
        List<StudentOrder> result = new LinkedList<>();
        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(SELECT_ORDERS)){

            stmt.setInt(1, StudentOrderStatus.START.ordinal());
            stmt.setInt(2, Integer.parseInt(Config.getProperty(Config.DB_LIMIT)));
           ResultSet rs  = stmt.executeQuery();

           while (rs.next()){
               StudentOrder so = getFullStudentOrder(rs);

               result.add(so);

           }
           findChildren(con, result);

           rs.close();

        }catch (SQLException ex){
            throw new DaoException(ex);
        }
        return result;
    }

    private StudentOrder getFullStudentOrder(ResultSet rs) throws SQLException {
        StudentOrder so = new StudentOrder();

        fillStudentOrder(rs, so);
        fillMarriage(rs, so);

        so.setHusband(fillAdult(rs, "h_"));
        so.setWife(fillAdult(rs, "w_"));
        return so;
    }

    private void fillStudentOrder(ResultSet rs, StudentOrder so) throws SQLException {
        so.setStudentOrderID(rs.getLong("student_order_id"));
        so.setStudentOrderDate(rs.getTimestamp("student_order_date").toLocalDateTime());
        so.setStudentOrderStatus(StudentOrderStatus.fromValue(rs.getInt("student_order_status")));

    }
    private void fillMarriage(ResultSet rs, StudentOrder so) throws SQLException {
        so.setMarriageCertificateID(rs.getString("certificate_ID"));
        so.setMarriageDate(rs.getDate("marriage_date").toLocalDate());

        Long roId = rs.getLong("register_office_ID");
        String areaId = rs.getString("r_office_area_ID");
        String name = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, areaId,name);
        so.setMarriageOffice(ro);
    }
    private Adult fillAdult(ResultSet rs, String pref) throws SQLException {
        Adult adult = new Adult();
        adult.setSurNAme(rs.getString(pref + "sur_name"));
        adult.setGivenNAme(rs.getString(pref + "given_name"));
        adult.setPatronymic(rs.getString(pref + "patronymic"));
        adult.setDateOfBirth(rs.getDate(pref + "date_of_birth").toLocalDate());
        adult.setPassportSeria(rs.getString(pref + "passport_seria"));
        adult.setPassportNumber(rs.getString(pref + "passport_number"));
        adult.setIssueDate(rs.getDate(pref + "passport_date").toLocalDate());

        Long poID = rs.getLong(pref + "passport_office_ID");
        String poArea = rs.getString(pref + "p_office_area_ID");
        String poName = rs.getString(pref + "p_office_name");
        PassportOffice po = new PassportOffice(poID, poArea, poName);
        adult.setIssueDepartment(po);
        Address adr = new Address();
        Street st = new Street(rs.getLong(pref + "street_code"),"");
        adr.setStreet(st);
        adr.setPostcode(rs.getString(pref+"post_index"));
        adr.setBuilding(rs.getString(pref+"building"));
        adr.setExtension(rs.getString(pref+"extension"));
        adr.setApartment(rs.getString(pref+"apartment"));
        adult.setAddress(adr);

        University uni = new University(rs.getLong(pref+ "university_id"),"");
        adult.setUniversity(uni);
        adult.setStudentid(rs.getString(pref + "student_number"));
        return adult;

    }
    private void findChildren(Connection con, List<StudentOrder> result) throws SQLException {
        String cl = "(" + result.stream().map(so -> String.valueOf(so.getStudentOrderID()))
                .collect(Collectors.joining(",")) + ")";

        Map<Long, StudentOrder> maps = result.stream().collect(Collectors.toMap(so -> so.getStudentOrderID(), so->so));

        try (PreparedStatement stmt = con.prepareStatement(SELECT_CHILD + cl)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Child ch = fillChild(rs);
                StudentOrder so = maps.get(rs.getLong("student_order_ID"));
                so.addChild(ch);
            }

        }
    }
    private Child fillChild(ResultSet rs) throws SQLException{
        String surNAme = rs.getString("c_sur_name");
        String givenNAme = rs.getString("c_given_name");
        String patronymic = rs.getString("c_patronymic");
        LocalDate dateOfBirth = rs.getDate("c_date_of_birth").toLocalDate();
        Child child = new Child(surNAme, givenNAme, patronymic, dateOfBirth);

        child.setCertificateNumber(rs.getString("c_certificate_number"));
        child.setIssueDate(rs.getDate("c_certificate_date").toLocalDate());

        Long roId = rs.getLong("c_register_office_ID");
        String roArea = rs.getString("r_office_area_ID");
        String roName = rs.getString("r_office_name");
        RegisterOffice ro = new RegisterOffice(roId, roArea,roName);
        child.setIssueDepartment(ro);

        Address adr = new Address();
        Street st = new Street(rs.getLong( "c_street_code"),"");
        adr.setStreet(st);
        adr.setPostcode(rs.getString("c_post_index"));
        adr.setBuilding(rs.getString("c_building"));
        adr.setExtension(rs.getString("c_extension"));
        adr.setApartment(rs.getString("c_apartment"));
        child.setAddress(adr);
        return child;
    }




}
