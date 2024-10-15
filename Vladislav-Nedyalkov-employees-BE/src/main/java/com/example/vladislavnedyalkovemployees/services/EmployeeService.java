package com.example.vladislavnedyalkovemployees.services;

import com.example.vladislavnedyalkovemployees.entities.PairProjectWorkDuration;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface EmployeeService {

    List<PairProjectWorkDuration> getPairsWorkDurationFromCsv(MultipartFile file) throws Exception;
}
