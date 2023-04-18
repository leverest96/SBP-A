package com.ssafit.controller;

import com.ssafit.security.userdetails.MemberDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @GetMapping("/")
    public String directIndexPage(final Model model) {
        model.addAttribute("page", 1);
        model.addAttribute("size", 3);

        return "main";
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

    @GetMapping("/exerciseRegister")
    public String directExerciseRegisterPage(@AuthenticationPrincipal final MemberDetails memberDetails) {
        if (memberDetails == null) {
            return "redirect:/";
        }

        return "exerciseRegister";
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