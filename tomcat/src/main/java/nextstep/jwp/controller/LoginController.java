package nextstep.jwp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import nextstep.jwp.FileIOUtils;
import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.model.User;
import org.apache.catalina.session.Session;
import org.apache.coyote.http11.HttpServlet;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.StatusCode;

public class LoginController extends HttpServlet {

    public static final String JSESSIONID = "JSESSIONID";
    private static final String PREFIX = "static";
    private static final String SUFFIX = ".html";

    @Override
    public void doGet(final HttpRequest req, final HttpResponse resp) throws IOException {
        if (req.getSession()==null || req.getSession().containskey("user")) {
            resp.sendRedirect("/index.html");
            return;
        }

        resp.setHttpResponseStartLine(StatusCode.OK);
        Path path = FileIOUtils.getPath(PREFIX + req.getPath() + SUFFIX);
        resp.setResponseBody(Files.readAllBytes(path));
        resp.addHeader("Content-Type", Files.probeContentType(path) + "; charset=utf-8");
    }

    @Override
    public void doPost(final HttpRequest req, final HttpResponse resp) {
        RequestParam requestParam = RequestParam.of(req.getRequestBody());

        Optional<User> findAccount = InMemoryUserRepository.findByAccount(requestParam.get("account"));

        if (findAccount.isPresent()) {
            User user = findAccount.get();
            if (user.checkPassword(requestParam.get("password"))) {
                Session session = req.getSession();
                session.setAttribute("user", user);
                resp.addCookie(JSESSIONID, req.getSession().getId());
                resp.sendRedirect("/index.html");
                return;
            }
        }
        resp.sendRedirect("/401.html");
    }
}
