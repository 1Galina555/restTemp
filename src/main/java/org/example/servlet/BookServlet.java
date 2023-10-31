package org.example.servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.DataBaseConnect;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.BookDto;
import org.example.service.implService.BookServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(name = "BookServlet", value = "/book/*")
public class BookServlet extends HttpServlet {
    private BookServiceImpl bookService;
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void init() throws ServletException {
        //Object dataBase = getServletContext().getAttribute("dataBase");
        DataBaseConnect connect=new DataBaseConnect("org.postgresql.Driver","jdbc:postgresql://localhost:5432/restTemplate","postgres","Fox1997");
        this.bookService = new BookServiceImpl(connect);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validGetRequests = "Valid requests:\n" +
                "/show/?id=x\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (pathInfo.equals("show")) {
            if (queryString == null) {
                writer.write(validGetRequests);
            } else {
                String byId = req.getParameter("id");
                long id;
                if (byId != null && !byId.isEmpty()) {
                    try {
                        id = Long.parseLong(byId);
                        String jsonBook = mapper.writeValueAsString(bookService.findById(id));
                        writer.write(jsonBook);
                    } catch (NumberFormatException e) {
                        writer.write("Invalid id\n" + validGetRequests);
                    }
                } else {
                    writer.write("Invalid request\n" + validGetRequests);
                }
            }
        } else {
            writer.write("Invalid id\n" + validGetRequests);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validPostRequests = "Valid requests:\n" +
                "/add/?title=SomeTitle&genre=someGenre&library_id=x&author_id=y\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        //String pathInfo = req.getPathInfo().split("/")[1];
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
        }
        if (queryString == null) {
            writer.write("Request needed\n" + validPostRequests);
        } else if (pathInfo.equals("add")) {
            Map<String, String[]> parameterMap = req.getParameterMap();
            if (parameterMap != null && !parameterMap.isEmpty()) {
                try {
                    String title = parameterMap.get("title")[0];
                    String genre = parameterMap.get("genre")[0];
                    String libraryId = parameterMap.get("library_id")[0];
                    String authorId = parameterMap.get("author_id")[0];
                    try {
                        long idLibrary = Long.parseLong(libraryId);
                        long idAuthor = Long.parseLong(authorId);
                    } catch (NumberFormatException e) {
                        writer.write("Invalid input\n" + validPostRequests);
                    }
                    if (!title.isEmpty()&&!genre.isEmpty()&&!libraryId.isEmpty()&&!authorId.isEmpty()) {
                        long idLibrary = Long.parseLong(libraryId);
                        long idAuthor = Long.parseLong(authorId);
                        BookDto bookDto=new BookDto();
                        bookDto.setTitle(title);
                        bookDto.setGenre(genre);
                        bookDto.setLibraryId(idLibrary);
                        bookDto.setAuthorId(idAuthor);
                        bookService.save(bookDto);
                        writer.write("Book added");
                        String jsonAuthor = mapper.writeValueAsString(bookService.save(bookDto));
                        writer.write(jsonAuthor);
                    } else {
                        writer.write("Unable to add new book");
                    }
                } catch (NullPointerException e) {
                    writer.write("Invalid input\n" + validPostRequests);
                }
            }
        } else {
            writer.write("Invalid request\n" + validPostRequests);
        }
    }
    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String validDeleteRequests = "Valid requests:\n" +
                "/delete/?id=X\n";
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (queryString == null && pathInfo.equals("delete")) {
            writer.write("List of libraries:\n");
        } else if (pathInfo.equals("delete")) {
            String byId = req.getParameter("id");
            long id;
            if (byId != null) {
                try {
                    id = Long.parseLong(byId);
                    boolean isDeleted = bookService.deleteById(id);
                    if (isDeleted) {
                        writer.write("Delete successful");
                    } else {
                        writer.write("Unable to delete");
                    }
                } catch (NumberFormatException e) {
                    writer.write("Invalid id\n" + validDeleteRequests);
                }
            }
        } else {
            writer.write("Invalid request\n" + validDeleteRequests);
        }
    }
}
