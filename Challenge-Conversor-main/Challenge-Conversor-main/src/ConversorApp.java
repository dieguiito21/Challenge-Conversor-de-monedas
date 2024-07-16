import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorApp {

    private static final String API_KEY = "bc63000349693eec042bc826";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";

    public static void main(String[] args) {
        int opcion = 0;

        System.out.println("**********************************************************");
        System.out.println("\nMenu:\n");

        String menu = """
                1) Dólar --> Peso chileno
                2) Peso chileno --> Dólar
                3) Dólar --> Peso argentino
                4) Peso argentino --> Dólar
                5) Dólar --> Real brasileño
                6) Real brasileño --> Dólar  
                7) Salir 
                """;

        System.out.println("**********************************************************\n");

        Scanner teclado = new Scanner(System.in);
        while (opcion != 7){
            // Muestra opciones al usuario
            System.out.println(menu);
            System.out.println("\nElija una opción valida:");
            System.out.println("\n**********************************************************");
            opcion = teclado.nextInt();
            // Agregamos una condicion con un mensaje cuando el usuario elija la opcion 7
            if (opcion == 7){
                System.out.println("Saliendo de la aplicacion...");
                break;
            }

            // Pedimos el valor a convertir
            System.out.println("Ingrese el valor a convertir: ");
            double valor = teclado.nextDouble();

           try{
               switch (opcion){
                   case 1:
                       convertirMoneda("USD", "CLP", valor);
                       break;
                   case 2:
                       convertirMoneda("CLP", "USD", valor);
                       break;
                   case 3:
                       convertirMoneda("USD", "ARS", valor);
                       break;
                   case 4:
                       convertirMoneda("ARS", "USD", valor);
                       break;
                   case 5:
                       convertirMoneda("USD", "BRL", valor);
                       break;
                   case 6:
                       convertirMoneda("BRL", "USD", valor);
                       break;
                   default:
                       System.out.println("Opcion no valida");
               }
           } catch (IOException | InterruptedException e){
               System.out.println("Error al realizar la conversion: " + e.getMessage());
           }
        }
        teclado.close();
    }

    public static void convertirMoneda (String from, String to, double valor) throws IOException, InterruptedException {
        String url = API_URL + from;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        MonedaUtil monedaUtil = new MonedaUtil();
        double tasa = monedaUtil.obtenerTasaConversion(response.body(), to);

        double resultado = valor * tasa;


        System.out.println("El valor" + " " + valor + " " + "(" + from + ")" + " " + "corresponde al valor final de -->" + " " + resultado + " " + "(" + to + ")\n");

    }

}
