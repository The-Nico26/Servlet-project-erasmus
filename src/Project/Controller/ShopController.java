package Project.Controller;

import Project.Model.Collection;
import Project.Model.Merchant;
import Project.Model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/shop")
public class ShopController extends HttpServlet {
    private User user;
    private String idMerchant;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = (req.getParameter("action") == null ? "":req.getParameter("action"));
        switch (action){
            case "remove_collection":
                removeCollection(req, resp);
                resp.sendRedirect("/shop?id="+idMerchant);
                return;
        }
        req.getRequestDispatcher("shop.jsp").forward(req, resp);
    }

    private void removeCollection(HttpServletRequest req, HttpServletResponse resp) {
        String id = req.getParameter("collection");
        Collection collection = Collection.getId(id);
        if(collection == null)return;
        idMerchant = collection.merchant;
        collection.delete();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        user = User.getId((String) httpSession.getAttribute("auth"));
        if(user == null || user.merchant == null || req.getParameter("action") == null){
            resp.sendRedirect("401.jsp");
            return;
        }
        switch(req.getParameter("action")){
            case "add_categories":
                addCategories(req, resp);
                break;
            case "edit_categories":
                editCategories(req, resp);
                break;
        }
    }

    private void editCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        Collection collection = Collection.getId(req.getParameter("collection"));
        if(collection == null || user.merchant.haveCollection(collection.getIdString()) == null) return;
        collection.name = req.getParameter("name");
        collection.description = req.getParameter("description");
        collection.update();
        resp.sendRedirect("/shop?collection="+collection.getIdString());
    }

    private void addCategories(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Merchant merchant = user.merchant;
        Collection collection = new Collection();
        collection.merchant = merchant.getIdString();
        collection.name = req.getParameter("name");
        collection.description = req.getParameter("description");
        collection.insert();
        resp.sendRedirect("/shop?id="+user.merchant.getIdString());
    }
}
