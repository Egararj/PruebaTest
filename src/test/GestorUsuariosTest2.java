package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import controller.GestorUsuarios;
import excepciones.DniException;
import modelo.User;

class GestorUsuariosTest2 {
	
	private GestorUsuarios gestorUsuarios;
	private User usuario;
	
	// 1. assertEquals comprobar que al agregar usuarios se incrementa la lista
	
	@BeforeEach
	void instancia () throws DniException {
		gestorUsuarios = new GestorUsuarios();

			usuario = new User("Pablo", "Pablo", "pass", "21150568K");
			gestorUsuarios.agregarUsuario(usuario);
			gestorUsuarios.agregarUsuario(new User("Ana", "Ana", "pass", "21150570T"));

	}
	
	@AfterEach
	void desisntancia () {
		gestorUsuarios = null;
	}
	
	@Test
	void testAgregarUsuario () {
		
			int esperado = 2;
			int lista = gestorUsuarios.cantidadUsuarios();
			assertEquals(esperado, lista);
			assertEquals(2,gestorUsuarios.cantidadUsuarios());
			
		
		
	}
	
	// 2. assertTrue/assertFalse Comprobar que existe un usuario y que al eliminarlo ya no existe
	
	@Test
	void testAgregaYEliminarUsuario () {
		
		
		try {
			usuario = new User("Pablo", "Pablo", "pass", "21150568K");
			gestorUsuarios.agregarUsuario(usuario);
			
			assertTrue(gestorUsuarios.existeUsuario("Pablo"));
			gestorUsuarios.eliminarUsuario("21150568K");
			assertFalse(gestorUsuarios.existeUsuario("Pablo"));
				
		} catch (DniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	// 3. comprobar que existe usuario es case_insensitive

	   //utilizar @DisplayName para que en la prueba aparezca "Verificar que existeUsuario() ignora mayúsculas/minúsculas en el username"
	@Test
	@DisplayName ("Verificar que existeUsuario() ignora mayúsculas/minúsculas en el username")
	void testUsuarioCaseInsensitive () {
		
		try {
		User usuario= new User("Pablo", "Pablo", "pass", "21150568K");
			gestorUsuarios.agregarUsuario(usuario);
			assertTrue(gestorUsuarios.existeUsuario("pablo"));
			
			assertAll(
					() -> assertTrue(gestorUsuarios.existeUsuario("PABLO")),
					() -> assertTrue(gestorUsuarios.existeUsuario("pablo")),
					() -> assertTrue(gestorUsuarios.existeUsuario("Pablo"))
					);
		} catch (DniException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	// 4. assertThrows comprobar que se lanza la excepcion con dni invalido y no lanza con dni valido
	
	@Test
	void testValidarDni () {

		assertThrows(DniException.class, () -> new User("Test", "test", "pass", "21150568L"));
		
		assertDoesNotThrow( () -> new User("Test","test", "pass","21150568K"));
		
	}
	
	// 5. assertAll . buscar usuario por dni y comprobar con assertall que devuelve todos sus atributos
	
	@Test
	void testBuscarUsuariosPorDni () {
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

	}
	
	// 7. @EnabledOnOs. eliminar un usuario solo en windows
	
	@Test
	@EnabledOnOs(OS.WINDOWS)
	void eliminarUsuarioWindows () {
		try {
			User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
				gestorUsuarios.agregarUsuario(usuario);
				gestorUsuarios.eliminarUsuario("21150568K");
				
				assertTrue(gestorUsuarios.eliminarUsuario("21150568K"));
				assertEquals(1,gestorUsuarios.cantidadUsuarios());
		
			} catch (DniException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	// 8. @Disabled. test que deberia validar password pero que esta pendiente
	
	@Test
	@Disabled
	void testPassword () {
		try {
			User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
				gestorUsuarios.agregarUsuario(usuario);
				String password = "pass";
				assertEquals(usuario.getPass(), password);
			} catch (DniException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	// 10. @RepeatedTest metodo que se repite 5 veces, por ejemplo agregar usuario

	@RepeatedTest(5)
	@Test
	void testAgregarUsuarioRepetido () {	
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
	}





	// 11. @Nested crea dos metodos , uno que compruebe que la busqueda por dni deberia SerCaseInsensitive

	// y que devuelve un valor no nulo y el segundo un metodo que devuelve null si la busqueda de un dni es inexistente

	// estos metodos se agrupan dentro de nested

	
	@Nested
	class pruebasDniNested {
		
		@Test
		void dniCaseInsensitive () {		
			try {
				User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
				gestorUsuarios.agregarUsuario(usuario);
				String dniLowercase = "21150568k";			
				assertNotNull(gestorUsuarios.buscarPorDni(dniLowercase));
			} catch (DniException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Test
		void dniInexistente () {		
			try {
				User usuario = new User("Pablo", "Pablo", "pass", "21150568K");
				gestorUsuarios.agregarUsuario(usuario);
				assertNull(gestorUsuarios.buscarPorDni("1111A"));
			} catch (DniException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	// 12. Pruebas parametrizadas

	//Crea una prueba parametizada mediante @MethodSource a la que le mandes un dni y el valor esperado true o false

	// y que compruebe que no lanza la excepcion cuando el dni es valido y la lance cuando el dni es erroneo
	
	@ParameterizedTest
	@MethodSource("proveedorDni")
	void pruebaParametrizada (String dni, boolean esperado) {
		if(esperado) {
			assertDoesNotThrow(() -> {User usuario = new User("Pablo","Pablo","pass",dni);});
		}else {
			assertThrows(DniException.class, () -> {User usuario = new User("Pablo","Pablo","pass",dni);});
		}
		
	}
	
	private static Stream<Arguments> proveedorDni (){
		return Stream.of(
				Arguments.of("21150568K", true),
				Arguments.of("123f", false)
				);
	}
}