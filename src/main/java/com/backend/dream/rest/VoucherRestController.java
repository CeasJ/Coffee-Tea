package com.backend.dream.rest;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.dto.VoucherDTO;
import com.backend.dream.dto.VoucherStatusDTO;
import com.backend.dream.dto.VoucherTypeDTO;
import com.backend.dream.entity.Voucher;
import com.backend.dream.service.AccountService;
import com.backend.dream.service.VoucherService;
import com.backend.dream.service.VoucherTypeService;
import com.backend.dream.util.ValidationService;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/vouchers")
public class VoucherRestController {

    @Autowired
    private VoucherService voucherService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private VoucherTypeService voucherTypeService;
    @Autowired
    private ValidationService validateService;
    // Get voucher for user
    @GetMapping("/applicable")
    public List<VoucherDTO> getApplicableVouchers(){
        String user = request.getRemoteUser();
        return voucherService.getApplicableVouchers(user);
    }

    @GetMapping("/all")
    public List<VoucherDTO> getAllVouchers(){
        return voucherService.getAllVouchers();
    }

    @GetMapping("/voucherstatus/all")
    public List<VoucherStatusDTO> getAllVoucherStatus() {
        return voucherService.getAllVoucherStatus();
    }
    @GetMapping("/type/all")
    public List<VoucherTypeDTO> getAllVoucherTypes() {
        return voucherTypeService.getAllVoucherTypes();
    }

    @GetMapping("/filterByStatus/{statusId}")
    public List<VoucherDTO> getVouchersByStatus(@PathVariable Long statusId) {
        return voucherService.getVouchersByStatusId(statusId);
    }

    // Searching features
    @GetMapping("/search")
    public List<VoucherDTO> searchVouchersByName(@RequestParam String name) {
        return voucherService.searchVouchersByName(name);
    }

    @PostMapping()
    public ResponseEntity<?> createVoucher(@RequestBody @Valid JsonNode voucherData, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            validateService.validation(bindingResult);
            return ResponseEntity.badRequest().body(validateService.validation(bindingResult));
        }
        return ResponseEntity.ok(voucherService.createVoucher(voucherData));
    }
    @PutMapping("{id}")
    public VoucherDTO updateVoucher(@RequestBody VoucherDTO voucherDTO,@PathVariable Long id){
        return voucherService.updateVoucher(voucherDTO,id);
    }
    @PutMapping("/{name}/{idType}")
    public List<VoucherDTO> updateVoucher(@RequestBody VoucherDTO voucherDTO,@PathVariable String name ,@PathVariable Long idType){
        return voucherService.updateListVoucherByNameAndIdType(voucherDTO,name,idType);
    }


    @DeleteMapping("/{voucherId}")
    public ResponseEntity<String> deleteVoucher(@PathVariable Long voucherId) {
        try {
            voucherService.delete(voucherId);
            return ResponseEntity.ok("Voucher has been deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting voucher: " + e.getMessage());
        }
    }
    @DeleteMapping("/{name}/{idType}")
    public ResponseEntity<String> deleteListVoucher(@PathVariable String name, @PathVariable Long idType) {
        try {
            voucherService.deleteByNameAndType(name,idType);
            return ResponseEntity.ok("Voucher has been deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting voucher: " + e.getMessage());
        }
    }



}
