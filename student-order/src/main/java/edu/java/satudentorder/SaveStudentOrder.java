package edu.java.satudentorder;

import edu.java.satudentorder.Domein.*;
import edu.java.satudentorder.Domein.other.Adult;
import edu.java.satudentorder.dao.StudentDaoImpl;
import edu.java.satudentorder.dao.StudentOrderDao;

import java.time.LocalDate;
import java.util.List;

public class SaveStudentOrder {


    public static void main(String[] args) throws Exception{
/*        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/jc_student",
                "postgres", "123456");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from jc_street");
        while (rs.next()){
            System.out.println(rs.getLong(1) + ":" + rs.getString(2));
        }*/
/*       List<Street> d = new DictionaryDaoImpl().findStreets("про");
       for(Street s : d){
           System.out.println(s.getStreetName());
        }
        List<PassportOffice> po = new DictionaryDaoImpl().findPassportOffices("010020000000");
       for(PassportOffice p: po){
           System.out.println(p.getOfficeName());
       }
        List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffice("010020000000");
        for(RegisterOffice r: ro){
            System.out.println(r.getOfficeName());
        }*/

        /*List<CountryArea> ca1 = new DictionaryDaoImpl().findAreas("");
        for(CountryArea c: ca1){
            System.out.println(c.getAreaId() + ":" + c.getAreaName());
        }
        System.out.println("---->");
        List<CountryArea> ca2 = new DictionaryDaoImpl().findAreas("020000000000");
        for(CountryArea c: ca2){
            System.out.println(c.getAreaId() + ":" + c.getAreaName());
        }
        System.out.println("---->");
        List<CountryArea> ca3 = new DictionaryDaoImpl().findAreas("020010000000");
        for(CountryArea c: ca3){
            System.out.println(c.getAreaId() + ":" + c.getAreaName());
        }
        System.out.println("---->");
        List<CountryArea> ca4 = new DictionaryDaoImpl().findAreas("020010010000");
        for(CountryArea c: ca4){
            System.out.println(c.getAreaId() + ":" + c.getAreaName());
        }*/

//       StudentOrder s = buildStudentOrder(10);
        StudentOrderDao dao = new StudentDaoImpl();
//        long id = dao.saveStudentOrder(s);
//        System.out.println(id);

        List<StudentOrder> soList = dao.getStudentOrders();
        for(StudentOrder so: soList){
            System.out.println(so.getStudentOrderID());
        }

//        buildStudentOrder(10);
//        Class.forName("org.postgresql.Driver");
//        Connection con = DriverManager.getConnection(
//                "jdbc:postgresql://localhost:5432/jc_student",
//                "postgres", "123456");
//        Statement stmt = con.createStatement();
//        ResultSet rs = stmt.executeQuery("select * from jc_street");
//        while (rs.next()) {
//            System.out.println(rs.getLong(1)+ ":" + rs.getString(2) );
//        }
 //       StudentOrder so = new StudentOrder();
//        buildStudentOrder();
 //       long ans =saveStudentOrder(so);
 //       System.out.println(ans);

    }

    static long saveStudentOrder(StudentOrder studentOrder){
        long answer=199;
        System.out.println("saveStudentOrder 1" );

        return answer;
    }
    static  StudentOrder buildStudentOrder(long ID){
        StudentOrder so = new StudentOrder();
        so.setStudentOrderID(ID);
        so.setMarriageCertificateID(""+(123456000+ID));
        so.setMarriageDate(LocalDate.of(2016,7,4));
        RegisterOffice ro = new RegisterOffice(1L, "","");
        so.setMarriageOffice(ro);

        Street street = new Street(1L, "First street");

        Address address = new Address("195000", street, "12", "142", "12");
        //муж
        Adult husband = new Adult("vasilev","andrey","petrovich",LocalDate.of(1997,8,24));
        husband.setPassportSeria(""+(1000+ID));
        husband.setPassportNumber(""+(10000+ID));
        husband.setIssueDate(LocalDate.of(2017,9,15));
        PassportOffice po1 = new PassportOffice(1L,"","");
        husband.setIssueDepartment(po1);
        husband.setStudentid(""+(10000+ID));
        husband.setAddress(address);
        husband.setUniversity(new University(2L,","));
        husband.setStudentid("HH12345");
        //Жена
        Adult wife = new Adult("petrova", "vera", "aleksandrovna",LocalDate.of(1998,3,12));
        wife.setPassportSeria(""+(2000+ID));
        wife.setPassportNumber(""+(20000+ID));
        wife.setIssueDate(LocalDate.of(2018,7,19));
        PassportOffice po2 = new PassportOffice(2L,"","");
        wife.setIssueDepartment(po2);
        wife.setStudentid(""+(20000+ID));
        wife.setAddress(address);
        wife.setUniversity(new University(1L,","));
        wife.setStudentid("WW54321");
        //ребенок
        Child child1 = new Child("Petrova", "Irina","Andreevna",LocalDate.of(2018,6,29));
        child1.setCertificateNumber(""+(300000+ID));
        child1.setIssueDate(LocalDate.of(2018,6,11));
        RegisterOffice ro2 = new RegisterOffice(2L,"","");
        child1.setIssueDepartment(ro2);
        child1.setAddress(address);

        Child child2 = new Child("Petrov", "evgeniy","Andreevnch",LocalDate.of(2019,6,29));
        child2.setCertificateNumber(""+(300000+ID));
        child2.setIssueDate(LocalDate.of(2019,7,19));
        RegisterOffice ro3 = new RegisterOffice(3L,"","");
        child2.setIssueDepartment(ro3);
        child2.setAddress(address);

        so.setHusband(husband);
        so.setWife(wife);
        so.addChild(child1);
        so.addChild(child2);

        return so;
    }
    /*static void printStudentOrder(StudentOrder stOr){
        System.out.println(stOr.getStudentOrderID());
    }*/
}
