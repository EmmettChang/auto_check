package com.emmett.auto_check.service.impl;

import com.emmett.auto_check.constants.Api;
import com.emmett.auto_check.domain.*;
import com.emmett.auto_check.service.IAutoCheckService;
import com.emmett.auto_check.utils.HttpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.emmett.auto_check.constants.Api.*;

/**
 * Description: 申请开票
 *
 * @author tpf
 * @since 2022-08-06
 */
@Slf4j
@Service
public class AutoCheckService implements IAutoCheckService {

    @Value("${login.id}")
    private String id;
    @Value("${login.name}")
    private String name;

    @Override
    public void doCheck() {
        RequetBody requetBody = new RequetBody();
        String fixedCookie = null;
        String newCookie = null;

        // 登录接口，获取cookie
        try {
            String loginUrl = String.format(Api.LoginRequestUrl, id, name);
            log.info(loginUrl);
            HashMap<String,String> loginParams = new HashMap<>();
            Response response = HttpUtil.formBodyPost(loginUrl, loginParams, null);
            List<String> loginCookies = response.headers("Set-Cookie");
            fixedCookie = getFixedCookie(loginCookies);
            newCookie = getNewCookie(loginCookies);
            log.info(newCookie);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 任务查询接口
        List<List<String>> rows = new ArrayList<>();
        try {
            QueryTaskReAndResBody queryTaskRequetBody = new Gson().fromJson(requetBody.getQueryTaskRequetBodyString(), QueryTaskReAndResBody.class);
            queryTaskRequetBody.setEfSecurityToken(fixedCookie);
            queryTaskRequetBody.setCOOKIE(newCookie);
            Response response = HttpUtil.jsonBodyPost(queryTaskRequestUrl, queryTaskRequetBody, fixedCookie + newCookie);
            List<String> loginCookies = response.headers("Set-Cookie");
            newCookie = getNewCookie(loginCookies);
            log.info(newCookie);
            assert response.body() != null;
            QueryTaskReAndResBody queryTaskResultBody = new Gson().fromJson(response.body().toString(), QueryTaskReAndResBody.class);
            rows = queryTaskResultBody.get__blocks__().getResult().getRows();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 任务详情查询后处理数据提交
        try {
            for (List<String> row : rows) {
                QueryTaskDetailRequetBody queryTaskDetailRequetBody = new Gson().fromJson(requetBody.getQueryTaskDetailRequetBodyString(), QueryTaskDetailRequetBody.class);
                queryTaskDetailRequetBody.setCheckStandardId(row.get(6));
                queryTaskDetailRequetBody.setCheckPlanInternalCode(row.get(24));
                Response dateilResponses = HttpUtil.jsonBodyPost(queryTaskDetailRequestUrl, queryTaskDetailRequetBody, fixedCookie + newCookie);
                List<String> loginCookies = dateilResponses.headers("Set-Cookie");
                newCookie = getNewCookie(loginCookies);
                log.info(newCookie);
                assert dateilResponses.body() != null;
                QueryTaskDetailResultBody queryTaskDetailResultBody = new Gson().fromJson(dateilResponses.body().toString(), QueryTaskDetailResultBody.class);
                List<List<String>> detailRows = queryTaskDetailResultBody.get__blocks__().getResultXc().getRows();

                /*if (rightTable[j].children[4].innerText == ""温度状态"") {
                    leftT.getElementsByTagName(""tr"")[j].children[0].firstElementChild.click();
                    await sleep(t);
                    data = null;
                    data = resultXcGrid.getCheckedRows()[0];
                    if (data != null) {
                        data.determinant = ""10"";
                        data.currentResult = 22;
                        saveB.click();
                        i++;
                    } else {
                        t = (t + 5) % 30;
                    }
                    break;*/

                CompletedRequestBody updateRequestBody = new Gson().fromJson(requetBody.getCompletedRequetBodyString(), CompletedRequestBody.class);
                Response updateResponses = HttpUtil.jsonBodyPost(updateTaskDetailRequestUrl, updateRequestBody, fixedCookie + newCookie);

                CompletedRequestBody completedRequestBody = new Gson().fromJson(requetBody.getCompletedRequetBodyString(), CompletedRequestBody.class);
                Response completedResponses = HttpUtil.jsonBodyPost(completedRequestUrl, completedRequestBody, fixedCookie + newCookie);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String getNewCookie(List<String> cookies) {
        String newCookie = "";
        for (String val : cookies) {
            if (val.startsWith("J")) {
                int index = val.indexOf(";");
                newCookie = val.substring(0, index + 1);
                break;
            }
        }
        return newCookie;
    }

    public String getFixedCookie(List<String> cookies) {
        String fixedCookie = "";
        for (String val : cookies) {
            if (val.startsWith("i")) {
                int index = val.indexOf(";");
                fixedCookie = val.substring(0, index + 1);
                break;
            }
        }
        return fixedCookie;
    }
}
