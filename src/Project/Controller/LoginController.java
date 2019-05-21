package Project.Controller;


import Project.Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        HttpSession hS = req.getSession();

        User user = User.getUserByEmail(email);
        if(user != null){
            if(user.checkPassword(password)) {
                hS.setAttribute("auth", user.getIdString());
                resp.sendRedirect("/account");
                return;
            } else
                hS.setAttribute("error", "Error password");
        }else{
            hS.setAttribute("error", "Error email");
        }
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }
}
