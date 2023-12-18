package com.backend.dream.rest;

import com.backend.dream.dto.DiscountDTO;
import com.backend.dream.dto.NotificationDTO;
import com.backend.dream.mapper.DiscountMapper;
import com.backend.dream.service.AccountService;
import com.backend.dream.service.DiscountService;
import com.backend.dream.service.NotificationService;
import com.backend.dream.util.ValidationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/rest/discount")
public class DiscountRestController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DiscountService discountService;
    @Autowired
    private ValidationService validateService;

    @GetMapping()
    public List<DiscountDTO> getAll() throws Exception {
        return discountService.findAll();
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody @Valid DiscountDTO discountDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            validateService.validation(bindingResult);
            return ResponseEntity.badRequest().body(validateService.validation(bindingResult));
        }

        return ResponseEntity.ok(discountService.createDiscount(discountDTO));
    }

    @PutMapping("{id}")
    public DiscountDTO update(@RequestBody DiscountDTO discountDTO, @PathVariable("id") Long id,
            HttpServletRequest request) {
        String username = request.getRemoteUser();
        Long idAccount = accountService.findIDByUsername(username);
        Long idRole = accountService.findRoleIdByUsername(username);

        if (idRole == 1 || idRole == 2) {
            DiscountDTO updatedDiscount = discountService.update(discountDTO);
            String discountName = updatedDiscount.getName();
            String notificationTitle = "Có sự thay đổi trong sự kiện giảm giá";
            String notificationText = "Sự kiện '" + discountName + "' đã được cập nhật bởi '" + username + "'";

            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setIdAccount(idAccount);
            notificationDTO.setNotificationTitle(notificationTitle);
            notificationDTO.setNotificationText(notificationText);
            notificationDTO.setId_role(idRole);
            notificationDTO.setImage("discount-change.jpg");
            notificationDTO.setCreatedTime(Timestamp.from(Instant.now()));
            notificationService.createNotification(notificationDTO);

            return updatedDiscount;
        }
        return null;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Long id, HttpServletRequest request) {
        String username = request.getRemoteUser();
        Long idAccount = accountService.findIDByUsername(username);
        Long idRole = accountService.findRoleIdByUsername(username);

        DiscountDTO deletedDiscount = discountService.getDiscountByID(id);
        String discountName = deletedDiscount.getName();
        String notificationTitle = "Có sự thay đổi trong sự kiện giảm giá";
        String notificationText = "Sự kiện '" + discountName + "' đã bị xóa bởi '" + username + "'";
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setIdAccount(idAccount);
        notificationDTO.setNotificationTitle(notificationTitle);
        notificationDTO.setNotificationText(notificationText);
        notificationDTO.setId_role(idRole);
        notificationDTO.setImage("discount-change.jpg");
        notificationDTO.setCreatedTime(Timestamp.from(Instant.now()));
        notificationService.createNotification(notificationDTO);

        discountService.delete(id);
    }

    @GetMapping("/download")
    private ResponseEntity<InputStreamResource> download() throws IOException {
        String fileName ="Data-discount.xlsx";
        ByteArrayInputStream inputStream = discountService.getdataDiscount();
        InputStreamResource response = new InputStreamResource(inputStream);

        ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
        return responseEntity;
    }
}
