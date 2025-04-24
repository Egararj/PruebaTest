package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import controller.GestorUsuarios;
import excepciones.DniException;
import modelo.User;

class GestorUsuariosTest2 {

	//GestorUsuarios gestorUsuarios = new GestorUsuarios();
	
	// 1. assertEquals comprobar que al agregar usuarios se incrementa la lista
	
	@Test
	void testAgregarUsuario () {
		
		GestorUsuarios gestorUsuarios = new GestorUsuarios();
		
		try {
			User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
			gestorUsuarios.agregarUsuario(usuario);
			int esperado = 1;
			int lista = gestorUsuarios.cantidadUsuarios();
			assertEquals(esperado, lista);
			
		} catch (DniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		gestorUsuarios = null;
	}
	
	// 2. assertTrue/assertFalse Comprobar que existe un usuario y que al eliminarlo ya no existe
	
	@Test
	void testAgregaYEliminarUsuario () {
		
		GestorUsuarios gestorUsuarios = new GestorUsuarios();
		
		try {
			User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
			gestorUsuarios.agregarUsuario(usuario);
			
			assertTrue(gestorUsuarios.existeUsuario("Pablo"));
			gestorUsuarios.eliminarUsuario("21150568K");
			assertFalse(gestorUsuarios.existeUsuario("Pablo"));
				
		} catch (DniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gestorUsuarios = null;
	}
	
	// 3. comprobar que existe usuario es case_insensitive

	   //utilizar @DisplayName para que en la prueba aparezca "Verificar que existeUsuario() ignora mayúsculas/minúsculas en el username"
	@Test
	@DisplayName ("Verificar que existeUsuario() ignora mayúsculas/minúsculas en el username")
	void testUsuarioCaseInsensitive () {
		
		GestorUsuarios gestorUsuarios = new GestorUsuarios();
		try {
		User usuario= new User("Pablo", "Pablo", "pass", "21150568K");
			gestorUsuarios.agregarUsuario(usuario);
			assertTrue(gestorUsuarios.existeUsuario("pablo"));
	
		} catch (DniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		gestorUsuarios = null;
	}
	
	// 4. assertThrows comprobar que se lanza la excepcion con dni invalido y no lanza con dni valido
	
	@Test
	void testValidarDni () {
		GestorUsuarios gestorUsuarios = new GestorUsuarios();

		assertThrows(DniException.class, () -> new User("Test", "test", "pass", "21150568L"));
		
		gestorUsuarios = null;
	}
	
	// 5. assertAll . buscar usuario por dni y comprobar con assertall que devuelve todos sus atributos
	
	@Test
	void testBuscarUsuariosPorDni () {
		GestorUsuarios gestorUsuarios = new GestorUsuarios();
		try {
			User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
				gestorUsuarios.agregarUsuario(usuario);
				
				User userDni = gestorUsuarios.buscarPorDni("21150568K");
				assertAll(
						() -> {assertEquals(userDni.getName(), "Pablo");},
						() -> {assertEquals(userDni.getUsername(), "Pablo");},
						() -> {assertEquals(userDni.getPass(), "pass");},
						() -> {assertEquals(userDni.getDni(), "21150568K");}
						);
		
			} catch (DniException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		gestorUsuarios = null;

	}
	
	// 7. @EnabledOnOs. eliminar un usuario solo en windows
	
	@Test
	@EnabledOnOs(OS.WINDOWS)
	void eliminarUsuarioWindows () {
		GestorUsuarios gestorUsuarios = new GestorUsuarios();
		try {
			User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
				gestorUsuarios.agregarUsuario(usuario);
				gestorUsuarios.eliminarUsuario("21150568K");
		
			} catch (DniException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		
		gestorUsuarios = null;
	}
	
	// 8. @Disabled. test que deberia validar passwor pero que esta pendiente
	
	
}
