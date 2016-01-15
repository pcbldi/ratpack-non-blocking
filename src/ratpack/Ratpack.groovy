import ratpack.exec.Blocking
import ratpack.groovy.template.MarkupTemplateModule
import static ratpack.jackson.Jackson.json

import java.nio.file.Path

import static ratpack.groovy.Groovy.groovyMarkupTemplate
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module MarkupTemplateModule
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

        get("books") {
            def booksP = Blocking.get {
                BookUtils.allBooks
            }
            booksP.then {
                render json(it)
            }
        }

        get("authors") {
            def booksP = Blocking.get {
                BookUtils.allBooks
            }
            booksP.map({ books ->
                books.collect { it.author }
            }).then {
                render json(it)
            }
        }

        files { dir "public" }
    }
}
