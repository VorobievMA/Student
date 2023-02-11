package edu.java.satudentorder.validators;

import edu.java.satudentorder.Domein.Child;
import edu.java.satudentorder.Domein.Person;
import edu.java.satudentorder.Domein.StudentOrder;
import edu.java.satudentorder.Domein.register.AnswerCityRegister;
import edu.java.satudentorder.Domein.register.AnswerCityRegisterItem;
import edu.java.satudentorder.Domein.register.CityRegisterResponse;
import edu.java.satudentorder.exception.CityRegisterException;
import edu.java.satudentorder.exception.TransportException;

public class CityRegisterValidator {
    public static final String no_grn = "no_grn";
    public String hostName;
    public String login;
    public String password;
    protected int port;
    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder so) {
        AnswerCityRegister ans = new AnswerCityRegister();
        ans.addItem(checkPerson(so.getHusband()));
        ans.addItem(checkPerson(so.getWife()));
        for (Child child : so.getChildren()) {
            ans.addItem(checkPerson(child));
        }
        return ans;
    }
//        try {
//        CityRegisterCheckerResponse hans = personChecker.checkPerson(so.getHusband());
//        CityRegisterCheckerResponse wans = personChecker.checkPerson(so.getWife());
//        List <Child> children = so.getChildren();
//        for (int i = 0; i<so.getChildren().size(); i++){
//            CityRegisterCheckerResponse cans = personChecker.checkPerson(so.getChildren().get(i));}
//        }
//        catch (CityRegisterException ex){
//            ex.printStackTrace(System.out);}
//        AnswerCityRegister ans = new AnswerCityRegister();
//        return ans;
  // }

    private AnswerCityRegisterItem checkPerson(Person person) {
        AnswerCityRegisterItem.CityStatus status =null;
        AnswerCityRegisterItem.CityError error = null;
        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = tmp.isRegistered() ?
                    AnswerCityRegisterItem.CityStatus.YES:
                    AnswerCityRegisterItem.CityStatus.NO;
        } catch (CityRegisterException ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(ex.getCode(), ex.getMessage());
        }catch (TransportException ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(no_grn, ex.getMessage());
        }
        catch (Exception ex){
            ex.printStackTrace(System.out);
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            error = new AnswerCityRegisterItem.CityError(no_grn, ex.getMessage());
        }
        AnswerCityRegisterItem ans = new AnswerCityRegisterItem(status, person, error);
        return ans;
    }
}
