import java.io.IOException;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.GestionProduitImplJDBC;
import dao.IGestionProduit;
import entity.Produit;

@WebServlet("/index.php")
public class FirstServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    IGestionProduit gestion;

    public void init(ServletConfig config) throws ServletException {
        gestion = new GestionProduitImplJDBC();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mc = request.getParameter("mc"); // Get search query
        List<Produit> liste;

        if (mc != null && !mc.isEmpty()) {
            liste = gestion.getProductsByMc(mc); // Search products by keyword
        } else {
            liste = gestion.getAllProducts(); // Fetch all products
        }

        request.setAttribute("products", liste);
        request.getRequestDispatcher("acceuil.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
