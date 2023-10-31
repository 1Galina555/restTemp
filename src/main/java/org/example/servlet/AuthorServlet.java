package org.example.servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.db.DataBaseConnect;
import org.example.service.dto.AuthorDto;
import org.example.service.implService.AuthorServiceImpl;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

@WebServlet(name = "AuthorServlet", value = "/author/*")
public class AuthorServlet extends HttpServlet {
    private  AuthorServiceImpl authorService;
    ObjectMapper mapper = new ObjectMapper();



    @Override
    public void init() throws ServletException {
        //Object dataBase = getServletContext().getAttribute("dataBase");
        DataBaseConnect connect=new DataBaseConnect("org.postgresql.Driver","jdbc:postgresql://localhost:5432/restTemplate","postgres","Fox1997");
        this.authorService = new AuthorServiceImpl(connect);
    }
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validGetRequests = "Valid requests:\n" +
                "/show\n" +
                "/show/?id=x\n" +
                "/show/?name=SomeName&lastname=SomeLastName\n" +
                "/show/?id=x books\n" +
                "/show/?id=x libraries\n\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (pathInfo.equals("show")) {
            if (queryString == null) {
                StringWriter writerl = new StringWriter();
                mapper.writeValue(writerl,authorService.getAllAuthors());
                writer.write(writerl.toString());
            } else {
                String byId = req.getParameter("id");
                Map<String, String[]> parameterMap = req.getParameterMap();
                long id;
                if (byId != null && !byId.isEmpty()) {
                    try {
                        id = Long.parseLong(byId);
                        String jsonAuthor = mapper.writeValueAsString(authorService.findById(id));
                        writer.write(jsonAuthor);
                    } catch (NumberFormatException e) {
                        String[] splitParam = req.getParameter("id").split(" ");
                        id = Long.parseLong(splitParam[0]);
                        switch (splitParam[1]) {
                            case "books":
                                StringWriter writerl = new StringWriter();
                                mapper.writeValue(writerl,authorService.getBooksById(id));
                                writer.write(writerl.toString());
                                break;
                            case "libraries":
                                StringWriter writer2 = new StringWriter();
                                mapper.writeValue(writer2,authorService.getLibraryById(id));
                                writer.write(writer2.toString());
                                break;
                            default:
                                writer.write("unknown parameter\n" + validGetRequests);
                                break;
                        }
                    }
                } else if (parameterMap != null && !parameterMap.isEmpty()) {
                    try {
                        String name = parameterMap.get("name")[0];
                        String lastName = parameterMap.get("lastname")[0];
                        String jsonAuthor = mapper.writeValueAsString(authorService.getByFullName(name, lastName));
                        writer.write(jsonAuthor);
                    } catch (NullPointerException e) {
                        writer.write("Incorrect input\n" + validGetRequests);
                    }
                }
            }
        } else {
            writer.write("Unknown operation\n" + validGetRequests);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validPostRequests = "Valid requests:\n" +
                "/add/?name=SomeName&lastname=SomeLastName\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        System.out.println("POST");
        if (queryString == null) {
            writer.write("Request needed");
        } else if (pathInfo.equals("add")) {
            Map<String, String[]> parameterMap = req.getParameterMap();
            if (parameterMap != null && !parameterMap.isEmpty()) {
                try {
                    String name = parameterMap.get("name")[0];
                    String lastName = parameterMap.get("lastname")[0];
                    if (!name.isEmpty()&&!lastName.isEmpty()) {
                        AuthorDto authorDto=new AuthorDto();
                        authorDto.setName(name);
                        authorDto.setLastName(lastName);
                        authorService.addAuthor(authorDto);
                        String jsonAuthor = mapper.writeValueAsString(authorService.addAuthor(authorDto));
                        writer.write("Author added:");
                        writer.write(jsonAuthor);
                    } else {
                        writer.write("Unable to add new author");
                    }
                } catch (NullPointerException e) {
                    writer.write("Incorrect input\n" + validPostRequests);
                }
            }
        } else {
            writer.write("Invalid request\n" + validPostRequests);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String validPutRequests = "Valid requests:\n" +
                "/edit/?id=x&name=SomeName&lastname=SomeLastName\n";
        PrintWriter writer = resp.getWriter();
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (queryString == null) {
            writer.write("request needed");
            writer.write("Current list of authors:");
            StringWriter writerl = new StringWriter();
            mapper.writeValue(writerl,authorService.getAllAuthors());
            writer.write(writerl.toString());

        } else if (pathInfo.equals("edit")) {
            Map<String, String[]> parameterMap = req.getParameterMap();

            long id;
            if (parameterMap != null && !parameterMap.isEmpty()) {
                try {
                    String byId = parameterMap.get("id")[0];
                    String name = parameterMap.get("name")[0];
                    String lastName = parameterMap.get("lastname")[0];
                    id = Long.parseLong(byId);
                    AuthorDto authorDto=new AuthorDto();
                    authorDto.setName(name);
                    authorDto.setLastName(lastName);
                    boolean update = authorService.updateById(id,authorDto);
                    if (update) {
                        writer.write("Update successful");
                    } else {
                        writer.write("Update failed");
                    }
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
                "/delete" +
                "/delete/?id=X\n";
        String queryString = req.getQueryString();
        String pathInfo = req.getPathInfo().split("/")[1];
        if (queryString == null && pathInfo.equals("delete")) {
            writer.write("Current list of entities:");
            StringWriter writerl = new StringWriter();
            mapper.writeValue(writerl,authorService.getAllAuthors());
            writer.write(writerl.toString());
        } else if (pathInfo.equals("delete")) {
            String byId = req.getParameter("id");
            long id;
            if (byId != null) {
                try {
                    id = Long.parseLong(byId);
                    boolean isDeleted = authorService.deleteById(id);
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
