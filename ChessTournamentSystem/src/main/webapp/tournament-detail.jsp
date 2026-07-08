<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Arena Room Hub</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <nav class="navbar navbar-dark bg-dark mb-4"><div class="container"><a class="navbar-brand fw-bold" href="ChessServlet?action=listTournaments">🏆 Active Context Arena: <%= request.getAttribute("tName") %></a></div></nav>
    <div class="container">
        
        <div class="card p-4 mb-4 border-0 shadow-sm text-center">
            <h5 class="fw-bold mb-3">🎖️ Championship Live Podium Standings</h5>
            <div class="d-flex justify-content-center align-items-end" style="height: 140px;">
                <% 
                List<Map<String, String>> leaderboard = (List<Map<String, String>>) request.getAttribute("leaderboard");
                String first = "-", second = "-", third = "-";
                if(leaderboard != null) {
                    if(leaderboard.size() > 0) first = leaderboard.get(0).get("name") + " (" + leaderboard.get(0).get("score") + " pts)";
                    if(leaderboard.size() > 1) second = leaderboard.get(1).get("name") + " (" + leaderboard.get(1).get("score") + " pts)";
                    if(leaderboard.size() > 2) third = leaderboard.get(2).get("name") + " (" + leaderboard.get(2).get("score") + " pts)";
                }
                %>
                <div class="mx-2" style="width: 140px;"><div class="small fw-bold"><%= second %></div><div class="bg-secondary text-white p-2 rounded-top" style="height: 60px;">🥈 2nd Place</div></div>
                <div class="mx-2" style="width: 160px;"><div class="fw-bold text-warning">🥇 <%= first %></div><div class="bg-warning text-dark p-2 rounded-top fw-bold shadow-sm" style="height: 90px;">🏆 Champion</div></div>
                <div class="mx-2" style="width: 140px;"><div class="small fw-bold"><%= third %></div><div class="text-white p-2 rounded-top" style="height: 40px; background-color: #cd7f32;">🥉 3rd Place</div></div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-6">
                <div class="card p-3 shadow-sm mb-4">
                    <h6>Enroll Competitor to This Arena</h6>
                    <form action="ChessServlet?action=registerPlayer" method="POST" class="d-flex mt-2">
                        <input type="hidden" name="tournamentId" value="<%= request.getAttribute("tId") %>">
                        <select name="playerId" class="form-select me-2" required>
                            <option value="">Choose global roster player...</option>
                            <% List<Map<String, String>> available = (List<Map<String, String>>) request.getAttribute("available");
                               if(available != null){ for(Map<String, String> av : available) { %>
                                <option value="<%= av.get("id") %>"><%= av.get("name") %></option>
                            <% } } %>
                        </select>
                        <button type="submit" class="btn btn-dark btn-sm">Enroll</button>
                    </form>
                </div>

                <div class="card p-3 shadow-sm">
                    <h6>Complete Scoreboard Standings</h6>
                    <table class="table table-sm mt-2">
                        <thead><tr><th>Rank</th><th>Name</th><th>Club</th><th>Score</th></tr></thead>
                        <tbody>
                            <% if(leaderboard != null) { int r = 1; for(Map<String, String> entry : leaderboard) { %>
                                <tr><td>#<%= r++ %></td><td><strong><%= entry.get("name") %></strong></td><td><%= entry.get("club") %></td><td><span class="badge bg-dark"><%= entry.get("score") %> Pts</span></td></tr>
                            <% } } %>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="col-md-6">
                <div class="card p-3 shadow-sm bg-white">
                    <div class="d-flex justify-content-between align-items-center mb-3">
                        <h6 class="m-0">Matchmaking Simulation Engine</h6>
                        <a href="ChessServlet?action=runMatchEngine&id=<%= request.getAttribute("tId") %>" class="btn btn-danger btn-sm fw-bold">🎲 Roll & Pair Round</a>
                    </div>
                    <div style="max-height: 380px; overflow-y: auto;">
                        <% List<Map<String, String>> matches = (List<Map<String, String>>) request.getAttribute("matches");
                           if(matches != null) { for(Map<String, String> m : matches) { %>
                            <div class="border rounded p-3 mb-2 bg-light">
                                <div class="d-flex justify-content-between small text-muted"><span>Match Ref #<%= m.get("id") %></span><span class="text-success fw-bold">Winner: <%= m.get("winner") %></span></div>
                                <div class="text-center fw-bold mt-1"><%= m.get("p1") %> <span class="text-danger mx-2">VS</span> <%= m.get("p2") %></div>
                            </div>
                        <% } } %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>