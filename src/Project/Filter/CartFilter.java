package Project.Filter;

import Project.Model.Cart;
import Project.Model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebListener("/cart")
public class CartFilter implements Filter {
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
            response.sendRedirect("401.jsp");
        else {
            String id = request.getParameter("id");
            if(id != null){
                User user = User.getId((String) hS.getAttribute("auth"));
                assert user != null;
                for (Cart cart : user.carts){
                    if(cart.getIdString().equals(id)){
                        request.getRequestDispatcher(action).forward(servletRequest, servletResponse);
                        return;
                    }
                    response.sendRedirect("401.jsp");
                }
            }else{
                request.getRequestDispatcher(action).forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
