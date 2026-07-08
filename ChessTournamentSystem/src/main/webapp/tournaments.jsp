<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tournament Arenas</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-dark mb-4 shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="ChessServlet?action=listTournaments">♟️ Chess Arena Manager</a>
            <div class="d-flex"><a class="btn btn-light me-2" href="ChessServlet?action=listTournaments">Tournaments</a><a class="btn btn-outline-light" href="ChessServlet?action=listPlayers">Players</a></div>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="col-md-4">
                <div class="card p-4 shadow-sm">
                    <h5 class="fw-bold mb-3">Schedule New Arena Event</h5>
                    <form action="ChessServlet?action=addTournament" method="POST">
                        <div class="mb-3"><label class="form-label">Event Name</label><input type="text" name="name" class="form-control" required></div>
                        <div class="mb-3"><label class="form-label">Date of Execution</label><input type="date" name="date" class="form-control" required></div>
                        <button type="submit" class="btn btn-success w-100">Build Bracket</button>
                    </form>
                </div>
            </div>
            <div class="col-md-8">
                <div class="card p-4 shadow-sm">
                    <h5 class="fw-bold mb-3">Active Tournament Brackets</h5>
                    <table class="table table-hover align-middle">
                        <thead><tr><th>ID</th><th>Arena Target Title</th><th>Execution Date</th><th>Actions</th></tr></thead>
                        <tbody>
                            <% List<Map<String, String>> tournaments = (List<Map<String, String>>) request.getAttribute("tournaments");
                               if(tournaments != null) { for(Map<String, String> t : tournaments) { %>
                                <tr>
                                    <td><%= t.get("id") %></td>
                                    <td><a href="ChessServlet?action=viewTournament&id=<%= t.get("id") %>" class="fw-bold text-decoration-none"><%= t.get("name") %></a></td>
                                    <td><code><%= t.get("date") %></code></td>
                                    <td>
                                        <a href="ChessServlet?action=editTournamentForm&id=<%= t.get("id") %>" class="btn btn-warning btn-sm">Edit</a>
                                        <a href="ChessServlet?action=deleteTournament&id=<%= t.get("id") %>" class="btn btn-danger btn-sm" onclick="return confirm('Delete Arena Bracket Entirely?')">Delete</a>
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