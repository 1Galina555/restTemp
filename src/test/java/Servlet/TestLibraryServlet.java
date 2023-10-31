package Servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.LibraryDto;
import org.example.service.implService.LibraryServiceImpl;
import org.example.servlet.LibraryServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestLibraryServlet {
    @Mock
    private LibraryServiceImpl libraryService;

    @InjectMocks
    private LibraryServlet libraryServlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private PrintWriter writer;

    private StringWriter stringWriter;
    private ObjectMapper mapper;

    @BeforeEach
    public void setUp() throws IOException, ServletException {
        MockitoAnnotations.openMocks(this);
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(response.getWriter()).thenReturn(writer);
        libraryServlet.init();
        mapper = new ObjectMapper();
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/show");
        when(request.getQueryString()).thenReturn("title=Library2");
        when(request.getParameterMap()).thenReturn(Collections.singletonMap("title", new String[]{"Library2"}));
        when(request.getParameter("name")).thenReturn("Library2");
        when(libraryService.getAuthorsById(1L)).thenReturn(Arrays.asList(new AuthorDto("Artur", "Don"), new AuthorDto("Mishel", "Larson")));
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(printWriter);
        libraryServlet.doGet(request, response);
        verify(response).getWriter();
    }
    @Test
    void testDoPut() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn("id=1&title=Library1");
        when(request.getPathInfo()).thenReturn("/edit");
        when(request.getParameterMap()).thenReturn(getParameterMap());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        libraryServlet.doPut(request, response);

    }
    @Test
    void testDoPutInvalid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn(null);
        when(request.getPathInfo()).thenReturn("/edit");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        libraryServlet.doPut(request, response);
    }

    @Test
    void testDoPutInvalidId() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn("id=invalid&name=John");
        when(request.getPathInfo()).thenReturn("/edit");
        when(request.getParameterMap()).thenReturn(getParameterMap());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        libraryServlet.doPut(request, response);
    }
    private Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("id", new String[]{"1"});
        parameterMap.put("title", new String[]{"Library1"});
        return parameterMap;
    }
    @Test
    public void testDeleteInvalid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn(null);
        when(request.getPathInfo()).thenReturn("/invalid");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }





    @Test
    public void testDoDeletePathInfo() throws ServletException, IOException {
        PrintWriter writer = mock(PrintWriter.class);

        when(response.getWriter()).thenReturn(writer);
        when(request.getQueryString()).thenReturn("someQuery");
        when(request.getPathInfo()).thenReturn("/invalid/");

        libraryServlet.doDelete(request, response);

        verify(writer).write("Invalid request\nValid requests:\n/delete/?id=X\n");
    }


}
