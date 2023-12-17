package com.backend.dream.service.imp;


import com.backend.dream.entity.Account;
import com.backend.dream.entity.Authority;
import com.backend.dream.repository.AccountRepository;
import com.backend.dream.repository.AuthorityRepository;
import com.backend.dream.service.AuthorityService;
import com.backend.dream.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class AuthorityServiceImp implements AuthorityService {

	@Autowired
	AuthorityRepository authorityRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Override
	public List<Authority> getAdmin() {
		List<Account> accounts = accountRepository.getStaff();
		return authorityRepository.autoritiesOf(accounts);
	}

	@Override
	public List<Authority> findALL() {
		return authorityRepository.findAll();
	}

	@Override
	public void delete(Long id) {
		authorityRepository.deleteById(id);
	}

	@Override
	public ByteArrayInputStream getdataAuthority() throws IOException {
		List<Authority> authorities = authorityRepository.findAll();
		ByteArrayInputStream data = ExcelUtil.dataToExcelAuthority(authorities);
		return data;
	}

	@Override
	public Authority create(Authority authority) {
		return authorityRepository.save(authority);
	}

}
