package exercise1.liangyuwang_comp228lab5;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//liangyu
//980025288

public class Game extends Application {

    // Create TextFields
    TextField fnametf = new TextField("");
    TextField lnametf = new TextField("");
    TextField addresstf = new TextField("");
    TextField provincetf = new TextField("");
    TextField pcodetf = new TextField("");
    TextField phonetf = new TextField("");
    TextField updatetf = new TextField("");
    TextField titletf = new TextField("");
    TextField scoretf = new TextField("");
    TextField datetf = new TextField("");

    private static Connection connection;

    @Override
    public void start(Stage stage) {

        try {

            //establish database connection
            System.out.println("> Start Program ...");
            Class.forName("oracle.jdbc.driver.OracleDriver");
            System.out.println("> Driver Loaded successfully.");

            connection=
                    DriverManager.getConnection("jdbc:oracle:thin:@ 199.212.26.208:1521:SQLD","COMP228_w22_sy_108", "password");
                    System.out.println("Database connected successfully.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        // create labels
        Label infolb = new Label("Player Information:");
        infolb.setStyle("-fx-font-weight: bold");
        Label fnamelb = new Label("First Name:");
        Label lnamelb = new Label("Last Name:");
        Label addresslb = new Label("Address:");
        Label provincelb = new Label("Province:");
        Label pcodelb = new Label("Postal Code:");
        Label phonelb = new Label("Phone Number:");
        Label updatelb = new Label("Update player by ID:");
        Label gamelb = new Label("Game Information:");
        gamelb.setStyle("-fx-font-weight: bold");
        Label titlelb = new Label("Game Title:");
        Label scorelb = new Label("Game Score:");
        Label datelb = new Label("Date Played:");

        // create buttons
        Button upbtn = new Button("Update");
        Button createbtn = new Button("Create Player");
        Button displaybtn = new Button("Display All Players");

        //create GUI layout components
        HBox hbox = new HBox(8);
        VBox vbox = new VBox(8);
        GridPane pane1 = new GridPane();
        GridPane pane2 = new GridPane();
        GridPane pane3 = new GridPane();
        GridPane pane4 = new GridPane();

        // place GUI components
        pane1.addRow(0, infolb);
        pane1.addRow(1, fnamelb, fnametf);
        pane1.addRow(2, lnamelb, lnametf);
        pane1.addRow(3, addresslb, addresstf);
        pane1.addRow(4, provincelb, provincetf);
        pane1.addRow(5, pcodelb, pcodetf);
        pane1.addRow(6, phonelb, phonetf);
        pane2.addRow(0, updatelb, updatetf, upbtn);
        pane3.addRow(0, gamelb);
        pane3.addRow(1, titlelb, titletf);
        pane3.addRow(2, scorelb, scoretf);
        pane3.addRow(3, datelb, datetf);
        pane4.addRow(0, createbtn, displaybtn);

        // set padding and spacing
        pane1.setHgap(5);
        pane1.setVgap(5);
        pane1.setPadding(new Insets(10, 10, 10, 10));
        pane2.setHgap(5);
        pane2.setVgap(5);
        pane2.setPadding(new Insets(30, 10, 10, 10));
        pane3.setHgap(5);
        pane3.setVgap(5);
        pane3.setPadding(new Insets(10, 10, 10, 10));
        pane4.setHgap(5);
        pane4.setVgap(5);
        pane4.setPadding(new Insets(20, 10, 10, 60));

        // display GUI components
        vbox.getChildren().addAll(pane2, pane3, pane4);
        hbox.getChildren().addAll(pane1, vbox);

        //update button handler
        updateHandler h1 = new updateHandler();
        upbtn.setOnAction(h1);

        //create button handler
        createHandler h2= new createHandler();
        createbtn.setOnAction(h2);

        //display button handler
        displayHandler h3= new displayHandler();
        displaybtn.setOnAction(h3);

        // display scene
        Scene scene = new Scene(hbox, 650, 320);
        stage.setTitle("Player Registration");
        stage.setScene(scene);

        stage.show();


    }

    //update button action handler
    class updateHandler implements EventHandler <ActionEvent>
    {
        @Override
        public void handle(ActionEvent e)
        {

            String update= updatetf.getText();
            String fname = fnametf.getText();
            String lname = lnametf.getText();
            String addr = addresstf.getText();
            String province = provincetf.getText();
            String pcode= pcodetf.getText();
            String phone= phonetf.getText();

            // update player information by player ID SQL query
            try {

                String sql = "UPDATE PLAYER SET " +
                        "FIRST_NAME = ?, "   +
                        "LAST_NAME = ?, "    +
                        "ADDRESS = ?, "      +
                        "PROVINCE = ?, "     +
                        "POSTAL_CODE = ?, "  +
                        "PHONE_NUMBER = ? "  +
                        "WHERE PLAYER_ID = ?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, addr);
                ps.setString(4, province);
                ps.setString(5, pcode);
                ps.setString(6, phone);
                ps.setString(7, update);

                System.out.println(sql);
                int rows = ps.executeUpdate();
                System.out.println(rows+ " Player " + update + " updated.");
            } catch(SQLException ex) {
                ex.printStackTrace();
            }


        }
    }

    //create button action handler
    class createHandler implements EventHandler <ActionEvent>
    {
        @Override
        public void handle(ActionEvent e)
        {
            // get information from textfields
            int score =0;
            String fname = fnametf.getText();
            String lname = lnametf.getText();
            String addr = addresstf.getText();
            String province = provincetf.getText();
            String pcode= pcodetf.getText();
            String phone= phonetf.getText();

            String title= titletf.getText();
            score= scoretf.getText().isEmpty()?0:Integer.parseInt(scoretf.getText());
            String date= datetf.getText();
            String generatedColumns[] = { "PLAYER_ID" };
            String generatedColumns2[] = { "GAME_ID" };


            try{
                Statement stm = connection.createStatement();
                int pid=-1;
                int gid=-1;
                int s=-1;

                // insert player information into PLAYER table SQL query
                String sql1= "INSERT INTO PLAYER (FIRST_NAME, LAST_NAME, ADDRESS, PROVINCE, POSTAL_CODE, PHONE_NUMBER) VALUES ( ?, ?, ?, ?, ?, ? )";
                PreparedStatement ps = connection.prepareStatement(sql1, generatedColumns);
                ps.setString(1, fname);
                ps.setString(2, lname);
                ps.setString(3, addr);
                ps.setString(4, province);
                ps.setString(5, pcode);
                ps.setString(6, phone);
                System.out.println(sql1);
                s=ps.executeUpdate();

                //retrieve player id
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    pid = rs.getInt(1);
                    System.out.println("Inserted Player ID:" + pid);
                }

                // insert game information into GAME table SQL query
                String sql2= "INSERT INTO GAME (GAME_TITLE) VALUES (?)";
                ps = connection.prepareStatement(sql2, generatedColumns2);
                ps.setString(1, title);
                System.out.println(sql2);
                s=ps.executeUpdate();

                //retrieve game id
                ResultSet ks = ps.getGeneratedKeys();
                if (ks.next()) {
                    gid = ks.getInt(1);
                    System.out.println("Inserted Game ID:" + gid);
                }

                // insert player and game information into PLAYERANDGAME table SQL query
                String sql3= "INSERT INTO PLAYERANDGAME (GAME_ID, PLAYER_ID, PLAYING_DATE, SCORE) VALUES ("
                        +"'"+gid+"', "
                        +"'"+pid+"', "
                        +"'"+date+"', "
                        +score+ ")";
                System.out.println(sql3);
                stm.executeUpdate(sql3);


            } catch(SQLException ex) {
                ex.printStackTrace();
            }
        }

    }


    //display button action handler
    class displayHandler implements EventHandler <ActionEvent>
    {
        @Override
        public void handle(ActionEvent e)
        {
            try
            {
                // retrieve player information from PLAYER table using SQL query
                String query = "SELECT * FROM PLAYER";

                Statement stm = connection.createStatement();
                ResultSet res = stm.executeQuery(query);

                String columns[] = { "ID", "First Name", "Last Name", "Address", "Postal Code", "Province", "Phone Number" };
                String data[][] = new String[50][7];

                int i = 0;
                while (res.next()) {
                    int id = res.getInt("PLAYER_ID");
                    String fname = res.getString("FIRST_NAME");
                    String lname = res.getString("LAST_NAME");
                    String addr = res.getString("ADDRESS");
                    String pcode = res.getString("POSTAL_CODE");
                    String province = res.getString("PROVINCE");
                    String phone = res.getString("PHONE_NUMBER");
                    data[i][0] = id + "";
                    data[i][1] = fname;
                    data[i][2] = lname;
                    data[i][3] = addr;
                    data[i][4] = pcode;
                    data[i][5] = province;
                    data[i][6] = phone;
                    i++;
                }

                // create JTable GUI component
                DefaultTableModel model = new DefaultTableModel(data, columns);
                JTable table = new JTable(model);
                table.setShowGrid(true);
                table.setShowVerticalLines(true);
                JScrollPane pane = new JScrollPane(table);
                JFrame f = new JFrame("Player Information");
                JPanel panel = new JPanel();
                panel.add(pane);
                f.add(panel);

                // display JTable
                table.getColumnModel().getColumn(1).setPreferredWidth(90);
                table.getColumnModel().getColumn(2).setPreferredWidth(90);
                table.getColumnModel().getColumn(3).setPreferredWidth(110);
                table.getColumnModel().getColumn(4).setPreferredWidth(80);
                table.getColumnModel().getColumn(6).setPreferredWidth(150);
                f.setSize(700, 450);
                table.setPreferredScrollableViewportSize(table.getPreferredSize());
                table.setFillsViewportHeight(true);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setVisible(true);


            } catch(SQLException ex) {
                ex.printStackTrace();
            }

        }
    }




    public static void main(String[] args)
    {
        Game.launch();

        // close database connection
        try{
            connection.close();
            System.out.println("> Connection closed successfully.");
        }catch(SQLException e){
            System.out.println("ERROR while closing connection!");
            System.out.println(e.toString());
        }
    }
}

