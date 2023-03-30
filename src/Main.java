import Clases.Pelicula;
import Clases.Sala;
import Clases.Usuario;
import Clases.Reserva;
import static Clases.Reserva.reservas;
import static Clases.Usuario.usuarios;
import java.util.*;
public class Main {
    final static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        Pelicula pelicula1 = new Pelicula("El gato con botas", 10);
        Pelicula pelicula2 = new Pelicula("Pinochio", 10);
        Sala sala1 = new Sala(4, 4, 1, pelicula1);
        Sala sala2 = new Sala(5, 4, 2, pelicula2);
        sala1.llenarSala();
        sala2.llenarSala();
        String menu = "Bienvenido, eliga una opcion\n1.Reservar silla\n" +
                "2.Mostrar estadisticas\n3.Mostrar reservas\n4.Salir";
        int opcion;
        do {
            System.out.println(menu);
            opcion = scan.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Ingrese su nombre");
                    String nombre = scan.next();
                    System.out.println("Ingrese su edad");
                    int edad = scan.nextInt();
                    System.out.println("Ingrese su género\n1.M: masculino\n2.F: femenino\n3.NB: no binario\n4.Otro");
                    int genero = scan.nextInt();
                    Usuario nuevoUsuario = crearUsuario(nombre, edad, genero);
                    for (Usuario usuario : usuarios) System.out.println(usuario.nombre);
                    System.out.println("Elija la película que desea ver:\n1." + pelicula1.nombre + "\n2." + pelicula2.nombre);
                    int peliOpcion = scan.nextInt();
                    Sala sala = peliOpcion == 1 ? sala1 : sala2;
                    sala.imprimirSala();
                    String asiento = reservaAsiento(sala);
                    Reserva.guardarReserva(sala.numeroSala, peliOpcion == 1 ? pelicula1.precioEntrada : pelicula2.precioEntrada, asiento, nuevoUsuario.nombre, peliOpcion == 1 ? pelicula1.nombre : pelicula2.nombre);
                    System.out.println("Reserva realizada con éxito\n");
                    break;
                case 2:
                    float totalUsuarios = usuarios.size();
                    float totalHombres = (float) usuarios.stream()
                            .filter(usuario -> usuario.genero == Usuario.Genero.MASCULINO)
                            .count();
                    float porcentajeHombres = totalHombres / totalUsuarios * 100;
                    System.out.println("El porcentaje de hombres que han reservado son: " + porcentajeHombres);
                    float totalMujeres = (float) usuarios.stream()
                            .filter(usuario -> usuario.genero == Usuario.Genero.FEMENINO)
                            .count();
                    float porcentajeMujeres = totalMujeres / totalUsuarios * 100;
                    System.out.println("El porcentaje de mujeres que han reservado son: " + porcentajeMujeres);
                    List<String> peliculas = Arrays.asList("El gato con botas", "Pinochio");
                    Map<String, Integer> reservasPorPelicula = new HashMap<>();
                        for (String pelicula : peliculas) {
                            int contReservas = 0;
                            for (Reserva reserva : reservas) {
                                if (reserva.pelicula.equals(pelicula)) {
                                    contReservas++;
                                }
                            }
                            reservasPorPelicula.put(pelicula, contReservas);
                        }
                    String peliculaMasReservada = Collections.max(reservasPorPelicula.entrySet(), Map.Entry.comparingByValue()).getKey();
                    int numReservas = reservasPorPelicula.get(peliculaMasReservada);
                    System.out.println(peliculaMasReservada + " ha sido la película más reservada con " + numReservas + " reservas.");
                    usuarioMasViejo(usuarios);
                    usuarioMasJoven(usuarios);
                    System.out.println("\n");
                    break;
                case 3:
                    Reserva.mostrarReservas();
                    break;
            }
        } while (opcion != 4);
    }
    private static Usuario crearUsuario(String nombre, int edad, int genero) {
        Usuario.Genero generoUsuario = Arrays.stream(Usuario.Genero.values())
                .filter(generoEnum -> generoEnum.ordinal() == genero - 1)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ingrese una opción válida"));
        Usuario nuevoUsuario = new Usuario(nombre, edad, generoUsuario);
        usuarios.add(nuevoUsuario);
        return nuevoUsuario;
    }
    public static String reservaAsiento(Sala sala) {
        System.out.println("Ingrse el numero de la fila: ");
        int fila = scan.nextInt();
        System.out.println("Ingrese el numero e la silla: ");
        int columna = scan.nextInt();
        while (!sala.verificarAsiento(fila, columna)) {
            System.out.println("Ingrese un numero de fila");
            fila = scan.nextInt();
            System.out.println("Ingrese un numero de columna");
            columna = scan.nextInt();
        }
        return fila + "-" + columna;
    }
    public static void usuarioMasViejo(List<Usuario> usuarios) {
        Optional<Usuario> usuarioMasViejo = usuarios.stream()
                .max(Comparator.comparingInt(u -> u.edad));
        usuarioMasViejo.ifPresent(u -> System.out.println("El usuario mas viejo es: " + u.nombre));
    }
    public static void usuarioMasJoven(List<Usuario> usuarios) {
        Optional<Usuario> usuarioMasJoven = usuarios.stream()
                .min(Comparator.comparingInt(u -> u.edad));
        usuarioMasJoven.ifPresent(u -> System.out.println("El usuario mas joven es: " + u.nombre));
    }
}