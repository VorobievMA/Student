package edu.java.satudentorder.validators;

import edu.java.satudentorder.Domein.Child;
import edu.java.satudentorder.Domein.CityRegisterCheckerResponse;
import edu.java.satudentorder.Domein.Person;
import edu.java.satudentorder.Domein.other.Adult;
import edu.java.satudentorder.Domein.register.CityRegisterResponse;
import edu.java.satudentorder.exception.CityRegisterException;
import edu.java.satudentorder.exception.TransportException;

public class FakeCityRegisterChecker implements CityRegisterChecker {
    private static final String GOOD_1 = "1000";
    private static final String GOOD_2 = "2000";
    private static final String BAD_1 = "1001";
    private static final String BAD_2 = "2001";
    private static final String ERROR_1 = "1002";
    private static final String ERROR_2 = "2002";
    private static final String ERRORT_1 = "1003";
    private static final String ERRORT_2 = "2003";
    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException {
        CityRegisterCheckerResponse res = new CityRegisterCheckerResponse();
        if (person instanceof Adult){
            Adult t = (Adult) person;
            String ps = t.getPassportSeria();
            if (ps.equals(GOOD_1) ||ps.equals(GOOD_2)){
                res.setExisting(true);
                res.setTemporal(false);
            }
            if (ps.equals(BAD_1) ||ps.equals(BAD_2)) {
                res.setExisting(false);
            }
            if (ps.equals(ERROR_1) ||ps.equals(ERROR_2)) {
                CityRegisterException ex = new CityRegisterException("1","GRN error"+ps);
                throw ex;
            }
            if (ps.equals(ERRORT_1) ||ps.equals(ERRORT_2)) {
                TransportException ex = new TransportException("Transport error"+ps);
                throw ex;
            }
        }
        if (person instanceof Child){
            res.setExisting(true);
            res.setTemporal(true);
        }
        System.out.println(res);
        return res;
    }
}
