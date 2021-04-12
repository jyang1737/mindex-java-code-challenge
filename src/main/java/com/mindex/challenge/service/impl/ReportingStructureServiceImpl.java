package com.mindex.challenge.service.impl;

import com.mindex.challenge.controller.EmployeeController;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ReportingStructure getReport(String id) {
        LOG.debug("Creating reporting structure for [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        ReportingStructure reportingStructure = new ReportingStructure(employee);
        reportingStructure.setNumberOfReports(calculateNumberOfReports(employee));
        return reportingStructure;
    }

    private int calculateNumberOfReports(Employee employee) {
        int totalReportees = 0;
        List<Employee> reportees = employee.getDirectReports();
        if (reportees != null) {
            for (Employee directReportee : reportees) {
                Employee directReporteeEmployee = employeeRepository.findByEmployeeId(directReportee.getEmployeeId());

                if (directReporteeEmployee == null) {
                    throw new RuntimeException("Invalid employeeId: " + directReportee.getEmployeeId());
                }

                totalReportees += calculateNumberOfReports(directReporteeEmployee);
            }
            totalReportees += employee.getDirectReports().size();
        }
        return totalReportees;
    }
}
