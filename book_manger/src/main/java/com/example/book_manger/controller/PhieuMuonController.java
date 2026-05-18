package com.example.book_manger.controller;

import com.example.book_manger.dto.ChiTietPhieuMuonDTO;
import com.example.book_manger.dto.PhieuMuonDTO;
import com.example.book_manger.service.NguoiDungService;
import com.example.book_manger.service.PhieuMuonService;
import com.example.book_manger.service.SachService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/phieu-muon")
public class PhieuMuonController {

    private final PhieuMuonService phieuMuonService;
    private final NguoiDungService nguoiDungService;
    private final SachService sachService;

    public PhieuMuonController(PhieuMuonService phieuMuonService,
                               NguoiDungService nguoiDungService,
                               SachService sachService) {
        this.phieuMuonService = phieuMuonService;
        this.nguoiDungService = nguoiDungService;
        this.sachService = sachService;
    }

    @GetMapping
    public String listPhieuMuon(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String trangThai,
            Model model) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PhieuMuonDTO> phieuMuonPage = phieuMuonService.getAllPhieuMuon(pageable);

        model.addAttribute("phieuMuonPage", phieuMuonPage);
        model.addAttribute("trangThai", trangThai);

        return "phieu-muon/list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("phieuMuon", new PhieuMuonDTO());
        model.addAttribute("nguoiDungList", nguoiDungService.getAllNguoiDung(PageRequest.of(0, 100)));
        model.addAttribute("sachList", sachService.getAllSach(PageRequest.of(0, 100)));
        return "phieu-muon/create";
    }

    @PostMapping("/create")
    public String createPhieuMuon(@ModelAttribute PhieuMuonDTO phieuMuonDTO,
                                  @RequestParam(required = false) Integer[] sachIds,
                                  RedirectAttributes redirectAttributes) {
        try {
            if (sachIds != null && sachIds.length > 0) {
                phieuMuonDTO.setChiTietList(java.util.Arrays.stream(sachIds)
                        .map(id -> {
                            ChiTietPhieuMuonDTO ct = new ChiTietPhieuMuonDTO();
                            ct.setSachId(id);
                            return ct;
                        })
                        .toList());
            }

            phieuMuonService.createPhieuMuon(phieuMuonDTO);
            redirectAttributes.addFlashAttribute("message", "Tạo phiếu mượn thành công");
            return "redirect:/phieu-muon";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/phieu-muon/create";
        }
    }

    @GetMapping("/{id}")
    public String viewPhieuMuon(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return phieuMuonService.getPhieuMuonById(id)
                .map(phieuMuon -> {
                    model.addAttribute("phieuMuon", phieuMuon);
                    return "phieu-muon/view";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Phiếu mượn không tồn tại");
                    return "redirect:/phieu-muon";
                });
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        return phieuMuonService.getPhieuMuonById(id)
                .map(phieuMuon -> {
                    model.addAttribute("phieuMuon", phieuMuon);
                    return "phieu-muon/edit";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("error", "Phiếu mượn không tồn tại");
                    return "redirect:/phieu-muon";
                });
    }

    @PostMapping("/{id}/update")
    public String updatePhieuMuon(@PathVariable Integer id,
                                  @ModelAttribute PhieuMuonDTO phieuMuonDTO,
                                  RedirectAttributes redirectAttributes) {
        try {
            phieuMuonService.updatePhieuMuon(id, phieuMuonDTO);
            redirectAttributes.addFlashAttribute("message", "Cập nhật phiếu mượn thành công");
            return "redirect:/phieu-muon/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/phieu-muon/" + id + "/edit";
        }
    }

    @PostMapping("/{phieuMuonId}/tra/{sachId}")
    public String traPhieuMuon(@PathVariable Integer phieuMuonId,
                               @PathVariable Integer sachId,
                               RedirectAttributes redirectAttributes) {
        try {
            phieuMuonService.traPhieuMuon(phieuMuonId, sachId);
            redirectAttributes.addFlashAttribute("message", "Trả sách thành công");
            return "redirect:/phieu-muon/" + phieuMuonId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/phieu-muon/" + phieuMuonId;
        }
    }

    @PostMapping("/{phieuMuonId}/bao-mat/{sachId}")
    public String baoMatSach(@PathVariable Integer phieuMuonId,
                             @PathVariable Integer sachId,
                             RedirectAttributes redirectAttributes) {
        try {
            phieuMuonService.baoMatSach(phieuMuonId, sachId);
            redirectAttributes.addFlashAttribute("message", "Báo mất sách thành công");
            return "redirect:/phieu-muon/" + phieuMuonId;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/phieu-muon/" + phieuMuonId;
        }
    }

    @PostMapping("/{id}/delete")
    public String deletePhieuMuon(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            phieuMuonService.deletePhieuMuon(id);
            redirectAttributes.addFlashAttribute("message", "Xóa phiếu mượn thành công");
            return "redirect:/phieu-muon";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/phieu-muon";
        }
    }
}
