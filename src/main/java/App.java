import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class App {
    private final String GET_URL = "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";

    public void get(CloseableHttpClient httpClient) throws IOException {
        HttpGet request = new HttpGet(GET_URL);
        CloseableHttpResponse response = httpClient.execute(request);
        ObjectMapper objectMapper = new ObjectMapper();
        List<Cat> catList = objectMapper.readValue(response.getEntity().getContent(), new TypeReference<List<Cat>>() {
        });
        catList.stream()
                .filter(cat -> cat.getUpvotes() != null && cat.getUpvotes() > 0)
                .forEach(System.out::println);
    }

    public static void main(String[] args) throws IOException {
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
