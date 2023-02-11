package edu.java.satudentorder.validators;

import edu.java.satudentorder.Domein.AnswerWedding;
import edu.java.satudentorder.Domein.StudentOrder;

public class WeddingValidator {
    public AnswerWedding checkWedding(StudentOrder so){
        System.out.println("Wedding yep");
        return new AnswerWedding();
    }

}
