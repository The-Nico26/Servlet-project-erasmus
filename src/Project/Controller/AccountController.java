package Project.Controller;

import Project.Model.Merchant;
import Project.Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/account")
public class AccountController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (action){
            case "account":
                updateAccount(req, resp);
                break;
            case "merchant":
                createMerchant(req, resp);
                break;
        }
    }

    private void createMerchant(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String name = req.getParameter("name");
        String address = req.getParameter("address");
        String weblink = req.getParameter("weblink");
        HttpSession httpSession = req.getSession();
        if(Merchant.getMerchantByName(name, true) == null) {
            Merchant merchant = new Merchant();
            User user = User.getId((String) httpSession.getAttribute("auth"));
            if (user.merchant != null) {
                merchant = user.merchant;
            } else {
                merchant.valid = "1";
                merchant.name = name;
            }
            merchant.address = address;
            merchant.webLink = weblink;

            if (user.merchant != null)
                merchant.update();
            else {
                merchant.insert();
                user.merchant = merchant;
                user.update();
            }
        }else{
            httpSession.setAttribute("error_shop", "Shop already exist");
        }
        httpSession.setAttribute("screen", "shop");
        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }

    private void updateAccount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String password_repeat = req.getParameter("password_repeat");
        String address = req.getParameter("address");
        HttpSession httpSession = req.getSession();

        if(password_repeat.equals("") || password.equals(password_repeat)) {
            User user = User.getId((String) httpSession.getAttribute("auth"));

            User userCheckMail = User.getUserByEmail(email);
            if (userCheckMail == null || user.getIdString().equals(userCheckMail.getIdString())) {
                user.name = name;
                user.email = email;
                user.address = address;
                if(!password_repeat.equals(""))
                    user.setPassword(password_repeat);

                user.update();
            } else
                httpSession.setAttribute("error", "Problem with email");
        }else
            httpSession.setAttribute("error", "Password not equals");

        req.getRequestDispatcher("account.jsp").forward(req, resp);
    }
}
