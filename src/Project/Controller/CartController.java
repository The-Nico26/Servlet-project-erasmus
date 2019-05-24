package Project.Controller;

import Project.Model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/cart")
public class CartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(action != null){
            switch (action){
                case "checkout":
                    checkout(req);
                    break;
                case "remove":
                    removeElement(req, resp);
                    break;
            }
        }
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }

    private void checkout(HttpServletRequest req) {
        HttpSession httpSession = req.getSession();
        User user = User.getId((String) httpSession.getAttribute("auth"));

        if(user == null)return;
        Cart cart = user.getLastCart();

        if(cart == null || cart.status.equals("1")) return;
        cart.status = "1";
        cart.update();
        httpSession.setAttribute("success", "Checkout succesful");

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch(req.getParameter("action")){
            case "add":
                addElement(req, resp);
                break;
        }
        resp.sendRedirect("/cart");
    }

    private void addElement(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession httpSession = req.getSession();
        User user = User.getId((String) httpSession.getAttribute("auth"));
        Product product = Product.getId(req.getParameter("id_product"));
        if(product == null || user == null)return;

        Cart cart = user.getLastCart();
        if(cart == null || cart.status.equals("1")){
            cart = new Cart();
            cart.status = "0";
            cart.user = user.getIdString();
            cart.insert();
        }

        CartElement cartElement = new CartElement();
        cartElement.cart = cart.getIdString();
        cartElement.merchant = Objects.requireNonNull(Collection.getId(product.collection)).merchant;
        cartElement.product = product.getIdString();
        cartElement.productNumber = Integer.valueOf(req.getParameter("number"));
        cartElement.productPrice = product.price;
        String name = product.name+"<br>";
        String[] options = req.getParameterValues("options[]");
        String[] values = req.getParameterValues("values[]");
        if(options != null){
            for (int in = 0; in < options.length; in++){
                name += options[in]+": "+values[in]+"<br>";
            }
        }
        cartElement.productName = name;
        cartElement.insert();
    }

    private void removeElement(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("id");
        if(id == null) return;
        CartElement cartElement = CartElement.getID(id);
        if(cartElement == null) return;
        cartElement.delete();
    }
}
