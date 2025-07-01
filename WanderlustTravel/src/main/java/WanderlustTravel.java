import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class WanderlustTravel extends GenericServlet {
    public void service(ServletRequest req, ServletResponse res) throws IOException {
        res.setContentType("text/html");
        PrintWriter writer = res.getWriter();
        writer.println("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"UTF-8\">\n" +
                "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "  <title>Wanderlust Travel</title>\n" +
                "  <style>\n" +
                "    * {\n" +
                "      box-sizing: border-box;\n" +
                "      margin: 0;\n" +
                "      padding: 0;\n" +
                "      font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;\n" +
                "    }\n" +
                "\n" +
                "    body {\n" +
                "      background-color: #f2f2f2;\n" +
                "      color: #333;\n" +
                "      scroll-behavior: smooth;\n" +
                "    }\n" +
                "\n" +
                "    nav {\n" +
                "      background-color: rgba(0, 0, 0, 0.6);\n" +
                "      padding: 10px 0;\n" +
                "      position: fixed;\n" +
                "      width: 100%;\n" +
                "      top: 0;\n" +
                "      left: 0;\n" +
                "      text-align: center;\n" +
                "      z-index: 1000;\n" +
                "    }\n" +
                "\n" +
                "    nav a {\n" +
                "      color: white;\n" +
                "      margin: 0 15px;\n" +
                "      text-decoration: none;\n" +
                "      font-weight: bold;\n" +
                "      transition: color 0.3s;\n" +
                "    }\n" +
                "\n" +
                "    nav a:hover {\n" +
                "      color: #f4d35e;\n" +
                "    }\n" +
                "\n" +
                "    header {\n" +
                "      background: url('https://images.unsplash.com/photo-1507525428034-b723cf961d3e') no-repeat center center/cover;\n" +
                "      height: 100vh;\n" +
                "      color: white;\n" +
                "      text-align: center;\n" +
                "      display: flex;\n" +
                "      flex-direction: column;\n" +
                "      justify-content: center;\n" +
                "    }\n" +
                "\n" +
                "    header h1 {\n" +
                "      font-size: 3rem;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "\n" +
                "    header p {\n" +
                "      font-size: 1.2rem;\n" +
                "    }\n" +
                "\n" +
                "    section {\n" +
                "      padding: 60px 20px;\n" +
                "      text-align: center;\n" +
                "    }\n" +
                "\n" +
                "    .destinations {\n" +
                "      display: flex;\n" +
                "      flex-wrap: wrap;\n" +
                "      justify-content: center;\n" +
                "      gap: 20px;\n" +
                "    }\n" +
                "\n" +
                "    .card {\n" +
                "      background: white;\n" +
                "      border-radius: 10px;\n" +
                "      box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);\n" +
                "      overflow: hidden;\n" +
                "      width: 300px;\n" +
                "      transition: transform 0.3s ease;\n" +
                "    }\n" +
                "\n" +
                "    .card:hover {\n" +
                "      transform: scale(1.03);\n" +
                "    }\n" +
                "\n" +
                "    .card img {\n" +
                "      width: 100%;\n" +
                "      height: 200px;\n" +
                "      object-fit: cover;\n" +
                "    }\n" +
                "\n" +
                "    .card h3 {\n" +
                "      margin: 15px 0;\n" +
                "    }\n" +
                "\n" +
                "    .card p {\n" +
                "      padding: 0 15px 15px;\n" +
                "      font-size: 0.95rem;\n" +
                "    }\n" +
                "\n" +
                "    .book-btn {\n" +
                "      display: inline-block;\n" +
                "      background-color: #4CAF50;\n" +
                "      color: white;\n" +
                "      padding: 10px 20px;\n" +
                "      margin: 10px auto;\n" +
                "      border: none;\n" +
                "      border-radius: 5px;\n" +
                "      cursor: pointer;\n" +
                "      font-size: 1rem;\n" +
                "      transition: background 0.3s;\n" +
                "    }\n" +
                "\n" +
                "    .book-btn:hover {\n" +
                "      background-color: #45a049;\n" +
                "    }\n" +
                "\n" +
                "    footer {\n" +
                "      background-color: #333;\n" +
                "      color: white;\n" +
                "      text-align: center;\n" +
                "      padding: 20px;\n" +
                "    }\n" +
                "\n" +
                "    @media (max-width: 768px) {\n" +
                "      header h1 {\n" +
                "        font-size: 2rem;\n" +
                "      }\n" +
                "\n" +
                "      .destinations {\n" +
                "        flex-direction: column;\n" +
                "        align-items: center;\n" +
                "      }\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\n" +
                "  <nav>\n" +
                "    <a href=\"#home\">Home</a>\n" +
                "    <a href=\"#destinations\">Destinations</a>\n" +
                "    <a href=\"#contact\">Contact</a>\n" +
                "  </nav>\n" +
                "\n" +
                "  <header id=\"home\">\n" +
                "    <h1>Explore the World</h1>\n" +
                "    <p>Adventure is out there. Discover new places with Wanderlust Travel.</p>\n" +
                "  </header>\n" +
                "\n" +
                "  <section id=\"destinations\">\n" +
                "    <h2>Popular Destinations</h2>\n" +
                "    <div class=\"destinations\">\n" +
                "      <div class=\"card\">\n" +
                "        <img src=\"https://images.unsplash.com/photo-1505761671935-60b3a7427bad\" alt=\"Paris\">\n" +
                "        <h3>Paris, France</h3>\n" +
                "        <p>The city of lights and love awaits with its iconic Eiffel Tower and rich culture.</p>\n" +
                "        <button class=\"book-btn\">Book Now</button>\n" +
                "      </div>\n" +
                "      <div class=\"card\">\n" +
                "        <img src=\"https://images.unsplash.com/photo-1491553895911-0055eca6402d\" alt=\"Tokyo\">\n" +
                "        <h3>Tokyo, Japan</h3>\n" +
                "        <p>Experience the perfect blend of modern life and ancient tradition in Japan's capital.</p>\n" +
                "        <button class=\"book-btn\">Book Now</button>\n" +
                "      </div>\n" +
                "      <div class=\"card\">\n" +
                "        <img src=\"https://images.unsplash.com/photo-1506744038136-46273834b3fb\" alt=\"New York\">\n" +
                "        <h3>New York, USA</h3>\n" +
                "        <p>The city that never sleeps offers excitement, culture, and endless exploration.</p>\n" +
                "        <button class=\"book-btn\">Book Now</button>\n" +
                "      </div>\n" +
                "    </div>\n" +
                "  </section>\n" +
                "\n" +
                "  <section id=\"contact\">\n" +
                "    <h2>Get in Touch</h2>\n" +
                "    <p>Ready to plan your next journey? Contact us for exclusive travel packages and deals!</p>\n" +
                "    <p>Email: <a href=\"mailto:contact@wanderlusttravel.com\">contact@wanderlusttravel.com</a></p>\n" +
                "  </section>\n" +
                "\n" +
                "  <footer>\n" +
                "    <p>&copy; 2025 Wanderlust Travel. All rights reserved.</p>\n" +
                "  </footer>\n" +
                "\n" +
                "  <script>\n" +
                "    // Smooth scroll fallback (modern browsers already support it via CSS)\n" +
                "    document.querySelectorAll('nav a').forEach(anchor => {\n" +
                "      anchor.addEventListener('click', function (e) {\n" +
                "        const target = document.querySelector(this.getAttribute('href'));\n" +
                "        if (target) {\n" +
                "          e.preventDefault();\n" +
                "          target.scrollIntoView({ behavior: 'smooth' });\n" +
                "        }\n" +
                "      });\n" +
                "    });\n" +
                "\n" +
                "    // Book button interaction\n" +
                "    document.querySelectorAll('.book-btn').forEach(btn => {\n" +
                "      btn.addEventListener('click', () => {\n" +
                "        alert('Thank you! Our travel agent will contact you soon!');\n" +
                "      });\n" +
                "    });\n" +
                "  </script>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n");
    }
}


