package dao;

import model.Expense;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class ExpenseDAO {
    public void addExpense(Expense exp) throws Exception {
        String sql = "INSERT INTO expenses (title, amount, category, expense_date, notes) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, exp.getTitle());
            ps.setDouble(2, exp.getAmount());
            ps.setString(3, exp.getCategory());
            ps.setDate(4, new java.sql.Date(exp.getDate().getTime()));
            ps.setString(5, exp.getNotes());
            ps.executeUpdate();
        }
    }

    public List<Expense> getAllExpenses() throws Exception {
        List<Expense> list = new ArrayList<>();
        String sql = "SELECT * FROM expenses";
        try (Connection conn = DBConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Expense exp = new Expense();
                exp.setId(rs.getInt("id"));
                exp.setTitle(rs.getString("title"));
                exp.setAmount(rs.getDouble("amount"));
                exp.setCategory(rs.getString("category"));
                exp.setDate(rs.getDate("expense_date"));
                exp.setNotes(rs.getString("notes"));
                list.add(exp);
            }
        }
        return list;
    }
}
