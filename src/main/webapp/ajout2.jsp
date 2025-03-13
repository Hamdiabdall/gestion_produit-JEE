<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
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
        .form-group {
            margin-bottom: 1rem;
        }
        .error-message {
            color: red;
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

    <div class="container">
        <h1 class="text-center mb-4">Add New Product</h1>

        <!-- Add Product Form -->
        <form action="${produit.id != 0 ? 'add' : 'update'}" method="post">
            <!-- Add this hidden field for the product ID when updating -->
            <input type="hidden" name="id" value="${produit.id != 0 ? produit.id : ''}" />

            <div class="form-group">
                <label for="nom">Product Name</label>
                <input type="text" name="nom" id="nom" value="${produit.nom}" required class="form-control">
            </div>

            <div class="form-group">
                <label for="prix">Price</label>
                <input type="text" name="prix" id="prix" value="${produit.prix}" required class="form-control">
            </div>

            <div class="form-group">
                <label for="quantite">Quantity</label>
                <input type="text" name="quantite" id="quantite" value="${produit.quantite}" required class="form-control">
            </div>

            <!-- Button changes based on context (adding or updating) -->
            <button type="submit" class="btn btn-primary">${nomB}</button>
        </form>
    </div>

    <!-- Bootstrap JS and Popper.js -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
