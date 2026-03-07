package lad.com.alura.conversormoneda;

import java.text.DecimalFormat;
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Conversor {

    static Scanner lectura = new Scanner(System.in);

    public static void eleccionUserMenu() throws IOException, InterruptedException {
        int opcion = 0;

        while (opcion != 8) {
            try {
                System.out.println("****************************************");
                System.out.println("Sea bienvenido/a al Conversor de Moneda");
                System.out.println();

                System.out.println("1) Dólar → Peso argentino");
                System.out.println("2) Peso argentino → Dólar");
                System.out.println("3) Dólar → Real brasileño");
                System.out.println("4) Real brasileño → Dólar");
                System.out.println("5) Dólar → Peso chileno");
                System.out.println("6) Peso chileno → Dólar");
                System.out.println("7) Ver historial");
                System.out.println("8) Salir");

                System.out.println("Elija una opción válida:");

                // Leer como String y convertir a int para evitar errores
                String entrada = lectura.next();
                opcion = Integer.parseInt(entrada);

                switch (opcion) {
                    case 1:
                        convertir("USD", "ARS");
                        break;
                    case 2:
                        convertir("ARS", "USD");
                        break;
                    case 3:
                        convertir("USD", "BRL");
                        break;
                    case 4:
                        convertir("BRL", "USD");
                        break;
                    case 5:
                        convertir("USD", "CLP");
                        break;
                    case 6:
                        convertir("CLP", "USD");
                        break;
                    case 7:
                        Historial.mostrarHistorial();
                        break;
                    case 8:
                        System.out.println("Gracias por usar el conversor.");
                        break;
                    default:
                        System.out.println("Opción inválida. Por favor elija un número del 1 al 8.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido (del 1 al 8)");
                lectura.nextLine(); // Limpiar el buffer
            }
        }
    }

    public static void convertir(String monedaBase, String monedaDestino) throws IOException, InterruptedException {
        double monto = 0;
        boolean entradaValida = false;

        while (!entradaValida) {
            try {
                System.out.println("Ingrese el monto a convertir:");
                String entrada = lectura.next();
                monto = Double.parseDouble(entrada);
                entradaValida = true;
            } catch (NumberFormatException e) {
                System.out.println("Error: Debe ingresar un número válido (ej: 100.50 o 100,50)");
            }
        }

        try {
            double tasa = ConsultaAPI.obtenerTasa(monedaBase, monedaDestino);
            double resultado = monto * tasa;
            DecimalFormat formatoDecimal = new DecimalFormat("#.##");

            System.out.println("El valor convertido es: " + formatoDecimal.format(resultado) + " " + monedaDestino);
            Historial.agregarRegistro(monto, monedaBase, resultado, monedaDestino);

        } catch (IOException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        } catch (InterruptedException e) {
            System.out.println("La operación fue interrumpida.");
        } catch (Exception e) {
            System.out.println("Error inesperado: " + e.getMessage());
        }
    }

}