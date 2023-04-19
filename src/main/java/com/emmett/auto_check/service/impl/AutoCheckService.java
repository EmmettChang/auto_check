package com.emmett.auto_check.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.emmett.auto_check.config.SYSConfig;
import com.emmett.auto_check.constants.Api;
import com.emmett.auto_check.domain.*;
import com.emmett.auto_check.service.IAutoCheckService;
import com.emmett.auto_check.utils.HttpUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

//    @Value("${login.username}")
//    private String username;
//    @Value("${login.password}")
//    private String password;

    /*每天九点执行*/
    @Override
//    @Scheduled(cron = "0 0 9 * * ?")
    public void doCheck(String id, String pwd, SYSConfig sysConfig) {
        Log log = LogFactory.getLog(AutoCheckService.class);

        RequetBody requetBody = new RequetBody();
        String efSecurityToken = "";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // 获取当月第一天
//        LocalDate firstDayOfMonth = LocalDate.now().withDayOfMonth(1);
//        String beginTime = sysConfig.getBeginTime().equals("") ? firstDayOfMonth.format(dateTimeFormatter) : sysConfig.getBeginTime();

        // 获取今天
        LocalDate today = LocalDate.now();
        String beginTime = sysConfig.getBeginTime().equals("") ? today.format(dateTimeFormatter) : sysConfig.getBeginTime();
        String endTime = sysConfig.getEndTime().equals("") ? today.format(dateTimeFormatter) : sysConfig.getEndTime();

        // 页面接口，获取token
        try {
            Response response = HttpUtil.jsonGet(LoginJspRequestUrl);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // 登录接口，获取cookie
        try {
            String loginUrl = String.format(Api.LoginRequestUrl, id, pwd);
            HashMap<String,String> loginParams = new HashMap<>();
            Response response = HttpUtil.formBodyPost(loginUrl, loginParams);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        // 页面接口，获取token
        try {
            Response response = HttpUtil.jsonGet(QCRT0101RequestUrl);
            String html = response.body().string();
            Document doc = Jsoup.parse(html);
            Element efSecurityTokenElement = doc.getElementById("efSecurityToken");
            efSecurityToken = efSecurityTokenElement.attr("value");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        // 任务查询接口
        List<List<String>> rows = new ArrayList<>();
        try {
            QueryTaskReAndResBody queryTaskRequestBody = new Gson().fromJson(requetBody.getQueryTaskRequetBodyString(), QueryTaskReAndResBody.class);
            queryTaskRequestBody.setEfSecurityToken(efSecurityToken);
            queryTaskRequestBody.setCOOKIE(MyCookieJar.getFixCookieValue());
//            log.info(MyCookieJar.getFixCookieValue());
            List<String> strings = queryTaskRequestBody.get__blocks__().getInqu_status().getRows().get(0);
            strings.set(2, beginTime);
            strings.set(3, endTime);
            Response response = HttpUtil.jsonBodyPost(queryTaskRequestUrl, queryTaskRequestBody, efSecurityToken);
            assert response.body() != null;
            QueryTaskReAndResBody queryTaskResultBody = new Gson().fromJson(response.body().string(), QueryTaskReAndResBody.class);
            rows = queryTaskResultBody.get__blocks__().getResult().getRows();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        if (rows.size() == 0) {
            log.info("***" + id + "：" + beginTime + "-" + endTime + "无点检");
            return;
        }

        // 任务详情查询后处理数据提交
        try {
            for (List<String> row : rows) {
                QueryTaskDetailRequetBody queryTaskDetailRequetBody = new Gson().fromJson(requetBody.getQueryTaskDetailRequetBodyString(), QueryTaskDetailRequetBody.class);
                queryTaskDetailRequetBody.setCheckStandardId(row.get(6));
                queryTaskDetailRequetBody.setCheckPlanInternalCode(row.get(24));
                Response dateilResponses = HttpUtil.jsonBodyPost(queryTaskDetailRequestUrl, queryTaskDetailRequetBody, "");
                assert dateilResponses.body() != null;
                QueryTaskDetailResultBody queryTaskDetailResultBody = new Gson().fromJson(dateilResponses.body().string(), QueryTaskDetailResultBody.class);
                List<List<String>> detailRows = queryTaskDetailResultBody.get__blocks__().getResultXc().getRows();
                List<String> dataRow = new ArrayList<>(), temp;
                //13,19,4,3,5,6,7(数值),9（10判定正常）,10,11,8,12,15,16,14,18
                List<List<String>> detailRowsSpe = detailRows.stream().filter(item -> sysConfig.getOpData().contains(item.get(6))).collect(Collectors.toList());
                for (List<String> detailRow: detailRowsSpe) {
                    detailRow.set(7, sysConfig.getOpValue());
                    detailRow.set(9, "10");
                    UpdateTaskDetailRequestBody updateTaskDetailRequestBody = new Gson().fromJson(requetBody.getUpdateTaskDetailRequetBodyString(), UpdateTaskDetailRequestBody.class);
                    temp = updateTaskDetailRequestBody.get__blocks__().getInqu_status().getRows().get(0);
                    temp.set(0, detailRow.get(3));
                    temp.set(2, beginTime);
                    temp.set(3, endTime);
                    dataRow.clear();
                    dataRow.add(detailRow.get(13));
                    dataRow.add(detailRow.get(19));
                    dataRow.add(detailRow.get(4));
                    dataRow.add(detailRow.get(3));
                    dataRow.add(detailRow.get(5));
                    dataRow.add(detailRow.get(6));
                    dataRow.add(detailRow.get(7));
                    dataRow.add(detailRow.get(9));
                    dataRow.add(detailRow.get(10));
                    dataRow.add(detailRow.get(11));
                    dataRow.add(detailRow.get(8));
                    dataRow.add(detailRow.get(12));
                    dataRow.add(detailRow.get(15));
                    dataRow.add(detailRow.get(16));
                    dataRow.add(detailRow.get(14));
                    dataRow.add(detailRow.get(18));
                    updateTaskDetailRequestBody.get__blocks__().getResultXc().getRows().set(0, dataRow);
                    Response response = HttpUtil.jsonBodyPost(updateTaskDetailRequestUrl, updateTaskDetailRequestBody, "");
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        try {
            CompletedRequestBody completedRequestBody = new Gson().fromJson(requetBody.getCompletedRequetBodyString(), CompletedRequestBody.class);
            List<List<String>> resultRows = completedRequestBody.get__blocks__().getResult().getRows();
            for (List<String> row : rows) {
                List<String> resultRow = new ArrayList<>();
                resultRow.add(row.get(24));
                resultRow.add(row.get(6));
                resultRow.add("0");
                resultRow.add(row.get(8));
                resultRow.add("1");
                resultRow.add(row.get(5));
                resultRow.add(row.get(13));
                resultRow.add(row.get(2));
                resultRow.add(row.get(13));
                resultRows.add(resultRow);
            }

            Response completedResponses = HttpUtil.jsonBodyPost(completedRequestUrl, completedRequestBody, efSecurityToken);
            log.info("***" + id + "：" + beginTime + "-" + endTime + "点检数：" + rows.size());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    public String getNewCookie(List<String> cookies) {
        String newCookie = "";
        for (String val : cookies) {
            if (val.startsWith("JSESSIONID")) {
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
            if (val.startsWith("iplat.sessionId")) {
                int index = val.indexOf(";");
                fixedCookie = val.substring(0, index);
                break;
            }
        }
        return fixedCookie;
    }
}
