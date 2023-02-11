package edu.java.satudentorder.validators;

import edu.java.satudentorder.Domein.AnswerChildren;
import edu.java.satudentorder.Domein.StudentOrder;

public class ChildrenValidator {
    public AnswerChildren checkChildren(StudentOrder so) {
        System.out.println("Children check is running");
        return new AnswerChildren();
    }
}
