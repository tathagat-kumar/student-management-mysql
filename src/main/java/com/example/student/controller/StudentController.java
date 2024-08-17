package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.repository.StudentRepository;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String viewHomePage(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "index";
    }

    @GetMapping("/getAllStudentsInfoXml")
    public List<Student> viewHomePageXml() {
        //List<Student> students = studentService.getAllStudents();
        return studentService.getAllStudents();
    }

    @GetMapping("/addStudent")
    public String showAddStudentPage(Model model)
    {
        Student student = new Student();
        model.addAttribute("student",student);
        return "add-student";
    }

    @PostMapping("/saveStudent")
    public String saveStudent(@ModelAttribute("student") Student student, Model model)
    {
        studentService.saveStudent(student);
        return "redirect:/";
    }

    @GetMapping("editStudent/{id}")
    public String showUpdateStudentPage(@PathVariable("id") long id, Model model)
    {
        Student student = studentService.getStudentById(id);
        model.addAttribute("student",student);
        return "update-student";
    }

    @PostMapping("/updateStudent/{id}")
    public String updateExpense(@PathVariable("id") long id,@ModelAttribute("student") Student student)
    {
        Student existingStudent = studentService.getStudentById(id);
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        existingStudent.setEmail(student.getEmail());
        studentService.saveStudent(existingStudent);
        return "redirect:/";
    }

    @GetMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") long id)
    {
        studentService.deleteStudentById(id);
        return "redirect:/";
    }
}
