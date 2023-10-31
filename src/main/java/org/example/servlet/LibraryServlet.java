package org.example.servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.DataBaseConnect;
import org.example.service.dto.LibraryDto;
import org.example.service.implService.LibraryServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
@WebServlet(name = "LibraryServlet", value = "/library/*")
public class LibraryServlet extends HttpServlet {
    private LibraryServiceImpl libraryService;
    ObjectMapper mapper = new ObjectMapper();
    @Override
    public void init() throws ServletException {
        //Object dataBase = getServletContext().getAttribute("dataBase");
        DataBaseConnect connect=new DataBaseConnect("org.postgresql.Driver","jdbc:postgresql://localhost:5432/restTemplate","postgres","Fox1997");
        this.libraryService = new LibraryServiceImpl(connect);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validGetRequests = "Valid requests:\n" +
                "/show\n" +
                "/show/?id=X\n" +
                "/show/?title=SomeTitle\n" +
                "/show/?id=X books\n" +
                "/show/?id=X authors\n\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (pathInfo.equals("show")) {
            if (queryString == null) {
                StringWriter writerl = new StringWriter();
                mapper.writeValue(writerl,libraryService.getAll());
                writer.write(writerl.toString());
            } else {
                String byId = req.getParameter("id");
                String title = req.getParameter("title");
                long id;
                if (byId != null) {
                    try {
                        id = Long.parseLong(byId);
                        String jsonLibrary = mapper.writeValueAsString(libraryService.findById(id));
                        writer.write(jsonLibrary);
                    } catch (NumberFormatException e) {
                        String[] splitParam = req.getParameter("id").split(" ");
                        id = Long.parseLong(splitParam[0]);
                        switch (splitParam[1]) {
                            case "books":
                                StringWriter writerl = new StringWriter();
                                mapper.writeValue(writerl,libraryService.getBooksById(id));
                                writer.write(writerl.toString());
                                break;
                            case "authors":
                                StringWriter writer2 = new StringWriter();
                                mapper.writeValue(writer2,libraryService.getAuthorsById(id));
                                writer.write(writer2.toString());
                                break;
                            default:
                                writer.write("Unknown parameter\n" + validGetRequests);
                                break;
                        }
                    }
                } else if (title != null) {
                    title = req.getParameter("title");
                    String jsonLibrary = mapper.writeValueAsString(libraryService.getByTitle(title));
                    writer.write(jsonLibrary);
                } else {
                    writer.write("Invalid request\n" + validGetRequests);
                }
            }
        } else {
            writer.write("Invalid request\n" + validGetRequests);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validPostRequests = "Valid requests:\n" +
                "/add\n" +
                "/add/?title=SomeTitle\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        System.out.println("POST");
        if (queryString == null) {
            writer.write("Request needed\n" + validPostRequests);
        } else if (pathInfo.equals("add")) {
            String title = req.getParameter("title");
            if (title != null) {
                title = req.getParameter("title");
                if (!title.isEmpty()) {
                    LibraryDto libraryDto=new LibraryDto();
                    libraryDto.setTitle(title);
                    libraryService.save(libraryDto);
                    String jsonLibrary = mapper.writeValueAsString(libraryService.save(libraryDto));
                    writer.write("Library added");
                    writer.write(jsonLibrary);
                } else {
                    writer.write("Unable to add new library");
                }
            }
        } else {
            writer.write("Invalid request\n" + validPostRequests);
        }
    }
    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validPutRequests = "Valid requests:\n" +
                "/edit/?id=x&title=SomeTitle\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (queryString == null) {
            writer.write("request needed");
        } else if (pathInfo.equals("edit")) {
            Map<String, String[]> parameterMap = req.getParameterMap();
            String byId = parameterMap.get("id")[0];
            String title = parameterMap.get("title")[0];
            long id;
            if (byId != null && title != null) {
                try {
                    id = Long.parseLong(byId);
                    LibraryDto libraryDto=new LibraryDto();
                    libraryDto.setTitle(title);
                    libraryService.updateById(id,libraryDto);
                    String jsonLibrary = mapper.writeValueAsString(libraryService.updateById(id,libraryDto));
                    writer.write("Update successful:");
                    writer.write(jsonLibrary);

                } catch (NumberFormatException e) {
                    writer.write("Invalid id\n" + validPutRequests);
                }
            }
        } else {
            writer.write("Invalid request\n" + validPutRequests);
        }
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        String validDeleteRequests = "Valid requests:\n" +
                "/delete/?id=X\n";
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (queryString == null) {
            writer.write("List of libraries:\n");
            StringWriter writerl = new StringWriter();
            mapper.writeValue(writerl,libraryService.getAll());
            writer.write(writerl.toString());
        } else if (pathInfo.equals("delete")) {
            String byId = req.getParameter("id");
            long id;
            if (byId != null) {
                try {
                    id = Long.parseLong(byId);
                    boolean isDeleted = libraryService.deleteById(id);
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
