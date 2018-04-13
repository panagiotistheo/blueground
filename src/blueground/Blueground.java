
package blueground;

/**
 *
 * @author Panagiotis Theodoropoulos
 * reference1: https://stackoverflow.com/questions/4308554/simplest-way-to-read-json-from-a-url-in-java
 * reference2: https://mvnrepository.com/artifact/org.json/json/20131018
 * /reference3: //https://stackoverflow.com/questions/9349703/server-returned-http-response-code-400
 */

// JSON to be parsed
//{
//  "response": {
//  "version":"0.1",
//  "termsofService":"http://www.wunderground.com/weather/api/d/terms.html",
//  "features": {
//  "almanac": 1
//  ,
//  "history": 1
//  }
//	}
//		,
//	"history": {
//		"date": {
//		"pretty": "",
//		"year": "",
//		"mon": "",
//		"mday": "",
//		"hour": "",
//		"min": "",
//		"tzname": ""
//		},
//		"utcdate": {
//		"pretty": "",
//		"year": "",
//		"mon": "",
//		"mday": "",
//		"hour": "",
//		"min": "",
//		"tzname": ""
//		},
//		"observations": [
//		],
//		"dailysummary": [
//		]
//	}
//		,
//	"almanac": {
//		"airport_code": "KNYC",
//		"temp_high": {
//		"normal": {
//		"F": "60",
//		"C": "16"
//		}
//		,
//		"record": {
//		"F": "84",
//		"C": "28"
//		},
//		"recordyear": "1955"
//		},
//		"temp_low": {
//		"normal": {
//		"F": "43",
//		"C": "6"
//		}
//		,
//		"record": {
//		"F": "24",
//		"C": "-4"
//		},
//		"recordyear": "1909"
//		}
//	}
//}

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;

public class Blueground {

  private static String readAll(Reader rd) throws IOException {
    
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
    
    InputStream instrm = new URL(url).openStream();
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(instrm, Charset.forName("UTF-8")));
      //BufferedReader reader = new BufferedReader(new InputStreamReader(((HttpURLConnection) (new URL(url)).openConnection()).getInputStream(), Charset.forName("UTF-8")));
      String jsontxt = readAll(reader);
      JSONObject json = new JSONObject(jsontxt);
      return json;
    } finally {
      instrm.close();
    }
  }

  public static void main(String[] args) throws IOException, JSONException {
      // my stuff
//    JSONObject json = readJsonFromUrl("http://api.wunderground.com/api/APIKEY/history_YYYYMMDD/q/NY/New_York.json");
//    JSONObject json = readJsonFromUrl("http://api.wunderground.com/api/61eac75ef57aa52a/almanac/q/CA/San_Francisco.json");
    JSONObject json = readJsonFromUrl("http://api.wunderground.com/api/61eac75ef57aa52a/almanac/history_YYYYMMDD/q/NY/New_York.json");
    System.out.println(json.toString());
//    System.out.println(json.get("almanac")); //works
//    JSONArray jsonArray = json.getJSONArray("almanac"); //not a JSONArray.
//    System.out.println(json.getJSONArray("almanac"));
//    System.out.println(json.getString("airport_code")); //not found
//    System.out.println(json.get("airport_code")); //not found
//    System.out.println(json.get("record")); //not found
//    System.out.println(json.get("recordyear")); //not found

    // final solution
    System.out.println("Max percentage humidity " + "N/A"); //humidity is not available in json
    System.out.println("Max Temp in C " + json.toString().substring(111,113)); //extract information from json manually
    System.out.println("Min Temp in C " + json.toString().substring(201,203)); //extract information from json manually
    System.out.println("Precipitation in mm " + "N/A"); //Precipitation is not available in json
    
    // put the same into an output file
    try (PrintWriter out = new PrintWriter("bluegroundfilename.txt")) {
        out.println("Max percentage humidity " + "N/A"+"\n"
                +"Max Temp in C " +json.toString().substring(111,113) + "\n"
                +"Min Temp in C" + json.toString().substring(201,203) + "\n"
                + "Precipitation in mm " + "N/A");
    }
    
  }
}
