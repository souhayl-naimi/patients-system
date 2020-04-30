package io.naimi.patientsystem.Web;


import io.naimi.patientsystem.DAO.PatientRepository;
import io.naimi.patientsystem.Entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@Controller
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @RequestMapping(value = "/")
    public String index() {
        return "template";
    }


    @RequestMapping(value = "/patients")
    public String patients(Model model,
                           @RequestParam(name = "page", defaultValue = "0") int page,
                           @RequestParam(name = "size", defaultValue = "6") int size,
                           @RequestParam(name = "name", defaultValue = "") String name) {
        Page<Patient> patientPage = patientRepository.findByNameContainsIgnoreCase(name, PageRequest.of(page, size));
        model.addAttribute("result", patientPage.getTotalElements());
        model.addAttribute("patientList", patientPage.getContent());
        model.addAttribute("pages", new int[patientPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("name", name);
        model.addAttribute("size", size);
        return "patients";
    }
    @RequestMapping(value = "/username", method = RequestMethod.GET)
    @ResponseBody
    public String currentUserNameSimple(HttpServletRequest request) {
        Principal principal = request.getUserPrincipal();
        return principal.getName();
    }
    @RequestMapping(value = "/deletePatient", method = RequestMethod.POST)
    public String deletePatient(Long id, int page, int size, String name) {
        patientRepository.deleteById(id);
        return "redirect:/patients?page=" + page + "&size=" + size + "&name=" + name + "";
    }

    @RequestMapping(value = "/loginPage", method = RequestMethod.GET)
    public String login(){return "loginPage";}

    @RequestMapping(value = "formPatient", method = RequestMethod.GET)
    public String formPatient(Model model) {
        model.addAttribute("patient", new Patient());
        return "formPatient";
    }
    @RequestMapping(value = "editPatient", method = RequestMethod.GET)
    public String editPatient(Model model, Long id) {
        Patient patient = patientRepository.findById(id).get();
        System.out.println(id);
        model.addAttribute("patient", patient);
        return "formPatient";
    }
    @PostMapping(value = "savePatient")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult, Model model) {
        model.addAttribute("saved", patient);
        if (bindingResult.hasErrors()) return "formPatient";
        patientRepository.save(patient);
        return "confirmation";

    }
}
