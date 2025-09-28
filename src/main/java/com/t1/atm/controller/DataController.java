package com.t1.atm.controller;

import com.t1.atm.service.RepairInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

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
        model.addAttribute("reasonsList", repairInfoService.getTop3Reasons());
//        SortedMap sortedMap = new SortedHashMap<>(with comparator)    todo
        return "top3reasons";
    }

    @GetMapping("/top-3-repair-times")
    public String showTop3RepairTimes() {
        return "data";
    }

    @GetMapping("/repeat-repairs")
    public String showRepeatRepairs() {
        return "data";
    }
}
