package org.educa.dao;

import org.educa.entity.ClienteEntity;

public interface ClienteDAO {
    /**
     * Metodo login para verificar el dni y la contraseña introducida
     * @param dni DNI que introduce el usuario
     * @param password PASSWORD que introduce el usuario
     * @return datos del usuario que hace login
     */
    ClienteEntity login(String dni, String password);
}
