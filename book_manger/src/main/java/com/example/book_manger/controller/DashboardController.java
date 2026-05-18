package com.example.book_manger.controller;

import com.example.book_manger.service.DanhMucService;
import com.example.book_manger.service.DonHangService;
import com.example.book_manger.service.NguoiDungService;
import com.example.book_manger.service.PhieuMuonService;
import com.example.book_manger.service.SachService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final SachService sachService;
    private final PhieuMuonService phieuMuonService;
    private final NguoiDungService nguoiDungService;
    private final DonHangService donHangService;
    private final DanhMucService danhMucService;

    public DashboardController(SachService sachService,
                               PhieuMuonService phieuMuonService,
                               NguoiDungService nguoiDungService,
                               DonHangService donHangService,
                               DanhMucService danhMucService) {
        this.sachService = sachService;
        this.phieuMuonService = phieuMuonService;
        this.nguoiDungService = nguoiDungService;
        this.donHangService = donHangService;
        this.danhMucService = danhMucService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        Pageable pageable = PageRequest.of(0, 10);

        // Thống kê tổng quát
        long totalSach = sachService.getAllSach(PageRequest.of(0, 1)).getTotalElements();
        long totalNguoiDung = nguoiDungService.getAllNguoiDung(PageRequest.of(0, 1)).getTotalElements();
        long totalPhieuMuon = phieuMuonService.getAllPhieuMuon(PageRequest.of(0, 1)).getTotalElements();
        long totalDonHang = donHangService.getAllDonHang(PageRequest.of(0, 1)).getTotalElements();

        model.addAttribute("totalSach", totalSach);
        model.addAttribute("totalNguoiDung", totalNguoiDung);
        model.addAttribute("totalPhieuMuon", totalPhieuMuon);
        model.addAttribute("totalDonHang", totalDonHang);

        // Dữ liệu gần đây
        model.addAttribute("recentSach", sachService.getAllSach(pageable).getContent());
        model.addAttribute("recentPhieuMuon", phieuMuonService.getAllPhieuMuon(pageable).getContent());
        model.addAttribute("recentDonHang", donHangService.getAllDonHang(pageable).getContent());

        return "dashboard/index";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}
