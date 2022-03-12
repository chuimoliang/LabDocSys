package com.moliang.labdocsys.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhang qing
 * @date 2022/3/12 15:02
 */
@Data
@Component
public class Config {

    @Value("${live.jdbc.user}")
    private String liveUser;

}
