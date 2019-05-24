package Project.Filter;

import Project.Model.Product;
import Project.Model.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/product")
public class ProductFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String action = request.getRequestURI();
        Product product = Product.getId(request.getParameter("id"));

        String param = request.getParameter("action") == null ? "index" : request.getParameter("action");

        if(product == null && !param.equals("new")){
            response.sendRedirect("404.jsp");
            return;
        }
        if(!param.equals("index")){
            User user = User.getId((String) request.getSession().getAttribute("auth"));
            if(user == null){
                response.sendRedirect("401.jsp");
                return;
            }
            if(param.equals("edit")) {
                if (user.merchant == null || user.merchant.haveCollection(product.collection) == null) {
                    response.sendRedirect("404.jsp");
                    return;
                }
            }
        }

        servletRequest.getRequestDispatcher(action).forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
