package com.cybernetic;

import java.util.ArrayList;
import java.util.List;

/**
 * Analyzes and calculates compatibility scores between organs and patients.
 */
public class OrganCompatibilityAnalyzer {
    private List<Organ> organs;
    private List<Patient> patients;

    /**
     * Constructs a new OrganCompatibilityAnalyzer instance.
     */
    public OrganCompatibilityAnalyzer() {
        organs = new ArrayList<>();
        patients = new ArrayList<>();
    }

    /**
     * Adds an organ to the analyzer.
     *
     * @param organ The organ to be added.
     */
    public void addOrgan(Organ organ) {
        organs.add(organ);
    }

    /**
     * Adds a patient to the analyzer.
     *
     * @param patient The patient to be added.
     */
    public void addPatient(Patient patient) {
        patients.add(patient);
    }

    /**
     * Finds the highest priority compatible patient from the waiting list for the given organ.
     *
     * @param organ       The organ for which to find a compatible patient.
     * @param waitingList The waiting list containing patients.
     * @return The highest priority compatible patient, or null if no compatible patient is found.
     */
    public Patient findCompatiblePatient(Organ organ, WaitingList waitingList) {
        WaitingListNode current = waitingList.getHead();
        Patient matchedPatient = null;

        while (current != null) {
            Patient patient = current.getPatient();
            if (isCompatible(organ, patient)) {
                matchedPatient = patient;
                System.out.println("Compatible patient found: " + patient.getName() +
                        " (Priority: " + current.getPriority() + ")");
                break; // Stop after finding the first compatible patient
            }
            current = current.getNext();
        }

        if (matchedPatient == null) {
            System.out.println("No compatible patient found for organ: " + organ.getName());
        }

        return matchedPatient;
    }

    /**
     * Determines if an organ is compatible with a patient based on compatibility score.
     *
     * @param organ   The organ being evaluated.
     * @param patient The patient receiving the organ.
     * @return true if the compatibility score is above a certain threshold, false otherwise.
     */
    private boolean isCompatible(Organ organ, Patient patient) {
        double score = calculateCompatibilityScore(organ, patient);
        double threshold = 50.0; // Example threshold; can be adjusted as needed
        return score >= threshold;
    }

    /**
     * Calculates the overall compatibility score between an organ and a patient.
     *
     * @param organ   The organ being evaluated.
     * @param patient The patient receiving the organ.
     * @return The compatibility score.
     */
    public double calculateCompatibilityScore(Organ organ, Patient patient) {
        double bloodTypeScore = calculateBloodTypeCompatibility(organ.getBloodType(), patient.getBloodType());
        double weightScore = calculateWeightCompatibility(organ.getWeight(), patient.getWeight());
        double hlaScore = calculateHlaCompatibility(organ.getHlaType(), patient.getHlaType());
        // Weights: Blood Type 40%, Weight 30%, HLA Type 30%
        return (bloodTypeScore * 0.4) + (weightScore * 0.3) + (hlaScore * 0.3);
    }

    /**
     * Calculates the blood type compatibility between donor and recipient.
     *
     * @param donorType     Blood type of the donor organ.
     * @param recipientType Blood type of the recipient patient.
     * @return The blood type compatibility score.
     */
    private double calculateBloodTypeCompatibility(String donorType, String recipientType) {
        // Define compatibility rules
        if (donorType.equals(recipientType)) {
            return 100.0;
        }
        // Simple compatibility mapping
        switch (donorType) {
            case "O-" -> {
                return 100.0; // Universal donor
            }
            case "O+" -> {
                if (recipientType.endsWith("+")) return 90.0;
            }
            case "A-" -> {
                if (recipientType.startsWith("A") || recipientType.equals("AB-")) return 80.0;
            }
            case "A+" -> {
                if (recipientType.startsWith("A") || recipientType.equals("AB+")) return 70.0;
            }
            case "B-" -> {
                if (recipientType.startsWith("B") || recipientType.equals("AB-")) return 80.0;
            }
            case "B+" -> {
                if (recipientType.startsWith("B") || recipientType.equals("AB+")) return 70.0;
            }
            case "AB-" -> {
                if (recipientType.equals("AB-") || recipientType.equals("AB+")) return 60.0;
            }
            case "AB+" -> {
                // AB+ can receive from all types but assign lower compatibility
                return 50.0;
            }
        }
        return 0.0; // Incompatible
    }

    /**
     * Calculates the weight compatibility between donor organ and recipient patient.
     *
     * @param organWeight   Weight of the organ in grams.
     * @param patientWeight Weight of the patient in kilograms.
     * @return The weight compatibility score.
     */
    private double calculateWeightCompatibility(int organWeight, int patientWeight) {
        double organWeightKg = organWeight / 1000.0; // Convert grams to kilograms
        double difference = Math.abs(organWeightKg - patientWeight);
        if (difference > 30) { // Adjusted threshold, e.g., 30kg
            return 0.0;
        }
        // Linearly decrease score as difference increases
        return 100.0 - (difference * (100.0 / 30.0));
    }

    /**
     * Calculates the HLA compatibility between donor and recipient.
     *
     * @param organHla   HLA type of the donor organ.
     * @param patientHla HLA type of the recipient patient.
     * @return The HLA compatibility score.
     */
    private double calculateHlaCompatibility(String organHla, String patientHla) {
        // Simple HLA matching: exact match gets full score, else partial or zero
        if (organHla.equals(patientHla)) {
            return 100.0;
        }
        // Implement more complex HLA matching logic if needed
        return 50.0; // Partial compatibility
    }
}
