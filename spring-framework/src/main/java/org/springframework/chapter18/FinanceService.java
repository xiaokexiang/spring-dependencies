package org.springframework.chapter18;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author xiaokexiang
 * @since 2021/5/14
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
