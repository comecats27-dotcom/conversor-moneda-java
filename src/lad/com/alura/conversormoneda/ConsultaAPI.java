//package lad.com.alura.conversormoneda;
//
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//public class ConsultaAPI {
//
//    public static double obtenerTasa(String monedaBase, String monedaDestino) throws IOException, InterruptedException {
//
//        String API_KEY = System.getenv("EXCHANGE_API_KEY");
//        String urlFinal = "https://v6.exchangerate-api.com/v6/" + API_KEY +
//                "/pair/" + monedaBase + "/" + monedaDestino;
//
//        HttpClient cliente = HttpClient.newHttpClient();
//
//        HttpRequest solicitud = HttpRequest.newBuilder()
//                .uri(URI.create(urlFinal))
//                .GET()
//                .build();
//
//        HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
//
//        JsonElement elemento = JsonParser.parseString(respuesta.body());
//        JsonObject objectRoot = elemento.getAsJsonObject();
//
//        double tasa = objectRoot.get("conversion_rate").getAsDouble();
//
//        return tasa;
//    }
//}

package lad.com.alura.conversormoneda;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.ConnectException;
import java.net.http.HttpTimeoutException;

public class ConsultaAPI {

    public static double obtenerTasa(String monedaBase, String monedaDestino) throws IOException, InterruptedException {

        String API_KEY = System.getenv("EXCHANGE_API_KEY");

        // Verificar que la API key existe
        if (API_KEY == null || API_KEY.isEmpty()) {
            throw new IOException("ERROR: No se encontró la clave de API. Debes configurar la variable de entorno EXCHANGE_API_KEY");
        }

        String urlFinal = "https://v6.exchangerate-api.com/v6/" + API_KEY +
                "/pair/" + monedaBase + "/" + monedaDestino;

        HttpClient cliente = HttpClient.newHttpClient();

        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(URI.create(urlFinal))
                .GET()
                .build();

        try {
            HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de respuesta HTTP
            if (respuesta.statusCode() != 200) {
                throw new IOException("Error en la API. Código: " + respuesta.statusCode());
            }

            JsonElement elemento = JsonParser.parseString(respuesta.body());
            JsonObject objectRoot = elemento.getAsJsonObject();

            // Verificar si la respuesta contiene un mensaje de error
            if (objectRoot.has("error-type")) {
                String errorType = objectRoot.get("error-type").getAsString();
                throw new IOException("Error de API: " + errorType);
            }

            // Verificar que existe el campo conversion_rate
            if (!objectRoot.has("conversion_rate")) {
                throw new IOException("Respuesta de API no contiene la tasa de conversión");
            }

            double tasa = objectRoot.get("conversion_rate").getAsDouble();
            return tasa;

        } catch (ConnectException e) {
            throw new IOException("No se pudo conectar con la API. Verifica tu conexión a internet.");
        } catch (HttpTimeoutException e) {
            throw new IOException("Tiempo de espera agotado. La API no respondió.");
        } catch (JsonSyntaxException e) {
            throw new IOException("Error al procesar la respuesta JSON de la API.");
        } catch (InterruptedException e) {
            throw new InterruptedException("La operación fue interrumpida.");
        }
    }
}