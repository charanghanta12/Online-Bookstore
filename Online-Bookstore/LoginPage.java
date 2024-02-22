import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginPage extends JFrame {
    private final String DB_URL = "jdbc:mysql://localhost:3306/bookstore";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "Charan123!";

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField registerUsernameField;
    private JPasswordField registerPasswordField;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private DefaultListModel<Book> bookListModel;
    private List<Book> cartBooks;

    public LoginPage() {
        setTitle("BOOKSTORE");
        setBounds(0, 0, 600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        ImageIcon appIcon = new ImageIcon("logo.png"); 
        setIconImage(appIcon.getImage());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.add(createLoginPanel(), "Login");
        cardPanel.add(createRegistrationPanel(), "Registration");
        cardPanel.add(createDashboardPanel(), "Dashboard");

        add(cardPanel);

        setVisible(true);
    }

        private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);

        ImageIcon backgroundImageIcon = new ImageIcon("b1.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        panel.add(backgroundLabel);

        JLabel titleLabel = new JLabel("LOGIN");
        titleLabel.setForeground(Color.cyan);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        titleLabel.setBounds(220, 80, 300, 50);
        backgroundLabel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.cyan);
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        usernameLabel.setBounds(110, 180, 120, 25);
        backgroundLabel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 180, 200, 25);
        backgroundLabel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.cyan);
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        passwordLabel.setBounds(110, 220, 80, 25);
        backgroundLabel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 220, 200, 25);
        backgroundLabel.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(Color.DARK_GRAY);
        loginButton.setBounds(200, 260, 100, 40);
        backgroundLabel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (validateLogin(username, password)) {
                    System.out.println("Login successful! Enjoy Our Bookstore");
                    cardLayout.show(cardPanel, "Dashboard");
                } else {
                    System.out.println("Invalid username or password");
                    JOptionPane.showMessageDialog(LoginPage.this, "Invalid username or password",
                            "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton registerButton = new JButton("Register");
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(Color.DARK_GRAY);
        registerButton.setBounds(310, 260, 100, 40);
        backgroundLabel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Registration");
            }
        });

        return panel;
    }

    private JPanel createRegistrationPanel() {
        JPanel panel = new JPanel(null);

        ImageIcon backgroundImageIcon = new ImageIcon("b1.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        panel.add(backgroundLabel);

        JLabel titleLabel = new JLabel("REGISTER");
        titleLabel.setForeground(Color.cyan);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        titleLabel.setBounds(200, 60, 300, 50);
        backgroundLabel.add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.cyan);
        usernameLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        usernameLabel.setBounds(110, 150, 120, 25);
        backgroundLabel.add(usernameLabel);

        registerUsernameField = new JTextField();
        registerUsernameField.setBounds(200, 150, 200, 25);
        backgroundLabel.add(registerUsernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.cyan);
        passwordLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        passwordLabel.setBounds(110, 190, 80, 25);
        backgroundLabel.add(passwordLabel);

        registerPasswordField = new JPasswordField();
        registerPasswordField.setBounds(200, 190, 200, 25);
        backgroundLabel.add(registerPasswordField);

        JButton registerButton = new JButton("Register");
        registerButton.setForeground(Color.WHITE);
        registerButton.setBackground(Color.DARK_GRAY);
        registerButton.setBounds(200, 230, 100, 40);
        backgroundLabel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = registerUsernameField.getText();
                String password = new String(registerPasswordField.getPassword());
                if (registerUser(username, password)) {
                    System.out.println("Registration successful!");
                    JOptionPane.showMessageDialog(LoginPage.this, "Registration successful",
                            "Registration", JOptionPane.INFORMATION_MESSAGE);
                    cardLayout.show(cardPanel, "Login");
                } else {
                    System.out.println("Registration failed");
                    JOptionPane.showMessageDialog(LoginPage.this, "Registration failed",
                            "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.DARK_GRAY);
        backButton.setBounds(310, 230, 100, 40);
        backgroundLabel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Login");
            }
        });

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(null);

        ImageIcon backgroundImageIcon = new ImageIcon("b1.jpg");
        JLabel backgroundLabel = new JLabel(backgroundImageIcon);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        panel.add(backgroundLabel);

        JLabel titleLabel = new JLabel("BOOKSTORE");
        titleLabel.setForeground(Color.cyan);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        titleLabel.setBounds(180, 50, 300, 50);
        backgroundLabel.add(titleLabel);

        JButton browseButton = new JButton("Browse Books");
        browseButton.setForeground(Color.WHITE);
        browseButton.setBackground(Color.DARK_GRAY);
        browseButton.setBounds(200, 130, 200, 40);
        backgroundLabel.add(browseButton);

        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showBooks();
                cardLayout.show(cardPanel, "Books");
            }
        });

        JButton cartButton = new JButton("View Cart");
        cartButton.setForeground(Color.WHITE);
        cartButton.setBackground(Color.DARK_GRAY);
        cartButton.setBounds(200, 180, 200, 40);
        backgroundLabel.add(cartButton);

        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewCart();
                cardLayout.show(cardPanel, "Cart");
            }
        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setBackground(Color.DARK_GRAY);
        logoutButton.setBounds(200, 230, 200, 40);
        backgroundLabel.add(logoutButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
                cardLayout.show(cardPanel, "Login");
            }
        });

        return panel;
    }

    private void showBooks() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Browse Books");
        titleLabel.setForeground(Color.black);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        String[] imagePaths = { "b2.jpg", "b3.jpg", "b4.jpg", "b5.jpg", "b6.jpg", "b7.jpg", "b8.jpg" };

        DefaultListModel<ImageIcon> bookImageListModel = new DefaultListModel<>();
        for (String imagePath : imagePaths) {
            ImageIcon imageIcon = new ImageIcon(imagePath);
            bookImageListModel.addElement(imageIcon);
        }
        JList<ImageIcon> bookImageList = new JList<>(bookImageListModel);
        panel.add(new JScrollPane(bookImageList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.setForeground(Color.WHITE);
        addToCartButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(addToCartButton);

        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon selectedImageIcon = bookImageList.getSelectedValue();
                if (selectedImageIcon != null) {
                    addToCart(new Book(selectedImageIcon.getDescription(), "OFFER", "charan", 99));
                    JOptionPane.showMessageDialog(LoginPage.this, "Book added to cart",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Please select a book",
                            "Selection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Dashboard");
            }
        });

        cardPanel.add(panel, "Books");
        cardLayout.show(cardPanel, "Books");
    }

    private void addToCart(Book book) {
        if (cartBooks == null) {
            cartBooks = new ArrayList<>();
        }
        cartBooks.add(book);
    }

    private void viewCart() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.BLACK);

        JLabel titleLabel = new JLabel("View Cart");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        DefaultListModel<Book> cartListModel = new DefaultListModel<>();
        if (cartBooks != null) {
            for (Book book : cartBooks) {
                cartListModel.addElement(book);
            }
        }
        JList<Book> cartList = new JList<>(cartListModel);
        panel.add(new JScrollPane(cartList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.DARK_GRAY);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JButton removeButton = new JButton("Remove from Cart");
        removeButton.setForeground(Color.WHITE);
        removeButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(removeButton);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Book selectedBook = cartList.getSelectedValue();
                if (selectedBook != null) {
                    cartListModel.removeElement(selectedBook);
                    cartBooks.remove(selectedBook);
                    JOptionPane.showMessageDialog(LoginPage.this, "Book removed from cart",
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(LoginPage.this, "Please select a book",
                            "Selection Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setForeground(Color.WHITE);
        checkoutButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(checkoutButton);

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(LoginPage.this, "Checkout successful!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                cartListModel.clear();
                cartBooks.clear();
            }
        });

        JButton backButton = new JButton("Back");
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(Color.DARK_GRAY);
        buttonPanel.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Dashboard");
            }
        });

        cardPanel.add(panel, "Cart");
        cardLayout.show(cardPanel, "Cart");
    }

    private boolean validateLogin(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            boolean valid = rs.next();

            rs.close();
            stmt.close();
            conn.close();

            return valid;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean registerUser(String username, String password) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rowsAffected = stmt.executeUpdate();

            stmt.close();
            conn.close();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        registerUsernameField.setText("");
        registerPasswordField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPage();
            }
        });
    }
}

class Book {
    private String imagePath;
    private String title;
    private String author;
    private double price;

    public Book(String imagePath, String title, String author, double price) {
        this.imagePath = imagePath;
        this.title = title;
        this.author = author;
        this.price = price;
    }

    public Book(int id, String title2, String author2, double price2) {
    }

    public String getImagePath() {
        return imagePath;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return title + " by " + author + " ($" + price + ")";
    }
}