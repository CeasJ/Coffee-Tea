package com.backend.dream.service;

import com.backend.dream.dto.AccountDTO;
import com.backend.dream.entity.Account;
import com.backend.dream.repository.AccountRepository;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(String username);

    AccountDTO registerAccount(AccountDTO accountDTO);

    AccountDTO updateAccount(AccountDTO accountDTO);

    public boolean isUsernameExists(String username);

    public boolean isEmailExists(String email);

    Account findByUsernameAndEmail(String username, String email);

    AccountDTO updatePassword(AccountDTO accountDTO, String password);

    Long findIDByUsername(String username) throws NoSuchElementException;

    String findFullNameByUsername(String username) throws NoSuchElementException;

    List<Account> getStaff();

    List<Account> findALL();

    Account findById(String username);

    AccountDTO findById(Long id);

    String getImageByUserName(String remoteUser) throws NoSuchElementException;

    Account createStaff(AccountDTO accountDTO);

    Account updateStaff(Account staffToUpdate);

    String getAddressByUsername(String remoteUser);

    List<AccountDTO> getAllAccounts();

//    Account createAccountWhenDontHaveAccount(Account account);

    ByteArrayInputStream getdataStaff() throws IOException;
}
