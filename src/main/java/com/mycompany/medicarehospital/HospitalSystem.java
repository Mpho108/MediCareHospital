/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.medicarehospital;

import java.util.ArrayList;

public class HospitalSystem {

    private ArrayList<Patient> patients;
    private Inpatient[][] beds;

    public HospitalSystem() {
        patients = new ArrayList<>();
        beds = new Inpatient[4][5];
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public Inpatient[][] getBeds() {
        return beds;
    }
    
    public boolean registerPatient(Patient patient) {
    if (patient == null) {
        return false;
    }

    if (patient.getPatientId() == null || patient.getPatientId().trim().isEmpty()) {
        return false;
    }

    if (patient.getFirstName() == null || patient.getFirstName().trim().isEmpty()) {
        return false;
    }

    if (patient.getLastName() == null || patient.getLastName().trim().isEmpty()) {
        return false;
    }

    if (patient.getAge() < 0 || patient.getAge() > 120) {
        return false;
    }

    if (patient.getGender() == null || patient.getGender().trim().isEmpty()) {
        return false;
    }

    if (patient.getMedicalCondition() == null || patient.getMedicalCondition().trim().isEmpty()) {
        return false;
    }

    if (patient.getCategory() == null) {
        return false;
    }

    if (searchPatient(patient.getPatientId()) != null) {
        return false;
    }

    patients.add(patient);
    return true;
}
    public Patient searchPatient(String patientId) {

    if (patientId == null || patientId.trim().isEmpty()) {
        return null;
    }

    for (int i = 0; i < patients.size(); i++) {

        if (patients.get(i).getPatientId().equalsIgnoreCase(patientId.trim())) {
            return patients.get(i);
        }

    }

    return null;
}
    public boolean updatePatient(String patientId, String firstName, String lastName,
                             int age, String gender, String medicalCondition,
                             PatientCategory category) {

    Patient patient = searchPatient(patientId);

    if (patient == null) {
        return false;
    }

    if (firstName == null || firstName.trim().isEmpty()
            || lastName == null || lastName.trim().isEmpty()
            || age < 0 || age > 120
            || gender == null || gender.trim().isEmpty()
            || medicalCondition == null || medicalCondition.trim().isEmpty()
            || category == null) {
        return false;
    }

    patient.setFirstName(firstName.trim());
    patient.setLastName(lastName.trim());
    patient.setAge(age);
    patient.setGender(gender.trim());
    patient.setMedicalCondition(medicalCondition.trim());
    patient.setCategory(category);

    return true;
}
    public boolean deletePatient(String patientId) {

    Patient patient = searchPatient(patientId);

    if (patient == null) {
        return false;
    }

    if (patient instanceof Inpatient) {
        releaseBed(patientId);
    }

    patients.remove(patient);
    return true;
}
    public void displayAllPatients() {

    if (patients.isEmpty()) {
        System.out.println("No patients are currently registered.");
        return;
    }

    System.out.println("\n========== REGISTERED PATIENTS ==========");

    for (int i = 0; i < patients.size(); i++) {
        System.out.println("\nPatient " + (i + 1));
        System.out.println("-----------------------------------------");
        patients.get(i).displayDetails();
    }

    System.out.println("=========================================");
}
   public boolean allocateBed(Inpatient inpatient) {

    if (inpatient == null) {
        return false;
    }

    if (inpatient.getCategory() != PatientCategory.INPATIENT) {
        return false;
    }

    Patient registeredPatient = searchPatient(inpatient.getPatientId());

    if (registeredPatient == null) {
        return false;
    }

    if (!(registeredPatient instanceof Inpatient)) {
        return false;
    }

    if (findPatientBed(inpatient.getPatientId()) != null) {
        return false;
    }

    if (areAllBedsOccupied()) {
        return false;
    }

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column] == null) {

                beds[row][column] = inpatient;

                String bedNumber = String.format(
                        "B%02d", (row * 5) + column + 1
                );

                inpatient.setBedNumber(bedNumber);

                return true;
            }
        }
    }

    return false;
}
    public String findPatientBed(String patientId) {

    if (patientId == null || patientId.trim().isEmpty()) {
        return null;
    }

    for (int row = 0; row < beds.length; row++) {
        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column] != null
                    && beds[row][column].getPatientId().equalsIgnoreCase(patientId.trim())) {

                return String.format("B%02d", (row * 5) + column + 1);
            }
        }
    }

    return null;
}
    public boolean releaseBed(String patientId) {

    if (patientId == null || patientId.trim().isEmpty()) {
        return false;
    }

    for (int row = 0; row < beds.length; row++) {
        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column] != null
                    && beds[row][column].getPatientId().equalsIgnoreCase(patientId.trim())) {

                beds[row][column].setBedNumber(null);
                beds[row][column] = null;

                return true;
            }
        }
    }

    return false;
}
    public void displayWardLayout() {

    System.out.println("\n========== WARD BED LAYOUT ==========");

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            String bedNumber = String.format("B%02d", (row * 5) + column + 1);

            if (beds[row][column] == null) {
                System.out.print("[" + bedNumber + ": Available] ");
            } else {
                System.out.print("[" + bedNumber + ": Occupied] ");
            }
        }

        System.out.println();
    }

    System.out.println("=====================================");
}
    public void displayAvailableBeds() {

    System.out.println("\n========== AVAILABLE BEDS ==========");

    boolean availableBedFound = false;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column] == null) {

                String bedNumber = String.format("B%02d", (row * 5) + column + 1);

                System.out.println(bedNumber + " - Available");

                availableBedFound = true;
            }
        }
    }

    if (!availableBedFound) {
        System.out.println("No beds are currently available.");
    }

    System.out.println("====================================");
}
    public void displayOccupiedBeds() {

    System.out.println("\n========== OCCUPIED BEDS ==========");

    boolean occupiedBedFound = false;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column] != null) {

                String bedNumber = String.format("B%02d", (row * 5) + column + 1);

                System.out.println(bedNumber + " - Patient ID: "
                        + beds[row][column].getPatientId());

                occupiedBedFound = true;
            }
        }
    }

    if (!occupiedBedFound) {
        System.out.println("No beds are currently occupied.");
    }

    System.out.println("===================================");
}
    public int getOccupiedBedCount() {

    int occupiedBeds = 0;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column] != null) {
                occupiedBeds++;
            }
        }
    }

    return occupiedBeds;
}
    public int getAvailableBedCount() {

    int availableBeds = 0;

    for (int row = 0; row < beds.length; row++) {

        for (int column = 0; column < beds[row].length; column++) {

            if (beds[row][column] == null) {
                availableBeds++;
            }
        }
    }

    return availableBeds;
}
    public boolean areAllBedsOccupied() {
    return getAvailableBedCount() == 0;
}
    public void displayPatientReport() {

    if (patients.isEmpty()) {
        System.out.println("\nNo patients are currently registered.");
        return;
    }

    System.out.println("\n========== PATIENT REPORT ==========");

    for (int i = 0; i < patients.size(); i++) {

        System.out.println("\nPatient " + (i + 1));
        System.out.println("-------------------------------------");

        patients.get(i).displayDetails();

        System.out.println("-------------------------------------");
    }

    System.out.println("=====================================");
}
    public void displayBedOccupancyReport() {

    int totalPatients = patients.size();
    int occupiedBeds = getOccupiedBedCount();
    int availableBeds = getAvailableBedCount();

    double occupancyPercentage = (occupiedBeds / 20.0) * 100;

    System.out.println("\n========== BED OCCUPANCY REPORT ==========");
    System.out.println("Total Registered Patients: " + totalPatients);
    System.out.println("Total Occupied Beds: " + occupiedBeds);
    System.out.println("Total Available Beds: " + availableBeds);
    System.out.println("Occupancy Percentage: " + occupancyPercentage + "%");
    System.out.println("==========================================");
}
    public void sortPatientsBySurname() {

    Patient[] patientArray = patients.toArray(new Patient[patients.size()]);

    for (int i = 0; i < patientArray.length - 1; i++) {

        for (int j = 0; j < patientArray.length - 1 - i; j++) {

            if (patientArray[j].getLastName().compareToIgnoreCase(
                    patientArray[j + 1].getLastName()) > 0) {

                Patient temporary = patientArray[j];
                patientArray[j] = patientArray[j + 1];
                patientArray[j + 1] = temporary;
            }
        }
    }

    System.out.println("\n========== PATIENTS SORTED BY SURNAME ==========");

    for (int i = 0; i < patientArray.length; i++) {
        System.out.println(
                patientArray[i].getLastName() + ", "
                + patientArray[i].getFirstName()
                + " - Patient ID: "
                + patientArray[i].getPatientId()
        );
    }

    System.out.println("===============================================");
}

public void sortPatientsById() {

    Patient[] patientArray = patients.toArray(new Patient[patients.size()]);

    for (int i = 0; i < patientArray.length - 1; i++) {

        for (int j = 0; j < patientArray.length - 1 - i; j++) {

            if (patientArray[j].getPatientId().compareToIgnoreCase(
                    patientArray[j + 1].getPatientId()) > 0) {

                Patient temporary = patientArray[j];
                patientArray[j] = patientArray[j + 1];
                patientArray[j + 1] = temporary;
            }
        }
    }

    System.out.println("\n========== PATIENTS SORTED BY PATIENT ID ==========");

    for (int i = 0; i < patientArray.length; i++) {
        System.out.println(
                "Patient ID: "
                + patientArray[i].getPatientId()
                + " - "
                + patientArray[i].getFirstName()
                + " "
                + patientArray[i].getLastName()
        );
    }

    System.out.println("===================================================");
}
}