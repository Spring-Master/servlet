package hello.servlet.servlet.basic.request;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet(name = "requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printAllParameters(request);
        printParameter(request);
        printMultipleParameterValue(request);
    }

    private void printAllParameters(HttpServletRequest request) {
        System.out.println("---- REQUEST PARAMETERS[START] ----");

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + " = " + request.getParameter(paramName)));

        System.out.println("---- REQUEST PARAMETERS[END] ----");
        System.out.println();
    }

    private void printParameter(HttpServletRequest request) {
        System.out.println("---- REQUEST PARAMETER[START] ----");

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        String nothing = request.getParameter("nothing");
        System.out.println("nothing = " + nothing);

        System.out.println("---- REQUEST PARAMETER[END] ----");
        System.out.println();
    }

    private void printMultipleParameterValue(HttpServletRequest request) {
        System.out.println("---- REQUEST MULTIPLE PARAMETER VALUE[START] ----");

        Arrays.stream(request.getParameterValues("username"))
                .forEach(System.out::println);

        System.out.println("---- REQUEST MULTIPLE PARAMETER VALUE[END] ----");
        System.out.println();
    }
}
