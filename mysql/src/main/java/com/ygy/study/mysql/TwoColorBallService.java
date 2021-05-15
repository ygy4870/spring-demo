package com.ygy.study.mysql;

import com.ygy.study.mysql.dao.InformationSchemaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TwoColorBallService {

    static String NUMBER_STRING = "01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,";

    @Autowired
    private InformationSchemaDao informationSchemaDao;

    /**
     * 是否连续 continueNumber 个数字
     * @param redStr
     * @param continueNumber
     * @return
     */
    public boolean checkContinuity(String redStr, int continueNumber) {
        continueNumber = continueNumber * 3;
        for (int i = 0; i < redStr.length() - continueNumber; i = i + 3) {
            String substring = redStr.substring(i, i + continueNumber - 1);
            if (NUMBER_STRING.indexOf(substring) > -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * 计算红/蓝号数字和
     * @param red
     * @param blue
     * @return
     */
    public int calcTotalValue(String red, String blue) {
        String[] split = red.split(",");
        int total = 0;
        for (String s : split) {
            int value = Integer.valueOf(s);
            total += value;
        }
        if (Objects.nonNull(blue)) {
            total += Integer.valueOf(blue);
        }
        return total;
    }

    /**
     * 红码全部是奇数或者偶数
     * @param red
     * @return
     */
    public boolean oddOrEven(String red) {
        String[] split = red.split(",");
        int oddNumber = 0;
        int evenNumber = 0;
        for (String s : split) {
            int value = Integer.valueOf(s);
            if ((value & 1) == 1) {
                oddNumber++;
            } else {
                evenNumber++;
            }
        }
        return oddNumber == 0 || evenNumber == 0;
    }

    /**
     * 红蓝号码重复
     * @param red
     * @param blue
     * @return
     */
    public boolean checkRedBlueRepeat(String red, String blue) {
        return red.indexOf(blue) > -1;
    }

    public double redBlueRepeatHistoryRate() {
        return informationSchemaDao.redBlueRepeatHistoryRate();
    }


    public boolean exitHistory(RandomRedBlue redBlue) {
        boolean exit = informationSchemaDao.exitHistory(redBlue) > 0;
        return exit;
    }

    public boolean exitHistory(TwoColorBall twoColorBall, int number) {
        String[] split = twoColorBall.getRed().split(",");
        RandomRedBlue redBlue = new RandomRedBlue();
        redBlue.setRed1(split[0]);
        redBlue.setRed2(split[1]);
        redBlue.setRed3(split[2]);
        redBlue.setRed4(split[3]);
        redBlue.setRed5(split[4]);
        redBlue.setRed6(split[5]);
        redBlue.setBlue(twoColorBall.getBlue());
        redBlue.setNumber(number);

        twoColorBall.setRedBlue(redBlue);
        boolean exit = informationSchemaDao.exitHistory(redBlue) > 0;
        return exit;
    }

    public int countEqualTotalValue(int total) {
        return informationSchemaDao.countEqualTotalValue(total);
    }

    public TwoColorBall randomTwoColorBall() {

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        TwoColorBall twoColorBall = new TwoColorBall();

        Random r = new Random(System.currentTimeMillis());
        List<Integer> arr = new ArrayList<>(6);

        int total = 0;
        while (arr.size() < 6) {
            int ran = r.nextInt(33) + 1;
            if (!arr.contains(ran)) {
                arr.add(ran);
                total += ran;
            }
        }
        Collections.sort(arr);

        StringBuilder sb = new StringBuilder();
        for (Integer integer : arr) {
            sb.append(integer >= 10 ? String.valueOf(integer) : "0"+integer).append(",");
        }
        twoColorBall.setRed(sb.substring(0, sb.length()-1));

        int ran = r.nextInt(16) + 1;
        twoColorBall.setBlue(ran>=10 ? ""+ran : "0"+ran);
        total += ran;
        twoColorBall.setTotal(total);

        return twoColorBall;
    }


    public TwoColorBall calcTwoColorBall(boolean isContinuity, boolean isRedBlueRepeat, boolean isExitHistory, boolean noOddOrEven, boolean isRedBlueTotalValue, int leftRedTotalValue, int rightRedTotalValue) {

        TwoColorBall twoColorBall = null;

        int a =0;
        int b=0;
        int c=0;
        int d=0;

        while (true) {
            twoColorBall = randomTwoColorBall();
            // 连续性检查，红码连续大于等于3个
            if (isContinuity && checkContinuity(twoColorBall.getRed(), 3)) {
                a++;
                continue;
            }
            // 红蓝重复
            if (isRedBlueRepeat && checkRedBlueRepeat(twoColorBall.getRed(), twoColorBall.getBlue())) {
                b++;
                continue;
            }
            // 排除红码全是奇数或偶数
            if (noOddOrEven && oddOrEven(twoColorBall.getRed())) {
                continue;
            }
            // 指定红码总和值
            if (leftRedTotalValue > 0 && rightRedTotalValue > 0 ) {
                int redTotalValue = calcTotalValue(twoColorBall.getRed(), null);
                if (redTotalValue > rightRedTotalValue || redTotalValue < leftRedTotalValue) {
                    continue;
                }
            }
            // 历史已存在 大于等于5个红蓝号码相同
            if (isExitHistory && exitHistory(twoColorBall, 5)) {
                c++;
                continue;
            }
            // 红蓝号，总和值相等
            if (isRedBlueTotalValue && countEqualTotalValue(twoColorBall.getTotal()) > 4) {
                d++;
                continue;
            }

            break;
        }

        System.out.println("a="+a+",b="+b+",c="+c+",d="+d);
        return twoColorBall;
    }

}
