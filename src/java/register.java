/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author cgl
 */
@WebServlet(urlPatterns = {"/register"})
public class register extends HttpServlet {

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
       response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw=response.getWriter();
        response.setContentType("text/html");        
        
        String username1 = request.getParameter("username1");
        String password1 = request.getParameter("password1");
        String fullname = request.getParameter("fullname");
        
          try {
             MongoClient mongo = new MongoClient( "localhost" , 27017 );
             DB db = mongo.getDB("test");
             System.out.println("Connection established");
             DBCollection User = db.getCollection("User");
             BasicDBObject query = new BasicDBObject("username",  username1);
             DBCursor cursor = User.find(query);
             if(cursor.size()>0 || username1.length()<3)
             {
                 response.getWriter().write("This username is already taken!");
             }
             else{
                 BasicDBObject document = new BasicDBObject();
                 document.put("username", username1);
                 document.put("password", password1);
                 document.put("fullname", fullname);
                 document.put("follower_count", 0);
                 document.put("creation_date", new Date());
                 document.put("picture_url", "https://www.google.com.tr/search?q=cloud&client=ubuntu&hs=PwX&channel=fs&source=lnms&tbm=isch&sa=X&ved=0CAcQ_AUoAWoVChMI9vnwiaSUxgIVJZvbCh0X6AC8&biw=1301&bih=599#imgrc=8Vk2w2MZFJyxHM%253A%3Bn_-mi929XFO5mM%3Bhttp%253A%252F%252Fblog.xebialabs.com%252Fwp-content%252Fuploads%252F2014%252F12%252Fthe-cloud.jpg%3Bhttp%253A%252F%252Fblog.xebialabs.com%252F2014%252F12%252F09%252Fcan-ara-help-move-cloud%252F%3B1024%3B728");
                 List<Integer> tweets = new ArrayList();
                 document.put("tweet_id", tweets);
                 List<String> following = new ArrayList();
                 
                 document.put("following_username", following);
                 User.insert(document);
                   DBObject searchObject =new BasicDBObject();
                searchObject.put("username", username1);
                
                
              DBObject modifiedObject =new BasicDBObject();
                modifiedObject.put("$push", new BasicDBObject().append("following_username", "cgl"));
	    User.update(searchObject, modifiedObject);
            modifiedObject.put("$push", new BasicDBObject().append("following_username", "damlot"));
	    User.update(searchObject, modifiedObject);
                 response.getWriter().write("Registered!");
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
