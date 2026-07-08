<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Modify Arena Context</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5" style="max-width: 500px;">
        <div class="card p-4 shadow-sm">
            <h4 class="fw-bold text-center mb-4">Edit Tournament Details</h4>
            <form action="ChessServlet?action=updateTournament" method="POST">
                <input type="hidden" name="id" value="${t_id}">
                <div class="mb-3"><label class="form-label">Tournament Title</label><input type="text" name="name" value="${t_name}" class="form-control" required></div>
                <div class="mb-3"><label class="form-label">Execution Date</label><input type="date" name="date" value="${t_date}" class="form-control" required></div>
                <button type="submit" class="btn btn-success w-100">Commit Changes</button>
                <a href="ChessServlet?action=listTournaments" class="btn btn-link w-100 mt-2 text-decoration-none text-muted">Cancel</a>
            </form>
        </div>
    </div>
</body>
</html>