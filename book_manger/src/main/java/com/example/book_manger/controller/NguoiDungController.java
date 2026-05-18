package com.example.book_manger.controller;

import com.example.book_manger.dto.NguoiDungDTO;
import com.example.book_manger.service.NguoiDungService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nguoi-dung")
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    public NguoiDungController(NguoiDungService nguoiDungService) {
        this.nguoiDungService = nguoiDungService;
    }

    @GetMapping
    public String listNguoiDung(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String hoTen,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<NguoiDungDTO> nguoiDungPage;

        if (hoTen != null && !hoTen.isEmpty()) {
            nguoiDungPage = nguoiDungService.searchNguoiDung(hoTen, pageable);
        } else {
            nguoiDungPage = nguoiDungService.getAllNguoiDung(pageable);
        }

        model.addAttribute("nguoiDungPage", nguoiDungPage);
        model.addAttribute("hoTen", hoTen);

        return "nguoi-dung/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("nguoiDung", new NguoiDungDTO());
        return "nguoi-dung/create";
    }

    @PostMapping("/create")
    public String createNguoiDung(@ModelAttribute NguoiDungDTO nguoiDungDTO,
                                  RedirectAttributes redirectAttributes) {
        try {
            nguoiDungService.createNguoiDung(nguoiDungDTO);
            redirectAttributes.addFlashAttribute("message", "Tạo người dùng thành công");
            return "redirect:/nguoi-dung";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/nguoi-dung/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return nguoiDungService.getNguoiDungById(id)
                .map(nguoiDung -> {
                    model.addAttribute("nguoiDung", nguoiDung);
                    return "nguoi-dung/edit";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại");
                    return "redirect:/nguoi-dung";
                });
    }

    @PostMapping("/{id}/update")
    public String updateNguoiDung(@PathVariable Integer id,
                                  @ModelAttribute NguoiDungDTO nguoiDungDTO,
                                  RedirectAttributes redirectAttributes) {
        try {
            nguoiDungService.updateNguoiDung(id, nguoiDungDTO);
            redirectAttributes.addFlashAttribute("message", "Cập nhật người dùng thành công");
            return "redirect:/nguoi-dung";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/nguoi-dung/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteNguoiDung(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            nguoiDungService.deleteNguoiDung(id);
            redirectAttributes.addFlashAttribute("message", "Xóa người dùng thành công");
            return "redirect:/nguoi-dung";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/nguoi-dung";
        }
    }
}