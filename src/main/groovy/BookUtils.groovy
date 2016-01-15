import ratpack.exec.Downstream
import ratpack.exec.Promise
import ratpack.exec.Upstream

class BookUtils {

    static def getAllBooks(){
        (1..5).collect{new Book(name:"book-${it}", author: "author-${it}")}
    }

    static def findByAuthor(String author){
        getAllBooks().find{it.author==author}
    }

    static def findByTitle(String name){
        getAllBooks().find{it.name==name}
    }
}