import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import net.proteanit.sql.DbUtils; 
public class dbms {
     
    public static void main(String[] args) {
         
        login();
    }
}
public static Connection connect()
{
    try {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@218.248.0.7:1521:RDBMS","it19737084","vasavi");
        //System.out.println("Connected to MySQL");
        return con;
    } 
    catch (Exception ex) {
        ex.printStackTrace();
    }
    return null;
}
public static void login() {
     
    JFrame f=new JFrame("Login");//creating instance of JFrame  
    JLabel l1,l2;  
    l1=new JLabel("Username");  //Create label Username
    l1.setBounds(30,15, 100,30); //x axis, y axis, width, height 
     
    l2=new JLabel("Id_no");  //Create label id
    l2.setBounds(30,50, 100,30);    
     
    JTextField F_user = new JTextField(); //Create text field for username
    F_user.setBounds(110, 15, 200, 30);
         
    JTextField F_id=new JTextField(); //Create text field for id
    F_id.setBounds(110, 50, 200, 30);
       
    JButton login_but=new JButton("Login");//creating instance of JButton for Login Button
    login_but.setBounds(130,90,80,25);//Dimensions for button
    login_but.addActionListener(new ActionListener() {  //Perform action
         
        public void actionPerformed(ActionEvent e){ 
 
        String user = F_user.getText(); //Store username entered by the user in the variable "username"
        String id = F_id.getText(); //Store id entered by the user in the variable "id"
         
        if(username.equals("")) //If username is null
        {
            admin_menu();
        } 
        else if(id.equals("")) //If id is null
        {
            admin_menu();
        }
        else { //If both the fields are present then to login the user, check wether the user exists already
            //System.out.println("Login connect");
            Connection connection=connect();  //Connect to the database
            try
            {
            Statement stmt = connection.createStatement();
              stmt.executeUpdate("USE Music_Mania"); //Use the database with the name "Music_Maina"
              String st = ("SELECT * FROM USERS WHERE USER_NAME='"+username+"' AND ID_NO='"+id+"'"); //Retreive username and id from users
              ResultSet rs = stmt.executeQuery(st); //Execute query
              if(rs.next()==false) { //Move pointer below
                  System.out.print("No user");  
                  JOptionPane.showMessageDialog(null,"Wrong Username/id!"); //Display Message
 
              }
              else {
                   user_menu();
              }
            }
            catch (Exception ex) {
                 ex.printStackTrace();
            }
        }
    }               
    });
 
     
    f.add(F_id); //add password
    f.add(login_but);//adding button in JFrame  
    f.add(F_user);  //add user
    f.add(l1);  // add label1 i.e. for username
    f.add(l2); // add label2 i.e. for id 
    f.setSize(400,180);//400 width and 500 height  
    f.setLayout(null);//using no layout managers  
    f.setVisible(true);//making the frame visible 
    f.setLocationRelativeTo(null);
     
}
public static void create() {
          Connection connection=connect();
          Statement stmt = connection.createStatement(); 
          String sql = "CREATE DATABASE Music_Mania"; 
          stmt.executeUpdate(sql); 
          stmt.executeUpdate("USE Music_Mania"); 
          //Create Users Table
          String sql1 = ("CREATE TABLE USERS(ID_NO NUMBER(5) PRIMARY KEY, USER_NAME VARCHAR2(20),PREMIUM_TYPE VARCHAR2(20))");
          stmt.executeUpdate(sql1);
          //Create artists table
          stmt.executeUpdate("CREATE TABLE ARTIST(ARTISTS_NAME VARCHAR2(20) PRIMARY KEY, TOTAL_ALBUM NUMBER(5), LABEL VARCHAR2(20))");
          //Create albums Table
          stmt.executeUpdate("CREATE TABLE ALBUM(ALBUM_TITLE VARCHAR2(20) PRIMARY KEY, NO_OF_TRACKS NUMBER(5),LABEL VARCHAR2(20),STREAMINGS NUMBER(10))");
          //create songs table
          stmt.executeUpdate("CREATE TABLE SONGS(SONG VARCHAR2(20) PRIMARY KEY, LISTENERS NUMBER (5), ALBUM_TITLE VARCHAR2(20))");
          //Create Music_mania table
          stmt.executeUpdate("CREATE TABLE MUSIC_MANIA(ID_NO NUMBER(5),ARTIST_NAME VARCHAR2(20),FOREIGN KEY(ID_NO) REFERENCES USERS,FOREIGN KEY(ARTIST_NAME) REFERENCES ARTIST);
          //create table Your_library
          stmt.executeUpdate("CREATE TABLE YOUR_LIBRARY(ID_NO NUMBER(5),PLAYLIST_NAME VARCHAR2(20) PRIMARY KEY,PLAYLISTS_SONG VARCHAR2(20),ALBUM_TITLE VARCHAR2(20))");
          //create table User_library
          stmt.executeUpdate("CREATE TABLE USER_LIBRARY(ID_NO NUMBER(5),PLAYLIST_NAME VARCHAR2(20),FOREIGN KEY(ID_NO) REFERENCES USERS,FOREIGN KEY(PLAYLIST_NAME) REFERENCES YOUR_LIBRARY)");
          //create table Album_songs
          stmt.executeUpdate("CREATE TABLE ALBUM_SONGS(SONG VARCHAR2(20),ALBUM_TITLE VARCHAR2(20),FOREIGN KEY(SONG) REFERENCES SONGS,FOREIGN KEY (ALBUM_TITLE) REFERENCES ALBUMS)");
          //create composed table
          stmt.executeUpdate("CREATE TABLE COMPOSED(ARTISTS_NAME VARCHAR2(20),ALBUM_TITLE VARCHAR2(20),FOREIGN KEY(ARTISTS_NAME) REFERENCES ARTIST,FOREIGN KEY(ALBUM_TITLE) REFERENCES ALBUMS)"); 
    resultSet.close();
    }
     catch (Exception ex) {
         ex.printStackTrace();
}
}
public static void user_menu() {
     
     
    JFrame f=new JFrame("User Functions"); //Give dialog box name as User functions
    //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit user menu on closing the dialog box
    JButton Song=new JButton("Songs");//creating instance of JButton  
    Song.setBounds(20,20,120,25);//x axis, y axis, width, height 
    Song.addActionListener(new ActionListener() { 
        public void actionPerformed(ActionEvent e){
             
            JFrame f = new JFrame("Songs"); //View songs in database
            //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             
             
            Connection connection = connect();
            String sql="select * from Songs"; //Retreive data from database
            try {
                Statement stmt = connection.createStatement(); 
                stmt.executeUpdate("USE Music_Mania"); 
                stmt=connection.createStatement();
                ResultSet rs=stmt.executeQuery(sql);
                JTable song_list= new JTable(); //show data in table format
                song_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                  
                JScrollPane scrollPane = new JScrollPane(song_list); //enable scroll bar
 
                f.add(scrollPane); //add scroll bar
                f.setSize(800, 400); //set dimensions of view songs frame
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                 JOptionPane.showMessageDialog(null, e1);
            }               
             
    }
    }
    );
     
    JButton Album=new JButton("Albums");//creating instance of JButton  
    Album.setBounds(150,20,120,25);//x axis, y axis, width, height 
    Album.addActionListener(new ActionListener() { //Perform action
        public void actionPerformed(ActionEvent e){
             
               
            JFrame f = new JFrame("Album"); //View albums
            //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            Connection connection = connect(); //connect to database
             String sql="select * from Albums"; //Retreive data from database
            try {
                 Statement stmt = connection.createStatement(); //connect to database
                 stmt.executeUpdate("USE Music_Mania"); // use Music_Mania
                 stmt=connection.createStatement();
                 ResultSet rs=stmt.executeQuery(sql);
                 JTable album_list= new JTable(); //show data in table format
                 album_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                  
                JScrollPane scrollPane = new JScrollPane(album_list); //enable scroll bar
 
                f.add(scrollPane); //add scroll bar
                f.setSize(800, 400); //set dimensions of view album frame
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                 JOptionPane.showMessageDialog(null, e1);
            }               
    }
    }
    );
     
  
    JButton add_playlist=new JButton("Add playlist"); //creating instance of JButton to add playlist
    add_playlist.setBounds(20,60,120,25); //set dimensions for button
     
    add_playlist.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
                 
                JFrame g = new JFrame("Enter playlist "); //Frame to enter playlist details
                //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //Create label 
                JLabel l1,l2,l3,l4;  

                l1=new JLabel("id"); // label 1 for id no
                l1.setBounds(30,15,100,30);
  
                l2=new JLabel("playlist name");  //label 2 for playlistname
                l2.setBounds(30,50,100,30); 
                  
                l3=new JLabel("song name");  //label 3 for song name
                l3.setBounds(30,85, 100,30); 
                
                l4=new JLabel("album title"); // label 4 for album
                l4.setBounds(30,130,100,30);
                
                // set text field for id
                JTextField F_id = new JTextField();
                F_id.setBounds(110,15,200,30);
 
                //set text field for playlist name 
                JTextField F_playlist = new JTextField();
                F_playlist.setBounds(110, 50, 200, 30);
                 
                //set text field for song name
                JTextField F_song = new JTextField();
                F_song.setBounds(110, 85, 200, 30);
                   
                // set text field for album name 
                JTextField F_album = new JTextField();
                F_album.setBounds(110,130,200,30);                
                 
                JButton create_but=new JButton("Create");//creating instance of JButton for Create 
                create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
                create_but.addActionListener(new ActionListener() {
                     
                    public void actionPerformed(ActionEvent e){
                    String id = F_id.getText();
                    int id_int = Integer.parseInt(id);
                    String playlist = F_playlist.getText(); 
                    String song = F_song.getText();
                    String album = F_album.getText(); 
                    Connection connection = connect();
                     
                    try {
                     Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE MUSIC_MANIA");
                     stmt.executeUpdate("INSERT INTO YOUR_LIBRARY(ID_NO, PLAYLIST_NAME, PLAYLISTS_SONG, ALBUM_TITLE) VALUES ('"+id_int+"','"+playlist+"','"+song+"',"+album")");
                     JOptionPane.showMessageDialog(null,"playlist added!");
                     g.dispose();
                      
                    }
                     
                    catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }   
                     
                    }
                     
                });
                     
                 
                    g.add(create_but);
                    g.add(a2);
                    g.add(a1);
                    g.add(l1);
                    g.add(l2);
                    g.add(l3);
                    g.add(l4);
                    g.add(F_id);
                    g.add(F_playlist);
                    g.add(F_song);
                    g.add(f_album);
                    g.setSize(350,200);//400 width and 500 height  
                    g.setLayout(null);//using no layout managers  
                    g.setVisible(true);//making the frame visible 
                    g.setLocationRelativeTo(null);
                 
                 
    }
    });  
}
public static void admin_menu() {
     
     
    JFrame f=new JFrame("Admin Functions"); //Give dialog box name as admin functions
    //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
     
     
    JButton create_but=new JButton("Create/Reset");//creating instance of JButton to create or reset database
    create_but.setBounds(450,60,120,25);//x axis, y axis, width, height 
    create_but.addActionListener(new ActionListener() { //Perform action
        public void actionPerformed(ActionEvent e){
             
            create(); //Call create function
            JOptionPane.showMessageDialog(null,"Database Created/Reset!"); //Open a dialog box and display the message
             
        }
    });
     
     
    JButton Song=new JButton("Songs");//creating instance of JButton to view songs
    Song.setBounds(20,20,120,25);//x axis, y axis, width, height 
    Song.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
             
            JFrame f = new JFrame("Songs"); 
            //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             
             
            Connection connection = connect(); //connect to database
            String sql="select * from Songs"; //select all songs 
            try {
                 Statement stmt = connection.createStatement();
                 stmt.executeUpdate("USE Music_Mania"); 
                 stmt=connection.createStatement();
                 ResultSet rs=stmt.executeQuery(sql);
                 JTable song_list= new JTable(); //view data in table format
                 song_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                //mention scroll bar
                JScrollPane scrollPane = new JScrollPane(song_list); 
 
                f.add(scrollPane); //add scrollpane
                f.setSize(800, 400); //set size for frame
                f.setVisible(true);
                f.setLocationRelativeTo(null);
            } catch (SQLException e1) {
                // TODO Auto-generated catch block
                 JOptionPane.showMessageDialog(null, e1);
            }               
             
    }
    }
    );
     
    JButton users_but=new JButton("View Users");//creating instance of JButton to view users
    users_but.setBounds(150,20,120,25);//x axis, y axis, width, height 
    users_but.addActionListener(new ActionListener() { //Perform action on click button
        public void actionPerformed(ActionEvent e){
                 
                JFrame f = new JFrame("Users List");
                //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 
                 
                Connection connection = connect();
                String sql="select * from Users"; //retrieve all users
                try {
                     Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE Music_Mania"); 
                     stmt=connection.createStatement();
                     ResultSet rs=stmt.executeQuery(sql);
                     JTable users_list= new JTable();
                     book_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                     //mention scroll bar
                     JScrollPane scrollPane = new JScrollPane(book_list);
 
                    f.add(scrollPane); //add scrollpane
                    f.setSize(800, 400); //set size for frame
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null, e1);
                }       
                 
                 
    }
        }
    );  
     
    JButton Album=new JButton("View Albums");//creating instance of JButton to view the albums
    Album.setBounds(280,20,160,25);//x axis, y axis, width, height 
    Album.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
                 
                JFrame f = new JFrame("Users List");
                //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 
                 
                Connection connection = connect();
                String sql="select * from Albums";
                try {
                     Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE Music_Mania");
                     stmt=connection.createStatement();
                     ResultSet rs=stmt.executeQuery(sql);
                     JTable album_list= new JTable();
                     album_list.setModel(DbUtils.resultSetToTableModel(rs)); 
                     
                     JScrollPane scrollPane = new JScrollPane(album_list);
 
                    f.add(scrollPane);
                    f.setSize(800, 400);
                    f.setVisible(true);
                    f.setLocationRelativeTo(null);
                } catch (SQLException e1) {
                    // TODO Auto-generated catch block
                     JOptionPane.showMessageDialog(null, e1);
                }       
                             
    }
        }
    );
     
     
    JButton add_user=new JButton("Add User"); //creating instance of JButton to add users
    add_user.setBounds(20,60,120,25); //set dimensions for button
     
    add_user.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
                 
                JFrame g = new JFrame("Enter User Details"); //Frame to enter user details
                //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                //Create label 
                JLabel l1,l2,l3;  
                l1=new JLabel("ID_NO");  //label 1 for id
                l1.setBounds(30,15, 100,30); 
                 
                 
                l2=new JLabel("Username");  //label 2 for username
                l2.setBounds(30,50, 100,30); 
                
                l3=new JLabel("Premium type"); // label 3 for premium type
                l3.setBounds(30,85,100,30);
                 
                //set text field for id no 
                JTextField F_id = new JTextField();
                F_id.setBounds(110, 15, 200, 30);
                 
                //set text field for username
                JTextField F_user=new JTextField();
                F_user.setBounds(110, 50, 200, 30);
                   
                // set text field for premium type 
                JTextField F_premium= new JTextField();
                F_premium.setBounds(110,85,200,30);                
                 
                JButton create_but=new JButton("Create");//creating instance of JButton for Create 
                create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
                create_but.addActionListener(new ActionListener() {
                     
                    public void actionPerformed(ActionEvent e){
                    String id = F_id.getText(); 
                    int id_int = Integer.parseInt(id);
                    String user = F_user.getText();
                    String premium = F_premium.getText(); 
                    Connection connection = connect();
                     
                    try {
                     Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE MUSIC_MANIA");
                     stmt.executeUpdate("INSERT INTO USERS(ID_NO, USER_NAME ,PREMIUM_TYPE) VALUES ('"+id_int+"','"+user+"',"+premium+")");
                     JOptionPane.showMessageDialog(null,"User added!");
                     g.dispose();
                      
                    }
                     
                    catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }   
                     
                    }
                     
                });
                     
                 
                    g.add(create_but);
                    g.add(a2);
                    g.add(a1);
                    g.add(l1);
                    g.add(l2);
                    g.add(l3);
                    g.add(F_id);
                    g.add(F_user);
                    g.add(F_premium);
                    g.setSize(350,200);//400 width and 500 height  
                    g.setLayout(null);//using no layout managers  
                    g.setVisible(true);//making the frame visible 
                    g.setLocationRelativeTo(null);
                 
                 
    }
    });
         
     
    JButton add_song=new JButton("Add song"); //creating instance of JButton for adding songs
    add_song.setBounds(150,60,120,25); 
     
    add_song.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
                //set frame to enter song details
                JFrame g = new JFrame("Enter song Details");
                //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // set labels
                JLabel l1,l2;  
                l1=new JLabel("Song name");  //label 1 for song name
                l1.setBounds(30,15, 100,30); 
                 
                 
                l2=new JLabel("Album title");  //label 2 for album
                l2.setBounds(30,53, 100,30); 
                 
                //set text field for song name
                JTextField F_sname = new JTextField();
                F_sname.setBounds(110, 15, 200, 30);
                 
                //set text field for album 
                JTextField F_albumtitle=new JTextField();
                F_albumtitle.setBounds(110, 53, 200, 30);
                         
                 
                JButton create_but=new JButton("Submit");//creating instance of JButton to submit details  
                create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
                create_but.addActionListener(new ActionListener() {
                     
                    public void actionPerformed(ActionEvent e){
                    String sname = F_sname.getText();
                    String albumtitle = F_albumtitle.getText();
                    int listeners =1000    
                    Connection connection = connect();
                     
                    try {
                    Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE Music_Mania");
                     stmt.executeUpdate("INSERT INTO Songs(SONG, LISTENERS, ALBUM_TITLE) VALUES ('"+sname+"','"+listeners+"',"+album_title+")");
                     JOptionPane.showMessageDialog(null,"song added!");
                     g.dispose();
                      
                    }
                     
                    catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }   
                     
                    }
                     
                });
                                 
                    g.add(create_but);
                    g.add(l1);
                    g.add(l2);
                    g.add(F_sname);
                    g.add(F_albumtitle);
                    g.setSize(350,200);//400 width and 500 height  
                    g.setLayout(null);//using no layout managers  
                    g.setVisible(true);//making the frame visible 
                    g.setLocationRelativeTo(null);
                             
    }
    });
     
    JButton add_album=new JButton("Add album"); //creating instance of JButton for adding albums
    add_album.setBounds(450,20,120,25); 
     
    add_album.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
                JFrame g = new JFrame("Enter album details");
                //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // set labels
                JLabel l1,l2,l3;  
                l1=new JLabel("Album name");  //label 1 for album name
                l1.setBounds(30,15, 100,30); 
                 
                 
                l2=new JLabel("no of tracks");  //label 2 for no of tracks
                l2.setBounds(30,53, 100,30);
               
                l3=new JLabel("Label"); 
                l3.setBounds(30,85,100,30);
     
                //set text field for album name
                JTextField F_album = new JTextField();
                F_album.setBounds(110, 15, 200, 30);
                 
                //set text field for no of tracks 
                JTextField F_tracks=new JTextField();
                F_tracks.setBounds(110, 53, 200, 30);
 
                //set text field for label
                JTextField F_label = new JTextField();
                F_label.setBounds(110,85,200,30);                        
                 
                JButton create_but=new JButton("Submit");//creating instance of JButton to submit details  
                create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
                create_but.addActionListener(new ActionListener() {
                     
                    public void actionPerformed(ActionEvent e){
                    String albname = F_album.getText();
                    String tracks = F_tracks.getText();
                    int tracks_int = Integer.parseInt(tracks);
                    String label = F_label.getText();
                    int streamings=1000;
                        
                    Connection connection = connect();
                     
                    try {
                     Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE Music_Mania");
                     stmt.executeUpdate("INSERT INTO ALBUM(ALBUM_TITLE, NO_OF_TRACKS, LABEL, STREAMINGS) VALUES ('"+albname+"','"+tracks_int+"',"+label+"','"+streamings")");
                     JOptionPane.showMessageDialog(null,"album added!");
                     g.dispose();
                      
                    }
                     
                    catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }   
                     
                    }
                     
                });
                                 
                    g.add(l3);
                    g.add(create_but);
                    g.add(l1);
                    g.add(l2);
                    g.add(F_albname);
                    g.add(F_tracks);
                    g.add(F_label);
                    g.setSize(350,200);//400 width and 500 height  
                    g.setLayout(null);//using no layout managers  
                    g.setVisible(true);//making the frame visible 
                    g.setLocationRelativeTo(null);
                             
    }
    });
    JButton add_artist=new JButton("Add artist details"); //creating instance of JButton for adding artists
    add_artist.setBounds(450,20,120,25); 
     
    add_artist.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e){
                JFrame g = new JFrame("Enter artist Details");
                //g.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                // set labels
                JLabel l1,l2;  
                l1 = new JLabel("Artist name");  //label 1 for artist name
                l1.setBounds(30,15, 100,30); 
                  
                l2 = new JLabel("label");  //label 2 for label
                l2.setBounds(30,53, 100,30);
               
                //set text field for artist name
                JTextField F_artist = new JTextField();
                F_artist.setBounds(110, 15, 200, 30);
                 
                //set text field for label 
                JTextField F_label = new JTextField();
                F_label.setBounds(110, 53, 200, 30);                        
                 
                JButton create_but=new JButton("Submit");//creating instance of JButton to submit details  
                create_but.setBounds(130,130,80,25);//x axis, y axis, width, height 
                create_but.addActionListener(new ActionListener() {
                     
                    public void actionPerformed(ActionEvent e){
                    String artist = F_artist.getText();
                    String label = F_label.getText();
                    int no_albums=1;
                        
                    Connection connection = connect();
                    try {
                     Statement stmt = connection.createStatement();
                     stmt.executeUpdate("USE Music_Mania");
                     stmt.executeUpdate("INSERT INTO ARTIST(ARTISTS_NAME VARCHAR2(20) PRIMARY KEY, TOTAL_ALBUM NUMBER(5), LABEL VARCHAR2(20)) VALUES ('"+artist+"','"+label+"',"+no_albums")");
                     JOptionPane.showMessageDialog(null,"artist added!");
                     g.dispose();
                      
                    }
                     
                    catch (SQLException e1) {
                        // TODO Auto-generated catch block
                         JOptionPane.showMessageDialog(null, e1);
                    }   
                     
                    }
                     
                });

                    g.add(create_but);
                    g.add(l1);
                    g.add(l2);
                    g.add(F_artist);
                    g.add(F_label);
                    g.setSize(350,200);//400 width and 500 height  
                    g.setLayout(null);//using no layout managers  
                    g.setVisible(true);//making the frame visible 
                    g.setLocationRelativeTo(null);
                             
    }
    });
}
}     
   