package s3.website.model

import java.io.File
import s3.website.model.Files.recursiveListFiles
import s3.website.{ErrorOrFile, ErrorReport}

// ssg = static site generator
trait Ssg {
  def outputDirectory: String
}

object Ssg {
  val automaticallySupportedSiteGenerators = Jekyll :: Nanoc :: Nil

  def autodetectSiteDir(workingDirectory: File): Option[File] =
    recursiveListFiles(workingDirectory).find { file =>
      file.isDirectory && automaticallySupportedSiteGenerators.exists(ssg => file.getAbsolutePath.endsWith(ssg.outputDirectory))
    }
}

case object Jekyll extends Ssg {
  def outputDirectory = "_site"
}

case object Nanoc extends Ssg {
  def outputDirectory = "public/output"
}