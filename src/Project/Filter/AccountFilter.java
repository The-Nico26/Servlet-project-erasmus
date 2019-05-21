package Project.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AccountFilter", urlPatterns = {"/account", "/cart"})
public class AccountFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String action = request.getRequestURI();
        HttpSession hS = request.getSession();

        if(hS.getAttribute("auth") == null)
            response.sendRedirect("/login");
        else
            servletRequest.getRequestDispatcher(action).forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
