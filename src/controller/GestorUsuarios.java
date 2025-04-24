package controller;

import java.util.HashSet;
import java.util.Set;

import excepciones.DniException;
import modelo.User;

public class GestorUsuarios {
    private Set<User> usuarios;

    public GestorUsuarios() {
        this.usuarios = new HashSet<>();
    }

    public void agregarUsuario(User usuario) throws DniException {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        if (buscarPorDni(usuario.getDni()) != null) {
            throw new DniException("Ya existe un usuario con este DNI");
        }
        usuarios.add(usuario);
    }

    public boolean eliminarUsuario(String dni) {
        User usuario = buscarPorDni(dni);
        if (usuario != null) {
            return usuarios.remove(usuario);
        }
        return false;
    }

    public User buscarPorDni(String dni) {
        return usuarios.stream()
                .filter(u -> u.getDni().equalsIgnoreCase(dni))
                .findFirst()
                .orElse(null);
    }

    public boolean existeUsuario(String username) {
        return usuarios.stream()
                .anyMatch(u -> u.getUsername().equalsIgnoreCase(username));
    }

    public int cantidadUsuarios() {
        return usuarios.size();
    }

	
}