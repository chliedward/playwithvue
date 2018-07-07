package auth.web.controller;

import auth.SecurityService;
import auth.service.UserInfoAccessService;
import auth.web.validator.UserValidator;
import auth.web.view.UserViewEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
    UserDetailsService userDetailsService;
    UserInfoAccessService userInfoAccessService;
    SecurityService securityService;
    UserValidator userValidator;
    @Autowired
    public UserController(UserDetailsService userDetailsService,
                          UserInfoAccessService userInfoAccessService,
                          SecurityService securityService,
                          UserValidator userValidator){
        this.userDetailsService = userDetailsService;
        this.userInfoAccessService = userInfoAccessService;
        this.securityService = securityService;
        this.userValidator = userValidator;
    }
//
//    @RequestMapping("/hello")
//    public ModelAndView welcomeMessage(
//            @RequestParam(value = "name", required = false) String name) {
//        // Name of your jsp file as parameter
//        ModelAndView view = new ModelAndView("hello");
//        view.addObject("name", name);
//        return view;
//    }
    @RequestMapping(value="/getTest", method=RequestMethod.GET)
    public String getTest(){
        return  "redirect:/login.jsp";
    }
    @RequestMapping(value= "/postTest", method=RequestMethod.POST)
    public String postTest(Model model){
        return "redirect:/login.jsp";
    }
    @RequestMapping(value= "/login", method=RequestMethod.POST)
    public String login(@ModelAttribute("userForm") UserViewEntity userViewEntity,
                              BindingResult bindingResult, Model model){
        userValidator.validate(userViewEntity, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if(securityService.login(userViewEntity.getUserName(), userViewEntity.getPasswordPlain())){

        }
        return "redirect:/login.jsp";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserViewEntity());
        return "redirect:/login.jsp";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registration(@ModelAttribute("userForm") UserViewEntity userViewEntity,
                               BindingResult bindingResult, Model model) {
        userValidator.validate(userViewEntity, bindingResult);

        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userInfoAccessService.saveUserView(userViewEntity);

        securityService.login(userViewEntity.getUserName(), userViewEntity.getPasswordPlain());

        return "redirect:/dispatcher1/helloWorld/helloAuth?name="+userViewEntity.getUserName();
    }
}
