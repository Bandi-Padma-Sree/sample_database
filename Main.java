import java.util.ArrayList;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Date;
class Company
{
	String coid,coname,coloc;
	Company(String coid,String coname, String coloc)
	{
		this.coid = coid;
		this.coname = coname;
		this.coloc = coloc;
	}
	Company(String coid)
	{
		this.coid = coid;
	}
	public String toString()
	{
	  return ("\nCompany ID: "+coid+
	  		"\nCompany Name: "+coname+
	  		"\nCompany Address: "+coloc);
	}
}
class Product
{
	String pid,pname,coid;
	long pcost;
	Blob image;
	Product(String pid,String pname,long pcost,String coid,Blob image)
	{
		this.coid = coid;
		this.pid = pid;
		this.pname = pname;
		this.pcost = pcost;
		this.image = image;
	}
	public String toString()
	{
	  return ("\nImage: "+image+
			"\nProduct ID: "+pid+
	  		"\nProduct Name: "+pname+
	  		"\nProduct Cost: "+pcost+
	  		"\nCompany ID: "+coid);
	}
}
class Customer
{
	String cid,cname,caddress,cnumber,cmail;
	Customer(String cid,String cname, String caddress, String cnumber, String cmail)
	{
		this.cid = cid;
		this.cname = cname;
		this.caddress = caddress;
		this.cnumber = cnumber;
		this.cmail = cmail;
	}
	public String toString()
	{
	  return ("\nCustomer ID: "+cid+
	  		"\nCustomer Name: "+cname+
	  		"\nCustomer Address: "+caddress+
	  		"\nCustomer Number: "+cnumber+
	  		"\nCustomer MailID: "+cmail);
	}
}
class Transaction
{
	String tid,pid,cid;
	Date dob,dod;
	Transaction(String tid,String pid,String cid,Date dob,Date dod)
	{
		this.tid = tid;
		this.pid = pid;
		this.cid = cid;
		this.dob = dob;
		this.dod = dod;
	}
	public String toString()
	{
	  return ("\nTransaction ID: "+tid+
			  "\nProduct ID: "+pid+
			  "\nCostumer ID: "+cid+
			  "\nDate Of Booking: "+dob+
			  "\nDate of Delivery: "+dod);
	}
}
class ManageDB implements DBInterface{
	
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/Sample";
	static final String USER = "root";
	static final String PASS = "2521";
	
	public boolean editData(String query)
	{
		Scanner sc = new Scanner(System.in);
		Connection conn = null;
		Statement stmt = null;
		 try{
			 Class.forName("com.mysql.cj.jdbc.Driver");

		      //STEP 3: Open a connection
		      //System.out.println("Connecting to a selected database...");
		      conn = DriverManager.getConnection(DB_URL, USER, PASS);
		      //System.out.println("Connected database successfully...");
		      
		      //STEP 4: Execute a query
		      System.out.println("Performing the queries...");
		      stmt = conn.createStatement();
		      
		      if(query.contains("product") && query.contains("insert"))
		      {
		    	  PreparedStatement pstmt = conn.prepareStatement(query);
		    	  System.out.println("Enter path file for image");
		    	  FileInputStream image = new FileInputStream(sc.nextLine());
		    	  pstmt.setBinaryStream(1, image);
		    	  pstmt.execute();
		    	  
		    	  /*insert into product values('p444','shoes','500','co100', ?)
					Performing the queries...
					Enter path file for image
					C:\Users\Lenovo\Documents\SIGMANALYTICS\watch1.jpeg
					Successfully performed queries
					Enter 1 to continue entry of queries*/

		      } 
		      else
		    	  stmt.executeUpdate(query);
		      stmt.close();
		      conn.close();
		   }
		 catch(Exception e)
		   {
			   System.out.println(e);
			   return false;
		   }
		 return true;
	}
    public ArrayList<Object> getRows(String query)
    {
    	Connection conn = null;
		Statement stmt = null;
		
		ArrayList<Object> obj = new ArrayList<Object>();
	      
		 try{
		      //STEP 2: Register JDBC driver
		      Class.forName("com.mysql.cj.jdbc.Driver");

		      //STEP 3: Open a connection
		      //System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);
		      //System.out.println("Connected database successfully...");
		      stmt = conn.createStatement();
		      
		      ResultSet rs = stmt.executeQuery(query);
		      //STEP 5: Extract data from result set
		      int n=0;
		      
		      if(query.contains("company"))
		    	  n = 1;
		      else if(query.contains("product"))
		    	  n = 2;
		      else if(query.contains("customer"))
		    	  n = 3;
		      else if(query.contains("transaction"))
		    	  n = 4;
		      while(rs.next()){
		         switch(n)
		         {
		         case 1:
		    	  Company company = new Company(rs.getString("coid"),
		        		 rs.getString("coname"),
		        		 rs.getString("coloc"));
		         obj.add(company);
		         break;
		         case 2:
		         Product product = new Product(rs.getString("pid"),
		        		 rs.getString("pname"),
		        		 rs.getLong("pcost"),
		        		 rs.getString("coid"),
		        		 rs.getBlob("image"));
		         obj.add(product);
		         break;
		         case 3:
		         Customer customer = new Customer(rs.getString("cid"),
		        		 rs.getString("cname"),
		        		 rs.getString("cadd"),
		        		 rs.getString("cno"),
		        		 rs.getString("cmail"));
		         obj.add(customer);
		         break;
		         case 4:
		        	 Transaction transaction = new Transaction(rs.getString("tid"),
		        		 rs.getString("pid"),
		        		 rs.getString("cid"),
		        		 rs.getDate("dob"),
		        		 rs.getDate("dod"));
		         obj.add(transaction);
		         break;
		      }
		      }
		      //STEP 6: Clean-up environment
		      rs.close();
		      stmt.close();
		      conn.close();
		 }
		 catch(Exception e)
		 {
			 System.out.println(e);
		 }
		 return obj;
    }
}
public class Main{
	public static void main(String[] args) {
		   
		   Scanner sc = new Scanner(System.in);
		      
		      //final String MENU = new String("Choose from the queries : 1.Insert		2.Update		3.Delete");
		      
		   ManageDB DBobj = new ManageDB();
		      do {
		    	  String sql = sc.nextLine();
		    	  if(DBobj.editData(sql))
				      System.out.println("Successfully performed queries");
		    	  else

				      System.out.println("Error in execution");
		    	  System.out.println("Enter 1 to continue entry of queries");
		      }while(sc.nextLine().equals("1"));
		   		ArrayList<Object> list[] = new ArrayList[4];
		      list[0] = new ArrayList<Object>(DBobj.getRows("select * from company"));
		      list[1] = new ArrayList<Object>(DBobj.getRows("select * from product"));
		      list[2] = new ArrayList<Object>(DBobj.getRows("select * from customer"));
		      list[3] = new ArrayList<Object>(DBobj.getRows("select * from transaction"));
		      for(int i = 0 ; i < 4 ; i++)
		      {
		    	  objectDisplay(list[i]);
		      }
		}  
	static void objectDisplay(ArrayList<Object> obj)
	{
		for(int i = 0 ; i < obj.size(); i++)
			System.out.println(obj.get(i));
	}
}