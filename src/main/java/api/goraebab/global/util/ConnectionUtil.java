package api.goraebab.global.util;

import java.util.Objects;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ConnectionUtil {

  private static final String PING_URL = "http://%s:%d/_ping";
  private static final String OK_RESPONSE_BODY = "OK";


  public static boolean testPing(String host, int port) {
    String requestUrl = String.format(PING_URL, host, port);
    try {
      RestTemplate restTemplate = new RestTemplate();
      String response = restTemplate.getForObject(requestUrl, String.class);
      return Objects.equals(response, OK_RESPONSE_BODY);
    } catch (RestClientException e) {
      return false;
    }
  }

}
