package com.ssafit.controller;

import com.ssafit.security.userdetails.MemberDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String directIndexPage() {
        return "index";
    }

    @GetMapping("/register")
    public String directRegisterPage(@AuthenticationPrincipal final MemberDetails memberDetails) {
        if (memberDetails != null) {
            return "redirect:/";
        }

        return "register";
    }

    @GetMapping("/login")
    public String directLoginPage(@AuthenticationPrincipal final MemberDetails memberDetails) {
        if (memberDetails != null) {
            return "redirect:/";
        }

        return "login";
    }

    @GetMapping("/member")
    public String directMemberPage(@AuthenticationPrincipal final MemberDetails memberDetails,
                                   final Model model) {
        model.addAttribute("studentId", memberDetails.getStudentId());
        model.addAttribute("nickname", memberDetails.getNickname());

        return "member";
    }

    @GetMapping("/admin")
    public String directAdminPage(@AuthenticationPrincipal final MemberDetails memberDetails,
                                  final Model model) {
        model.addAttribute("studentId", memberDetails.getStudentId());
        model.addAttribute("nickname", memberDetails.getNickname());

        return "admin";
    }
}