package com.example.book_manger.controller;


import com.example.book_manger.dto.DanhMucDTO;
import com.example.book_manger.service.DanhMucService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/danh-muc")
public class DanhMucController {

    private final DanhMucService danhMucService;

    public DanhMucController(DanhMucService danhMucService) {
        this.danhMucService = danhMucService;
    }

    @GetMapping
    public String listDanhMuc(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String tenDanhMuc,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<DanhMucDTO> danhMucPage;

        if (tenDanhMuc != null && !tenDanhMuc.isEmpty()) {
            danhMucPage = danhMucService.searchDanhMuc(tenDanhMuc, pageable);
        } else {
            danhMucPage = danhMucService.getDanhMucPaged(pageable);
        }

        model.addAttribute("danhMucPage", danhMucPage);
        model.addAttribute("tenDanhMuc", tenDanhMuc);

        return "danh-muc/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("danhMuc", new DanhMucDTO());
        return "danh-muc/create";
    }

    @PostMapping("/create")
    public String createDanhMuc(@ModelAttribute DanhMucDTO danhMucDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            danhMucService.createDanhMuc(danhMucDTO);
            redirectAttributes.addFlashAttribute("message", "Tạo danh mục thành công");
            return "redirect:/danh-muc";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/danh-muc/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return danhMucService.getDanhMucById(id)
                .map(danhMuc -> {
                    model.addAttribute("danhMuc", danhMuc);
                    return "danh-muc/edit";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Danh mục không tồn tại");
                    return "redirect:/danh-muc";
                });
    }

    @PostMapping("/{id}/update")
    public String updateDanhMuc(@PathVariable Integer id,
                                @ModelAttribute DanhMucDTO danhMucDTO,
                                RedirectAttributes redirectAttributes) {
        try {
            danhMucService.updateDanhMuc(id, danhMucDTO);
            redirectAttributes.addFlashAttribute("message", "Cập nhật danh mục thành công");
            return "redirect:/danh-muc";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/danh-muc/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteDanhMuc(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            danhMucService.deleteDanhMuc(id);
            redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công");
            return "redirect:/danh-muc";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/danh-muc";
        }
    }
}
