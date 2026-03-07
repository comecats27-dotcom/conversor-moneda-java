package lad.com.alura.conversormoneda;
import java.util.ArrayList;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Historial {

    private static ArrayList<String> historial = new ArrayList<>();

    public static void agregarRegistro(double monto, String base, double resultado, String destino) {

        LocalTime ahora = LocalTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");

        String registro = "[" + ahora.format(formato) + "] "
                + monto + " " + base
                + " → " + resultado + " " + destino;

        historial.add(registro);
    }

    public static void mostrarHistorial() {

        System.out.println();
        System.out.println("===== HISTORIAL DE CONVERSIONES =====");

        if(historial.isEmpty()){
            System.out.println("No hay conversiones registradas.");
        } else {
            for(String registro : historial){
                System.out.println(registro);
            }
        }

        System.out.println();
    }
}