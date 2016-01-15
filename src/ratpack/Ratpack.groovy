import ratpack.exec.Blocking
import ratpack.groovy.template.MarkupTemplateModule
import ratpack.handling.Context

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

    get("file"){
        Path filePath=serverConfig.baseDir.binding("sampleFile.txt").getFile()
        Blocking.get{
          filePath.toFile().text
        }.then{
          render it
        }
      }

    get("better-file"){
      render serverConfig.baseDir.binding("sampleFile.txt").getFile()
    }

    files { dir "public" }
  }
}
