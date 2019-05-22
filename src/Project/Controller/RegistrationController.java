package Project.Controller;

import Project.Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/registration")
public class RegistrationController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        String password_repeat = req.getParameter("password_repeat");
        String email = req.getParameter("email");
        String error = "";
        HttpSession hS = req.getSession();

        if(password.equals(password_repeat) && !password.equals("")){
            User user = User.getUserByEmail(email);
            if(user == null){
                user = new User();
                user.name = name;
                user.email = email;
                user.power.addAll(Arrays.asList("cart", "info"));
                user.role.add("user");

                user.insert();
                hS.setAttribute("auth", user.getIdString());
                resp.sendRedirect("/account");
                return;
            }else
                error = "Email already exists";
        }else
            error = "Password not equals";

        hS.setAttribute("error", error);
        req.getRequestDispatcher("registration.jsp").forward(req, resp);
    }
}
