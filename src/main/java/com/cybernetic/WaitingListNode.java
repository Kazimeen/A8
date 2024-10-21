package com.cybernetic;

/**
 * Represents a node in the waiting list linked list.
 */
public class WaitingListNode {
    private Patient patient;
    private int priority;
    private WaitingListNode next;

    /**
     * Constructs a new WaitingListNode instance.
     *
     * @param patient  The patient to be added to the waiting list.
     * @param priority The priority of the patient (higher number means higher priority).
     */
    public WaitingListNode(Patient patient, int priority) {
        this.patient = patient;
        this.priority = priority;
        this.next = null;
    }

    // Getters and Setters

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public WaitingListNode getNext() {
        return next;
    }

    public void setNext(WaitingListNode next) {
        this.next = next;
    }
}
