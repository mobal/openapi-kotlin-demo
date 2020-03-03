package hu.netcode.openapikotlindemo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping(value = ["/"])
class WelcomeController {
    @GetMapping(value = [""])
    @ResponseBody
    fun welcome(): String {
        return "It's working!"
    }
}
