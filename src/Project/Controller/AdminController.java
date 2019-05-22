package Project.Controller;

import Project.Model.Merchant;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin")
public class AdminController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getParameter("action")){
            case "valid_shop":
                validShop(req, resp);
                break;
        }
        resp.sendRedirect("/admin");

    }

    private void validShop(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String id = req.getParameter("id");
        Merchant merchant = Merchant.getId(id);
        if(merchant == null) return;

        String name = req.getParameter("name");
        String weblink = req.getParameter("weblink");
        String address = req.getParameter("address");

        merchant.valid = req.getParameter("valid_value");
        merchant.name = name;
        merchant.address = address;
        merchant.webLink = weblink;

        merchant.update();
    }
}
