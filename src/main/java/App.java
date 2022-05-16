import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

public class App {
    private final String GET_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public void get(CloseableHttpClient httpClient) {
        HttpGet request = new HttpGet(GET_URL);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Cat> catList = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {
            });
            catList.stream()
                    .filter(cat -> cat.getUpvotes() != null && cat.getUpvotes() > 0)
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .build())
                .build();
        App app = new App();
        app.get(closeableHttpClient);
    }
}
