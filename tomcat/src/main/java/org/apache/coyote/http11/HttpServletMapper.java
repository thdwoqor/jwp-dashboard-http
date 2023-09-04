package org.apache.coyote.http11;

import java.util.Map;
import nextstep.jwp.controller.LoginController;
import nextstep.jwp.controller.RegisterController;
import nextstep.jwp.controller.ResourceController;

public class HttpServletMapper {

    private static final Map<String, HttpServlet> servletMap = Map.of(
            "/login", new LoginController(),
            "/register", new RegisterController()
    );
    private static final HttpServlet DEFAULT_SERVLET = new ResourceController();

    public HttpServlet get(final String path) {
        return servletMap.getOrDefault(path, DEFAULT_SERVLET);
    }
}
