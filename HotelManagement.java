import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Single-file Java Hotel Management System — Enhanced Premium GUI
 * Features:
 * 1. Swing GUI with custom painted panels, gradient headers, rounded buttons
 * 2. Room Booking, Check-In/Check-Out, Billing & Record Table
 * 3. In-Memory Persistence + MongoDB Support Hook
 * 4. Automatic Web HTML Export
 */
public class HotelManagement extends JFrame {

    // ═══════════════════ COLOR PALETTE ═══════════════════
    private static final Color PRIMARY       = new Color(30, 60, 114);
    private static final Color PRIMARY_LIGHT = new Color(42, 82, 152);
    private static final Color ACCENT        = new Color(0, 150, 136);
    private static final Color ACCENT_DARK   = new Color(0, 121, 107);
    private static final Color SUCCESS       = new Color(46, 125, 50);
    private static final Color DANGER        = new Color(198, 40, 40);
    private static final Color WARNING       = new Color(245, 124, 0);
    private static final Color CARD_BG       = new Color(255, 255, 255);
    private static final Color PANEL_BG      = new Color(240, 242, 247);
    private static final Color TEXT_PRIMARY   = new Color(33, 33, 33);
    private static final Color TEXT_SECONDARY = new Color(117, 117, 117);
    private static final Color GOLD          = new Color(255, 193, 7);
    private static final Font FONT_TITLE     = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITLE  = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONT_LABEL     = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_INPUT     = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BUTTON    = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_TABLE     = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_MONO      = new Font("Consolas", Font.PLAIN, 13);

    // ═══════════════════ INNER DATA MODEL ═══════════════════
    public static class Booking {
        private static int idCounter = 1001;
        private int bookingId;
        private String guestName, phone, roomType, status;
        private int roomNumber, days;
        private double totalPrice;
        private String bookingDate;

        public Booking(String guestName, String phone, String roomType, int roomNumber, int days, double totalPrice) {
            this.bookingId = idCounter++;
            this.guestName = guestName;
            this.phone = phone;
            this.roomType = roomType;
            this.roomNumber = roomNumber;
            this.days = days;
            this.totalPrice = totalPrice;
            this.status = "Booked";
            this.bookingDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy"));
        }

        public int getBookingId() { return bookingId; }
        public String getGuestName() { return guestName; }
        public String getPhone() { return phone; }
        public String getRoomType() { return roomType; }
        public int getRoomNumber() { return roomNumber; }
        public int getDays() { return days; }
        public double getTotalPrice() { return totalPrice; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getBookingDate() { return bookingDate; }
    }

    // ═══════════════════ CUSTOM UI COMPONENTS ═══════════════════

    /** Gradient painted panel for headers */
    static class GradientPanel extends JPanel {
        private Color start, end;
        GradientPanel(Color start, Color end) { this.start = start; this.end = end; setOpaque(false); }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setPaint(new GradientPaint(0, 0, start, getWidth(), getHeight(), end));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Rounded button with hover animation */
    static class RoundedButton extends JButton {
        private Color bgColor, hoverColor, pressColor;
        private boolean hovered = false, pressed = false;
        RoundedButton(String text, Color bg) {
            super(text);
            this.bgColor = bg;
            this.hoverColor = bg.brighter();
            this.pressColor = bg.darker();
            setFont(FONT_BUTTON);
            setForeground(Color.WHITE);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setPreferredSize(new Dimension(180, 38));
            addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
                public void mouseExited(MouseEvent e) { hovered = false; pressed = false; repaint(); }
                public void mousePressed(MouseEvent e) { pressed = true; repaint(); }
                public void mouseReleased(MouseEvent e) { pressed = false; repaint(); }
            });
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color c = pressed ? pressColor : (hovered ? hoverColor : bgColor);
            g2.setColor(c);
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
            // shadow on hover
            if (hovered && !pressed) {
                g2.setColor(new Color(0, 0, 0, 30));
                g2.fill(new RoundRectangle2D.Float(2, 2, getWidth(), getHeight(), 12, 12));
                g2.setColor(c);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
            }
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Card-style panel with rounded corners and shadow */
    static class CardPanel extends JPanel {
        CardPanel() {
            setBackground(CARD_BG);
            setOpaque(false);
            setBorder(BorderFactory.createEmptyBorder(18, 20, 18, 20));
        }
        @Override protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // soft shadow
            g2.setColor(new Color(0, 0, 0, 18));
            g2.fill(new RoundRectangle2D.Float(3, 3, getWidth() - 3, getHeight() - 3, 16, 16));
            // card bg
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 3, getHeight() - 3, 16, 16));
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /** Styled text field with rounded border */
    static class StyledTextField extends JTextField {
        StyledTextField(int cols) {
            super(cols);
            setFont(FONT_INPUT);
            setBackground(new Color(248, 249, 252));
            setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 210, 225), 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
        }
        StyledTextField(String text, int cols) { this(cols); setText(text); }
    }

    /** Status badge label */
    static JLabel createBadge(String text, Color bg) {
        JLabel badge = new JLabel(text, SwingConstants.CENTER) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 14, 14));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(false);
        badge.setPreferredSize(new Dimension(100, 24));
        return badge;
    }

    // ═══════════════════ STATE ═══════════════════
    private static final List<Booking> bookingList = new ArrayList<>();
    private static boolean mongoAvailable = false;

    private StyledTextField txtName, txtPhone, txtDays, txtSearchId;
    private JComboBox<String> comboRoomType;
    private JLabel lblPriceCalc, lblMongoStatus;
    private JLabel lblStatTotal, lblStatCheckedIn, lblStatRevenue;
    private DefaultTableModel tableModel;
    private JTable bookingTable;

    // ═══════════════════ CONSTRUCTOR ═══════════════════
    public HotelManagement() {
        setTitle("Grand Luxury Hotel — Management System");
        setSize(960, 680);
        setMinimumSize(new Dimension(800, 550));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception ignored) {}

        // Main layout: header + body
        setLayout(new BorderLayout());
        add(createHeader(), BorderLayout.NORTH);
        add(createTabbedBody(), BorderLayout.CENTER);

        checkMongoConnection();
        loadSampleData();
        refreshTable();
        updateDashboardStats();
    }

    // ═══════════════════ HEADER BANNER ═══════════════════
    private JPanel createHeader() {
        GradientPanel header = new GradientPanel(PRIMARY, PRIMARY_LIGHT);
        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(0, 72));
        header.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));

        JLabel title = new JLabel("Grand Luxury Hotel & Resort");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("Management System v2.0  •  " +
            LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy")));
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(200, 215, 240));

        JPanel left = new JPanel(new GridLayout(2, 1));
        left.setOpaque(false);
        left.add(title);
        left.add(subtitle);
        header.add(left, BorderLayout.WEST);

        // Star Rating Badge
        JLabel stars = new JLabel("★★★★★", SwingConstants.RIGHT);
        stars.setFont(new Font("Segoe UI", Font.BOLD, 22));
        stars.setForeground(GOLD);
        header.add(stars, BorderLayout.EAST);

        return header;
    }

    // ═══════════════════ TABBED BODY ═══════════════════
    private JComponent createTabbedBody() {
        JTabbedPane tabs = new JTabbedPane(JTabbedPane.LEFT);
        tabs.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tabs.setBackground(PANEL_BG);

        tabs.addTab("  Room Booking  ", createBookingPanel());
        tabs.addTab("  Check-In/Out  ", createManagePanel());
        tabs.addTab("  All Records   ", createRecordsPanel());
        tabs.addTab("  Dashboard     ", createDashboardPanel());

        return tabs;
    }

    // ═══════════════════ TAB 1: ROOM BOOKING ═══════════════════
    private JPanel createBookingPanel() {
        JPanel outer = new JPanel(new BorderLayout(20, 0));
        outer.setBackground(PANEL_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Left: Booking Form Card
        CardPanel formCard = new CardPanel();
        formCard.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel formTitle = new JLabel("New Guest Reservation");
        formTitle.setFont(FONT_TITLE);
        formTitle.setForeground(PRIMARY);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formCard.add(formTitle, gbc);

        // Separator
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(220, 225, 235));
        gbc.gridy = 1;
        formCard.add(sep, gbc);
        gbc.gridwidth = 1;

        // Fields
        addFormRow(formCard, gbc, 2, "Guest Full Name");
        txtName = new StyledTextField(18);
        gbc.gridx = 1; formCard.add(txtName, gbc);

        addFormRow(formCard, gbc, 3, "Phone Number");
        txtPhone = new StyledTextField(18);
        gbc.gridx = 1; formCard.add(txtPhone, gbc);

        addFormRow(formCard, gbc, 4, "Room Category");
        comboRoomType = new JComboBox<>(new String[]{
            "Standard — ₹1,500/night",
            "Deluxe — ₹2,800/night",
            "Executive Suite — ₹5,000/night"
        });
        comboRoomType.setFont(FONT_INPUT);
        comboRoomType.setBackground(new Color(248, 249, 252));
        gbc.gridx = 1; formCard.add(comboRoomType, gbc);

        addFormRow(formCard, gbc, 5, "Stay Duration (Days)");
        txtDays = new StyledTextField("1", 18);
        gbc.gridx = 1; formCard.add(txtDays, gbc);

        // Price display
        lblPriceCalc = new JLabel("Estimated Total: ₹1,500.00");
        lblPriceCalc.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblPriceCalc.setForeground(SUCCESS);
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        gbc.insets = new Insets(14, 8, 8, 8);
        formCard.add(lblPriceCalc, gbc);
        gbc.insets = new Insets(8, 8, 8, 8);

        // Buttons
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        btnRow.setOpaque(false);
        RoundedButton btnCalc = new RoundedButton("Calculate Price", ACCENT);
        RoundedButton btnBook = new RoundedButton("Confirm Booking", PRIMARY);
        btnRow.add(btnCalc);
        btnRow.add(btnBook);
        gbc.gridy = 7;
        formCard.add(btnRow, gbc);

        btnCalc.addActionListener(e -> calculatePrice());
        btnBook.addActionListener(e -> handleBooking());

        outer.add(formCard, BorderLayout.CENTER);

        // Right: Room Info Cards
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(220, 0));

        rightPanel.add(createRoomInfoCard("Standard", "₹1,500", "Wi-Fi • TV • AC", new Color(66, 165, 245)));
        rightPanel.add(Box.createVerticalStrut(12));
        rightPanel.add(createRoomInfoCard("Deluxe", "₹2,800", "Balcony • Breakfast", new Color(102, 187, 106)));
        rightPanel.add(Box.createVerticalStrut(12));
        rightPanel.add(createRoomInfoCard("Executive", "₹5,000", "Jacuzzi • Butler", new Color(255, 167, 38)));

        outer.add(rightPanel, BorderLayout.EAST);
        return outer;
    }

    private CardPanel createRoomInfoCard(String type, String price, String features, Color accentColor) {
        CardPanel card = new CardPanel();
        card.setLayout(new BorderLayout(0, 6));
        card.setMaximumSize(new Dimension(220, 120));

        JLabel lblType = new JLabel(type);
        lblType.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblType.setForeground(accentColor);
        card.add(lblType, BorderLayout.NORTH);

        JLabel lblPrice = new JLabel(price + " / night");
        lblPrice.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblPrice.setForeground(TEXT_PRIMARY);
        card.add(lblPrice, BorderLayout.CENTER);

        JLabel lblFeatures = new JLabel(features);
        lblFeatures.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblFeatures.setForeground(TEXT_SECONDARY);
        card.add(lblFeatures, BorderLayout.SOUTH);

        return card;
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_PRIMARY);
        gbc.gridx = 0; gbc.gridy = row; gbc.gridwidth = 1;
        panel.add(lbl, gbc);
    }

    // ═══════════════════ TAB 2: CHECK-IN/OUT & BILLING ═══════════════════
    private JPanel createManagePanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 16));
        outer.setBackground(PANEL_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        // Search Bar Card
        CardPanel searchCard = new CardPanel();
        searchCard.setLayout(new FlowLayout(FlowLayout.LEFT, 12, 4));

        JLabel searchTitle = new JLabel("Search Booking");
        searchTitle.setFont(FONT_LABEL);
        searchTitle.setForeground(PRIMARY);
        searchCard.add(searchTitle);

        JLabel idLabel = new JLabel("Booking ID:");
        idLabel.setFont(FONT_LABEL);
        searchCard.add(idLabel);

        txtSearchId = new StyledTextField(10);
        searchCard.add(txtSearchId);

        RoundedButton btnSearch = new RoundedButton("Search", PRIMARY_LIGHT);
        btnSearch.setPreferredSize(new Dimension(110, 34));
        searchCard.add(btnSearch);

        outer.add(searchCard, BorderLayout.NORTH);

        // Invoice Display Card
        CardPanel invoiceCard = new CardPanel();
        invoiceCard.setLayout(new BorderLayout());

        JLabel invTitle = new JLabel("  Invoice / Bill Preview");
        invTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        invTitle.setForeground(PRIMARY);
        invoiceCard.add(invTitle, BorderLayout.NORTH);

        JTextArea billDisplay = new JTextArea();
        billDisplay.setFont(FONT_MONO);
        billDisplay.setEditable(false);
        billDisplay.setBackground(new Color(248, 249, 252));
        billDisplay.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        billDisplay.setText("  Enter a Booking ID above and click 'Search' to view details.\n\n" +
                            "  Tip: Sample booking IDs: 1001, 1002");

        JScrollPane scrollPane = new JScrollPane(billDisplay);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
        invoiceCard.add(scrollPane, BorderLayout.CENTER);

        outer.add(invoiceCard, BorderLayout.CENTER);

        // Action Buttons Card
        CardPanel actionCard = new CardPanel();
        actionCard.setLayout(new FlowLayout(FlowLayout.CENTER, 16, 4));

        RoundedButton btnCheckIn = new RoundedButton("Mark Checked-In", ACCENT);
        RoundedButton btnCheckOut = new RoundedButton("Check-Out & Invoice", DANGER);
        actionCard.add(btnCheckIn);
        actionCard.add(btnCheckOut);
        outer.add(actionCard, BorderLayout.SOUTH);

        // Event Logic
        btnSearch.addActionListener(e -> {
            Booking b = findBooking(txtSearchId.getText());
            if (b != null) {
                billDisplay.setText(generateBillText(b));
            } else {
                JOptionPane.showMessageDialog(this, "Booking ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCheckIn.addActionListener(e -> {
            Booking b = findBooking(txtSearchId.getText());
            if (b != null) {
                b.setStatus("Checked-In");
                refreshTable(); updateDashboardStats();
                billDisplay.setText(generateBillText(b));
                JOptionPane.showMessageDialog(this, "Guest status updated to Checked-In.");
            }
        });

        btnCheckOut.addActionListener(e -> {
            Booking b = findBooking(txtSearchId.getText());
            if (b != null) {
                b.setStatus("Checked-Out");
                refreshTable(); updateDashboardStats();
                billDisplay.setText(generateBillText(b));
                JOptionPane.showMessageDialog(this, "Invoice generated & Room vacated.");
            }
        });

        return outer;
    }

    // ═══════════════════ TAB 3: BOOKING RECORDS TABLE ═══════════════════
    private JPanel createRecordsPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 12));
        outer.setBackground(PANEL_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("  All Booking Records");
        title.setFont(FONT_TITLE);
        title.setForeground(PRIMARY);
        outer.add(title, BorderLayout.NORTH);

        CardPanel tableCard = new CardPanel();
        tableCard.setLayout(new BorderLayout());

        String[] columns = {"ID", "Guest Name", "Phone", "Room Type", "Room #", "Days", "Total (₹)", "Date", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        bookingTable = new JTable(tableModel);
        bookingTable.setRowHeight(32);
        bookingTable.setFont(FONT_TABLE);
        bookingTable.setShowGrid(false);
        bookingTable.setIntercellSpacing(new Dimension(0, 0));
        bookingTable.setSelectionBackground(new Color(227, 242, 253));
        bookingTable.setSelectionForeground(TEXT_PRIMARY);
        bookingTable.setFillsViewportHeight(true);

        // Header styling
        JTableHeader header = bookingTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(PRIMARY);
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(0, 36));
        header.setReorderingAllowed(false);

        // Alternating row colors & status badge rendering
        bookingTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object val, boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, val, sel, foc, row, col);
                if (!sel) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 247, 250));
                }
                ((JLabel) c).setBorder(BorderFactory.createEmptyBorder(0, 8, 0, 8));
                // Color the status column
                if (col == 8 && val != null) {
                    String status = val.toString();
                    if ("Checked-In".equals(status)) c.setForeground(SUCCESS);
                    else if ("Checked-Out".equals(status)) c.setForeground(DANGER);
                    else if ("Booked".equals(status)) c.setForeground(PRIMARY_LIGHT);
                    else c.setForeground(TEXT_PRIMARY);
                } else {
                    if (!sel) c.setForeground(TEXT_PRIMARY);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(bookingTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 235)));
        tableCard.add(scrollPane, BorderLayout.CENTER);
        outer.add(tableCard, BorderLayout.CENTER);

        return outer;
    }

    // ═══════════════════ TAB 4: DASHBOARD ═══════════════════
    private JPanel createDashboardPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 18));
        outer.setBackground(PANEL_BG);
        outer.setBorder(BorderFactory.createEmptyBorder(24, 24, 24, 24));

        JLabel title = new JLabel("  Hotel Dashboard");
        title.setFont(FONT_TITLE);
        title.setForeground(PRIMARY);
        outer.add(title, BorderLayout.NORTH);

        // Stats Row
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 16, 0));
        statsRow.setOpaque(false);
        statsRow.setPreferredSize(new Dimension(0, 100));

        lblStatTotal = new JLabel("0");
        lblStatCheckedIn = new JLabel("0");
        lblStatRevenue = new JLabel("₹0");

        statsRow.add(createStatCard("Total Bookings", lblStatTotal, PRIMARY));
        statsRow.add(createStatCard("Currently Checked-In", lblStatCheckedIn, ACCENT));
        statsRow.add(createStatCard("Total Revenue", lblStatRevenue, SUCCESS));

        outer.add(statsRow, BorderLayout.CENTER);

        // Bottom: System Info + HTML Export
        CardPanel sysCard = new CardPanel();
        sysCard.setLayout(new BoxLayout(sysCard, BoxLayout.Y_AXIS));

        JLabel sysTitle = new JLabel("System Configuration");
        sysTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        sysTitle.setForeground(PRIMARY);
        sysTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        sysCard.add(sysTitle);
        sysCard.add(Box.createVerticalStrut(10));

        lblMongoStatus = new JLabel("MongoDB Status: Checking...");
        lblMongoStatus.setFont(FONT_LABEL);
        lblMongoStatus.setAlignmentX(Component.LEFT_ALIGNMENT);
        sysCard.add(lblMongoStatus);
        sysCard.add(Box.createVerticalStrut(8));

        JLabel javaLabel = new JLabel("Java Version: " + System.getProperty("java.version") +
                                       "  |  OS: " + System.getProperty("os.name"));
        javaLabel.setFont(FONT_SUBTITLE);
        javaLabel.setForeground(TEXT_SECONDARY);
        javaLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        sysCard.add(javaLabel);
        sysCard.add(Box.createVerticalStrut(14));

        RoundedButton btnExport = new RoundedButton("Generate & Open hotel.html", ACCENT_DARK);
        btnExport.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnExport.addActionListener(e -> generateAndOpenHtml());
        sysCard.add(btnExport);

        outer.add(sysCard, BorderLayout.SOUTH);
        return outer;
    }

    private CardPanel createStatCard(String label, JLabel valueLabel, Color accent) {
        CardPanel card = new CardPanel();
        card.setLayout(new BorderLayout(0, 4));

        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(TEXT_SECONDARY);
        card.add(lbl, BorderLayout.NORTH);

        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        valueLabel.setForeground(accent);
        card.add(valueLabel, BorderLayout.CENTER);

        // Colored bottom accent bar
        JPanel bar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(accent);
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 6, 6));
                g2.dispose();
            }
        };
        bar.setPreferredSize(new Dimension(0, 4));
        bar.setOpaque(false);
        card.add(bar, BorderLayout.SOUTH);

        return card;
    }

    private void updateDashboardStats() {
        if (lblStatTotal == null) return;
        lblStatTotal.setText(String.valueOf(bookingList.size()));
        long checkedIn = bookingList.stream().filter(b -> "Checked-In".equals(b.getStatus())).count();
        lblStatCheckedIn.setText(String.valueOf(checkedIn));
        double revenue = bookingList.stream().mapToDouble(Booking::getTotalPrice).sum();
        lblStatRevenue.setText(String.format("₹%,.0f", revenue));
    }

    // ═══════════════════ BUSINESS LOGIC ═══════════════════
    private double calculatePrice() {
        try {
            int days = Integer.parseInt(txtDays.getText().trim());
            int idx = comboRoomType.getSelectedIndex();
            double rate = (idx == 0) ? 1500 : (idx == 1) ? 2800 : 5000;
            double total = rate * days;
            lblPriceCalc.setText(String.format("Estimated Total: ₹%,.2f", total));
            return total;
        } catch (Exception ex) {
            lblPriceCalc.setText("Invalid duration entered!");
            return 0;
        }
    }

    private void handleBooking() {
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        if (name.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide Guest Name and Phone Number.", "Input Required", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int days;
        try {
            days = Integer.parseInt(txtDays.getText().trim());
            if (days <= 0) throw new Exception();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number of days.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idx = comboRoomType.getSelectedIndex();
        String roomType = (idx == 0) ? "Standard" : (idx == 1) ? "Deluxe" : "Executive Suite";
        double total = calculatePrice();
        int roomNo = 100 + (bookingList.size() + 1);

        Booking booking = new Booking(name, phone, roomType, roomNo, days, total);
        bookingList.add(booking);
        refreshTable();
        updateDashboardStats();

        JOptionPane.showMessageDialog(this,
            "Booking Confirmed!\nBooking ID: " + booking.getBookingId() +
            "\nAssigned Room: #" + roomNo +
            "\nTotal: ₹" + String.format("%,.2f", total),
            "Success", JOptionPane.INFORMATION_MESSAGE);

        txtName.setText("");
        txtPhone.setText("");
        txtDays.setText("1");
    }

    private Booking findBooking(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            for (Booking b : bookingList) if (b.getBookingId() == id) return b;
        } catch (Exception ignored) {}
        return null;
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Booking b : bookingList) {
            tableModel.addRow(new Object[]{
                b.getBookingId(), b.getGuestName(), b.getPhone(),
                b.getRoomType(), "#" + b.getRoomNumber(), b.getDays(),
                String.format("₹%,.2f", b.getTotalPrice()), b.getBookingDate(), b.getStatus()
            });
        }
    }

    private String generateBillText(Booking b) {
        double subtotal = b.getTotalPrice();
        double tax = subtotal * 0.12;
        double svcCharge = subtotal * 0.05;
        double finalTotal = subtotal + tax + svcCharge;

        return String.format(
            "  ╔══════════════════════════════════════════════╗%n" +
            "  ║         GRAND LUXURY HOTEL & RESORT         ║%n" +
            "  ║              OFFICIAL INVOICE                ║%n" +
            "  ╠══════════════════════════════════════════════╣%n" +
            "  ║  Booking ID    : %-28d║%n" +
            "  ║  Guest Name    : %-28s║%n" +
            "  ║  Phone         : %-28s║%n" +
            "  ║  Room Category : %-28s║%n" +
            "  ║  Room Number   : #%-27d║%n" +
            "  ║  Booking Date  : %-28s║%n" +
            "  ║  Stay Duration : %-24d Days║%n" +
            "  ║  Status        : %-28s║%n" +
            "  ╠══════════════════════════════════════════════╣%n" +
            "  ║  Room Charges     :  ₹%,18.2f    ║%n" +
            "  ║  GST (12%%)        :  ₹%,18.2f    ║%n" +
            "  ║  Service Charge   :  ₹%,18.2f    ║%n" +
            "  ╠══════════════════════════════════════════════╣%n" +
            "  ║  GRAND TOTAL      :  ₹%,18.2f    ║%n" +
            "  ╚══════════════════════════════════════════════╝%n",
            b.getBookingId(), b.getGuestName(), b.getPhone(),
            b.getRoomType(), b.getRoomNumber(), b.getBookingDate(),
            b.getDays(), b.getStatus(),
            subtotal, tax, svcCharge, finalTotal
        );
    }

    private void checkMongoConnection() {
        try {
            Class.forName("com.mongodb.client.MongoClients");
            mongoAvailable = true;
            lblMongoStatus.setText("<html>MongoDB: <font color='#2e7d32'><b>CONNECTED</b></font> (Driver Found)</html>");
        } catch (ClassNotFoundException e) {
            mongoAvailable = false;
            lblMongoStatus.setText("<html>MongoDB: <font color='#f57c00'><b>IN-MEMORY MODE</b></font> (No driver — using local storage)</html>");
        }
    }

    private void loadSampleData() {
        bookingList.add(new Booking("Rahul Sharma", "9876543210", "Deluxe", 101, 2, 5600.0));
        bookingList.add(new Booking("Priya Patel", "9123456789", "Executive Suite", 102, 3, 15000.0));
    }

    private void generateAndOpenHtml() {
        File htmlFile = new File("hotel.html");
        try (FileWriter fw = new FileWriter(htmlFile)) {
            fw.write(getHtmlContent());
            JOptionPane.showMessageDialog(this, "hotel.html generated successfully!\nOpening in browser...");
            if (Desktop.isDesktopSupported()) Desktop.getDesktop().open(htmlFile);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error generating HTML: " + ex.getMessage());
        }
    }

    private String getHtmlContent() {
        return "<!DOCTYPE html>\n<html lang='en'>\n<head>\n"
            + "<meta charset='UTF-8'><meta name='viewport' content='width=device-width,initial-scale=1'>\n"
            + "<title>Grand Luxury Hotel Portal</title>\n<style>\n"
            + "*{box-sizing:border-box;margin:0;padding:0}\n"
            + "body{font-family:'Segoe UI',Tahoma,sans-serif;background:#f4f6f9;color:#333;line-height:1.6}\n"
            + "header{background:linear-gradient(135deg,#1e3c72,#2a5298);color:#fff;padding:50px 20px;text-align:center}\n"
            + "header h1{font-size:2.4rem;margin-bottom:8px}\n"
            + "header p{opacity:.85;font-size:1.1rem}\n"
            + ".container{max-width:1040px;margin:30px auto;padding:0 20px}\n"
            + ".grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(290px,1fr));gap:24px;margin:30px 0}\n"
            + ".card{background:#fff;border-radius:14px;padding:28px;box-shadow:0 6px 20px rgba(0,0,0,.08);text-align:center;transition:transform .3s,box-shadow .3s}\n"
            + ".card:hover{transform:translateY(-6px);box-shadow:0 12px 28px rgba(0,0,0,.12)}\n"
            + ".card h3{color:#1e3c72;margin:12px 0 8px;font-size:1.4rem}\n"
            + ".badge{display:inline-block;padding:4px 14px;border-radius:20px;font-size:.8rem;font-weight:700;color:#fff}\n"
            + ".price{font-size:1.8rem;font-weight:700;color:#2e7d32;margin:12px 0}\n"
            + ".features{color:#757575;font-size:.92rem}\n"
            + ".applet-section{background:#fff;border-radius:14px;padding:30px;box-shadow:0 6px 20px rgba(0,0,0,.08);margin-top:30px;text-align:center}\n"
            + ".applet-box{border:2px dashed #90caf9;border-radius:10px;padding:24px;background:#fafafa;margin-top:16px}\n"
            + "footer{text-align:center;padding:28px;color:#999;border-top:1px solid #e0e0e0;margin-top:50px;background:#fff}\n"
            + "</style>\n</head>\n<body>\n"
            + "<header><h1>Grand Luxury Hotel &amp; Resort</h1><p>Java GUI &amp; HTML/CSS Presentation Portal</p></header>\n"
            + "<div class='container'>\n<h2 style='text-align:center;color:#1e3c72;margin-top:10px'>Our Accommodations</h2>\n"
            + "<div class='grid'>\n"
            + "<div class='card'><span class='badge' style='background:#42a5f5'>Standard</span>"
            + "<h3>Standard Room</h3><p class='features'>Twin/Double Bed • High-Speed Wi-Fi • Work Desk</p>"
            + "<div class='price'>₹1,500<span style='font-size:1rem;color:#777'> / night</span></div></div>\n"
            + "<div class='card'><span class='badge' style='background:#66bb6a'>Popular</span>"
            + "<h3>Deluxe Room</h3><p class='features'>Balcony View • King Bed • Complimentary Breakfast</p>"
            + "<div class='price'>₹2,800<span style='font-size:1rem;color:#777'> / night</span></div></div>\n"
            + "<div class='card'><span class='badge' style='background:#ffa726'>VIP</span>"
            + "<h3>Executive Suite</h3><p class='features'>Jacuzzi • Living Area • 24/7 Personal Butler</p>"
            + "<div class='price'>₹5,000<span style='font-size:1rem;color:#777'> / night</span></div></div>\n"
            + "</div>\n"
            + "<div class='applet-section'><h2>Java Applet Container</h2><p>For academic/lab manual submissions</p>"
            + "<div class='applet-box'><applet code='HotelManagement.class' width='800' height='500'>"
            + "<p style='color:#c62828;font-weight:700'>Modern browsers cannot run Java Applets. "
            + "Run <code>java HotelManagement</code> from your terminal.</p></applet></div></div>\n"
            + "</div>\n<footer><p>&copy; 2026 Grand Luxury Hotel — Java &amp; HTML Project</p></footer>\n"
            + "</body>\n</html>";
    }

    // ═══════════════════ MAIN ═══════════════════
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HotelManagement app = new HotelManagement();
            app.setVisible(true);
        });
    }
}
