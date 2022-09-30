
package javadbconnect;
import java.sql.*;
import java.util.Scanner;
import java.sql.Connection;
public class JavaDBConnect {
    
     public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException{
        
        try
        {    int choice=0;
             javadb s = new javadb();
            do
            {
                System.out.println("Select an operation \n 1-insert in database\n 2-  Update \n 3- Delete a Record \n 4- Search for a movie \n 5- Exit");
                Scanner choicein = new Scanner(System.in);
                choice=choicein.nextInt();
                switch(choice)
                {
                    case 1:
                        s.getMovieDetails();
                        s.insertName();
                       
                        break;
                    case 2:
                        s.updateYear();
                        break;
                    case 3:
                        s.deleteRecord();
                        break;
                    case 4:
                        s.searchMovie();
                        break;
                    case 5:
                        break;
                    default:
                        System.out.println("Select the correct choice");
                }
            }while(choice!=5);
            System.out.println("Thanks for Using our Software");
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }        
     }
}
class javadb
{
    private String name;
    private String actor;
    private String actress;
    private String director;
    private int year;
    
   public void getMovieDetails(){
        
           Scanner input = new Scanner(System.in);
        System.out.println("Enter movie Name");
        name = input.nextLine();
        System.out.println("Enter actor");
        actor = input.nextLine();
        System.out.println("Enter actress");
        actress = input.nextLine();
        System.out.println("Enter director");
        director = input.nextLine();
        System.out.println("Enter year of release");
        year = input.nextInt();

}
    
   public void insertName() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        //here we are going to work with a database
        //we need to open a database connection and enter details there.
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/moviesdb","root","smenglish@me06");
        Connection con = dbmsconnect.getConnection();
       String sql = "insert into movies values (?,?,?,?);";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, name);
        stmt.setString(2, actor);
        stmt.setString(3, actress);
        stmt.setString(4, director);
        stmt.setInt(5, year);
        
       int i = stmt.executeUpdate();
        if(i>0)
        {
        System.out.println("Record  inserted successfully");
        }
        dbmsconnect.closeConnection(con, stmt);
    }
    
    public void updateYear() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/moviesdb","root","smenglish@me06");
        Connection con = dbmsconnect.getConnection();
        System.out.println("Enter movie Name");
        Scanner input = new Scanner(System.in);
        String inputname=input.nextLine();
        System.out.println("Enter the actor ");
        String inputactor=input.nextLine();
        System.out.println("Enter the actress ");
        String inputactress=input.nextLine();
        System.out.println("Enter the director ");
        String inputyear=input.nextLine();
        System.out.println("Enter the year ");
        String inputdirector=input.nextLine();
        String sql = "update movies set actor=?,actress = ? ,director=?, year = ? where name = ?;";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, inputactor);
        stmt.setString(2, inputactress);
        stmt.setString(3, inputdirector);
        stmt.setString(4, inputyear);
        stmt.setString(5, inputname);
        int i = stmt.executeUpdate();
        if(i>0)
        {
            System.out.println("Record updated sucessfully");
        }else
        {
            System.out.println("No Such record in the Database");
        }
        dbmsconnect.closeConnection(con, stmt);
    }


public void deleteRecord() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/moviesdb","root","smenglish@me06");
    Connection con = dbmsconnect.getConnection();
    System.out.println("Enter the Name of the movie");
    Scanner input = new Scanner(System.in);
    String inputname=input.nextLine();
    String sql = "delete from movies where name = ?;";
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setString(1, inputname);
    int i = stmt.executeUpdate();
    if(i>0)
    {
        System.out.println("Record Deleted Successfully");
    }
    else
    {
        System.out.println("No Such Record in the Database");
    }
   // dbmsconnect.closeConnection(con, stmt);
}

public void searchMovie() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
    dbmsconnection dbmsconnect = new dbmsconnection("jdbc:mysql://localhost:3306/moviesdb","root","smenglish@me06");
    Connection con = dbmsconnect.getConnection();
    System.out.println("Enter movie Name");
    Scanner input = new Scanner(System.in);
    String inputname=input.nextLine();
    String sql = "select * from movies where name=?";
    PreparedStatement stmt = con.prepareStatement(sql);
    stmt.setString(1, inputname);
    ResultSet rs = stmt.executeQuery();
    if(rs.next()==false)
    {
        System.out.println("No such record found in the database");
    }
    else
    {    
        System.out.println("Name:"+rs.getString(1)+"\nActor:"+rs.getString(2)+"\nActress:"+rs.getString(3)+"\nDirector:"+rs.getString(4)+"\nYear:"+rs.getInt(5));
        
    }
    dbmsconnect.closeConnection(con, stmt);
}
}

class dbmsconnection
{
    String url;
    String username;
    String password;
    
    public dbmsconnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
        Connection con = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,username,password);
        System.out.println("Connection Established Successfully");
        return con;
    }
    
    public void closeConnection(Connection con,Statement stmt) throws SQLException
    {
        stmt.close();
        con.close();
        System.out.println("The connection is closed");
    }
}