package com.t1.atm.controller;

import com.t1.atm.model.AtmRepairEntity;
import com.t1.atm.service.RepairInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final RepairInfoService repairInfoService;

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("repairs", repairInfoService.getAll());
        model.addAttribute("pageType", "все");
        return "data";
    }

    @GetMapping("/top-3-reasons")
    public String showTop3Reasons(Model model) {
        model.addAttribute("top3reasons", repairInfoService.getTop3Reasons());
        model.addAttribute("pageType", "топ 3 причины");

        return "top3reasons";
    }

    @GetMapping("/top-3-duration-repairs")
    public String showTop3RepairTimes(Model model) {
        model.addAttribute("repairs", repairInfoService.getTop3DurationRepairs());
        model.addAttribute("pageType", "топ 3 времени ремонта");

        return "data";
    }

    @GetMapping("/repeated-repairs")
    public String showRepeatRepairs(Model model) {
        model.addAttribute("repairs", repairInfoService.getRepeatedRepairs());
        model.addAttribute("pageType", "повторные ремонты");

        return "data";
    }

    @GetMapping("/{id}/edit")
    public String editRepair(@PathVariable("id") Long id, Model model) {
        model.addAttribute("repair", repairInfoService.getRepair(id));
        model.addAttribute("repairId", id);

        return "editRepair";
    }

    @PatchMapping("/{id}")
    public String updateRepair(@PathVariable("id") Long id, @ModelAttribute AtmRepairEntity repair) {
        repair.setCaseId(id);
        repairInfoService.update(repair);

        return "redirect:/data/all";
    }
}
