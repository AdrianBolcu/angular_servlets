/**
 *
 */
package ro.mindit.book.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.json.JSONObject;
import ro.mindit.book.dao.BookDao;
import ro.mindit.book.model.Book;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class BookResource extends HttpServlet {

    private BookDao bookDao;

    @Override
    public void init() throws ServletException {
        bookDao = new BookDao();
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // set response content type
        response.setContentType("application/json");

//        String json = getTodoFromMemory(request);
        String json = getBookFromDb(request);//convertToJson(todoDao.getTodoFromDb(request));  se apeleaza o metoda din dao
/*
        List<Todo> allTodos = todoDao.getTodoFromDb();
        String json = convertToJson(allTodos);

 */
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }



    @Override
    public void destroy() {
    }

    private String getBookFromDb(HttpServletRequest request) throws JsonProcessingException {
        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();

        String id = request.getQueryString();
        try {
            // Connect to the database
            bookDao.connect();

            if (id != null) {
                id = id.replace("id=", "");
                Book book = bookDao.findOne(Integer.parseInt(id));
                json = objectMapper.writeValueAsString(book);
            } else {
                List<Book> books = bookDao.findAll();
                json = objectMapper.writeValueAsString(books);
            }

            // Disconnect from the database
            bookDao.disconnect();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return json;
    }

    private String getBookFromMemory(HttpServletRequest request) throws JsonProcessingException, SQLException {

        String json = "";
        ObjectMapper objectMapper = new ObjectMapper();

        String id = request.getQueryString();
        if (id != null) {
            Book book = bookDao.getBook(1);
            json = objectMapper.writeValueAsString(book);
        } else {
            List<Book> books = bookDao.queryAll();
            json = objectMapper.writeValueAsString(books);
        }
        return json;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);
        } catch (Exception e) { /*report an error*/ }

        try (PrintWriter out = response.getWriter()) {
            JSONObject jsonObject = new JSONObject(jb.toString());

            Integer id = jsonObject.getInt("id");
            String name = jsonObject.getString("name");
            String author = jsonObject.getString("author");
            String type = jsonObject.getString("type");

            Book book = buildBook(id, name, author, type);

            int status = bookDao.save(book);
            if (status > 0) {
                out.print("<p>Record saved successfully!</p>");
            } else {
                out.println("Sorry! unable to save record");
            }

        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        response.setContentType("text/html");
        PrintWriter out=response.getWriter();


        int id = Integer.parseInt(request.getQueryString());;
        String name = request.getParameter("name");
        String author = request.getParameter("author");
        String type = request.getParameter("type");

        Book book = buildBook(id, name, author, type);

        int status = 0;

        status = bookDao.updateBook(book);

        if(status>0){
            out.print("<p>Record saved successfully!</p>");
            request.getRequestDispatcher("index.html").include(request, response);
        }else{
            out.println("Sorry! unable to save record");
        }

        out.close();
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){
        String bookId=request.getParameter("id");
        int id=Integer.parseInt(bookId);
        BookDao.delete(id);

    }

    private Book buildBook(Integer id, String name, String author, String type) {
        Book book = new Book();
        book.setId(id);
        book.setName(name);
        book.setAuthor(author);
        book.setType(type);
        return book;
    }



}