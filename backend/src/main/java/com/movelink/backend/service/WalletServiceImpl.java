package com.movelink.backend.service;

import com.movelink.backend.dto.WalletRequest;
import com.movelink.backend.entity.User;
import com.movelink.backend.entity.Wallet;
import com.movelink.backend.entity.WalletTransaction;
import com.movelink.backend.enums.TransactionType;
import com.movelink.backend.repository.UserRepository;
import com.movelink.backend.repository.WalletRepository;
import com.movelink.backend.repository.WalletTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public WalletServiceImpl(
            WalletRepository walletRepository,
            WalletTransactionRepository transactionRepository,
            UserRepository userRepository) {

        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Wallet createWallet(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (walletRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("Wallet already exists");
        }

        Wallet wallet = Wallet.builder()
                .user(user)
                .balance(0.0)
                .build();

        return walletRepository.save(wallet);
    }

    @Override
    public Wallet deposit(WalletRequest request) {

        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance() + request.getAmount());

        Wallet savedWallet = walletRepository.save(wallet);

        transactionRepository.save(
                WalletTransaction.builder()
                        .wallet(savedWallet)
                        .amount(request.getAmount())
                        .transactionType(TransactionType.DEPOSIT)
                        .build()
        );

        return savedWallet;
    }

    @Override
    public Wallet pay(WalletRequest request) {

        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        if (wallet.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient wallet balance");
        }

        wallet.setBalance(wallet.getBalance() - request.getAmount());

        Wallet savedWallet = walletRepository.save(wallet);

        transactionRepository.save(
                WalletTransaction.builder()
                        .wallet(savedWallet)
                        .amount(request.getAmount())
                        .transactionType(TransactionType.PAYMENT)
                        .build()
        );

        return savedWallet;
    }

    @Override
    public Wallet refund(WalletRequest request) {

        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        wallet.setBalance(wallet.getBalance() + request.getAmount());

        Wallet savedWallet = walletRepository.save(wallet);

        transactionRepository.save(
                WalletTransaction.builder()
                        .wallet(savedWallet)
                        .amount(request.getAmount())
                        .transactionType(TransactionType.REFUND)
                        .build()
        );

        return savedWallet;
    }

    @Override
    public Wallet getWallet(Long userId) {

        return walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));
    }

    @Override
    public List<WalletTransaction> getTransactions(Long userId) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        return transactionRepository.findByWalletId(wallet.getId());
    }
}