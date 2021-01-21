package zhangc.week02;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

/**
 * 使用HttpClient发送请求
 */
public class MakeRequestByHttpClient {
    public static void main(String[] args) throws IOException {
        RequestConfig requestConfig =
                RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).build();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet("http://localhost:8001");
        HttpResponse response = httpClient.execute(httpGet);

        System.out.println("headers:");
        Arrays.stream(response.getAllHeaders()).forEach(System.out::println);

        HttpEntity entity = response.getEntity();
        byte[] bytes = new byte[1024];
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        try {
            inputStream = entity.getContent();
            while (inputStream.read(bytes) > 0){
                stringBuffer.append(new String(bytes, "utf8"));
            }
            System.out.println("body:");
            System.out.println(stringBuffer.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
