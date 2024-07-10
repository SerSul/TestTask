package com.example.testtask.Api;


import com.example.testtask.entity.Student;
import com.example.testtask.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/api/v1")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public String getAllStudents(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Student> studentPage = studentService.getAllStudents(page, 10);
        model.addAttribute("students", studentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", studentPage.getTotalPages());
        return "students";
    }

    @PostMapping("/students/moveUp/{id}")
    public String moveStudentUp(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        studentService.moveStudentUp(id);
        return "redirect:/api/v1/students?page=" + page;
    }

    @PostMapping("/students/moveDown/{id}")
    public String moveStudentDown(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        studentService.moveStudentDown(id);
        return "redirect:/api/v1/students?page=" + page;
    }


    @PostMapping("/students/edit/{id}")
    public String editStudent(@PathVariable Long id, @ModelAttribute Student studentDetails, @RequestParam(defaultValue = "0") int page) {
        studentService.updateStudent(id, studentDetails);
        return "redirect:/api/v1/students?page=" + page;
    }

    @PostMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id, @RequestParam(defaultValue = "0") int page) {
        studentService.deleteStudent(id);
        return "redirect:/api/v1/students?page=" + page;
    }
}
