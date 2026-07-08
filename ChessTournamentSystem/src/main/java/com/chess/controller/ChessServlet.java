package com.chess.controller;

import java.io.IOException;
import java.sql.*;
import java.util.*;

// Cleaned up and updated specifically for Tomcat 10+
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import com.chess.util.DBConnection;

@WebServlet("/ChessServlet")
public class ChessServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "listTournaments";

        try {
            switch (action) {
                case "listPlayers": listPlayers(request, response); break;
                case "addPlayer": addPlayer(request, response); break;
                case "editPlayerForm": editPlayerForm(request, response); break;
                case "updatePlayer": updatePlayer(request, response); break;
                case "deletePlayer": deletePlayer(request, response); break;
                case "listTournaments": listTournaments(request, response); break;
                case "addTournament": addTournament(request, response); break;
                case "editTournamentForm": editTournamentForm(request, response); break;
                case "updateTournament": updateTournament(request, response); break;
                case "deleteTournament": deleteTournament(request, response); break;
                case "viewTournament": viewTournament(request, response); break;
                case "registerPlayer": registerPlayer(request, response); break;
                case "runMatchEngine": runMatchEngine(request, response); break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void listPlayers(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Map<String, String>> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); Statement s = c.createStatement(); ResultSet r = s.executeQuery("SELECT * FROM players ORDER BY id DESC")) {
            while (r.next()) {
                Map<String, String> m = new HashMap<>();
                m.put("id", r.getString("id")); m.put("name", r.getString("name")); m.put("club", r.getString("club"));
                list.add(m);
            }
        }
        request.setAttribute("players", list);
        request.getRequestDispatcher("players.jsp").forward(request, response);
    }

    private void addPlayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("INSERT INTO players (name, club) VALUES (?, ?)")) {
            p.setString(1, request.getParameter("name")); p.setString(2, request.getParameter("club"));
            p.executeUpdate();
        }
        response.sendRedirect("ChessServlet?action=listPlayers");
    }

    private void editPlayerForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM players WHERE id = ?")) {
            p.setInt(1, Integer.parseInt(request.getParameter("id")));
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    request.setAttribute("player_id", r.getString("id"));
                    request.setAttribute("player_name", r.getString("name"));
                    request.setAttribute("player_club", r.getString("club"));
                }
            }
        }
        request.getRequestDispatcher("edit-player.jsp").forward(request, response);
    }

    private void updatePlayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("UPDATE players SET name = ?, club = ? WHERE id = ?")) {
            p.setString(1, request.getParameter("name")); p.setString(2, request.getParameter("club"));
            p.setInt(3, Integer.parseInt(request.getParameter("id"))); p.executeUpdate();
        }
        response.sendRedirect("ChessServlet?action=listPlayers");
    }

    private void deletePlayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("DELETE FROM players WHERE id = ?")) {
            p.setInt(1, Integer.parseInt(request.getParameter("id"))); p.executeUpdate();
        }
        response.sendRedirect("ChessServlet?action=listPlayers");
    }

    private void listTournaments(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<Map<String, String>> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); Statement s = c.createStatement(); ResultSet r = s.executeQuery("SELECT * FROM tournaments ORDER BY id DESC")) {
            while (r.next()) {
                Map<String, String> m = new HashMap<>();
                m.put("id", r.getString("id")); m.put("name", r.getString("name")); m.put("date", r.getString("t_date"));
                list.add(m);
            }
        }
        request.setAttribute("tournaments", list);
        request.getRequestDispatcher("tournaments.jsp").forward(request, response);
    }

    private void addTournament(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("INSERT INTO tournaments (name, t_date) VALUES (?, ?)")) {
            p.setString(1, request.getParameter("name")); p.setString(2, request.getParameter("date")); p.executeUpdate();
        }
        response.sendRedirect("ChessServlet?action=listTournaments");
    }

    private void editTournamentForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM tournaments WHERE id = ?")) {
            p.setInt(1, Integer.parseInt(request.getParameter("id")));
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    request.setAttribute("t_id", r.getString("id"));
                    request.setAttribute("t_name", r.getString("name"));
                    request.setAttribute("t_date", r.getString("t_date"));
                }
            }
        }
        request.getRequestDispatcher("edit-tournament.jsp").forward(request, response);
    }

    private void updateTournament(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("UPDATE tournaments SET name = ?, t_date = ? WHERE id = ?")) {
            p.setString(1, request.getParameter("name")); p.setString(2, request.getParameter("date"));
            p.setInt(3, Integer.parseInt(request.getParameter("id"))); p.executeUpdate();
        }
        response.sendRedirect("ChessServlet?action=listTournaments");
    }

    private void deleteTournament(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("DELETE FROM tournaments WHERE id = ?")) {
            p.setInt(1, Integer.parseInt(request.getParameter("id"))); p.executeUpdate();
        }
        response.sendRedirect("ChessServlet?action=listTournaments");
    }

    private void viewTournament(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int tId = Integer.parseInt(request.getParameter("id"));
        
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM tournaments WHERE id = ?")) {
            p.setInt(1, tId);
            try (ResultSet r = p.executeQuery()) { if (r.next()) request.setAttribute("tName", r.getString("name")); }
        }
        request.setAttribute("tId", tId);

        List<Map<String, String>> leaderboard = new ArrayList<>();
        String rQuery = "SELECT p.name, p.club, tp.score FROM players p JOIN tournament_players tp ON p.id = tp.player_id WHERE tp.tournament_id = ? ORDER BY tp.score DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(rQuery)) {
            p.setInt(1, tId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("name", r.getString("name")); row.put("club", r.getString("club")); row.put("score", r.getString("score"));
                    leaderboard.add(row);
                }
            }
        }
        request.setAttribute("leaderboard", leaderboard);

        List<Map<String, String>> matches = new ArrayList<>();
        String mQuery = "SELECT m.id, p1.name AS p1n, p2.name AS p2n, w.name AS wn FROM matches m JOIN players p1 ON m.player1_id = p1.id LEFT JOIN players p2 ON m.player2_id = p2.id LEFT JOIN players w ON m.winner_id = w.id WHERE m.tournament_id = ? ORDER BY m.id DESC";
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement(mQuery)) {
            p.setInt(1, tId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("id", r.getString("id")); row.put("p1", r.getString("p1n"));
                    row.put("p2", r.getString("p2n") == null ? "BYE ROUND" : r.getString("p2n"));
                    row.put("winner", r.getString("wn") == null ? "BYE" : r.getString("wn"));
                    matches.add(row);
                }
            }
        }
        request.setAttribute("matches", matches);

        List<Map<String, String>> available = new ArrayList<>();
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT * FROM players WHERE id NOT IN (SELECT player_id FROM tournament_players WHERE tournament_id = ?)")) {
            p.setInt(1, tId);
            try (ResultSet r = p.executeQuery()) {
                while (r.next()) {
                    Map<String, String> row = new HashMap<>();
                    row.put("id", r.getString("id")); row.put("name", r.getString("name"));
                    available.add(row);
                }
            }
        }
        request.setAttribute("available", available);
        request.getRequestDispatcher("tournament-detail.jsp").forward(request, response);
    }

    private void registerPlayer(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int tId = Integer.parseInt(request.getParameter("tournamentId"));
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("INSERT IGNORE INTO tournament_players (tournament_id, player_id, score) VALUES (?, ?, 0)")) {
            p.setInt(1, tId); p.setInt(2, Integer.parseInt(request.getParameter("playerId"))); p.executeUpdate();
        }
        response.sendRedirect("ChessServlet?action=viewTournament&id=" + tId);
    }

    private void runMatchEngine(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int tId = Integer.parseInt(request.getParameter("id"));
        List<Integer> playerIds = new ArrayList<>();
        
        try (Connection c = DBConnection.getConnection(); PreparedStatement p = c.prepareStatement("SELECT player_id FROM tournament_players WHERE tournament_id = ?")) {
            p.setInt(1, tId);
            try (ResultSet r = p.executeQuery()) { while (r.next()) playerIds.add(r.getInt("player_id")); }
        }

        if (playerIds.size() >= 2) {
            Collections.shuffle(playerIds);
            Random random = new Random();
            try (Connection c = DBConnection.getConnection()) {
                for (int i = 0; i < playerIds.size(); i += 2) {
                    if (i + 1 < playerIds.size()) {
                        int p1 = playerIds.get(i); int p2 = playerIds.get(i + 1);
                        int winner = random.nextBoolean() ? p1 : p2;

                        try (PreparedStatement pm = c.prepareStatement("INSERT INTO matches (tournament_id, player1_id, player2_id, winner_id) VALUES (?,?,?,?)")) {
                            pm.setInt(1, tId); pm.setInt(2, p1); pm.setInt(3, p2); pm.setInt(4, winner); pm.executeUpdate();
                        }
                        try (PreparedStatement pu = c.prepareStatement("UPDATE tournament_players SET score = score + 1 WHERE tournament_id = ? AND player_id = ?")) {
                            pu.setInt(1, tId); pu.setInt(2, winner); pu.executeUpdate();
                        }
                    } else {
                        int p1 = playerIds.get(i);
                        try (PreparedStatement pm = c.prepareStatement("INSERT INTO matches (tournament_id, player1_id, player2_id, winner_id) VALUES (?,?,NULL,?)")) {
                            pm.setInt(1, tId); pm.setInt(2, p1); pm.setInt(3, p1); pm.executeUpdate();
                        }
                        try (PreparedStatement pu = c.prepareStatement("UPDATE tournament_players SET score = score + 1 WHERE tournament_id = ? AND player_id = ?")) {
                            pu.setInt(1, tId); pu.setInt(2, p1); pu.executeUpdate();
                        }
                    }
                }
            }
        }
        response.sendRedirect("ChessServlet?action=viewTournament&id=" + tId);
    }
}