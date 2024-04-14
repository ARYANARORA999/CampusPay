import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class AdminViewVendorTransaction {
    // this class is used to display the transaction history of a vendor
    private Container c;
    private JLabel title;
    private JTextArea transactions;
    private JScrollPane scroll;

    public void show(String Vid) {
        // create a new frame to store the transaction history
        JFrame f = new JFrame("Transaction History");
        f.setSize(900, 600); // Set frame size
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(243, 238, 234)); // Set background color

        // create a label
        title = new JLabel("Transaction History");
        title.setFont(new Font("MONOSPACED", Font.BOLD, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        f.add(title);

        // create a scrollable area to store the transaction history
        transactions = new JTextArea(30, 30);
        transactions.setFont(new Font("Arial", Font.PLAIN, 10));
        scroll = new JScrollPane(transactions);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setSize(800, 400); // Adjust size to fit frame
        scroll.setLocation(50, 100);
        f.add(scroll);

        // connect to the database and get the transaction history
        try {
            Conn c = new Conn();
            // use procedure get_all_transactions_made_by_vendor(IN vendor_id varchar(50))
            CallableStatement cs = c.con.prepareCall("{call get_all_transactions_made_by_vendor(?)}");
            cs.setString(1, Vid);
            ResultSet rs = cs.executeQuery();

            // display the transaction history
            while (rs.next()) {
                transactions.append("Transaction ID: " + rs.getString("ID") + "\n");
                transactions.append("Student ID: " + rs.getString("student_id") + "\n");
                transactions.append("Amount: " + rs.getString("total_amount") + "\n");
                transactions.append("Date and Time: " + rs.getString("date_time") + "\n\n");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        // create a button to go back to the home page
        JButton back = new JButton("Back");
        back.setFont(new Font("Arial", Font.PLAIN, 15));
        back.setSize(100, 20);
        back.setLocation(400, 520); // Adjust location to fit frame
        back.setBackground(new Color(176, 166, 149)); // Set background color
        back.setBorder(BorderFactory.createLineBorder(new Color(176, 166, 149),2)); // Set border color
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewVendors view1 = new ViewVendors();
                view1.show();
                f.dispose();
            }
        });
        f.add(back);

        f.setVisible(true);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (dim.width - f.getSize().width) / 2;
        int y = (dim.height - f.getSize().height) / 2;
        f.setLocation(x, y);
    }

    public static void main(String[] args) {
        AdminViewVendorTransaction adminViewVendorTransaction = new AdminViewVendorTransaction();
        adminViewVendorTransaction.show("V001");
    }
}
