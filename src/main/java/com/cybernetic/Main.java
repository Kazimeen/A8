package com.cybernetic;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Create a waiting list
        WaitingList waitingList = new WaitingList();

        // Create some patients
        Patient johnDoe = new Patient("P001", "John Doe", "A+", 70, "HLA-A");
        Patient janeSmith = new Patient("P002", "Jane Smith", "B-", 65, "HLA-B");
        Patient bobJohnson = new Patient("P003", "Bob Johnson", "O+", 80, "HLA-A");
        Patient aliceBrown = new Patient("P004", "Alice Brown", "AB-", 55, "HLA-C");

        // Add patients to the waiting list with randomized priorities
        System.out.println("Adding patients to the waiting list...");
        waitingList.addPatient(johnDoe);     // Priority randomized
        waitingList.addPatient(janeSmith);   // Priority randomized
        waitingList.addPatient(bobJohnson);  // Priority randomized

        // Display initial waiting list
        System.out.println("\nInitial Waiting List:");
        waitingList.displayWaitingList();

        // Add a new patient
        System.out.println("\nAdding new patient: Alice Brown");
        waitingList.addPatient(aliceBrown);  // Priority randomized

        // Display updated waiting list
        System.out.println("Updated Waiting List:");
        waitingList.displayWaitingList();

        // Remove highest priority patient
        Patient removedPatient = waitingList.removeHighestPriority();
        if (removedPatient != null) {
            System.out.println("\nRemoving highest priority patient: " + removedPatient.getName());
        }

        // Update priority for a patient (randomized)
        System.out.println("\nUpdating priority for Bob Johnson");
        waitingList.updatePriority("P003"); // Priority is now randomized

        // Display updated waiting list
        System.out.println("Updated Waiting List:");
        waitingList.displayWaitingList();

        // Create an organ
        Organ cyberHeart = new Organ("O001", "CyberHeart-X1", "A+", 350000, "HLA-A"); // Weight in grams

        // Create an OrganCompatibilityAnalyzer
        OrganCompatibilityAnalyzer analyzer = new OrganCompatibilityAnalyzer();

        // Prepare lists of organs and patients for the analyzer
        List<Organ> organs = new ArrayList<>();
        organs.add(cyberHeart); // Add organs as needed

        List<Patient> patients = new ArrayList<>();
        patients.add(johnDoe);
        patients.add(janeSmith);
        patients.add(bobJohnson);
        patients.add(aliceBrown);

        // Add organs and patients to the analyzer using method references
        organs.forEach(analyzer::addOrgan);
        patients.forEach(analyzer::addPatient);

        // Match organ to waiting list
        System.out.println("\nMatching " + cyberHeart.getName() + " to Waiting List:");
        Patient matchedPatient = analyzer.findCompatiblePatient(cyberHeart, waitingList);
        if (matchedPatient != null) {
            System.out.println("Compatible patient found: " + matchedPatient.getName() +
                    " (Priority: " + waitingList.getPosition(matchedPatient.getId()) + ")");
        } else {
            System.out.println("No compatible patient found in the waiting list.");
        }

        // After matchedPatient is found, remove the patient from the waiting list
        if (matchedPatient != null) {
            System.out.println("\nRemoving matched patient from the waiting list...");
            waitingList.removePatient(matchedPatient.getId());
            System.out.println("Updated Waiting List:");
            waitingList.displayWaitingList();
        }
    }
}
