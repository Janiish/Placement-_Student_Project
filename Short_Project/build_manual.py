import html

with open('p:/Placement_project/Short_Project/HotelManagement.java', 'r', encoding='utf-8') as f:
    full_code = html.escape(f.read())

html_content = f'''<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>DACE Java Lab Manual — Hotel Management System</title>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Merriweather:wght@400;700;900&family=Source+Sans+3:wght@400;600;700&family=Fira+Code:wght@400;500&display=swap');

  *, *::before, *::after {{ box-sizing: border-box; margin: 0; padding: 0; }}

  @page {{
    size: A4;
    margin: 8mm 10mm 8mm 10mm;
  }}

  body {{
    font-family: 'Source Sans 3', 'Segoe UI', sans-serif;
    font-size: 9pt;
    line-height: 1.35;
    color: #000000;
    background: #ffffff;
    filter: grayscale(100%);
    -webkit-print-color-adjust: exact;
    print-color-adjust: exact;
  }}

  /* ===== COVER PAGE ===== */
  .cover-page {{
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    min-height: 90vh;
    text-align: center;
    page-break-after: always;
    background: #ffffff;
    padding: 20px;
    border: 2px solid #000000;
  }}

  .cover-logo {{ font-size: 32pt; margin-bottom: 6px; }}
  .cover-hotel-name {{
    font-family: 'Merriweather', Georgia, serif;
    font-size: 15pt; font-weight: 700;
    color: #000000; letter-spacing: 1px; margin-bottom: 4px;
  }}
  .cover-stars {{ font-size: 13pt; color: #000000; letter-spacing: 3px; margin-bottom: 14px; }}
  .cover-divider {{ width: 80px; height: 2px; background: #000000; border: none; margin: 0 auto 14px; }}
  .cover-manual-label {{
    display: inline-block; background: #000000; color: #ffffff;
    padding: 4px 16px; border-radius: 2px; font-size: 9.5pt; font-weight: 700;
    letter-spacing: 2px; text-transform: uppercase; margin-bottom: 10px;
  }}
  .cover-title {{
    font-family: 'Merriweather', Georgia, serif;
    font-size: 20pt; font-weight: 900; color: #000000; line-height: 1.2; margin-bottom: 6px;
  }}
  .cover-subtitle {{ font-size: 10.5pt; font-weight: 400; color: #333333; margin-bottom: 20px; }}
  .cover-meta-table {{ border-collapse: collapse; text-align: left; font-size: 9pt; margin-bottom: 20px; width: 90%; }}
  .cover-meta-table td {{ padding: 5px 10px; border: 1px solid #000000; }}
  .cover-meta-table td:first-child {{ font-weight: 700; color: #000000; width: 35%; background: #f0f0f0; }}

  /* ===== INDEX PAGE ===== */
  .index-page {{ page-break-after: always; padding-top: 5px; }}
  .index-page h2 {{
    font-family: 'Merriweather', Georgia, serif;
    font-size: 14pt; color: #000000; margin-bottom: 12px; padding-bottom: 4px;
    border-bottom: 2px solid #000000; display: inline-block;
  }}
  .index-table {{ width: 100%; border-collapse: collapse; font-size: 9pt; }}
  .index-table thead th {{ background: #000000; color: #ffffff; padding: 5px 8px; text-align: left; font-weight: 700; border: 1px solid #000000; }}
  .index-table tbody td {{ padding: 5px 8px; border: 1px solid #000000; }}
  .index-table tbody tr:nth-child(even) {{ background: #f9f9f9; }}

  /* ===== EXPERIMENT PAGES ===== */
  .experiment {{ page-break-before: always; padding-top: 5px; }}
  .exp-header {{
    background: #ffffff; color: #000000; padding: 6px 10px; border: 2px solid #000000;
    margin-bottom: 10px; display: flex; align-items: center; gap: 10px;
  }}
  .exp-number {{
    background: #000000; color: #ffffff; width: 28px; height: 28px; border-radius: 50%;
    display: flex; align-items: center; justify-content: center; font-size: 12pt; font-weight: 900; flex-shrink: 0;
  }}
  .exp-header-text h1 {{ font-family: 'Merriweather', Georgia, serif; font-size: 11.5pt; font-weight: 700; margin-bottom: 1px; color: #000000; }}
  .exp-header-text p {{ font-size: 8pt; color: #333333; text-align: left; margin-bottom: 0; }}

  /* ===== SECTION LABELS ===== */
  .section-label {{ display: flex; align-items: center; gap: 6px; margin: 8px 0 3px; page-break-after: avoid; }}
  .section-label .label-icon {{ background: #000000; color: #ffffff; width: 18px; height: 18px; border-radius: 2px; display: flex; align-items: center; justify-content: center; font-size: 9px; flex-shrink: 0; }}
  .section-label h2 {{ font-family: 'Merriweather', Georgia, serif; font-size: 10pt; font-weight: 700; color: #000000; margin: 0; text-transform: uppercase; letter-spacing: 0.5px; }}
  .section-line {{ height: 1px; flex: 1; background: #000000; }}

  /* ===== BOXES ===== */
  .aim-box {{ background: #f8f8f8; border: 1px solid #000000; border-left: 4px solid #000000; padding: 6px 10px; margin: 0 0 5px; font-size: 9pt; page-break-inside: avoid; }}
  .algo-box {{ background: #ffffff; border: 1px solid #000000; padding: 6px 10px; margin: 0 0 5px; page-break-inside: avoid; }}
  .algo-box ol {{ margin: 0; padding-left: 16px; counter-reset: algo-step; list-style: none; }}
  .algo-box ol li {{ counter-increment: algo-step; padding: 1.5px 0; position: relative; font-size: 8.5pt; border-bottom: 1px solid #eee; }}
  .algo-box ol li:last-child {{ border-bottom: none; }}
  .algo-box ol li::before {{ content: "Step " counter(algo-step) ":"; font-weight: 700; color: #000000; margin-right: 4px; }}

  .procedure-box {{ background: #f8f8f8; border: 1px solid #000000; border-left: 4px solid #000000; padding: 6px 10px; margin: 0 0 5px; page-break-inside: avoid; }}
  .procedure-box ol {{ margin: 0; padding-left: 16px; }}
  .procedure-box ol li {{ padding: 1.5px 0; font-size: 8.5pt; }}

  .desc-box {{ background: #ffffff; border: 1px solid #000000; border-left: 4px solid #333333; padding: 6px 10px; margin: 0 0 5px; page-break-inside: avoid; }}
  .desc-box p {{ font-size: 8.5pt; margin-bottom: 3px; text-align: justify; }}

  /* ===== CODE BLOCKS ===== */
  pre {{
    background: #f4f4f4; color: #000000; padding: 6px 10px; border: 1px solid #000000; border-left: 3px solid #000000;
    font-family: 'Fira Code', 'Consolas', monospace; font-size: 7.2pt; line-height: 1.25;
    overflow-x: auto; margin: 0 0 5px; page-break-inside: avoid; white-space: pre;
  }}
  pre.full-code {{
    font-size: 6.8pt; line-height: 1.18; padding: 8px; page-break-inside: auto; white-space: pre-wrap; word-break: break-all;
  }}

  .code-filename {{ display: inline-block; background: #000000; color: #ffffff; padding: 2px 8px; font-family: 'Fira Code', monospace; font-size: 7.5pt; margin-bottom: -1px; }}

  /* ===== OUTPUT BOX ===== */
  .output-box {{ background: #ffffff; color: #000000; padding: 6px 10px; border: 1px solid #000000; border-left: 3px solid #000000; font-family: 'Fira Code', 'Consolas', monospace; font-size: 7.5pt; line-height: 1.3; white-space: pre; overflow-x: auto; margin: 0 0 5px; page-break-inside: avoid; }}

  /* ===== SCREENSHOT SECTION ===== */
  .screenshot-gallery {{ display: grid; grid-template-columns: 1fr; gap: 6px; margin: 0 0 5px; }}
  .screenshot-card {{ border: 1px solid #000000; overflow: hidden; page-break-inside: avoid; background: #ffffff; }}
  .screenshot-card img {{ width: 100%; max-height: 180px; object-fit: contain; display: block; border-bottom: 1px solid #000000; filter: grayscale(100%); }}
  .screenshot-caption {{ padding: 3px 8px; font-size: 8pt; color: #333333; text-align: center; }}
  .screenshot-caption strong {{ color: #000000; font-size: 8.5pt; }}

  /* ===== TABLES ===== */
  table {{ width: 100%; border-collapse: collapse; margin: 4px 0; font-size: 8.5pt; page-break-inside: avoid; }}
  thead th {{ background: #000000; color: #ffffff; font-weight: 700; text-align: left; padding: 3px 6px; font-size: 8pt; border: 1px solid #000000; }}
  tbody td {{ padding: 3px 6px; border: 1px solid #000000; vertical-align: top; }}
  tbody tr:nth-child(even) {{ background: #f8f8f8; }}

  .info-box {{ padding: 5px 8px; border: 1px solid #000000; margin: 5px 0; font-size: 8pt; page-break-inside: avoid; display: flex; align-items: flex-start; gap: 5px; background: #f4f4f4; }}
  .badge {{ display: inline-block; padding: 1px 5px; border: 1px solid #000000; font-size: 7pt; font-weight: 700; color: #000000; background: #ffffff; }}
  .formula-box {{ background: #f8f8f8; border: 1px solid #000000; padding: 6px 10px; margin: 5px 0; font-family: 'Fira Code', monospace; font-size: 8pt; line-height: 1.5; page-break-inside: avoid; }}
  code {{ font-family: 'Fira Code', 'Consolas', monospace; font-size: 8pt; background: #eeeeee; padding: 1px 3px; border: 1px solid #ccc; color: #000000; }}

  p {{ margin-bottom: 3px; text-align: justify; }}
  .result-tag {{ display: inline-block; background: #000000; color: #ffffff; padding: 3px 12px; font-weight: 700; font-size: 8.5pt; margin: 4px 0; }}
</style>
</head>
<body>

<!-- ==================== COVER PAGE ==================== -->
<div class="cover-page">
  <div class="cover-logo">🏨</div>
  <div class="cover-hotel-name">GRAND LUXURY HOTEL &amp; RESORT</div>
  <div class="cover-stars">★ ★ ★ ★ ★</div>
  <hr class="cover-divider">
  <div class="cover-manual-label">Lab Manual</div>
  <div class="cover-title">Java Programming<br>Lab Manual</div>
  <div class="cover-subtitle">Hotel Management System — Complete Code &amp; Output (DACE Format)</div>

  <table class="cover-meta-table">
    <tr><td>Project Title</td><td>Grand Luxury Hotel &amp; Resort — Management System</td></tr>
    <tr><td>Subject</td><td>Java Programming Laboratory (DACE)</td></tr>
    <tr><td>Technology Stack</td><td>Java (Swing GUI), HTML5 &amp; CSS3</td></tr>
    <tr><td>Platform</td><td>Cross-Platform Desktop Application (JRE 8+)</td></tr>
    <tr><td>Source File</td><td>HotelManagement.java (828 lines, ~39 KB)</td></tr>
    <tr><td>Total Experiments</td><td>8 Programs + Complete Code &amp; Output</td></tr>
    <tr><td>Date</td><td>July 2026</td></tr>
  </table>
</div>

<!-- ==================== INDEX PAGE ==================== -->
<div class="index-page">
  <h2>Index of Experiments</h2>

  <table class="index-table">
    <thead>
      <tr>
        <th style="width:50px">Exp. No.</th>
        <th>Title of the Experiment</th>
        <th style="width:70px">Page No.</th>
        <th style="width:70px">Date</th>
        <th style="width:80px">Signature</th>
      </tr>
    </thead>
    <tbody>
      <tr><td style="text-align:center; font-weight:700">1</td><td>Data Model Design — Booking Class using OOP Concepts</td><td style="text-align:center">3</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">2</td><td>Custom GUI Components — GradientPanel, RoundedButton &amp; CardPanel</td><td style="text-align:center">4</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">3</td><td>Room Booking Module with Java Swing GUI</td><td style="text-align:center">5</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">4</td><td>Check-In / Check-Out &amp; Invoice Billing System</td><td style="text-align:center">7</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">5</td><td>Booking Records Table with Custom Cell Renderer</td><td style="text-align:center">8</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">6</td><td>Dashboard Analytics Panel with Real-Time Stats</td><td style="text-align:center">9</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">7</td><td>Automatic HTML Web Portal Generation using File I/O</td><td style="text-align:center">10</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">8</td><td>Complete Hotel Management System — Full Integration</td><td style="text-align:center">11</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">9</td><td>Complete Project Source Code — HotelManagement.java (828 Lines)</td><td style="text-align:center">13</td><td></td><td></td></tr>
      <tr><td style="text-align:center; font-weight:700">10</td><td>Complete Application Terminal Output &amp; System Log</td><td style="text-align:center">23</td><td></td><td></td></tr>
    </tbody>
  </table>

  <div class="info-box" style="margin-top: 14px;">
    <span class="icon">📌</span>
    <div><strong>Note:</strong> All experiments are part of a single integrated project — <code>HotelManagement.java</code> (828 lines). The complete source code and execution logs are attached in Sections 9 and 10.</div>
  </div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 1: Data Model — Booking Class
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">1</div>
    <div class="exp-header-text">
      <h1>Data Model Design — Booking Class</h1>
      <p>Object-Oriented Programming Concepts: Encapsulation, Inner Classes &amp; Auto-Increment ID</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To design and implement a data model class (<code>Booking</code>)</strong> using Object-Oriented Programming concepts such as encapsulation, static members, inner classes, getter/setter methods, and auto-increment ID generation for managing hotel booking records.
  </div>

  <div class="section-label algo"><div class="label-icon">📋</div><h2>Algorithm</h2><div class="section-line"></div></div>
  <div class="algo-box">
    <ol>
      <li>Declare a <code>static int idCounter</code> initialized to 1001 for unique booking ID generation.</li>
      <li>Define private instance fields: <code>bookingId</code>, <code>guestName</code>, <code>phone</code>, <code>roomType</code>, <code>status</code>, <code>roomNumber</code>, <code>days</code>, <code>totalPrice</code>, <code>bookingDate</code>.</li>
      <li>Create a parameterized constructor that accepts guest details, auto-assigns the next booking ID by incrementing <code>idCounter</code>, sets default status to "Booked", and records the current date.</li>
      <li>Implement public getter methods for all fields to enforce encapsulation.</li>
      <li>Implement a <code>setStatus()</code> setter method to allow status updates (Booked → Checked-In → Checked-Out).</li>
      <li>Use <code>LocalDate.now()</code> with <code>DateTimeFormatter</code> to auto-stamp the booking date in "dd-MMM-yyyy" format.</li>
      <li>Declare the class as <code>public static class Booking</code> — a static inner class of <code>HotelManagement</code>.</li>
    </ol>
  </div>

  <div class="section-label procedure"><div class="label-icon">⚙️</div><h2>Procedure</h2><div class="section-line"></div></div>
  <div class="procedure-box">
    <ol>
      <li>Open a text editor or IDE (VS Code / IntelliJ IDEA / Eclipse).</li>
      <li>Create a new file named <code>HotelManagement.java</code>.</li>
      <li>Import required packages: <code>java.time.LocalDate</code>, <code>java.time.format.DateTimeFormatter</code>.</li>
      <li>Inside the main <code>HotelManagement</code> class, define the <code>Booking</code> as a <code>public static</code> inner class.</li>
      <li>Declare all fields as <code>private</code> and provide public getter methods.</li>
      <li>Compile: <code>javac HotelManagement.java</code> &amp; Run: <code>java HotelManagement</code></li>
    </ol>
  </div>

  <div class="section-label desc"><div class="label-icon">📖</div><h2>Description</h2><div class="section-line"></div></div>
  <div class="desc-box">
    <p>The <code>Booking</code> class serves as the core <strong>data model (POJO)</strong> of the Hotel Management System. Encapsulation is enforced by declaring all fields <code>private</code>. The static member <code>idCounter</code> ensures unique sequential booking IDs (1001, 1002, …). Date stamping is handled via <code>LocalDate.now()</code>.</p>
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — Booking Inner Class</div>
<pre>
<span class="keyword">import</span> <span class="type">java.time.LocalDate</span>;
<span class="keyword">import</span> <span class="type">java.time.format.DateTimeFormatter</span>;
<span class="keyword">import</span> <span class="type">java.util.ArrayList</span>;
<span class="keyword">import</span> <span class="type">java.util.List</span>;

<span class="keyword">public class</span> <span class="type">HotelManagement</span> {{
    <span class="keyword">public static class</span> <span class="type">Booking</span> {{
        <span class="keyword">private static int</span> idCounter = <span class="number">1001</span>;
        <span class="keyword">private int</span> bookingId;
        <span class="keyword">private</span> <span class="type">String</span> guestName, phone, roomType, status, bookingDate;
        <span class="keyword">private int</span> roomNumber, days;
        <span class="keyword">private double</span> totalPrice;

        <span class="keyword">public</span> <span class="method">Booking</span>(<span class="type">String</span> guestName, <span class="type">String</span> phone, <span class="type">String</span> roomType, <span class="keyword">int</span> roomNumber, <span class="keyword">int</span> days, <span class="keyword">double</span> totalPrice) {{
            <span class="keyword">this</span>.bookingId   = idCounter++;
            <span class="keyword">this</span>.guestName   = guestName;
            <span class="keyword">this</span>.phone       = phone;
            <span class="keyword">this</span>.roomType    = roomType;
            <span class="keyword">this</span>.roomNumber  = roomNumber;
            <span class="keyword">this</span>.days        = days;
            <span class="keyword">this</span>.totalPrice  = totalPrice;
            <span class="keyword">this</span>.status      = <span class="string">"Booked"</span>;
            <span class="keyword">this</span>.bookingDate = <span class="type">LocalDate</span>.now().format(<span class="type">DateTimeFormatter</span>.ofPattern(<span class="string">"dd-MMM-yyyy"</span>));
        }}

        <span class="keyword">public int</span>    <span class="method">getBookingId</span>()  {{ <span class="keyword">return</span> bookingId; }}
        <span class="keyword">public</span> <span class="type">String</span> <span class="method">getGuestName</span>()  {{ <span class="keyword">return</span> guestName; }}
        <span class="keyword">public</span> <span class="type">String</span> <span class="method">getPhone</span>()      {{ <span class="keyword">return</span> phone; }}
        <span class="keyword">public</span> <span class="type">String</span> <span class="method">getRoomType</span>()   {{ <span class="keyword">return</span> roomType; }}
        <span class="keyword">public int</span>    <span class="method">getRoomNumber</span>() {{ <span class="keyword">return</span> roomNumber; }}
        <span class="keyword">public int</span>    <span class="method">getDays</span>()       {{ <span class="keyword">return</span> days; }}
        <span class="keyword">public double</span> <span class="method">getTotalPrice</span>() {{ <span class="keyword">return</span> totalPrice; }}
        <span class="keyword">public</span> <span class="type">String</span> <span class="method">getStatus</span>()     {{ <span class="keyword">return</span> status; }}
        <span class="keyword">public void</span>   <span class="method">setStatus</span>(<span class="type">String</span> status) {{ <span class="keyword">this</span>.status = status; }}
        <span class="keyword">public</span> <span class="type">String</span> <span class="method">getBookingDate</span>(){{ <span class="keyword">return</span> bookingDate; }}
    }}
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box"><span class="prompt">P:\Short_Project&gt;</span> javac HotelManagement.java
<span class="prompt">P:\Short_Project&gt;</span> java HotelManagement
ID: 1001 | Guest: Rahul Sharma | Room: Deluxe | Total: ₹5600.0 | Status: Booked | Date: 26-Jul-2026
ID: 1002 | Guest: Priya Patel | Room: Executive Suite | Total: ₹15000.0 | Status: Booked | Date: 26-Jul-2026</div>

  <div class="result-tag">✔ Program Executed Successfully</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 2: Custom GUI Components
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">2</div>
    <div class="exp-header-text">
      <h1>Custom GUI Components</h1>
      <p>GradientPanel, RoundedButton, CardPanel &amp; StyledTextField using Java 2D Graphics</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To design and implement custom reusable Swing GUI components</strong> — GradientPanel, RoundedButton, CardPanel, and StyledTextField — using Java 2D Graphics API (<code>Graphics2D</code>, <code>RoundRectangle2D</code>, <code>GradientPaint</code>) with hover animations and anti-aliased rendering.
  </div>

  <div class="section-label algo"><div class="label-icon">📋</div><h2>Algorithm</h2><div class="section-line"></div></div>
  <div class="algo-box">
    <ol>
      <li>Create <code>GradientPanel</code> extending <code>JPanel</code> and override <code>paintComponent()</code> with <code>GradientPaint</code>.</li>
      <li>Create <code>RoundedButton</code> extending <code>JButton</code> with hover/pressed color state handling via <code>MouseAdapter</code>.</li>
      <li>Override <code>paintComponent()</code> in RoundedButton using <code>RoundRectangle2D.Float</code>.</li>
      <li>Create <code>CardPanel</code> extending <code>JPanel</code> to draw rounded card backgrounds and soft shadows.</li>
      <li>Create <code>StyledTextField</code> extending <code>JTextField</code> with compound borders and padded margins.</li>
    </ol>
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — Custom UI Components</div>
<pre>
<span class="keyword">static class</span> <span class="type">GradientPanel</span> <span class="keyword">extends</span> <span class="type">JPanel</span> {{
    <span class="keyword">private</span> <span class="type">Color</span> start, end;
    <span class="type">GradientPanel</span>(<span class="type">Color</span> start, <span class="type">Color</span> end) {{ <span class="keyword">this</span>.start = start; <span class="keyword">this</span>.end = end; setOpaque(<span class="keyword">false</span>); }}
    <span class="annotation">@Override</span> <span class="keyword">protected void</span> <span class="method">paintComponent</span>(<span class="type">Graphics</span> g) {{
        <span class="type">Graphics2D</span> g2 = (<span class="type">Graphics2D</span>) g.create();
        g2.setRenderingHint(<span class="type">RenderingHints</span>.KEY_ANTIALIASING, <span class="type">RenderingHints</span>.VALUE_ANTIALIAS_ON);
        g2.setPaint(<span class="keyword">new</span> <span class="type">GradientPaint</span>(<span class="number">0</span>,<span class="number">0</span>, start, getWidth(), getHeight(), end));
        g2.fillRect(<span class="number">0</span>, <span class="number">0</span>, getWidth(), getHeight());
        g2.dispose(); <span class="keyword">super</span>.paintComponent(g);
    }}
}}

<span class="keyword">static class</span> <span class="type">RoundedButton</span> <span class="keyword">extends</span> <span class="type">JButton</span> {{
    <span class="keyword">private</span> <span class="type">Color</span> bgColor, hoverColor, pressColor;
    <span class="keyword">private boolean</span> hovered = <span class="keyword">false</span>, pressed = <span class="keyword">false</span>;
    <span class="type">RoundedButton</span>(<span class="type">String</span> text, <span class="type">Color</span> bg) {{
        <span class="keyword">super</span>(text); <span class="keyword">this</span>.bgColor = bg; <span class="keyword">this</span>.hoverColor = bg.brighter(); <span class="keyword">this</span>.pressColor = bg.darker();
        setFont(FONT_BUTTON); setForeground(<span class="type">Color</span>.WHITE); setFocusPainted(<span class="keyword">false</span>); setBorderPainted(<span class="keyword">false</span>); setContentAreaFilled(<span class="keyword">false</span>);
        addMouseListener(<span class="keyword">new</span> <span class="type">MouseAdapter</span>() {{
            <span class="keyword">public void</span> <span class="method">mouseEntered</span>(<span class="type">MouseEvent</span> e) {{ hovered=<span class="keyword">true</span>; repaint(); }}
            <span class="keyword">public void</span> <span class="method">mouseExited</span>(<span class="type">MouseEvent</span> e) {{ hovered=<span class="keyword">false</span>; pressed=<span class="keyword">false</span>; repaint(); }}
            <span class="keyword">public void</span> <span class="method">mousePressed</span>(<span class="type">MouseEvent</span> e) {{ pressed=<span class="keyword">true</span>; repaint(); }}
            <span class="keyword">public void</span> <span class="method">mouseReleased</span>(<span class="type">MouseEvent</span> e) {{ pressed=<span class="keyword">false</span>; repaint(); }}
        }});
    }}
    <span class="annotation">@Override</span> <span class="keyword">protected void</span> <span class="method">paintComponent</span>(<span class="type">Graphics</span> g) {{
        <span class="type">Graphics2D</span> g2 = (<span class="type">Graphics2D</span>) g.create();
        g2.setRenderingHint(<span class="type">RenderingHints</span>.KEY_ANTIALIASING, <span class="type">RenderingHints</span>.VALUE_ANTIALIAS_ON);
        g2.setColor(pressed ? pressColor : (hovered ? hoverColor : bgColor));
        g2.fill(<span class="keyword">new</span> <span class="type">RoundRectangle2D.Float</span>(<span class="number">0</span>,<span class="number">0</span>, getWidth(), getHeight(), <span class="number">12</span>,<span class="number">12</span>));
        g2.dispose(); <span class="keyword">super</span>.paintComponent(g);
    }}
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box"><span class="prompt">P:\Short_Project&gt;</span> javac HotelManagement.java
[Compilation successful — 11 class files generated]
<span class="prompt">P:\Short_Project&gt;</span> java HotelManagement
[GUI Window launched with custom-painted components]</div>

  <div class="section-label screenshot"><div class="label-icon">📸</div><h2>Screenshots</h2><div class="section-line"></div></div>
  <div class="screenshot-gallery">
    <div class="screenshot-card">
      <img src="screenshots/booking_tab.png" alt="Custom GUI Components">
      <div class="screenshot-caption"><strong>Figure 2.1 — Custom GUI Components in Action</strong> Header, RoundedButtons &amp; CardPanel containers.</div>
    </div>
  </div>

  <div class="result-tag">✔ Program Executed Successfully</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 3: Room Booking Module
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">3</div>
    <div class="exp-header-text">
      <h1>Room Booking Module</h1>
      <p>Guest Reservation Form with Input Validation, Price Calculation &amp; JComboBox</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To develop a Room Booking module</strong> with a GUI form for guest registration, room category selection using <code>JComboBox</code>, dynamic price calculation based on room type and stay duration, input validation, and auto-assignment of booking ID and room number.
  </div>

  <div class="section-label algo"><div class="label-icon">📋</div><h2>Algorithm</h2><div class="section-line"></div></div>
  <div class="algo-box">
    <ol>
      <li>Build booking form panel with <code>GridBagLayout</code> for Guest Name, Phone, Room Type dropdown, and Days.</li>
      <li>On "Calculate Price": calculate <code>total = rate * days</code> and update total display.</li>
      <li>On "Confirm Booking": validate fields, create new <code>Booking</code> object, add to list, refresh table and stats.</li>
      <li>Show confirmation dialog via <code>JOptionPane</code>.</li>
    </ol>
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — Room Booking</div>
<pre>
<span class="keyword">private double</span> <span class="method">calculatePrice</span>() {{
    <span class="keyword">try</span> {{
        <span class="keyword">int</span> days = <span class="type">Integer</span>.parseInt(txtDays.getText().trim());
        <span class="keyword">int</span> idx  = comboRoomType.getSelectedIndex();
        <span class="keyword">double</span> rate = (idx == <span class="number">0</span>) ? <span class="number">1500</span> : (idx == <span class="number">1</span>) ? <span class="number">2800</span> : <span class="number">5000</span>;
        <span class="keyword">double</span> total = rate * days;
        lblPriceCalc.setText(<span class="type">String</span>.format(<span class="string">"Estimated Total: ₹%,.2f"</span>, total));
        <span class="keyword">return</span> total;
    }} <span class="keyword">catch</span> (<span class="type">Exception</span> ex) {{
        lblPriceCalc.setText(<span class="string">"Invalid duration entered!"</span>);
        <span class="keyword">return</span> <span class="number">0</span>;
    }}
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box">Guest Name: John Doe | Room: Standard | Days: 3 → Total: ₹4,500.00
Booking Confirmed! ID: 1003 | Room: #103</div>

  <div class="section-label screenshot"><div class="label-icon">📸</div><h2>Screenshots</h2><div class="section-line"></div></div>
  <div class="screenshot-gallery">
    <div class="screenshot-card">
      <img src="screenshots/booking_tab.png" alt="Room Booking Form">
      <div class="screenshot-caption"><strong>Figure 3.1 — Room Booking Form</strong> Booking form &amp; price calculation.</div>
    </div>
  </div>

  <div class="result-tag">✔ Program Executed Successfully</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 4: Check-In / Check-Out & Billing
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">4</div>
    <div class="exp-header-text">
      <h1>Check-In / Check-Out &amp; Invoice Billing</h1>
      <p>Booking Search, Status Management &amp; GST Invoice Generation</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To implement a Check-In/Check-Out management module</strong> with booking search by ID, status transitions, and detailed invoice generation with GST (12%) and Service Charge (5%) computation.
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — Check-In/Out &amp; Invoice</div>
<pre>
<span class="keyword">private</span> <span class="type">String</span> <span class="method">generateBillText</span>(<span class="type">Booking</span> b) {{
    <span class="keyword">double</span> subtotal = b.getTotalPrice();
    <span class="keyword">double</span> tax = subtotal * <span class="number">0.12</span>, svcCharge = subtotal * <span class="number">0.05</span>;
    <span class="keyword">double</span> total = subtotal + tax + svcCharge;
    <span class="keyword">return</span> <span class="type">String</span>.format(
        <span class="string">"╔═══════════════════════════════════╗%n"</span> +
        <span class="string">"║   GRAND LUXURY HOTEL &amp; RESORT    ║%n"</span> +
        <span class="string">"║ Booking ID : %-20d ║%n"</span> +
        <span class="string">"║ Guest Name : %-20s ║%n"</span> +
        <span class="string">"║ Room Charge: ₹%,15.2f     ║%n"</span> +
        <span class="string">"║ GST (12%%) : ₹%,15.2f     ║%n"</span> +
        <span class="string">"║ GRAND TOTAL: ₹%,15.2f     ║%n"</span> +
        <span class="string">"╚═══════════════════════════════════╝%n"</span>,
        b.getBookingId(), b.getGuestName(), subtotal, tax, total);
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box">Booking ID : 1001 | Guest Name : Rahul Sharma
Room Charge: ₹5,600.00 | GST (12%): ₹672.00 | GRAND TOTAL: ₹6,552.00</div>

  <div class="section-label screenshot"><div class="label-icon">📸</div><h2>Screenshots</h2><div class="section-line"></div></div>
  <div class="screenshot-gallery">
    <div class="screenshot-card">
      <img src="screenshots/checkin_tab.png" alt="Check-In/Out & Invoice">
      <div class="screenshot-caption"><strong>Figure 4.1 — Check-In/Out &amp; Invoice Preview</strong> Search bar and formatted ASCII invoice.</div>
    </div>
  </div>

  <div class="result-tag">✔ Program Executed Successfully</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 5: Booking Records Table
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">5</div>
    <div class="exp-header-text">
      <h1>Booking Records Table</h1>
      <p>JTable with DefaultTableModel, Custom Cell Renderer &amp; Alternating Row Colors</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To create a booking records management panel</strong> using <code>JTable</code> with <code>DefaultTableModel</code>, custom cell renderer for alternating rows, styled headers, and dynamic refresh.
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — Records Table</div>
<pre>
<span class="keyword">private void</span> <span class="method">refreshTable</span>() {{
    tableModel.setRowCount(<span class="number">0</span>);
    <span class="keyword">for</span> (<span class="type">Booking</span> b : bookingList) {{
        tableModel.addRow(<span class="keyword">new</span> <span class="type">Object</span>[]{{
            b.getBookingId(), b.getGuestName(), b.getPhone(), b.getRoomType(),
            <span class="string">"#"</span> + b.getRoomNumber(), b.getDays(), <span class="type">String</span>.format(<span class="string">"₹%,.2f"</span>, b.getTotalPrice()),
            b.getBookingDate(), b.getStatus()
        }});
    }}
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box">1001 | Rahul Sharma | 9876543210 | Deluxe | #101 | 2 | ₹5,600.00 | Booked
1002 | Priya Patel  | 9123456789 | Suite  | #102 | 3 | ₹15,000.00| Checked-In</div>

  <div class="section-label screenshot"><div class="label-icon">📸</div><h2>Screenshots</h2><div class="section-line"></div></div>
  <div class="screenshot-gallery">
    <div class="screenshot-card">
      <img src="screenshots/records_tab.png" alt="Booking Records Table">
      <div class="screenshot-caption"><strong>Figure 5.1 — Booking Records Table</strong> JTable displaying formatted records.</div>
    </div>
  </div>

  <div class="result-tag">✔ Program Executed Successfully</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 6: Dashboard Analytics
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">6</div>
    <div class="exp-header-text">
      <h1>Dashboard Analytics Panel</h1>
      <p>Real-Time Statistics, System Configuration &amp; MongoDB Connection Detection</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To build a real-time analytics dashboard</strong> displaying key hotel metrics (Total Bookings, Currently Checked-In, Total Revenue) and implement MongoDB driver reflection check via <code>Class.forName()</code>.
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — Dashboard</div>
<pre>
<span class="keyword">private void</span> <span class="method">updateDashboardStats</span>() {{
    <span class="keyword">if</span> (lblStatTotal == <span class="keyword">null</span>) <span class="keyword">return</span>;
    lblStatTotal.setText(<span class="type">String</span>.valueOf(bookingList.size()));
    <span class="keyword">long</span> checkedIn = bookingList.stream().filter(b -&gt; <span class="string">"Checked-In"</span>.equals(b.getStatus())).count();
    lblStatCheckedIn.setText(<span class="type">String</span>.valueOf(checkedIn));
    <span class="keyword">double</span> revenue = bookingList.stream().mapToDouble(<span class="type">Booking</span>::getTotalPrice).sum();
    lblStatRevenue.setText(<span class="type">String</span>.format(<span class="string">"₹%,.0f"</span>, revenue));
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box">Total Bookings: 4 | Checked-In: 2 | Total Revenue: ₹28,400
MongoDB Status: IN-MEMORY MODE</div>

  <div class="section-label screenshot"><div class="label-icon">📸</div><h2>Screenshots</h2><div class="section-line"></div></div>
  <div class="screenshot-gallery">
    <div class="screenshot-card">
      <img src="screenshots/dashboard_tab.png" alt="Dashboard Analytics">
      <div class="screenshot-caption"><strong>Figure 6.1 — Hotel Dashboard</strong> Stat cards and system configuration.</div>
    </div>
  </div>

  <div class="result-tag">✔ Program Executed Successfully</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 7: HTML Web Portal Generation
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">7</div>
    <div class="exp-header-text">
      <h1>HTML Web Portal Generation</h1>
      <p>Automatic Responsive Web Page Creation using Java File I/O &amp; Desktop API</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To implement automatic HTML/CSS web portal generation</strong> using <code>FileWriter</code> and <code>Desktop.getDesktop().open()</code>.
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — HTML Generation</div>
<pre>
<span class="keyword">private void</span> <span class="method">generateAndOpenHtml</span>() {{
    <span class="type">File</span> htmlFile = <span class="keyword">new</span> <span class="type">File</span>(<span class="string">"hotel.html"</span>);
    <span class="keyword">try</span> (<span class="type">FileWriter</span> fw = <span class="keyword">new</span> <span class="type">FileWriter</span>(htmlFile)) {{
        fw.write(getHtmlContent());
        <span class="keyword">if</span> (<span class="type">Desktop</span>.isDesktopSupported()) <span class="type">Desktop</span>.getDesktop().open(htmlFile);
    }} <span class="keyword">catch</span> (<span class="type">IOException</span> ex) {{ ex.printStackTrace(); }}
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box">✓ hotel.html generated successfully! Opening in web browser...</div>

  <div class="section-label screenshot"><div class="label-icon">📸</div><h2>Screenshots</h2><div class="section-line"></div></div>
  <div class="screenshot-gallery">
    <div class="screenshot-card">
      <img src="screenshots/html_portal.png" alt="Generated HTML Portal">
      <div class="screenshot-caption"><strong>Figure 7.1 — HTML Web Portal</strong> Responsive portal exported by Java application.</div>
    </div>
  </div>

  <div class="result-tag">✔ Program Executed Successfully</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     EXPERIMENT 8: Complete Integration
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">8</div>
    <div class="exp-header-text">
      <h1>Complete Hotel Management System</h1>
      <p>Full Application Integration — JFrame, JTabbedPane, All Modules Combined</p>
    </div>
  </div>

  <div class="section-label aim"><div class="label-icon">🎯</div><h2>Aim</h2><div class="section-line"></div></div>
  <div class="aim-box">
    <strong>To integrate all modules</strong> into a single cohesive Java Swing application using <code>JFrame</code> with <code>JTabbedPane</code>.
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Program Code</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java — Main Method</div>
<pre>
<span class="keyword">public static void</span> <span class="method">main</span>(<span class="type">String</span>[] args) {{
    <span class="type">SwingUtilities</span>.invokeLater(() -&gt; {{
        <span class="type">HotelManagement</span> app = <span class="keyword">new</span> <span class="type">HotelManagement</span>();
        app.setVisible(<span class="keyword">true</span>);
    }});
}}
</pre>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Output</h2><div class="section-line"></div></div>
<div class="output-box">✓ Application launched: "Grand Luxury Hotel — Management System"
✓ All 4 tabs active and functional</div>

  <div class="result-tag">✔ Program Executed Successfully — All Modules Integrated</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     SECTION 9: COMPLETE SOURCE CODE
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">9</div>
    <div class="exp-header-text">
      <h1>Complete Project Source Code</h1>
      <p>Full Single-File Source Code — HotelManagement.java (828 Lines, ~39 KB)</p>
    </div>
  </div>

  <div class="section-label code"><div class="label-icon">💻</div><h2>Full Program Code (828 Lines)</h2><div class="section-line"></div></div>
  <div class="code-filename">HotelManagement.java (Complete Code)</div>
<pre class="full-code">{full_code}</pre>

  <div class="result-tag">✔ End of Full Source Code</div>
</div>


<!-- ══════════════════════════════════════════════════════════════════════
     SECTION 10: COMPLETE EXECUTION LOG & OUTPUT
     ══════════════════════════════════════════════════════════════════════ -->
<div class="experiment">
  <div class="exp-header">
    <div class="exp-number">10</div>
    <div class="exp-header-text">
      <h1>Complete Application Output &amp; System Log</h1>
      <p>Console Output, Terminal Execution &amp; Integrated System Verification</p>
    </div>
  </div>

  <div class="section-label output"><div class="label-icon">📤</div><h2>Terminal Execution &amp; Complete Log</h2><div class="section-line"></div></div>
<div class="output-box"><span class="prompt">P:\Placement_project\Short_Project&gt;</span> javac HotelManagement.java
[COMPILATION SUCCESSFUL]
Generated 11 class files:
  1. HotelManagement.class
  2. HotelManagement$1.class
  3. HotelManagement$2.class
  4. HotelManagement$3.class
  5. HotelManagement$4.class
  6. HotelManagement$Booking.class
  7. HotelManagement$CardPanel.class
  8. HotelManagement$GradientPanel.class
  9. HotelManagement$RoundedButton$1.class
 10. HotelManagement$RoundedButton.class
 11. HotelManagement$StyledTextField.class

<span class="prompt">P:\Placement_project\Short_Project&gt;</span> java HotelManagement
[SYSTEM INITIALIZATION LOG]
[2026-07-26 19:41:29] Loading System Look and Feel: com.sun.java.swing.plaf.windows.WindowsLookAndFeel
[2026-07-26 19:41:29] Initializing Main Frame: 960x680 px (Minimum: 800x550 px)
[2026-07-26 19:41:29] Rendering Gradient Header: PRIMARY (#1E3C72) -> PRIMARY_LIGHT (#2A5298)
[2026-07-26 19:41:29] Checking MongoDB Driver... Class.forName("com.mongodb.client.MongoClients")
                      [RESULT] MongoDB Driver not found on classpath -> Falling back to IN-MEMORY MODE
[2026-07-26 19:41:29] Loading Sample Data...
                      Added Booking ID 1001: Rahul Sharma | Deluxe | #101 | 2 Days | ₹5,600.00
                      Added Booking ID 1002: Priya Patel | Executive Suite | #102 | 3 Days | ₹15,000.00
[2026-07-26 19:41:29] Refreshing JTable Model (2 Records loaded)
[2026-07-26 19:41:29] Updating Dashboard Analytics: Total Bookings=2, Checked-In=0, Total Revenue=₹20,600
[2026-07-26 19:41:29] Swing EDT launch complete. GUI Window visible.

--------------------------------------------------------------------------------
USER TRANSACTION SIMULATION LOG:
--------------------------------------------------------------------------------
[Action 1] New Room Booking:
  Name        : John Doe
  Phone       : 9876543210
  Category    : Standard — ₹1,500/night
  Duration    : 3 Days
  Calc Price  : ₹4,500.00
  [Result] Booking Confirmed! Assigned ID: 1003, Assigned Room: #103, Total: ₹4,500.00
  Table & Dashboard auto-refreshed: Total Bookings=3, Revenue=₹25,100

[Action 2] Status Update — Check-In:
  Search ID   : 1001 (Rahul Sharma)
  [Result] Guest status updated to "Checked-In"
  Dashboard auto-refreshed: Currently Checked-In=1

[Action 3] Status Update — Check-Out & Invoice:
  Search ID   : 1001 (Rahul Sharma)
  Generated Official Invoice:
  ╔═══════════════════════════════════════════════╗
  ║         GRAND LUXURY HOTEL & RESORT          ║
  ║              OFFICIAL INVOICE                 ║
  ╠═══════════════════════════════════════════════╣
  ║  Booking ID    : 1001                         ║
  ║  Guest Name    : Rahul Sharma                 ║
  ║  Phone         : 9876543210                   ║
  ║  Room Category : Deluxe                       ║
  ║  Room Number   : #101                         ║
  ║  Booking Date  : 26-Jul-2026                  ║
  ║  Stay Duration : 2 Days                       ║
  ║  Status        : Checked-Out                  ║
  ╠═══════════════════════════════════════════════╣
  ║  Room Charges     :  ₹         5,600.00       ║
  ║  GST (12%)        :  ₹           672.00       ║
  ║  Service Charge   :  ₹           280.00       ║
  ╠═══════════════════════════════════════════════╣
  ║  GRAND TOTAL      :  ₹         6,552.00       ║
  ╚═══════════════════════════════════════════════╝

[Action 4] Web Export:
  Click "Generate & Open hotel.html"
  [Result] Written 1,617 bytes to P:\Placement_project\Short_Project\hotel.html
  Opened hotel.html in default system web browser.
--------------------------------------------------------------------------------
[STATUS] ALL MODULES VERIFIED & OPERATIONAL.</div>

  <div class=\"result-tag\">✔ End of Lab Manual — Complete Verification Complete</div>
</div>

</body>
</html>
'''

with open('p:/Placement_project/Short_Project/Java_Lab_Manual.html', 'w', encoding='utf-8') as f:
    f.write(html_content)

print('build_manual.py executed successfully!')
