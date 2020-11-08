package rest.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rest.model.Role;
import rest.model.User;
import rest.service.RoleService;
import rest.service.UserService;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController() {
    }

    @GetMapping(value = {"/", "/home"})
    public String homePage(Model model) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.toString().contains("ROLE_ADMIN"))) {
            model.addAttribute("usersList", userService.listUsers());
            model.addAttribute("newUser", new User());
            model.addAttribute("allRoles", roleService.listRoles());
        }
        return "home";
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.listUsers();

        return (users != null && !users.isEmpty())
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user;
        try {
            user = userService.findUserById(id);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/users", consumes={"application/json"})
    public ResponseEntity<?> create(@RequestBody User user) {
        userService.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody User user) {
        try {
            userService.findUserById(id);
            userService.updateUser(user);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        try {
            userService.findUserById(id);
            userService.removeUser(id);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}


/*
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/registerfromform")
    public String registration(@ModelAttribute("user") User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        userService.save(userForm);
        return "redirect:/";
    }

    @GetMapping(value = { "/", "/home" })
    public String homePage(Model model) {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().anyMatch(a -> a.toString().contains("ROLE_ADMIN"))) {
            model.addAttribute("usersList", userService.listUsers());
            model.addAttribute("newUser", new User());
            model.addAttribute("allRoles", roleService.listRoles());
        }
        return "home";
    }


    @PostMapping("/create")
    public String create(@ModelAttribute @Valid User userForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("allRoles", roleService.listRoles());
            return "redirect:/home";
        }
        userService.save(userForm);
        return "redirect:/home";
    }

    @GetMapping(value = "/userPage")
    public String showUserPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();//get logged in username
        User user = (User) userService.loadUserByUsername(name);
        model.addAttribute("user", user);
        return "userPage";
    }

    @GetMapping("/delete/{id}")
    public String deleteUserById(@PathVariable(value = "id", required = true) long id, Model model) {
        userService.removeUser(id);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("newUser") User userForm,
                         @RequestParam(name = "ROLE_ADMIN", required = false) String confirmAdmin,
                         @RequestParam(name = "ROLE_USER", required = false) String confirmUser,
                         BindingResult bindingResult,
                         Model model) {

 */
/*       if (bindingResult.hasErrors()) {
            return "/";
        }*//*


        //Cgecking Roles
        User userRole = new User();
        Set<Role> newroles = new HashSet<>();

        if (confirmAdmin!=null) {
            newroles.add(roleService.getRoleByName("ROLE_ADMIN"));
        }
        if (confirmUser!=null) {
            newroles.add(roleService.getRoleByName("ROLE_USER"));
        }

        userRole.setRoles(newroles);

        //updating user's roles
        userForm.setRoles(userRole.getRoles());

        userService.updateUser(userForm);

        model.addAttribute("usersList", userService.listUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.listRoles());
        return "redirect:/";
    }

*/
