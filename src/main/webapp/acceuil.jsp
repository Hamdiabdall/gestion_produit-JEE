<%@page import="entity.Produit"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product List</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1 class="text-center mb-4">Product Management</h1>
        <a href="add" class="btn btn-success mb-3">Add New Product</a>

        <table class="table table-bordered">
            <thead class="bg-primary text-white">
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <% 
                    List<Produit> liste = (List<Produit>) request.getAttribute("products");
                    if (liste != null && !liste.isEmpty()) {
                        for (Produit p : liste) { 
                %>
                <tr>
                    <td><%= p.getId() %></td>
                    <td><%= p.getNom() %></td>
                    <td>$<%= p.getPrix() %></td>
                    <td><%= p.getQuantite() %></td>
                    <td>
                        <a href="edit?id=<%= p.getId() %>" class="btn btn-warning">Edit</a>
                        <a href="delete?id=<%= p.getId() %>" class="btn btn-danger" onclick="return confirm('Are you sure?')">Delete</a>
                    </td>
                </tr>
                <% 
                        }
                    } else {
                %>
                <tr>
                    <td colspan="5" class="text-center text-muted">No products found.</td>
                </tr>
                <% 
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
