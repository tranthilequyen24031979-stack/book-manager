package com.example.book_manger.controller;

import com.example.book_manger.dto.SachDTO;
import com.example.book_manger.service.DanhMucService;
import com.example.book_manger.service.SachService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/sach")
public class SachController {

    private final SachService sachService;
    private final DanhMucService danhMucService;

    public SachController(SachService sachService, DanhMucService danhMucService) {
        this.sachService = sachService;
        this.danhMucService = danhMucService;
    }

    @GetMapping
    public String listSach(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String tieuDe,
            @RequestParam(required = false) String tacGia,
            @RequestParam(required = false) Integer danhMucId,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<SachDTO> sachPage;

        if (tieuDe != null || tacGia != null || danhMucId != null) {
            sachPage = sachService.searchSach(tieuDe, tacGia, danhMucId, pageable);
        } else {
            sachPage = sachService.getAllSach(pageable);
        }

        model.addAttribute("sachPage", sachPage);
        model.addAttribute("danhMucList", danhMucService.getAllDanhMuc());
        model.addAttribute("tieuDe", tieuDe);
        model.addAttribute("tacGia", tacGia);
        model.addAttribute("danhMucId", danhMucId);

        return "sach/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("sach", new SachDTO());
        model.addAttribute("danhMucList", danhMucService.getAllDanhMuc());
        return "sach/create";
    }

    @PostMapping("/create")
    public String createSach(@Valid @ModelAttribute SachDTO sachDTO,
                             RedirectAttributes redirectAttributes) {
        try {
            sachService.createSach(sachDTO);
            redirectAttributes.addFlashAttribute("message", "Thêm sách thành công");
            return "redirect:/sach";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/sach/create";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return sachService.getSachById(id)
                .map(sach -> {
                    model.addAttribute("sach", sach);
                    model.addAttribute("danhMucList", danhMucService.getAllDanhMuc());
                    return "sach/edit";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Sách không tồn tại");
                    return "redirect:/sach";
                });
    }

    @PostMapping("/{id}/update")
    public String updateSach(@PathVariable Integer id,
                             @Valid @ModelAttribute SachDTO sachDTO,
                             RedirectAttributes redirectAttributes) {
        try {
            sachService.updateSach(id, sachDTO);
            redirectAttributes.addFlashAttribute("message", "Cập nhật sách thành công");
            return "redirect:/sach";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/sach/" + id + "/edit";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteSach(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            sachService.deleteSach(id);
            redirectAttributes.addFlashAttribute("message", "Xóa sách thành công");
            return "redirect:/sach";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/sach";
        }
    }

    @GetMapping("/{id}")
    public String viewSach(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return sachService.getSachById(id)
                .map(sach -> {
                    model.addAttribute("sach", sach);
                    return "sach/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Sách không tồn tại");
                    return "redirect:/sach";
                });
    }
}
