package main.java;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.Instant;
import com.google.gson.*;
import java.io.Serializable;





public class Simulator implements Serializable{

    String message1;
    String message2;
    String message3;
    Simulator simDevice;
    public Simulator(){};

    /** Object to aggregate an transport the data */
    public Simulator (String message1, String message2, String message3){
        this.message1 = message1;
        this.message2 = message2;
        this.message3 = message3;
    }



    public static void main(String[] args){

    }

    /** Method to generate data variations with thing a given range */

    public static double randomGenerator(double minValue, int range) {
        double rand = (int) (Math.random() * range) + minValue;
        return rand;
    }


    /** Method to simulate messages sent by three devices */

    public String textGenerator(int index) throws FileNotFoundException {
        String filePath="in/devices.json";
        JsonParser parser = new JsonParser();

        Object obj = parser.parse(new FileReader(filePath));
        JsonObject jsonObject = (JsonObject) obj;

        JsonArray jsonFile = jsonObject.get("data").getAsJsonArray();
        JsonObject device = jsonFile.get(index).getAsJsonObject();

        JsonObject location = device.get("location").getAsJsonObject();
        String deviceId = String.valueOf(device.get("deviceId")).substring(1,37);

        int temp = (Integer.parseInt(String.valueOf(device.get("temperature"))));
        int temperature = (int) randomGenerator(temp, 10);

        double latde = Double.parseDouble(String.valueOf(location.get("latitude")));
        double latitude = randomGenerator(latde, 15);

        double londe = Double.parseDouble(String.valueOf(location.get("longitude")));
        double longitude = randomGenerator(londe, 20);

        long timestamp = Instant.now().getEpochSecond();

        /** Structure of the message sent by each device*/
        String message = deviceId + "," + temperature + "," + latitude +","+ longitude +","+ timestamp;
        return message;
    }


    /** Method to aggregate the mesages of three devices so the can be forward to a socket*/

    public Simulator messageAgregator() throws Exception {
        simDevice = new Simulator(textGenerator(0), textGenerator(1), textGenerator(2));
        return simDevice;
    }

}
