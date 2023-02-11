package edu.java.satudentorder.dao;

import edu.java.satudentorder.Domein.StudentOrder;
import edu.java.satudentorder.exception.DaoException;

import java.util.List;

public interface StudentOrderDao {
    long saveStudentOrder(StudentOrder so) throws DaoException;

    List<StudentOrder> getStudentOrders() throws DaoException;
}
