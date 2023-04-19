package com.emmett.auto_check.service;


import com.emmett.auto_check.config.SYSConfig;

/**
 * Description: 自动点检接口
 *
 * @author Emmett
 * @since 2023-04-10 20:37
 */
public interface IAutoCheckService {

    void doCheck(String username, String password, SYSConfig sysConfig);

}
