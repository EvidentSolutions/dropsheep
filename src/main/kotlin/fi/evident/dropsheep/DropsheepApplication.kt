package fi.evident.dropsheep

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class DropsheepApplication

fun main(args: Array<String>) {
    SpringApplication.run(DropsheepApplication::class.java, *args)
}
