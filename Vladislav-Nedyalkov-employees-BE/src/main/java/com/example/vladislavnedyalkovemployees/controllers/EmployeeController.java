package com.example.vladislavnedyalkovemployees.controllers;

import com.example.vladislavnedyalkovemployees.dtos.PairProjectResponse;
import com.example.vladislavnedyalkovemployees.entities.PairProjectWorkDuration;
import com.example.vladislavnedyalkovemployees.exceptions.FileFormatException;
import com.example.vladislavnedyalkovemployees.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/upload")
    public ResponseEntity<PairProjectResponse> getPairsWorkDurationFromCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PairProjectResponse(null, "File is empty"));
        }

        try {
            return ResponseEntity.ok(new PairProjectResponse(employeeService.getPairsWorkDurationFromCsv(file), null));
        } catch (DataFormatException | NumberFormatException | FileFormatException e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new PairProjectResponse(null, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new PairProjectResponse(null, "There was a problem with the server!"));
        }
    }
}
