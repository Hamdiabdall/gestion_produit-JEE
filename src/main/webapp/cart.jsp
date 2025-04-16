<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Shopping Cart - Gestion de Produits</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
</head>
<body>
    <div class="container mt-4">
        <h1>Your Shopping Cart</h1>
        
        <nav aria-label="breadcrumb" class="mt-3">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="acceuil">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">Cart</li>
            </ol>
        </nav>
        
        <c:choose>
            <c:when test="${empty cart.items}">
                <div class="alert alert-info mt-4">
                    <h4>Your cart is empty</h4>
                    <p>Add some products to your cart from our <a href="acceuil" class="alert-link">product catalog</a>.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="card mt-4">
                    <div class="card-header bg-primary text-white">
                        <h5 class="mb-0">Cart Items (${cart.itemCount})</h5>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-striped">
                                <thead>
                                    <tr>
                                        <th>Product</th>
                                        <th>Price</th>
                                        <th>Quantity</th>
                                        <th>Total</th>
                                        <th>Actions</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="item" items="${cart.items}">
                                        <tr>
                                            <td>${item.productName}</td>
                                            <td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="€"/></td>
                                            <td>
                                                <form action="update-cart" method="get" class="row g-1">
                                                    <input type="hidden" name="id" value="${item.productId}">
                                                    <div class="col-6">
                                                        <input type="number" class="form-control form-control-sm" name="quantity" value="${item.quantity}" min="1">
                                                    </div>
                                                    <div class="col-6">
                                                        <button type="submit" class="btn btn-sm btn-secondary">Update</button>
                                                    </div>
                                                </form>
                                            </td>
                                            <td><fmt:formatNumber value="${item.total}" type="currency" currencySymbol="€"/></td>
                                            <td>
                                                <a href="remove-from-cart?id=${item.productId}" class="btn btn-sm btn-danger">
                                                    <i class="bi bi-trash"></i> Remove
                                                </a>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                                <tfoot>
                                    <tr class="table-active">
                                        <td colspan="3" class="text-end"><strong>Cart Total:</strong></td>
                                        <td><fmt:formatNumber value="${cart.total}" type="currency" currencySymbol="€"/></td>
                                        <td></td>
                                    </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <div class="card-footer">
                        <div class="d-flex justify-content-between">
                            <a href="acceuil" class="btn btn-secondary">
                                <i class="bi bi-arrow-left"></i> Continue Shopping
                            </a>
                            <a href="checkout" class="btn btn-success">
                                <i class="bi bi-check-circle"></i> Checkout
                            </a>
                        </div>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 