import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.GestionProduitJPA; // Use JPA DAO
import dao.IGestionProduit;   // Use the interface
import entity.Produit;

@WebServlet("/index.php")
public class FirstServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private IGestionProduit gestion;  // Declare the reference to the interface

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        gestion = new GestionProduitJPA();  // Instantiate the implementing class
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mc = request.getParameter("mc"); // Get search query
        List<Produit> liste;

        if (mc != null && !mc.isEmpty()) {
            liste = gestion.getProductsByMc(mc); // Search products by keyword
        } else {
            liste = gestion.getAllProducts(); // Fetch all products
        }

        request.setAttribute("products", liste);
        request.getRequestDispatcher("acceuil2.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // Handle POST requests the same as GET
    }
}
