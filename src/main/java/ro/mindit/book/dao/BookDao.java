package ro.mindit.book.dao;

import ro.mindit.book.model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ro.mindit.book.util.Constants.*;

public class BookDao {
    private static int idSeq = 1;
    private List<Book> books;

    private static Connection jdbcConnection;

    public BookDao() {
        final Book book = new Book() {{
            setId(idSeq++);
            setName("Todo 1");
            setAuthor("Adi");
            setType("Business");
        }};
        books = new ArrayList<Book>() {{
            add(book);
        }};
    }

    public List<Book> queryAll() {
        return books;
    }

    public Book getBook(int id) {
        Book result = null;
        for (Book book : books) {
            if (book.getId() == id) {
                result = book;
                break;
            }
        }
        return result;
    }


    public static void connect() throws SQLException {
        if (jdbcConnection == null || jdbcConnection.isClosed()) {
            try {
                Class.forName(jdbcDriver);
            } catch (ClassNotFoundException e) {
                throw new SQLException(e);
            }
            jdbcConnection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
    }

    public static void disconnect() throws SQLException {
        if (jdbcConnection != null && !jdbcConnection.isClosed()) {
            jdbcConnection.close();
        }
    }

    public Book findOne(int id) throws SQLException {
        Book book = null;
        String sql = "SELECT * FROM book WHERE id = ?";

        connect();

        PreparedStatement statement = jdbcConnection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            String type = resultSet.getString("type");

            book = new Book(id, name, author, type);
        }

        resultSet.close();
        statement.close();

        return book;
    }

    public List<Book> findAll() throws SQLException {
        List<Book> bookList = new ArrayList<Book>();

        String sql = "SELECT * FROM book";

        connect();

        Statement statement = jdbcConnection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            String type = resultSet.getString("type");
            Book book = new Book(id, name, author, type);
            bookList.add(book);
        }

        resultSet.close();
        statement.close();

        disconnect();

        return bookList;
    }

    public int save(Book book){
        int status = 0;
        String sql = "INSERT INTO book (id,name,author,type) VALUES (?,?,?,?); ";
        try {
            connect();
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setInt(1,book.getId());
            statement.setString(2,book.getName());
            statement.setString(3,book.getAuthor());
            statement.setString(4,book.getType());
            status = statement.executeUpdate();
            statement.close();
            disconnect();

            System.out.println("Successfully added new row " + book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public int updateBook( Book book){
        int status = 0;
        try{
            String sql = "UPDATE book SET name=?,author=?,type=? WHERE id=?;";
            connect();
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);

            statement.setInt(4,book.getId());
            statement.setString(1,book.getName());
            statement.setString(2,book.getAuthor());
            statement.setString(3,book.getType());
            status = statement.executeUpdate();
            statement.close();
            disconnect();

            System.out.println("Successfully updated " + book);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static int delete(int id)  {
        int status = 0;
        String sql = "DELETE from book where id=?";
        try {
            connect();
            PreparedStatement statement = jdbcConnection.prepareStatement(sql);
            statement.setInt(1,id);
            status = statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;

    }
    public static void main(String [] args){
        // save(new Todo(3,"x","x","x"));
        // delete(3);
        // updateTodo(new Todo(3,"z","x","x"));
    }

}
