package zhangc.gateway.demo.router;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class FairRouter implements Router{

    private AtomicInteger i = new AtomicInteger(0);

    @Override
    public String route(List<String> urls) {
        return urls.get(i.getAndIncrement() % urls.size());
    }
}
