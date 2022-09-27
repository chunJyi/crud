package com.example.crud_example.controller;

import com.example.crud_example.entity.Student;
import com.example.crud_example.exception.ResourceNotFoundException;
import com.example.crud_example.service.IStudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping("student")
public class StudentController {

    private IStudentService studentService;

    @GetMapping("")
    public String home(ModelMap modelMap){
        modelMap.addAttribute("students",studentService.getStudent());
        return "home";
    }

    @GetMapping("/create")
    public String studentForm(ModelMap model) {
        model.addAttribute("student",new Student());
        return "studentFrom";
    }

    @PostMapping("")
    public String createStudent(@Valid @ModelAttribute(name = "student") Student student , BindingResult result,ModelMap model){
        if(result.hasErrors()){
            return "redirect:/student";
        }
        studentService.saveStudent(student);
        return "redirect:/student";
    }

    @GetMapping("/edit/{id}")
    public String updateStudent(@PathVariable(name = "id") int id,ModelMap modelMap) {
        Student student = null;
        try {
            student=studentService.findById(id);
        }
        catch (ResourceNotFoundException exception)
        {
          return "redirect:/student";
        }
        modelMap.addAttribute("student",student);
       return "studentFrom";
    }

    @GetMapping("delete/{id}")
    public String  DeleteStudent(@PathVariable (name = "id") int id, ModelMap modelMap){
        Student student = null;
        try {
            student=studentService.findById(id);
        }catch (ResourceNotFoundException exception){
            return  "redirect:/student";
        }
        studentService.deleteStudent(student);
       return "redirect:/student";
    }
}
