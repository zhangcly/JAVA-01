package zhangc.gateway.demo.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public interface Filter {

     default void filter(HttpRequest request, ChannelHandlerContext ctx, FilterChain chain){
         doFilter(request, ctx, chain);
         chain.doFilter(request, ctx);
     }

     void doFilter(HttpRequest request, ChannelHandlerContext ctx, FilterChain chain);
}
