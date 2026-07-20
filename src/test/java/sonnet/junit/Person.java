package sonnet.junit;

public class Person {
    private String fname;
    private String lname;
    private int age;

    public Person(String fname, String lname, int age) {
        this.fname = fname;
        this.lname = lname;
        this.age = age;
    }

    public String getFirstName() {
        return fname;
    }

    public String getLastName() {
        return lname;
    }

    public int getAge() {
        return age;
    }

    public void setFirstName(String fname) {
        if (fname == null || fname.isEmpty()){
            throw new IllegalArgumentException("First name cannot be null or empty.");
        }
        else {
            this.fname = fname;
        }

    }

    public void setLastName(String lname) {
        if (lname == null || lname.isEmpty()){
            throw new IllegalArgumentException("Last name cannot be null or empty.");
        }
        else {
            this.lname = lname;
        }

    }

    public void setAge(int age) {
        if (age < 0 || age > 150){
            throw new IllegalArgumentException("Age must be between 0 and 150.");
        }
        else {
            this.age = age;
        }

    }


}
