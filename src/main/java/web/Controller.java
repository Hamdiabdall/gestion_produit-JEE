package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GestionProduitJPA;
import dao.IGestionProduit;
import dao.IOrderDao;
import dao.IUserDao;
import dao.OrderDaoJPA;
import dao.UserDaoJPA;
import entity.Cart;
import entity.CartItem;
import entity.Order;
import entity.OrderItem;
import entity.Produit;
import entity.User;

/**
 * Servlet implementation class Controller
 */
@WebServlet(name = "controllerServlet", urlPatterns = {"", "/", "/acceuil", "/search", "/add", "/delete", "/update", 
        "/login", "/logout", "/register", "/cart", "/add-to-cart", "/remove-from-cart", "/update-cart", 
        "/checkout", "/my-orders", "/admin/orders", "/admin/users"})
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private IGestionProduit gestion;
    private IUserDao userDao;
    private IOrderDao orderDao;

    @Override
    public void init() throws ServletException {
        // Initialize the DAO instances
        gestion = new GestionProduitJPA();
        userDao = new UserDaoJPA();
        orderDao = new OrderDaoJPA();
        
        // Create default admin user if it doesn't exist
        User adminUser = userDao.findUserByUsername("admin");
        if (adminUser == null) {
            adminUser = new User("admin", "admin123", "admin@example.com", "ADMIN");
            userDao.addUser(adminUser);
        }
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        System.out.println("Servlet path: " + path);
        
        // Get current user from session
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        // Public routes - accessible without login
        if (path.equals("") || path.equals("/") || path.equals("/acceuil")) {
            List<Produit> liste = gestion.getAllProducts();
            request.setAttribute("products", liste);
            request.getRequestDispatcher("acceuil2.jsp").forward(request, response);
        }
        else if (path.equals("/search")) {
            String mc = request.getParameter("mc");
            List<Produit> produitsByMc = gestion.getProductsByMc(mc);
            request.setAttribute("products", produitsByMc);
            request.getRequestDispatcher("acceuil2.jsp").forward(request, response);
        }
        else if (path.equals("/login")) {
            // Show login form
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        else if (path.equals("/register")) {
            // Show registration form
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
        else if (path.equals("/logout")) {
            // Logout user
            session.invalidate();
            response.sendRedirect("acceuil");
            return;
        }
        
        // Routes requiring user login
        else if (path.equals("/cart")) {
            if (currentUser == null) {
                response.sendRedirect("login");
                return;
            }
            
            // Initialize cart if needed
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
        else if (path.equals("/add-to-cart")) {
            if (currentUser == null) {
                response.sendRedirect("login");
                return;
            }
            
            // Get the product ID and quantity
            int productId = Integer.parseInt(request.getParameter("id"));
            int quantity = 1; // Default quantity
            if (request.getParameter("quantity") != null) {
                quantity = Integer.parseInt(request.getParameter("quantity"));
            }
            
            // Get or create cart
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
            
            // Add product to cart
            Produit produit = gestion.getProduct(productId);
            if (produit != null) {
                cart.addItem(produit, quantity);
            }
            
            response.sendRedirect("cart");
        }
        else if (path.equals("/remove-from-cart")) {
            if (currentUser == null) {
                response.sendRedirect("login");
                return;
            }
            
            // Get the product ID
            int productId = Integer.parseInt(request.getParameter("id"));
            
            // Get cart
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.removeItem(productId);
            }
            
            response.sendRedirect("cart");
        }
        else if (path.equals("/update-cart")) {
            if (currentUser == null) {
                response.sendRedirect("login");
                return;
            }
            
            // Get the product ID and new quantity
            int productId = Integer.parseInt(request.getParameter("id"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            
            // Get cart
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart != null) {
                cart.updateItemQuantity(productId, quantity);
            }
            
            response.sendRedirect("cart");
        }
        else if (path.equals("/checkout")) {
            if (currentUser == null) {
                response.sendRedirect("login");
                return;
            }
            
            // Get cart
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null || cart.getItemCount() == 0) {
                response.sendRedirect("cart");
                return;
            }
            
            // Create new order
            Order order = new Order(currentUser);
            
            // Add items from cart to order
            for (CartItem cartItem : cart.getItems()) {
                Produit produit = gestion.getProduct(cartItem.getProductId());
                if (produit != null) {
                    OrderItem orderItem = OrderItem.fromCartItem(cartItem, produit);
                    order.addItem(orderItem);
                    
                    // Update product quantity
                    produit.setQuantite(produit.getQuantite() - cartItem.getQuantity());
                    gestion.updateProduct(produit);
                }
            }
            
            // Save order
            orderDao.saveOrder(order);
            
            // Clear cart
            cart.clear();
            
            // Redirect to order confirmation
            response.sendRedirect("my-orders");
        }
        else if (path.equals("/my-orders")) {
            if (currentUser == null) {
                response.sendRedirect("login");
                return;
            }
            
            // Get user's orders
            List<Order> orders = orderDao.findOrdersByUser(currentUser);
            request.setAttribute("orders", orders);
            
            request.getRequestDispatcher("my-orders.jsp").forward(request, response);
        }
        
        // Admin routes
        else if (path.equals("/admin/orders")) {
            if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
                response.sendRedirect("../login");
                return;
            }
            
            // Get all orders
            List<Order> orders = orderDao.getAllOrders();
            request.setAttribute("orders", orders);
            
            request.getRequestDispatcher("../admin/orders.jsp").forward(request, response);
        }
        else if (path.equals("/admin/users")) {
            if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
                response.sendRedirect("../login");
                return;
            }
            
            // Get all users
            List<User> users = userDao.getAllUsers();
            request.setAttribute("users", users);
            
            request.getRequestDispatcher("../admin/users.jsp").forward(request, response);
        }
        else if (path.equals("/add") || path.equals("/update") || path.equals("/delete")) {
            // Admin-only product management routes
            if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
                response.sendRedirect("login");
                return;
            }
            
            // Handle the "add product" page (form for adding a new product)
            if (path.equals("/add")) {
                request.setAttribute("nomB", "Ajouter");
                request.getRequestDispatcher("ajout2.jsp").forward(request, response);
            }
            // Handle the delete product functionality
            else if (path.equals("/delete")) {
                int id = Integer.parseInt(request.getParameter("id"));
                gestion.deleteProduct(id);
                response.sendRedirect("acceuil");  // Redirect to the home page after deletion
            }
            // Handle the "update product" page (form for editing an existing product)
            else if (path.equals("/update")) {
                int id = Integer.parseInt(request.getParameter("id"));
                Produit produit = gestion.getProduct(id);
                request.setAttribute("produit", produit);
                request.setAttribute("nomB", "Modifier");
                request.getRequestDispatcher("ajout2.jsp").forward(request, response);
            }
        } else {
            // Handle unknown paths
            response.sendRedirect("acceuil");
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getServletPath();
        
        // Get current user from session
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        
        if (path.equals("/login")) {
            // Handle login form submission
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            
            if (userDao.authenticate(username, password)) {
                User user = userDao.findUserByUsername(username);
                session.setAttribute("user", user);
                
                if ("ADMIN".equals(user.getRole())) {
                    response.sendRedirect("admin/orders");
                } else {
                    response.sendRedirect("acceuil");
                }
            } else {
                request.setAttribute("error", "Invalid username or password");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        }
        else if (path.equals("/register")) {
            // Handle registration form submission
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            
            // Check if username or email already exists
            if (userDao.findUserByUsername(username) != null) {
                request.setAttribute("error", "Username already exists");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            
            if (userDao.findUserByEmail(email) != null) {
                request.setAttribute("error", "Email already exists");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }
            
            // Create new user
            User user = new User(username, password, email, "USER");
            userDao.addUser(user);
            
            // Log in the new user
            session.setAttribute("user", user);
            response.sendRedirect("acceuil");
        }
        else if (path.equals("/add") || path.equals("/update")) {
            // Admin-only product management
            if (currentUser == null || !"ADMIN".equals(currentUser.getRole())) {
                response.sendRedirect("login");
                return;
            }
            
            // Handle add and update product forms
            String nom = request.getParameter("nom");
            String prixParam = request.getParameter("prix");
            String quantiteParam = request.getParameter("quantite");
            String idParam = request.getParameter("id"); // Get product ID

            // Parse price and quantity
            double prix = Double.parseDouble(prixParam);
            int quantite = Integer.parseInt(quantiteParam);
            int id = idParam.isEmpty() ? 0 : Integer.parseInt(idParam); // Set id to 0 for new products

            // Create the product object
            Produit produit = new Produit(nom, quantite, prix);
            produit.setId(id); // Set the ID to the product object

            // Check if it's an update or add
            if (path.equals("/update")) {
                gestion.updateProduct(produit); // Update product in DB
            } else {
                gestion.addProduct(produit); // Add product to DB
            }

            // Redirect to the home page (acceuil) after updating or adding the product
            response.sendRedirect("acceuil");
        }
    }
}
