import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DBConnection 
{

    public static Connection connect() 
        {
        Connection conn = null;
    
        try 
        {
            Class.forName("org.sqlite.JDBC"); // ✅ ADD THIS LINE
    
            conn = DriverManager.getConnection("jdbc:sqlite:tournament.db");
            System.out.println("Connected to database.");
        } 
        catch (Exception e) 
        {
            System.out.println("Connection failed: " + e.getMessage());
        }
    
        return conn;
    }

    public static void createTables(Connection conn) 
    {
        try {
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS Team (team_id INTEGER PRIMARY KEY, team_name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Player (player_id INTEGER PRIMARY KEY, name TEXT, email TEXT, team_id INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Game (game_id INTEGER PRIMARY KEY, game_name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Tournament (tournament_id INTEGER PRIMARY KEY, name TEXT, game_id INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Match (match_id INTEGER PRIMARY KEY, tournament_id INTEGER, team1_id INTEGER, team2_id INTEGER, match_date TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Score (score_id INTEGER PRIMARY KEY, match_id INTEGER, team_id INTEGER, points INTEGER)");

            System.out.println("Tables created successfully.");

        } 
        catch (Exception e) 
        {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }
}