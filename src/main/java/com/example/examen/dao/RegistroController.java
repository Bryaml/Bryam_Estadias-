package com.example.examen.dao;

import com.example.examen.Repository.DocenteRepository;
import com.example.examen.Repository.TecnicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import com.example.examen.Entity.Docente;
import com.example.examen.Entity.Tecnico;
import com.example.examen.service.RegistroForm;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/registro")
public class RegistroController {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Autowired
    private JavaMailSender mailSender;

    @PostMapping("")
    public String registrar(@RequestBody RegistroForm form) {
        System.out.println("entrando al metodo");
        String rol = form.getRol();
        System.out.println(form.toString());
        System.out.println("Nombres: " + form.getNombres());
        System.out.println("Apellidos: " + form.getApellidos());
        System.out.println("Email: " + form.getEmail());
        System.out.println("Password: " + form.getPassword());
        System.out.println("Teléfono: " + form.getTelefono());
        System.out.println("rol: " + form.getRol());

        try {
            if ("docente".equals(rol)) {
                Docente docente = new Docente();
                docente.setNombres(form.getNombres());
                docente.setApellidos(form.getApellidos());
                docente.setEmail(form.getEmail());
                docente.setPassword(form.getPassword());
                docente.setTelefono(form.getTelefono());
                docente.setRol(form.getRol());

                docenteRepository.save(docente);
            } else if ("tecnico".equals(rol)) {
                Tecnico tecnico = new Tecnico();
                tecnico.setNombres(form.getNombres());
                tecnico.setApellidos(form.getApellidos());
                tecnico.setEmail(form.getEmail());
                tecnico.setPassword(form.getPassword());
                tecnico.setTelefono(form.getTelefono());
                tecnico.setRol(form.getRol());

                tecnicoRepository.save(tecnico);
            }

            // enviar correo electrónico
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(form.getEmail());
            helper.setSubject("Registro exitoso");
            helper.setText("Hola " + form.getNombres() + ", acabas de ser registrado. Tus datos de registro son:\n" +
                    "Nombres: " + form.getNombres() + "\n" +
                    "Apellidos: " + form.getApellidos() + "\n" +
                    "Email: " + form.getEmail() + "\n" +
                    "Password: " + form.getPassword() + "\n" +
                    "Teléfono: " + form.getTelefono() + "\n" +
                    "Rol: " + form.getRol());

            mailSender.send(message);

            return "registro_exitoso";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}
