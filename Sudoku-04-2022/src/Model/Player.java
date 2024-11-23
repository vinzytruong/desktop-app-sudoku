package Model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Controller.ConnectDatabase;

public class Player {
	public String name;
	public String time;
	public Player(){
		this.name=new String();
		this.time=new String();
	}
	public Player(String name,String time) {
		this.name=new String(name);
		this.time=new String(time);
	}
	public List<Player> getTop3Player(String level) {
		List<Player> dsPlayer=new ArrayList<>();
		Statement s=null;
		Connection conn=null;
        ResultSet rs=null;
        String sql="select Name,Second,Level from score where Level=\""+level+"\" order by Second asc LIMIT 0,3";
        try {
        	conn=ConnectDatabase.connectMySQL();
            s = conn.createStatement();
            rs=s.executeQuery(sql);
            while(rs.next()) {
            	dsPlayer.add(new Player(rs.getString(1),rs.getString(2)));
            }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        ConnectDatabase.closeConnect(conn);
        return dsPlayer;
	}
	public static void insertPlayer(String name,int second,String level) {
		Connection conn=null;
		Statement s=null;
		String value="\'"+name+"\',"+second+",\'"+level+"\'";
		System.out.println(value);
		try {
			String sql="INSERT sudoku.score(Name,Second,Level) VALUES ("+value+")";
			conn=ConnectDatabase.connectMySQL();
			s=conn.createStatement();
			if(s.executeUpdate(sql)>0) System.out.println("Insert success !!!");
			else System.out.println("Insert failed !!!");
		} catch (Exception e) {
			System.out.println("Error");
		}finally {
			ConnectDatabase.closeConnect(conn);
		}
	}
	
	public static void main(String[] args) {
//		Player p=new Player();
//		List<Player> dsPlayer=new ArrayList<>();
//		dsPlayer=p.getTop3Player("medium");
//		for(int i=0;i<dsPlayer.size();i++) {
//			System.out.println(dsPlayer.get(i).name);
//			System.out.println(dsPlayer.get(i).time);
//		}
//		p.insertPlayer("Hai","00:00:11",11,"medium");
//		dsPlayer=p.getTop3Player("medium");
//		for(int i=0;i<dsPlayer.size();i++) {
//			System.out.println(dsPlayer.get(i).name);
//			System.out.println(dsPlayer.get(i).time);
//		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
}
