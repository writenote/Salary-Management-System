<template>
  <div class="weekly">
    <div class="subject">
      <h3>주급 계산하기</h3>
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
        <label>한 주 근무 일수</label>
        <b-dropdown
          id="workingDays" class="m-2" required
          :text="selectedDays"
          v-model="days"
        >
          <b-dropdown-item
            v-for="days in daysList" :key="days.id"
            @click="selectDays(days)"
          >
            {{ days }}
          </b-dropdown-item>
        </b-dropdown>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calWeeklyPay(hourlyWage, startTime, endTime, days)" class="btn btn-success">계산하기</button>
    </div>


    <div class="resultArea" v-show="result">
      <h5>일주일 총 근무시간은 하루 {{ dailyTotalTime }}시간 X {{ days }}일로 총 {{ weeklyTotalTime }}시간입니다.</h5>
      <h5> - 기본 수당: 일급 {{ dailyPay }}원 X {{ days }}일 = {{ basicDailyPay }}원</h5>
      <h5> - 주휴 수당: {{ holidayPay }}원</h5>
      <h5>총 주급은 {{ weeklyPay }}원입니다.</h5>
    </div>
  </div>
</template>

<script>
  export default {
    name: "WeeklyPay",
    data() {
      return {
        result: false,
        hourlyWage: null,
        startTime: "",
        endTime: "",
        days: "",
        dailyPay: 0,
        weeklyPay: 0,
        dailyTotalTime: 0,
        weeklyTotalTime: 0,
        basicDailyPay: 0,
        holidayPay: 0,
        selectedDays: '근무 일수 선택',
        daysList: ['1', '2', '3', '4', '5', '6', '7']
      }
    },
    methods: {
      selectDays(selectedItem) {
        this.selectedDays = selectedItem;
        this.days = this.selectedDays;
      },
      calWeeklyPay(hourlyWage, startTime, endTime, days) {

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
        this.weeklyTotalTime = this.dailyTotalTime * days;
        this.basicDailyPay = this.dailyPay * days;

        if(this.weeklyTotalTime >= 15 && this.weeklyTotalTime < 40) {
          this.holidayPay = (this.weeklyTotalTime / 40) * 8 * hourlyWage;
        } else if(this.weeklyTotalTime >= 40) {
          this.holidayPay = 8 * hourlyWage;
        }

        this.weeklyPay = parseInt(this.basicDailyPay) + parseInt(this.holidayPay);
        this.dailyPay = sum.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.basicDailyPay = this.basicDailyPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.holidayPay = this.holidayPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        this.weeklyPay = this.weeklyPay.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        // this.formatPay(this.dailyPay);
        // this.formatPay(this.basicDailyPay);
        // this.formatPay(this.holidayPay);
        // this.formatPay(this.weeklyPay);

        this.result = true;
      },
      formatPay(pay) {
        pay = pay.toString()
                .replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        return pay;
      }
    }
  }
</script>

<style lang="scss" scoped>

  .weekly {
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

        .m-2 {
          margin: 0px;  // 수정 필요
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
