<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entity.Produit" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Form</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            margin-top: 2rem;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-4">
            <% if (request.getAttribute("product") != null) { %>
                Update Product
            <% } else { %>
                Add New Product
            <% } %>
        </h1>

        <% Produit p = (Produit) request.getAttribute("product"); %>
        <form action="save" method="post">
            <% if (p != null) { %>
                <input type="hidden" name="id" value="<%= p.getId() %>">
            <% } %>

            <div class="form-group">
                <label for="nom">Name:</label>
                <input type="text" id="nom" name="nom" class="form-control" value="<%= (p != null) ? p.getNom() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="prix">Price:</label>
                <input type="number" id="prix" name="prix" step="0.01" class="form-control" value="<%= (p != null) ? p.getPrix() : "" %>" required>
            </div>

            <div class="form-group">
                <label for="quantite">Quantity:</label>
                <input type="number" id="quantite" name="quantite" min="0" class="form-control" value="<%= (p != null) ? p.getQuantite() : "" %>" required>
            </div>

            <button type="submit" class="btn btn-primary">
                <% if (p != null) { %> Update Product <% } else { %> Add Product <% } %>
            </button>
        </form>
    </div>
</body>
</html>
