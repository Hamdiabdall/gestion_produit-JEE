<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome Icons -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
    <!-- Custom Styles -->
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f8f9fa;
            margin: 0;
            padding: 0;
        }
        .container {
            margin-top: 2rem;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        th, td {
            text-align: center;
            padding: 12px;
            vertical-align: middle;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #e0e0e0;
        }
        .btn-edit {
            background-color: #28a745;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-delete {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 6px 12px;
            border-radius: 5px;
            cursor: pointer;
        }
        .btn-edit:hover, .btn-delete:hover {
            opacity: 0.9;
        }
        .search-form {
            margin-bottom: 20px;
        }
        .search-input {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 16px;
        }
        .product-card {
            transition: transform 0.3s ease;
            height: 100%;
        }
        .product-card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <!-- Navigation bar -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container">
            <a class="navbar-brand" href="acceuil">Product Store</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link active" href="acceuil">Home</a>
                    </li>
                    <c:if test="${user.role == 'ADMIN'}">
                        <li class="nav-item">
                            <a class="nav-link" href="add">Add Product</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="admin/orders">Manage Orders</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="admin/users">Manage Users</a>
                        </li>
                    </c:if>
                </ul>
                <ul class="navbar-nav">
                    <c:choose>
                        <c:when test="${empty user}">
                            <li class="nav-item">
                                <a class="nav-link" href="login">Login</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="register">Register</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <c:if test="${user.role == 'USER'}">
                                <li class="nav-item">
                                    <a class="nav-link" href="cart">
                                        <i class="fas fa-shopping-cart"></i> Cart
                                        <c:if test="${not empty cart and cart.itemCount > 0}">
                                            <span class="badge bg-danger">${cart.itemCount}</span>
                                        </c:if>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="my-orders">My Orders</a>
                                </li>
                            </c:if>
                            <li class="nav-item dropdown">
                                <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                    <i class="fas fa-user"></i> ${user.username}
                                </a>
                                <ul class="dropdown-menu dropdown-menu-end">
                                    <li><a class="dropdown-item" href="logout">Logout</a></li>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
    </nav>

    <div class="container">
        <h1 class="text-center mb-4">Our Products</h1>
        
        <!-- Search Form -->
        <form class="search-form d-flex justify-content-center mb-4" method="get" action="search">
            <div class="input-group" style="max-width: 500px;">
                <input name="mc" type="text" class="form-control" placeholder="Search by name..." value="${mc}">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-search"></i> Search
                </button>
            </div>
        </form>

        <c:choose>
            <c:when test="${empty products}">
                <div class="alert alert-info text-center">
                    <h4>No products found</h4>
                    <p>Try a different search or check back later for new products.</p>
                </div>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${user.role == 'ADMIN'}">
                        <!-- Admin View - Table Layout -->
                        <div class="card shadow-sm">
                            <div class="card-header bg-primary text-white">
                                <h5 class="card-title mb-0">Product Management</h5>
                            </div>
                            <div class="card-body p-0">
                                <table class="table table-striped mb-0">
                                    <thead class="table-dark">
                                        <tr>
                                            <th scope="col">#</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Quantity</th>
                                            <th scope="col">Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${products}" var="p">
                                            <tr>
                                                <td>${p.id}</td>
                                                <td>${p.nom}</td>
                                                <td>€${p.prix}</td>
                                                <td>${p.quantite}</td>
                                                <td>
                                                    <a href="update?id=${p.id}" class="btn btn-sm btn-outline-success me-1">
                                                        <i class="fas fa-edit"></i> Edit
                                                    </a>
                                                    <a href="delete?id=${p.id}" class="btn btn-sm btn-outline-danger" 
                                                       onclick="return confirm('Are you sure you want to delete this product?')">
                                                        <i class="fas fa-trash-alt"></i> Delete
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="card-footer">
                                <a href="add" class="btn btn-success">
                                    <i class="fas fa-plus-circle"></i> Add New Product
                                </a>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- User View - Card Layout -->
                        <div class="row row-cols-1 row-cols-md-3 g-4">
                            <c:forEach items="${products}" var="p">
                                <div class="col">
                                    <div class="card h-100 product-card shadow-sm">
                                        <div class="card-body">
                                            <h5 class="card-title">${p.nom}</h5>
                                            <p class="card-text text-success fw-bold">€${p.prix}</p>
                                            <p class="card-text">
                                                <small class="text-muted">Available: ${p.quantite} units</small>
                                            </p>
                                        </div>
                                        <div class="card-footer bg-transparent border-top-0">
                                            <c:choose>
                                                <c:when test="${p.quantite > 0 && not empty user && user.role == 'USER'}">
                                                    <a href="add-to-cart?id=${p.id}" class="btn btn-primary w-100">
                                                        <i class="fas fa-cart-plus"></i> Add to Cart
                                                    </a>
                                                </c:when>
                                                <c:when test="${p.quantite > 0 && empty user}">
                                                    <a href="login" class="btn btn-outline-primary w-100">
                                                        Login to Purchase
                                                    </a>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-outline-secondary w-100" disabled>
                                                        Out of Stock
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </div>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
