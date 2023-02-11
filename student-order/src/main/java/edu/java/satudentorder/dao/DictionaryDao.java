package edu.java.satudentorder.dao;

import edu.java.satudentorder.Domein.CountryArea;
import edu.java.satudentorder.Domein.PassportOffice;
import edu.java.satudentorder.Domein.RegisterOffice;
import edu.java.satudentorder.Domein.Street;
import edu.java.satudentorder.exception.DaoException;

import java.util.List;

public interface DictionaryDao {
    List<Street> findStreets(String pattern) throws DaoException;
    List<PassportOffice> findPassportOffices(String areaID) throws DaoException;
    List<RegisterOffice> findRegisterOffice(String areaID) throws DaoException;
    List<CountryArea> findAreas(String areaID) throws DaoException;
}
