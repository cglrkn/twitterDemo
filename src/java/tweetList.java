/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cgl
 */
@WebServlet(urlPatterns = {"/tweetList"})
public class tweetList extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out=response.getWriter();
        response.setContentType("text/html");        
        String username = request.getParameter("username");
       
        try {
             MongoClient mongo = new MongoClient( "localhost" , 27017 );
             DB db = mongo.getDB("test");
             System.out.println("Connection established");
             DBCollection User = db.getCollection("User");
             DBCollection Tweet = db.getCollection("Tweet");
     
            BasicDBObject query = new BasicDBObject("username",  username);
             DBCursor curssc = User.find(query);
            // out.println(curssc.size());
             DBObject result = curssc.next();
             ArrayList<String> arr2 = new ArrayList();
             arr2 = (ArrayList<String>) result.get("tweet_id");
             
             ArrayList<String> e = new ArrayList();
             e = (ArrayList<String>) result.get("following_username");
              ArrayList<String> texts = new ArrayList();
             //out.println(e.size());
             //out.println(e.get(0));
                
               if(e.size()>0 || arr2.size()>0)
               {
                   for(int i=e.size()-1; i>=0; i--)
                   {
                        BasicDBObject query2 = new BasicDBObject("username",  e.get(i));
                        DBCursor curssc2 = User.find(query2);
                        if(curssc2.size()==1)
                        {
                            DBObject res = curssc2.next();
                            ArrayList<String> arr = new ArrayList();
                            arr = (ArrayList<String>) res.get("tweet_id") ;
                            
                            texts.addAll(arr);
                            
                        }
                    
                   }
                   texts.addAll(arr2);
                   Collections.sort(texts);
                   for(int j=texts.size()-1; j>=0; j--)
                            {
                                BasicDBObject query3 = new BasicDBObject("tweet_id",  texts.get(j ));
                                DBCursor curssc3 = Tweet.find(query3);
                                DBObject result1 =curssc3.next();
                                response.getWriter().write(result1.get("username").toString());
                                 response.getWriter().write(":  ");
                                response.getWriter().write(result1.get("tweet_text").toString());
                                response.getWriter().write("\n");
                            }
                   
               }
               else{
                   response.getWriter().write("No tweet");
               }
              
                
            
             
         }catch(Exception e){
	     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
