package model.objects;

import java.io.Serializable;

public class Person implements Serializable {

    private int code;
    private String fullName;
    private String name;
    private String lastName;
    private String sex;
    private String birthday;
    private String height;
    private String nationality;

    public Person(int code, String name, String lastName, String sex, String birthday, String height, String nationality) {
        this.code = code;
        this.name = name;
        this.lastName = lastName;
        this.sex = sex;
        this.birthday = birthday;
        this.height = height;
        this.nationality = nationality;
        this.fullName = name + " " + lastName;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

}
