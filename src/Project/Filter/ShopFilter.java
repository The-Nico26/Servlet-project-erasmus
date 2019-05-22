package Project.Filter;

import Project.Model.Collection;
import Project.Model.Merchant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/shop")
public class ShopFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String action = request.getRequestURI();

        if(servletRequest.getParameter("collection") != null){
            Collection c = Collection.getId(servletRequest.getParameter("collection"));
            if(c == null)
                response.sendRedirect("404.jsp");
            else
                servletRequest.getRequestDispatcher(action).forward(servletRequest, servletResponse);
            return;
        }

        Merchant m = Merchant.getId(servletRequest.getParameter("id"));
        if(m == null)
            response.sendRedirect("404.jsp");
        else
            servletRequest.getRequestDispatcher(action).forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
