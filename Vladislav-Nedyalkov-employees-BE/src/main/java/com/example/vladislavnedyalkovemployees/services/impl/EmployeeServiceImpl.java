package com.example.vladislavnedyalkovemployees.services.impl;

import com.example.vladislavnedyalkovemployees.entities.EmployeeProject;
import com.example.vladislavnedyalkovemployees.entities.PairProjectWorkDuration;
import com.example.vladislavnedyalkovemployees.exceptions.FileFormatException;
import com.example.vladislavnedyalkovemployees.services.EmployeeService;
import com.example.vladislavnedyalkovemployees.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public List<PairProjectWorkDuration> getPairsWorkDurationFromCsv(MultipartFile file) throws Exception {
        return getPairWorkDurations(readCSV(file.getInputStream()));
    }

    private List<PairProjectWorkDuration> getPairWorkDurations(List<EmployeeProject> employeeProjects) {
        Map<Integer, List<EmployeeProject>> projects = new HashMap<>();

        for (EmployeeProject employeeProject : employeeProjects) {
            if (!projects.containsKey(employeeProject.getProjectID())) {
                projects.put(employeeProject.getProjectID(), new ArrayList<>());
            }
            projects.get(employeeProject.getProjectID()).add(employeeProject);
        }

        List<PairProjectWorkDuration> longestPairs = new ArrayList<>();

        for (Map.Entry<Integer, List<EmployeeProject>> entry : projects.entrySet()) {
            List<EmployeeProject> currentEmployeeProjects = entry.getValue();

            PairProjectWorkDuration longestPair = null;
            long maxDaysWorked = 0;

            for (int i = 0; i < currentEmployeeProjects.size(); i++) {
                for (int j = i + 1; j < currentEmployeeProjects.size(); j++) {
                    EmployeeProject firstEmployeeProject = currentEmployeeProjects.get(i);
                    EmployeeProject secondEmployeeProject = currentEmployeeProjects.get(j);

                    if (firstEmployeeProject.getProjectID() == secondEmployeeProject.getProjectID()) {
                        long overlapDays = DateUtil.getOverlappingDays(firstEmployeeProject.getDateFrom(), firstEmployeeProject.getDateTo(),
                                secondEmployeeProject.getDateFrom(), secondEmployeeProject.getDateTo());

                        if (overlapDays > 0 && overlapDays > maxDaysWorked) {
                            maxDaysWorked = overlapDays;
                            longestPair = new PairProjectWorkDuration(firstEmployeeProject.getEmpID(),
                                    secondEmployeeProject.getEmpID(), entry.getKey(), overlapDays);
                        }
                    }
                }
            }
            if (longestPair != null) {
                longestPairs.add(longestPair);
            }
        }

        return longestPairs;
    }

    private List<EmployeeProject> readCSV(InputStream inputStream) throws Exception {
        List<EmployeeProject> employeeProjects = new ArrayList<>();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = br.readLine()) != null) {
            String[] fileRow = line.split(", ");
            EmployeeProject employeeProject = new EmployeeProject();

            Field[] fields = EmployeeProject.class.getDeclaredFields();
            if (fileRow.length != fields.length) {
                throw new FileFormatException("File format does not match the requirements(FirstEmpId, SecondEmpId, DateFrom, DateTo)");
            }

            for (int i = 0; i < fileRow.length; i++) {
                Field field = fields[i];
                field.setAccessible(true);

                if (field.getType() == int.class) {
                    try {
                        field.set(employeeProject, Integer.parseInt(fileRow[i]));
                    } catch (NumberFormatException e) {
                        throw new NumberFormatException("Invalid number format: " + fileRow[i]);
                    }
                } else if (field.getType() == LocalDate.class) {
                    LocalDate date = DateUtil.parseDate(fileRow[i]);
                    field.set(employeeProject, date);
                }
            }
            employeeProjects.add(employeeProject);
        }
        br.close();
        return employeeProjects;
    }
}
