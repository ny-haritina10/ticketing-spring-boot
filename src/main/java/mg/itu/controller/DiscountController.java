package mg.itu.controller;

import mg.itu.model.PercentageDiscount;
import mg.itu.repository.CategorieAgeRepository;
import mg.itu.repository.PercentageDiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/discounts")
public class DiscountController {

    @Autowired
    private PercentageDiscountRepository discountRepository;

    @Autowired
    private CategorieAgeRepository categorieAgeRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("discounts", discountRepository.findAll());
        return "discount/list";
    }

    @GetMapping("/form")
    public String showForm(@RequestParam(required = false) Integer id, Model model) {
        PercentageDiscount discount = id != null ? discountRepository.findById(id).orElse(new PercentageDiscount()) : new PercentageDiscount();
        
        model.addAttribute("discount", discount);
        model.addAttribute("categories", categorieAgeRepository.findAll());
        return "discount/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("discount") PercentageDiscount discount, 
                      BindingResult bindingResult, 
                      Model model) 
    {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categorieAgeRepository.findAll());
            return "discount/form"; 
        }
        discountRepository.save(discount);
        return "redirect:/discounts";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Integer id) {
        discountRepository.deleteById(id);
        return "redirect:/discounts";
    }
}