package com.movelink.backend.service;

import com.movelink.backend.dto.WalletRequest;
import com.movelink.backend.entity.Wallet;
import com.movelink.backend.entity.WalletTransaction;

import java.util.List;

public interface WalletService {

    Wallet createWallet(Long userId);

    Wallet deposit(WalletRequest request);

    Wallet pay(WalletRequest request);

    Wallet refund(WalletRequest request);

    Wallet getWallet(Long userId);

    List<WalletTransaction> getTransactions(Long userId);
}