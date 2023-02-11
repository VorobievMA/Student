package edu.java.satudentorder;

import edu.java.satudentorder.Domein.AnswerChildren;
import edu.java.satudentorder.Domein.AnswerStudent;
import edu.java.satudentorder.Domein.AnswerWedding;
import edu.java.satudentorder.Domein.StudentOrder;
import edu.java.satudentorder.Domein.register.AnswerCityRegister;
import edu.java.satudentorder.mail.MailSander;
import edu.java.satudentorder.validators.ChildrenValidator;
import edu.java.satudentorder.validators.CityRegisterValidator;
import edu.java.satudentorder.validators.StudentValidator;
import edu.java.satudentorder.validators.WeddingValidator;

import java.util.LinkedList;
import java.util.List;

public class StudentOrderValidator {
    private CityRegisterValidator cityRegisterVal;
    private WeddingValidator weddingVal;
    private  ChildrenValidator childrenVal;
    private StudentValidator studentVal;
    private MailSander mailSander;

    public StudentOrderValidator() {
        cityRegisterVal = new CityRegisterValidator();
        weddingVal = new WeddingValidator();
        childrenVal = new ChildrenValidator();
        studentVal =  new StudentValidator();
        mailSander = new MailSander();
    }

    public static void main(String[] args) {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkALL();

    }

    public  void checkALL(){
       // while (true) {
        List<StudentOrder> soList = readStudentOrders();

//        for (int c = 0; c<soArray.length; c++) {
//            System.out.println();
//            checkOneOrder(soArray[c]);
//        }
        for (StudentOrder so: soList){
            System.out.println();
            checkOneOrder(so);
        }
            //System.out.println("start");
//            if (so == null) {
//                break;
//            }
//
//            AnswerCityRegister cityAnswer = checkCityRegister(so);
//            if (!cityAnswer.success){
//                //continue;
//                break;
//            }
//

//        }
        //System.out.println("finish");
    }
    public List<StudentOrder> readStudentOrders(){
        List<StudentOrder> soList = new LinkedList<>();

        for (int c=0;c < 5; c++){
            StudentOrder so = SaveStudentOrder.buildStudentOrder(c);
            soList.add(so);
        }
        //StudentOrder so = new StudentOrder();
        return soList;
    }
    public void checkOneOrder(StudentOrder so){
        AnswerCityRegister cityRegisterVal = checkCityRegister(so);
        AnswerWedding wedAnswer = checkWedding(so);
        AnswerChildren childAnswer = checkChildren(so);
        AnswerStudent studentAnswer = checkStudent(so);
        sendMail(so);
    }



    public AnswerCityRegister checkCityRegister (StudentOrder so){

        return  cityRegisterVal.checkCityRegister(so);
    }


    public AnswerWedding checkWedding(StudentOrder so){
        /*WeddingValidator wd = new WeddingValidator();
        return wd.checkWedding(so);*/
        return weddingVal.checkWedding(so);
    }
    public AnswerChildren checkChildren(StudentOrder so){
        /*ChildrenValidator cv = new ChildrenValidator();
        return cv.checkChildren(so);*/
        return childrenVal.checkChildren(so);
    }
    public AnswerStudent checkStudent(StudentOrder so){
        /*return new StudentValidator().checkStudent(so);*/
        return studentVal.checkStudent(so);
    }
    public void sendMail(StudentOrder so){
        /*new MailSander().sendMail(so);*/
        mailSander.sendMail(so);
    }
}
