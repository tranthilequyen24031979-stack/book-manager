package com.example.book_manger.controller;


import com.example.book_manger.service.DonHangService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/don-hang")
public class DonHangController {

    private final DonHangService donHangService;

    public DonHangController(DonHangService donHangService) {
        this.donHangService = donHangService;
    }

    @GetMapping
    public String listDonHang(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String trangThai,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Object> donHangPage;

        if (trangThai != null && !trangThai.isEmpty()) {
            donHangPage = donHangService.getDonHangByTrangThai(trangThai, pageable);
        } else {
            donHangPage = donHangService.getAllDonHang(pageable);
        }

        model.addAttribute("donHangPage", donHangPage);
        model.addAttribute("trangThai", trangThai);

        return "don-hang/list";
    }

    @GetMapping("/{id}")
    public String viewDonHang(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return donHangService.getDonHangById(id)
                .map(donHang -> {
                    model.addAttribute("donHang", donHang);
                    return "don-hang/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Đơn hàng không tồn tại");
                    return "redirect:/don-hang";
                });
    }
}
