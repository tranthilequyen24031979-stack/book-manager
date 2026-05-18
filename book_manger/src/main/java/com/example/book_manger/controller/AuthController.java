package com.example.book_manger.controller;
import com.example.book_manger.dto.NguoiDungDTO;
import com.example.book_manger.service.NguoiDungService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final NguoiDungService nguoiDungService;

    public AuthController(NguoiDungService nguoiDungService) {
        this.nguoiDungService = nguoiDungService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("nguoiDung", new NguoiDungDTO());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute NguoiDungDTO nguoiDungDTO,
                               RedirectAttributes redirectAttributes) {
        try {
            // Kiểm tra username đã tồn tại
            if (nguoiDungService.isUserExists(nguoiDungDTO.getTenDangNhap())) {
                redirectAttributes.addFlashAttribute("error", "Tên đăng nhập đã tồn tại");
                return "redirect:/register";
            }

            // Kiểm tra email đã tồn tại
            if (nguoiDungService.isEmailExists(nguoiDungDTO.getEmail())) {
                redirectAttributes.addFlashAttribute("error", "Email đã được sử dụng");
                return "redirect:/register";
            }

            nguoiDungService.createNguoiDung(nguoiDungDTO);
            redirectAttributes.addFlashAttribute("message", "Đăng ký thành công. Vui lòng đăng nhập.");
            return "redirect:/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi đăng ký: " + e.getMessage());
            return "redirect:/register";
        }
    }
}
