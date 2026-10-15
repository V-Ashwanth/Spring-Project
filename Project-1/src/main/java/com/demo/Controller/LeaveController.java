package com.demo.Controller;

import com.demo.Model.Leave;
import com.demo.Service.LeaveService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping
    public ResponseEntity<List<Leave>> getLeaves(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(leaveService.searchLeavesByKeyword(search));
        }
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    @PostMapping
    public ResponseEntity<Leave> applyLeave(@Valid @RequestBody Leave leave) {
        Leave savedLeave = leaveService.applyLeave(leave);
        return new ResponseEntity<>(savedLeave, HttpStatus.CREATED);
    }

    @GetMapping("/{leaveId}")
    public ResponseEntity<Leave> searchLeaveById(@PathVariable String leaveId) {
        return leaveService.searchLeaveById(leaveId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/employee/{employeeName}")
    public ResponseEntity<List<Leave>> searchLeavesByEmployeeName(@PathVariable String employeeName) {
        List<Leave> leaves = leaveService.searchLeavesByEmployeeName(employeeName);
        if (!leaves.isEmpty()) {
            return ResponseEntity.ok(leaves);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/type/{leaveType}")
    public ResponseEntity<List<Leave>> searchLeavesByLeaveType(@PathVariable String leaveType) {
        List<Leave> leaves = leaveService.searchLeavesByLeaveType(leaveType);
        if (!leaves.isEmpty()) {
            return ResponseEntity.ok(leaves);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PatchMapping("/{leaveId}/days")
    public ResponseEntity<Void> updateLeaveDays(
            @PathVariable String leaveId, 
            @RequestParam int numberOfDays) {
        boolean updated = leaveService.updateLeaveDays(leaveId, numberOfDays);
        if (updated) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/{leaveId}")
    public ResponseEntity<Void> deleteLeave(@PathVariable String leaveId) {
        boolean deleted = leaveService.deleteLeave(leaveId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/employee/{employeeName}")
    public ResponseEntity<Void> deleteLeavesByEmployeeName(@PathVariable String employeeName) {
        long deletedCount = leaveService.deleteLeavesByEmployeeName(employeeName);
        if (deletedCount > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @DeleteMapping("/type/{leaveType}")
    public ResponseEntity<Void> deleteLeavesByLeaveType(@PathVariable String leaveType) {
        long deletedCount = leaveService.deleteLeavesByLeaveType(leaveType);
        if (deletedCount > 0) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}