package com.cybernetic;

import java.util.Random;

/**
 * Manages the waiting list of patients using a singly linked list.
 */
public class WaitingList {
    protected WaitingListNode head;
    private Random random;

    /**
     * Constructs an empty WaitingList instance.
     */
    public WaitingList() {
        this.head = null;
        this.random = new Random();
    }

    /**
     * Adds a patient to the waiting list with a randomized priority between 1 and 10.
     * The list is maintained in descending order of priority.
     *
     * @param patient The patient to be added.
     */
    public void addPatient(Patient patient) {
        int priority = random.nextInt(10) + 1; // Random priority between 1 and 10
        WaitingListNode newNode = new WaitingListNode(patient, priority);

        // If the list is empty or the new node has higher priority than the head
        if (head == null || priority > head.getPriority()) {
            newNode.setNext(head);
            head = newNode;
            System.out.println("Added patient: " + patient.getName() + " (Priority: " + priority + ")");
            return;
        }

        // Traverse the list to find the correct position
        WaitingListNode current = head;
        while (current.getNext() != null && current.getNext().getPriority() >= priority) {
            current = current.getNext();
        }

        // Insert the new node
        newNode.setNext(current.getNext());
        current.setNext(newNode);
        System.out.println("Added patient: " + patient.getName() + " (Priority: " + priority + ")");
    }

    /**
     * Removes and returns the patient with the highest priority from the waiting list.
     *
     * @return The patient with the highest priority, or null if the list is empty.
     */
    public Patient removeHighestPriority() {
        if (head == null) {
            System.out.println("Waiting list is empty. No patient to remove.");
            return null;
        }

        Patient highestPriorityPatient = head.getPatient();
        head = head.getNext();
        System.out.println("Removed highest priority patient: " + highestPriorityPatient.getName());
        return highestPriorityPatient;
    }

    /**
     * Updates the priority of a patient identified by their patientId.
     *
     * @param patientId The unique identifier of the patient.
     */
    public void updatePriority(String patientId) {
        // Remove the patient from the list
        Patient patient = removePatient(patientId);
        if (patient != null) {
            // Re-add the patient with a new random priority
            addPatient(patient);
            System.out.println("Updated priority for patient ID " + patientId + " to a new randomized value.");
        }
    }

    /**
     * Removes a patient from the waiting list based on their patientId.
     *
     * @param patientId The unique identifier of the patient to be removed.
     * @return The removed patient, or null if not found.
     */
    public Patient removePatient(String patientId) {
        if (head == null) {
            System.out.println("Waiting list is empty. Cannot remove patient.");
            return null;
        }

        // If the head needs to be removed
        if (head.getPatient().getId().equals(patientId)) {
            Patient removedPatient = head.getPatient();
            System.out.println("Removed patient: " + removedPatient.getName());
            head = head.getNext();
            return removedPatient;
        }

        // Traverse to find the node to remove
        WaitingListNode current = head;
        while (current.getNext() != null && !current.getNext().getPatient().getId().equals(patientId)) {
            current = current.getNext();
        }

        if (current.getNext() == null) {
            System.out.println("Patient with ID " + patientId + " not found in the waiting list.");
            return null;
        }

        // Remove the node
        Patient removedPatient = current.getNext().getPatient();
        System.out.println("Removed patient: " + removedPatient.getName());
        current.setNext(current.getNext().getNext());
        return removedPatient;
    }

    /**
     * Displays the current waiting list.
     */
    public void displayWaitingList() {
        if (head == null) {
            System.out.println("Waiting list is empty.");
            return;
        }

        
        WaitingListNode current = head;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.getPatient().getName() +
                    " (Priority: " + current.getPriority() + ")");
            current = current.getNext();
            position++;
        }
    }

    /**
     * Retrieves the position of a patient in the waiting list based on their patientId.
     *
     * @param patientId The unique identifier of the patient.
     * @return The position of the patient in the list, or -1 if not found.
     */
    public int getPosition(String patientId) {
        WaitingListNode current = head;
        int position = 1;
        while (current != null) {
            if (current.getPatient().getId().equals(patientId)) {
                return position;
            }
            current = current.getNext();
            position++;
        }
        return -1; // Not found
    }

    /**
     * Checks if the waiting list is empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Getter for the head node. Added for OrganCompatibilityAnalyzer access.
     *
     * @return The head of the waiting list.
     */
    public WaitingListNode getHead() {
        return head;
    }
}
