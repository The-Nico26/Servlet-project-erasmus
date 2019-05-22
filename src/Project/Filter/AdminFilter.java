package Project.Filter;

import Project.Model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/admin")
public class AdminFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String action = request.getRequestURI();
        HttpSession hS = request.getSession();

        User user = User.getId((String) hS.getAttribute("auth"));
        if(user != null && user.role.contains("admin"))
            request.getRequestDispatcher(action).forward(servletRequest, servletResponse);
        else
            response.sendRedirect("401.jsp");
    }

    @Override
    public void destroy() {

    }
}
