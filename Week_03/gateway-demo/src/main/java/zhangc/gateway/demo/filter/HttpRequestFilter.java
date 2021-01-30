package zhangc.gateway.demo.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class HttpRequestFilter implements Filter {
    public void doFilter(HttpRequest request, ChannelHandlerContext ctx, FilterChain chain) {
        request.headers().add("zhangc", "ok");
    }
}
