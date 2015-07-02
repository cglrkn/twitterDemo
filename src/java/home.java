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
@WebServlet(urlPatterns = {"/home"})
public class home extends HttpServlet {

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
        
        String tweet = request.getParameter("tweet");  
        try {
             MongoClient mongo = new MongoClient( "localhost" , 27017 );
             DB db = mongo.getDB("test");
             System.out.println("Connection established");
             DBCollection User = db.getCollection("User");
             DBCollection Tweet = db.getCollection("Tweet");
     
             DBCursor cursor = Tweet.find();
             int count=0;
              if(cursor.size() < 1)
              {
                 count=1; 
              }
              else{
                  count = cursor.size()+1;
                  
              }
              BasicDBObject document = new BasicDBObject();
              document.put("tweet_id", count);
               document.put("username", username);
              document.put("tweet_text", tweet);
              document.put("date",new Date() );
              Tweet.insert(document);
              
              ArrayList<Integer> tweet_id = new ArrayList();
              tweet_id.add(count);
              DBObject searchObject =new BasicDBObject();
                searchObject.put("username", username);
                
                
              DBObject modifiedObject =new BasicDBObject();
                modifiedObject.put("$push", new BasicDBObject().append("tweet_id", count));
	    User.update(searchObject, modifiedObject);
                
             out.println(tweet);
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
