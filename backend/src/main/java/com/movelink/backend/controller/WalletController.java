package com.movelink.backend.controller;

import com.movelink.backend.dto.WalletRequest;
import com.movelink.backend.entity.Wallet;
import jakarta.validation.Valid;
import com.movelink.backend.entity.WalletTransaction;
import com.movelink.backend.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Wallet> createWallet(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.createWallet(userId));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Wallet> deposit(@Valid @RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.deposit(request));
    }

    @PostMapping("/pay")
    public ResponseEntity<Wallet> pay(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.pay(request));
    }

    @PostMapping("/refund")
    public ResponseEntity<Wallet> refund(@RequestBody WalletRequest request) {
        return ResponseEntity.ok(walletService.refund(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable Long userId) {
        return ResponseEntity.ok(walletService.getWallet(userId));
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<List<WalletTransaction>> getTransactions(
            @PathVariable Long userId) {

        return ResponseEntity.ok(walletService.getTransactions(userId));
    }
}