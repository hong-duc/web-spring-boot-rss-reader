package dman.hongduc;

import java.io.File;
import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * lớp chính của chương trình
 *
 * @author duc
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) throws IOException {
        File dir = new File("users/myuser");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        SpringApplication.run(Application.class, args);
    }
}
