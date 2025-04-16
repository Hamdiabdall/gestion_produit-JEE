<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>My Orders - Gestion de Produits</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.3/font/bootstrap-icons.css">
</head>
<body>
    <div class="container mt-4">
        <h1>My Orders</h1>
        
        <nav aria-label="breadcrumb" class="mt-3">
            <ol class="breadcrumb">
                <li class="breadcrumb-item"><a href="acceuil">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">My Orders</li>
            </ol>
        </nav>
        
        <c:choose>
            <c:when test="${empty orders}">
                <div class="alert alert-info mt-4">
                    <h4>You don't have any orders yet</h4>
                    <p>Browse our <a href="acceuil" class="alert-link">product catalog</a> to place an order.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="row mt-4">
                    <c:forEach var="order" items="${orders}">
                        <div class="col-md-12 mb-4">
                            <div class="card">
                                <div class="card-header">
                                    <div class="d-flex justify-content-between align-items-center">
                                        <div>
                                            <h5 class="mb-0">Order #${order.id}</h5>
                                            <small class="text-muted">
                                                <fmt:formatDate value="${order.orderDate}" pattern="dd/MM/yyyy HH:mm" />
                                            </small>
                                        </div>
                                        <div>
                                            <span class="badge bg-primary">${order.status}</span>
                                            <span class="ms-2">
                                                <fmt:formatNumber value="${order.totalAmount}" type="currency" currencySymbol="€"/>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="card-body">
                                    <h5 class="card-title">Order Items</h5>
                                    <div class="table-responsive">
                                        <table class="table table-sm">
                                            <thead>
                                                <tr>
                                                    <th>Product</th>
                                                    <th>Price</th>
                                                    <th>Quantity</th>
                                                    <th>Total</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="item" items="${order.items}">
                                                    <tr>
                                                        <td>${item.product.nom}</td>
                                                        <td><fmt:formatNumber value="${item.price}" type="currency" currencySymbol="€"/></td>
                                                        <td>${item.quantity}</td>
                                                        <td><fmt:formatNumber value="${item.total}" type="currency" currencySymbol="€"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        
        <div class="mt-3">
            <a href="acceuil" class="btn btn-primary">
                <i class="bi bi-arrow-left"></i> Back to Home
            </a>
        </div>
    </div>
    
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html> 