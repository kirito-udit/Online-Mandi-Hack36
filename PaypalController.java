package com.om.onlinemandi01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.context.Context;
@Controller
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "pay/success";
    public static final String CANCEL_URL = "pay/cancel";

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    @GetMapping("/")
    public String payment(){//@ModelAttribute("order") Order order) {
        try {
           // Model model = new Model();
       //     model.addAttribute("variableName", "someValue");
//            Payment payment = service.createPayment(order.getPrice(), order.getCurrency(), order.getMethod(),
//                    order.getIntent(), order.getDescription(), "http://localhost:9090/" + CANCEL_URL,
//                    "http://localhost:9090/" + SUCCESS_URL);
            Payment payment = service.createPayment(99.90, "INR", "paypal",
                    "sale", "opop", "http://localhost:9090/" + CANCEL_URL,
                    "http://localhost:9090/" + SUCCESS_URL);
            for(Links link:payment.getLinks()) {
                if(link.getRel().equals("approval_url")) {
                    return "redirect:"+link.getHref();
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return "redirect:/";
    }

  

}
