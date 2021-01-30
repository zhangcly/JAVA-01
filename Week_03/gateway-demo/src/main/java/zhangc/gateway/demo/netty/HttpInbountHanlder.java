package zhangc.gateway.demo.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import org.apache.http.util.EntityUtils;
import zhangc.gateway.demo.filter.FilterChain;
import zhangc.gateway.demo.router.FairRouter;
import zhangc.gateway.demo.router.Router;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class HttpInbountHanlder extends ChannelInboundHandlerAdapter {
    private List<String> serviceList = Arrays.asList("http://localhost:8001", "http://localhost:8002");
    private FilterChain filterChain = new FilterChain();
    private Router router = new FairRouter();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest)msg;
            filterChain.doFilter(request, ctx);

            HttpResponse response = doGet(router.route(serviceList));
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.valueOf(response.getStatusLine().getStatusCode()), Unpooled.wrappedBuffer(bytes));
            ctx.write(httpResponse);
        }
    }

    private HttpResponse doGet(String url) throws IOException {

        RequestConfig requestConfig =
                RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).build();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpClient.execute(httpGet);
        return response;
    }
}
