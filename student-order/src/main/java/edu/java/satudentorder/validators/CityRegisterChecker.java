package edu.java.satudentorder.validators;

import edu.java.satudentorder.Domein.Person;
import edu.java.satudentorder.Domein.register.CityRegisterResponse;
import edu.java.satudentorder.exception.CityRegisterException;
import edu.java.satudentorder.exception.TransportException;

public interface CityRegisterChecker {
    CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException, TransportException;
}
