package com.prueba.springback.api;

import com.prueba.springback.model.cliente;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/usuario")
public class ClienteController {

    // Datos quemados (simulando una base de datos)
    private Map<String, cliente> clientes = new HashMap<>();

    
    public ClienteController() {
    
        clientes.put("C23445322", new cliente("Juan", "Carlos", "Saldaña", "Gómez", "3188113915", "Calle 51, No. 45-67", "Bogotá"));
        clientes.put("P98765432", new cliente("María", "", "García", "Martínez", "3214523521", "Avenida 21, No. 78-90", "Medellín"));
    }

    @GetMapping("/cliente")
    public ResponseEntity<?> obtenerCliente(
            @RequestParam String tipoId,
            @RequestParam String numeroId
    ) {
    	
    	try {
    		
    
        if (tipoId == null || numeroId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Los parámetros 'tipo' y 'numero' son obligatorios.");
        }

        if (!tipoId.equals("C") && !tipoId.equals("P")) { 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El tipo de documento debe ser 'C' o 'P'.");
        }

        // Formar el identificador del cliente
        String clienteId = tipoId + numeroId;

        // Buscar al cliente en la "base de datos (quemada)"
        cliente cliente = clientes.get(clienteId);


        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
        } 

        return ResponseEntity.ok("Cliente encontrado: " + cliente);
        
    	} catch (Exception ex) {
            // Capturar cualquier excepción no controladas y manejar el error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor: " + ex.getMessage());
        }
    }
    }
