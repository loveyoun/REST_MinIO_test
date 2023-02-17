package RESTuploadtest.RESTuploadtest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Path;

@Controller
@RequestMapping("/")
public class UploadController {

    @GetMapping
    public String main(){
        return "index";
    }

    @PostMapping
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message", "You successfully uploaded" + file.getOriginalFilename());

        return "redirect:/";
    }

}
