package cpu2;

public class P_Class {
    private int brustTime;
    private int priority;
    private int quantum;
    private int processID;
    private String state;  


    public P_Class(int brustTime, int priority, int quantum, int processID) {
        this.brustTime = brustTime;
        this.priority = priority;
        this.quantum = quantum;
        this.processID = processID;
        this.state = "Ready";
    }

    // getters and setters
    public int getBrustTime() {
        return brustTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getQuantum() {
        return quantum;
    }

    public int getProcessID() {
        return processID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    // setters
    public void setBrustTime(int brustTime) {
        this.brustTime = brustTime;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setQuantum(int quantum) {
        this.quantum = quantum;
    }

    public void setProcessID(int processID) {
        this.processID = processID;
    }

    @Override
    public String toString() {
        return "Process ID: " + processID + ", Brust Time: " + brustTime + ", Priority: " + priority + ", Quantum: " + quantum + ", State: " + state;
    }
}
