package main.java;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import java.text.ParseException;
import static org.apache.spark.sql.functions.col;

public class sparkSqlQuerries {

    /** Variable for columns header definition, for better handling */
    private static final String ID = "device-id";
    private static final String TEMPERATURE = "temperature";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String TIME = "timestamp";

    public static void main(String[] args) throws ParseException {

        /** Spark session initialization and dataset import from file */
        Logger.getLogger("org").setLevel(Level.ERROR);
        SparkSession session = SparkSession.builder().appName("HousePriceSolution").master("local[1]").getOrCreate();
        Dataset<Row> ingestedData = session.read().option("header", "true").csv("out/Output.csv");

        /** Columns data type casting to be able of working with the data*/
        Dataset<Row> castedData = ingestedData
                .withColumn(TEMPERATURE, col(TEMPERATURE).cast("long"))
                .withColumn(LATITUDE, col(LATITUDE).cast("double"))
                .withColumn(LONGITUDE, col(LONGITUDE).cast("double"))
                .withColumn(TIME, col(TIME).cast("long"));

        /** QUERY: The maximum temperatures measured for every device */
        castedData.groupBy("device-id").max(TEMPERATURE).orderBy(ID).show();

        /** QUERY: The amount of data points aggregated for every device */
        castedData.groupBy("device-id").count().show();

        /** QUERY: The highest temperature measured on a given day for every device */

        String givenDay = "09/06/2020";  /** Day test example in format MM/dd/YYYY (String) */
        Dataset<Row> castedData2 = castedData.where(col("timestamp").$greater$eq(timestampConverter(givenDay)));
        castedData2.where(col("timestamp").$less$eq(timestampConverter(givenDay) + 86400)) /** The number 86400 corresponds to the number of seconds in a day */
                .groupBy("device-id").max(TEMPERATURE).orderBy(ID).show();
    }

    public static long timestampConverter(String date) throws ParseException {
        long timestamp = new java.text.SimpleDateFormat("MM/dd/yyyy").parse(date).getTime() / 1000;
        return timestamp;
    }

}
