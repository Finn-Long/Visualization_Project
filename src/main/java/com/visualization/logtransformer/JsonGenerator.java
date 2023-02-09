package com.visualization.logtransformer;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Make request to Cyverse api and write all the logs to json file
 */
public class JsonGenerator {
    public static void main( String[] args ) throws IOException, InterruptedException{

        // json file generator function
        //   generate_all_id();
        generate_bash_logs();
        //generate_plugin_logs();
    }

    public static void generate_plugin_logs() throws IOException, InterruptedException{
        String url_str = "https://us-south.functions.appdomain.cloud/api/v1/web/ORG-UNC-dist-seed-james_dev/cyverse/get-cyverse-log";
        String jsonInpuString = "{\"body\":{\"password\":\"password\",\"skip\":0,\"limit\":1000,\"log_type\":\"ChromePlugin\",\"course_id\":\"Cyverse_Cloud_Tutorial\"}}";
        String file_name = "plugin_logs.json";
        generate_logs(url_str, jsonInpuString, file_name);
    }

    public static void generate_bash_logs() throws IOException, InterruptedException{
        String url_str = "https://us-south.functions.appdomain.cloud/api/v1/web/ORG-UNC-dist-seed-james_dev/cyverse/get-cyverse-log";
        String jsonInpuString = "{\"body\":{\"password\":\"password\",\"skip\":0,\"limit\":6000,\"log_type\":\"Bash\",\"course_id\":\"Cyverse-RNA-Tutorial\"}}";
        String file_name = "bash_logs.json";
        generate_logs(url_str, jsonInpuString, file_name);
    }

    public static void generate_all_id() throws IOException, InterruptedException{
        String url_str = "https://us-south.functions.appdomain.cloud/api/v1/web/ORG-UNC-dist-seed-james_dev/cyverse/find-cyverse-log";
        String jsonInpuString = "{\"body\":{\"password\":\"password\",\"find\":\"log_id\",\"by\":\"log_type\",\"value\":\"ChromePlugin\",\"course_id\":\"Cyverse_Cloud_Tutorial\"}}";
        String file_name = "ids.json";
        generate_logs(url_str, jsonInpuString, file_name);
    }

    public static void generate_logs(String url_str, String jsonInputString, String file_name) throws IOException, InterruptedException{
        URL url = new URL (url_str);
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            try {
                FileWriter file = new FileWriter(file_name);
                file.write(response.toString());
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("JSON file created: " + file_name);
        }
    }
}
