package com.smartstore.service;

import com.smartstore.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteServiceTest {

    private ClienteService service;

    @BeforeEach
    void setUp() {
        service = new ClienteService();
    }

    private Cliente crearCliente() {
        return new Cliente(
                1,
                "Jonathan",
                "Mendez",
                "123456",
                "3001234567",
                "jonathan@email.com",
                "Ibagué",
                true
        );
    }

    @Test
    void agregarClienteCorrectamente() {

        Cliente cliente = crearCliente();

        service.agregarCliente(cliente);

        assertEquals(1, service.cantidadClientes());
    }

    @Test
    void noDebePermitirIdDuplicado() {

        Cliente c1 = crearCliente();

        Cliente c2 = new Cliente(
                1,
                "Pedro",
                "Lopez",
                "987654",
                "3000000000",
                "pedro@email.com",
                "Bogotá",
                true
        );

        service.agregarCliente(c1);

        assertThrows(IllegalArgumentException.class,
                () -> service.agregarCliente(c2));
    }

    @Test
    void noDebePermitirCedulaDuplicada() {

        Cliente c1 = crearCliente();

        Cliente c2 = new Cliente(
                2,
                "Pedro",
                "Lopez",
                "123456",
                "3000000000",
                "pedro@email.com",
                "Bogotá",
                true
        );

        service.agregarCliente(c1);

        assertThrows(IllegalArgumentException.class,
                () -> service.agregarCliente(c2));
    }

    @Test
    void buscarClientePorId() {

        Cliente cliente = crearCliente();

        service.agregarCliente(cliente);

        Cliente encontrado = service.buscarPorId(1);

        assertNotNull(encontrado);
        assertEquals("Jonathan", encontrado.getNombre());
    }

    @Test
    void buscarClientePorCedula() {

        Cliente cliente = crearCliente();

        service.agregarCliente(cliente);

        Cliente encontrado = service.buscarPorCedula("123456");

        assertNotNull(encontrado);
        assertEquals("Jonathan", encontrado.getNombre());
    }

    @Test
    void actualizarClienteCorrectamente() {

        Cliente cliente = crearCliente();

        service.agregarCliente(cliente);

        Cliente actualizado = new Cliente(
                1,
                "Juan",
                "Mendez",
                "123456",
                "3111111111",
                "juan@email.com",
                "Medellín",
                true
        );

        service.actualizarCliente(actualizado);

        Cliente resultado = service.buscarPorId(1);

        assertEquals("Juan", resultado.getNombre());
        assertEquals("Medellín", resultado.getDireccion());
    }

    @Test
    void eliminarCliente() {

        Cliente cliente = crearCliente();

        service.agregarCliente(cliente);

        assertTrue(service.eliminarCliente(1));

        assertEquals(0, service.cantidadClientes());
    }

    @Test
    void listarClientes() {

        service.agregarCliente(crearCliente());

        List<Cliente> clientes = service.listarClientes();

        assertEquals(1, clientes.size());
    }

    @Test
    void obtenerClientesActivos() {

        service.agregarCliente(crearCliente());

        Cliente inactivo = new Cliente(
                2,
                "Pedro",
                "Lopez",
                "654321",
                "3000000000",
                "pedro@email.com",
                "Bogotá",
                false
        );

        service.agregarCliente(inactivo);

        List<Cliente> activos = service.obtenerClientesActivos();

        assertEquals(1, activos.size());
    }

    @Test
    void obtenerClientesInactivos() {

        service.agregarCliente(crearCliente());

        Cliente inactivo = new Cliente(
                2,
                "Pedro",
                "Lopez",
                "654321",
                "3000000000",
                "pedro@email.com",
                "Bogotá",
                false
        );

        service.agregarCliente(inactivo);

        List<Cliente> inactivos = service.obtenerClientesInactivos();

        assertEquals(1, inactivos.size());
    }

}