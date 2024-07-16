import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class MonedaUtil {

    public double obtenerTasaConversion(String jsonResponse, String toCurrency){
        JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();

        JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");
        return conversionRates.get(toCurrency).getAsDouble();
    }

}
