package Project.Controller;

import Project.Model.Collection;
import Project.Model.Product;
import Project.Model.TypeProduct;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/product")
public class ProductController extends HttpServlet {

    private String idMerchant = "";
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") == null ? "index" : req.getParameter("action");
        switch (action){
            case "index":
                req.getRequestDispatcher("product.jsp").forward(req, resp);
                break;
            case "edit": case"new":
                req.getRequestDispatcher("product-edit.jsp").forward(req, resp);
                break;
            case "remove":
                removeProduct(req, resp);
                resp.sendRedirect("/shop?id="+idMerchant);
                break;
        }
    }

    private void removeProduct(HttpServletRequest req, HttpServletResponse resp){
        String id = req.getParameter("id");
        Product product = Product.getId(id);
        if(product == null)return;
        idMerchant = Collection.getId(product.collection).merchant;
        product.delete();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String collection = req.getParameter("collection");
        String description = req.getParameter("description");
        String typeproduct = req.getParameter("typeproduct");
        float price = Float.parseFloat(req.getParameter("price"));
        String id = req.getParameter("id");
        String[] options = req.getParameterValues("options[]");
        String[] values = req.getParameterValues("values[]");

        Product product;
        if(id == null)
            product = new Product();
        else
            product = Product.getId(id);

        if(product != null) {
            product.name = name;
            product.collection = collection;
            product.price = price;
            product.description = description;
            product.typeProduct = TypeProduct.getId(typeproduct);
            product.options.clear();
            if(options != null)
                for (int in = 0; in < options.length; in++){
                    product.addOption(options[in], values[in]);
                }

            if (id == null)
                product.insert();
            else
                product.update();
        }else{
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("error", "Product not found");
            req.getRequestDispatcher("product-edit.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/product?id="+product.getIdString());

    }
}
