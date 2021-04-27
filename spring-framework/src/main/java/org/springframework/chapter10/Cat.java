package org.springframework.chapter10;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaokexiang
 * @since 2021/4/25
 */
@Data
@Component
public class Cat {

    @Value("miaow")
    private String name;

    private Person master;

    @Autowired
    public void setMaster(Person master) {
        this.master = master;
    }
}
