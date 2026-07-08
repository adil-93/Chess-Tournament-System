<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Modify Competitor Profile</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5" style="max-width: 500px;">
        <div class="card p-4 shadow-sm">
            <h4 class="fw-bold text-center mb-4">Edit Player Registry</h4>
            <form action="ChessServlet?action=updatePlayer" method="POST">
                <input type="hidden" name="id" value="${player_id}">
                <div class="mb-3"><label class="form-label">Full Name</label><input type="text" name="name" value="${player_name}" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Club Affiliation</label><input type="text" name="club" value="${player_club}" class="form-control" required></div>
                <button type="submit" class="btn btn-success w-100">Commit Update Changes</button>
                <a href="ChessServlet?action=listPlayers" class="btn btn-link w-100 mt-2 text-decoration-none text-muted">Back to Profiles</a>
            </form>
        </div>
    </div>
</body>
</html>