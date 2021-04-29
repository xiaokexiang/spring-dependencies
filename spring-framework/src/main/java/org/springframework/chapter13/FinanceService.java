package org.springframework.chapter13;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiaokexiang
 * @since 2021/4/28
 */
@Service
public class FinanceService {

    FinanceDao financeDao;

    @Autowired
    public void setFinanceDao(FinanceDao financeDao) {
        this.financeDao = financeDao;
    }

    @Transactional
    public void transfer(Long source, Long target, int money) {
        financeDao.subtractMoney(source, money);
        int i = 1 / 0;
        financeDao.addMoney(target, money);
    }
}