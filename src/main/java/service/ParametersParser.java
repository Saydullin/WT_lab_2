package service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.net.URLDecoder;

public class ParametersParser {
    public static HashMap<String, String> parse(String str) throws UnsupportedEncodingException {
        HashMap<String, String> parameters = new HashMap<>();
        if (str != null) {
            str = URLDecoder.decode(str, StandardCharsets.UTF_8.name());
            //str = str.replaceAll("%40", "@");
            String[] keyValuePairs = str.split("&");
            for (String pair : keyValuePairs) {
                String[] entry = pair.split("=");
                parameters.put(entry[0], entry[1]);
            }
        }
        return parameters;
    }

}
