package com.demo.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Leave {
    
    private String leaveId;
    private String employeeId;
    
    @NotBlank(message = "Employee name is required")
    private String employeeName;
    
    @NotBlank(message = "Leave type is mandatory")
    private String leaveType;
    
    @Min(value = 1, message = "Leave days should be minimum 1")
    private int numberOfDays;
    
    private String reason;

    public Leave() {}

    public Leave(String leaveId, String employeeId, String employeeName, String leaveType, int numberOfDays, String reason) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.leaveType = leaveType;
        this.numberOfDays = numberOfDays;
        this.reason = reason;
    }

    // Getters and Setters
    public String getLeaveId() { return leaveId; }
    public void setLeaveId(String leaveId) { this.leaveId = leaveId; }

    public String getEmployeeId() { return employeeId; }
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public int getNumberOfDays() { return numberOfDays; }
    public void setNumberOfDays(int numberOfDays) { this.numberOfDays = numberOfDays; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    @Override
    public String toString() {
        return "Leave [leaveId=" + leaveId + ", employeeId=" + employeeId + ", employeeName=" + employeeName 
                + ", leaveType=" + leaveType + ", numberOfDays=" + numberOfDays + ", reason=" + reason + "]";
    }
}