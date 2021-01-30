package zhangc.gateway.demo.router;

import java.util.List;

public interface Router {
    String route(List<String> urls);
}
