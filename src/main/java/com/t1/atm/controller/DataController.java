package com.t1.atm.controller;

import com.t1.atm.service.RepairInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/data")
@RequiredArgsConstructor
public class DataController {

    private final RepairInfoService repairInfoService;

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("repairs", repairInfoService.getAll());
        return "data";
    }

    @GetMapping("/top-3-reasons")
    public String showTop3Reasons(Model model) {
        model.addAttribute("top3reasons", repairInfoService.getTop3Reasons());
//        SortedMap sortedMap = new SortedHashMap<>(with comparator)    todo
        return "top3reasons";
    }

    @GetMapping("/top-3-duration-repairs")
    public String showTop3RepairTimes(Model model) {
        model.addAttribute("repairs", repairInfoService.getTop3DurationRepairs());
        return "data";
    }

    @GetMapping("/repeated-repairs")
    public String showRepeatRepairs(Model model) {
        model.addAttribute("repairs", repairInfoService.getRepeatedRepairs());
        return "data";
    }
}
