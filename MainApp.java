import java.sql.*;
import java.util.Scanner;

public class MainApp 
{

    public static void main(String[] args) 
    {

        Scanner sc = new Scanner(System.in);
        Connection conn = DBConnection.connect();

        if (conn == null) return;

        DBConnection.createTables(conn);

        while (true) 
        {
            System.out.println("\n==== MENU ====");
            System.out.println("1. Add Player");
            System.out.println("2. View Players");
            System.out.println("3. Update Player");
            System.out.println("4. Delete Player");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            try 
            {

                if (choice == 1) 
                {
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    System.out.print("Enter Name: ");
                    String name = sc.next();
                    System.out.print("Enter Email: ");
                    String email = sc.next();

                    PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO Player VALUES (?, ?, ?, ?)"
                    );

                    ps.setInt(1, id);
                    ps.setString(2, name);
                    ps.setString(3, email);
                    ps.setInt(4, 1); // default team

                    ps.executeUpdate();

                    System.out.println("Player added.");

                } 
                else if (choice == 2) 
                {

                    Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT * FROM Player");

                    System.out.println("\nPlayers:");
                    while (rs.next()) {
                        System.out.println(
                            rs.getInt("player_id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("email")
                        );
                    }

                } 
                else if (choice == 3) 
                {
                    System.out.print("Enter Player ID to update: ");
                    int id = sc.nextInt();
                
                    System.out.print("Enter new name: ");
                    String newName = sc.next();
                
                    System.out.print("Enter new email: ");
                    String newEmail = sc.next();
                
                    PreparedStatement ps = conn.prepareStatement(
                        "UPDATE Player SET name = ?, email = ? WHERE player_id = ?"
                    );
                
                    ps.setString(1, newName);
                    ps.setString(2, newEmail);
                    ps.setInt(3, id);
                
                    ps.executeUpdate();
                
                    System.out.println("Player updated (name + email).");
                }
                else if (choice == 4) 
                {

                    System.out.print("Enter Player ID to delete: ");
                    int id = sc.nextInt();

                    PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM Player WHERE player_id = ?"
                    );

                    ps.setInt(1, id);
                    ps.executeUpdate();

                    System.out.println("Player deleted.");

                } 
                else if (choice == 5) 
                {
                    System.out.println("Exiting...");
                    break;
                }

            } 
            catch (Exception e) 
            {
                System.out.println("Error: " + e.getMessage());
            }
        }

        try 
        {
            conn.close();
        } 
        catch (Exception e) 
        {
            System.out.println(e);
        }
    }
}