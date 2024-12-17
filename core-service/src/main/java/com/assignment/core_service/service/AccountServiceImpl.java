package com.assignment.core_service.service;

import com.assignment.core_service.dto.account.AccountDTO;
import com.assignment.core_service.dto.account.CreateAccountDTO;
import com.assignment.core_service.dto.account.UpdateAccountDTO;
import com.assignment.core_service.dto.response.PagedDTO;
import com.assignment.core_service.entity.Account;
import com.assignment.core_service.mapper.AccountMapper;
import com.assignment.core_service.rabbitmq.publisher.RabbitMQProducer;
import com.assignment.core_service.repository.AccountRepository;
import com.assignment.core_service.service.interfaces.AccountService;
import com.assignment.core_service.service.interfaces.PagingService;
import com.assignment.core_service.util.Constant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final MessageSource messageSource;

    private final PasswordEncoder passwordEncoder;

    private final AccountMapper accountMapper;

    private final PagingService pagingService;

    private final RabbitMQProducer rabbitMQProducer;

    private final AccountRepository accountRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public boolean isDuplicateUsername(String userName) {

        return accountRepository.existsByUserName(userName);
    }

    @Override
    public boolean existsById(long id) {

        return accountRepository.existsByIdAndStatus(id, true);
    }

    @Override
    @Cacheable(cacheNames = "accounts:detail", key = "#id")
    public AccountDTO findById(long id) {

        Optional<Account> account = accountRepository.findByIdAndStatus(id, true);

        return account.map(accountMapper::entityToDTO).orElse(null);
    }

    @Override
    @Cacheable(cacheNames = "accounts:list")
    public PagedDTO<AccountDTO> findAllByCondition(String search, String sort, int page, int limit) {

        if (page < 0) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_NUMBER, null, LocaleContextHolder.getLocale()));
        if (limit < 1) throw new InvalidParameterException(
                messageSource.getMessage(Constant.INVALID_PAGE_SIZE, null, LocaleContextHolder.getLocale()));

        List<Sort.Order> order = new ArrayList<>();

        Set<String> sourceFieldList = pagingService.getAllFields(Account.class);
        String[] subSort = sort.split(",");
        if (pagingService.checkPropertyPresent(sourceFieldList, subSort[0])) {

            order.add(new Sort.Order(pagingService.getSortDirection(subSort[1]), subSort[0]));
        } else {

            throw new InvalidParameterException("{" + subSort[0] + "} " +
                    messageSource.getMessage(Constant.INVALID_PROPERTY, null, LocaleContextHolder.getLocale()));
        }

        Pageable pageable = PageRequest.of(page, limit).withSort(Sort.by(order));
        Page<Account> pageResult = accountRepository.findAllByCondition(true, search, pageable);

        return new PagedDTO<>(pageResult.map(accountMapper::entityToDTO));
    }

    @Override
    public void create(CreateAccountDTO create) {

        try {

            create.setPassword(passwordEncoder.encode(create.getPassword()));
            accountRepository.save(accountMapper.createToEntity(create));

            try {

                rabbitMQProducer.sendMessage(create);
            }catch (Exception e){

                LOGGER.info("Send message fail -> {}", create);
            }
        } catch (Exception e) {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.CREATE_FAIL, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void update(UpdateAccountDTO update, long id) {

        Optional<Account> account = accountRepository.findByIdAndStatus(id, true);
        if (account.isPresent()) {

            try {

                accountRepository.save(accountMapper.updateToEntity(update, account.get()));
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.UPDATE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_ACCOUNT, null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @CacheEvict(cacheNames = "accounts:detail", key = "#id")
    public void delete(long id) {

        Optional<Account> account = accountRepository.findByIdAndStatus(id, true);
        if (account.isPresent()) {

            try {

                account.get().setStatus(false);
                accountRepository.save(account.get());
            } catch (Exception e) {

                throw new InvalidParameterException(
                        messageSource.getMessage(Constant.DELETE_FAIL, null, LocaleContextHolder.getLocale()));
            }
        } else {

            throw new InvalidParameterException(
                    messageSource.getMessage(Constant.INVALID_ACCOUNT, null, LocaleContextHolder.getLocale()));
        }
    }
}
