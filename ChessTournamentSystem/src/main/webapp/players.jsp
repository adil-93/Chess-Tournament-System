<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Player Master List</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-dark mb-4 shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="ChessServlet?action=listTournaments">♟️ Chess Arena Manager</a>
            <div class="d-flex"><a class="btn btn-outline-light me-2" href="ChessServlet?action=listTournaments">Tournaments</a><a class="btn btn-light" href="ChessServlet?action=listPlayers">Players</a></div>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card p-4 shadow-sm">
                    <h5 class="fw-bold mb-3">Add New Competitor</h5>
                    <form action="ChessServlet?action=addPlayer" method="POST">
                        <div class="mb-3"><label class="form-label">Player Name</label><input type="text" name="name" class="form-control" required></div>
                        <div class="mb-3"><label class="form-label">Club Affiliation</label><input type="text" name="club" class="form-control" required></div>
                        <button type="submit" class="btn btn-primary w-100">Register Player</button>
                    </form>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card p-4 shadow-sm">
                    <h5 class="fw-bold mb-3">Active System Directory</h5>
                    <table class="table table-striped align-middle">
                        <thead><tr><th>ID</th><th>Name</th><th>Club</th><th>Actions</th></tr></thead>
                        <tbody>
                            <% List<Map<String, String>> players = (List<Map<String, String>>) request.getAttribute("players");
                               if(players != null) { for(Map<String, String> p : players) { %>
                                <tr>
                                    <td><%= p.get("id") %></td>
                                    <td><strong><%= p.get("name") %></strong></td>
                                    <td><%= p.get("club") %></td>
                                    <td>
                                        <a href="ChessServlet?action=editPlayerForm&id=<%= p.get("id") %>" class="btn btn-warning btn-sm">Edit</a>
                                        <a href="ChessServlet?action=deletePlayer&id=<%= p.get("id") %>" class="btn btn-danger btn-sm" onclick="return confirm('Remove Player?')">Delete</a>
                                    </td>
                                </tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>