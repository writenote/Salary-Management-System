package com.example.controller;

import com.example.model.Salary;
import com.example.model.SelectedDate;
import com.example.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/home")
public class SalaryController {

    @Autowired
    SalaryRepository repository;

    @PostMapping("/list")
    public List<Salary> findAll(@RequestBody SelectedDate selectedDate) {
        System.out.println("Get a List...");

        List<Salary> list = repository.findAll().stream()
                .filter(a -> a.filterDate().equals(selectedDate.getYear() + "-" + selectedDate.getMonth()))
                .collect(Collectors.toList());

        list.sort((a1, a2) -> a2.getId().compareTo(a1.getId()));

        return list;
    }

    @DeleteMapping("/salary/{id}")
    public ResponseEntity<String> deleteRow(@PathVariable Integer id) {
        System.out.println("Delete the Line with ID = " + id + "...");

        repository.deleteById(id);

        return new ResponseEntity<>("The Line has been deleted!", HttpStatus.OK);
    }

    @PostMapping("/add")
    public Salary addRow(@RequestBody Salary salary) {
        Salary _salary = repository.save(
                new Salary(
                    salary.getHourlyWage(),
                    salary.getStartTime(),
                    salary.getEndTime()
                )
        );

        return _salary;
    }
}
