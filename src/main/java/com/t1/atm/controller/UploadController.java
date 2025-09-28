package com.t1.atm.controller;

import com.poiji.exception.InvalidExcelFileExtension;
import com.t1.atm.service.RepairInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.format.DateTimeParseException;

@Controller
@RequestMapping("/control")
@RequiredArgsConstructor
public class UploadController {

    private final RepairInfoService repairInfoService;

    @GetMapping
    public String showControlPage() {
        return "control";
    }

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              RedirectAttributes redirectAttributes) {

        try {
            long recordsQuantity = repairInfoService.uploadExcel(file);
            redirectAttributes.addFlashAttribute("message",
                    String.format("File uploaded successfully. %d records.", recordsQuantity));

            return "redirect:/control";
        } catch (InvalidExcelFileExtension e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());

            return "redirect:/control";
        } catch (JpaSystemException e) {
            redirectAttributes.addFlashAttribute("error",
                    String.format("This file can't be persist. Check file data. Message: " + e.getMessage()));

            return "redirect:/control";
        } catch (DateTimeParseException e) {
            redirectAttributes.addFlashAttribute("error",
                    "Incorrect Date/Time format. Check file data. Message: " + e.getMessage());

            return "redirect:/control";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error occurred: " + e.getMessage());

            return "redirect:/control";
        }
    }

    @PostMapping("/delete-all")
    public String deleteAll(RedirectAttributes redirectAttributes) {
        repairInfoService.deleteAll();
        redirectAttributes.addFlashAttribute("message", "All data was deleted.");

        return"redirect:/control";
    }
}
