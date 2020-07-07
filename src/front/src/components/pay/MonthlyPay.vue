<template>
  <div class="monthly">
    <div class="subject">
      <h3>월급 계산하기</h3>
    </div>
    <div class="inputForm">
      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
               v-model="hourlyWage"/>
      </div>

      <div class="startTime">
        <label for="startTime">시작 시간</label>
        <datetime type="time" id="startTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"
                  :input-style="'width: 375px; border: transparent'"
                  class="form-control"
                  v-model="startTime">  <!--class="theme-orange"-->
        </datetime>
      </div>

      <div class="endTime">
        <label for="endTime">종료 시간</label>
        <datetime type="time" id="endTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"
                  :input-style="'width: 375px; border: transparent'"
                  class="form-control"
                  v-model="endTime">
        </datetime>
      </div>

      <div class="workingDays">
        <label for="quantity">월 근무 일수</label>
        <div>
          <input type="number" id="quantity" name="quantity" min="1" max="31"
                 placeholder="근무 일수 입력"
                 class="form-control"
                 v-model="days">
          <p>※ 1부터 31까지만 입력됩니다.</p>
        </div>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calMonthlyPay(hourlyWage, startTime, endTime, days)" class="btn btn-success">계산하기</button>
    </div>

    <div class="resultArea" v-show="result">
      <h5>월 총 근무시간은 하루 {{ dailyTotalTime }}시간 X {{ days }}일로 총 {{ monthlyTotalTime }}시간입니다.</h5>
      <h5> - 기본 수당: 일급 {{ dailyPay }}원 X {{ days }}일 = {{ basicDailyPay }}원</h5>
      <h5> - 예상 주휴 수당: 한 주 주휴수당 {{ holidayPay }}원 X 4주 = {{ holidayTotalPay }}원</h5>
      <h5>총 예상 월급은 {{ monthlyPay }}원입니다.</h5>
    </div>
  </div>
</template>

<script>
  export default {
    name: "MonthlyPay",
    data() {
      return {
        result: false,
        hourlyWage: null,
        startTime: "",
        endTime: "",
        days: "",
        dailyPay: 0,
        monthlyPay: 0,
        dailyTotalTime: 0,
        weeklyTotalTime: 0,
        monthlyTotalTime: 0,
        basicDailyPay: 0,
        holidayPay: 0,
        holidayTotalPay: 0,
      }
    },
    methods: {
      calMonthlyPay(hourlyWage, startTime, endTime, days) {
        this.dailyTotalTime = 0;

        if(hourlyWage == null) {
          hourlyWage = 8590;
        } else {
          hourlyWage = parseInt(hourlyWage);
        }

        const formatStartTime = startTime.substring(11, 13);   // 2020-06-26T09:00:00.000+09:00 -> 09
        const formatEndTime = endTime.substring(11, 13);   // 12

        const workStartTime = parseInt(formatStartTime);   // 9
        const workEndTime = parseInt(formatEndTime);   // 12

        let timeList = new Array(24);
        for(let i=0; i<=23; i++) {
          timeList[i] = Boolean(false);
        }

        if(workStartTime < workEndTime) {   // 근무 시간이 하루 안에 시작 및 종료
          for(let i=workStartTime; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        } else {   // 근무 시간이 자정을 넘어감
          for(let i=workStartTime; i<=23; i++) {
            timeList[i] = Boolean(true);
          }
          for(let i=0; i<workEndTime; i++) {
            timeList[i] = Boolean(true);
          }
        }

        let timeMap = new Map();
        for(let i=0; i<=23; i++) {
          if(timeList[i] == Boolean(true)) {
            this.dailyTotalTime++;
            if(i>=6 && i<=21) {
              timeMap.set(i, hourlyWage);   // 주간 기본 시급
            } else {
              timeMap.set(i, (hourlyWage*1.5));   // 야간 수당
            }
          }
        }

        let sum=0;
        timeMap.forEach(function (value) {
          sum += value;
        });
        this.dailyPay = sum;
        this.weeklyTotalTime = this.dailyTotalTime * parseInt(days/4);
        console.log("주 총 근무시간: " + this.weeklyTotalTime);
        this.monthlyTotalTime = this.dailyTotalTime * days;
        this.basicDailyPay = this.dailyPay * days;

        if(this.weeklyTotalTime >= 15 && this.weeklyTotalTime < 40) {
          this.holidayPay = (this.weeklyTotalTime / 40) * 8 * hourlyWage;
        } else if(this.weeklyTotalTime >= 40) {
          this.holidayPay = 8 * hourlyWage;
        }
        console.log("한 주 주휴수당: " + this.holidayPay);
        this.monthlyPay = this.basicDailyPay + (this.holidayPay*4);
        this.holidayTotalPay = this.holidayPay * 4;
        this.dailyPay = sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.basicDailyPay = this.basicDailyPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.holidayPay = this.holidayPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.holidayTotalPay = this.holidayTotalPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.monthlyPay = this.monthlyPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        this.result = true;
      },
    }
  }
</script>

<style lang="scss" scoped>

  .monthly {
    margin-top: 100px;

    .subject {
      width: 1000px;
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .inputForm {

      justify-content: center;   // 화면 중앙
      text-align: center;
      max-width: 500px;
      margin: auto;

      .hourlyWageForm {

        padding-top: 30px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }

      }

      .startTime {
        padding-top: 30px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }
      }

      .endTime {
        padding-top: 30px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }
      }

      .workingDays {
        padding-top: 30px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }

        div > p {
          font-size: small;
          text-align: left;
          color: red;
          margin: 3px auto;
          width: 402px;

        }
      }
    }

    .btnArea {
      padding-top: 50px;
      padding-bottom: 50px;
      display: flex;
      width: 100px;
      justify-content: center;
      font-size: 18px;
      margin: 0 auto;
    }

    .resultArea {
      background-color: lightgrey;
      padding: 5% 5% 5% 15%;
      font-size: large;
      width: 100vw;
      margin: auto;
      align-items: center;
      justify-content: space-between;
    }
  }

</style>
