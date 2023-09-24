package com.henrry.languagesproject.service.impl;

import com.henrry.languagesproject.exception.ResourceExistException;
import com.henrry.languagesproject.model.Empleado;
import com.henrry.languagesproject.repository.EmpleadoRepository;
import com.henrry.languagesproject.service.EmpleadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.apache.logging.log4j.ThreadContext.isEmpty;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpleadoServiceImplTest {
    private EmpleadoRepository empleadoRepository;
    private EmpleadoService empleadoService;
    private Empleado empleado;

    @BeforeEach
    void setUp(){
        empleadoRepository = mock(EmpleadoRepository.class);
        empleadoService = new EmpleadoServiceImpl(empleadoRepository);

        empleado = new Empleado();
        empleado.setId(1L);
        empleado.setName("Cristian");
        empleado.setLastName("Ramirez");
        empleado.setEmail("C1@gmail.com");
    }

    @DisplayName("Test para guardar un empleado")
    @Test
    void testGuardarEmpleado(){
        //
        when(empleadoRepository.findByEmail(empleado.getEmail())).thenReturn(Optional.empty());
        when(empleadoRepository.save(empleado)).thenReturn(empleado);

        //when
        Empleado response = empleadoService.saveEmpleado(empleado);

        //then
        assertNotNull(response);
    }

    @DisplayName("Test para guardar un empleado con Throw Exception")
    @Test
    void testParGuardarUnEmpleadoConThrowException(){
        //given
        when(empleadoRepository.findByEmail(empleado.getEmail())).thenReturn(Optional.of(empleado));

        //when
        assertThrows(ResourceExistException.class,()->{
            empleadoService.saveEmpleado(empleado);
        });

        //then
        verify(empleadoRepository, never()).save(empleado);
    }

    @DisplayName("Test para listar los empleado")
    @Test
    void testParaListarEmpleados(){
        //given
        Empleado empleado1 = new Empleado();
        empleado.setId(2L);
        empleado.setName("Julen");
        empleado.setLastName("Oliva");
        empleado.setEmail("J2@gmail.com");

        when(empleadoRepository.findAll()).thenReturn(List.of(empleado1, empleado));

        //when
        List<Empleado>response = empleadoService.getAllEmpleados();

        //then
        assertNotNull(response);
        assertEquals(response.size(),2);
    }

    @DisplayName("Test para obtener una lista vacía")
    @Test
    void testParaObtenerUnaListaVacía(){
        //given
        Empleado empleado1= new Empleado();
        empleado.setId(2L);
        empleado.setName("Julen");
        empleado.setLastName("Oliva");
        empleado.setEmail("J2@gmail.com");

        when(empleadoRepository.findAll()).thenReturn(Collections.emptyList());

        //when
        List<Empleado>response = empleadoService.getAllEmpleados();

        //then
        assertTrue(response.isEmpty());
        assertEquals(0, 0);
    }

    @DisplayName("Test para obtener un empleado por Id")
    @Test
    void testParaObtenerUnEmpleadoPorId(){
        //given
        when(empleadoRepository.findById(anyLong())).thenReturn(Optional.of(empleado));

        //when
        Optional<Empleado>response = empleadoService.getEmpleadoById(1L);

        //then
        assertNotNull(response);
    }

    @DisplayName("Test para actualizar empleado")
    @Test
    void testParaActualizarEmpleado(){
        //given
        when(empleadoRepository.save(empleado)).thenReturn(empleado);
        empleado.setEmail("char2@gmail.com");
        empleado.setName("Cristian Raúl");

        //when
        Empleado response = empleadoService.updateEmpleado(empleado);

        //then
        assertEquals(response.getEmail(),"char2@gmail.com");
        assertEquals(response.getName(),"Cristian Raúl");
    }
    @DisplayName("Test para eliminar un empleado")
    @Test
    void testParaEliminarUnEmpleado(){
        //given
        Long empleadoId = 1L;
        when(empleadoRepository.findById(anyLong())).thenReturn(Optional.of(empleado));

        //when
        empleadoService.deleteEmpleado(empleadoId);

        //then
        verify(empleadoRepository, times(1)).deleteById(empleadoId);
    }
}