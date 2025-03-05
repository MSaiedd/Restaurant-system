package Controllers;

import java.util.regex.Pattern;

public class Validator {
    private Pattern name_pattern = Pattern.compile("^[a-z]+$");
    private Pattern phone_pattern = Pattern.compile("^(01[0125])[0-9]{8}$");
    private Pattern email_pattern = Pattern.compile("^[A-Za-z0-9+_-]+@gmail.com$");
    private Pattern agePattern = Pattern.compile("^(1[8-9]|[2-5][0-9]|60)$");

    private Pattern salaryPattern = Pattern.compile("^(1000|10000|[1-9]\\d{3})$");

    public boolean check_name(CharSequence characters) {
        String name=characters.toString().toLowerCase();
        if (name.length()>50)
            return false;
        return name_pattern.matcher(name).matches();
    }


    protected boolean check_phone(CharSequence characters) {
        String phone=characters.toString();
        return phone_pattern.matcher(phone).matches();
    }

    protected boolean check_email(CharSequence characters) {
        String email = characters.toString();
        return email_pattern.matcher(email).matches();
    }

    protected boolean check_password(CharSequence characters) {
        String password=characters.toString();
        return password.length() >= 8;
    }

    protected boolean check_age(CharSequence characters) {
        String age = characters.toString();
        return agePattern.matcher(age).matches();
    }

    protected boolean check_salary(CharSequence characters) {
        String salary = characters.toString();
        return salaryPattern.matcher(salary).matches();
    }


}
