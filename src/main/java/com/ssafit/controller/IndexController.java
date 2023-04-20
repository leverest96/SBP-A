package com.ssafit.controller;

import com.ssafit.security.userdetails.MemberDetails;
import com.ssafit.service.ExerciseService;
import com.ssafit.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IndexController {
    private final ExerciseService exerciseService;
    private final MemberService memberService;

    @GetMapping("/")
    public String directIndexPage(@AuthenticationPrincipal final MemberDetails memberDetails,
                                  final Model model) {
        model.addAttribute("page", 1);
        model.addAttribute("size", 3);

        if (memberDetails != null) {
            final String memberNickname = memberService.searchNicknameUsingStudentId(memberDetails.getStudentId());

            model.addAttribute("memberNickname", memberNickname);
        }

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

    @GetMapping("/detail/{uuid}")
    public String directMemberPage(@PathVariable final String uuid,
                                   @RequestParam final String page,
                                   @RequestParam final String size,
                                   @AuthenticationPrincipal final MemberDetails memberDetails,
                                   final Model model) {
        if (!exerciseService.checkExerciseUuidExistence(uuid)) {
            return "redirect:/";
        }

        model.addAttribute("uuid", uuid);
        model.addAttribute("page", page);
        model.addAttribute("size", size);

        if (memberDetails != null) {
            final String memberNickname = memberService.searchNicknameUsingStudentId(memberDetails.getStudentId());
            model.addAttribute("memberNickname", memberNickname);
            model.addAttribute("reviewable", true);
        }

        return "review";
    }

    @GetMapping("/admin")
    public String directAdminPage(@AuthenticationPrincipal final MemberDetails memberDetails,
                                  final Model model) {
        model.addAttribute("studentId", memberDetails.getStudentId());
        model.addAttribute("nickname", memberDetails.getNickname());

        return "admin";
    }
}