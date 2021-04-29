package org.springframework.chapter13;

import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/29
 */
@Component
public class MoneyValidatorImpl implements MoneyValidator {
    @Override
    public boolean validate(int money) {
        return money > 0;
    }
}
