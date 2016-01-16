import ratpack.exec.Operation
import ratpack.exec.Promise

class BookService {
    private final List<Book> storage = []

    Operation save(Book book) {
        storage << book
        Operation.noop()
    }

    Promise<List<Book>> getAllBooks() {
        Promise.of { downstream -> downstream.success(storage) }
    }

    Promise<List<String>> getAllAuthors(){
        getAllBooks().map{books->
            books.collect{it.author}
        }
    }
}