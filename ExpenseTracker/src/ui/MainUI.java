package ui;

import model.Expense;
import dao.ExpenseDAO;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainUI extends JFrame {

    private JTextField titleField, amountField, categoryField, dateField;
    private JTextArea notesArea;
    private JTable expenseTable;
    private DefaultTableModel tableModel;

    public MainUI() {
        setTitle("💸 Expense Tracker and Manager");
        setSize(950, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);
        ((JComponent) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 13);

        // ===== Input Panel =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(250, 252, 255));
        formPanel.setBorder(new TitledBorder(new EtchedBorder(), "Add New Expense", TitledBorder.LEFT, TitledBorder.TOP, labelFont));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Title:"), gbc);
        gbc.gridx = 1;
        titleField = new JTextField(); titleField.setFont(fieldFont);
        formPanel.add(titleField, gbc);

        // Amount
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Amount:"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(); amountField.setFont(fieldFont);
        formPanel.add(amountField, gbc);

        // Category
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Category:"), gbc);
        gbc.gridx = 1;
        categoryField = new JTextField(); categoryField.setFont(fieldFont);
        formPanel.add(categoryField, gbc);

        // Date
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Date (yyyy-MM-dd):"), gbc);
        gbc.gridx = 1;
        dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        dateField.setFont(fieldFont);
        formPanel.add(dateField, gbc);

        // Notes
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Notes:"), gbc);
        gbc.gridx = 1;
        notesArea = new JTextArea(3, 20); notesArea.setFont(fieldFont);
        formPanel.add(new JScrollPane(notesArea), gbc);

        // ===== Buttons =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(formPanel.getBackground());

        JButton addButton = new JButton("➕ Add");
        addButton.setToolTipText("Add new expense to database");
        addButton.setBackground(new Color(0, 123, 255));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(labelFont);
        addButton.addActionListener(e -> addExpense());

        JButton loadButton = new JButton("🔄 Load");
        loadButton.setToolTipText("Reload all expenses");
        loadButton.setBackground(new Color(40, 167, 69));
        loadButton.setForeground(Color.WHITE);
        loadButton.setFont(labelFont);
        loadButton.addActionListener(e -> loadExpenses());

        buttonPanel.add(addButton);
        buttonPanel.add(loadButton);

        // ===== Table Panel =====
        tableModel = new DefaultTableModel(new String[]{"ID", "Title", "Amount", "Category", "Date", "Notes"}, 0);
        expenseTable = new JTable(tableModel);
        expenseTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        expenseTable.setRowHeight(24);
        expenseTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Alternating row colors
        expenseTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            private final Color evenColor = new Color(245, 245, 250);
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                c.setBackground(row % 2 == 0 ? evenColor : Color.WHITE);
                return c;
            }
        });

        JScrollPane tableScrollPane = new JScrollPane(expenseTable);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Expense Records", TitledBorder.LEFT, TitledBorder.TOP, labelFont));

        // Layout the panels
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(formPanel, BorderLayout.CENTER);
        northPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addExpense() {
        try {
            String title = titleField.getText().trim();
            String amountText = amountField.getText().trim();
            String category = categoryField.getText().trim();
            String dateText = dateField.getText().trim();
            String notes = notesArea.getText().trim();

            if (title.isEmpty() || amountText.isEmpty() || category.isEmpty() || dateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields.", "Missing Info", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double amount = Double.parseDouble(amountText);
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateText);

            Expense expense = new Expense();
            expense.setTitle(title);
            expense.setAmount(amount);
            expense.setCategory(category);
            expense.setDate(date);
            expense.setNotes(notes);

            new ExpenseDAO().addExpense(expense);
            JOptionPane.showMessageDialog(this, "✅ Expense added successfully!");
            clearFields();
            loadExpenses();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Amount must be a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error adding expense: " + ex.getMessage());
        }
    }

    private void loadExpenses() {
        try {
            List<Expense> list = new ExpenseDAO().getAllExpenses();
            tableModel.setRowCount(0);
            for (Expense e : list) {
                tableModel.addRow(new Object[]{
                        e.getId(), e.getTitle(), e.getAmount(), e.getCategory(),
                        new SimpleDateFormat("yyyy-MM-dd").format(e.getDate()), e.getNotes()
                });
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error loading expenses: " + ex.getMessage());
        }
    }

    private void clearFields() {
        titleField.setText("");
        amountField.setText("");
        categoryField.setText("");
        dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        notesArea.setText("");
    }
}
