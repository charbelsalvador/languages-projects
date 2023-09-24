package com.henrry.languagesproject.service.impl;

import com.henrry.languagesproject.exception.ResourceExistException;
import com.henrry.languagesproject.model.Empleado;
import com.henrry.languagesproject.repository.EmpleadoRepository;
import com.henrry.languagesproject.service.EmpleadoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    @Override
    public Empleado saveEmpleado(Empleado empleado) {
        Optional<Empleado>empleadoSaved = empleadoRepository.findByEmail(empleado.getEmail());
        if (empleadoSaved.isPresent()){
            throw new ResourceExistException("El empleado con este email ya existe" + empleado.getEmail());
        }
        return empleadoRepository.save(empleado);
    }

    @Override
    public List<Empleado> getAllEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Optional<Empleado> getEmpleadoById(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Empleado updateEmpleado(Empleado empleadoActualizado) {
        return empleadoRepository.save(empleadoActualizado);
    }

    @Override
    public void deleteEmpleado(Long id) {
        empleadoRepository.deleteById(id);
    }
}
