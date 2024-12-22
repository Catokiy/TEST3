package org.example.TEST3;

import org.example.TEST3.Order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private Service service;

    private Integer editBackup = 0;

    @RequestMapping("/")
    public String index(Model model, @Param("keyword") String keyword) {
        List<Order> orderList = service.listAll(keyword);
        model.addAttribute("filter", new Order());
        model.addAttribute("orderList", orderList);
        model.addAttribute("keyword", keyword);
        model.addAttribute("plasticQuantity", service.get_plastic_quantity());
        model.addAttribute("glassQuantity", service.get_glass_quantity());
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value="/")
    public String indexFiltered(Model model, @ModelAttribute("filter") Order filter) {
        List<Order> orderList = service.listFiltered(filter);
        model.addAttribute("filter", new Order());
        model.addAttribute("orderList", orderList);
        return "index";
    }

    @RequestMapping("/new")
    public String newCargo(Model model) {
        model.addAttribute("order", new Order());
        return "new";
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

    @RequestMapping("/find")
    public String search(@RequestParam String keyword) {
        List<Order> result = service.listAll(keyword);
        ModelAndView mav = new ModelAndView("find");
        mav.addObject("result", result);
        return "find";
    }

    @RequestMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
