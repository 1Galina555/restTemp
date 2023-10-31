package Servlet;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.service.dto.AuthorDto;
import org.example.service.dto.LibraryDto;
import org.example.service.implService.AuthorServiceImpl;
import org.example.servlet.AuthorServlet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestAuthorServlet {
    @Mock
    private AuthorServiceImpl authorService;

    @InjectMocks
    private AuthorServlet authorServlet;

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
        authorServlet.init();
        mapper = new ObjectMapper();
    }

    @Test
    public void testDoGetNameAndLastName() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/show");
        when(request.getQueryString()).thenReturn("name=John&lastname=Doe");
        when(request.getParameterMap()).thenReturn(Collections.singletonMap("name", new String[]{"John"}));
        when(request.getParameter("name")).thenReturn("John");
        when(request.getParameter("lastname")).thenReturn("Doe");
        when(authorService.getLibraryById(1L)).thenReturn(Arrays.asList(new LibraryDto(1,"Library 1"), new LibraryDto(2,"Library 2")));
        StringWriter writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        when(response.getWriter()).thenReturn(printWriter);
        authorServlet.doGet(request, response);
        verify(response).getWriter();
    }
    @Test
    public void testDoPostAddsAuthor() throws ServletException, IOException {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("name", new String[] {"SomeName"});
        parameterMap.put("lastname", new String[] {"SomeLastName"});
        when(request.getParameterMap()).thenReturn(parameterMap);
        when(request.getPathInfo()).thenReturn("/add");
        when(request.getQueryString()).thenReturn("name=SomeName&lastname=SomeLastName");
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(0);
        authorDto.setName("SomeName");
        authorDto.setLastName("SomeLastName");
        when(authorService.addAuthor(authorDto)).thenReturn(authorDto);
        authorServlet.doPost(request, response);
        writer.flush();
        assertEquals("Author added:" + mapper.writeValueAsString(authorDto), stringWriter.toString());
    }
    @Test
    void testDoPutValid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn("id=1&name=John&lastname=Doe");
        when(request.getPathInfo()).thenReturn("/edit");
        when(request.getParameterMap()).thenReturn(getParameterMap());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        authorServlet.doPut(request, response);
    }

    @Test
    void testDoPutInvalid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn(null);
        when(request.getPathInfo()).thenReturn("/edit");
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        authorServlet.doPut(request, response);
    }

    @Test
    void testDoPutInvalidId() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn("id=invalid&name=John&lastname=Doe");
        when(request.getPathInfo()).thenReturn("/edit");
        when(request.getParameterMap()).thenReturn(getParameterMap());
        when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
        authorServlet.doPut(request, response);
    }

    private Map<String, String[]> getParameterMap() {
        Map<String, String[]> parameterMap = new HashMap<>();
        parameterMap.put("id", new String[]{"1"});
        parameterMap.put("name", new String[]{"John"});
        parameterMap.put("lastname", new String[]{"Doe"});
        return parameterMap;
    }





    @Test
    public void testDoDeleteInvalid() throws ServletException, IOException {
        when(request.getQueryString()).thenReturn(null);
        when(request.getPathInfo()).thenReturn("/invalid");
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printWriter);
    }

}
