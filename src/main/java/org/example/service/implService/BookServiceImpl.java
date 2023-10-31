package org.example.service.implService;
import org.example.db.DataBaseConnect;
import org.example.model.Book;
import org.example.repository.impl.SimpleBookRepositoryImpl;
import org.example.service.ServiceBook;
import org.example.service.dto.BookDto;
import org.example.service.mapper.BookMapper;

public class BookServiceImpl implements ServiceBook {
    private final DataBaseConnect dataBase;
    private SimpleBookRepositoryImpl bookRepository;
    public BookServiceImpl(DataBaseConnect dataBase) {
        this.dataBase = dataBase;
        this.bookRepository = new SimpleBookRepositoryImpl(dataBase);
    }
    @Override
    public BookDto save(BookDto bookDto) {
        Book book = new Book(bookDto.getTitle(),bookDto.getGenre(),bookDto.getLibraryId(),bookDto.getAuthorId());
        bookRepository.save(book);
        bookDto=BookMapper.INSTANCE.toDto(book);
        return bookDto;
    }
    @Override
    public BookDto findById(long id) {
       Book book = bookRepository.findById(id);
        BookDto bookDto=new BookDto();
        bookDto=BookMapper.INSTANCE.toDto(book);
        return bookDto;
    }
    public boolean deleteById(long id) {
        return bookRepository.deleteById(id);
    }
    public void setBookRepository(SimpleBookRepositoryImpl bookRepository) {
        this.bookRepository = bookRepository;
    }

}
