package tubes.ewaste.view;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private LoginPanel loginPanel;
    private RegisterPanel registerPanel;
    private DashboardPanel dashboardPanel;

    public MainFrame() {
        initComponents();
        setupFrame();
    }

    private void initComponents() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Initialize panels
        loginPanel = new LoginPanel(this);
        registerPanel = new RegisterPanel(this);
        dashboardPanel = new DashboardPanel(this);

        // Add panels to card layout
        mainPanel.add(loginPanel, "LOGIN");
        mainPanel.add(registerPanel, "REGISTER");
        mainPanel.add(dashboardPanel, "DASHBOARD");

        // Add main panel to frame
        add(mainPanel);

        // Show login by default
        showLogin();
    }

    private void setupFrame() {
        setTitle("E-WastePas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public void showLogin() {
        cardLayout.show(mainPanel, "LOGIN");
    }

    public void showRegister() {
        cardLayout.show(mainPanel, "REGISTER");
    }

    public void showDashboard() {
        dashboardPanel.loadUsers(); // Load users data when showing dashboard
        cardLayout.show(mainPanel, "DASHBOARD");
    }
}



// package tubes.ewaste.view;

// import com.formdev.flatlaf.FlatLightLaf;
// import javax.swing.*;
// import java.awt.*;

// public class MainFrame extends JFrame {
//     private CardLayout cardLayout;
//     private JPanel mainPanel;
//     private LoginPanel loginPanel;
//     private RegisterPanel registerPanel;

//     public MainFrame() {
//         initComponents();
//         setupFrame();
//     }

//     private void initComponents() {
//         cardLayout = new CardLayout();
//         mainPanel = new JPanel(cardLayout);

//         // Initialize panels
//         loginPanel = new LoginPanel(this);
//         registerPanel = new RegisterPanel(this);

//         // Add panels to card layout
//         mainPanel.add(loginPanel, "LOGIN");
//         mainPanel.add(registerPanel, "REGISTER");

//         // Add main panel to frame
//         add(mainPanel);

//         // Show login by default
//         showLogin();
//     }

//     private void setupFrame() {
//         setTitle("E-WastePas");
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//         setSize(800, 600);
//         setLocationRelativeTo(null);
//         setResizable(false);
//     }

//     public void showLogin() {
//         cardLayout.show(mainPanel, "LOGIN");
//     }

//     public void showRegister() {
//         cardLayout.show(mainPanel, "REGISTER");
//     }
// }