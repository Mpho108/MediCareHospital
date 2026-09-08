/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author maboy
 */
package com.mycompany.medicarehospital;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HospitalSystemTest {

    @Test
    public void testRegisterPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P001",
                "John",
                "Smith",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        boolean result = hospital.registerPatient(patient);

        assertTrue(result);
        assertEquals(1, hospital.getPatients().size());
        assertNotNull(hospital.searchPatient("P001"));
    }

    @Test
    public void testSearchPatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P002",
                "Sarah",
                "Jones",
                30,
                "Female",
                "Asthma",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        Patient foundPatient = hospital.searchPatient("P002");

        assertNotNull(foundPatient);
        assertEquals("Sarah", foundPatient.getFirstName());
        assertEquals("Jones", foundPatient.getLastName());
    }

    @Test
    public void testUpdatePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P003",
                "David",
                "Brown",
                40,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result = hospital.updatePatient(
                "P003",
                "Daniel",
                "Williams",
                41,
                "Male",
                "Pneumonia",
                PatientCategory.OUTPATIENT
        );

        assertTrue(result);

        Patient updatedPatient = hospital.searchPatient("P003");

        assertNotNull(updatedPatient);
        assertEquals("Daniel", updatedPatient.getFirstName());
        assertEquals("Williams", updatedPatient.getLastName());
        assertEquals(41, updatedPatient.getAge());
        assertEquals("Pneumonia", updatedPatient.getMedicalCondition());
        assertEquals(PatientCategory.OUTPATIENT, updatedPatient.getCategory());
    }

    @Test
    public void testDeletePatient() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient = new Patient(
                "P004",
                "Michael",
                "Davis",
                35,
                "Male",
                "Infection",
                PatientCategory.OUTPATIENT
        );

        hospital.registerPatient(patient);

        boolean result = hospital.deletePatient("P004");

        assertTrue(result);
        assertNull(hospital.searchPatient("P004"));
        assertEquals(0, hospital.getPatients().size());
    }

    @Test
    public void testAllocateBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient inpatient = new Inpatient(
                "P005",
                "James",
                "Miller",
                50,
                "Male",
                "Heart condition",
                PatientCategory.INPATIENT,
                "Ward 1",
                null
        );

        hospital.registerPatient(inpatient);

        boolean result = hospital.allocateBed(inpatient);

        assertTrue(result);
        assertEquals("B01", inpatient.getBedNumber());
        assertEquals(1, hospital.getOccupiedBedCount());
        assertEquals(19, hospital.getAvailableBedCount());
    }

    @Test
    public void testReleaseBed() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient inpatient = new Inpatient(
                "P006",
                "Emma",
                "Wilson",
                45,
                "Female",
                "Diabetes",
                PatientCategory.INPATIENT,
                "Ward 1",
                null
        );

        hospital.registerPatient(inpatient);
        hospital.allocateBed(inpatient);

        boolean result = hospital.releaseBed("P006");

        assertTrue(result);
        assertNull(inpatient.getBedNumber());
        assertEquals(0, hospital.getOccupiedBedCount());
        assertEquals(20, hospital.getAvailableBedCount());
    }

    @Test
    public void testDuplicatePatientId() {

        HospitalSystem hospital = new HospitalSystem();

        Patient firstPatient = new Patient(
                "P007",
                "Peter",
                "Taylor",
                28,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient secondPatient = new Patient(
                "P007",
                "Robert",
                "Anderson",
                32,
                "Male",
                "Asthma",
                PatientCategory.EMERGENCY
        );

        boolean firstResult = hospital.registerPatient(firstPatient);
        boolean secondResult = hospital.registerPatient(secondPatient);

        assertTrue(firstResult);
        assertFalse(secondResult);
        assertEquals(1, hospital.getPatients().size());
    }

    @Test
    public void testOccupiedBedAllocation() {

        HospitalSystem hospital = new HospitalSystem();

        Inpatient inpatient = new Inpatient(
                "P008",
                "Thomas",
                "Moore",
                55,
                "Male",
                "Injury",
                PatientCategory.INPATIENT,
                "Ward 1",
                null
        );

        hospital.registerPatient(inpatient);

        boolean firstAllocation = hospital.allocateBed(inpatient);
        boolean secondAllocation = hospital.allocateBed(inpatient);

        assertTrue(firstAllocation);
        assertFalse(secondAllocation);
        assertEquals(1, hospital.getOccupiedBedCount());
        assertEquals("B01", inpatient.getBedNumber());
    }

    @Test
    public void testFullWard() {

        HospitalSystem hospital = new HospitalSystem();

        for (int i = 1; i <= 20; i++) {

            String patientId = String.format("P%03d", i);

            Inpatient inpatient = new Inpatient(
                    patientId,
                    "Patient",
                    "Number" + i,
                    30,
                    "Male",
                    "General condition",
                    PatientCategory.INPATIENT,
                    "Ward 1",
                    null
            );

            assertTrue(hospital.registerPatient(inpatient));
            assertTrue(hospital.allocateBed(inpatient));
        }

        assertEquals(20, hospital.getOccupiedBedCount());
        assertEquals(0, hospital.getAvailableBedCount());
        assertTrue(hospital.areAllBedsOccupied());

        Inpatient twentyFirstPatient = new Inpatient(
                "P021",
                "Extra",
                "Patient",
                30,
                "Female",
                "General condition",
                PatientCategory.INPATIENT,
                "Ward 1",
                null
        );

        assertTrue(hospital.registerPatient(twentyFirstPatient));

        boolean result = hospital.allocateBed(twentyFirstPatient);

        assertFalse(result);
        assertEquals(20, hospital.getOccupiedBedCount());
        assertEquals(0, hospital.getAvailableBedCount());
    }

    @Test
    public void testSortingPatients() {

        HospitalSystem hospital = new HospitalSystem();

        Patient patient1 = new Patient(
                "P003",
                "Thabo",
                "Zulu",
                25,
                "Male",
                "Flu",
                PatientCategory.OUTPATIENT
        );

        Patient patient2 = new Patient(
                "P001",
                "Sipho",
                "Adams",
                30,
                "Male",
                "Asthma",
                PatientCategory.OUTPATIENT
        );

        Patient patient3 = new Patient(
                "P002",
                "Kabelo",
                "Mokoena",
                28,
                "Male",
                "Infection",
                PatientCategory.EMERGENCY
        );

        hospital.registerPatient(patient1);
        hospital.registerPatient(patient2);
        hospital.registerPatient(patient3);

        PrintStream originalOutput = System.out;

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        hospital.sortPatientsBySurname();

        String surnameOutput = output.toString();

        assertTrue(
                surnameOutput.indexOf("Adams, Sipho")
                < surnameOutput.indexOf("Mokoena, Kabelo")
        );

        assertTrue(
                surnameOutput.indexOf("Mokoena, Kabelo")
                < surnameOutput.indexOf("Zulu, Thabo")
        );

        output.reset();

        hospital.sortPatientsById();

        String idOutput = output.toString();

        assertTrue(
                idOutput.indexOf("Patient ID: P001")
                < idOutput.indexOf("Patient ID: P002")
        );

        assertTrue(
                idOutput.indexOf("Patient ID: P002")
                < idOutput.indexOf("Patient ID: P003")
        );

        System.setOut(originalOutput);
    }
}