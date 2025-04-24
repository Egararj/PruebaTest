package modelo;

import excepciones.DniException;

public class User {
    private String name;
    private String username;
    private String pass;
    private String dni;

    public User(String name, String username, String pass, String dni) throws DniException {
        if (!validarDNI(dni)) {
            throw new DniException("DNI no válido :"+dni);
        }
        this.name = name;
        this.username = username;
        this.pass = pass;
        this.dni = dni;
    }

    private boolean validarDNI(String dni) {
        if (dni == null || dni.length() != 9) {
            return false;
        }
        try {
        	 int dni_num=Integer.parseInt(dni.substring(0, 8));
        }catch (NumberFormatException e) {
			return false;
		}
       
        String numeros = dni.substring(0, 8);
        char letra = Character.toUpperCase(dni.charAt(8));
        
        try {
            int num = Integer.parseInt(numeros);
            char letraCalculada = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(num % 23);
            return letra == letraCalculada;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Getters y setters
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPass() { return pass; }
    public String getDni() { return dni; }
}