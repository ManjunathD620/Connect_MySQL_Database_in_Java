import java.sql.*;
import java.util.Scanner;
public class movie {

    public  static void main(String[] args) throws Exception{

        Scanner sc = new Scanner(System.in);

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mulesoft","root","Dddd@123@");
        String drop= "DROP TABLE IF EXISTS `mulesoft`.`movies`\n";
        String create = "CREATE TABLE `mulesoft`.`movies` (\n" +
                "  `Name` VARCHAR(60) NOT NULL,\n" +
                "  `Actor` VARCHAR(255) NOT NULL,\n" +
                "  `Actress` VARCHAR(255) NOT NULL,\n" +
                "  `Director` VARCHAR(60) NOT NULL,\n" +
                "  `Year of release` INT NOT NULL,\n" +
                "  PRIMARY KEY (`Name`));\n" ;

        String insertDetails = "INSERT INTO `mulesoft`.`movies`\n" +
                "(`Name`,\n" +
                "`Actor`,\n" +
                "`Actress`,\n" +
                "`Director`,\n" +
                "`Year of release`)\n" +
                "VALUES\n" +
                "(\"K.G.F Chapter 2\",\n" +
                "\"Yash\",\n" +
                "\"Sanjay Dutt, Raveena Tandon, Srinidhi Shetty and Prakash Raj\",\n" +
                "\"Prashanth Neel\",\n" +
                "2022),\n" +
                "(\"Beast\",\n" +
                "\"Vijay\",\n" +
                "\"Pooja Hegde, Selvaraghavan, Shine Tom Chacko, Yogi Babu\",\n" +
                "\"Nelson Dilipkumar\",\n" +
                "2022),\n" +
                "(\"Avatara Purusha\",\n" +
                "\"Sharan\",\n" +
                "\"Ashika Ranganath, Srinagara Kitty, Saikumar, Sudharani, Bhavya\",\n" +
                "\"Rajkishor Prasad\",\n" +
                "2022),\n" +
                "(\"RRR\",\n" +
                "\"N. T. Rama Rao, Jr. Ram Charan\",\n" +
                "\"Ajay Devgn Alia Bhatt Shriya Saran Samuthirakani Ray \",\n" +
                "\"S. S. Rajamouli\",\n" +
                "2022),\n" +
                "(\"Runway 34\",\n" +
                "\"Amitabh Bachchan, Rakul Preet Singh\",\n" +
                "\" Boman Irani Al-Mamun Al Siyam\",\n" +
                "\"Ajay Devgn\",\n" +
                "2022)";


        DatabaseMetaData dbm = conn.getMetaData();
        Statement stm = conn.createStatement();


        ResultSet tables = dbm.getTables(null, null, "movies", null);
        if (tables.next()) {}
        else {
            stm.execute(create);
            stm.execute(insertDetails);
        }



        System.out.println("============== Welcome to Movie DataBase ==============");
        boolean stat = true;
        while(stat){
            System.out.println("\n-------> Press '1' to Enter the Movie details Manually\n-------> Press '2' to Query the Database" +
                    "\n-------> Press '-1' to Exit ");
            int v = sc.nextInt();
            if(v == 1)
                insertManually(conn);
            else if(v==2)
                queryDB(conn);
            else if(v == -1)
                stat = false;
            else
            {
                stat = false;
                System.out.println("Select the Valid Option");
            }



        }


    }
    static void queryDB(Connection conn) throws Exception{
        Scanner sc = new Scanner(System.in);
        System.out.println("\npress '1' to get all Movie Names\nPress'2' to get all Actor name \nPress '3' to get Directors Names \nPress '4' to get all the Movies Details");
        int selected = sc.nextInt();
        Statement stm = conn.createStatement();
        ResultSet set = stm.executeQuery("select * from movies");
        System.out.println("\n---------------------------------------");
        if(selected == 1){

            while(set.next()){
                System.out.println(set.getString(1));
            }
        }
        else if(selected ==  2){
            while(set.next()){
                System.out.println(set.getString(2));
            }
        }
        else if(selected == 3){
            while(set.next()){
                System.out.println(set.getString(4));
            }
        }
        else  if(selected == 4){
            System.out.println("\nName   |   Actor    |   Actress    |   Director   |   Year of Release\n");
            while(set.next()){
                System.out.println(set.getString(1)+"   "+set.getString(2)+"   "+set.getString(3)+"   "+set.getString(4)+"   "+set.getString(5));
            }
        }
        else
            System.out.println("Select the valid option");

        System.out.println("---------------------------------------");

    }
    static void insertManually(Connection conn) throws Exception{
        String[] movieDetails = new String[4];
        int year = 0 ;

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Movie Name : ");
        movieDetails[0] = sc.next();
        System.out.print("Enter the Actor Name : ");
        movieDetails[1] = sc.next();
        System.out.print("Enter the Actress Name : ");
        movieDetails[2] = sc.next();
        System.out.print("Enter the Director Name : ");
        movieDetails[3] = sc.next();
        System.out.print("Enter the year of release : ");
        try{
            year = sc.nextInt();
        }

        catch(Exception e){
            System.err.println("\nEnter the Year in-terms of Numbers");
            System.exit(90);
        }

        String query = " INSERT INTO `mulesoft`.`movies`\n" +
                "(`Name`,\n" +
                "`Actor`,\n" +
                "`Actress`,\n" +
                "`Director`,\n" +
                "`Year of release`) VALUES (?,?,?,?,?);";

        PreparedStatement Pstm = conn.prepareStatement(query);
        Pstm.setString (1, movieDetails[0]);
        Pstm.setString (2, movieDetails[1]);
        Pstm.setString (3, movieDetails[2]);
        Pstm.setString (4, movieDetails[3]);
        Pstm.setInt (5, year);
        try{
            Pstm.execute();
            System.out.println("Inserted Successfully");
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }

}
