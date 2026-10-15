package com.demo.Service;

import com.demo.Model.Leave;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class LeaveService {
    private final Map<String, Leave> leaveStore = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        applyLeave(new Leave("LEV-001", "EMP-101", "Alice Smith", "Casual Leave", 2, "Family function"));
        applyLeave(new Leave("LEV-002", "EMP-102", "Bob Jones", "Sick Leave", 3, "Fever and cold"));
        applyLeave(new Leave("LEV-003", "EMP-103", "Charlie Brown", "Annual Leave", 5, "Vacation trip"));
    }

    // Apply for leave (Add)
    public Leave applyLeave(Leave leave) {
        if (leave.getLeaveId() == null || leave.getLeaveId().trim().isEmpty()) {
            leave.setLeaveId("LEV-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        }
        leaveStore.put(leave.getLeaveId(), leave);
        return leave;
    }

    // Search leave by ID
    public Optional<Leave> searchLeaveById(String leaveId) {
        return Optional.ofNullable(leaveStore.get(leaveId));
    }

    // Search leaves by keyword (Employee Name or Leave Type)
    public List<Leave> searchLeavesByKeyword(String keyword) {
        String lowerCaseKeyword = keyword.toLowerCase();
        return leaveStore.values().stream()
                .filter(l -> (l.getEmployeeName() != null && l.getEmployeeName().toLowerCase().contains(lowerCaseKeyword)) ||
                           (l.getLeaveType() != null && l.getLeaveType().toLowerCase().contains(lowerCaseKeyword)))
                .collect(Collectors.toList());
    }

    // Search leaves by Employee Name
    public List<Leave> searchLeavesByEmployeeName(String employeeName) {
        String lowerCaseName = employeeName.toLowerCase();
        return leaveStore.values().stream()
                .filter(l -> l.getEmployeeName() != null && l.getEmployeeName().toLowerCase().contains(lowerCaseName))
                .collect(Collectors.toList());
    }

    // Search leaves by Leave Type
    public List<Leave> searchLeavesByLeaveType(String leaveType) {
        String lowerCaseType = leaveType.toLowerCase();
        return leaveStore.values().stream()
                .filter(l -> l.getLeaveType() != null && l.getLeaveType().toLowerCase().contains(lowerCaseType))
                .collect(Collectors.toList());
    }

    // Update number of days for a leave request
    public boolean updateLeaveDays(String leaveId, int numberOfDays) {
        Leave leave = leaveStore.get(leaveId);
        if (leave != null) {
            leave.setNumberOfDays(numberOfDays);
            return true;
        }
        return false;
    }

    // Delete leave application by ID
    public boolean deleteLeave(String leaveId) {
        return leaveStore.remove(leaveId) != null;
    }

    // Delete leaves by Employee Name
    public long deleteLeavesByEmployeeName(String employeeName) {
        long initialSize = leaveStore.size();
        leaveStore.values().removeIf(l -> l.getEmployeeName() != null && l.getEmployeeName().equalsIgnoreCase(employeeName));
        return initialSize - leaveStore.size();
    }

    // Delete leaves by Leave Type
    public long deleteLeavesByLeaveType(String leaveType) {
        long initialSize = leaveStore.size();
        leaveStore.values().removeIf(l -> l.getLeaveType() != null && l.getLeaveType().equalsIgnoreCase(leaveType));
        return initialSize - leaveStore.size();
    }

    // Get all leave applications
    public List<Leave> getAllLeaves() {
        return new ArrayList<>(leaveStore.values());
    }
}