package com.belleileperfumee.belle_ile_parfumee.mapper;

import com.belleileperfumee.belle_ile_parfumee.dto.AccountRequestDTO;
import com.belleileperfumee.belle_ile_parfumee.dto.AccountResponseDTO;
import com.belleileperfumee.belle_ile_parfumee.entity.Account;

public class AccountMapper {

    // Convertir RequestDTO → Entity (pour créer/sauvegarder)
    public static Account toEntity(AccountRequestDTO dto) {
        if (dto == null) {
            return null;
        }

        Account account = new Account();
        account.setEmail(dto.getEmail());
        account.setPassword(dto.getPassword());
        account.setRole(dto.getRole());
        return account;
    }

    // Convertir Entity → ResponseDTO (pour renvoyer au client)
    public static AccountResponseDTO toResponseDTO(Account account) {
        if (account == null) {
            return null;
        }

        AccountResponseDTO dto = new AccountResponseDTO();
        dto.setEmail(account.getEmail());
        // ✅ On ne copie PAS le password !
        dto.setRole(account.getRole());
        return dto;
    }
}