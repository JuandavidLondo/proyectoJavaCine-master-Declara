package Clases;
import java.util.Arrays;
public class Sala{
    public int filas;
    public  int columnas;
    public  int numeroSala;
    public String[][] asientos;
    public final Pelicula pelicula;
    public Sala(int filas, int columnas, int numeroSala, Pelicula pelicula) {
        this.filas = filas;
        this.columnas = columnas;
        this.numeroSala = numeroSala;
        this.pelicula = pelicula;
        this.asientos = new String[filas +1][columnas +1];
    }
    public  void llenarSala(){
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[0].length; j++) {
                if (i==0 && j==0) asientos[0][0] = " ";
                else if (i==0) asientos[i][j] = String.valueOf(j);
                else if (j==0) asientos[i][j] = String.valueOf(i);
                else asientos[i][j] = "D";
            }
        }
    }
    public void imprimirSala() {
        System.out.println("Sala: " + numeroSala);
        System.out.println(Arrays.deepToString(asientos).replace("], ", "]\n"));
    }
    public boolean verificarAsiento(int fila, int columna) {
        boolean asientoNoDisponible = fila > filas || columna > columnas || fila == 0 || columna == 0 || asientos[fila][columna].equals("O");
        if (asientoNoDisponible) {
            System.out.println(asientos[fila][columna].equals("O") ? "Este asiento ya está ocupado" : "Entrada incorrecta");
            return false;
        } else {
            asientos[fila][columna] = "O";
            return true;
        }
    }
}