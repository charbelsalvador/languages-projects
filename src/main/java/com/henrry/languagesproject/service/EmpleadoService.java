package com.henrry.languagesproject.service;

import com.henrry.languagesproject.model.Empleado;

import java.util.List;
import java.util.Optional;

public interface EmpleadoService {
    Empleado saveEmpleado (Empleado empleado);
    List<Empleado> getAllEmpleados ();
    Optional<Empleado>getEmpleadoById(Long id);
    Empleado updateEmpleado(Empleado empleadoActualizado);
    void deleteEmpleado(Long id);
}
