package com.example.prashant.dentist;

/**
 * Created by sinprl on 6/4/2015.
 */
public class patientInformation {

    int _id;
    String _name;
    String _phone;
    String _address;
    String _sex;
    String _age;

    public patientInformation(){}

    public patientInformation(int id, String name, String phone, String address, String sex, String age){
        this._id=id;
        this._name=name;
        this._phone=phone;
        this._address=address;
        this._sex=sex;
        this._age=age;
    }

    public patientInformation(int id, String name, String phone){
        this._id=id;
        this._name=name;
        this._phone=phone;
    }

    public int getID(){return this._id;}
    public void setID(int id){this._id=id;}

    public String getName(){return this._name;}
    public void setName(String name){this._name=name;}

    public String getPhone(){return this._phone;}
    public void setPhone(String phone){this._phone=phone;}

    public String getAddress(){return this._address;}
    public void setAddress(String address){this._address=address;}

    public String getSex(){return this._sex;}
    public void setSex(String sex){this._sex=sex;}

    public String getAge(){return this._age;}
    public void setAge(String age){this._age=age;}


}
