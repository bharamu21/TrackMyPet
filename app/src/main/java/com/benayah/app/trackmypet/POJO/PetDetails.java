package com.benayah.app.trackmypet.POJO;

import java.util.ArrayList;

/**
 * Created by User on 02-08-2017.
 */

public class PetDetails {

    private ArrayList<String> petImagesList;
    private String petName;
    private String petType;
    private String petBreed;
    private String petAge;
    private String petColor;
    private String petAdditionalInfo;
    private String petOwnerName;
    private String petLostAddress;
    private String petOwnerPermanentNumber;
    private String petOwnerAddress;
    private String petOwnerAlternateNumber;
    private String petSex;

    public PetDetails(ArrayList<String> petImagesList,String petName,String petType,String petBreed,String petAge
                        ,String petSex,String petColor,String petAdditionalInfo,String petOwnerName,String petOwnerAddress,
                      String petOwnerPermanentNumber,String petOwnerAlternateNumber,String petLostAddress)
    {
        this.petImagesList = petImagesList;
        this.petName = petName;
        this.petType = petType;
        this.petBreed = petBreed;
        this.petAge = petAge;
        this.petSex = petSex;
        this.petColor = petColor;
        this.petAdditionalInfo = petAdditionalInfo;
        this.petOwnerName = petOwnerName;
        this.petOwnerAddress = petOwnerAddress;
        this.petOwnerPermanentNumber = petOwnerPermanentNumber;
        this.petOwnerAlternateNumber = petOwnerAlternateNumber;
        this.petLostAddress = petLostAddress;
    }

    public void setPetImagesList(ArrayList<String> petImagesList) {
        this.petImagesList = petImagesList;
    }

    public ArrayList<String> getPetImagesList() {
        return petImagesList;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetAdditionalInfo(String petAdditionalInfo) {
        this.petAdditionalInfo = petAdditionalInfo;
    }

    public String getPetAdditionalInfo() {
        return petAdditionalInfo;
    }

    public void setPetOwnerName(String petOwnerName) {
        this.petOwnerName = petOwnerName;
    }

    public String getPetOwnerName() {
        return petOwnerName;
    }

    public void setPetOwnerAddress(String petOwnerAddress) {
        this.petOwnerAddress = petOwnerAddress;
    }

    public String getPetOwnerAddress() {
        return petOwnerAddress;
    }

    public void setPetOwnerPermanentNumber(String petOwnerPermanentNumber) {
        this.petOwnerPermanentNumber = petOwnerPermanentNumber;
    }

    public String getPetOwnerPermanentNumber() {
        return petOwnerPermanentNumber;
    }

    public void setPetOwnerAlternateNumber(String petOwnerAlternateNumber) {
        this.petOwnerAlternateNumber = petOwnerAlternateNumber;
    }

    public String getPetOwnerAlternateNumber() {
        return petOwnerAlternateNumber;
    }

    public void setPetLostAddress(String petLostAddress) {
        this.petLostAddress = petLostAddress;
    }

    public String getPetLostAddress() {
        return petLostAddress;
    }
}
