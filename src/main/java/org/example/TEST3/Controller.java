package org.example.TEST3;

import org.example.TEST3.Order.Order;
import org.example.TEST3.User.User;
import org.example.TEST3.User.UserService;
import org.example.TEST3.waitingOrder.waitingOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private Service service;

    @Autowired
    private UserService userService;

    private Integer editBackup = 0;

    @RequestMapping("/")
    public String index(Model model, @Param("keyword") String keyword, @AuthenticationPrincipal User user) {
        List<Order> orderList = service.listAll(keyword,user.getUsername());
        model.addAttribute("filter", new Order());
        model.addAttribute("orderList", orderList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("plasticQuantity", service.get_plastic_quantity());
        model.addAttribute("glassQuantity", service.get_glass_quantity());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value="/")
    public String indexFiltered(Model model, @ModelAttribute("filter") Order filter, @AuthenticationPrincipal  User user) {
        List<Order> orderList = service.listFiltered(filter, user.getUsername());
        model.addAttribute("filter", new Order());
        model.addAttribute("orderList", orderList);
        model.addAttribute("plasticQuantity", service.get_plastic_quantity());
        model.addAttribute("glassQuantity", service.get_glass_quantity());
        return "index";
    }

    @RequestMapping("/waitingList")
    public String waitingOrders(Model model) {
        List<waitingOrder> waitingOrders = service.waitListAll();
        model.addAttribute("waitingOrders", waitingOrders);
        return "waiting_list";
    }

    @RequestMapping("/save/{id}")
    public String saveWaiting(@PathVariable Integer id) {
        waitingOrder waitingOrder = service.waitingFindById(id);
        Order order = new Order();
        order.setBottleQuantity(waitingOrder.getBottleQuantity());
        order.setClientType(waitingOrder.getClientType());
        order.setClientName(waitingOrder.getClientName());
        order.setWaterType(waitingOrder.getWaterType());
        order.setBottleType(waitingOrder.getBottleType());
        order.setBottleQuantity(waitingOrder.getBottleQuantity());
        order.setOrderDate(waitingOrder.getOrderDate());
        order.setDeliveryDate(waitingOrder.getDeliveryDate());
        order.setDeliveryAddress(waitingOrder.getDeliveryAddress());
        order.setManagerInCharge(null);
        try {
            service.updateQuantity(order.getBottleQuantity(), order.getBottleType());
        } catch (RuntimeException e) {
            return "/quantityError";
        }
        service.save(order);
        service.waitingDeleteById(id);
        return "redirect:/waitingList";
    }

    @RequestMapping("/new")
    public String newCargo(Model model) {
        model.addAttribute("order", new Order());
        return "new";
    }

    @RequestMapping("/newWaiting")
    public String newWaitingCargo(Model model) {
        model.addAttribute("order", new waitingOrder());
        return "newWaiting";
    }

    @RequestMapping(value = "/save")
    public String saveCargo(@ModelAttribute("order") Order order) {
        try {
            service.updateQuantity(order.getBottleQuantity(), order.getBottleType());
        } catch (RuntimeException e) {
            return "/quantityError";
        }
        service.save(order);

        return "redirect:/";
    }

    @RequestMapping(value = "/saveWaiting")
    public String saveWaitingCargo(@ModelAttribute("order") waitingOrder order) {
        service.waitingSave(order);
        return "redirect:/welcome";
    }

    @RequestMapping("/edit/{id}")
    public ModelAndView editCargo(@PathVariable Integer id) {
        Order order = service.findById(id);
        editBackup = order.getBottleQuantity();
        service.updateQuantity(-editBackup, order.getBottleType());
        ModelAndView modelAndView = new ModelAndView("edit");
        modelAndView.addObject("order", order);
        return modelAndView;
    }

    @RequestMapping("/save-edited")
    public String saveEditedCargo(@ModelAttribute("order") Order order) {
        try {
            service.updateQuantity(order.getBottleQuantity(), order.getBottleType());
        } catch (RuntimeException e) {
            service.updateQuantity(editBackup, order.getBottleType());
            editBackup = 0;
            return "/quantityError";
        }
        service.save(order);

        return "redirect:/";
    }

    @RequestMapping("/cancel-edit/{id}")
    public String cancelEdit(@PathVariable Integer id) {
        Order order = service.findById(id);
        service.updateQuantity(editBackup, order.getBottleType());
        editBackup = 0;
        return "redirect:/";
    }

    @RequestMapping("/delete/{id}")
    public String deleteOrder(@PathVariable Integer id) {
        Order order = service.findById(id);
        service.updateQuantity(-order.getBottleQuantity(), order.getBottleType());
        service.deleteById(id);
        return "redirect:/";
    }
    @RequestMapping("/deleteWaiting/{id}")
    public String deleteWaitingOrder(@PathVariable Integer id) {
        service.waitingDeleteById(id);
        return "redirect:/";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }

    @RequestMapping("/user_administration")
    public String user_admin(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("users",users);
        return "user_administration";
    }

    @RequestMapping("/delete_user/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return "redirect:/user_administration";
    }
    @RequestMapping("/edit_user/{username}")
    public ModelAndView editUser(@PathVariable String username) {
        User user = userService.loadUserByUsername(username);
        ModelAndView modelAndView = new ModelAndView("edit_user");
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    @RequestMapping("/save_user")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/user_administration";
    }
    @RequestMapping("/permission_denied")
    public String permission_denied() {
        return "permissioneErrrore";
    }

}
