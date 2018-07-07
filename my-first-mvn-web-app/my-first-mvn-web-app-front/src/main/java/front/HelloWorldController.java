package front;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// RequestMapping on the class will represent as the first part of path after "AppContext"'s url
// EX: 
//   localhost:xxxx/my-first-mvn-web-app-front/dispatcher1/
@Controller
@RequestMapping("/helloWorld")
public class HelloWorldController {
	@RequestMapping(value= "/postTest", method=RequestMethod.POST)
	public String postTest(Model model){
		return "redirect:/login.jsp";
	}
	@RequestMapping("/hello")
	public ModelAndView welcomeMessage(
			@RequestParam(value = "name", required = false) String name) {
		// Name of your jsp file as parameter
		ModelAndView view = new ModelAndView("hello");
		view.addObject("name", name);
		return view;
	}
	@RequestMapping("/helloAuth")
	public ModelAndView welcomeMessageAuth(
			@RequestParam(value = "name", required = false) String name) {
		// Name of your jsp file as parameter
		ModelAndView view = new ModelAndView("hello");
		view.addObject("name", name);
		return view;
	}
}
