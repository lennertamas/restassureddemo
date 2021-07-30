
import com.google.gson.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.engine.Constants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class ApiTest {

   @Test
   public void TestResponseCode()
   {
      Response response = null;
      String uri = "https://jsonplaceholder.typicode.com/albums";

      response = RestAssured.given()
              .relaxedHTTPSValidation()
              //.header("Content-Type", "application/json")
              .when()
              .get(uri);

      Assertions.assertEquals(200, response.getStatusCode());

   }

   @Test
   public void TestResponseBody1()
   {
      Response response = null;
      String uri = "https://jsonplaceholder.typicode.com/posts";

      response = RestAssured.given()
              .relaxedHTTPSValidation()
              .header("Content-Type", "application/json")
              .when()
              .get(uri);

      String expected = "";
      try {
         File myObj = new File("reference.json");
         Scanner myReader = new Scanner(myObj);
         while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            expected += data;
         }
         myReader.close();
      } catch (FileNotFoundException e) {
         System.out.println("An error occurred.");
         e.printStackTrace();
      }


      JsonArray array = response.getBody().as(JsonArray.class);
      Assertions.assertEquals(expected, array.toString());

   }

   @Test
   public void TestResponseBody2()
   {
      Response response = null;
      String uri = "https://jsonplaceholder.typicode.com/posts";

      response = RestAssured.given()
              .relaxedHTTPSValidation()
              .header("Content-Type", "application/json")
              .when()
              .get(uri);


      JsonArray array = response.getBody().as(JsonArray.class);
      System.out.println(array);
      JsonObject obj = array.get(0).getAsJsonObject();
      Set<Map.Entry<String, JsonElement>> entries = obj.entrySet();//will return members of your object
      for (Map.Entry<String, JsonElement> entry: entries) {
         System.out.println(entry.getKey() + " : " + entry.getValue());
      }
   }
}
