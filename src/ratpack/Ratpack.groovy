import ratpack.exec.Blocking
import ratpack.groovy.template.MarkupTemplateModule
import static ratpack.jackson.Jackson.json

import java.nio.file.Path

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack
import static ratpack.jackson.Jackson.jsonNode

ratpack {
    bindings {
        module MarkupTemplateModule
        bindInstance(new BookService())

    }

    handlers {
        get {
            render groovyMarkupTemplate("index.gtpl", title: "My Ratpack App")
        }

        get("file") {
            Path filePath = serverConfig.baseDir.binding("sampleFile.txt").getFile()
            Blocking.get {
                filePath.toFile().text
            }.then {
                render it
            }
        }

        get("better-file") {
            render serverConfig.baseDir.binding("sampleFile.txt").getFile()
        }

        get("books") {BookService bookService->
            bookService.allBooks.then {
                render json(it)
            }
        }

        get("authors") {BookService bookService->
            bookService.allAuthors.then {
                render json(it)
            }
        }

        post("book"){BookService bookService->
            def postBody=parse jsonNode()
            postBody.then{bookService.save(new Book(name: it.name,author: it.author))}
            response.status(200)
            response.send()
        }
        files { dir "public" }
    }
}
