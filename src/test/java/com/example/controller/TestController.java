package com.example.controller;

import com.example.model.Salary;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class TestController {

    //1. 임의로 설정한 변수들

//    Salary _salary = null;
//    String k = _salary.getStartTime();   //"2020-06-19T14:00:00.000";
//    LocalDateTime m = LocalDateTime.parse(k);
//        System.out.println(m);


    //상수
    Long money = 8590L;
    Long nmoney = 12885L;

    //야간 수당을 받는 시간 기준 (고정값)
    LocalTime night1 = LocalTime.of(06, 0,0,0);
    LocalTime night2 = LocalTime.of(22, 0,0,0);

    int salary = 15; // 임의로 월급날 15일 설정

    //변수

    //입력 날짜 LocalDateTime now = LocalDateTime.now();
    LocalDateTime now = LocalDateTime.of(2020, 06, 20, 10, 00,0,0);

    //입력받은 출퇴근 날짜+시간 (18:00 부터 다음날 03:00 까지 일했다고 가정)
    //입력시 최대 근무 가능 시간은 24시간 (시작날의 +24시간)
    LocalDateTime workdatetime1 = LocalDateTime.of(2020, 06, 16, 18, 00,0,0);
    LocalDateTime workdatetime2 = LocalDateTime.of(2020, 06, 17, 03, 00,0,0);

    //몇시부터 일했는지 (형변형)
    LocalTime worktime1 = LocalTime.from(workdatetime1);
    LocalTime worktime2 = LocalTime.from(workdatetime2);

    //전체 노동 시간
    Long working = ChronoUnit.HOURS.between(workdatetime1,workdatetime2);

    //주간,야간 근무 시간
    Long dayworking = 0L;
    Long nightworking = 0L;

    //일급, 주급, 월급
    Long daymoney = 0L;
    Long weekmoney = 0L;
    Long monthmoney = 0L;

    //2. 주급 계산

    @Test
    void week() {
        //월(1) ~ 일(7) 요일을 숫자로 출력 (.getDayOfWeek().getValue())
        int num = now.getDayOfWeek().getValue();

        //특정 날짜(now)에서 해당 주의 월요일 = now - (요일.getValue() - 1)

        for (num=num;num>=1;num--) {
           //??? $$ weekmoney += now.minusDays(num -1).일급; // 해당 주 월요일 부터 해당 요일 까지
        } //주급 계산 완료
        System.out.println("현재 요일을 숫자로 표현하면 "+num);
        System.out.println("당신의 주급은 " + weekmoney);
    }






    //3. 월급 계산

    @Test
        //월급날 지정. ex 7일이면 가장 최근의 8일부터 현재까지의 급여를 계산
    void month() {

        LocalDateTime salaryp = now.withDayOfMonth(salary); //이번달 월급일. (6월 15일 + 시간)
        LocalDate temp = LocalDate.from(salaryp); // 이번'달' 월급일 (6월 15일 +시간x)

        //5월 16일 기준 현재까지 걸리는 시간
        Long k = ChronoUnit.DAYS.between(salaryp, now);


        //이번'달' 월급일이 지나지 않은 경우 (ex현 날짜 6.2/월급일 매달 15일)
        if (!(now.isAfter(salaryp))) {
            temp = temp.minusMonths(1); //6월 15일
        }
        //이번달 월급일이 지났을 경우 그대로 (ex현 날짜 6.18/월급일 매달 15일)

        for (int i = 1; i < k; i++) {
            temp.plusDays(i); //5월 16일부터 현재날짜까지의 일수동안 하루씩 증가.

           //??? $$ monthmoney += temp.일급;
        }
        System.out.println("이번 '달' 월급일 날짜 "+temp);
        System.out.println("당신의 월급은 " + monthmoney);

    }
}
