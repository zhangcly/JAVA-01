package zhangc.gateway.demo.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FilterChain {
    private List<? extends Filter> filters = Arrays.asList(new HttpRequestFilter());

    Iterator<? extends Filter> iterator = filters.iterator();

    public void doFilter(HttpRequest request, ChannelHandlerContext ctx){
        if (iterator.hasNext()){
            Filter filter = iterator.next();
            filter.filter(request, ctx, this);
        }
    }
}
