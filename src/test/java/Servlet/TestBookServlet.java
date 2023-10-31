package Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.dto.BookDto;
import org.example.service.implService.BookServiceImpl;
import org.example.servlet.BookServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
public class TestBookServlet {
    @Mock
    HttpServletRequest request;
    @Mock
    HttpServletResponse response;
    @Mock
    PrintWriter writer;
    @Mock
    BookServiceImpl bookService;
    @InjectMocks
    BookServlet bookServlet;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void doGetValidQuery() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn("id=1");
        when(request.getPathInfo()).thenReturn("/show");
        when(response.getWriter()).thenReturn(writer);
        when(bookService.findById(anyLong())).thenReturn(new BookDto());
        bookServlet.doGet(request, response);
        verify(writer).write(anyString());
        verify(response, never()).sendError(anyInt(), anyString());
    }
    @Test
    void doGetInvalid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn("id=1");
        when(request.getPathInfo()).thenReturn("/invalid");
        when(response.getWriter()).thenReturn(writer);
        bookServlet.doGet(request, response);
        verify(writer).write(contains("Invalid id"));
        verify(response, never()).sendError(anyInt(), anyString());
    }
    @Test
    public void testDoPost() throws ServletException, IOException {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("title", new String[]{"SomeTitle"});
        parameterMap.put("genre", new String[]{"someGenre"});
        parameterMap.put("library_id", new String[]{"1"});
        parameterMap.put("author_id", new String[]{"2"});

        when(request.getQueryString()).thenReturn(null);
        when(request.getPathInfo()).thenReturn("add");
        when(request.getParameterMap()).thenReturn(parameterMap);

        PrintWriter writer = new PrintWriter("response.txt");
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doPost(request, response);

        verify(response).getWriter();
        writer.flush();
    }

    @Test
    public void testDoPostInvalid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn(null);
        when(request.getPathInfo()).thenReturn(null);
        PrintWriter writer = new PrintWriter("response.txt");
        when(response.getWriter()).thenReturn(writer);

        bookServlet.doPost(request, response);

        verify(response).getWriter();
        writer.flush();
    }
    @Test
    public void doDelete() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn(null);
        when(request.getPathInfo()).thenReturn("/delete");
        when(request.getParameter("id")).thenReturn("0");
        when(response.getWriter()).thenReturn(writer);
        when(bookService.deleteById(0)).thenReturn(true);
        bookServlet.doDelete(request, response);
        verify(writer).write("List of libraries:\n" +
                "");
    }
    @Test
    public void doDeleteInvalid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn("query");
        when(request.getPathInfo()).thenReturn("/invalid");
        when(response.getWriter()).thenReturn(writer);
        bookServlet.doDelete(request, response);
        verify(writer).write("Invalid request\nValid requests:\n/delete/?id=X\n");
    }

}




