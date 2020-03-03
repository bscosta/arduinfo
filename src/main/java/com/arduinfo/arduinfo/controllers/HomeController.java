package com.arduinfo.arduinfo.controllers;

import com.arduinfo.arduinfo.dto.ObjectsDto;
import com.arduinfo.arduinfo.services.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private ArduinoService arduinoService;

    @GetMapping
    public ModelAndView home() {
        return new ModelAndView("home");
    }

    @PostMapping("/connectPort")
    public ResponseEntity<?> initArduino(@RequestHeader(value = "port", required = true) String port,
                                         @RequestHeader(value = "taxa", required = true) Integer taxa) {
        String response = arduinoService.initialize(port, taxa);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/closeConectPort")
    public ResponseEntity<?> closePort() {
        Boolean closed = arduinoService.close();

        if (!closed)
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok().build();
    }

    @PostMapping("/sendCommand")
    public ResponseEntity<?> sendCommand(@RequestHeader(value = "command", required = true) Integer command) {
        arduinoService.enviaDados(command);

        return ResponseEntity.ok("ok");
    }

    @GetMapping("/getData")
    public ResponseEntity<ObjectsDto> getData() {
        ObjectsDto objectsDto = new ObjectsDto();
        objectsDto.setId(Long.valueOf(1));
        objectsDto.setName("Bruno Costa");
        objectsDto.setStatus(true);


        return ResponseEntity.ok(objectsDto);
    }
}
