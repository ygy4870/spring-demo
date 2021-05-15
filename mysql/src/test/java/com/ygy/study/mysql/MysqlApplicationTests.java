package com.ygy.study.mysql;

import com.alibaba.fastjson.JSON;
import com.ygy.study.mysql.dao.InformationSchemaDao;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.*;

@SpringBootTest
class MysqlApplicationTests {

    @Autowired
    private InformationSchemaDao informationSchemaDao;

    @Test
    void calcTotalValue() {
        List<TwoColorBall> twoColorBalls = informationSchemaDao.listAll();
        for (TwoColorBall twoColorBall : twoColorBalls) {
            int total = twoColorBallService.calcTotalValue(twoColorBall.getRed(), twoColorBall.getBlue());
            informationSchemaDao.updateTotal(total, twoColorBall.getTerm());
        }
    }

    @Test
    void contextLoads() {
        int num = 10;
        String url = "https://m.daguoxiaoxian.com/wechat-api/missValue/list?lotno=1001&count="+num+"&key=1001MV_X";
        String res = doGet(url, null);
        String substring = res.substring(1, res.length() - 1);
        AAA aaa = JSON.parseObject(substring, AAA.class);
        BBB bbb = JSON.parseObject(aaa.getValue(), BBB.class);
        for (CP cp : bbb.getResult()) {

            String[] split = cp.getWinCode().split("\\|");
            if (null==split || split.length < 2) {
                continue;
            }

            TwoColorBall byTerm = informationSchemaDao.getByTerm(cp.getBatchCode());
            if (Objects.nonNull(byTerm)) {
                continue;
            }

            TwoColorBall twoColorBall = new TwoColorBall();
            twoColorBall.setTerm(cp.getBatchCode());
            twoColorBall.setRed(split[0]);
            twoColorBall.setBlue(split[1]);
            twoColorBall.setTotal(twoColorBallService.calcTotalValue(twoColorBall.getRed(), twoColorBall.getBlue()));

            try {
                informationSchemaDao.insert(twoColorBall);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println(res);
    }



    /**
     * 以get方式调用第三方接口
     * @param url
     * @return
     */
    public static String doGet(String url, String token){
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet get = new HttpGet(url);

        try {
            get.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.81 Safari/537.36");
            HttpResponse response = httpClient.execute(get);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                //返回json格式
                String res = EntityUtils.toString(response.getEntity());
                return res;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Autowired
    TwoColorBallService twoColorBallService;

    @Test
    void isContinuity() {
        String str = "01,02,03,04,07,08,";
        boolean continuity = twoColorBallService.checkContinuity(str, 3);
        System.out.println(continuity);

    }

    @Test
    void redBlueRepeatHistoryRate() {
        double rate = twoColorBallService.redBlueRepeatHistoryRate();
        System.out.println(rate);
    }


    // 02,09,10,11,16,29
    @Test
    void exitHistory() {
        RandomRedBlue randomRedBlue = new RandomRedBlue();
        randomRedBlue.setRed1("02");
        randomRedBlue.setRed2("09");
        randomRedBlue.setRed3("10");
        randomRedBlue.setRed4("13");
        randomRedBlue.setRed5("33");
        randomRedBlue.setRed6("26");
        randomRedBlue.setBlue("03");
        randomRedBlue.setNumber(5);

        boolean exitHistory = twoColorBallService.exitHistory(randomRedBlue);

        System.out.println(exitHistory);
    }

    @Test
    void countEqualTotalValue() {
        int count = twoColorBallService.countEqualTotalValue(120);
        System.out.println(count);

    }


    @Test
    void randomTwoColorBall() {
        TwoColorBall twoColorBall = twoColorBallService.randomTwoColorBall();
        System.out.println(JSON.toJSON(twoColorBall));
    }

    @Test
    void calcTwoColorBall() {
        for (int i = 0; i < 5; i++) {
            TwoColorBall twoColorBall = twoColorBallService.calcTwoColorBall(
                    true,
                    false,
                    true,
                    true,
                    false,
                    100,
                    100);
            System.out.println(twoColorBall);
        }
    }


    @Test
    void number_info() {
        for (int i = 1; i <= 33; i++) {
            String number = (i < 10) ? "0"+i : String.valueOf(i);
            int countTotal = informationSchemaDao.calcCountByNumber(number, 10000);
            int count30 = informationSchemaDao.calcCountByNumber(number, 30);
            int count50 = informationSchemaDao.calcCountByNumber(number, 50);
            int count100 = informationSchemaDao.calcCountByNumber(number, 100);
            informationSchemaDao.insertNumberInfo(number, countTotal, count30, count50, count100);
        }
    }

}
