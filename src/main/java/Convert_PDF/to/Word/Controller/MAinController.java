package Convert_PDF.to.Word.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MAinController {
    @GetMapping("/home")
    public String getHome(){
        return "Home";
    }
}
