package hello.servlet.servlet.web.frontcontroller.v5;

import hello.servlet.servlet.web.frontcontroller.ModelView;
import hello.servlet.servlet.web.frontcontroller.MyView;
import hello.servlet.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.servlet.web.frontcontroller.v4.controller.MemberFromControllerV4;
import hello.servlet.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerServlet5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private Map<String, Object> handlerMappingMap = new HashMap<>();
    private List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappings();
        initHandlerAdapters();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView modelView = adapter.handle(request, response, handler);
        String viewName = modelView.getViewName();
        MyView myView = viewResolve(viewName);
        myView.render(modelView.getModel(), request, response);
    }

    private void initHandlerMappings() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFromControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.support(handler)) {
                System.out.println("adapter = " + adapter);
                return adapter;
            }
        }
        throw new IllegalArgumentException("Cannot find handler adapter=" + handler);
    }

    private MyView viewResolve(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
