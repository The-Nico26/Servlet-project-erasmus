package Project.Filter;

import Project.Model.Merchant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/shop")
public class ShopFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        Merchant m = Merchant.getMerchantByName(servletRequest.getParameter("name"), false);
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if(m == null)
            response.sendRedirect("404.jsp");
        else
            servletRequest.getRequestDispatcher("/shop").forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
