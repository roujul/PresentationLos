package com.coderoux.presentation.los.repository;

import com.coderoux.presentation.los.domain.Account;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by jroux on 20.06.2016.
 */
public interface AccountDao extends CrudRepository<Account, Long> {

}
