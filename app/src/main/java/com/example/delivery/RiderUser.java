package com.example.delivery;

public class RiderUser {
    //Property name must be the same as what we defined in real time database
    private String name, phone, email ,password,shortdescription,imageUrl;

    public RiderUser() {
        //Constructor , it is needed
    }

    public RiderUser(String name, String phone, String email, String password, String shortdescription,String imageUrl) {
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.name = name;
        this.email = email;
        this.password = password;
        if (shortdescription.trim().equals("")) {
            this.shortdescription = "Information is not provided";
        } else this.shortdescription = shortdescription;

    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone =phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }





    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getShortdescription() {
        return shortdescription;
    }
    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
