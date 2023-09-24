package com.henrry.languagesproject.controller;

import com.henrry.languagesproject.model.Empleado;
import com.henrry.languagesproject.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@AllArgsConstructor
public class EmpleadoController {
    private final EmpleadoService empleadoService;

    @PostMapping("/created")
    public ResponseEntity<Empleado>guardarEmpleado(@RequestBody Empleado empleado){
        Empleado empleadoGuardar = this.empleadoService.saveEmpleado(empleado);
        return ResponseEntity.ok(empleadoGuardar);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Empleado>>listaDeEmpleado(){
        List<Empleado>listEmpleado = this.empleadoService.getAllEmpleados();
        return ResponseEntity.ok(listEmpleado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado>obtenerEmpleadoPorId(@PathVariable Long id){
        return this.empleadoService.getEmpleadoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("{id}")
    public ResponseEntity<Empleado>actualizarEmpleado(@PathVariable("id")Long id,
                                                      @RequestBody Empleado empleado){
        return empleadoService.getEmpleadoById(id)
                .map(empleadoGuardado ->{
                    empleadoGuardado.setName(empleado.getName());
                    empleadoGuardado.setLastName(empleado.getLastName());
                    empleadoGuardado.setEmail(empleado.getEmail());

                    Empleado empleadoActualizado = empleadoService.updateEmpleado(empleadoGuardado);
                    return new ResponseEntity<>(empleadoActualizado, HttpStatus.OK);
                } )
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String>eliminarEmpleado(@PathVariable("id")Long id){
        empleadoService.deleteEmpleado(id);
        return new ResponseEntity<String>("Empleado eliminado exitosamente", HttpStatus.OK);
    }
}
