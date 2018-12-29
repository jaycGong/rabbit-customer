package rabbitcustomer.jacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/***
 *
 * @author jacy
 *
 */
@Controller
public class UserController {
    @RequestMapping(value="/toIndex" , method=RequestMethod.GET)
    public String toIndex()  {
        return "index";
    }
}
