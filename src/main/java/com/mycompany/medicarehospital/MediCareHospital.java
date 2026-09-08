/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.medicarehospital;

import java.util.Scanner;

public class MediCareHospital {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        HospitalSystem hospital = new HospitalSystem();

        boolean running = true;

        while (running) {

            System.out.println("\n========================================");
            System.out.println("   MEDICARE HOSPITAL ADMISSION SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Patient Management");
            System.out.println("2. Bed Management");
            System.out.println("3. Reports");
            System.out.println("4. Exit");
            System.out.println("========================================");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                 patientManagementMenu(scanner, hospital);
                  break;

                case "2":
                    bedManagementMenu(scanner, hospital);
                    break;

                case "3":
                 reportsMenu(scanner, hospital);
                 break;

                case "4":
                    running = false;
                    System.out.println("\nThank you for using the MediCare Hospital Admission System.");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please enter a number from 1 to 4.");
            }
        }

        scanner.close();
    }
    private static void patientManagementMenu(Scanner scanner, HospitalSystem hospital) {

    boolean patientMenuRunning = true;

    while (patientMenuRunning) {

        System.out.println("\n========================================");
        System.out.println("        PATIENT MANAGEMENT");
        System.out.println("========================================");
        System.out.println("1. Register Patient");
        System.out.println("2. Search Patient");
        System.out.println("3. Update Patient");
        System.out.println("4. Delete Patient");
        System.out.println("5. Display All Patients");
        System.out.println("6. Return to Main Menu");
        System.out.println("========================================");

        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine();

        switch (choice) {

            case "1":
                registerPatient(scanner, hospital);
                break;

            case "2":

    System.out.print("\nEnter Patient ID to search: ");
    String searchId = scanner.nextLine().trim();

    Patient foundPatient = hospital.searchPatient(searchId);

    if (foundPatient != null) {
        System.out.println("\nPatient found:");
        System.out.println("-----------------------------------------");
        foundPatient.displayDetails();
        System.out.println("-----------------------------------------");
    } else {
        System.out.println("\nPatient not found.");
    }

    break;

            case "3":

    System.out.print("\nEnter Patient ID to update: ");
    String updateId = scanner.nextLine().trim();

    Patient patientToUpdate = hospital.searchPatient(updateId);

    if (patientToUpdate == null) {
        System.out.println("\nPatient not found.");
        break;
    }

    System.out.println("\nEnter the new patient details.");

    System.out.print("Enter First Name: ");
    String updatedFirstName = scanner.nextLine().trim();

    System.out.print("Enter Last Name: ");
    String updatedLastName = scanner.nextLine().trim();

    System.out.print("Enter Age: ");

    int updatedAge;

    try {
        updatedAge = Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid age. Please enter a number.");
        break;
    }

    System.out.print("Enter Gender: ");
    String updatedGender = scanner.nextLine().trim();

    System.out.print("Enter Medical Condition: ");
    String updatedMedicalCondition = scanner.nextLine().trim();

    System.out.println("\nSelect Patient Category:");
    System.out.println("1. Inpatient");
    System.out.println("2. Outpatient");
    System.out.println("3. Emergency");
    System.out.print("Enter category: ");

    String updatedCategoryChoice = scanner.nextLine().trim();

    PatientCategory updatedCategory;

switch (updatedCategoryChoice) {

    case "1":
        updatedCategory = PatientCategory.INPATIENT;
        break;

    case "2":
        updatedCategory = PatientCategory.OUTPATIENT;
        break;

    case "3":
        updatedCategory = PatientCategory.EMERGENCY;
        break;

    default:
        System.out.println("Invalid patient category.");
        return;
}

    if (updatedCategoryChoice.equals("1")
            || updatedCategoryChoice.equals("2")
            || updatedCategoryChoice.equals("3")) {

        if (hospital.updatePatient(
                updateId,
                updatedFirstName,
                updatedLastName,
                updatedAge,
                updatedGender,
                updatedMedicalCondition,
                updatedCategory)) {

            System.out.println("\nPatient details updated successfully.");

        } else {

            System.out.println("\nPatient update failed. Please check the information entered.");
        }
    }

    break;

           case "4":

    System.out.print("\nEnter Patient ID to delete: ");
    String deleteId = scanner.nextLine().trim();

    Patient patientToDelete = hospital.searchPatient(deleteId);

    if (patientToDelete == null) {
        System.out.println("\nPatient not found.");
        break;
    }

    System.out.println("\nPatient found:");
    patientToDelete.displayDetails();

    System.out.print("\nAre you sure you want to delete this patient? (Y/N): ");
    String confirmation = scanner.nextLine().trim();

    if (confirmation.equalsIgnoreCase("Y")) {

        if (hospital.deletePatient(deleteId)) {
            System.out.println("\nPatient deleted successfully.");
        } else {
            System.out.println("\nPatient could not be deleted.");
        }

    } else {
        System.out.println("\nDelete operation cancelled.");
    }

    break;

            case "5":
               hospital.displayAllPatients();
             break;

            case "6":
                patientMenuRunning = false;
                break;

            default:
                System.out.println("\nInvalid choice. Please enter a number from 1 to 6.");
        }
    }
}
    private static void registerPatient(Scanner scanner, HospitalSystem hospital) {

    System.out.println("\n========== REGISTER PATIENT ==========");

    System.out.print("Enter Patient ID: ");
    String patientId = scanner.nextLine().trim();

    if (hospital.searchPatient(patientId) != null) {
        System.out.println("A patient with this ID already exists.");
        return;
    }

    System.out.print("Enter First Name: ");
    String firstName = scanner.nextLine().trim();

    System.out.print("Enter Last Name: ");
    String lastName = scanner.nextLine().trim();

    System.out.print("Enter Age: ");

    int age;

    try {
        age = Integer.parseInt(scanner.nextLine().trim());
    } catch (NumberFormatException e) {
        System.out.println("Invalid age. Please enter a number.");
        return;
    }

    System.out.print("Enter Gender: ");
    String gender = scanner.nextLine().trim();

    System.out.print("Enter Medical Condition: ");
    String medicalCondition = scanner.nextLine().trim();

    System.out.println("\nSelect Patient Category:");
    System.out.println("1. Inpatient");
    System.out.println("2. Outpatient");
    System.out.println("3. Emergency");
    System.out.print("Enter category: ");

    String categoryChoice = scanner.nextLine().trim();

    PatientCategory category;

    switch (categoryChoice) {

        case "1":
            category = PatientCategory.INPATIENT;
            break;

        case "2":
            category = PatientCategory.OUTPATIENT;
            break;

        case "3":
            category = PatientCategory.EMERGENCY;
            break;

        default:
            System.out.println("Invalid patient category.");
            return;
    }

    Patient patient;

if (category == PatientCategory.INPATIENT) {

    patient = new Inpatient(
            patientId,
            firstName,
            lastName,
            age,
            gender,
            medicalCondition,
            category,
            "Ward 1",
            null
    );

} else {

    patient = new Patient(
            patientId,
            firstName,
            lastName,
            age,
            gender,
            medicalCondition,
            category
    );
}

if (hospital.registerPatient(patient)) {
    System.out.println("\nPatient registered successfully.");
} else {
    System.out.println("\nPatient registration failed. Please check the information entered.");
}
}
    private static void bedManagementMenu(Scanner scanner, HospitalSystem hospital) {

    boolean bedMenuRunning = true;

    while (bedMenuRunning) {

        System.out.println("\n========================================");
        System.out.println("           BED MANAGEMENT");
        System.out.println("========================================");
        System.out.println("1. Allocate Bed");
        System.out.println("2. Release Bed");
        System.out.println("3. Display Ward Layout");
        System.out.println("4. Display Available Beds");
        System.out.println("5. Display Occupied Beds");
        System.out.println("6. Return to Main Menu");
        System.out.println("========================================");

        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {

           case "1":

    System.out.print("\nEnter Patient ID to allocate a bed: ");
    String allocateId = scanner.nextLine().trim();

    Patient patientToAllocate = hospital.searchPatient(allocateId);

    if (patientToAllocate == null) {
        System.out.println("\nPatient not found.");
        break;
    }

    if (!(patientToAllocate instanceof Inpatient)) {
        System.out.println("\nOnly inpatients can be allocated a bed.");
        break;
    }

    Inpatient inpatient = (Inpatient) patientToAllocate;

    if (hospital.allocateBed(inpatient)) {
        System.out.println("\nBed allocated successfully.");
        System.out.println("Patient ID: " + inpatient.getPatientId());
        System.out.println("Bed Number: " + inpatient.getBedNumber());
    } else {
        System.out.println("\nBed allocation failed.");
    }

    break;

            case "2":

    System.out.print("\nEnter Patient ID to release their bed: ");
    String releaseId = scanner.nextLine().trim();

    if (hospital.releaseBed(releaseId)) {
        System.out.println("\nBed released successfully.");
    } else {
        System.out.println("\nBed release failed. Patient does not have an allocated bed.");
    }

    break;

            case "3":
                hospital.displayWardLayout();
                break;

            case "4":
                hospital.displayAvailableBeds();
                break;

            case "5":
                hospital.displayOccupiedBeds();
                break;

            case "6":
                bedMenuRunning = false;
                break;

            default:
                System.out.println("\nInvalid choice. Please enter a number from 1 to 6.");
        }
    }
}
    private static void reportsMenu(Scanner scanner, HospitalSystem hospital) {

    boolean reportsMenuRunning = true;

    while (reportsMenuRunning) {

        System.out.println("\n========================================");
        System.out.println("              REPORTS");
        System.out.println("========================================");
        System.out.println("1. Patient Report");
        System.out.println("2. Bed Occupancy Report");
        System.out.println("3. Sort Patients");
        System.out.println("4. Return to Main Menu");
        System.out.println("========================================");

        System.out.print("Enter your choice: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {

            case "1":
                hospital.displayPatientReport();
                break;

            case "2":
    hospital.displayBedOccupancyReport();
    break;
    
            case "3":

    System.out.println("\n========== SORT PATIENTS ==========");
    System.out.println("1. Sort by Surname");
    System.out.println("2. Sort by Patient ID");
    System.out.println("===================================");

    System.out.print("Enter your choice: ");

    String sortChoice = scanner.nextLine().trim();

    switch (sortChoice) {

        case "1":
            hospital.sortPatientsBySurname();
            break;

        case "2":
            hospital.sortPatientsById();
            break;

        default:
            System.out.println("\nInvalid sorting choice.");
    }

    break;

            case "4":
                reportsMenuRunning = false;
                break;

            default:
                System.out.println("\nInvalid choice. Please enter a number from 1 to 4.");
        }
    }
}
}
