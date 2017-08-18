package fi.evident.dropsheep

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Controller
import org.springframework.util.StringUtils.cleanPath
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Controller
class FileUploadController {

    private val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/")
    fun showUploadForm() = "uploadForm"

    @PostMapping("/")
    fun handleFileUpload(@RequestParam("file") file: MultipartFile, redirectAttributes: RedirectAttributes): String {

        storeFile(file)
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded ${file.originalFilename}!")

        return "redirect:/"
    }

    private fun storeFile(file: MultipartFile) {
        val filename = cleanPath(file.originalFilename)

        check(filename.isNotEmpty()) { "empty file $filename" }
        check(".." !in filename) { "invalid relative path $filename" }

        val uploadDirectory = Paths.get("uploads").toAbsolutePath()

        log.info("Uploading '$filename' to '$uploadDirectory'...")

        Files.createDirectories(uploadDirectory)
        Files.copy(file.inputStream, uploadDirectory.resolve(filename), StandardCopyOption.REPLACE_EXISTING)

        log.info("Completed upload of '$filename'.")
    }
}
